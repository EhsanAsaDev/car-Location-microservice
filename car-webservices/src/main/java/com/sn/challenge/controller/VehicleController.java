package com.sn.challenge.controller;

import com.sn.challenge.model.Vehicle;
import com.sn.challenge.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ehsan Sh
 */

@RestController
@Slf4j
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping(value = "/api/v1//vehicles/")
    public ResponseEntity<Vehicle> getVehicleByVin(@RequestParam("vin") String vin) {

        log.info("REST request to /api/v1//vehicles?vin={}", vin);
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicleByVin(vin));
        log.info("/api/v1//weather?city={} : {}", vin ,responseEntity);
        return responseEntity;
    }

    @GetMapping(value = "/api/v1//vehiclesByPolygon/")
    public ResponseEntity<List<Vehicle>> getVehiclesByPolygonId(@RequestParam("polygonId") String polygonId) {

        log.info("REST request to /api/v1//vehicles?polygonId={}", polygonId);
        ResponseEntity<List<Vehicle>> responseEntity = ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehiclesByPolygonId(polygonId));
        log.info("/api/v1//weather?city={} : {}", polygonId ,responseEntity);
        return responseEntity;
    }
}   
