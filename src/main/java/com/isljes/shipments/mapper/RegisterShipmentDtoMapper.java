package com.isljes.shipments.mapper;

import com.isljes.shipments.dto.RegisterShipmentDto;
import com.isljes.shipments.model.Shipment;
import org.springframework.stereotype.Component;

@Component
public class RegisterShipmentDtoMapper {
    public Shipment toEntity(RegisterShipmentDto dto) {
        return Shipment.builder()
                .recipientAddress(dto.recipientAddress())
                .recipientIndex(dto.recipientIndex())
                .recipientName(dto.recipientName())
                .type(dto.type()).build();
    }
}
