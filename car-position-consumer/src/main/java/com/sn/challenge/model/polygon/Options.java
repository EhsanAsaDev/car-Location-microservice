package com.sn.challenge.model.polygon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Options {
    private Boolean active;
    @JsonProperty("is_excluded")
    private Boolean excluded;
    private Double area;
}
