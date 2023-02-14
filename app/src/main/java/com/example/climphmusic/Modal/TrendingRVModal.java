package com.example.climphmusic.Modal;

public class TrendingRVModal {

    String albumImage,albumName,albumArtist,albumId,albumUrl,albumType;

    public TrendingRVModal(String albumImage, String albumName, String albumArtist, String albumId, String albumUrl, String albumType) {
        this.albumImage = albumImage;
        this.albumName = albumName;
        this.albumArtist = albumArtist;
        this.albumId = albumId;
        this.albumUrl = albumUrl;
        this.albumType = albumType;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }
}
