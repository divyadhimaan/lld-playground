package spotify.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Album {
    private static final AtomicLong idGenerator = new AtomicLong(0);
    private final Long albumId;
    private final String albumName;
    private final List<Long> songs; //storing Ids

    public Album(String name){
        this.albumId = idGenerator.incrementAndGet();
        this.albumName = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Long songId){
        songs.add(songId);
    }
}
