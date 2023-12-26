package com.n1akai.gamesstore.models;

public class Cart {
    private String title;
    private String story;
    private double price;
    private int img;
    private int amount;

    public Cart(String title, String story, double price, int img, int amount) {
        this.title = title;
        this.story = story;
        this.price = price;
        this.img = img;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
