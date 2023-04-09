package com.tzs.marshall.service;

import com.tzs.marshall.bean.DiscountConfig;
import com.tzs.marshall.bean.Fare;

import java.util.Map;

public interface FareCalculationService {

    Fare getEstimatedFareByPassengerAndDistance(Integer passenger, Double distance);

    Fare getEstimatedFareForPreview(Map<String, String> priceProperties);

    DiscountConfig[] getDiscountConfig();

    void updateDiscountConfig(DiscountConfig[] discountConfig);
}
