package com.epam.controllers;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.dto.AlbumDto;
import com.epam.service.dtointerfaces.AlbumDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AlbumController {

    @Autowired
    private AlbumDtoService albumDtoService;

    @PostMapping("/albums")
    public  Long add(@RequestBody AlbumDto albumDto){
        return albumDtoService.add(albumDto);
    }

    @PutMapping("/albums/{id}")
    public  Long edit(@RequestBody AlbumDto albumDto,@PathVariable Long id) throws EntityNotFoundException {
        return albumDtoService.edit(albumDto, id);
    }

    @GetMapping("/albums/{id}")
    public AlbumDto getAlbum(@PathVariable Long id) throws EntityNotFoundException {
        return albumDtoService.getAlbum(id);
    }

    @DeleteMapping("/albums")
    public Long delete(@RequestParam Long id) throws EntityNotFoundException{
        return  albumDtoService.delete(id);
    }

}
