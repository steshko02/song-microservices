package com.epam.service.converters;

import com.epam.model.entity.Artist;
import com.epam.model.entity.dto.ArtistDto;
import com.epam.model.entity.Genre;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class ArtistToDtoConverter
        implements Converter<Artist, ArtistDto> {

    @Override
    public ArtistDto convert(Artist entity) {
        ArtistDto dto = new ArtistDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        for (Genre g: entity.getGenres()) {
            dto.getGenres().add(g.getId());
        }
        return dto;
    }
}
