package com.example.demo;

public class Animal {
    private String name;
    private int price;
    private int productValue; // The amount of product produced per day

    public Animal(String name, int price, int productValue) {
        this.name = name;
        this.price = price;
        this.productValue = productValue;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getProductValue() {
        return productValue;
    }
}
