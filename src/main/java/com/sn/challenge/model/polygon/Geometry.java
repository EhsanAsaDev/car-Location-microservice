package com.sn.challenge.model.polygon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Geometry {
        private String type;
        private List<List<List<Double>>> coordinates = null;

}
