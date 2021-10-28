package com.epam.repository.mysql;


import com.epam.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository  extends JpaRepository<Album,Long> {
    Album findByName(String name);
}
