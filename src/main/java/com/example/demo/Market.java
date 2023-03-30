package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Market {

    private GridPane marketPane;
    private Player player;
    private Stage marketStage;
    private HashMap<String, Integer> seedPrices;

    private HashMap<String, Upgrade> upgrades;

    private Runnable onUpgradePurchased;

    public Market(Player player, Stage marketStage, IdleFarmingGame game) {
        this.player = player;
        this.marketStage = marketStage;
        marketPane = new GridPane();
        marketPane.setPrefSize(400, 300); // Set the preferred size of the market pane
        marketPane.setHgap(10); // Set horizontal gap between columns
        marketPane.setVgap(10); // Set vertical gap between rows
        marketPane.setAlignment(Pos.CENTER); // Align the content to the center of the pane
        marketPane.setPadding(new Insets(20, 20, 20, 20)); // Set padding for the market pane

        Label marketLabel = new Label("Market");
        marketLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-font-family: 'Mali'"); // Set the style for marketLabel
        marketPane.add(marketLabel, 0, 0, 3, 1); // Span the marketLabel across 3 columns

        Label seedLabel = new Label("Seeds");
        seedLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-font-family: 'Mali'"); // Set the style for seedLabel
        marketPane.add(seedLabel, 0, 1);

        // Initialize the seedPrices HashMap
        seedPrices = new HashMap<String, Integer>();
        seedPrices.put("Carrot", 5);
        seedPrices.put("Tomato", 25);
        seedPrices.put("Corn", 20);
        seedPrices.put("Eggplant", 10);
        seedPrices.put("Pepper", 15);


        // Display the seed prices in the market pane
        int row = 2;
        for (String seed : seedPrices.keySet()) {
            Label nameLabel = new Label(seed);
            nameLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'"); // Set the style for nameLabel
            marketPane.add(nameLabel, 0, row);

            Label priceLabel = new Label("$" + seedPrices.get(seed));
            priceLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'"); // Set the style for priceLabel
            marketPane.add(priceLabel, 1, row);


            Button buyButton = new Button("Buy");
            buyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-font-family: 'Mali'"); // Set the style for buyButton
            buyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    int seedPrice = seedPrices.get(seed);
                    player.buySeed(seed, seedPrice);
                    game.updateLabels(player); // call updateLabels() using the game instance
                }
            });


            // Set the cursor to hand when hovering over the buyButton
            buyButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    buyButton.setCursor(Cursor.HAND);
                }
            });

            // Reset the cursor when the mouse exits the buyButton
            buyButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    buyButton.setCursor(Cursor.DEFAULT);
                }
            });

            marketPane.add(buyButton, 2, row);

            row++;
        }

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-font-family: 'Mali'"); // Set the style for closeButton
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                marketStage.close();
            }
        });

        // Set the cursor to hand when hovering over the closeButton
        closeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton.setCursor(Cursor.HAND);
            }
        });

        // Reset the cursor when the mouse exits the closeButton
        closeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                closeButton.setCursor(Cursor.DEFAULT);
            }
        });

        marketPane.add(closeButton, 2, row);

        // Initialize the upgrades HashMap
        upgrades = new HashMap<>();
        upgrades.put("Faster Growth", new Upgrade("Faster Growth", 20));
        upgrades.put("Increased Yield", new Upgrade("Increased Yield", 30));
        upgrades.put("AutoPlanter", new Upgrade("AutoPlanter", 100));
        upgrades.put("AutoBuy Seeds", new Upgrade("AutoBuy Seeds", 50)); // Add the "AutoBuy Seeds" upgrade

//        // Add upgrades to the market pane
//        Label upgradeLabel = new Label("Upgrades");
//        upgradeLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-font-family: 'Mali'");
//        marketPane.add(upgradeLabel, 0, row);
//
//        row++;
//
//        for (String upgradeName : upgrades.keySet()) {
//            Upgrade upgrade = upgrades.get(upgradeName);
//
//            Label upgradeNameLabel = new Label(upgradeName);
//            upgradeNameLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");
//            marketPane.add(upgradeNameLabel, 0, row);
//
//            Label upgradeCostLabel = new Label("$" + upgrade.getCost());
//            upgradeCostLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");
//            marketPane.add(upgradeCostLabel, 1, row);
//
//            Button upgradeButton = new Button("Upgrade");
//            upgradeButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14;");
//
//            upgradeButton.setOnAction(e -> {
//                if (player.getMoney() >= upgrade.getCost()) {
//                    player.setMoney(player.getMoney() - upgrade.getCost());
//                    upgrade.incrementLevel();
//                    upgrade.setCost(upgrade.getCost() * 2);
//                    upgradeCostLabel.setText("$" + upgrade.getCost());
//                    game.updateLabels(player);
//
//                    if (upgradeName.equals("AutoBuy Seeds")) {
//                        autobuySeeds();
//                    }
//
//                    upgradePurchased(); // Move the upgradePurchased() call outside the if condition
//                }
//            });
//
//
//            // Set the cursor to hand when hovering over the upgradeButton
//            upgradeButton.setOnMouseEntered(e -> upgradeButton.setCursor(Cursor.HAND));
//            upgradeButton.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");
//
//            // Reset the cursor when the mouse exits the upgradeButton
//            upgradeButton.setOnMouseExited(e -> upgradeButton.setCursor(Cursor.DEFAULT));
//            upgradeButton.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");
//
//            marketPane.add(upgradeButton, 2, row);
//
//            row++;
//        }
//    }
    }

    public void autobuySeeds() {
        List<String> seedTypes = new ArrayList<>(seedPrices.keySet());
        Random random = new Random();
        while (player.getMoney() > 0) {
            String randomSeed = seedTypes.get(random.nextInt(seedTypes.size()));
            int seedPrice = seedPrices.get(randomSeed);
            if (player.getMoney() >= seedPrice) {
                player.buySeed(randomSeed, seedPrice);
            } else {
                break;
            }
        }
    }

    public void setOnUpgradePurchased(Runnable onUpgradePurchased) {
        this.onUpgradePurchased = onUpgradePurchased;
    }

    // Call this method after purchasing an upgrade
    private void upgradePurchased() {
        if (onUpgradePurchased != null) {
            onUpgradePurchased.run();
        }}

    public int getPrice(String cropType) {
        // Assuming you have a HashMap<String, Integer> called seedPrices in your Market class
        // that stores the seed type as a key and its price as a value
        if (seedPrices.containsKey(cropType)) {
            return seedPrices.get(cropType) * 2;
        }
        return 0; // Return 0 if the crop type is not found in the market
    }


    public GridPane getMarketPane() {
        return marketPane;
    }

    public HashMap<String, Integer> getSeedPrices() {
        return seedPrices;
    }

    public void setSeedPrices(HashMap<String, Integer> seedPrices) {
        this.seedPrices = seedPrices;
    }

    public HashMap<String, Upgrade> getUpgrades() {
        return upgrades;
    }


}
