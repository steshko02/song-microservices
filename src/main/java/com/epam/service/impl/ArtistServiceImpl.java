package com.epam.service.impl;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Artist;
import com.epam.model.entity.Genre;
import com.epam.repository.mysql.ArtistRepository;
import com.epam.repository.mysql.GenreRepository;
import com.epam.service.interfaces.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Long add(Artist artist) {
        artistRepository.save(artist);
        return artist.getId();
    }

    private boolean checkExist(Set<Genre> genres){
        for (Genre g: genres) {
            if(!genreRepository.existsById(g.getId()))
                return false;
        }
        return true;
    }

    @Override
    public Long edit(Artist artist,Long id) {
        Artist artistForEdit = artistRepository.findById(id).orElse(null);

        if (checkExist(artist.getGenres()) &&
                artistForEdit!=null){
            artistForEdit.setName(artist.getName());
            artistForEdit.setNotes(artist.getNotes());
            artistForEdit.setGenres(artist.getGenres());
            artistRepository.save(artistForEdit);
            return artistForEdit.getId();
        }
        throw new EntityNotFoundException(Artist.class, "id", id.toString());
    }

    @Override
    public Artist get(Long id) {
        Artist artist = artistRepository.findById(id).orElse(null);
        if(artist == null){
            throw new EntityNotFoundException(Artist.class, "id", id.toString());
        }
        return artist;
    }

    @Override
    public Long delete(Long id) {
            artistRepository.deleteById(id);
        return id;
    }

    public List<Artist> getByFilters(String name, Long[] ids){
        return artistRepository.getFilters(Arrays.asList(ids), name);
    }
}
