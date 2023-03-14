package com.example.demo;

import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IdleFarmingGame extends Application {

    Player player = new Player(100, 0);
    private Label moneyLabel = new Label("Money: $" + player.getMoney());
    private Label seedLabel = new Label("Seeds: " + player.getNumSeeds());
    private Label cropLabel = new Label("Crops: " + player.getCrops());

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create the market UI
        Stage marketStage = new Stage();
        Market market = new Market(player, marketStage, this);

        Scene marketScene = new Scene(market.getMarketPane());
        marketStage.setScene(marketScene);

        // Create buttons to open the market UI and buy seeds
        Button marketButton = new Button("Market");
        marketButton.setOnAction(e -> marketStage.show());

        Button buySeedButton = new Button("Buy Seed");
        buySeedButton.setOnAction(e -> buySeed(player, market));

        // Create a button to plant a seed
        Button plantButton = new Button("Plant Seed");
        plantButton.setOnAction(e -> plantSeed(player));

        // Create a HBox to hold the buttons
        HBox buttonBox = new HBox(10, buySeedButton, plantButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create a VBox to hold the labels and buttons
        VBox root = new VBox(10, moneyLabel, seedLabel, cropLabel, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(400, 400);

        // Create a Scene and set it on the primary stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add the market button to the VBox
        root.getChildren().add(marketButton);
    }

    private void buySeed(Player player, Market market) {
        HashMap<String, Integer> seedPrices = market.getSeedPrices();
        String seedType = "Carrot"; // can be changed to any other seed type
        if (player.getMoney() >= seedPrices.get(seedType)) {
            player.buySeed(seedType, seedPrices.get(seedType));
            updateLabels(player);
        }
    }

    private void plantSeed(Player player) {
        HashMap<String, Integer> seeds = player.getSeeds();
        String seedType = seeds.keySet().stream().filter(type -> seeds.get(type) > 0).findFirst().orElse(null);
        if (seedType != null) {
            seeds.put(seedType, seeds.get(seedType) - 1);
            updateLabels(player);
            Label plantedLabel = new Label("Planted: " + seedType);
            VBox root = (VBox) moneyLabel.getParent();
            root.getChildren().add(0, plantedLabel); // Add the label at the top

            // Add an ImageView for the planted crop
            ImageView cropImage = new ImageView(new Image("file:src/main/resources/" + seedType + ".png"));
            cropImage.setFitWidth(50);
            cropImage.setFitHeight(50);
            root.getChildren().add(1, cropImage); // Add the image at the top

            // Add a progress bar to show growth time
            ProgressBar progressBar = new ProgressBar();
            progressBar.setProgress(0.0);
            root.getChildren().add(2, progressBar); // Add the progress bar at the top

            // Initialize the Timeline object
            Timeline timeline = new Timeline();

            // Create a Timeline to update the progress bar
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
                double progress = progressBar.getProgress();
                if (progress < 1.0) {
                    progressBar.setProgress(progress + 1.0/30.0);
                } else {
                    player.addCrop(seedType);
                    updateLabels(player);
                    root.getChildren().remove(plantedLabel);
                    root.getChildren().remove(cropImage);
                    root.getChildren().remove(progressBar);

                    // Add an ImageView for the harvested crop
                    ImageView harvestedImage = new ImageView(new Image("file:src/main/resources/" + seedType + ".png"));
                    harvestedImage.setFitWidth(50);
                    harvestedImage.setFitHeight(50);
                    root.getChildren().add(0, harvestedImage); // Add the harvested image at the top
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), harvestedImage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setOnFinished(f -> root.getChildren().remove(harvestedImage));
                    fadeTransition.play();

                    timeline.stop();
                }
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }












    public void updateLabels(Player player) {
        moneyLabel.setText("Money: $" + player.getMoney());
        seedLabel.setText("Seeds:");
        for (String seedType : player.getSeeds().keySet()) {
            seedLabel.setText(seedLabel.getText() + " " + seedType + ": " + player.getSeeds().get(seedType));
        }
        cropLabel.setText("Crops:");
        for (String cropType : player.getCrops().keySet()) {
            cropLabel.setText(cropLabel.getText() + " " + cropType + ": " + player.getCrops().get(cropType));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
