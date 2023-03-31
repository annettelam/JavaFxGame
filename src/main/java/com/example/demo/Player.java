package com.example.demo;

import java.util.HashMap;

public class Player {

    private int goldenEggs;

    private int goldRat;

    private int goldCobra;

    private int goldOx;
    private int level;
    private int money;
    private int numOfSeeds;
    private HashMap<String, Integer> seeds;
    private HashMap<String, Integer> crops;

    public Player(int money, int numSeeds,int level) {
        this.money = money;
        this.numOfSeeds = numSeeds;
        this.seeds = new HashMap<String, Integer>();
        this.crops = new HashMap<String, Integer>();
        this.level = 1;
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



    public int getLevel() {
        return level;
    }

    public int getRequiredXPForNextLevel() {
        return level * 100; // Change the value 100 to your desired base XP for leveling up
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addGoldenEgg() {
        goldenEggs++;
    }

    public int getGoldenEggs() {
        return goldenEggs;
    }

    public void addGoldRat() {
        goldRat++;
    }
    public int getGoldRats() {
        return goldRat;
    }

    public void addGoldCobra() {
        goldCobra++;
    }

    public int getGoldCobra() {
        return goldCobra;
    }

    public void addGoldOx() {
        goldOx++;
    }

    public int getGoldOx() {
        return goldOx;
    }
}
