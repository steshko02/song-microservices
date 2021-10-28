package com.epam.service.impl;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Album;
import com.epam.model.resource.Resource;
import com.epam.model.entity.Song;
import com.epam.repository.mysql.SongRepository;
import com.epam.service.interfaces.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

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
    public void addSong(Song song) {
        songRepository.save(song);
    }
}
