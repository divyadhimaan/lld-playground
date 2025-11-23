package spotify.service;


import spotify.model.Role;
import spotify.model.User;
import spotify.repository.UserRepository;
import spotify.utils.TokenProvider;

public class AuthService {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository, TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    public Long signupUser(String username, String password){
        return signup(username, password, Role.LISTENER);
    }

    public Long signupArtist(String username, String password){
        return signup(username, password, Role.ARTIST);
    }

    private Long signup(String username, String password, Role role){
        User existing = userRepository.findByUsername(username);
        if(existing != null){
            throw new RuntimeException("User already Exists!");
        }

        User newUser = new User(username, password, role);
        userRepository.save(newUser);
        return newUser.getUserId();
    }

    public String login(String username, String password){
        User user = userRepository.findByUsername(username);
        if(user == null || !user.getPassword().equals(password)){
            throw new RuntimeException("Invalid Credentials");
        }
        return tokenProvider.generateToken(user.getUserId());
    }


    public User authenticateUser(String token){
        return userRepository.findByUserId(tokenProvider.extractUserId(token));
    }

}
