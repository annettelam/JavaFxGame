package com.example.demo;

public class Animal {
    private String type;
    private int cost;
    private String productType;


    public Animal(String type, String productType, int cost) {
        this.type = type;
        this.productType = productType;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public String getProductType() {
        return productType;
    }

    @Override
    public String toString() {
        return type;
    }
}


