package com.tzs.marshall.service;

import com.sun.istack.NotNull;
import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.RideRequest;

import java.util.List;
import java.util.Map;

public interface RideRequestService {

    void handleRideRequest(@NotNull RideRequest rideRequest, String option);

    Map<String, Object> openBookingRequest(RideRequest rideRequest, Long userId);

    List<RideRequest> fetchRideBookingRequestsForDriversByDBSAndDriverId(Long userId, String status);

    Map<String, Object> fetchRideBookingRequestsByUserId(Long userId, String currentRide);

    void updateDriverDutyStatus(Long userId, String status);

    void writeUserLocation(Location location);

    void updateRideBookingStatus(@NotNull String rideRequest, String status, Long userId);

    RideRequest acceptRideBookingRequest(String bookingRequestId, Long userId);

    void rejectRideBookingRequest(String bookingRequestId, Long userId);

    String getDriverDutyStatus(Long userId);

    RideRequest verifyOtpAndStartRide(@NotNull String otp, String bookingRequestId);

    Boolean updatePaymentStatusOfRideBookingRequest(String bookingRequestId, String paymentStatus);

    Map<String, Object> getTotalEarningByDriver(Long userId);

    void createOrUpdateFirebaseToken(Long userId, String token);

    String getFirebaseTokenById(Long userId);
}
