package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.service.RideRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/user", "/admin", "/driver"})
public class RideController {

    @Autowired
    private RideRequestService rideRequestService;

    //book a new ride for customer
    @RequestMapping(value = "/ride/book/new", method = RequestMethod.POST)
    public PersistentUserDetails bookNewRideRequest(@RequestBody RideRequest rideRequest, @AuthenticationPrincipal PersistentUserDetails userDetails) {
        return rideRequestService.openBookingRequest(rideRequest, userDetails.getUserId());
    }

    //cancel the already booked or opened ride for customer
    @RequestMapping(value = "/ride/book/cancel", method = RequestMethod.POST)
    public void cancelBookingRequestStatus(@RequestParam String bookingRequestId, @RequestParam String status) {
        rideRequestService.updateRideBookingStatus(bookingRequestId, Constants.CANCEL);
    }

    //accept the booking request for driver
    @RequestMapping(value = "/ride/book/accept", method = RequestMethod.POST)
    public RideRequest acceptBookingRequest(@RequestParam String bookingRequestId, @AuthenticationPrincipal PersistentUserDetails userDetails) {
        return rideRequestService.acceptRideBookingRequest(bookingRequestId, userDetails.getUserId());
    }

    //reject the booking request for driver
    @RequestMapping(value = "/ride/book/reject", method = RequestMethod.POST)
    public void rejectBookingRequest(@RequestParam String bookingRequestId, @AuthenticationPrincipal PersistentUserDetails userDetails) {
        rideRequestService.rejectRideBookingRequest(bookingRequestId, userDetails.getUserId());
    }

    //close the booking request after trip end for driver
    @RequestMapping(value = "/ride/book/close", method = RequestMethod.POST)
    public void closeBookingRequestStatus(@RequestParam String bookingRequestId, @RequestParam String status) {
        rideRequestService.updateRideBookingStatus(bookingRequestId, Constants.CLOSE);
    }


    //primarily use to fetch all new booking request for driver
    @RequestMapping(value = "/rides/status/{option}", method = RequestMethod.GET)
    public List<RideRequest> getRidesRequestsByStatus(@AuthenticationPrincipal PersistentUserDetails userDetails, @PathVariable String option) {
        return rideRequestService.fetchRideBookingRequestsForDriversByDBSAndDriverId(userDetails.getUserId(), option);

    }

    //update the driver duty status ie. status=ON_DUTY/OFF_DUTY
    @RequestMapping(value = "/duty/status", method = RequestMethod.POST)
    public void updateDriveStatus(@AuthenticationPrincipal PersistentUserDetails userDetails, @RequestParam String status) {
        rideRequestService.updateDriverDutyStatus(userDetails.getUserId(), status);
    }

    //get the driver duty status
    @RequestMapping(value = "/duty/status", method = RequestMethod.GET)
    public Map<Integer, String> getDriverDutyStatus(@AuthenticationPrincipal PersistentUserDetails userDetails) {
        return rideRequestService.getDriverDutyStatus(userDetails.getUserId());
    }

    //get all the ride requests
    @RequestMapping(value = "/rides", method = RequestMethod.GET)
    public List<RideRequest> getRidesRequests(@AuthenticationPrincipal PersistentUserDetails userDetails) {
        return rideRequestService.fetchRideBookingRequestsByUserId(userDetails.getUserId());

    }

    //write user current location
    @RequestMapping(value = "/location/write", method = RequestMethod.POST)
    public void writeUserLocation (@AuthenticationPrincipal PersistentUserDetails userDetails, @RequestBody Location location) {
        location.setUserId(userDetails.getUserId());
        rideRequestService.writeUserLocation(location);
    }
}
