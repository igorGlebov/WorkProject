package com.example.host.databaseproject.OurClasses.Products;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.host.databaseproject.OurClasses.Product;

import java.util.HashMap;
import java.util.Map;


//Добавить цвета
public class Ribbon extends Product {

    private String material;
    private String text;
    private Color currentColor;
    private Color currentWriteColor;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Ribbon(String price, String name, String id, int count, Bitmap image, String material, String text){
        super(price, name, id, count, image);
        this.text = text;
        this.material = material;

    }

}
