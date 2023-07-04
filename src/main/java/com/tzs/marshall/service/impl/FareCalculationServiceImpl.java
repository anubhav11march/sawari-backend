package com.tzs.marshall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.DiscountConfig;
import com.tzs.marshall.bean.Fare;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.service.FareCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
            int minDistRange = findFloorDistanceFromDistanceRange(distance, null);
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

    @Override
    public Fare getEstimatedFareForPreview(Map<String, String> priceProperties) {
        double fare;
        double distance = Double.parseDouble(priceProperties.get("distance"));
        int passenger = Integer.parseInt(priceProperties.get("passengers"));
        double distanceThreshold = Double.parseDouble(priceProperties.get("distanceThreshold"));
        double distanceSurcharges = Double.parseDouble(priceProperties.get("distanceSurcharge"));
        List<String> distanceRange = DBProperties.splitString(priceProperties.get("distanceRange"));
        double maxDistance = Double.parseDouble(priceProperties.get("maxDistance"));
        double basePrice = Double.parseDouble(priceProperties.get("basePrice"));
        double distanceFactor = Double.parseDouble(priceProperties.get("distanceFactor"));
        double discountFareThreshold = Double.parseDouble(priceProperties.get("discountFareThreshold"));

        AtomicReference<Double> discount = new AtomicReference<>((double) 0);
        double distanceSurcharge = distance >= distanceThreshold ? distanceSurcharges : 0;
        if (distance <= maxDistance) {
            int minDistRange = findFloorDistanceFromDistanceRange(distance, distanceRange);
            if (passenger == 1) {
                fare = ((basePrice + (distanceFactor * minDistRange) + distanceSurcharge) * 2);
            } else {
                fare = ((basePrice + (distanceFactor * minDistRange) + distanceSurcharge) * passenger);
            }
            if (fare >= discountFareThreshold) {
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

    private int findFloorDistanceFromDistanceRange(double distance, List<String> distanceRange) {
        AtomicReference<Double> minDistance = new AtomicReference<>((double) 0);
        if (distanceRange == null || distanceRange.isEmpty()) {
            DISTANCE_RANGE.forEach(d -> {
                String[] split = d.split("-");
                double lowerDistance = Double.parseDouble(split[0]);
                double upperDistance = Double.parseDouble(split[1]);
                if (distance >= lowerDistance && distance <= upperDistance) {
                    minDistance.set(lowerDistance==0?1:lowerDistance);
                }
            });
        } else {
            distanceRange.forEach(d -> {
                String[] split = d.split("-");
                double lowerDistance = Double.parseDouble(split[0]);
                double upperDistance = Double.parseDouble(split[1]);
                if (distance >= lowerDistance && distance <= upperDistance) {
                    minDistance.set(lowerDistance==0?1:lowerDistance);
                }
            });
        }
        return minDistance.get().intValue();
    }

    @Override
    public DiscountConfig[] getDiscountConfig() {
        try {
            Gson gson = new Gson();
            com.google.gson.stream.JsonReader jsonReader = new com.google.gson
                    .stream
                    .JsonReader(new FileReader(Constants.BASE_PATH + Constants.CONFIG_FILE + Constants.DISCOUNT_CONFIG_FILE));
            DiscountConfig[] discountMap = gson.fromJson(jsonReader, DiscountConfig[].class);
            return discountMap;
        } catch (Exception e) {
            log.error("No discount config file found");
            throw new RuntimeException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public void updateDiscountConfig(DiscountConfig[] discountConfig) {
        try (FileWriter fileWriter = new FileWriter(Constants.BASE_PATH + Constants.CONFIG_FILE + Constants.DISCOUNT_CONFIG_FILE)){
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String discountConfigJson = gson.toJson(discountConfig);
            fileWriter.write(discountConfigJson);
        } catch (Exception e) {
            log.error("No discount config file found");
            throw new RuntimeException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
