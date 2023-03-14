package com.example.demo;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Market {

    private GridPane marketPane;
    private Player player;
    private Stage marketStage;
    private HashMap<String, Integer> seedPrices;

    public Market(Player player, Stage marketStage, IdleFarmingGame game) {
        this.player = player;
        this.marketStage = marketStage;
        marketPane = new GridPane();
        marketPane.setPrefSize(400, 300); // Set the preferred size of the market pane

        Label marketLabel = new Label("Market");
        marketPane.add(marketLabel, 0, 0);

        Label seedLabel = new Label("Seeds");
        marketPane.add(seedLabel, 0, 1);

        // Initialize the seedPrices HashMap
        seedPrices = new HashMap<String, Integer>();
        seedPrices.put("Carrot", 5);
        seedPrices.put("Tomato", 8);
        seedPrices.put("Corn", 10);

        // Display the seed prices in the market pane
        int row = 2;
        for (String seed : seedPrices.keySet()) {
            Label nameLabel = new Label(seed);
            marketPane.add(nameLabel, 0, row);

            Label priceLabel = new Label("$" + seedPrices.get(seed));
            marketPane.add(priceLabel, 1, row);

            Button buyButton = new Button("Buy");
            buyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    int seedPrice = seedPrices.get(seed);
                    player.buySeed(seed, seedPrice);
                    game.updateLabels(player); // call updateLabels() using the game instance
                }
            });
            marketPane.add(buyButton, 2, row);

            row++;
        }

        Button closeButton = new Button("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                marketStage.close();
            }
        });
        marketPane.add(closeButton, 3, 1);
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
}
