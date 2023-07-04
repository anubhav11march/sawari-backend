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

    private static final Logger log = LoggerFactory.getLogger(FareCalculationServiceImpl.class);

    @Override
    public Fare getEstimatedFareByPassengerAndDistance(Integer passenger, Double distance, Map<String, String> fareProperties) {
        double distanceThreshold;
        double maxDistance;
        double basePrice;
        List<String> distanceRanges;
        double distanceFactor;
        double distanceSurcharge;
        double discountFareThreshold;
        if (fareProperties == null || fareProperties.isEmpty()) {
            distanceThreshold = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_THRESHOLD","6.1"));
            maxDistance = Double.parseDouble(DBProperties.properties.getProperty("MAX_DISTANCE", "7.0"));
            basePrice = Double.parseDouble(DBProperties.properties.getProperty("BASE_PRICE", "10"));
            distanceRanges = DBProperties.splitString(DBProperties.properties.getProperty("DISTANCE_RANGE", "0-2,2.1-3.5,3.6-4.5,4.6-6.0,6.1-7"));
            distanceFactor = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_FACTOR", "5"));
            distanceSurcharge = Double.parseDouble(DBProperties.properties.getProperty("DISTANCE_SURCHARGE", "15"));
            discountFareThreshold = Double.parseDouble(DBProperties.properties.getProperty("DISCOUNT_FARE_THRESHOLD", "100"));
        } else {
            distanceRanges = DBProperties.splitString(fareProperties.get("distanceRange"));
            distanceThreshold = Double.parseDouble(fareProperties.get("distanceThreshold"));
            distanceSurcharge = Double.parseDouble(fareProperties.get("distanceSurcharge"));
            maxDistance = Double.parseDouble(fareProperties.get("maxDistance"));
            basePrice = Double.parseDouble(fareProperties.get("basePrice"));
            distanceFactor = Double.parseDouble(fareProperties.get("distanceFactor"));
            discountFareThreshold = Double.parseDouble(fareProperties.get("discountFareThreshold"));
        }
        double fare;
        AtomicReference<Double> discount = new AtomicReference<>((double) 0);
        double distanceSurcharges = distance >= distanceThreshold ? distanceSurcharge : 0;
        if (distance <= maxDistance) {
            int minDistRange = findFloorDistanceFromDistanceRange(distance, distanceRanges);
            if (passenger == 1) {
                fare = ((basePrice + (distanceFactor * minDistRange) + distanceSurcharges) * 2);
            } else {
                fare = ((basePrice + (distanceFactor * minDistRange) + distanceSurcharges) * passenger);
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

    private int findFloorDistanceFromDistanceRange(double distance, List<String> distanceRanges) {
        AtomicReference<Double> minDistance = new AtomicReference<>((double) 0);
        distanceRanges.forEach(d -> {
            String[] split = d.split("-");
            double lowerDistance = Double.parseDouble(split[0]);
            double upperDistance = Double.parseDouble(split[1]);
            if (distance >= lowerDistance && distance <= upperDistance) {
                minDistance.set(lowerDistance == 0 ? 1 : lowerDistance);
            }
        });

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
