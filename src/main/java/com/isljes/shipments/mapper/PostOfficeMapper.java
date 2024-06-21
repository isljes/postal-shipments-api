package com.isljes.shipments.mapper;

import com.isljes.shipments.dto.PostOfficeDto;
import com.isljes.shipments.model.PostOffice;
import org.springframework.stereotype.Component;

@Component
public class PostOfficeMapper {
    public PostOffice toEntity(PostOfficeDto dto) {
        return PostOffice.builder()
                .address(dto.address())
                .name(dto.name())
                .build();
    }

    public PostOfficeDto toDto(PostOffice entity) {
        return PostOfficeDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .name(entity.getName())
                .build();
    }
}
