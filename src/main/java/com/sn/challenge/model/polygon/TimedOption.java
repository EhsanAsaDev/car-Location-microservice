package com.sn.challenge.model.polygon;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TimedOption {
    private String key;
    private List<List<Double>> changesOverTime = null;
}
