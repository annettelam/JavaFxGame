package com.example.demo;

import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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

    public static Market market;

    Player player = new Player(1000, 0);
    private Label moneyLabel = new Label("Money: $" + player.getMoney());
    private Label seedLabel = new Label("Seeds: " + player.getNumSeeds());
    private Label cropLabel = new Label("Crops: " + player.getCrops());
    Label playerInfoLabel = new Label("Player Info");


    private VBox inventoryLayout;
    private HashMap<String, Label> inventoryLabels = new HashMap<>();



    private HashMap<String, Animal> animals = new HashMap<>(); // Animal type -> Animal object
    private HashMap<String, Label> animalLabels = new HashMap<>(); // Animal type -> Label object


    // Create labels for the stats
    Label increasedYieldLabel = new Label("Increased Yield Level: 0");
    Label fasterGrowthLabel = new Label("Faster Growth Level: 0");
    Label growthPercentageLabel = new Label("Crops Grow 0.00% Faster");

    Label autoPlanterLabel = new Label("AutoPlanter Level: 0");


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Set the style for moneyLabel, seedLabel, and cropLabel
        moneyLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        seedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        cropLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Style the labels
        increasedYieldLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        fasterGrowthLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        growthPercentageLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        autoPlanterLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        playerInfoLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");


        inventoryLayout = createInventory(player);


        // Create a VBox to hold the stats labels
        VBox statsLayout = new VBox(10);
        statsLayout.getChildren().addAll(increasedYieldLabel, fasterGrowthLabel, growthPercentageLabel, autoPlanterLabel);
        statsLayout.setAlignment(Pos.TOP_CENTER);

// Add background color, padding, and a border to the statsLayout
        statsLayout.setStyle("-fx-background-color: #F0F8FF; -fx-padding: 10;");

        statsLayout.getChildren().add(0, playerInfoLabel);
        // Create an ImageView for the barn image
        ImageView barnImage = new ImageView(new Image(getClass().getResource("/barn.png").toExternalForm()));
        barnImage.setFitWidth(100);
        barnImage.setFitHeight(100);

// Add a mouse click event listener to the barn image
        barnImage.setOnMouseClicked(e -> {
            // TODO: Implement code to handle buying animals from the barn
        });

// Add hover animation to the barn image
        barnImage.setOnMouseEntered(e -> barnImage.setScaleX(1.1));
        barnImage.setOnMouseExited(e -> barnImage.setScaleX(1.0));

// Create a label for the barn title
        Label barnTitle = new Label("Barn");
        barnTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

// Create a VBox to hold the barn image and its title
        VBox barnBox = new VBox(5, barnTitle, barnImage);
        barnBox.setAlignment(Pos.CENTER);

// Add padding and a border to the barnBox
        barnBox.setStyle("-fx-padding: 10 20 10 20; -fx-border-width: 3; -fx-border-color: black;");

