package com.example.grocergrid.models;

import java.io.Serializable;

public class ViewAllModel implements Serializable {

    String name;
    String rating;
    String img_url;
    String type;
    int price;
    int totalStock;

    public ViewAllModel() {
    }

    public ViewAllModel(String name, String rating, String img_url, String type, int price, int totalStock) {
        this.name = name;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
        this.totalStock = totalStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }
}