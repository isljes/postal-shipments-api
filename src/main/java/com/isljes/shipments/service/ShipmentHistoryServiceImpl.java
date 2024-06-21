package com.isljes.shipments.service;

import com.isljes.shipments.dto.ShipmentHistoryDto;
import com.isljes.shipments.mapper.ShipmentHistoryMapper;
import com.isljes.shipments.model.Event;
import com.isljes.shipments.model.PostOffice;
import com.isljes.shipments.model.Shipment;
import com.isljes.shipments.model.ShipmentHistory;
import com.isljes.shipments.repository.ShipmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentHistoryServiceImpl implements ShipmentHistoryService {

    private final ShipmentHistoryRepository historyRepository;
    private final ShipmentHistoryMapper shipmentHistoryMapper;


    @Override
    public ShipmentHistory createRecord(PostOffice office, Shipment shipment, Event event) {
        ShipmentHistory record = ShipmentHistory.builder()
                .shipment(shipment)
                .postOffice(office)
                .event(event)
                .time(Timestamp.from(Instant.now()))
                .build();
        return historyRepository.save(record);
    }

    @Override
    public List<ShipmentHistoryDto> getHistoryById(Long id) {
        return historyRepository.findAllByShipmentIdOrderById(id).stream()
                .map(shipmentHistoryMapper::toRegisterShipmentDto).toList();
    }


}
