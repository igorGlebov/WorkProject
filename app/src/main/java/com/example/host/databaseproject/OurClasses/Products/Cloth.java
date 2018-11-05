package com.example.host.databaseproject.OurClasses.Products;

import android.graphics.Bitmap;

import com.example.host.databaseproject.OurClasses.Product;

public class Cloth extends Product {
    private String contains;
    private int size;

    private enum Types{
        FOOTWEAR("Обувь"),  MALE_COMPLECT("Мужской комплект"), FEMALE_COMBLECT("Женский комплект");

        private String type;

        Types(String type){
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    public String getContains() {
        return contains;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType(String value) {
        if(value.equals(Types.FOOTWEAR.toString())){
            return Types.FOOTWEAR.toString();
        }
        else if(value.equals(Types.MALE_COMPLECT.toString())){
            return Types.MALE_COMPLECT.toString();
        }
        else if(value.equals(Types.FEMALE_COMBLECT.toString())){
            return Types.FEMALE_COMBLECT.toString();
        }
        else return "";
    }

    public void setContains(String contains) {
        this.contains = contains;
    }


    public Cloth(String price, String name, String id, int count, Bitmap image, String contains){
        super(price, name, id, count, image);
        this.contains = contains;
    }


}
