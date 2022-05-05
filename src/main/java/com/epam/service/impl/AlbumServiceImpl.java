package com.epam.service.impl;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Album;
import com.epam.model.entity.Genre;
import com.epam.repository.mysql.AlbumRepository;
import com.epam.repository.mysql.ArtistRepository;
import com.epam.repository.mysql.GenreRepository;
import com.epam.service.interfaces.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Long add(Album album) {
        albumRepository.save(album);
        return album.getId();
    }

    @Override
    public Long edit(Album album, Long id) {
      Album albumForEdit = albumRepository.findById(id).orElse(null);
        if (checkExistGenres(album.getGenres()) &&
                albumForEdit!=null){
            albumForEdit.setName(album.getName());
            albumForEdit.setNotes(album.getNotes());
            albumForEdit.setGenres(album.getGenres());
            albumForEdit.setArtists(album.getArtists());
            albumRepository.save(albumForEdit);
            return albumForEdit.getId();
        }
        throw new EntityNotFoundException(Album.class, "id", id.toString());
    }

    private boolean checkExistGenres(Set<Genre> genres){
        for (Genre g: genres) {
            if(!genreRepository.existsById(g.getId()))
                return false;
        }
        return true;
    }

    @Override
    public Album get(Long id) {
        log.info("Get by id - "+id);
        Album album = albumRepository.findById(id).orElse(null);
        if(album == null){
            throw new EntityNotFoundException(Album.class, "id", id.toString());
        }
        return album;
    }

    @Override
    public Long delete(Long id) {
            albumRepository.deleteById(id);
        return id;
    }

    @Override
    public Album findByName(String name) {
        Album album = albumRepository.findByName(name);
        if(album == null){
            throw new EntityNotFoundException(Album.class, "name", name);
        }
        return album;
    }
}
