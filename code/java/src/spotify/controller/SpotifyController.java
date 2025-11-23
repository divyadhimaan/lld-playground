package spotify.controller;


import spotify.model.Playlist;
import spotify.model.Song;
import spotify.model.User;
import spotify.repository.AlbumRepository;
import spotify.repository.PlaylistRepository;
import spotify.repository.SongRepository;
import spotify.repository.UserRepository;
import spotify.service.*;
import spotify.strategy.recommend.ArtistBasedStrategy;
import spotify.strategy.recommend.GenreBasedStrategy;
import spotify.strategy.recommend.TrendingStrategy;
import spotify.utils.TokenProvider;

import java.util.Arrays;
import java.util.List;

//aggregation
public class SpotifyController {
    private static SpotifyController instance;
    private final UserService userService;
    private final AlbumService albumService;
    private final AuthService authService;
    private final PlaylistService playlistService;
    private final PlaybackService playbackService;
    private final SearchService searchService;
    private final RecommendationService recommendationService;


    private SpotifyController(UserService userService,
                              AlbumService albumService,
                              AuthService authService,
                              PlaylistService playlistService,
                              PlaybackService playbackService,
                              SearchService searchService,
                              RecommendationService recommendationService ) {
        this.userService = userService;
        this.albumService = albumService;
        this.authService = authService;
        this.playlistService = playlistService;
        this.playbackService = playbackService;
        this.searchService = searchService;
        this.recommendationService = recommendationService;
    }

