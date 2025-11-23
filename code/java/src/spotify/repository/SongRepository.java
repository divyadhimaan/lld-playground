package spotify.repository;

import spotify.model.Song;
import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

public class SongRepository {
    private final Map<Long, Song> songById = new HashMap<>();

    public Song save(Song song) {
        songById.put(song.getSongId(), song);
        return song;
    }
    public Song findById(Long songId) {
        return songById.get(songId);
    }
    public Collection<Song> getAllSongs() {
        return songById.values();
    }
    public boolean exists(Long songId) {
        return songById.containsKey(songId);
    }
}
