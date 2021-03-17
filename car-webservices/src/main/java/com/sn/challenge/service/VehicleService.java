package com.sn.challenge.service;

import com.sn.challenge.model.Vehicle;
import com.sn.challenge.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Ehsan Sh
 */

@Service
public class VehicleService {
    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    public Optional<Vehicle> getVehicleByVin(String vin) {
        return vehicleRepository.findById(vin);
    }

    public List<Vehicle> getVehiclesByPolygonId(String polygonId) {
        return vehicleRepository.findByPolygonId(polygonId);
    }
}
