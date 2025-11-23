package spotify;

import spotify.controller.SpotifyController;

public class Spotify {
    public static void main(String[] args) throws IllegalAccessException, InterruptedException {

        SpotifyController spotify = SpotifyController.getInstance();

        /* ---------------------------------------------------
         * 1. USER & ARTIST CREATION + LOGIN
         * --------------------------------------------------- */
        Long user1 = spotify.signupUser("divya", "123");

        Long artistA = spotify.signupArtist("ABC", "123");
        Long artistB = spotify.signupArtist("DEF", "123");
        Long artistC = spotify.signupArtist("ABCDEF", "123");

        String tokenArtistA = spotify.login("ABC", "123");
        String tokenArtistB = spotify.login("DEF", "123");
        String tokenUser1   = spotify.login("divya", "123");


        /* ---------------------------------------------------
         * 2. CREATE ALBUM + ADD SONGS
         * --------------------------------------------------- */
        Long album1 = spotify.createAlbum(tokenArtistA, "New Era");

        Long s1 = spotify.addSongToAlbum(tokenArtistA, album1, "Flower", "Pop", 10800L, String.valueOf(artistA));
        Long s2 = spotify.addSongToAlbum(tokenArtistA, album1, "Flower & Thorns", "Slow", 10800L, String.valueOf(artistA));

        String twoArtists = artistA + "," + artistB;

        Long s3 = spotify.addSongToAlbum(tokenArtistA, album1, "May", "Pop", 10800L, twoArtists);
        Long s4 = spotify.addSongToAlbum(tokenArtistB, album1, "Closer", "Pop", 10800L, String.valueOf(artistB));


        /* ---------------------------------------------------
         * 3. PLAYLIST CREATION + ADD SONGS
         * --------------------------------------------------- */
        Long playlist1 = spotify.createPlaylist(tokenUser1, "My Songs");

        spotify.addSongToPlaylist(tokenUser1, playlist1, s1);
        spotify.addSongToPlaylist(tokenUser1, playlist1, s3);
        spotify.addSongToPlaylist(tokenUser1, playlist1, s4);

        // Reordering songs
        spotify.reorderSong(tokenUser1, playlist1, s3, 5);    // move to top
        spotify.reorderSong(tokenUser1, playlist1, s4, 15);   // middle

        // Print playlist
        spotify.printPlaylist(playlist1);

        // Remove song
        spotify.removeSongFromPlaylist(tokenUser1, playlist1, s3);
        spotify.printPlaylist(playlist1);


        /* ---------------------------------------------------
         * 4. INFORMATION PRINTING
         * --------------------------------------------------- */
        spotify.printAllUsers();
        spotify.printAllAlbums();

        spotify.printSongsByArtist(artistA);
        spotify.printAlbumsByArtist(artistA);


        /* ---------------------------------------------------
         * 5. PLAYBACK (COMMAND PATTERN)
         * --------------------------------------------------- */
        spotify.play(tokenUser1, s1);         // start song
        spotify.seek(tokenUser1, 120L);       // jump to 2 min
        spotify.pause(tokenUser1);            // pause
        spotify.play(tokenUser1, s1);         // resume
        spotify.skip(tokenUser1, s4);         // skip to next
        spotify.transfer(tokenUser1, "TV");   // change device
        spotify.pause(tokenUser1);


        /* ---------------------------------------------------
         * 6. SEARCH FEATURE
         * --------------------------------------------------- */
        spotify.searchSongs("flow");
        spotify.searchAlbums("era");
        spotify.searchArtists("ABC");


        /* ---------------------------------------------------
         * 7. RECOMMENDATIONS (STRATEGY PATTERN)
         * --------------------------------------------------- */
        spotify.recommendTrending();
        spotify.recommendByGenre("pop");
        spotify.recommendByArtist(artistA);


        // NEW PLAYLIST FOR SHUFFLE SCENARIO
        Long playlist2 = spotify.createPlaylist(tokenUser1, "Morning Vibes");
        spotify.addSongToPlaylist(tokenUser1, playlist2, s1);
        spotify.addSongToPlaylist(tokenUser1, playlist2, s2);
        spotify.addSongToPlaylist(tokenUser1, playlist2, s3);
        spotify.addSongToPlaylist(tokenUser1, playlist2, s4);

        spotify.printPlaylist(playlist2);

        // Playback on second playlist
        spotify.play(tokenUser1, s2);
        spotify.skip(tokenUser1, s3);
        spotify.seek(tokenUser1, 45L);
        spotify.pause(tokenUser1);

        // Search with partial keywords
        spotify.searchSongs("Flo");
        spotify.searchArtists("AB");

        // Recommendations again with different criteria
        spotify.recommendByGenre("slow");
        spotify.recommendByArtist(artistB);


        spotify.playPlaylist(tokenUser1, playlist1);

        System.out.println("\n========= END DEMO =========\n");
    }
}