// Add hover animation and change cursor to the barn image
        barnImage.setOnMouseEntered(e -> {
            barnImage.setScaleX(1.1);
            barnImage.setCursor(Cursor.HAND);
        });
        barnImage.setOnMouseExited(e -> {
            barnImage.setScaleX(1.0);
            barnImage.setCursor(Cursor.DEFAULT);
        });


        // Create the market UI

        Stage marketStage = new Stage();
        market = new Market(player, marketStage, this);

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

        // Call autoPlant() every 5 seconds
        Timeline autoPlantTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> autoPlant(grid, market))
        );
        autoPlantTimeline.setCycleCount(Animation.INDEFINITE);
        autoPlantTimeline.play();

        // Add the VBox to the layout
        VBox content = new VBox(10, statsLayout, titleLabel, gifImage, moneyLabel, seedLabel, cropLabel, grid);
        content.setAlignment(Pos.CENTER);

        // Create an HBox to hold the content and marketWrapper with some spacing between them
        VBox inventoryLayout = createInventory(player);
        HBox centeredContent = new HBox(20, statsLayout, content, inventoryLayout, marketWrapper);

        HBox marketAndBarnBox = new HBox(20, marketBox, barnBox);
        marketAndBarnBox.setAlignment(Pos.CENTER);

        VBox marketAndBarnWrapper = new VBox(marketAndBarnBox);
        marketAndBarnWrapper.setPrefHeight(500); // Set the height to match the grid height
        centeredContent.getChildren().add(marketAndBarnWrapper);

        centeredContent.setAlignment(Pos.CENTER);

        // Create a StackPane to hold the centeredContent and maintain the center position when resizing the window
        StackPane root = new StackPane(centeredContent);
        root.setStyle("-fx-background-color: white");

        // Create a Scene and set it on the primary stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Update the stats labels whenever an upgrade is purchased
        market.setOnUpgradePurchased(() -> updateStatsLabels(market, increasedYieldLabel, fasterGrowthLabel, growthPercentageLabel, autoPlanterLabel));

    }


    private GridPane createGrid(int gridSize, int cellSize) {
        GridPane grid = new GridPane();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);
                cell.setStyle("-fx-border-color: black");
                cell.setCursor(Cursor.HAND); // Set the cursor to a hand when hovering over a grid cel
                cell.setOnMouseClicked(e -> plantSeed(player, cell, market));
                grid.add(cell, i, j);
            }
        }
        return grid;
    }

    private void autoPlant(GridPane grid, Market market) {
        if (market.getUpgrades().get("AutoPlanter").getLevel() > 0) {
            for (Node cell : grid.getChildren()) {
                StackPane stackPaneCell = (StackPane) cell;
                if (stackPaneCell.getChildren().isEmpty()) {
                    plantSeed(player, stackPaneCell, market);
                }
            }
        }
    }


    private void plantSeed(Player player, StackPane cell, Market market) {
        HashMap<String, Integer> seeds = player.getSeeds();
        String seedType = seeds.keySet().stream().filter(type -> seeds.get(type) > 0).findFirst().orElse(null);

        if (seedType != null) {
            seeds.put(seedType, seeds.get(seedType) - 1);
            updateLabels(player);

            // Access the upgrade levels for "Increased Yield" and "Faster Growth"
            int increasedYieldLevel = market.getUpgrades().get("Increased Yield").getLevel();
            int fasterGrowthLevel = market.getUpgrades().get("Faster Growth").getLevel();

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

                // Modify the growth progress increment based on the "Faster Growth" upgrade level
                double progressIncrement = 1.0 / (10.0 - fasterGrowthLevel); // Adjust the denominator to control the growth speed

                if (progress < 1.0) {
                    progressBar.setProgress(progress + progressIncrement);
                } else {
                    // Add extra crops to the player based on the "Increased Yield" upgrade level when the seed is done being planted
                    for (int i = 0; i < 1 + increasedYieldLevel; i++) {
                        player.addCrop(seedType);
                    }
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

    private VBox createInventory(Player player) {
        inventoryLayout = new VBox(10);
        Label inventoryTitle = new Label("Inventory");
        inventoryTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        inventoryLayout.getChildren().add(inventoryTitle);

        for (String cropType : player.getCrops().keySet()) {
            HBox cropRow = new HBox(5);
            Image cropImage = new Image("file:src/main/resources/" + cropType.substring(0, 1).toUpperCase() + cropType.substring(1).toLowerCase() + ".png");
            ImageView cropImageView = new ImageView(cropImage);
            cropImageView.setFitWidth(30);
            cropImageView.setFitHeight(30);
            Label cropAmount = new Label(cropType + ": " + player.getCrops().get(cropType));
            inventoryLabels.put(cropType, cropAmount);
            cropRow.getChildren().addAll(cropImageView, cropAmount);
            inventoryLayout.getChildren().add(cropRow);
        }

        return inventoryLayout;
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

        for (String cropType : player.getCrops().keySet()) {
            String capitalizedCropType = cropType.substring(0, 1).toUpperCase() + cropType.substring(1);
            Label cropAmount = inventoryLabels.getOrDefault(cropType, null);
            if (cropAmount != null) {
                cropAmount.setText(cropType + ": " + player.getCrops().get(cropType));
            } else {
                HBox cropRow = new HBox(5);
                Image cropImage = new Image("file:src/main/resources/" + cropType.substring(0, 1).toUpperCase() + cropType.substring(1).toLowerCase() + ".png");

                ImageView cropImageView = new ImageView(cropImage);
                cropImageView.setFitWidth(30);
                cropImageView.setFitHeight(30);
                cropAmount = new Label(cropType + ": " + player.getCrops().get(cropType));
                inventoryLabels.put(cropType, cropAmount);
                cropRow.getChildren().addAll(cropImageView, cropAmount);
                inventoryLayout.getChildren().add(cropRow);
            }
        }



    }


    private void updateStatsLabels(Market market, Label increasedYieldLabel, Label fasterGrowthLabel, Label growthPercentageLabel, Label autoPlanterLabel) {
        int increasedYieldLevel = market.getUpgrades().get("Increased Yield").getLevel();
        int fasterGrowthLevel = market.getUpgrades().get("Faster Growth").getLevel();
        int autoPlanterLevel = market.getUpgrades().get("AutoPlanter").getLevel();
        double growthPercentage = (1 - (10.0 - fasterGrowthLevel) / 10.0) * 100;

        increasedYieldLabel.setText("Increased Yield Level: " + increasedYieldLevel);
        fasterGrowthLabel.setText("Faster Growth Level: " + fasterGrowthLevel);
        growthPercentageLabel.setText("Crops Grow " + String.format("%.2f", growthPercentage) + "% Faster");
        autoPlanterLabel.setText("AutoPlanter Level: " + autoPlanterLevel);
    }


    public static void main(String[] args) {
        launch(args);
    }


}