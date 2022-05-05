package com.epam.model.entity.dto;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class AlbumDto implements Serializable {
    @Id
    private Long id;

    private  String name;

    private  int year;

    private  String notes;

    private Set<Long> genres = new HashSet<>();

    private  Set<Long> artists = new HashSet<>();

    public AlbumDto() {
    }

    public AlbumDto(Long id, String name, int year, String notes, Set<Long> genres, Set<Long> artists) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.genres = genres;
        this.artists = artists;
    }

    public AlbumDto(Long id, String name, int year, String notes) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumDto albumDto = (AlbumDto) o;
        return year == albumDto.year && Objects.equals(id, albumDto.id) && Objects.equals(name, albumDto.name) && Objects.equals(notes, albumDto.notes) && Objects.equals(genres, albumDto.genres) && Objects.equals(artists, albumDto.artists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, notes, genres, artists);
    }
}
