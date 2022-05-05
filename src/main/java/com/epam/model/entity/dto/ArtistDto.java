package com.epam.model.entity.dto;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class ArtistDto implements Serializable {

    @Id
    private Long id;

    private  String name;

    private  String notes;

    private Set<Long> genres = new HashSet<>();

    public ArtistDto() {
    }

    public ArtistDto(Long id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    public ArtistDto(Long id, String name, String notes, Set<Long> genres) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistDto artistDto = (ArtistDto) o;
        return Objects.equals(id, artistDto.id) && Objects.equals(name, artistDto.name) && Objects.equals(notes, artistDto.notes) && Objects.equals(genres, artistDto.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, notes, genres);
    }
}
