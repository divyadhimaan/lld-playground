package spotify.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class User {
    private static AtomicLong idGenerator = new AtomicLong(0);

    private final Long userId;
    private final String username;
    private final String password;
    private final Role role;


    private final List<Long> playlists = new ArrayList<>();
    private final List<Long> albums = new ArrayList<>();


    public User(String username, String password, Role role){
        this.userId = idGenerator.incrementAndGet();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Only for normal users
    public void addPlaylist(Long playlistId) {
        if (role != Role.LISTENER)
            throw new IllegalStateException("Only LISTENERS can have playlists");
        playlists.add(playlistId);
    }

    // Only for artists
    public void addAlbum(Long albumId) {
        if (role != Role.ARTIST)
            throw new IllegalStateException("Only ARTISTS can have albums");
        albums.add(albumId);
    }


}
