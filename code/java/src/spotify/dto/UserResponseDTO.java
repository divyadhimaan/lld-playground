package src.main.java.dto;

import src.main.java.model.Role;
import java.util.List;

public class UserResponseDTO {
    private Long userId;
    private String username;
    private Role role;
    private List<Long> playlists;
    private List<Long> albums;

    public UserResponseDTO(Long userId, String username, Role role,
                           List<Long> playlists, List<Long> albums) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.playlists = playlists;
        this.albums = albums;
    }

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public List<Long> getPlaylists() { return playlists; }
    public List<Long> getAlbums() { return albums; }
}
