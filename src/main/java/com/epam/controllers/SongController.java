package com.epam.controllers;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Song;
import com.epam.model.entity.dto.SongDto;
import com.epam.model.resource.ResourceObj;
import com.epam.range.AudioStreaming;
import com.epam.service.interfaces.AlbumService;
import com.epam.service.interfaces.ResourceObjectService;
import com.epam.service.interfaces.SongService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.delay;

@RestController
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private ResourceObjectService resourceObjService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private AudioStreaming streaming;

    @GetMapping(value = "songs/search/byalbum")
    public List<SongDto> findSongByAlbum(@RequestParam String album){
        return  songService.getSongsByAlbum(album).stream().
                map(song -> conversionService.convert(song,SongDto.class)).
                collect(Collectors.toList());
    }

    @GetMapping(value = "songs/search")
    public List<SongDto> findSongByName(@RequestParam String name){

        return  songService.getSongsByName(name).stream().
                map(song -> conversionService.convert(song,SongDto.class)).
                collect(Collectors.toList());
    }

    @GetMapping("songs/page/{pageNo}")
    public Map.Entry<Integer,List<SongDto>> findPaginated(@PathVariable (value = "pageNo") int pageNo,@RequestParam("name") String name,
                                                       @RequestParam("size") int size) {
        Page<Song> page = songService.getSongsByName(pageNo, size,name);

        return Map.entry(page.getTotalPages(),page.getContent()
                .stream().map(s->conversionService.convert(s,SongDto.class)).collect(Collectors.toList()));
    }


    @GetMapping("songsList/page/{pageNo}")
    public List<SongDto> test(@PathVariable (value = "pageNo") int pageNo, @RequestParam("name") String name,
                                    @RequestParam("size") int size) {
        Page<Song> page = songService.getSongsByName(pageNo, size,name);

        return page.getContent().stream().map(s->conversionService.convert(s,SongDto.class)).collect(Collectors.toList());
    }


    @GetMapping("songs/delete")
    @ResponseBody
    public Boolean delete( @RequestParam("id") Long id) {
      return songService.delete(id);
    }

    @SneakyThrows
    @RequestMapping(value = "songsFull/{songId}", method = RequestMethod.GET, produces = {
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

    @SneakyThrows
    @RequestMapping(value = "songs/{songId}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<byte[]> streamVideo(@PathVariable("songId") Long songId,HttpServletResponse serverHttpResponse,
                                              @RequestHeader(value = "Range", required = false) String httpRangeList) throws IOException {
        return streaming.prepareContent(songId, "mp3", httpRangeList);
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
