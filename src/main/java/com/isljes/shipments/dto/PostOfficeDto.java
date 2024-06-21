package com.isljes.shipments.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record PostOfficeDto(
        @JsonProperty("postOfficeId")
        Long id,
        @JsonProperty("name")
        String name,
        @JsonProperty("address")
        String address) implements Serializable {
}
