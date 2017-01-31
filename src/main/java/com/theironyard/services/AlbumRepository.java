package com.theironyard.services;

import com.theironyard.entities.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ryankielty on 1/27/17.
 */
public interface AlbumRepository extends CrudRepository<Album, Integer> {
    List<Album> findByAlbumTitle(String albumTitle);
    List<Album> findByAlbumArtist(String albumArtist);
    List<Album> findByAlbumReleaseYear(Integer albumReleaseYear);
}