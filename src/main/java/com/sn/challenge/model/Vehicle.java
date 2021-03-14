package com.sn.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vehicle {
    public int id;
    public int locationId;
    public String vin;
    public String numberPlate;
    public Position position;
    public double fuel;
    public String model;

}   
