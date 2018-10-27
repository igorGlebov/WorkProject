package com.example.host.databaseproject.OurClasses;

public class ProductInTheBasket extends Product {

    private int count;

    public int getCount(){
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ProductInTheBasket(float price, String name, String id, int count){
        super(price, name, id);
        this.count = count;

    }
}
