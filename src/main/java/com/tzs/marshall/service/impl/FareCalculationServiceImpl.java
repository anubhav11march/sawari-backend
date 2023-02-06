package com.tzs.marshall.service.impl;

import com.google.gson.Gson;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.DiscountConfig;
import com.tzs.marshall.bean.Fare;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.service.FareCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FareCalculationServiceImpl implements FareCalculationService {

    private static Logger log = LoggerFactory.getLogger(FareCalculationServiceImpl.class);
    private static final List<String> DISTANCE_RANGE = DBProperties.splitString(DBProperties.properties.getProperty("DISTANCE_RANGE", "0-2,2.1-3.5,3.6-4.5,4.6-6.0,6.1-7"));
    private static final Double DISTANCE_THRESHOLD = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_THRESHOLD","6.1"));
    private static final Double MAX_DISTANCE = Double.parseDouble(DBProperties.properties.getProperty("MAX_DISTANCE", "7.0"));

    private static final Double BASE_PRICE = Double.parseDouble(DBProperties.properties.getProperty("BASE_PRICE", "10"));
    private static final Double DISTANCE_FACTOR = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_FACTOR", "5"));
    private static final Double DISTANCE_SURCHARGE = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_SURCHARGE", "15"));

    private static final double DISCOUNT_FARE_THRESHOLD = Double.parseDouble(DBProperties.properties.getProperty("DISCOUNT_FARE_THRESHOLD", "100"));

    @Override
    public Fare getEstimatedFareByPassengerAndDistance(Integer passenger, Double distance) {
        double fare;
        AtomicReference<Double> discount = new AtomicReference<>((double) 0);
        double distanceSurcharge = distance >= DISTANCE_THRESHOLD ? DISTANCE_SURCHARGE : 0;
        if (distance <= MAX_DISTANCE) {
            int minDistRange = findFloorDistanceFromDistanceRange(distance);
            if (passenger == 1) {
                fare = ((BASE_PRICE + (DISTANCE_FACTOR * minDistRange) + distanceSurcharge) * 2);
            } else {
                fare = ((BASE_PRICE + (DISTANCE_FACTOR * minDistRange) + distanceSurcharge) * passenger);
            }
            if (fare >= DISCOUNT_FARE_THRESHOLD) {
                DiscountConfig[] discountConfig = getDiscountConfig();
                Arrays.stream(discountConfig)
                        .filter(p -> p.getPassenger().equalsIgnoreCase(String.valueOf(passenger)))
                        .forEach(d -> discount.set(d.getMinDistance().get(String.valueOf(minDistRange))));
                fare = (fare - ((discount.get() * fare) / 100));
            }
        } else {
            throw new RuntimeException("Destination distance exceeds the service area.");
        }
        return new Fare(passenger, fare, discount.get(), distance, "INR");
    }

    private int findFloorDistanceFromDistanceRange(double distance) {
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

    public DiscountConfig[] getDiscountConfig() {
        try {
            Gson gson = new Gson();
            com.google.gson.stream.JsonReader jsonReader = new com.google.gson
                    .stream
                    .JsonReader(new FileReader(Constants.BASE_PATH + Constants.CONFIG_FILE + Constants.DISCOUNT_CONFIG_FILE));
            DiscountConfig[] discountMap = gson.fromJson(jsonReader, DiscountConfig[].class);
            System.out.println(Arrays.toString(discountMap));
            return discountMap;
        } catch (Exception e) {
            log.error("No discount config file found");
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
