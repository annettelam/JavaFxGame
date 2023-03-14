package com.example.demo;

import java.util.HashMap;

public class Player {

    private int money;
    private int numOfSeeds;
    private HashMap<String, Integer> seeds;
    private HashMap<String, Integer> crops;

    public Player(int money, int numSeeds) {
        this.money = money;
        this.numOfSeeds = numSeeds;
        this.seeds = new HashMap<String, Integer>();
        this.crops = new HashMap<String, Integer>();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getNumSeeds() {
        return numOfSeeds;
    }

    public void buySeed(String seedType, int seedPrice) {
        if (money >= seedPrice) {
            money -= seedPrice;
            addSeed(seedType);
        }
    }

    private void addSeed(String seedType) {
        if (seeds.containsKey(seedType)) {
            seeds.put(seedType, seeds.get(seedType) + 1);
        } else {
            seeds.put(seedType, 1);
        }
    }

    public HashMap<String, Integer> getSeeds() {
        return seeds;
    }

    public HashMap<String, Integer> getCrops() {
        return crops;
    }

    public void addCrop(String cropType) {
        if (crops.containsKey(cropType)) {
            crops.put(cropType, crops.get(cropType) + 1);
        } else {
            crops.put(cropType, 1);
        }
    }
}
