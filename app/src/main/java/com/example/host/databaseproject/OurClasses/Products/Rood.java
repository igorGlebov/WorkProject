package com.example.host.databaseproject.OurClasses.Products;

import android.graphics.Bitmap;

import com.example.host.databaseproject.OurClasses.Product;

public class Rood extends Product {
    private double hight;
    private String material;

    public double getHight() {
        return hight;
    }

    public String getMaterial() {
        return material;
    }

    public void setHight(double hight) {
        this.hight = hight;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Rood(float price, String name, String id, int count, Bitmap image, String material, double hight){
        super(price, name, id, count, image);
        this.hight = hight;
        this.material = material;


    }
}
