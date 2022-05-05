package com.epam.service.dtointerfaces;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.dto.ArtistDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;


public interface ArtistDtoService {

//    @CachePut(value = "artists-single", key = "#artistDto.id")
    Long add(ArtistDto artistDto);

//    @CachePut(value = "artists-single", key = "#id")
    Long edit(ArtistDto artistDto, Long id) throws EntityNotFoundException;

//    @Cacheable(value = "artists-single", key = "#id")
    ArtistDto getArtists(Long id) throws EntityNotFoundException;

//    @CacheEvict(value = "artists-single", key = "#ids")
    Long delete(Long id) throws EntityNotFoundException;

    Set<ArtistDto> getByFilters(String name, @RequestParam Long[] genres);
}
