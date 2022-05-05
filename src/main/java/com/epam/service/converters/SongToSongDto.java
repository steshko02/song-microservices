package com.epam.service.converters;

import com.epam.model.entity.Album;
import com.epam.model.entity.Song;
import com.epam.model.entity.dto.AlbumDto;
import com.epam.model.entity.dto.SongDto;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SongToSongDto implements Converter<Song, SongDto> {

    @Override
    public SongDto convert(Song song) {
    return   new SongDto(song.getId(), song.getName(), song.getYear(),
                song.getNotes(), song.getAlbum().getName(), song.getResource().getId(),song.getResourceObjId());
    }
}
