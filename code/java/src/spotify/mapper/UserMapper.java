package src.main.java.mapper;

import src.main.java.dto.UserResponseDTO;
import src.main.java.model.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getRole(),
                user.getPlaylists(),
                user.getAlbums()
        );
    }
}
