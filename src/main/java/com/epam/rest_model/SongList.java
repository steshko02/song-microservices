package com.epam.rest_model;

import com.epam.model.entity.Song;
import com.epam.model.entity.dto.SongDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Deprecated
public class SongList {

    private List<SongDto> songList;

    public SongList(){
        songList=new ArrayList<>();
    }

    public void addSong(SongDto song) {
        songList.add(song);
    }

    public SongList(List<SongDto> songList) {
        this.songList = songList;
    }
}
