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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceImplTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private RegisterShipmentDtoMapper registerShipmentDtoMapper;

    @Mock
    private ShipmentHistoryService historyService;

    @Mock
    private PostOfficeService officeService;

    @Mock
    private ShipmentMapper shipmentMapper;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    @Test
    public void registerShipment_shouldSaveAndReturnShipmentDto() throws Exception{
        // Given
        var registerShipmentDto = mock(RegisterShipmentDto.class);
        var shipmentHistory = ShipmentHistory.builder().id(1L).build();
        var shipment = mock(Shipment.class);
        var office = mock(PostOffice.class);
        var shipmentDto = mock(ShipmentDto.class);

        when(registerShipmentDtoMapper.toEntity(registerShipmentDto)).thenReturn(shipment);
        when(shipmentRepository.save(shipment)).thenReturn(shipment);
        when(officeService.findById(registerShipmentDto.fromOfficeId())).thenReturn(office);
        when(historyService.createRecord(office, shipment, Event.WAITING_SHIPMENT)).thenReturn(shipmentHistory);
        when(shipmentRepository.save(shipment)).thenReturn(shipment);
        when(shipmentMapper.toDto(shipment)).thenReturn(shipmentDto);

        // When
        ShipmentDto actual = shipmentService.registerShipment(registerShipmentDto);

        // Then
        assertNotNull(actual);
        assertEquals(shipmentDto, actual);
        verify(registerShipmentDtoMapper, times(1)).toEntity(registerShipmentDto);
        verify(shipmentRepository, times(2)).save(any(Shipment.class));
        verify(officeService, times(1)).findById(registerShipmentDto.fromOfficeId());
        verify(historyService, times(1)).createRecord(office, shipment, Event.WAITING_SHIPMENT);
        verify(shipmentMapper, times(1)).toDto(shipment);
    }


    @Test
    public void updateStatus_shouldUpdateAndReturnShipmentDto() throws Exception {
        // Given
        var shipmentId = 1L;
        var statusDto = mock(UpdateShipmentStatusDto.class);
        var shipment = mock(Shipment.class);
        var office = mock(PostOffice.class);
        var shipmentHistory = mock(ShipmentHistory.class);
        var updatedShipment = mock(Shipment.class);
        var shipmentDto = mock(ShipmentDto.class);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));
        when(officeService.findById(statusDto.officeId())).thenReturn(office);
        when(historyService.createRecord(office, shipment, statusDto.event())).thenReturn(shipmentHistory);
        when(shipmentRepository.save(shipment)).thenReturn(updatedShipment);
        when(shipmentMapper.toDto(updatedShipment)).thenReturn(shipmentDto);

        // When
        ShipmentDto actual = shipmentService.updateStatus(shipmentId, statusDto);

        // Then
        assertNotNull(actual);
        assertEquals(shipmentDto, actual);
        verify(shipmentRepository, times(1)).findById(shipmentId);
        verify(officeService, times(1)).findById(statusDto.officeId());
        verify(historyService, times(1)).createRecord(office, shipment, statusDto.event());
        verify(shipmentRepository, times(1)).save(shipment);
        verify(shipmentMapper, times(1)).toDto(updatedShipment);
    }

    @Test
    public void updateStatus_shouldThrowNotFoundException_whenShipmentNotFound() throws Exception{
        // Given
        Long shipmentId = 1L;
        UpdateShipmentStatusDto statusDto = mock(UpdateShipmentStatusDto.class);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> shipmentService.updateStatus(shipmentId, statusDto));
        verify(shipmentRepository, times(1)).findById(shipmentId);
        verify(officeService, never()).findById(anyLong());
        verify(historyService, never()).createRecord(any(), any(), any());
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    public void findById_shouldReturnShipmentDto_whenShipmentExists() throws Exception {
        // Given
        Long shipmentId = 1L;
        Shipment shipment = mock(Shipment.class);
        ShipmentDto shipmentDto = mock(ShipmentDto.class);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));
        when(shipmentMapper.toDto(shipment)).thenReturn(shipmentDto);

        // When
        ShipmentDto actual = shipmentService.findById(shipmentId);

        // Then
        assertNotNull(actual);
        assertEquals(shipmentDto, actual);
        verify(shipmentRepository, times(1)).findById(shipmentId);
        verify(shipmentMapper, times(1)).toDto(shipment);
    }

    @Test
    public void findById_shouldThrowNotFoundException_whenShipmentNotFound() throws Exception{
        // Given
        Long shipmentId = 1L;

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> shipmentService.findById(shipmentId));
        verify(shipmentRepository, times(1)).findById(shipmentId);
        verify(shipmentMapper, never()).toDto(any(Shipment.class));
    }

    @Test
    public void findAll_shouldReturnListOfShipmentDtos()  throws Exception{
        // Given
        Shipment shipment = mock(Shipment.class);
        ShipmentDto shipmentDto = mock(ShipmentDto.class);

        when(shipmentRepository.findAll()).thenReturn(List.of(shipment));
        when(shipmentMapper.toDto(shipment)).thenReturn(shipmentDto);

        // When
        List<ShipmentDto> actual = shipmentService.findAll();

        // Then
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(shipmentDto, actual.get(0));
        verify(shipmentRepository, times(1)).findAll();
        verify(shipmentMapper, times(1)).toDto(shipment);
    }

    @Test
    public void deleteById() throws Exception{
        // Given
        var id=1L;

        // When
        shipmentService.deleteById(id);

        // Then
        verify(shipmentRepository,times(1)).deleteById(id);
    }
}
