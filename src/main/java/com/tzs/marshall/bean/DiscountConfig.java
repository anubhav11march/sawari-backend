package com.tzs.marshall.bean;

import java.util.Map;

public class DiscountConfig {
    private String passenger;
    private Map<String, Double> minDistance;

    public DiscountConfig(String passenger, Map<String, Double> minDistance) {
        this.passenger = passenger;
        this.minDistance = minDistance;
    }

    public DiscountConfig() {
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public Map<String, Double> getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Map<String, Double> minDistance) {
        this.minDistance = minDistance;
    }

    @Override
    public String toString() {
        return "DiscountConfig{" +
                "passenger='" + passenger + '\'' +
                ", minDistance=" + minDistance +
                '}';
    }
}
