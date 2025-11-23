package spotify.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Song {
    private static final AtomicLong idGenerator = new AtomicLong(0);
    private final Long songId;
    private final String title;
    private final Long duration; //seconds
    private final String genre;
    private final String audioUrl;
    private final Long albumId;
    private List<Long> artists;

    public Song(String title, String genre, Long duration, Long albumId, List<Long> artists){
        this.songId = idGenerator.incrementAndGet();
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.albumId = albumId;
        this.artists = artists;
        this.audioUrl = "";
    }
}
