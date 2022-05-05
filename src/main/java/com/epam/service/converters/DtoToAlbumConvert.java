package com.epam.service.converters;

import com.epam.model.entity.Album;
import com.epam.model.entity.dto.AlbumDto;
import com.epam.repository.mysql.ArtistRepository;
import com.epam.repository.mysql.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToAlbumConvert
        implements Converter<AlbumDto, Album> {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Album convert(AlbumDto dto) {
        Album entity = new Album();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setYear(dto.getYear());
        for (Long id: dto.getGenres()) {
            entity.getGenres().add(genreRepository.getById(id));
        }
        for (Long a: dto.getArtists()) {
            entity.getArtists().add(artistRepository.getById(a));
        }
        return entity;
    }
}
