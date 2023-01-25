package com.tzs.marshall.service;

import com.tzs.marshall.bean.Fare;

public interface FareCalculationService {

    Fare getEstimatedFareByPassengerAndDistance(Integer passenger, Double distance);
}
