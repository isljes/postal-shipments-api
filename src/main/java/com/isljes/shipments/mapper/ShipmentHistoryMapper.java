package com.isljes.shipments.mapper;

import com.isljes.shipments.dto.ShipmentHistoryDto;
import com.isljes.shipments.model.ShipmentHistory;
import org.springframework.stereotype.Component;

@Component
public class ShipmentHistoryMapper {
    public ShipmentHistoryDto toRegisterShipmentDto(ShipmentHistory entity) {
        return ShipmentHistoryDto.builder()
                .id(entity.getId())
                .postOffice(entity.getPostOffice())
                .time(entity.getTime())
                .event(entity.getEvent())
                .build();

    }
}
