package com.dev.assessment.service;

import com.dev.assessment.model.InterchangeData;
import com.dev.assessment.model.TripCost;
import com.dev.assessment.util.SerDeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class TollCalculatorService {
    private InterchangeData interchangeData;
    private Map<String, Map.Entry<String, InterchangeData.Location>> locationMapByName;
    private static InterchangeData.Location defaultLocation;
    private static final float DEFAULT_KM_RATE = 0.25f;

    public TollCalculatorService(){
        String interChangeDataFileName = "interchange-data.json";
        defaultLocation = new InterchangeData.Location();
        defaultLocation.setRoutes(new ArrayList<>());
        try {
            interchangeData = SerDeUtil.readAsObject(interChangeDataFileName,InterchangeData.class);
        } catch (IOException e) {
            log.error("File not found : "+interChangeDataFileName);
        }
        locationMapByName = interchangeData.getLocations()
                .entrySet().stream().collect(Collectors.toMap(e -> e.getValue().getName(), e ->e));
    }

    public TripCost calculateTripCost(String source, String destination){
        log.info("Calculating trip cost between {} and {}",source,destination);
        int sourceId = Integer.parseInt(locationMapByName.get(source).getKey());
        int dstId = Integer.parseInt(locationMapByName.get(destination).getKey());
        log.info("Source: {} & Destnation:{}",sourceId,dstId);
        double distance =calculateDistance(sourceId,dstId);
        TripCost tripCost = TripCost.builder().distance(distance)
                .tripCost(new BigDecimal(distance).multiply(new BigDecimal(DEFAULT_KM_RATE)).setScale(2,BigDecimal.ROUND_HALF_EVEN))
                .build();
        try {
            log.info(SerDeUtil.writeAsString(tripCost));
        } catch (JsonProcessingException e) {
           log.error("Serialization error");
        }
        return tripCost;
    }

    public TripCost calculateTripCost(String vehicleType, String source, String destination){
        log.info("Calculating trip cost between {} and {} for {} vehicle type",source,destination,vehicleType);
        return null;
    }

    private double calculateDistance(int sourceId, int destinationId){
        int start = Math.min(sourceId,destinationId);
        int end = Math.max(sourceId,destinationId);
        return IntStream.range(start,end).parallel()
                .mapToDouble(i->interchangeData.getLocations().getOrDefault(Integer.toString(i),defaultLocation)
                        .getRoutes().stream().filter(r->r.getToId()>i)
                        .map(r->r.getDistance()).findFirst().orElse(0.0))
                .sum();
    }
}
