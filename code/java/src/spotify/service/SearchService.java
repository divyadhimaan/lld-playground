package spotify.service;

import spotify.model.Album;
import spotify.model.Role;
import spotify.model.Song;
import spotify.model.User;
import spotify.repository.AlbumRepository;
import spotify.repository.SongRepository;
import spotify.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public SearchService(SongRepository songRepository,
                         AlbumRepository albumRepository,
                         UserRepository userRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    // 🔎 Search Songs
    public List<Song> searchSongs(String keyword) {
        String k = keyword.toLowerCase();
        return songRepository.getAllSongs()
                .stream()
                .filter(song -> song.getTitle().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    // Search Albums
    public List<Album> searchAlbums(String keyword){
        String k = keyword.toLowerCase();
        return albumRepository.getAllAlbums()
                .stream()
                .filter(album -> album.getAlbumName().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    // Search Artist
    public List<User> searchArtists(String keyword){
        String k = keyword.toLowerCase();
        return userRepository.getAllUsers()
                .stream()
                .filter(user -> user.getRole() == Role.ARTIST)
                .filter(user -> user.getUsername().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }
}
