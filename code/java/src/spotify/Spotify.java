package spotify;

import spotify.controller.SpotifyController;

public class Spotify {
    public static void main(String[] args) throws IllegalAccessException {
        SpotifyController spotify = SpotifyController.getInstance();
        Long user1 = spotify.signupUser("divya", "123");



        Long artist1 = spotify.signupArtist("artist1", "123");
//        Long artist2 = spotify.signupArtist("artist2", "123");
//
        String token_artist1 = spotify.login("artist1", "123");
//
        Long albumId = spotify.createAlbum(token_artist1, "New Era");
        spotify.addSongToAlbum(token_artist1, albumId, "Flower", "Pop", 10800L, String.valueOf(artist1));

        spotify.getArtist(artist1);




    }
}
