package com.epam.controllers;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Song;
import com.epam.service.interfaces.ResourceObjectService;
import com.epam.service.interfaces.SongService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private ResourceObjectService resourceObjService;

    @SneakyThrows
    @RequestMapping(value = "songs/{songId}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<Resource> playAudio(@PathVariable("songId") Long songId) throws EntityNotFoundException {
        Song song = songService.getById(songId);
        Resource resource = new ByteArrayResource(resourceObjService.getResource(song.getResourceObjId()).read().readAllBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(resource.contentLength());
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("songs/download/{songId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long songId) throws IOException {

        Song song = songService.getById(songId);
        Resource file = new ByteArrayResource(resourceObjService.getResource(song.getResourceObjId()).read().readAllBytes());
        return ResponseEntity
                .ok()
                .contentLength(file.contentLength())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + song.getName()+".mp3" + "\"")
                .body(file);
    }

}
