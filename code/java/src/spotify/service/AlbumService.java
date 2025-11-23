package spotify.service;


import spotify.model.Album;
import spotify.model.Song;
import spotify.repository.AlbumRepository;
import spotify.repository.SongRepository;

import java.util.Collection;
import java.util.List;

public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public AlbumService(AlbumRepository albumRepository, SongRepository songRepository){
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;

    }

    public Long createAlbum(String name){
        Album newAlbum  = albumRepository.save(name);
        return newAlbum.getAlbumId();
    }

    public Album getAlbum(Long albumId){
        return albumRepository.getAlbum(albumId);
    }

    public Song getSong(Long songId){
        return albumRepository.getSong(songId);
    }

    public Long addSongToAlbum(Long albumId, String title, String genre, Long duration, List<Long> artistIds){
        Album album = getAlbum(albumId);
        if (album == null) {
            throw new RuntimeException("Album does not exist");
        }
        Song song = new Song(
                title,
                genre,
                duration,
                albumId,
                artistIds
        );
        songRepository.save(song);
        album.addSong(song.getSongId());

        albumRepository.saveAlbum(album);

        return song.getSongId();
    }
    public Collection<Album> getAllAlbums() {
        return albumRepository.getAllAlbums();
    }

}
