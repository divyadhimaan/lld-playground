package spotify.service;

import lombok.Getter;
import spotify.model.Album;
import spotify.model.Playlist;
import spotify.model.Song;
import spotify.repository.PlaylistRepository;
import spotify.repository.SongRepository;

import java.util.List;
import java.util.Map;

@Getter
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public PlaylistService(PlaylistRepository playlistRepository,  SongRepository songRepository){
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    public Long createPlaylist(String name, Long ownerId){
        Playlist playlist  = playlistRepository.save(name, ownerId);
        return playlist.getPlaylistId();
    }

    public Playlist getPlaylist(Long playlistId){
        return playlistRepository.getPlaylist(playlistId);
    }

    public void addSongToPlaylist(Long playlistId, Long songId, Long userId){
        Playlist playlist = playlistRepository.getPlaylist(playlistId);
        if (playlist == null) {
            throw new RuntimeException("Playlist does not exist!");
        }
        // validate ownership
        if (!playlist.getOwnerIds().contains(userId)) {
            throw new RuntimeException("User does not own this playlist");
        }

        //validate for song duplication
        if (!songRepository.exists(songId)) {
            throw new RuntimeException("Song does not exist!");
        }

        // add song using positional index auto-increment
        playlist.addSong(songId);

        playlistRepository.savePlaylist(playlist);
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId, Long userId){
        Playlist playlist = playlistRepository.getPlaylist(playlistId);
        if (playlist == null) {
            throw new RuntimeException("Playlist does not exist!");
        }
        // validate ownership
        if (!playlist.getOwnerIds().contains(userId)) {
            throw new RuntimeException("User does not own this playlist");
        }

        // verify song exists globally
        if (!songRepository.exists(songId)) {
            throw new RuntimeException("Song does not exist");
        }

        // remove song from playlist
        playlist.removeSong(songId);

        // save updates
        playlistRepository.savePlaylist(playlist);
    }

    public void reorderSong(Long playlistId, Long songId, Integer newPosition, Long userId) {

        Playlist playlist = playlistRepository.getPlaylist(playlistId);
        if (playlist == null) {
            throw new RuntimeException("Playlist does not exist!");
        }

        // validate ownership
        if (!playlist.getOwnerIds().contains(userId)) {
            throw new RuntimeException("User does not own this playlist");
        }

        // validate song exists
        if (!songRepository.exists(songId)) {
            throw new RuntimeException("Song does not exist");
        }

        // reorder
        playlist.reorderSong(songId, newPosition);

        playlistRepository.savePlaylist(playlist);
    }

    public List<Long> getPlaylistSongOrder(Long playlistId) {
        Playlist playlist = playlistRepository.getPlaylist(playlistId);

        return playlist.getOrderedPositions()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
    }


}
