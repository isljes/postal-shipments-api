package com.isljes.shipments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isljes.shipments.model.Shipment;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record RegisterShipmentDto(
        @JsonProperty("recipientName")
        String recipientName,
        @JsonProperty("recipientIndex")
        String recipientIndex,
        @JsonProperty("recipientAddress")
        String recipientAddress,
        @JsonProperty("type")
        Shipment.ShipmentType type,
        @JsonProperty("fromOfficeId")
        Long fromOfficeId) implements Serializable {
}
