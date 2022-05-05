package com.epam.service.impl;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Album;
import com.epam.model.resource.Resource;
import com.epam.model.entity.Song;
import com.epam.repository.mysql.SongRepository;
import com.epam.rest_model.SongIdList;
import com.epam.service.interfaces.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

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

        ResponseEntity<List<Long>> response = restTemplate.exchange(
                "http://es-indexer-app/search?name="+name, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Long>>(){});
        return  response.getBody();
    }

    @Override
    public List<Song> getSongsByName(String name){
        return getIdsByName(name).stream().map(x->getById(x)).collect(Collectors.toList());
    }

    @Override
    public Page<Song> getSongsByName(int pageNumber, int pageSize,String name){
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);

        Page<Song> page = songRepository.findByIds(getIdsByName(name),pageable);
        return page;
    }

    @Override
    public List<Song> getSongsByArtist(String artist) {
        return null;
    }

    @Override
    public List<Song> getSongsByAlbum(String album) {
        return getIdsByAlbum(album).stream().map(s->getById(s)).collect(Collectors.toList());
    }

    private  List<Long> getIdsByAlbum(String album){

        ResponseEntity<SongIdList> response = restTemplate.getForEntity("http://es-indexer-app/search/byalbum?album="+album,
                SongIdList.class);
        SongIdList songIdList = response.getBody();

        return  songIdList.getSongList();
    }

    @Override
    public void addSong(Song song) {
        songRepository.save(song);
    }

    @Override
    public Boolean delete(Long id){
        songRepository.deleteById(id);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                "http://es-indexer-app/delete?id="+id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Boolean>(){});
        return response.getBody();
    }
}
