package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.Fare;
import com.tzs.marshall.service.FareCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/", "http://admin.sawaricabs.in/", "http://179.61.188.172:5000/"}, allowCredentials = "true")
@RequestMapping({"/user", "/admin", "driver"})
public class FareCalculationController {

    @Autowired
    private FareCalculationService fareCalculationService;

    private static final Logger log = LoggerFactory.getLogger(FareCalculationController.class);


    @RequestMapping(value = "/fare", method = RequestMethod.POST)
    public Fare getEstimatedFare(@RequestBody Fare fare) {
        Double distance = fare.getDistance();
        Integer passenger = fare.getPassengers();
        return fareCalculationService.getEstimatedFareByPassengerAndDistance(passenger, distance);

    }
}
