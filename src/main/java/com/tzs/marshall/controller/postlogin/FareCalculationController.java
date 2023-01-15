package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.EstimatedFare;
import com.tzs.marshall.service.FareCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/user", "/admin", "driver"})
public class FareCalculationController {

    @Autowired
    private FareCalculationService fareCalculationService;

    private static final Logger log = LoggerFactory.getLogger(FareCalculationController.class);


    @RequestMapping(value = "/fare", method = RequestMethod.GET)
    public EstimatedFare getEstimatedFare(@RequestBody EstimatedFare estimatedFare) {
        Double distnace = estimatedFare.getDistance();
        Integer passenger = estimatedFare.getPassengers();
        return fareCalculationService.getEstimatedFareByPassengerAndDistance(passenger, distnace);

    }
}
