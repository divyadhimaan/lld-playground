package spotify.repository;


import spotify.model.Playlist;
import spotify.model.Song;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlaylistRepository {
    private final Map<Long, Playlist> playlistById = new HashMap<>();

    public Playlist save(String name, Long ownerId){
        Playlist playlist = new Playlist(name, ownerId);
        playlistById.put(playlist.getPlaylistId(), playlist);
        return playlist;
    }

    public Playlist getPlaylist(Long playlistId){
        return playlistById.getOrDefault(playlistId, null);
    }


    public void savePlaylist(Playlist playlist) {
        playlistById.put(playlist.getPlaylistId(), playlist);
    }

    public Collection<Playlist> getAllPlaylists(){
        return playlistById.values();
    }
}
