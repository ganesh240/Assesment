package com.dev.assessment.resource;

import com.dev.assessment.model.TripCost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TollCalculatorResourceTest {
    @LocalServerPort
    private int port;
    private static final String host = "http://localhost:";
    private static final String path = "/api/toll-calculator/Keele Street/Bathurst Street";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testTripCalculation() throws MalformedURLException {
        ResponseEntity<TripCost> response = testRestTemplate.getForEntity(new URL(host+port+path).toString(), TripCost.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(5.715,response.getBody().getDistance());
    }
}
