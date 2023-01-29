package com.tzs.marshall.service;

import com.sun.istack.NotNull;
import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.RideRequest;

import java.util.List;
import java.util.Map;

public interface RideRequestService {

    void handleRideRequest(@NotNull RideRequest rideRequest, String option);

    PersistentUserDetails openBookingRequest(RideRequest rideRequest, Long userId);

    List<RideRequest> fetchRideBookingRequestsForDriversByDBSAndDriverId(Long userId, String status);

    List<RideRequest> fetchRideBookingRequestsByUserId(Long userId);

    void updateDriverDutyStatus(Long userId, String status);

    void writeUserLocation(Location location);

    void updateRideBookingStatus(@NotNull String rideRequest, String status);

    RideRequest acceptRideBookingRequest(String bookingRequestId, Long userId);

    void rejectRideBookingRequest(String bookingRequestId, Long userId);

    Map<Integer, String> getDriverDutyStatus(Long userId);
}
