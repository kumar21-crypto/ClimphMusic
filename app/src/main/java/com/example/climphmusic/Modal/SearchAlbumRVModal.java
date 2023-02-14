package com.example.climphmusic.Modal;

public class SearchAlbumRVModal {

    public String albumID;
    public String albumName;
    public String albumUrl;
    public String albumSongCount;
    public String albumImageUrl;
    public String albumDescription;

    public SearchAlbumRVModal(String albumID, String albumName, String albumUrl, String albumSongCount, String albumImageUrl, String albumDescription) {
        this.albumID = albumID;
        this.albumName = albumName;
        this.albumUrl = albumUrl;
        this.albumSongCount = albumSongCount;
        this.albumImageUrl = albumImageUrl;
        this.albumDescription = albumDescription;
    }

    public String getAlbumID() {
        return albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public String getAlbumSongCount() {
        return albumSongCount;
    }

    public String getAlbumImageUrl() {
        return albumImageUrl;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    public void setAlbumSongCount(String albumSongCount) {
        this.albumSongCount = albumSongCount;
    }

    public void setAlbumImageUrl(String albumImageUrl) {
        this.albumImageUrl = albumImageUrl;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }
}
