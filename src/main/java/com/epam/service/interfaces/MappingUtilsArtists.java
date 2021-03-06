package com.epam.service.interfaces;


import com.epam.model.entity.Artist;
import com.epam.model.entity.dto.ArtistDto;

public interface MappingUtilsArtists {
    ArtistDto mapToDto(Artist entity);
    Artist mapToEntity(ArtistDto dto);
}
