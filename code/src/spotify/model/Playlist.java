package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Playlist {
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Long playlistId;
    private final String playlistName;
    private final List<String> ownerIds;

    public Playlist(String name, String ownerId){
        this.playlistId = idGenerator.incrementAndGet();
        this.playlistName = name;
        this.ownerIds = new ArrayList<>();
        this.ownerIds.add(ownerId);
    }

//    public addCollaborator(String userId){
//        this.ownerIds.add(userId);
//    }

    public void adSong(){

    }

    public void removeSong(){

    }

    public void reorderSong(){

    }
}
