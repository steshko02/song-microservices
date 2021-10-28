package com.epam.controllers;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Artist;
import com.epam.model.entity.ArtistDto;
import com.epam.service.interfaces.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConversionService conversionService;

    @PostMapping("/artists")
    public  Long add(@RequestBody ArtistDto artistDto){
        return artistService.add(conversionService.convert(artistDto, Artist.class));
    }

    @PutMapping("/artists/{id}")
    public  Long edit(@RequestBody ArtistDto artistDto,@PathVariable Long id) throws EntityNotFoundException {
        return artistService.edit(conversionService.convert(artistDto,Artist.class), id);
    }

    @GetMapping("/artists/{id}")
    public ArtistDto getGenres(@PathVariable Long id) throws EntityNotFoundException{
        return conversionService.convert(artistService.get(id),ArtistDto.class);
    }

    @DeleteMapping("/artists")
    public Long[] delete(@RequestParam Long[] ids) throws EntityNotFoundException {
        return  artistService.delete(ids);
    }

    @GetMapping("/artists")
    public Set<ArtistDto> getByFilters(@RequestParam String name, @RequestParam Long[] genres)  {

        Set<ArtistDto> artistDtoSet = new HashSet<>();
        List<Artist> artists = artistService.getByFilters(name,genres);
        artists.forEach(a->artistDtoSet.add(conversionService.convert(a,ArtistDto.class)));

        return   artistDtoSet;
    }
}
