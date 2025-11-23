package spotify.controller;


import spotify.model.User;
import spotify.repository.AlbumRepository;
import spotify.repository.UserRepository;
import spotify.service.AlbumService;
import spotify.service.AuthService;
import spotify.service.UserService;
import spotify.utils.TokenProvider;

import java.util.Arrays;
import java.util.List;

//aggregation
public class SpotifyController {
    private static SpotifyController instance;
    private final UserService userService;
    private final AlbumService albumService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    private SpotifyController(UserService userService,
                              AlbumService albumService,
                              AuthService authService,
                              TokenProvider tokenProvider) {
        this.userService = userService;
        this.albumService = albumService;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
    }

    public static SpotifyController getInstance(){
        if(instance == null){
            synchronized (SpotifyController.class){
                if(instance==null){
                    UserRepository userRepository = new UserRepository();
                    AlbumRepository albumRepository = new AlbumRepository();
                    TokenProvider tokenProvider = new TokenProvider();
                    UserService userService = new UserService(userRepository);
                    AlbumService albumService = new AlbumService(albumRepository);
                    AuthService authService = new AuthService(userRepository, tokenProvider);

                    instance = new SpotifyController(
                            userService,
                            albumService,
                            authService,
                            tokenProvider
                    );
                }
            }
        }
        return instance;
    }

    public Long signupUser(String username, String password){
        return authService.signupUser(username, password);
    }
    public Long signupArtist(String username, String password){
        return authService.signupArtist(username, password);
    }

    public String login(String username, String password){
        return authService.login(username, password);
    }

    public Long createAlbum(String token, String name) throws IllegalAccessException {
        User thisUser = authService.authenticateUser(token);
        if(thisUser==null){
            throw new IllegalAccessException("Not enough Permissions");
        }

        Long albumId = albumService.createAlbum(name);
        userService.addAlbumToUser(thisUser.getUserId(), albumId);

        return albumId;
    }

    public void addSongToAlbum(String token, Long albumId, String title, String genre, Long duration, String artists) throws IllegalAccessException {
        User thisUser = authService.authenticateUser(token);
        if(thisUser==null){
            throw new IllegalAccessException("Not enough Permissions");
        }

        // Convert CSV → List<Long>
        List<Long> artistIds = Arrays.stream(artists.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .toList();

        if(!userService.verifyAllUsers(artistIds)){
            throw new RuntimeException("Not valid artists");
        }

        Long songId = albumService.addSongToAlbum(albumId, title, genre, duration, artistIds);
    }

}
