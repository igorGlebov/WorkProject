package com.example.host.databaseproject.OurClasses;

public class Product {
    private float price;
    private String name;
    private String id;
    private int count;

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
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

    public Product(float price, String name, String id, int count){
        this.price = price;
        this.name = name;
        this.id = id;
        this.count = count;
    }
}

