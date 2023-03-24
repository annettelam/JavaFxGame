package com.example.demo;

import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IdleFarmingGame extends Application {

    Player player = new Player(100, 0);
    private Label moneyLabel = new Label("Money: $" + player.getMoney());
    private Label seedLabel = new Label("Seeds: " + player.getNumSeeds());
    private Label cropLabel = new Label("Crops: " + player.getCrops());

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Set the style for moneyLabel, seedLabel, and cropLabel
        moneyLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        seedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        cropLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Create the market UI
        Stage marketStage = new Stage();
        Market market = new Market(player, marketStage, this);

        Scene marketScene = new Scene(market.getMarketPane());
        marketStage.setScene(marketScene);

        // Create an ImageView for the market GIF
        ImageView marketGif = new ImageView(new Image(getClass().getResource("/market.gif").toExternalForm()));
        marketGif.setFitWidth(100);
        marketGif.setFitHeight(100);

        // Add a mouse click event listener to the market GIF
        marketGif.setOnMouseClicked(e -> marketStage.show());

        // Add hover animation to the market GIF
        marketGif.setOnMouseEntered(e -> marketGif.setScaleX(1.1));
        marketGif.setOnMouseExited(e -> marketGif.setScaleX(1.0));

        // Create a label for the market GIF title
        Label marketTitle = new Label("Market");
        marketTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create a VBox to hold the market GIF and its title
        VBox marketBox = new VBox(5, marketTitle, marketGif);
        marketBox.setAlignment(Pos.CENTER);

        // Add padding and a border to the marketBox
        marketBox.setStyle("-fx-padding: 10 20 10 20; -fx-border-width: 3; -fx-border-color: black;");

        // Add hover animation and change cursor to the market GIF
        marketGif.setOnMouseEntered(e -> {
            marketGif.setScaleX(1.1);
            marketGif.setCursor(Cursor.HAND);
        });
        marketGif.setOnMouseExited(e -> {
            marketGif.setScaleX(1.0);
            marketGif.setCursor(Cursor.DEFAULT);
        });

        // Create a VBox with a fixed height to wrap the marketBox
        VBox marketWrapper = new VBox(marketBox);
        marketWrapper.setPrefHeight(500); // Set the height to match the grid height
        marketWrapper.setAlignment(Pos.TOP_CENTER);

        // Add a title label with a larger font size
        Label titleLabel = new Label("Harvest Hero");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        // Add a GIF image with larger dimensions
        ImageView gifImage = new ImageView(new Image(getClass().getResource("/harvesthero.gif").toExternalForm()));
        gifImage.setFitWidth(300);
        gifImage.setFitHeight(300);

        // Create a grid for planting seeds
        GridPane grid = createGrid(3, 100);

        // Create a VBox to hold the title, GIF image, labels, and grid
        VBox content = new VBox(10, titleLabel, gifImage, moneyLabel, seedLabel, cropLabel, grid);
        content.setAlignment(Pos.CENTER);

        // Create an HBox to hold the content and marketWrapper with some spacing between them
        HBox centeredContent = new HBox(20, content, marketWrapper);
        centeredContent.setAlignment(Pos.CENTER);

        // Create a StackPane to hold the centeredContent and maintain the center position when resizing the window
        StackPane root = new StackPane(centeredContent);
        root.setStyle("-fx-background-color: white");

        // Create a Scene and set it on the primary stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private GridPane createGrid(int gridSize, int cellSize) {
        GridPane grid = new GridPane();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);
                cell.setStyle("-fx-border-color: black");
                cell.setCursor(Cursor.HAND); // Set the cursor to a hand when hovering over a grid cell
                cell.setOnMouseClicked(e -> plantSeed(player, cell));
                grid.add(cell, i, j);
            }
        }
        return grid;
    }



    private void plantSeed(Player player, StackPane cell) {
        HashMap<String, Integer> seeds = player.getSeeds();
        String seedType = seeds.keySet().stream().filter(type -> seeds.get(type) > 0).findFirst().orElse(null);
        if (seedType != null) {
            seeds.put(seedType, seeds.get(seedType) - 1);
            updateLabels(player);

            // Add an ImageView for the planted crop
            ImageView cropImage = new ImageView(new Image("file:src/main/resources/" + seedType + ".png"));
            cropImage.setFitWidth(50);
            cropImage.setFitHeight(50);

            // Add a progress bar to show growth time
            ProgressBar progressBar = new ProgressBar();
            progressBar.setProgress(0.0);
            progressBar.setMaxWidth(50);

            // Add ImageView and ProgressBar to a VBox
            VBox plantedCrop = new VBox(cropImage, progressBar);
            plantedCrop.setAlignment(Pos.CENTER);
            cell.getChildren().clear();
            cell.getChildren().add(plantedCrop); // Add the VBox to the cell

            // Initialize the Timeline object
            Timeline timeline = new Timeline();

            // Create a Timeline to update the progress bar
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
                double progress = progressBar.getProgress();
                if (progress < 1.0) {
                    progressBar.setProgress(progress + 1.0 / 10.0);
                } else {
                    player.addCrop(seedType);
                    updateLabels(player);
                    cell.getChildren().remove(plantedCrop);

                    // Add an ImageView for the harvested crop
                    ImageView harvestedImage = new ImageView(new Image("file:src/main/resources/" + seedType + ".png"));
                    harvestedImage.setFitWidth(50);
                    harvestedImage.setFitHeight(50);
                    cell.getChildren().add(harvestedImage); // Add the harvested image to the cell
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), harvestedImage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setOnFinished(f -> cell.getChildren().remove(harvestedImage));
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

        if (player.getCrops().isEmpty()) {
            cropLabel.setText("Crops: 0");
        } else {
            cropLabel.setText("Crops:");
            for (String cropType : player.getCrops().keySet()) {
                cropLabel.setText(cropLabel.getText() + " " + cropType + ": " + player.getCrops().get(cropType));
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


}