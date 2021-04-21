package com.dev.assessment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class TripCost {
    private String direction;
    private Double kmRate;
    private Double distance;
    private BigDecimal tripCost;
}
