package com.epam.repository.mysql;

import com.epam.model.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface SongRepository extends JpaRepository<Song,Long> {
    @Query("select s from Song s where s.id in :ids")
    Page<Song> findByIds(@Param("ids") Iterable<Long> ids, Pageable pageable);
}
