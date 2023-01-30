package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Fare {
    private Integer passengers;
    private Double fare;
    private Double discount;
    private Double distance;
    private String currency;

    public void setDistance(String distance) {
        this.distance = Double.parseDouble(distance.substring(0, distance.indexOf(" km")));
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setFare(String fare) {
        this.fare = Double.parseDouble(fare);
    }

    public void setFare (Double fare) {
        this.fare = fare;
    }
}
