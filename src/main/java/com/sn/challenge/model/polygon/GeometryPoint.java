package com.sn.challenge.model.polygon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GeometryPoint {
    private String type;
    private List<Double> coordinates = null;
}
