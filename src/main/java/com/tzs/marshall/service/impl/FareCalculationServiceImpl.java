package com.tzs.marshall.service.impl;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.Fare;
import com.tzs.marshall.service.FareCalculationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FareCalculationServiceImpl implements FareCalculationService {

    /**
     * SINGLE_PASSENGER:
     * 0.0-2.0km = 30rs
     * 2.1-3.5km = 40rs
     * 3.6-4.5km = 50rs
     * 4.6-5.5km = 60rs
     * 5.6-7.0km = 100rs
     *
     * MULTI_PASSENGERS:
     * 0.0-2.0km = 15*Passengers rs
     * 2.1-3.5km = 20*Passengers rs
     * 3.5-4.5km = 25*Passengers rs
     * 4.6-5.5km = 30*Passengers rs
     * 5.6-7.0km = 50*Passengers rs
     *
     * basePrice = 10
     * distFactor = 5
     * fair = basePrice + (distFactor * minDistRange) eg: ((10 + (5 * 4) + 0) * 2) = 60
     * **/

    private static final List<String> DISTANCE_RANGE = DBProperties.splitString(DBProperties.properties.getProperty("DISTANCE_RANGE", "0-2,2.1-3.5,3.6-4.5,4.6-5.5,5.6-7"));
    private static final double DISTANCE_THRESHOLD = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_THRESHOLD","5.6"));
    private static final double MAX_DISTANCE = Double.parseDouble(DBProperties.properties.getProperty("MAX_DISTANCE", "7.0"));

    private static final double BASE_PRICE = Double.parseDouble(DBProperties.properties.getProperty("BASE_PRICE", "10"));
    private static final double DISTANCE_FACTOR = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_FACTOR", "5"));
    private static final double DISTANCE_SURCHARGE = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_SURCHARGE", "15"));

    @Override
    public Fare getEstimatedFareByPassengerAndDistance(Integer passenger, Double distance) {
        double fare;
        double distanceSurcharge = distance >= DISTANCE_THRESHOLD ? DISTANCE_SURCHARGE : 0;
        if (distance <= MAX_DISTANCE) {
            int minDistRange = findLowerDistanceFromDistanceRange(distance);
            if (passenger==1) {
                fare = ((BASE_PRICE + (DISTANCE_FACTOR * minDistRange) + distanceSurcharge) * 2);
            } else {
                fare = ((BASE_PRICE + (DISTANCE_FACTOR * minDistRange) + distanceSurcharge) * passenger);
            }
        } else {
            throw new RuntimeException("Destination distance exceeds the service area.");
        }
        return new Fare(passenger, fare, distance, "INR");
    }

    private int findLowerDistanceFromDistanceRange(double distance) {
        AtomicReference<Double> minDistance = new AtomicReference<>((double) 0);
        DISTANCE_RANGE.forEach(d -> {
            String[] split = d.split("-");
            double lowerDistance = Double.parseDouble(split[0]);
            double upperDistance = Double.parseDouble(split[1]);
            if (distance >= lowerDistance && distance <= upperDistance) {
                minDistance.set(lowerDistance==0?1:lowerDistance);
            }
        });
        return minDistance.get().intValue();
    }
}
