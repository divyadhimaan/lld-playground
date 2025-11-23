package spotify.service;


import spotify.model.Album;
import spotify.model.Song;
import spotify.repository.AlbumRepository;

import java.util.List;

public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
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
        Song song = new Song(
                title,
                genre,
                duration,
                albumId,
                artistIds
        );
        albumRepository.saveSong(song);
        album.addSong(song.getSongId());
        albumRepository.saveAlbum(album);
        return song.getAlbumId();
    }

}
