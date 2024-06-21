package com.isljes.shipments.service;

import com.isljes.shipments.dto.PostOfficeDto;
import com.isljes.shipments.dto.RegisterPostOfficeDto;
import com.isljes.shipments.exception.NotFoundException;
import com.isljes.shipments.mapper.PostOfficeMapper;
import com.isljes.shipments.mapper.RegisterPostOfficeDtoMapper;
import com.isljes.shipments.model.PostOffice;
import com.isljes.shipments.repository.PostOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository officeRepository;
    private final PostOfficeMapper postOfficeMapper;
    private final RegisterPostOfficeDtoMapper registerPostOfficeDtoMapper;

    @Override
    public PostOffice findById(Long id) {
        return officeRepository
                .findById(id).orElseThrow(() -> new NotFoundException(
                                HttpStatus.NOT_FOUND,
                                String.format("Office with id= %s not exists", id)
                        )
                );

    }

    @Override
    public List<PostOfficeDto> findAll() {
        return officeRepository.findAll().stream()
                .map(postOfficeMapper::toDto)
                .toList();
    }

    @Override
    public PostOfficeDto registerPostOffice(RegisterPostOfficeDto registerPostOfficeDto) {
        PostOffice entity = registerPostOfficeDtoMapper.toEntity(registerPostOfficeDto);
        PostOffice registeredOfficeEntity = officeRepository.save(entity);
        return postOfficeMapper.toDto(registeredOfficeEntity);
    }

    @Override
    public void deleteById(Long id) {
        officeRepository.deleteById(id);
    }


}
