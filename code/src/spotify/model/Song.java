package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Song {
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Long songId;
    private final String title;
    private final Long duration; //seconds
    private final String genre;
    private final Long albumId;
    private List<Long> artists;

    public Song(String title, String genre, Long duration, Long albumId, List<Long> artists){
        this.songId = idGenerator.incrementAndGet();
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.albumId = albumId;
        this.artists = artists;
    }
}
