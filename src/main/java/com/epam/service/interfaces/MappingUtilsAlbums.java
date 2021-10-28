package com.epam.service.interfaces;


import com.epam.model.entity.Album;
import com.epam.model.entity.AlbumDto;

public interface MappingUtilsAlbums {
    AlbumDto mapToDto(Album entity);
    Album mapToEntity(AlbumDto dto);
}
