package com.dev.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class InterchangeData {
    private Map<String,Location> locations;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location{
        private String name;
        private double lat;
        private double lng;
        private List<Route> routes;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Route{
        private int toId;
        private double distance;
    }
}
