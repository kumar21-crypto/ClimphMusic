package com.example.climphmusic.Modal;

public class SearchSongRVModal {

    public String songID;
    public String songName;
    public String songImage;
    public String songUrl;
    public String songArtist;
    public String songDuration;
    public String songImage1;
    public int thumbnail;

    public SearchSongRVModal(String songID, String songName, String songImage, String songImage1, String songUrl, String songArtist, String songDuration) {
        this.songID = songID;
        this.songName = songName;
        this.songImage = songImage;
        this.songUrl = songUrl;
        this.songArtist = songArtist;
        this.songDuration = songDuration;
        this.songImage1 = songImage1;
    }

    public SearchSongRVModal(String songName, String songArtist, int thumbnail) {
        this.songName = songName;
        this.songArtist = songArtist;
        this.thumbnail = thumbnail;
    }

    public SearchSongRVModal(String songName, String songImage, String songUrl, String songArtist) {
        this.songName = songName;
        this.songImage = songImage;
        this.songUrl = songUrl;
        this.songArtist = songArtist;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSongID() {
        return songID;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongImage() {
        return songImage;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongImage1() {
        return songImage1;
    }

    public void setSongImage1(String songImage1) {
        this.songImage1 = songImage1;
    }
}
