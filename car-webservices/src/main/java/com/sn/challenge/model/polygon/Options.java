package com.sn.challenge.model.polygon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Options {
    private Boolean active;
    @JsonProperty("is_excluded")
    private Boolean excluded;
    private Double area;
}
