package com.tzs.marshall.repo;

import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.RideRequest;

import java.util.List;
import java.util.Map;

public interface RideRequestRepository {

    List<Integer> fetchDriverIdsByStatus(String status);

    Map<Integer, Location> fetchDriverLocationsAndIdsByStatus(String driverStatus);

    long saveBookingRequest(RideRequest rideRequest, Long userId);

    void insertNewRequestForNearestAvailableDrivers(List<RideRequest> persistentNearestDrivers);

    Map<String, Long> getExistingBookingStatusByUserId(Long userId);

    void updateBookingRequest(RideRequest rideRequest, Long userId);

    List<RideRequest> getAllRideBookingRequestsByDBSAndDriverId(Long userId, String status);

    List<RideRequest> getAllRideBookingRequestsByUserId(Long userId);

    void updateDriverDutyStatusById(Long userId, String status);

    void writeUserCurrentLocation(Location location);

    void updateRideBookingRequestStatusByBookingId(Long bookingRequestId, String status);

    void acceptRideBookingRequest(Long valueOf, Long userId, String status);

    void updateDriverBookingStatus(Long bookingRequestId, Long driverId, String status);

    List<RideRequest> getRideBookingRequestByBookingId(Long bookingRequestId);

    void rejectRideBookingRequest(Long bookingRequestId, Long driverId);

    String getDriverDutyStatusById(Long userId);

    int updatePaymentStatusByRideBookingRequestId(Long bookingRequestId, String paymentStatus);

    List<Integer> getDriverBookingRequestByStatusAndBookingId(Long bookingRequestId, String status);

    void insertOrUpdateFirebaseTokenById(Long userId, String token);

    Map<Long, String> getFirebaseTokenByDriverId(List<Long> userId);

    Location getUserLocationById(Long driverId);
}
