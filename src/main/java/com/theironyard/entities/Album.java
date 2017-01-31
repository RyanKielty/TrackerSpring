package com.theironyard.entities;


import javax.persistence.*;

/**
 * Created by ryankielty on 1/27/17.
 */
@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue
    int id;

    @Column (nullable = false)
    String albumTitle;

    @Column (nullable = false)
    String albumArtist;

    @Column (nullable = false)
    int albumReleaseYear;

    @ManyToOne
    User user;


    public Album() {
    }

    public Album(User user) {
        this.user = user;
    }

    public Album(String albumTitle, String albumArtist, int albumReleaseYear) {
        this.albumTitle = albumTitle;
        this.albumArtist = albumArtist;
        this.albumReleaseYear = albumReleaseYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public int getAlbumReleaseYear() {
        return albumReleaseYear;
    }

    public void setAlbumReleaseYear(int albumReleaseYear) {
        this.albumReleaseYear = albumReleaseYear;
    }
}
