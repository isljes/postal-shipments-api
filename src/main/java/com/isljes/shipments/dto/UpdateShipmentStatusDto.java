package com.isljes.shipments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isljes.shipments.model.Event;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UpdateShipmentStatusDto(
        @JsonProperty("officeId")
        Long officeId,
        @JsonProperty("event")
        Event event) implements Serializable {
}
