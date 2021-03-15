package com.sn.challenge.model.polygon;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Geometry {
        private String type;
        private List<List<List<Double>>> coordinates = null;

}
