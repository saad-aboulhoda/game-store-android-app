package com.n1akai.gamesstore.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Game implements Serializable {
    private String id;
    private String title;
    private String description;
    private String publisher;
    private String developer;
    private Long releaseDate;
    private String platforms;
    private String posterUrl;
    private String thumbnailUrl;
    private String price;
    private Map<String, Discount> discounts;
    private List<String> genres;
    private List<String> images;

    public Game() {

    }

    public Game(String id, String title, String description, String publisher, String developer, Long releaseDate, String platforms, String posterUrl, String thumbnailUrl, String price, Map<String, Discount> discounts, List<String> genres, List<String> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.platforms = platforms;
        this.posterUrl = posterUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.discounts = discounts;
        this.genres = genres;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public Long getReleaseDate() {
        return releaseDate;
    }

    public String getPlatforms() {
        return platforms;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getPrice() {
        return price;
    }

    public Map<String, Discount> getDiscounts() {
        return discounts;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getImages() {
        return images;
    }

}
