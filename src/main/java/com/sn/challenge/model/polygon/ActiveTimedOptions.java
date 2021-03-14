package com.sn.challenge.model.polygon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ActiveTimedOptions {
    private Double min;
    private Double max;
    @JsonProperty("idle_time")
    private Double idleTime;
    private Double revenue;
    @JsonProperty("walking_range1")
    private Double walkingRange1;
    @JsonProperty("walking_range2")
    private Double walkingRange2;
}
