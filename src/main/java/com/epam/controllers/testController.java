package com.epam.controllers;


import com.epam.model.entity.Album;
import com.epam.range.AudioStreaming;
import com.epam.repository.mysql.AlbumRepository;
import com.epam.service.interfaces.AlbumService;
import com.epam.service.interfaces.SongService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

@Controller
public class testController {

    @Autowired
    private SongService songService;
    @Autowired
    private AudioStreaming streaming;
    @Autowired
    private AlbumService albumService;

    @SneakyThrows
    @GetMapping("/test4")
    public String testSong1() throws IOException {
        return "audio";
    }

    @SneakyThrows
    @GetMapping("/test5/{id}")
    @ResponseBody
//    @Cacheable(value = "albums", key = "#id")
    public Album testSong2(@PathVariable("id") Long id) throws IOException {
        Album album1 = new Album();
        album1.setArtists(new HashSet<>());
        album1.setGenres(new HashSet<>());
        album1.setName("dfsf");
        album1.setNotes("fdsfsdfsdfsdf");
        album1.setYear(2000);
//        albumService.add(album1);
        System.out.println("1111111111111111111111111");
        albumService.get(35L);
        System.out.println("22222222222222222222222222");
        albumService.get(40L);
        System.out.println("2222222222222222222wwww2222222");
        albumService.get(40L);
        System.out.println("333333333333333333333333333");
        albumService.get(101L);
        return  album1;
    }

    @GetMapping("/stream")
    public ResponseEntity<byte[]> streamVideo(HttpServletResponse serverHttpResponse, @RequestHeader(value = "Range", required = false) String httpRangeList) throws IOException {
        return streaming.prepareContent(82L, "mp3", httpRangeList);
    }

}
