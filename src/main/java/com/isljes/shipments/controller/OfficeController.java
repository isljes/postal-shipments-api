package com.isljes.shipments.controller;

import com.isljes.shipments.dto.PostOfficeDto;
import com.isljes.shipments.dto.RegisterPostOfficeDto;
import com.isljes.shipments.service.PostOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-office")
@RequiredArgsConstructor
public class OfficeController {

    private final PostOfficeService officeService;

    @Operation(
            summary = "Получение всех почтовых отделений",
            description = "Получение всех почтовых отделений",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<PostOfficeDto>> getAllOffices() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(officeService.findAll());
    }

    @Operation(
            summary = "Регистрация почтового отделения",
            description = "Регистрация почтового отделения",

            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<PostOfficeDto> registerOffice(@RequestBody RegisterPostOfficeDto registerPostOfficeDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(officeService.registerPostOffice(registerPostOfficeDto));
    }

    @Operation(
            summary = "Удаление почтового отделения",
            description = "Удаление почтового отделения",

            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<PostOfficeDto> deleteOffice(@PathVariable Long id) {
        officeService.deleteById(id);
        return ResponseEntity.ok().build();

    }

}
