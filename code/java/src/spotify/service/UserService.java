package spotify.service;


import spotify.model.Album;
import spotify.model.Role;
import spotify.model.Song;
import spotify.model.User;
import spotify.repository.AlbumRepository;
import spotify.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public UserService(UserRepository userRepository, AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    public User getUser(Long userId){
        return userRepository.findByUserId(userId);
    }

    public void addPlaylistToUser(Long userId, Long playlistId) {
        User user = userRepository.findByUserId(userId);
        user.addPlaylist(playlistId);
        userRepository.save(user);
    }

    public void addAlbumToUser(Long userId, Long albumId) {
        User user = userRepository.findByUserId(userId);
        user.addAlbum(albumId);
        userRepository.save(user);
    }

    public Boolean verifyAllUsers(List<Long> userIds){
        if (userIds == null || userIds.isEmpty()) return false;

        return userIds.stream()
                .map(userRepository::findByUserId)
                .allMatch(user -> user != null && user.getRole() == Role.ARTIST);
    }

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<Album> getAlbumsByArtist(Long artistId) {
        return albumRepository.getAllAlbums().stream()
                .filter(album -> {
                    // Album belongs to artist if albumId is in user's album list
                    return userRepository.findByUserId(artistId)
                            .getAlbums()
                            .contains(album.getAlbumId());
                })
                .toList();
    }

    public List<Song> getSongsByArtist(Long artistId) {
        List<Song> results = new ArrayList<>();

        for (Album album : albumRepository.getAllAlbums()) {
            for (Long songId : album.getSongs()) {
                Song song = albumRepository.getSong(songId);

                if (song != null && song.getArtists().contains(artistId)) {
                    results.add(song);
                }
            }
        }

        return results;
    }
}
