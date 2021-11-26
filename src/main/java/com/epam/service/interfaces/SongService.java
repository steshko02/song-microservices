package com.epam.service.interfaces;


import com.epam.model.entity.Album;
import com.epam.model.resource.Resource;
import com.epam.model.entity.Song;

import java.util.List;

public interface SongService {

    void addSong(Song song);
    Song create(String name, int year, String notes, Resource resource, Album album);
    Song getById(Long id);
    List<Long> getIdsByName(String  name);

    List<Song> getSongsByName(String name);
}
