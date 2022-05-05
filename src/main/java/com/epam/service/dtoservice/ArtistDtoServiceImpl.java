package com.epam.service.dtoservice;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Artist;
import com.epam.model.entity.dto.ArtistDto;
import com.epam.service.dtointerfaces.ArtistDtoService;
import com.epam.service.interfaces.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArtistDtoServiceImpl implements ArtistDtoService {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConversionService conversionService;

    @Override
    @CachePut(value = "artists-single", key = "#artistDto.id")
    public  Long add(ArtistDto artistDto){
        return artistService.add(conversionService.convert(artistDto, Artist.class));
    }

    @Override
    @CachePut(value = "artists-single", key = "#id")
    public  Long edit(ArtistDto artistDto, Long id) throws EntityNotFoundException {
        return artistService.edit(conversionService.convert(artistDto,Artist.class), id);
    }

    @Override
    @Cacheable(value = "artists-single", key = "#id")
    public ArtistDto getArtists(Long id) throws EntityNotFoundException{
        return conversionService.convert(artistService.get(id),ArtistDto.class);
    }

    @Override
    @CacheEvict(value = "artists-single", key = "#ids")
    public Long delete(Long id) throws EntityNotFoundException {
        return  artistService.delete(id);
    }

    @Override
    public Set<ArtistDto> getByFilters(String name, @RequestParam Long[] genres)  {
        List<Artist> artists = artistService.getByFilters(name,genres);
        return   artists.stream().map(artist -> conversionService.convert(artist,ArtistDto.class)).collect(Collectors.toSet());
    }
}
