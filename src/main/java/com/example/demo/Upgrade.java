package com.example.demo;

/**
 * The Upgrade class represents an upgrade that can be purchased in the game.
 * Each upgrade has a name, cost, and level.
 *
 * @author Annette Lam, Cadan Glass
 * @version 2023
 */
public class Upgrade {
    private String name;
    private int cost;
    private int level;

    /**
     * Creates a new Upgrade instance with the given name and cost.
     *
     * @param name The name of the upgrade.
     * @param cost The initial cost of the upgrade.
     */
    public Upgrade(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.level = 0;
    }

    /**
     * Returns the current cost of the upgrade.
     *
     * @return The cost of the upgrade.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of the upgrade to the specified value.
     *
     * @param cost The new cost of the upgrade.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Returns the current level of the upgrade.
     *
     * @return The level of the upgrade.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Increments the level of the upgrade by 1.
     */
    public void incrementLevel() {
        this.level++;
    }
}
