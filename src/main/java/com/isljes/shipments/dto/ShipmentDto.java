package com.isljes.shipments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isljes.shipments.model.Shipment;
import com.isljes.shipments.model.ShipmentHistory;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ShipmentDto(
        @JsonProperty("shipmentsId")
        Long id,
        @JsonProperty("recipientName")
        String recipientName,
        @JsonProperty("recipientIndex")
        String recipientIndex,
        @JsonProperty("recipientAddress")
        String recipientAddress,
        @JsonProperty("type")
        Shipment.ShipmentType type,
        @JsonProperty("status")
        ShipmentHistory status) implements Serializable {
}
