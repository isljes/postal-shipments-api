package com.isljes.shipments.controller;

import com.isljes.shipments.dto.*;
import com.isljes.shipments.service.ShipmentHistoryService;
import com.isljes.shipments.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipmentHistoryService historyService;

    @Operation(
            summary = "Регистрация посылки",
            description = "Регистрация посылки, автоматически выставляется статус WAITING_SHIPMENT",

            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ShipmentDto> registerShipment(@RequestBody RegisterShipmentDto shipment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(shipmentService.registerShipment(shipment));
    }

    @Operation(
            summary = "Not Found если не найден офис или посылка",
            description = "Возможные статусы  ARRIVED, DEPARTED, RECEIVED",

            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found если негайден офис или посылка",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/{shipmentId}/status")
    public ResponseEntity<ShipmentDto> updateStatus(@PathVariable Long shipmentId,
                                                    @RequestBody UpdateShipmentStatusDto statusDto) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shipmentService.updateStatus(shipmentId, statusDto));
    }

    @Operation(
            summary = "Получение истории передвижение посылки по id",
            description = "Получение истории передвижение посылки по id",

            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not_Found",
                            responseCode = "404"
                    ),
            }
    )
    @GetMapping("/{shipmentId}/history")
    public ResponseEntity<List<ShipmentHistoryDto>> getShipmentsHistory(@PathVariable Long shipmentId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(historyService.getHistoryById(shipmentId));
    }

    @Operation(
            summary = "Получение посылки по id",
            description = "Получение посылки по id",

            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not_Found",
                            responseCode = "404"
                    ),
            }
    )
    @GetMapping("/{shipmentId}")
    public ResponseEntity<ShipmentDto> getShipment(@PathVariable Long shipmentId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shipmentService.findById(shipmentId));

    }

    @Operation(
            summary = "Получение всех зарегистрированных посылок",
            description = "Получение всех зарегистрированных посылок",

            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ShipmentDto>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shipmentService.findAll());
    }


    @Operation(
            summary = "Удаление посылки",
            description = "Удаление посылки",

            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<PostOfficeDto> deleteOffice(@PathVariable Long id) {
        shipmentService.deleteById(id);
        return ResponseEntity.ok().build();

    }


}