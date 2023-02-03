package com.tzs.marshall.service.impl;

import com.sun.istack.NotNull;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.RideRequestRepository;
import com.tzs.marshall.repo.UserPostLoginRepository;
import com.tzs.marshall.service.RideRequestService;
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

    @Override
    public void handleRideRequest(RideRequest rideRequest, String option) {
    }

    @Override
    public PersistentUserDetails openBookingRequest(RideRequest rideRequest, Long userId) {
        String driverStatus = ON_DUTY;
        String bookingStatus = OPEN;
        Long bookingRequestId;
        int randomPin = (int) (Math.random() * 900000) + 100000;
        String otp = String.valueOf(randomPin);
        rideRequest.setBookingStatus(bookingStatus);
        rideRequest.setOtp(otp);
        //fetch any existing booking status of the customer
        Map<String, Long> statusIdMap = rideRequestRepository.getExistingBookingStatusByUserId(userId);
        //if status is OPEN or NOT_SERVED then update the record else insert new record
        bookingRequestId = statusIdMap.get(OPEN);
        if (Objects.nonNull(bookingRequestId)) {
            rideRequest.setBookingRequestId(Objects.requireNonNull(bookingRequestId));
            rideRequestRepository.updateBookingRequest(rideRequest, userId);
        } else {
            bookingRequestId = rideRequestRepository.saveBookingRequest(rideRequest, userId);
        }
        List<Long> nearestAvailableDrivers = null;
        int count = 0;
        try {
            while (count <= 3 && nearestAvailableDrivers == null) {
                Map<Integer, Location> driverLocations = rideRequestRepository.fetchDriverLocationsAndIdsByStatus(driverStatus);
                nearestAvailableDrivers = findNearestAvailableDrivers(rideRequest, driverLocations);
                if (nearestAvailableDrivers == null || nearestAvailableDrivers.isEmpty()) {
                    //TODO: ask driver to update their current location
                    Thread.sleep(5000);
                }
                count++;
            }
        } catch (InterruptedException e) {
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
        if (nearestAvailableDrivers.size()==0) {
            rideRequest.setBookingStatus(NOT_SERVED);
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(bookingRequestId, rideRequest.getBookingStatus());
            throw new ApiException("There is no available driver at this moment");
        }
        List<RideRequest> persistentNearestDrivers = persistNearestAvailableDriverWithBookinId(bookingRequestId, nearestAvailableDrivers, bookingStatus);
        rideRequestRepository.insertNewRequestForNearestAvailableDrivers(persistentNearestDrivers);
        //broadcast the booking request to the nearest available drivers
        //wait for the driver to accept the request
        //update driver details in booking request
        //send driver details to customer and customer details to driver
        //send profile pic, rickshaw pics as well
        List<PersistentUserDetails> driverDetails = userPostLoginRepository.getUserProfileAndEssentialDetailsById(nearestAvailableDrivers.get(0));
        return driverDetails.stream().findFirst().get();
    }

    private List<RideRequest> persistNearestAvailableDriverWithBookinId(Long bookingRequestId, List<Long> nearestAvailableDrivers, String bookingStatus) {
        List<RideRequest> persistentNearestAvailableDrivers = new ArrayList<>();
        nearestAvailableDrivers.forEach( d -> {
            RideRequest rideRequest = new RideRequest(bookingRequestId, d, bookingStatus);
            persistentNearestAvailableDrivers.add(rideRequest);
        });
        return persistentNearestAvailableDrivers;
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
    public void updateRideBookingStatus(String bookingRequestId, String status) {
        if (CLOSE.equalsIgnoreCase(status)) {
            List<RideRequest> rideBookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
            RideRequest rideRequest = rideBookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
            if (PAID.equalsIgnoreCase(rideRequest.getPaymentStatus())) {
                rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), status.toUpperCase());
            } else {
                throw new ApiException(MessageConstants.PAYMENT_ERR);
            }
        } else {
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), status.toUpperCase());
        }
    }

    @Override
    public RideRequest acceptRideBookingRequest(String bookingRequestId, Long driverId) {
        rideRequestRepository.updateDriverBookingStatus(Long.valueOf(bookingRequestId), driverId, ACCEPT);
        rideRequestRepository.updateDriverBookingStatus(Long.valueOf(bookingRequestId), null, CLOSE);
        rideRequestRepository.acceptRideBookingRequest(Long.valueOf(bookingRequestId), driverId, BOOK);
        List<RideRequest> bookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
        return bookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
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
        List<RideRequest> rideBookingRequestByBookingId = rideRequestRepository.getRideBookingRequestByBookingId(Long.valueOf(bookingRequestId));
        RideRequest rideRequest = rideBookingRequestByBookingId.stream().findFirst().orElse(new RideRequest());
        if (rideRequest.getOtp().equalsIgnoreCase(otp)) {
            rideRequestRepository.updateRideBookingRequestStatusByBookingId(Long.valueOf(bookingRequestId), START);
            rideRequest.setBookingStatus(START);
        } else {
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

        Map<String, Object> rideMap = new HashMap<>();
        List<RideRequest> allRideBookingRequestsByUserId = rideRequestRepository.getAllRideBookingRequestsByUserId(userId);
        getTotalEarning(allRideBookingRequestsByUserId, rideMap);
        List<RideRequest> closedRides = (List<RideRequest>) rideMap.get("totalRides");
        getTotalEarningOfTheDay(closedRides, rideMap);
        return rideMap;
    }

    private void getTotalEarning(List<RideRequest> allRideBookingRequestsByUserId, Map<String, Object> rideMap) {
        AtomicReference<Double> totalEarnings = new AtomicReference<>(0D);
        List<RideRequest> closedRides = new ArrayList<>();
        allRideBookingRequestsByUserId.stream().filter(ride -> CLOSE.equalsIgnoreCase(ride.getBookingStatus()))
                .forEach(ride -> {
                    totalEarnings.set(totalEarnings.get() + ride.getFare());
                    closedRides.add(ride);
                });
        Double commission = ((Double.parseDouble(DBProperties.properties.getProperty("COMMISSION", "20")) * totalEarnings.get()) / 100);
        Double balance = totalEarnings.get() - commission;
        rideMap.put("totalEarning", totalEarnings);
        rideMap.put("totalRides", closedRides);
        rideMap.put("totalCommission", commission);
        rideMap.put("balance", balance);
    }

    private void getTotalEarningOfTheDay(List<RideRequest> closedRides, Map<String, Object> rideMap) {
        AtomicReference<Double> totalEarningsOfTheDay = new AtomicReference<>(0D);
        List<RideRequest> closedRidesOfTheDay = new ArrayList<>();
        closedRides.stream().filter(ride -> ride.getDate().equals(Date.valueOf(LocalDate.now())))
                .forEach(ride -> {
                    totalEarningsOfTheDay.set(totalEarningsOfTheDay.get() + ride.getFare());
                    closedRidesOfTheDay.add(ride);
                });
        Double totalCommissionOfTheDay = ((Double.parseDouble(DBProperties.properties.getProperty("COMMISSION", "20")) * totalEarningsOfTheDay.get()) / 100);
        Double balanceOfTheDay = totalEarningsOfTheDay.get() - totalCommissionOfTheDay;

        rideMap.put("totalEarningOfTheDay", totalEarningsOfTheDay.get());
        rideMap.put("totalRidesOfTheDay", closedRidesOfTheDay);
        rideMap.put("totalCommissionOfTheDay", totalCommissionOfTheDay);
        rideMap.put("balanceOfTheDay", balanceOfTheDay);
    }

    private List<Long> findNearestAvailableDrivers(RideRequest rideRequest, @NotNull Map<Integer, Location> driverLocations) {
        List<Long> nearestDriverIds = driverLocations.values().stream().filter(loc -> {
            double driverCustomerDistance = calculateDriverAndCustomerDistance(rideRequest.getPickupLocation().getLatitude(), rideRequest.getPickupLocation().getLongitude(), loc.getLatitude(), loc.getLongitude());
            return driverCustomerDistance < Double.parseDouble(DBProperties.properties.getProperty("BOOKING_RADIUS", "3.0"));
        }).map(Location::getUserId).collect(Collectors.toList());
        return nearestDriverIds;
    }

    private Double calculateDriverAndCustomerDistance(Double customerPickupLatitude, Double customerPickupLongitude, Double driverLatitude, Double driverLongitude) {
        //TODO: Google Maps Api Call to calculate distance between two points
        return 02.0;
    }


}
