package com.epam.model.entity.dto;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.epam.model.entity.Album;
import com.epam.model.resource.Resource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
public class SongDto implements Serializable{
    @Id
    private Long id;

    private  String name;

    private  int year;

    private String notes;

    private String album;

    private Long  resource;

    private  String resourceObjId;

    public SongDto(Long id, String name, int year, String notes, String album, Long resource, String resourceObjId) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.album = album;
        this.resource = resource;
        this.resourceObjId = resourceObjId;
    }

    public SongDto() {
    }
}
