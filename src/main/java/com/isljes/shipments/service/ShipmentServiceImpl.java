package com.isljes.shipments.service;

import com.isljes.shipments.dto.RegisterShipmentDto;
import com.isljes.shipments.dto.ShipmentDto;
import com.isljes.shipments.dto.UpdateShipmentStatusDto;
import com.isljes.shipments.exception.NotFoundException;
import com.isljes.shipments.mapper.RegisterShipmentDtoMapper;
import com.isljes.shipments.mapper.ShipmentMapper;
import com.isljes.shipments.model.Event;
import com.isljes.shipments.model.PostOffice;
import com.isljes.shipments.model.Shipment;
import com.isljes.shipments.model.ShipmentHistory;
import com.isljes.shipments.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final RegisterShipmentDtoMapper registerShipmentDtoMapper;
    private final ShipmentHistoryService historyService;
    private final PostOfficeService officeService;
    private final ShipmentMapper shipmentMapper;

    @Override
    public ShipmentDto registerShipment(RegisterShipmentDto shipmentDto) {
        Shipment shipment = registerShipmentDtoMapper.toEntity(shipmentDto);


        Shipment savedShipment = shipmentRepository.save(shipment);

        PostOffice office = officeService.findById(shipmentDto.fromOfficeId());
        ShipmentHistory savedRecord = historyService.createRecord(office, shipment, Event.WAITING_SHIPMENT);
        savedShipment.setStatus(savedRecord);
        Shipment registered = shipmentRepository.save(savedShipment);
        return shipmentMapper.toDto(registered);
    }

    @Override
    public ShipmentDto updateStatus(Long shipmentId, UpdateShipmentStatusDto statusDto) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new NotFoundException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shipment with id= %s doesn't exist", shipmentId)
                ));
        PostOffice office = officeService.findById(statusDto.officeId());
        ShipmentHistory record = historyService.createRecord(office, shipment, statusDto.event());
        shipment.setStatus(record);
        Shipment updatedEntity = shipmentRepository.save(shipment);
        return shipmentMapper.toDto(updatedEntity);
    }

    @Override
    public ShipmentDto findById(Long shipmentId) {
        Shipment entity = shipmentRepository.findById(shipmentId).orElseThrow(() ->
                new NotFoundException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shipment with id= %s doesn't exist", shipmentId)));
        return shipmentMapper.toDto(entity);
    }

    @Override
    public List<ShipmentDto> findAll() {
        return shipmentRepository.findAll().stream()
                .map(shipmentMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        shipmentRepository.deleteById(id);
    }
}
