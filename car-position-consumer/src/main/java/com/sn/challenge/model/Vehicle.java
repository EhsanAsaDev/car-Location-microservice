package com.sn.challenge.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vehicle {
    private Long id;
    @Id
    private String vin;
    private String numberPlate;
    private Position position;
    private Double fuel;
    private String model;
    private String polygonId;
    private Instant lastModified;

}   