package com.n1akai.gamesstore.models;

public class Discount {
    private String title;
    private int img;
    private Double price;
    private Double discount;

    public Discount(String title, int img, Double price, Double discount) {
        this.title = title;
        this.img = img;
        this.price = price;
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
