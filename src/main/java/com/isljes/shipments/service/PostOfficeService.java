package com.isljes.shipments.service;

import com.isljes.shipments.dto.PostOfficeDto;
import com.isljes.shipments.dto.RegisterPostOfficeDto;
import com.isljes.shipments.model.PostOffice;

import java.util.List;

public interface PostOfficeService {

    PostOffice findById(Long id);

    List<PostOfficeDto> findAll();

    PostOfficeDto registerPostOffice(RegisterPostOfficeDto registerPostOfficeDto);

    void deleteById(Long id);

}
