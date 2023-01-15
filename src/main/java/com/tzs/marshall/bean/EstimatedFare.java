package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimatedFare {
    private Integer passengers;
    private Double fare;
    private Double distance;
    private String currency;

    public void setDistance(String distance) {
        this.distance = Double.parseDouble(distance.substring(0, distance.indexOf(" km")));
    }
}
