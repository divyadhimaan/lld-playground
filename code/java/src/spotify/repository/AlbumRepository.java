package spotify.repository;



import spotify.model.Album;
import spotify.model.Song;

import java.util.HashMap;
import java.util.Map;

public class AlbumRepository {
    private final Map<Long, Album> albumById = new HashMap<>();
    private final Map<Long, Song> songById = new HashMap<>();

    public Album save(String name){
        Album album = new Album(name);
        albumById.put(album.getAlbumId(), album);
        return album;
    }

    public Album getAlbum(Long albumId){
        return albumById.getOrDefault(albumId, null);
    }

    public Song getSong(Long songId){
        return songById.getOrDefault(songId, null);
    }

    public Song saveSong(Song song) {
        songById.put(song.getSongId(), song);
        return song;
    }

    public Album saveAlbum(Album album) {
        albumById.put(album.getAlbumId(), album);
        return album;
    }
}