    public static SpotifyController getInstance(){
        if(instance == null){
            synchronized (SpotifyController.class){
                if(instance==null){
                    UserRepository userRepository = new UserRepository();
                    AlbumRepository albumRepository = new AlbumRepository();
                    SongRepository songRepository = new SongRepository();

                    TokenProvider tokenProvider = new TokenProvider();

                    UserService userService = new UserService(userRepository, albumRepository);
                    AlbumService albumService = new AlbumService(albumRepository, songRepository);

                    PlaylistRepository playlistRepository = new PlaylistRepository();
                    PlaylistService playlistService = new PlaylistService(playlistRepository, songRepository);
                    AuthService authService = new AuthService(userRepository, tokenProvider);

                    PlaybackService playbackService = new PlaybackService();
                    SearchService searchService = new SearchService(songRepository, albumRepository, userRepository);
                    RecommendationService recommendationService = new RecommendationService();


                    instance = new SpotifyController(
                            userService,
                            albumService,
                            authService,
                            playlistService,
                            playbackService,
                            searchService,
                            recommendationService
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

    public Long createPlaylist(String token, String name) throws IllegalAccessException {
        User thisUser = authService.authenticateUser(token);
        if(thisUser==null){
            throw new IllegalAccessException("Not enough Permissions");
        }

        Long playlistId = playlistService.createPlaylist(name, thisUser.getUserId());
        userService.addPlaylistToUser(thisUser.getUserId(), playlistId);

        return playlistId;
    }

    public void addSongToPlaylist(String token, Long playlistId, Long songId) throws IllegalAccessException {
        User user = authService.authenticateUser(token);
        if(user == null){
            throw new IllegalAccessException("Invalid token");
        }

        playlistService.addSongToPlaylist(playlistId, songId, user.getUserId());
    }
    public void removeSongFromPlaylist(String token, Long playlistId, Long songId) throws IllegalAccessException {

        User user = authService.authenticateUser(token);
        if (user == null) {
            throw new IllegalAccessException("Invalid token");
        }

        playlistService.removeSongFromPlaylist(playlistId, songId, user.getUserId());
    }

    public void reorderSong(
            String token,
            Long playlistId,
            Long songId,
            Integer newPosition
    ) throws IllegalAccessException {

        User user = authService.authenticateUser(token);
        if (user == null) {
            throw new IllegalAccessException("Invalid token");
        }

        playlistService.reorderSong(playlistId, songId, newPosition, user.getUserId());
    }


    public Long addSongToAlbum(String token, Long albumId, String title, String genre, Long duration, String artists) throws IllegalAccessException {
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

        return albumService.addSongToAlbum(albumId, title, genre, duration, artistIds);
    }

    // ------------------------------- PLAYBACK ------------------------------

    public void play(String token, Long songId) throws IllegalAccessException {
        User u = authService.authenticateUser(token);
        playbackService.play(u.getUserId(), songId);
    }

    public void pause(String token) throws IllegalAccessException {
        User u = authService.authenticateUser(token);
        playbackService.pause(u.getUserId());
    }

    public void skip(String token, Long nextSongId) throws IllegalAccessException {
        User u = authService.authenticateUser(token);
        playbackService.skip(u.getUserId(), nextSongId);
    }

    public void seek(String token, Long pos) throws IllegalAccessException {
        User u = authService.authenticateUser(token);
        playbackService.seek(u.getUserId(), pos);
    }

    public void transfer(String token, String deviceId) throws IllegalAccessException {
        User u = authService.authenticateUser(token);
        playbackService.transferDevice(u.getUserId(), deviceId);
    }


    // --------------------- PRINT ------------------------------- //

    public void printAllUsers() {
        System.out.println("\n---- USERS ----");
        userService.getAllUsers().forEach(user -> {
            System.out.println("UserID: " + user.getUserId() +
                    ", Username: " + user.getUsername() +
                    ", Role: " + user.getRole());
            System.out.println("  Albums: " + user.getAlbums());
            System.out.println("  Playlists: " + user.getPlaylists());
            System.out.println();
        });
    }

    public void printAllAlbums() {
        System.out.println("\n---- ALBUMS ----");
        albumService.getAllAlbums().forEach(album -> {
            System.out.println("AlbumID: " + album.getAlbumId() +
                    ", Name: " + album.getAlbumName());
            System.out.println("  Songs: " + album.getSongs());
            System.out.println();
        });
    }

    public void printSongsByArtist(Long artistId) {
        System.out.println("\n--- SONGS BY ARTIST " + artistId + " ---");

        userService.getSongsByArtist(artistId).forEach(song -> {
            System.out.println("SongID: " + song.getSongId() +
                    ", Title: " + song.getTitle() +
                    ", Album: " + song.getAlbumId() +
                    ", Artists: " + song.getArtists());
        });
    }

    public void printAlbumsByArtist(Long artistId) {
        System.out.println("\n--- ALBUMS BY ARTIST " + artistId + " ---");

        userService.getAlbumsByArtist(artistId).forEach(album -> {
            System.out.println("AlbumID: " + album.getAlbumId() +
                    ", Name: " + album.getAlbumName() +
                    ", Songs: " + album.getSongs());
        });
    }



    public void printPlaylist(Long playlistId){
        System.out.println("\n--- PLAYLIST " + playlistId + " ---");
        Playlist playlist = playlistService.getPlaylist(playlistId);
        if (playlist == null) {
            System.out.println("Playlist not found!");
            return;
        }

        playlist.getOrderedPositions().forEach((position, songId)-> {
            Song song = playlistService.getSongRepository().findById(songId);
            if (song != null) {
                System.out.println("Pos: " + position + " | SongId: " + songId + " | Title: " + song.getTitle());
            } else {
                System.out.println("Pos: " + position + " | SongId: " + songId + " | <Song not found>");
            }
        });
    }


    //-------------SEARCH FEATURE ------------------
    public void searchSongs(String keyword) {
        System.out.println("\n--- SONG SEARCH RESULTS ---");
        searchService.searchSongs(keyword).forEach(song ->
                System.out.println(song.getSongId() + " - " + song.getTitle())
        );
    }

    public void searchAlbums(String keyword) {
        System.out.println("\n--- ALBUM SEARCH RESULTS ---");
        searchService.searchAlbums(keyword).forEach(album ->
                System.out.println(album.getAlbumId() + " - " + album.getAlbumName())
        );
    }

    public void searchArtists(String keyword) {
        System.out.println("\n--- ARTIST SEARCH RESULTS ---");
        searchService.searchArtists(keyword).forEach(artist ->
                System.out.println(artist.getUsername())
        );
    }

    // ------------------ RECOMMENDATION------------------
    public void recommendTrending() {
        recommendationService.setStrategy(new TrendingStrategy(playlistService.getSongRepository()));
        List<Song> res = recommendationService.getRecommendations(null);

        System.out.println("\n--- TRENDING SONGS ---");
        res.forEach(s -> System.out.println(s.getSongId() + " - " + s.getTitle()));
    }

    public void recommendByGenre(String genre) {
        recommendationService.setStrategy(new GenreBasedStrategy(playlistService.getSongRepository(), genre));
        List<Song> res = recommendationService.getRecommendations(null);

        System.out.println("\n--- GENRE RECOMMENDATIONS (" + genre + ") ---");
        res.forEach(s -> System.out.println(s.getSongId() + " - " + s.getTitle()));
    }

    public void recommendByArtist(Long artistId) {
        recommendationService.setStrategy(new ArtistBasedStrategy(playlistService.getSongRepository(), artistId));
        List<Song> res = recommendationService.getRecommendations(null);

        System.out.println("\n--- ARTIST RECOMMENDATIONS ---");
        res.forEach(s -> System.out.println(s.getSongId() + " - " + s.getTitle()));
    }

    public void playPlaylist(String token, Long playlistId) throws IllegalAccessException {
        User user = authService.authenticateUser(token);
        List<Long> order = playlistService.getPlaylistSongOrder(playlistId);
        playbackService.playPlaylist(user.getUserId(), order);
    }

    public void nextSong(String token) throws IllegalAccessException {
        User user = authService.authenticateUser(token);
        playbackService.next(user.getUserId());
    }


}
