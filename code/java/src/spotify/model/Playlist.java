package spotify.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Playlist {
    private static final AtomicLong idGenerator = new AtomicLong(0);
    private final Long playlistId;
    private final String playlistName;
    private final List<Long> ownerIds;
    private final Map<Integer, Long> orderedPositions; //ordered songs added to playlist

    public Playlist(String name, Long ownerId){
        this.playlistId = idGenerator.incrementAndGet();
        this.playlistName = name;
        this.ownerIds = new ArrayList<>();
        this.ownerIds.add(ownerId);
        this.orderedPositions = new HashMap<>();
    }


    public void addSong(Long songId){
        if (orderedPositions.isEmpty()) {
            orderedPositions.put(100, songId);
            return;
        }
        int maxPosition = orderedPositions.keySet()
                .stream()
                .max(Integer::compareTo)
                .orElse(100);
        orderedPositions.put(maxPosition + 100, songId);
    }

    public void removeSong(Long songId) {
        Integer targetKey = null;

        for (Map.Entry<Integer, Long> entry : orderedPositions.entrySet()) {
            if (entry.getValue().equals(songId)) {
                targetKey = entry.getKey();
                break;
            }
        }

        if (targetKey == null) {
            throw new RuntimeException("Song not found in playlist");
        }

        orderedPositions.remove(targetKey);
    }

    public void reorderSong(Long songId, Integer newPosition){
        Integer oldKey = null;

        for (Map.Entry<Integer, Long> entry : orderedPositions.entrySet()) {
            if (entry.getValue().equals(songId)) {
                oldKey = entry.getKey();
                break;
            }
        }

        if (oldKey == null) {
            throw new RuntimeException("Song not found in playlist");
        }

        orderedPositions.remove(oldKey);
        if (orderedPositions.containsKey(newPosition)) {
            newPosition += 1;
        }
        orderedPositions.put(newPosition, songId);
    }
}
