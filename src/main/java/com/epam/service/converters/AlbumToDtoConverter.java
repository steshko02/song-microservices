package com.epam.service.converters;

import com.epam.model.entity.Album;
import com.epam.model.entity.AlbumDto;
import com.epam.model.entity.Artist;
import com.epam.model.entity.Genre;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class AlbumToDtoConverter
        implements Converter<Album, AlbumDto> {

    @Override
    public AlbumDto convert(Album entity) {
        AlbumDto dto = new AlbumDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setYear(entity.getYear());
        for (Genre g: entity.getGenres()) {
            dto.getGenres().add(g.getId());
        }
        for (Artist a: entity.getArtists()) {
            dto.getArtists().add(a.getId());
        }
        return dto;
    }
}
