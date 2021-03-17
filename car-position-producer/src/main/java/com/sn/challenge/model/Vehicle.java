package com.sn.challenge.model;

import lombok.*;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Vehicle {
    public Integer id;
    public int locationId;
    public String vin;
    public String numberPlate;
    public Position position;
    public double fuel;
    public String model;

}   
