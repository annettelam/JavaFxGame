package com.example.demo;

/**
 * The Animal class represents an animal that can be purchased and produces a product.
 *
 * @author Annette Lam, Cadan Glass
 * @version 2023
 */
public class Animal {
    private String type;
    private int cost;
    private String productType;
    private String imagePath;

    /**
     * Constructs an Animal object with the specified type, product type, cost, and image path.
     *
     * @param type        the type of animal
     * @param productType the product type that the animal provides
     * @param cost        the cost of the animal
     * @param imagePath   the file path of the image for the animal
     */
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

    /**
     * Returns the string representation of the Animal object, which is the type of animal.
     *
     * @return the string representation of the Animal object
     */
    @Override
    public String toString() {
        return type;
    }

    /**
     * Returns the file path of the image for the animal.
     *
     * @return the file path of the image for the animal
     */
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


