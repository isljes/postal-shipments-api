package com.isljes.shipments.service;

import com.isljes.shipments.dto.ShipmentHistoryDto;
import com.isljes.shipments.mapper.ShipmentHistoryMapper;
import com.isljes.shipments.model.Event;
import com.isljes.shipments.model.PostOffice;
import com.isljes.shipments.model.Shipment;
import com.isljes.shipments.model.ShipmentHistory;
import com.isljes.shipments.repository.ShipmentHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentHistoryServiceImplTest {

    @Mock
    private ShipmentHistoryRepository historyRepository;

    @Mock
    private ShipmentHistoryMapper shipmentHistoryMapper;

    @InjectMocks
    private ShipmentHistoryServiceImpl shipmentHistoryService;

    @Test
    public void createRecord_shouldSaveAndReturnShipmentHistory() throws Exception {
        // Given
        PostOffice office = mock(PostOffice.class);
        Shipment shipment = mock(Shipment.class);
        Event event = mock(Event.class);
        ShipmentHistory record = ShipmentHistory.builder()
                .shipment(shipment)
                .postOffice(office)
                .event(event)
                .time(Timestamp.from(Instant.now()))
                .build();

        when(historyRepository.save(any(ShipmentHistory.class))).thenReturn(record);

        // When
        ShipmentHistory actual = shipmentHistoryService.createRecord(office, shipment, event);

        // Then
        assertNotNull(actual);
        assertEquals(record, actual);
        verify(historyRepository, times(1)).save(any(ShipmentHistory.class));
    }

    @Test
    public void getHistoryById_shouldReturnListOfShipmentHistoryDtos() throws Exception {
        // Given
        Long id = 1L;
        ShipmentHistory shipmentHistory = mock(ShipmentHistory.class);
        ShipmentHistoryDto shipmentHistoryDto = mock(ShipmentHistoryDto.class);

        when(historyRepository.findAllByShipmentIdOrderById(id)).thenReturn(List.of(shipmentHistory));
        when(shipmentHistoryMapper.toRegisterShipmentDto(shipmentHistory)).thenReturn(shipmentHistoryDto);

        // When
        List<ShipmentHistoryDto> actual = shipmentHistoryService.getHistoryById(id);

        // Then
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(shipmentHistoryDto, actual.get(0));
        verify(historyRepository, times(1)).findAllByShipmentIdOrderById(id);
        verify(shipmentHistoryMapper, times(1)).toRegisterShipmentDto(shipmentHistory);
    }
}

