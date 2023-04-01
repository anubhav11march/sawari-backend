package com.tzs.marshall.service.impl;

import com.google.gson.Gson;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.RideRequestRepository;
import com.tzs.marshall.service.RideRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.tzs.marshall.constants.Constants.*;

@Service
public class RideRequestServiceImpl implements RideRequestService {

    @Autowired
    private RideRequestRepository rideRequestRepository;
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
            log.info("Fetching AVAILABLE drivers");
            Map<Integer, Location> driverLocations = rideRequestRepository.fetchDriverLocationsAndIdsByStatus(AVAILABLE);
            nearestAvailableDrivers = rideRequestHelper.findNearestAvailableDrivers(rideRequest, driverLocations);
            if (nearestAvailableDrivers.isEmpty()) {
                log.error("There is no nearest driver available.");
                //TODO: check if exception is required
                throw new ApiException("There is no available driver at this moment");
            }
            List<RideRequest> persistentNearestDrivers = rideRequestHelper.persistNearestAvailableDriverWithBookingId(bookingRequestId, nearestAvailableDrivers, bookingStatus);
            log.info("saving new requests in db for drivers to broadcast");
            rideRequestRepository.insertNewRequestForNearestAvailableDrivers(persistentNearestDrivers);

            //broadcast ride requests
            rideRequestHelper.broadcastNotificationToDrivers(nearestAvailableDrivers, rideRequest, null);

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
                responseMap = rideRequestHelper.prepareRideAcceptResponseMap(rideRequest, (long) acceptedDriverId.stream().findFirst().get());
                rideRequestRepository.updateRideBookingRequestStatusByBookingId(bookingRequestId, BOOK);
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
    public Map<String, Object> fetchRideBookingRequestsByUserId(Long userId, String currentRide) {
        Map<String, Object> responseMap = new HashMap<>();
        List<RideRequest> allRideBookingRequestsByUserId = rideRequestRepository.getAllRideBookingRequestsByUserId(userId);
        if (currentRide != null && currentRide.equalsIgnoreCase("Y")) {
            List<String> status = List.of(OPEN, BOOK, START);
            List<RideRequest> filteredRides = allRideBookingRequestsByUserId.stream().filter(ride -> status.contains(ride.getBookingStatus())).collect(Collectors.toList());
            if (!filteredRides.isEmpty()) {
                filteredRides.sort(Comparator.comparing(RideRequest::getModifyDate).reversed());
                RideRequest rideRequest = filteredRides.get(0);
                responseMap = rideRequestHelper.prepareRideAcceptResponseMap(rideRequest, rideRequest.getDriverId());
                return responseMap;
            }
        }
        responseMap.put("allRides", allRideBookingRequestsByUserId);
        return responseMap;
    }

    @Override
    public void updateDriverDutyStatus(Long userId, String status) {
        if (!AVAILABLE.equalsIgnoreCase(status) && !OFF_DUTY.equalsIgnoreCase(status)) {
            throw new ApiException(String.format("%s is not a valid status. Please use %s or %s", status, AVAILABLE, OFF_DUTY));
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
            //A trip can only be closed by driver
            List<RideRequest> rideBookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
            RideRequest rideRequest = rideBookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
            if (PAID.equalsIgnoreCase(rideRequest.getPaymentStatus())) {
                rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), status.toUpperCase());
                rideRequestRepository.updateDriverDutyStatusById(userId, AVAILABLE);
            } else {
                throw new ApiException(MessageConstants.PAYMENT_ERR);
            }
        } else if (CANCEL.equalsIgnoreCase(status)) {
            List<RideRequest> allRideBookingRequestsByUserId = rideRequestRepository.getAllRideBookingRequestsByUserId(userId);
            List<String> statusList = List.of(OPEN, BOOK, START);
            allRideBookingRequestsByUserId = allRideBookingRequestsByUserId.stream().filter(ride -> statusList.contains(ride.getBookingStatus())).collect(Collectors.toList());
            if (!allRideBookingRequestsByUserId.isEmpty()) {
                RideRequest rideRequest = allRideBookingRequestsByUserId.get(0);
                rideRequestRepository.updateRideBookingRequestStatusByBookingId(rideRequest.getBookingRequestId(), status.toUpperCase());
                if (rideRequest.getDriverId() != null) {
                    String message = "This ride has been canceled by customer";
                    rideRequestHelper.broadcastNotificationToDrivers(List.of(rideRequest.getDriverId()), rideRequest, message);
                    rideRequestRepository.updateDriverDutyStatusById(rideRequest.getDriverId(), AVAILABLE);
                }
            }
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
            rideRequestRepository.updateDriverDutyStatusById(driverId, ON_DUTY);
            List<RideRequest> bookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
            RideRequest acceptedRideRequest = bookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
            String navigationLink = createNavigationLink(acceptedRideRequest, false);
            acceptedRideRequest.setNavigationLink(navigationLink);
            return acceptedRideRequest;
        } else {
            log.error("{} bookingRequest has already been accepted by driverId: {}", bookingRequestId, acceptedDrivers);
            throw new ApiException("This request is already accepted");
        }
    }

    private String createNavigationLink(RideRequest acceptedRideRequest, boolean fromPickupLocation) {
        String url= DBProperties.properties.getProperty("BASE_NAVIGATION_URL", "https://www.google.com/maps/dir/?api=1");
        String origin;
        String destination;
        if (fromPickupLocation) {
            origin = acceptedRideRequest.getPickupLocationPoints();
            destination = acceptedRideRequest.getDropLocationPoints();
        } else {
            Location userLocationById = rideRequestRepository.getUserLocationById(acceptedRideRequest.getDriverId());
            origin = String.valueOf(userLocationById.getLatitude()).concat(",").concat(String.valueOf(userLocationById.getLongitude()));
            destination = acceptedRideRequest.getPickupLocationPoints();
        }
        String filter = "&origin=".concat(origin).concat("&destination=").concat(destination).concat("&travelmode=driving&dir_action=navigate");
        return url.concat(filter);
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
        String navigationLink = createNavigationLink(rideRequest, true);
        rideRequest.setNavigationLink(navigationLink);
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
