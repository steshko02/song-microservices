package com.epam.service.impl;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Album;
import com.epam.model.resource.Resource;
import com.epam.model.entity.Song;
import com.epam.repository.mysql.SongRepository;
import com.epam.rest_model.SongIdList;
import com.epam.service.interfaces.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private  RestTemplate restTemplate;

    public Song create(String name, int year, String notes, Resource resource, Album album){
                 return new Song(name,year,notes,album,resource);
    }

    @Override
    public Song getById(Long id) {
        Song song = songRepository.findById(id).orElse(null);
        if(song == null){
            throw new EntityNotFoundException(Song.class, "id", id.toString());
        }
        return song;
    }

    @Override
    public List<Long> getIdsByName(String name) {
        ResponseEntity<SongIdList> response = restTemplate.getForEntity("http://es-indexer-app/search/byname?name="+name,
                SongIdList.class);
        SongIdList songIdList = response.getBody();

        return  songIdList.getSongList();
    }

    @Override
    public List<Song> getSongsByName(String name){
        List<Song> songList = new ArrayList<>();
        getIdsByName(name).stream().forEach(x->songList.add(getById(x)));
        return songList;
    }

    @Override
    public void addSong(Song song) {
        songRepository.save(song);
    }
}
