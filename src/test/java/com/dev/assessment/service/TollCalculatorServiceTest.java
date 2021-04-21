package com.dev.assessment.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = TollCalculatorService.class)
public class TollCalculatorServiceTest {
    @InjectMocks
    private TollCalculatorService tollCalculatorService = new TollCalculatorService();

    @Test
    public void test(){
        assertEquals(5.715,tollCalculatorService.calculateTripCost("Keele Street","Bathurst Street").getDistance());
    }
}
