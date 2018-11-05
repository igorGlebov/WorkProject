package com.example.host.databaseproject.OurClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Product {
    private String price;
    private String name;
    private String id;
    private int count;
    private Bitmap image;

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Product(String price, String name, String id, int count, Bitmap image){
        this.price = price;
        this.name = name;
        this.id = id;
        this.count = count;
        if(image != null){
            this.image = image;
        }
    }

    public Product(String price, String name) {
        this.price = price;
        this.name = name;
    }
}

