package com.epam.repository.mysql;

import com.epam.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AlbumRepository  extends JpaRepository<Album,Long> {
    Album findByName(String name);

//    @Query("select u from Album u where u.id= :id")
//    Album getById(Long id);
}
