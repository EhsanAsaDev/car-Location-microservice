package com.sn.challenge.service;

import com.sn.challenge.model.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @author Ehsan Sh
 */

@Service
public class VehiclesService {
    private RestTemplate restTemplate;

    @Value("${vehicles.url}")
    public String vehicleUrl;

    public VehiclesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Vehicle[] getVehicleInfo(){

        ResponseEntity<Vehicle[]> responseEntity = restTemplate.getForEntity(
                vehicleUrl, Vehicle[].class);

        return responseEntity.getBody();
    }


}
