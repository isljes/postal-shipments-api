package com.isljes.shipments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isljes.shipments.model.Event;
import com.isljes.shipments.model.PostOffice;
import lombok.Builder;

import java.io.Serializable;
import java.sql.Timestamp;

@Builder
public record ShipmentHistoryDto(
        @JsonProperty("shipmentsHistoryId")
        Long id,
        @JsonProperty("postOffice")
        PostOffice postOffice,
        @JsonProperty("event")
        Event event,
        @JsonProperty("time")
        Timestamp time) implements Serializable {
}
