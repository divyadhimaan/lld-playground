package spotify.service;


import spotify.model.User;
import spotify.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
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
                .allMatch(id -> userRepository.findByUserId(id) != null);
    }
}
