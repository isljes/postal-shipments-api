package com.isljes.shipments.service;

import com.isljes.shipments.dto.RegisterShipmentDto;
import com.isljes.shipments.dto.ShipmentDto;
import com.isljes.shipments.dto.UpdateShipmentStatusDto;

import java.util.List;

public interface ShipmentService {

    ShipmentDto registerShipment(RegisterShipmentDto shipment);

    ShipmentDto updateStatus(Long shipmentId, UpdateShipmentStatusDto statusDto);

    ShipmentDto findById(Long shipmentId);

    List<ShipmentDto> findAll();
    void deleteById(Long id);
}
