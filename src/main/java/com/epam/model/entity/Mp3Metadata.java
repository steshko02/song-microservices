package com.epam.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Mp3Metadata implements Serializable {
    private  Long id;
    private  String name;
    private  int year;
    private String notes;
    private String album;

    public Mp3Metadata(Long id, String name, int year, String notes, String album) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.album = album;
    }

    public Mp3Metadata() {

    }
}
