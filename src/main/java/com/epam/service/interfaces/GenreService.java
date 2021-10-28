package com.epam.service.interfaces;


import com.epam.model.entity.Genre;

import java.util.List;

public interface GenreService {

    Genre create(String name);

    Long add(Genre genre);

    Long[] delete(Long ...id);

    List<Genre> getAll();


    Genre getById(Long id);
}
