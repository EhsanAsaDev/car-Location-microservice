package com.sn.challenge.model.polygon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GeoFeature {
    private String name;
    private GeometryPoint geometry;
}
