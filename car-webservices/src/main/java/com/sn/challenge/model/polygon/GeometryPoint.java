package com.sn.challenge.model.polygon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeometryPoint {
    private String type;
    private List<Double> coordinates = null;
}
