package com.example.demo;

import java.util.HashMap;

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
        marketLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;"); // Set the style for marketLabel
        marketPane.add(marketLabel, 0, 0, 3, 1); // Span the marketLabel across 3 columns

        Label seedLabel = new Label("Seeds");
        seedLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;"); // Set the style for seedLabel
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
            nameLabel.setStyle("-fx-font-size: 16;"); // Set the style for nameLabel
            marketPane.add(nameLabel, 0, row);

            Label priceLabel = new Label("$" + seedPrices.get(seed));
            priceLabel.setStyle("-fx-font-size: 16;"); // Set the style for priceLabel
            marketPane.add(priceLabel, 1, row);


            Button buyButton = new Button("Buy");
            buyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14;"); // Set the style for buyButton
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
        closeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14;"); // Set the style for closeButton
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
