package com.sn.challenge.controller;

import com.sn.challenge.model.Position;
import com.sn.challenge.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ehsan Sh
 */

@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VehicleController vehicleController;


    @Test
    void getVehicleByVin() throws Exception {
        //given
        String vin= "1FT2F30528U1H0CA4";

        //given
        Vehicle vehicle = Vehicle.builder()
                .id(1l)
                .vin("1FT2F30528U1H0CA4")
                .numberPlate("HQ8916")
                .position(new Position(49.12,9.12))
                .fuel(0.7)
                .model("SMART_42_PASSION")
                .build();
        ResponseEntity responseEntity =  ResponseEntity.ok(vehicle);

        when(vehicleController.getVehicleByVin(eq(vin))).thenReturn(responseEntity);

        //expect
        mockMvc.perform(get("/api/v1//vehicles?vin=" + vin))
                .andExpect(status().isOk());

    }

    @Test
    void getVehicleByVin_BadRequest() throws Exception {
        //given
        String vin= "1FT2F30528U1H0CA4";


        //expect
        mockMvc.perform(get("/api/v1//vehicles?vini=" + vin))
                .andExpect(status().isBadRequest());

    }


    @Test
    void getVehiclesByPolygonId() throws Exception {
        //given
        String polygonId= "58a58bdb85979b5415f39f1d";

        //given
        Vehicle vehicle = Vehicle.builder()
                .id(1l)
                .vin("1FT2F30528U1H0CA4")
                .numberPlate("HQ8916")
                .position(new Position(49.12,9.12))
                .fuel(0.7)
                .model("SMART_42_PASSION")
                .build();
        List<Vehicle> vehicleList =List.of(vehicle);
        ResponseEntity responseEntity =  ResponseEntity.ok(vehicleList);

        when(vehicleController.getVehiclesByPolygonId(eq(polygonId))).thenReturn(responseEntity);

        //expect
        mockMvc.perform(get("/api/v1//vehiclesByPolygon?polygonId=" + polygonId))
                .andExpect(status().isOk());

    }

    @Test
    void getVehiclesByPolygonId_BadRequest() throws Exception {
        //given
        String pPolygonId= "58a58bdb85979b5415f39f1d";


        //expect
        mockMvc.perform(get("/api/v1//vehiclesByPolygon?polygonI=" + pPolygonId))
                .andExpect(status().isBadRequest());

    }



}   
