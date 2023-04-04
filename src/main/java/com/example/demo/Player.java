package com.example.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player in the game.
 *
 * @author Annette Lam, Cadan Glass
 * @version 2023
 */
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

    /**
     * Constructs a new Player object with the given initial money, number of seeds and level.
     *
     * @param money    the initial amount of money the player has
     * @param numSeeds the initial number of seeds the player has
     * @param level    the initial level of the player
     */
    public Player(int money, int numSeeds, int level) {
        this.money = money;
        this.numOfSeeds = numSeeds;
        this.seeds = new HashMap<String, Integer>();
        this.crops = new HashMap<String, Integer>();
        this.level = 1;
    }

    /**
     * Allows the player to buy a seed of the specified type, provided they have enough money to do so.
     * If the purchase is successful, the player's money is decreased and their seed count is increased by one.
     *
     * @param seedType  the type of seed to buy
     * @param seedPrice the price of the seed
     */
    public void buySeed(String seedType, int seedPrice) {
        if (money >= seedPrice) {
            money -= seedPrice;
            addSeed(seedType);
        }
    }

    /**
     * Adds a seed of the specified type to the player's inventory.
     *
     * @param seedType the type of seed to add
     */
    private void addSeed(String seedType) {
        if (seeds.containsKey(seedType)) {
            seeds.put(seedType, seeds.get(seedType) + 1);
        } else {
            seeds.put(seedType, 1);
        }
    }

    /**
     * Adds a harvested crop of the specified type to the player's inventory.
     *
     * @param cropType the type of crop to add
     */
    public void addCrop(String cropType) {
        if (crops.containsKey(cropType)) {
            crops.put(cropType, crops.get(cropType) + 1);
        } else {
            crops.put(cropType, 1);
        }
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


    public HashMap<String, Integer> getSeeds() {
        return seeds;
    }

    public HashMap<String, Integer> getCrops() {
        return crops;
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

    public void setGoldenEggs(int goldenEggs) {
        this.goldenEggs = goldenEggs;
    }

    public void setGoldRats(int goldRats) {
        this.goldRat = goldRats;
    }

    public void setGoldCobras(int goldCobras) {
        this.goldCobra = goldCobras;
    }

    public void setGoldOxes(int goldOxes) {
        this.goldOx = goldOxes;
    }

    public void setCrops(Map<String, Integer> crops) {
    }
}
