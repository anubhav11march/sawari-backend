package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.Location;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.RideRequest;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.service.RideRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.tzs.marshall.constants.Constants.ADMIN;
import static com.tzs.marshall.constants.Constants.DRIVER;

@RestController
@RequestMapping({"/user", "/admin", "/driver"})
public class RideController {

    @Autowired
    private RideRequestService rideRequestService;

    //book a new ride for customer
    @RequestMapping(value = "/ride/book/new", method = RequestMethod.POST)
    public Map<String, Object> bookNewRideRequest(@RequestBody RideRequest rideRequest, @AuthenticationPrincipal PersistentUserDetails userDetails) {
        if (rideRequest.getMobileNo() == null) {
            rideRequest.setMobileNo(userDetails.getMobile());
        }
        if (rideRequest.getCustomerName() == null) {
            rideRequest.setCustomerName(userDetails.getFirstName().concat(" ").concat(userDetails.getLastName()));
        }
        Map<String, Object> responseMap = rideRequestService.openBookingRequest(rideRequest, userDetails.getUserId());
        return responseMap;
    }

    //cancel the already booked or opened ride for customer
    @RequestMapping(value = "/ride/book/cancel", method = RequestMethod.POST)
    public void cancelBookingRequestStatus(@RequestParam(required = false) String bookingRequestId, @RequestParam(required = false) String status,
                                           @AuthenticationPrincipal PersistentUserDetails userDetails) {
        rideRequestService.updateRideBookingStatus(bookingRequestId, Constants.CANCEL, userDetails.getUserId());
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

    //otp-verification and ride start
    @RequestMapping(value = "/ride/book/otp-verfication", method = RequestMethod.POST)
    public RideRequest verifyOtpAndStartRide(@RequestParam Map<String, String> allRequestParam) {
        return rideRequestService.verifyOtpAndStartRide(allRequestParam.get("otp"), allRequestParam.get("bookingRequestId"));
    }

    //modify payment status before ending the trip: paymentStatus=PAID/UNPAID
    @RequestMapping(value = "/ride/book/payment", method = RequestMethod.POST)
    public Boolean updatePaymentStatus(@RequestParam Map<String, String> allRequestParam) {
        return rideRequestService.updatePaymentStatusOfRideBookingRequest(allRequestParam.get("bookingRequestId"), allRequestParam.get("paymentStatus"));
    }

    //close the booking request after trip end for driver
    @RequestMapping(value = "/ride/book/close", method = RequestMethod.POST)
    public void closeBookingRequestStatus(@RequestParam String bookingRequestId, @RequestParam(required = false) String status,
                                          @AuthenticationPrincipal PersistentUserDetails userDetails) {
        rideRequestService.updateRideBookingStatus(bookingRequestId, Constants.CLOSE, userDetails.getUserId());
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
    public String getDriverDutyStatus(@AuthenticationPrincipal PersistentUserDetails userDetails) {
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

    //total earning
    @RequestMapping(value = "/rides/earning", method = RequestMethod.GET)
    public Map<String, Object> getTotalEarning(@AuthenticationPrincipal PersistentUserDetails userDetails, @RequestParam(required = false) String userId) {
        if (ADMIN.equalsIgnoreCase(userDetails.getRoleName())) {
            return rideRequestService.getTotalEarningByDriver(Long.valueOf(userId));
        } else if (DRIVER.equalsIgnoreCase(userDetails.getRoleName())) {
            return rideRequestService.getTotalEarningByDriver(userDetails.getUserId());
        } else {
            throw new ApiException(MessageConstants.NOT_AUTHORIZED);
        }
    }

    //firebase token
    @RequestMapping(value = "/firebase/token", method = RequestMethod.POST)
    public void createFirebaseToken(@AuthenticationPrincipal PersistentUserDetails userDetails, @RequestBody String token) {
        rideRequestService.createOrUpdateFirebaseToken(userDetails.getUserId(), token);
    }

    @RequestMapping(value = "/firebase/token", method = RequestMethod.GET)
    public String getFirebaseToken(@AuthenticationPrincipal PersistentUserDetails userDetails) {
        return rideRequestService.getFirebaseTokenById(userDetails.getUserId());
    }

}
