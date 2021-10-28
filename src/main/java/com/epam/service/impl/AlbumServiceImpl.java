package com.epam.service.impl;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Album;
import com.epam.model.entity.Genre;
import com.epam.repository.mysql.AlbumRepository;
import com.epam.repository.mysql.ArtistRepository;
import com.epam.repository.mysql.GenreRepository;
import com.epam.service.interfaces.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ArtistRepository artistRepository;


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
        Album album = albumRepository.findById(id).orElse(null);
        if(album == null){
            throw new EntityNotFoundException(Album.class, "id", id.toString());
        }
        return album;
    }

    @Override
    public Long[] delete(Long... ids) {
        for (Long id: ids) {
            albumRepository.deleteById(id);
        }
        return ids;
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
