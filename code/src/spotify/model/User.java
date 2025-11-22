package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private AtomicLong idGenerator = new AtomicLong(0);
    private final Long userId;
    private final String username;
    private final List<Long> playlists;

    public User(String name){
        this.userId = idGenerator.incrementAndGet();
        this.username = name;
        this.playlists = new ArrayList<>();
    }

    public void addPlaylist(Long playlistId){
        this.playlists.add(playlistId);
    }


}
