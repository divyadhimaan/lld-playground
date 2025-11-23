package spotify.repository;

import src.main.java.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> userByUsername = new HashMap<>();
    private final Map<Long, User> userById = new HashMap<>();

    public User save(User user){
        userByUsername.put(user.getUsername(), user);
        userById.put(user.getUserId(), user);
        return user;
    }

    public User findByUsername(String username){
        return userByUsername.getOrDefault(username, null);
    }

    public User findByUserId(Long userId){
        return userById.getOrDefault(userId, null);
    }



}
