package com.isljes.shipments.service;

import com.isljes.shipments.dto.ShipmentHistoryDto;
import com.isljes.shipments.model.Event;
import com.isljes.shipments.model.PostOffice;
import com.isljes.shipments.model.Shipment;
import com.isljes.shipments.model.ShipmentHistory;

import java.util.List;

public interface ShipmentHistoryService {
    ShipmentHistory createRecord(PostOffice office, Shipment shipment, Event event);

    List<ShipmentHistoryDto> getHistoryById(Long id);
}
