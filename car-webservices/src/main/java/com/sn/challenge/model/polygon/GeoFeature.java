package com.sn.challenge.model.polygon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoFeature {
    private String name;
    private GeometryPoint geometry;
}
