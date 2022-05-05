package com.epam.controllers;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.dto.ArtistDto;
import com.epam.service.dtointerfaces.ArtistDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ArtistController {

    @Autowired
    private ArtistDtoService artistDtoService;

    @PostMapping("/artists")
    public  Long add(@RequestBody ArtistDto artistDto){
        return artistDtoService.add(artistDto);
    }

    @PutMapping("/artists/{id}")
    public  Long edit(@RequestBody ArtistDto artistDto,@PathVariable Long id) throws EntityNotFoundException {
        return artistDtoService.edit(artistDto, id);
    }

    @GetMapping("/artists/{id}")
    public ArtistDto getArtists(@PathVariable Long id) throws EntityNotFoundException{
        return artistDtoService.getArtists(id);
    }

    @DeleteMapping("/artists")
    public Long delete(@RequestParam Long id) throws EntityNotFoundException {
        return  artistDtoService.delete(id);
    }

    @GetMapping("/artists")
    public Set<ArtistDto> getByFilters(@RequestParam String name, @RequestParam Long[] genres)  {
        return   artistDtoService.getByFilters(name,genres);
    }
}
