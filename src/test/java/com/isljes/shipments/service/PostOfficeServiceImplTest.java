package com.isljes.shipments.service;

import com.isljes.shipments.dto.PostOfficeDto;
import com.isljes.shipments.dto.RegisterPostOfficeDto;
import com.isljes.shipments.exception.NotFoundException;
import com.isljes.shipments.mapper.PostOfficeMapper;
import com.isljes.shipments.mapper.RegisterPostOfficeDtoMapper;
import com.isljes.shipments.model.PostOffice;
import com.isljes.shipments.repository.PostOfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class PostOfficeServiceImplTest {
    @Mock
    private PostOfficeRepository officeRepository;

    @Mock
    private PostOfficeMapper postOfficeMapper;

    @Mock
    private RegisterPostOfficeDtoMapper registerPostOfficeDtoMapper;

    @InjectMocks
    private PostOfficeServiceImpl postOfficeService;


    @Test
    public void findById_shouldReturnPostOffice_whenExist() throws Exception{
        // Given
        var id = 1L;
        var postOffice = mock(PostOffice.class);
        when(officeRepository.findById(id)).thenReturn(Optional.of(postOffice));

        // When
        PostOffice actual = postOfficeService.findById(id);

        // Then
        assertNotNull(actual);
        verify(officeRepository, times(1)).findById(id);
    }

    @Test
    public void findById_shouldThrowNotFoundException_whenOfficeNotExist() {
        // Given
        var id = 1L;
        when(officeRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> postOfficeService.findById(id));
        verify(officeRepository, times(1)).findById(id);
    }


    @Test
    public void findAll_shouldReturnListOfPostOfficeDtos() throws Exception{
        // Given
        var postOffice = mock(PostOffice.class);
        var postOfficeDto = mock(PostOfficeDto.class);
        when(officeRepository.findAll()).thenReturn(List.of(postOffice));
        when(postOfficeMapper.toDto(postOffice)).thenReturn(postOfficeDto);

        // When
        List<PostOfficeDto> actual = postOfficeService.findAll();

        // Then
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(postOfficeDto, actual.get(0));
        verify(officeRepository, times(1)).findAll();
        verify(postOfficeMapper, times(1)).toDto(postOffice);
    }

    @Test
    public void registerPostOffice_shouldReturnPostOfficeDto_whenValidInput() throws Exception{
        // Given
        var registerPostOfficeDto = mock(RegisterPostOfficeDto.class);
        var postOffice = mock(PostOffice.class);
        var savedPostOffice = mock(PostOffice.class);
        var postOfficeDto = mock(PostOfficeDto.class);
        when(registerPostOfficeDtoMapper.toEntity(registerPostOfficeDto)).thenReturn(postOffice);
        when(officeRepository.save(postOffice)).thenReturn(savedPostOffice);
        when(postOfficeMapper.toDto(savedPostOffice)).thenReturn(postOfficeDto);

        // When
        PostOfficeDto actual = postOfficeService.registerPostOffice(registerPostOfficeDto);

        // Then
        assertNotNull(actual);
        assertEquals(postOfficeDto, actual);
        verify(registerPostOfficeDtoMapper, times(1)).toEntity(registerPostOfficeDto);
        verify(officeRepository, times(1)).save(postOffice);
        verify(postOfficeMapper, times(1)).toDto(savedPostOffice);
    }


    @Test
    public void deleteByIdTest_shouldCallRepo() throws Exception{
        // Given
        var id=1L;

        // When
        postOfficeService.deleteById(id);

        // Then
        verify(officeRepository,times(1)).deleteById(id);
    }
}
