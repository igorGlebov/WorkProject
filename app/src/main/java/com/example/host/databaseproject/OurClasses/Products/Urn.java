package com.example.host.databaseproject.OurClasses.Products;

import android.graphics.Bitmap;

import com.example.host.databaseproject.OurClasses.Product;

public class Urn extends Product {

    private String material;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Urn(String price, String name, String id,  int count, Bitmap image, String material){
        super(price, name, id, count, image);
        this.material = material;
    }
}
