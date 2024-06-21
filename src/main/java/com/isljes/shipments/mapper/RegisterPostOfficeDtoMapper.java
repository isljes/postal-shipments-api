package com.isljes.shipments.mapper;

import com.isljes.shipments.dto.RegisterPostOfficeDto;
import com.isljes.shipments.model.PostOffice;
import org.springframework.stereotype.Component;

@Component
public class RegisterPostOfficeDtoMapper {

    public PostOffice toEntity(RegisterPostOfficeDto dto) {
        return PostOffice.builder()
                .address(dto.address())
                .name(dto.name())
                .build();
    }
}
