package com.example.climphmusic.Modal;

public class AlbumRVModal {

    public String album_type;
    public String albumName;
    public String artistName;
    public String external_urls;
    public String albumId;
    public String albumImageUrl;
    public String albumSubtitle;
    public String songCount;
    public String subTitle;

    // Popular Album Modal
    public AlbumRVModal(String albumName, String artistName, String external_urls, String albumId, String albumImageUrl,String album_type) {
        this.albumName = albumName;
        this.artistName = artistName;
        this.external_urls = external_urls;
        this.albumId = albumId;
        this.albumImageUrl = albumImageUrl;
        this.album_type = album_type;
    }

    // Trending Album Modal
    public AlbumRVModal(String albumName, String artistName, String albumId, String albumImageUrl) {
        this.albumName = albumName;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumImageUrl = albumImageUrl;
    }

    // Top Chart Album


    public AlbumRVModal(String album_type, String albumName,String artistName, String external_urls, String albumId, String albumImageUrl, String albumSubtitle, String songCount) {
        this.album_type = album_type;
        this.albumName = albumName;
        this.artistName = artistName;
        this.external_urls = external_urls;
        this.albumId = albumId;
        this.albumImageUrl = albumImageUrl;
        this.albumSubtitle = albumSubtitle;
        this.songCount = songCount;
    }

    // Top Playlist
    public AlbumRVModal(String albumName, String albumId, String albumImageUrl, String albumSubtitle, String songCount,String external_urls,String album_type) {
        this.albumName = albumName;
        this.albumId = albumId;
        this.albumImageUrl = albumImageUrl;
        this.albumSubtitle = albumSubtitle;
        this.songCount = songCount;
        this.external_urls = external_urls;
        this.album_type = album_type;
    }

    public AlbumRVModal(String albumName, String albumId) {
        this.albumName = albumName;
        this.albumId = albumId;
    }



    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSongCount() {
        return songCount;
    }

    public void setSongCount(String songCount) {
        this.songCount = songCount;
    }

    public String getAlbumSubtitle() {
        return albumSubtitle;
    }

    public void setAlbumSubtitle(String albumSubtitle) {
        this.albumSubtitle = albumSubtitle;
    }

    public String getAlbum_type() {
        return album_type;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getExternal_urls() {
        return external_urls;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumImageUrl() {
        return albumImageUrl;
    }

    public void setAlbum_type(String album_type) {
        this.album_type = album_type;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setExternal_urls(String external_urls) {
        this.external_urls = external_urls;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setAlbumImageUrl(String albumImageUrl) {
        this.albumImageUrl = albumImageUrl;
    }
}
