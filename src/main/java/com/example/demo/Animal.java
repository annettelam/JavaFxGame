package com.example.demo;

public class Animal {
    private String type;
    private int cost;
    private String productType;
    private String imagePath;

    public Animal(String type, String productType, int cost, String imagePath) {
        this.type = type;
        this.productType = productType;
        this.cost = cost;
        this.imagePath = imagePath;
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

    // Getter and setter for imagePath
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return type;
    }
}


