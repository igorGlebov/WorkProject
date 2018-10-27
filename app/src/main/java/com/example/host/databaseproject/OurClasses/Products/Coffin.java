package com.example.host.databaseproject.OurClasses.Products;

import android.graphics.Bitmap;

import com.example.host.databaseproject.OurClasses.Product;

public class Coffin extends Product {

    private String material;
    private String cloth;

    public String getMaterial() {
        return material;
    }

    public String getCloth() {
        return cloth;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public Coffin(float price, String name, String id,  int count, Bitmap image, String cloth, String material){
        super(price, name, id, count, image);
        this.cloth = cloth;
        this.material = material;
    }
}
