package com.example.demo;

public class Animal {
    private String type;
    private int cost;

    public Animal(String type, int cost) {
        this.type = type;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return type;
    }
}


