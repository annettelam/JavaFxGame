package com.example.demo;

public class Upgrade {
    private String name;
    private int cost;
    private int level;

    public Upgrade(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.level = 0;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public void incrementLevel() {
        this.level++;
    }
}
