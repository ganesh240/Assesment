package com.dev.assessment.resource;

import com.dev.assessment.model.TripCost;
import com.dev.assessment.service.TollCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TollCalculatorResource {

    @Autowired
    private TollCalculatorService tollCalculatorService;

    @GetMapping(path = "/{source}/{destination}")
    public ResponseEntity<TripCost> calculateTripCost(@PathVariable("source") String source,
                                                      @PathVariable("destination") String destination){
        TripCost tripCost = tollCalculatorService.calculateTripCost(source, destination);
        return new ResponseEntity<>(tripCost, HttpStatus.OK);
    }
}
