package com.tzs.marshall.service;

import com.tzs.marshall.bean.EstimatedFare;

public interface FareCalculationService {

    EstimatedFare getEstimatedFareByPassengerAndDistance(Integer passenger, Double distance);
}
