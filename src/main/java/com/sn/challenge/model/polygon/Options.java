package com.sn.challenge.model.polygon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Options {
    private Boolean active;
    @JsonProperty("is_excluded")
    private Boolean excluded;
    private Double area;
}
