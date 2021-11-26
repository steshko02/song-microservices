package com.epam.controllers;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Song;
import com.epam.model.resource.ResourceObj;
import com.epam.service.interfaces.ResourceObjectService;
import com.epam.service.interfaces.SongService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private ResourceObjectService resourceObjService;

    @GetMapping(value = "songs/search")
    public void findSongByName(@RequestParam String name){
         List<Song> songs = songService.getSongsByName(name);
         System.out.println("finish");
    }

    @SneakyThrows
    @RequestMapping(value = "songs/{songId}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public void playAudio(@PathVariable("songId") Long songId, HttpServletResponse response) throws EntityNotFoundException {
        Song song = songService.getById(songId);
        ResourceObj resourceObj  = resourceObjService.getResource(song.getResourceObjId());
        try{
            resourceObj.save(()->IOUtils.copy(resourceObj.read(),response.getOutputStream()));
        } catch (IOException e){
            throw e;
        }
    }


    @GetMapping("songs/download/{songId}")
    @ResponseBody
    public void serveFile(@PathVariable Long songId,HttpServletResponse response) throws IOException {

        Song song = songService.getById(songId);
        ResourceObj resourceObj  = resourceObjService.getResource(song.getResourceObjId());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+song.getName()+".mp3");
        try{
            resourceObj.save(()->IOUtils.copy(resourceObj.read(),response.getOutputStream()));
        } catch (IOException e){
            throw e;
        }
    }

}
