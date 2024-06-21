package com.isljes.shipments.mapper;

import com.isljes.shipments.dto.ShipmentDto;
import com.isljes.shipments.model.Shipment;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {
    public ShipmentDto toDto(Shipment entity) {
        return ShipmentDto.builder()
                .id(entity.getId())
                .recipientName(entity.getRecipientName())
                .recipientIndex(entity.getRecipientIndex())
                .recipientAddress(entity.getRecipientAddress())
                .type(entity.getType())
                .status(entity.getStatus()).build();
    }

    public Shipment toEntity(ShipmentDto dto) {
        return Shipment.builder()
                .id(dto.id())
                .recipientName(dto.recipientName())
                .recipientIndex(dto.recipientIndex())
                .recipientAddress(dto.recipientAddress())
                .type(dto.type())
                .status(dto.status()).build();
    }
}
