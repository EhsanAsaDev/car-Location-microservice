package com.sn.challenge.model.polygon;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoFeature {
    private String name;
    private GeometryPoint geometry;
}
