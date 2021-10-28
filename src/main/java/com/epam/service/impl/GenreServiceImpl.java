package com.epam.service.impl;

import com.epam.exception.EntityNotFoundException;
import com.epam.model.entity.Genre;
import com.epam.repository.mysql.GenreRepository;
import com.epam.service.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Genre create(String name) {
        return new Genre(name);
    }

    @Override
    public Long add(Genre genre) {
        genreRepository.save(genre);
        return genre.getId();
    }

    @Override
    public Long[] delete(Long... ids) {
        for (Long id: ids) {
            genreRepository.deleteById(id);
        }
        return ids;
    }

    @Override
    public List<Genre> getAll() {
       return genreRepository.findAll();
    }

    @Override
    public Genre getById(Long id) {
        Genre genre =genreRepository.findById(id).orElse(null);
        if(genre == null){
            throw new EntityNotFoundException(Genre.class, "id", id.toString());
        }
        return genre;
    }
}
