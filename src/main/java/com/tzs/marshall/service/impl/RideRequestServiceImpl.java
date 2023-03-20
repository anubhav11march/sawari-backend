package com.tzs.marshall.service.impl;

import com.google.gson.Gson;
import com.sun.istack.NotNull;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.bean.distnaceMatrix.DistanceDuration;
import com.tzs.marshall.bean.distnaceMatrix.DistanceMatrix;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.RideRequestRepository;
import com.tzs.marshall.repo.UserPostLoginRepository;
import com.tzs.marshall.service.RideRequestService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.tzs.marshall.constants.Constants.*;

@Service
public class RideRequestServiceImpl implements RideRequestService {

    @Autowired
    private RideRequestRepository rideRequestRepository;
    @Autowired
    private UserPostLoginRepository userPostLoginRepository;
    @Autowired
    private RideRequestHelper rideRequestHelper;

    private static Logger log = LoggerFactory.getLogger(RideRequestServiceImpl.class);

    @Override
    public void handleRideRequest(RideRequest rideRequest, String option) {
    }

    @Override
    public Map<String, Object> openBookingRequest(RideRequest rideRequest, Long userId) {
        log.info("New ride request received.");
        Map<String, Object> responseMap = new HashMap<>();
        String bookingStatus = OPEN;
        Long bookingRequestId = null;
        int randomPin = (int) (Math.random() * 900000) + 100000;
        String otp = String.valueOf(randomPin);
        rideRequest.setBookingStatus(bookingStatus);
        rideRequest.setOtp(otp);
        rideRequest.setPaymentStatus(PENDING);
        try {
            //fetch any existing booking status of the customer
            Map<String, Long> statusIdMap = rideRequestRepository.getExistingBookingStatusByUserId(userId);
            //if status is OPEN then update the record else insert new record
            bookingRequestId = statusIdMap.get(OPEN);
            if (Objects.nonNull(bookingRequestId)) {
                rideRequest.setBookingRequestId(Objects.requireNonNull(bookingRequestId));
                rideRequestRepository.updateBookingRequest(rideRequest, userId);
            } else {
                bookingRequestId = rideRequestRepository.saveBookingRequest(rideRequest, userId);
            }
            List<Long> nearestAvailableDrivers = new ArrayList<>();
            log.info("Fetching ON_DUTY drivers");
            Map<Integer, Location> driverLocations = rideRequestRepository.fetchDriverLocationsAndIdsByStatus(ON_DUTY);
            nearestAvailableDrivers = rideRequestHelper.findNearestAvailableDrivers(rideRequest, driverLocations);
            if (nearestAvailableDrivers.isEmpty()) {
                log.error("There is no nearest driver available.");
                throw new ApiException("There is no available driver at this moment");
            }
            List<RideRequest> persistentNearestDrivers = rideRequestHelper.persistNearestAvailableDriverWithBookingId(bookingRequestId, nearestAvailableDrivers, bookingStatus);
            log.info("saving new requests in db for drivers to broadcast");
            rideRequestRepository.insertNewRequestForNearestAvailableDrivers(persistentNearestDrivers);

            //broadcast ride requests
            rideRequestHelper.broadcastRideRequests(nearestAvailableDrivers, rideRequest);

            //wait for the driver to accept the request
            List<Integer> acceptedDriverId = new ArrayList<>();
            long interval = Long.parseLong(DBProperties.properties.getProperty("REQUEST_ACCEPT_INTERVAL"));
            int count = 0;
            while (count < 3 && acceptedDriverId.isEmpty()) {
                log.info("Waiting for drivers to accept the request");
                Thread.sleep(interval);
                //get the driver detail who has accepted the booking request
                acceptedDriverId = rideRequestRepository.getDriverBookingRequestByStatusAndBookingId(bookingRequestId, ACCEPT);
                if (!acceptedDriverId.isEmpty()) {
                    log.info("{} driver has accepted the request", acceptedDriverId);
                    break;
                }
                count++;
            }
            //update driver details in booking request: this has been handled at acceptRideBookingRequest method
            //send customer/booking request details to driver : this has been handled while a driver accept the booking request
            // and driver details to customer i.e. send profile pic, rickshaw pics as well
            if (!acceptedDriverId.isEmpty()) {
                List<PersistentUserDetails> driverDetails = userPostLoginRepository.getUserProfileAndEssentialDetailsById((long) acceptedDriverId.stream().findFirst().get());
                PersistentUserDetails driver = driverDetails.stream().findFirst().get();
                rideRequestHelper.prepareResponse(responseMap, rideRequest, driver);
                log.info("Request processing complete");
                return responseMap;
            } else {
                log.error("No driver has accepted the request");
                throw new ApiException("No driver has accepted the request");
            }
        } catch (Exception e) {
            log.error("Something went wrong: {}", e.getMessage());
            rideRequestHelper.rollBack(rideRequest, bookingRequestId);
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public List<RideRequest> fetchRideBookingRequestsForDriversByDBSAndDriverId(Long userId, String status) {
        List<RideRequest> allBookingRequestsByIdAndStatus = rideRequestRepository.getAllRideBookingRequestsByDBSAndDriverId(userId, status.toUpperCase());
        return allBookingRequestsByIdAndStatus;
    }

    @Override
    public List<RideRequest> fetchRideBookingRequestsByUserId(Long userId) {
        return rideRequestRepository.getAllRideBookingRequestsByUserId(userId);
    }

    @Override
    public void updateDriverDutyStatus(Long userId, String status) {
        if (!ON_DUTY.equalsIgnoreCase(status) && !OFF_DUTY.equalsIgnoreCase(status)) {
            throw new ApiException(String.format("%s is not a valid status. Please use %s or %s", status, ON_DUTY, OFF_DUTY));
        }
        rideRequestRepository.updateDriverDutyStatusById(userId, status.toUpperCase());
    }

    @Override
    public void writeUserLocation(Location location) {
        rideRequestRepository.writeUserCurrentLocation(location);
    }

    @Override
    public void updateRideBookingStatus(String bookingRequestId, String status, Long userId) {
        if (CLOSE.equalsIgnoreCase(status)) {
            List<RideRequest> rideBookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
            RideRequest rideRequest = rideBookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
            if (PAID.equalsIgnoreCase(rideRequest.getPaymentStatus())) {
                rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), status.toUpperCase());
            } else {
                throw new ApiException(MessageConstants.PAYMENT_ERR);
            }
        } else if (CANCEL.equalsIgnoreCase(status)) {
            Map<String, Long> existingBookingStatusByUserId = rideRequestRepository.getExistingBookingStatusByUserId(userId);
            Long id = existingBookingStatusByUserId.get(OPEN) != null ? existingBookingStatusByUserId.get(OPEN) : existingBookingStatusByUserId.get(BOOK) ;
            if (id == null)
                throw new ApiException(MessageConstants.NO_OPEN_RIDE_FOUND);
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(id, status.toUpperCase());
        } else {
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), status.toUpperCase());
        }
    }

    @Override
    public RideRequest acceptRideBookingRequest(String bookingRequestId, Long driverId) {
        List<Integer> acceptedDrivers = rideRequestRepository.getDriverBookingRequestByStatusAndBookingId(Long.valueOf(bookingRequestId), ACCEPT);
        if (acceptedDrivers.isEmpty()) {
            rideRequestRepository.updateDriverBookingStatus(Long.valueOf(bookingRequestId), null, CLOSE);
            rideRequestRepository.updateDriverBookingStatus(Long.valueOf(bookingRequestId), driverId, ACCEPT);
            rideRequestRepository.acceptRideBookingRequest(Long.valueOf(bookingRequestId), driverId, BOOK);
            List<RideRequest> bookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
            return bookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
        } else {
            log.error("{} bookingRequest has already been accepted by driverId: {}", bookingRequestId, acceptedDrivers);
            throw new ApiException("This request is already accepted");
        }
    }

    @Override
    public void rejectRideBookingRequest(String bookingRequestId, Long driverId) {
        rideRequestRepository.rejectRideBookingRequest(Long.valueOf(bookingRequestId), driverId);
    }

    @Override
    public String getDriverDutyStatus(Long userId) {
        return rideRequestRepository.getDriverDutyStatusById(userId);
    }

    @Override
    public RideRequest verifyOtpAndStartRide(String otp, String bookingRequestId) {
        log.info("verifying OTP..");
        List<RideRequest> rideBookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
        RideRequest rideRequest = rideBookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
        if (rideRequest.getOtp().equalsIgnoreCase(otp)) {
            log.info("OTP verified, ride request set to Start");
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), START);
            rideRequest.setBookingStatus(START);
        } else {
            log.error("Invalid OTP");
            throw new ApiException(MessageConstants.INVALID_TOKEN);
        }
        return rideRequest;
    }

    @Override
    public Boolean updatePaymentStatusOfRideBookingRequest(String bookingRequestId, String paymentStatus) {
        int i = rideRequestRepository.updatePaymentStatusByRideBookingRequestId(Long.valueOf(bookingRequestId), paymentStatus.toUpperCase());
        return i == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Map<String, Object> getTotalEarningByDriver(Long userId) {
        log.info("fetching all rides...");
        Map<String, Object> rideMap = new HashMap<>();
        List<RideRequest> allRideBookingRequestsByUserId = rideRequestRepository.getAllRideBookingRequestsByUserId(userId);
        log.info("calculating total earnings..");
        rideRequestHelper.getTotalEarning(allRideBookingRequestsByUserId, rideMap);
        List<RideRequest> closedRides = (List<RideRequest>) rideMap.get("totalRides");
        log.info("calculating daily earnings...");
        rideRequestHelper.getTotalEarningOfTheDay(closedRides, rideMap);
        log.info("calculation complete");
        return rideMap;
    }

    @Override
    public void createOrUpdateFirebaseToken(Long userId, String token) {
        Map tokenMap = new Gson().fromJson(token, Map.class);
        token = String.valueOf(tokenMap.get("token"));
        rideRequestRepository.insertOrUpdateFirebaseTokenById(userId, token);
    }

    @Override
    public String getFirebaseTokenById(Long userId) {
        Map<Long, String> firebaseTokenByDriverId = rideRequestRepository.getFirebaseTokenByDriverId(List.of(userId));
        return firebaseTokenByDriverId.get(userId);
    }

}
