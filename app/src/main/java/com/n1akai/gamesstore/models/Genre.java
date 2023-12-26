package com.n1akai.gamesstore.models;

public class Genre {
    private String id;
    private String title;
    private String imgUrl;

    public Genre() {

    }

    public Genre(String id) {
        this.id = id;
    }

    public Genre(String id, String title, String imgUrl) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
