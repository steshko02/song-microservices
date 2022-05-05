package com.epam.rest_model;

import com.epam.model.entity.Song;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Deprecated
public class SongIdList {

    private List<Long> songList;

    public SongIdList(){
        songList=new ArrayList<>();
    }
    public void addSong(Long songID){
            songList.add(songID);
    }

}
