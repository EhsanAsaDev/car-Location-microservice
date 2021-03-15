package com.sn.challenge.scheduler;

import com.sn.challenge.controller.VehicleEventController;
import com.sn.challenge.model.Vehicle;
import com.sn.challenge.service.VehiclesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
@Slf4j
@Component
public class FetchVehiclesPosition {
    private final VehiclesService vehiclesService;
    private final VehicleEventController vehicleEventController;


    @Scheduled(fixedDelay =30000 )
    public void fetchCarData(){
        log.info("Fetching Vehicles Data .......");
        Vehicle[] vehicles = vehiclesService.getVehicleInfo();
        for (Vehicle vehicle : vehicles){
            log.info("Vehicle with vim:{} sent as event",vehicle.getVin());
            vehicleEventController.sentVehicleEvents(vehicle);
        }
    }



}   
