package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RideRequest extends Fare {
    private Long bookingRequestId;
    private Long customerId;
    private String mobileNo;
    private Location pickupLocation;
    private Location dropLocation;
    private String paymentMode;
    private String paymentStatus;
    private String bookingStatus;
    private String otp;
    private Long driverId;
    private Date date;

    public RideRequest(Long bookingRequestId, Long driverId, String bookingStatus) {
        this.bookingRequestId = bookingRequestId;
        this.driverId = driverId;
        this.bookingStatus = bookingStatus;
    }

    public String getPickupLocationPoints() {
        return this.getPickupLocation().getLatitude().concat(",").concat(this.getPickupLocation().getLongitude());
    }

    public String getDropLocationPoints() {
        return this.getDropLocation().getLatitude().concat(",").concat(this.getDropLocation().getLongitude());
    }

    public void setPickupLocationPoints(String pickupLocationPoints) {
        String[] split = pickupLocationPoints.split(",");
        this.pickupLocation = new Location(this.customerId, split[0], split[1]);
    }

    public void setDropLocationPoints(String dropLocationPoints) {
        String[] split = dropLocationPoints.split(",");
        this.dropLocation = new Location(this.customerId, split[0], split[1]);
    }

}