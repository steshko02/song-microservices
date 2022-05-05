package com.epam.service.dtoservice;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Album;
import com.epam.model.entity.dto.AlbumDto;
import com.epam.service.dtointerfaces.AlbumDtoService;
import com.epam.service.interfaces.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class AlbumDtoServiceImpl implements AlbumDtoService {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ConversionService conversionService;

    @Override
    @CachePut(value = "albums", key = "#albumDto.id")
    public  Long add(AlbumDto albumDto){
        return albumService.add(conversionService.convert(albumDto, Album.class));
    }

    @Override
    @CachePut(value = "albums", key = "#id")
    public  Long edit(AlbumDto albumDto, Long id) throws EntityNotFoundException {
        return albumService.edit( conversionService.convert(albumDto,Album.class), id);
    }

    @Override
    @Cacheable(value = "albums", key = "#id")
    public AlbumDto getAlbum(Long id) throws EntityNotFoundException {
        return conversionService.convert(albumService.get(id),AlbumDto.class);
    }

    @Override
    @CacheEvict(value = "albums", key = "#id")
    public Long delete(Long id) throws EntityNotFoundException{
        return  albumService.delete(id);
    }
}
