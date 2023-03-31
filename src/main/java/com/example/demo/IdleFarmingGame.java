package com.example.demo;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javafx.geometry.Insets;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.nio.file.StandardCopyOption;


import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.paint.Color.rgb;

public class IdleFarmingGame extends Application {

    public static Market market;

    public static UpgradeMarket upgradeMarket;

    private Scene upgradeScene;

    private MediaPlayer mediaPlayer;


    Player player = new Player(10000, 0);
    private Label moneyLabel = new Label("Money \uD83D\uDCB0: $" + player.getMoney());
    private Label seedLabel = new Label("Seeds \uD83C\uDF31: " + player.getNumSeeds());
    private Label cropLabel = new Label("Crops: " + player.getCrops());
    Label playerInfoLabel = new Label("Upgrades Info");

    // Create a label for the upgrades GIF title
    Label upgradesTitle = new Label("Upgrades");


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

        playMusic("/verdantgrove.wav");

        // Load the default font for the game
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/Mali.ttf")).toExternalForm(), 12);

        // Create UI elements for start game screen
        Label startGameLabel = new Label("Welcome to Harvest Hero!");
        startGameLabel.setStyle("-fx-font-size: 55px; -fx-font-weight: bold; -fx-font-family: 'Mali'; -fx-border-color: MEDIUMAQUAMARINE; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: linear-gradient(from 25% 55% to 80% 90%, rgba(0, 153, 76, 0.8), rgba(204, 255, 204, 0.8)); -fx-background-radius: 5px; -fx-border-padding: 5px; -fx-padding: 20px;");

        Label titleGameLabel = new Label("COMP 2522 Term Project by Annette Lam & Cadan Glass");
        titleGameLabel.setStyle("-fx-font-size: 15px; -fx-font-family: 'Mali'; -fx-background-color: linear-gradient(from 25% 55% to 80% 90%, LIGHTSTEELBLUE, aliceblue); -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: darkslategrey; -fx-border-width: 1px;");
        titleGameLabel.setWrapText(true);

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        startButton.setOnAction(e -> {
            playClickSound("/click.mp3");
            primaryStage.show();
        });

        Button exitButton = new Button("Exit Game");
        exitButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        exitButton.setOnAction(e -> {
            playClickSound("/click.mp3");
            System.exit(0);
        });

        // Create a VBox to hold the start game label and buttons
        VBox startGameLayout = new VBox(10, startGameLabel, startButton, exitButton, titleGameLabel);
        startGameLayout.setAlignment(Pos.CENTER);
        startGameLayout.setStyle("-fx-background-image: url('startscreenbkg.jpeg'); -fx-background-size: cover;");

        // Create a Scene for the start game screen and set it on a new Stage
        Scene startGameScene = new Scene(startGameLayout, 800, 600);
        Stage startGameStage = new Stage();
        startGameStage.setScene(startGameScene);
        startGameStage.show();
        // Load the background image for the game from the resources folder
        Image backgroundImage = new Image(getClass().getResource("/background.jpeg").toExternalForm());

        // Create a background image for the game
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        // Set the style for moneyLabel, seedLabel, and cropLabel
        moneyLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        seedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        cropLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Mali';");

        // Style the labels
        increasedYieldLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        fasterGrowthLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        growthPercentageLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        autoPlanterLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        playerInfoLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        upgradesTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali'");


        inventoryLayout = createInventory(player);

        // Create a VBox to hold the stats labels
        VBox statsLayout = new VBox(10);
        statsLayout.getChildren().addAll(increasedYieldLabel, fasterGrowthLabel, growthPercentageLabel, autoPlanterLabel);
        statsLayout.setAlignment(Pos.TOP_CENTER);
        statsLayout.setStyle("-fx-font-size: 18px; -fx-font-family: 'Mali';");

        Map<String, ImageView> animalImages = new HashMap<>();
        animalImages.put("Chicken", new ImageView(new Image(getClass().getResource("/chicken.jpg").toExternalForm())));
        animalImages.put("Pig", new ImageView(new Image(getClass().getResource("/pig.jpg").toExternalForm())));
        animalImages.put("Cow", new ImageView(new Image(getClass().getResource("/cow.jpg").toExternalForm())));


        // Add background color, padding, and a border to the statsLayout
        statsLayout.setStyle("-fx-background-color: #F0F8FF; -fx-padding: 10; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: DARKSLATEGREY; -fx-border-width: 1px;");

        statsLayout.getChildren().add(0, playerInfoLabel);


        // Create a label for the barn title
        Label barnTitle = new Label("Barn");
        barnTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali';");

        // Create an ImageView for the barn image
        ImageView barnImage = new ImageView(new Image(getClass().getResource("/barn.png").toExternalForm()));
        barnImage.setOpacity(0.5);
        barnImage.setFitWidth(200);
        barnImage.setFitHeight(200);


        // Round out the corners of the barn image
        Rectangle clip = new Rectangle(barnImage.getFitWidth(), barnImage.getFitHeight());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        barnImage.setClip(clip);

        // Create a drop shadow effect for the barn image
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5);
        shadow.setOffsetY(5);
        shadow.setColor(Color.NAVY);
        barnImage.setEffect(shadow);


        // Set the background color and border of the barn image
        barnImage.setStyle("-fx-background-color: skyblue; -fx-border-color: goldenrod; -fx-border-width: 5px;");

        // Change barn button to opaque when clicked
        barnImage.setOnMousePressed(e -> barnImage.setOpacity(1.0));

        // Add a mouse click event listener to the barn image
        VBox barnBox = new VBox(5, barnTitle, barnImage);
        barnBox.setAlignment(Pos.CENTER);

        // Add padding and a border to the barnBox
        barnBox.setStyle("-fx-padding: 10 20 10 20;");

        // Add hover animation to the barn image
        barnImage.setOnMouseEntered(e -> barnImage.setScaleX(1.1));
        barnImage.setOnMouseExited(e -> barnImage.setScaleX(1.0));

        // Change barn button to opaque when clicked
        barnImage.setOnMousePressed(e -> {
            playClickSound("/click.mp3");
            barnImage.setOpacity(1.0);
        });

        // Add a mouse click event listener to the barn image
        barnImage.setOnMouseClicked(e -> {
            playClickSound("/click.mp3");

            // Create a dialog to buy animals
            Dialog<Animal> buyAnimalDialog = new Dialog<>();
            buyAnimalDialog.setTitle("Buy Animals");
            buyAnimalDialog.setHeaderText("Select an animal to buy");

            // Create a list of animals to buy
            ListView<Animal> animalListView = new ListView<>();
            animalListView.getItems().addAll(
                    new Animal("Chicken", "Egg", 100, "/chicken.jpg"),
                    new Animal("Pig", "Meat", 300, "/pig.jpg"),
                    new Animal("Cow", "Milk", 500, "/cow.jpg"));



            // Create a cell factory to format each Animal object with its name and price
                    animalListView.setCellFactory(param -> new ListCell<Animal>() {
                        @Override
                        protected void updateItem(Animal animal, boolean empty) {
                            super.updateItem(animal, empty);

                            if (empty || animal == null) {
                                setText(null);
                            } else {
                                setText(animal.getName() + " - $" + animal.getCost());
                            }
                        }
                    });
                    


            buyAnimalDialog.getDialogPane().setContent(animalListView);
            buyAnimalDialog.getDialogPane().setStyle("-fx-font-size: 18px; -fx-font-family: 'Mali';");

            // Add a buy button to the dialog
            ButtonType buyButtonType = new ButtonType("Buy", ButtonBar.ButtonData.OK_DONE);
            buyAnimalDialog.getDialogPane().getButtonTypes().addAll(buyButtonType, ButtonType.CANCEL);

            // Handle the result of the dialog
            buyAnimalDialog.setResultConverter(buttonType -> {
                if (buttonType == buyButtonType) {
                    Animal selectedAnimal = animalListView.getSelectionModel().getSelectedItem();
                    if (player.getMoney() >= selectedAnimal.getCost()) {
                        player.setMoney(player.getMoney() - selectedAnimal.getCost());
                        updateMoneyLabel();
                        if (!animals.containsKey(selectedAnimal.getType())) {
                            animals.put(selectedAnimal.getType(), selectedAnimal);
                            Label animalLabel = new Label(selectedAnimal.getType() + ": 1");
                            animalLabels.put(selectedAnimal.getType(), animalLabel);

                            // Load the animal's image
                            ImageView animalImage = new ImageView(new Image(getClass().getResource(selectedAnimal.getImagePath()).toExternalForm()));
                            animalImage.setFitWidth(30);
                            animalImage.setFitHeight(30);

                            // Create an HBox for the animal row
                            HBox animalRow = new HBox(5);
                            animalRow.getChildren().addAll(animalImage, animalLabel);

                            // Add the animalRow to the inventoryLayout
                            inventoryLayout.getChildren().add(animalRow);
                        } else {
                            int currentCount = Integer.parseInt(animalLabels.get(selectedAnimal.getType()).getText().split(": ")[1]);
                            animalLabels.get(selectedAnimal.getType()).setText(selectedAnimal.getType() + ": " + (currentCount + 1));
                        }


                        recentAnimalTypes.add(selectedAnimal.getType()); // Add the animal type to the list
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Not Enough Money");
                        alert.setHeaderText("You don't have enough money to buy this animal!");
                        alert.showAndWait();
                    }
                    return selectedAnimal;
                }
                return null;
            });


            buyAnimalDialog.showAndWait();
        });


        // Create the market UI
        Stage marketStage = new Stage();
        market = new Market(player, marketStage, this);
        
        Scene marketScene = new Scene(market.getMarketPane());
        marketStage.setScene(marketScene);

        Stage upgradeStage = new Stage();
        upgradeMarket = new UpgradeMarket(player, upgradeStage, this);

        // create the scene for the upgrade market
        upgradeScene = new Scene(upgradeMarket.getUpgradePane(), 400, 300);

        // Create an ImageView for the market GIF
        ImageView marketGif = new ImageView(new Image(getClass().getResource("/market.gif").toExternalForm()));

        // Set the background color of the market GIF to sky blue
        marketGif.setStyle("-fx-border-color: mediumseagreen; -fx-border-width: 5px;");
        marketGif.setOpacity(0.5);
        marketGif.setFitWidth(200);
        marketGif.setFitHeight(200);

        // Change market button to opaque when clicked
        marketGif.setOnMousePressed(e -> {
            playClickSound("/click.mp3");
            marketGif.setOpacity(1.0);
        });

        // Add a mouse click event listener to the market GIF
        marketGif.setOnMouseClicked(e -> {
            playClickSound("/click.mp3");
            marketStage.show();
        });

        // Add hover animation to the market GIF
        marketGif.setOnMouseEntered(e -> marketGif.setScaleX(1.1));
        marketGif.setOnMouseExited(e -> marketGif.setScaleX(1.0));

        // Round out the corners of the market GIF
        Rectangle clip2 = new Rectangle(marketGif.getFitWidth(), marketGif.getFitHeight());
        clip2.setArcWidth(30);
        clip2.setArcHeight(30);
        marketGif.setClip(clip2);

        // Add a shadow effect to the market GIF
        DropShadow shadow2 = new DropShadow();
        shadow2.setOffsetX(5);
        shadow2.setOffsetY(5);
        shadow2.setColor(Color.NAVY);
        marketGif.setEffect(shadow2);

        // Create a label for the market GIF title
        Label marketTitle = new Label("Market");
        marketTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali'");

        // Create a VBox to hold the market GIF and its title
        VBox marketBox = new VBox(5, marketTitle, marketGif);
        marketBox.setAlignment(Pos.CENTER);

        // Add padding and a border to the marketBox
        marketBox.setStyle("-fx-padding: 10 20 10 20;");

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

        // Create a drop shadow effect with a 5 pixel radius
        DropShadow dropShadow = new DropShadow(5, Color.MEDIUMSEAGREEN);

        // Add a title label with a larger font size
        Label titleLabel = new Label("Harvest Hero");
        titleLabel.setStyle("-fx-font-size: 55px; -fx-font-weight: bold; -fx-font-family: 'Mali'; -fx-border-color: MEDIUMAQUAMARINE; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: linear-gradient(from 25% 55% to 80% 90%, rgba(0, 153, 76, 0.8), rgba(204, 255, 204, 0.8)); -fx-background-radius: 5px; -fx-border-padding: 5px; -fx-padding: 20px;");
        titleLabel.setTextFill(rgb(0,51,25));
        titleLabel.setEffect(dropShadow);

        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 0)); // Adjust the negative value to control the titleLabel position

        // Create a grid for planting seeds
        GridPane grid = createGrid(4, 100);

        // Create a grid for animals
        GridPane animalGrid = createAnimalGrid(4, 100);

        // Call autoPlant() every 5 seconds
        Timeline autoPlantTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> autoPlant(grid, market))
        );
        autoPlantTimeline.setCycleCount(Animation.INDEFINITE);
        autoPlantTimeline.play();

        VBox cropContent = new VBox(10, new Label("Crops Farm"), grid);
        cropContent.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        cropContent.setAlignment(Pos.CENTER);

        VBox animalContent = new VBox(10, new Label("Animal Farm"), animalGrid);
        animalContent.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
        animalContent.setAlignment(Pos.CENTER);

        HBox gridTitles = new HBox(20, cropContent, animalContent);
        gridTitles.setAlignment(Pos.CENTER);

        HBox moneyAndSeedsLabels = new HBox(20, moneyLabel, seedLabel);
        moneyAndSeedsLabels.setAlignment(Pos.CENTER);
        // Style the money and seeds labels with a rounded border
        moneyAndSeedsLabels.setStyle("-fx-background-color: rgba(240, 255, 240, 0.8); -fx-padding: 10; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: DARKSLATEGREY; -fx-border-width: 1px;");

        VBox content = new VBox(10, titleLabel, statsLayout, moneyAndSeedsLabels, gridTitles);
        content.setAlignment(Pos.CENTER);



        // Add a top margin to statsLayout to space it out from titleLabel
        VBox.setMargin(statsLayout, new Insets(20, 0, 0, 0));

        VBox inventoryLayout = createInventory(player);
        inventoryLayout.setMaxHeight(400);

        inventoryLayout.setStyle("-fx-background-color: white;");
        inventoryLayout.setStyle("-fx-background-radius: 10; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Mali'; -fx-padding: 10 20 10 20; -fx-border-radius: 10; -fx-border-color: darkgreen; -fx-border-width: 1px; -fx-background-color: rgba(255, 255, 255, 0.8);");


        // Load the merchant image
        Image merchantImage = new Image("file:src/main/resources/merchant.gif");
        ImageView merchantImageView = new ImageView(merchantImage);
        merchantImageView.setPreserveRatio(true);
        merchantImageView.setFitWidth(200);
        merchantImageView.setFitHeight(200);


        // Create the label
        Label merchantLabel = new Label("Merchant");
        merchantLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali'; -fx-text-fill: black");

        // Create the sell button
        Button sellInventoryButton = new Button("Sell Inventory");
        sellInventoryButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali'");
        sellInventoryButton.setOpacity(0.5);


        // Add a mouse click event listener to the sell button
        sellInventoryButton.setOnAction(event -> {
            playClickSound("/click.mp3");
            sellEntireInventory(player);
        });

        // Add hover animation to the sell button
        sellInventoryButton.setOnMouseEntered(e -> {
            sellInventoryButton.setOpacity(1.0);
            sellInventoryButton.setCursor(Cursor.HAND);
        });

        // Add the merchant label, image, and sell button to a VBox
        VBox merchantBox = new VBox(10, merchantLabel, merchantImageView, sellInventoryButton);

        merchantBox.setAlignment(Pos.CENTER);

        // Add the merchantBox to the main VBox
        VBox container = new VBox(10, merchantBox);
        container.setAlignment(Pos.CENTER);

        // Create an ImageView for the upgrades GIF
        ImageView upgradesGif = new ImageView(new Image(getClass().getResource("/upgrades.gif").toExternalForm()));


        upgradesGif.setOnMouseEntered(e -> {
            upgradesGif.setScaleX(1.1);
            upgradesGif.setCursor(Cursor.HAND);
        });

        upgradesGif.setOnMouseExited(e -> {
            upgradesGif.setScaleX(1.0);
            upgradesGif.setCursor(Cursor.DEFAULT);
        });

        upgradesGif.setOpacity(0.5);
        upgradesGif.setFitWidth(200);
        upgradesGif.setFitHeight(200);

        // Add click action to the upgradesGif image

        upgradesGif.setOnMouseClicked(e -> {

            if (e.getButton() == MouseButton.PRIMARY) {
                playClickSound("/click.mp3");
                upgradesGif.setScaleX(1.0);
                upgradesGif.setOpacity(1.0);
                openUpgradeMarket(upgradeMarket, upgradeScene); // Open the UpgradeMarket window when the upgrades.gif is clicked
            }
        });

        // Change upgrade button to opaque when clicked
        upgradesGif.setOnMousePressed(e -> upgradesGif.setOpacity(1.0));
        upgradesGif.setOpacity(0.5);
        upgradesGif.setFitWidth(200);
        upgradesGif.setFitHeight(200);

        // Set the background color of the upgrades GIF to sky blue
        upgradesGif.setStyle("-fx-border-color: goldenrod; -fx-border-width: 5px;");

        // Round out the corners of the upgrades GIF
        Rectangle clip3 = new Rectangle(upgradesGif.getFitWidth(), upgradesGif.getFitHeight());
        clip3.setArcWidth(30);
        clip3.setArcHeight(30);
        upgradesGif.setClip(clip3);

        // Add a shadow effect to the upgrades GIF
        DropShadow shadow3 = new DropShadow();
        shadow3.setOffsetX(3);
        shadow3.setOffsetY(3);
        shadow3.setColor(Color.MEDIUMSEAGREEN);
        upgradesGif.setEffect(shadow3);


        // Create a VBox to hold the upgrades GIF and its title
        VBox upgradesBox = new VBox(5, upgradesTitle, upgradesGif);
        upgradesBox.setAlignment(Pos.CENTER);

        // Add padding and a border to the upgradesBox
        upgradesBox.setStyle("-fx-padding: 10 20 10 20;");


        // Create a new scene with the container as the root
        Scene scene2 = new Scene(container, 800, 600);


        // Declare the marketAndBarnBox variable, which will be used to hold the market, barn, and merchant
        VBox marketBarnMerch = new VBox(10, marketBox, barnBox, merchantBox);
        marketBarnMerch.setAlignment(Pos.CENTER);

        // Add spacing at the top of the marketBarnMerchUpgrades VBox
        Region spacer = new Region();
        spacer.setPrefHeight(80); // Adjust this value to control the spacing

        VBox marketBarnMerchUpgrades = new VBox(10, spacer, marketBox, barnBox, upgradesBox, merchantBox);
        marketBarnMerchUpgrades.setAlignment(Pos.CENTER);

        VBox marketAndBarnWrapper = new VBox(marketBarnMerchUpgrades);
        marketAndBarnWrapper.setPrefHeight(500); // Set the height to match the grid height

        HBox centeredContent = new HBox(20, content, inventoryLayout, marketWrapper, marketAndBarnWrapper);
        centeredContent.setAlignment(Pos.CENTER);

        marketBarnMerch.setAlignment(Pos.CENTER);
        centeredContent.setAlignment(Pos.CENTER);


        // Create a StackPane to hold the centeredContent and maintain the center position when resizing the window
        StackPane root = new StackPane(centeredContent);

        // Set the background of the root node to the background image
        root.setBackground(new Background(background));

        // Create a Scene and set it on the primary stage
        Scene scene = new Scene(root, 800, 600);

        // Set up the game window to be maximized upon launch
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);

        // Update the stats labels whenever an upgrade is purchased
        upgradeMarket.setOnUpgradePurchased(() -> updateStatsLabels(upgradeMarket, increasedYieldLabel, fasterGrowthLabel, growthPercentageLabel, autoPlanterLabel));
    }

    private GridPane createGrid(int gridSize, int cellSize) {
        GridPane grid = new GridPane();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);
                cell.setStyle("-fx-background-color: white; -fx-border-color: DARKSLATEBLUE; -fx-border-radius: 5; -fx-border-width: 1;");
                cell.setOpacity(0.8);
                cell.setCursor(Cursor.HAND); // Set the cursor to a hand when hovering over a grid cell
                cell.setOnMouseClicked(e -> {

                    plantSeed(player, cell, market);
                });
                grid.add(cell, i, j);
            }
        }

        // Round the corners of the grid for consistency with the market and barn buttons
        Rectangle clip = new Rectangle(gridSize * cellSize, gridSize * cellSize);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        grid.setClip(clip);
        return grid;
    }

    public void openUpgradeMarket(UpgradeMarket upgradeMarket, Scene upgradeScene) {
        Stage upgradeStage = upgradeMarket.getStage();

        upgradeStage.setScene(upgradeScene);
        upgradeStage.show();
    }



    private void autoPlant(GridPane grid, Market market) {
        if (upgradeMarket.getUpgrades().get("AutoPlanter").getLevel() > 0) {
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
            int increasedYieldLevel = upgradeMarket.getUpgrades().get("Increased Yield").getLevel();
            int fasterGrowthLevel = upgradeMarket.getUpgrades().get("Faster Growth").getLevel();

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
        inventoryTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Mali'");

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
        moneyLabel.setText("Money \uD83D\uDCB0: $" + player.getMoney());
        seedLabel.setText("Seeds \uD83C\uDF31:");
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

    private List<String> recentAnimalTypes = new ArrayList<>();

    private GridPane createAnimalGrid(int gridSize, int cellSize) {
        GridPane animalGrid = new GridPane();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);
                cell.setStyle("-fx-background-color: white; -fx-border-color: DARKSLATEBLUE; -fx-border-radius: 5; -fx-border-width: 1;");
                cell.setOpacity(0.8);
                cell.setCursor(Cursor.HAND);
                cell.setOnMouseClicked(e -> {
                    if (!recentAnimalTypes.isEmpty()) {
                        String animalType = recentAnimalTypes.get(0);
                        placeAnimal(player, cell, market, animalType);
                        recentAnimalTypes.remove(0);
                    }
                });
                animalGrid.add(cell, i, j);
            }
        }
        return animalGrid;
    }


    private void placeAnimal(Player player, StackPane cell, Market market, String animalType) {
        if (!animals.containsKey(animalType) || Integer.parseInt(animalLabels.get(animalType).getText().split(": ")[1]) <= 0) {
            return;
        }

        // Decrement the animal count in the player's inventory and update the label
        int currentCount = Integer.parseInt(animalLabels.get(animalType).getText().split(": ")[1]);
        animalLabels.get(animalType).setText(animalType + ": " + (currentCount - 1));

        String productType = animals.get(animalType).getProductType();

        ImageView animalImageView = new ImageView(new Image(animalType.toLowerCase() + ".jpg"));
        ImageView productImageView = new ImageView(new Image(productType.toLowerCase() + ".jpg"));
        ProgressBar progressBar = new ProgressBar();

        // Configure animal image and add it to the cell
        animalImageView.setFitWidth(50);
        animalImageView.setFitHeight(50);

        // Configure product image and add it to the cell (initially invisible)
        productImageView.setFitWidth(50);
        productImageView.setFitHeight(50);
        productImageView.setVisible(false);

        // Configure progress bar
        progressBar.setProgress(0.0);
        progressBar.setMaxWidth(50);

        // Add ImageView and ProgressBar to a VBox
        VBox animalAndProgressBar = new VBox(animalImageView, progressBar);
        animalAndProgressBar.setAlignment(Pos.CENTER);
        cell.getChildren().add(animalAndProgressBar);
        cell.getChildren().add(productImageView); // Add product ImageView to the cell

        // Initialize the Timeline object
        Timeline timeline = new Timeline();

        // Create a Timeline to update the progress bar
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            double progress = progressBar.getProgress();
            double progressIncrement = 1.0 / 10.0; // Adjust the denominator to control the production speed

            if (progress < 1.0) {
                progressBar.setProgress(progress + progressIncrement);
            } else {
                progressBar.setProgress(0);
                productImageView.setVisible(true);

                // Fade out the product image and update the inventory
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), productImageView);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.setOnFinished(f -> {
                    productImageView.setVisible(false);
                    updateProductInventory(player, productType);
                });
                fadeTransition.play();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void sellEntireInventory(Player player) {
        int totalIncome = 0;
        // Check if the player has any crops to sell
        if (player.getCrops().isEmpty()) {
            // Display an error message using an Alert dialog box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You have no crops to sell!");
            alert.setContentText("Time to get to work, farmer!");
            alert.showAndWait();
            return;
        }

        // Calculate the total income from selling crops
        for (String cropType : player.getCrops().keySet()) {
            int cropCount = player.getCrops().get(cropType);
            totalIncome += cropCount * market.getPrice(cropType);
        }

        // Clear the player's inventory and update the labels
        player.getCrops().clear();
        for (Label label : inventoryLabels.values()) {
            label.setText("0");
        }

        // Update the player's money
        player.setMoney(player.getMoney() + totalIncome);
        updateMoneyLabel();

        // Display an alert to show the total income from selling the inventory
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inventory Sold");
        alert.setHeaderText("You have sold your entire inventory!");
        alert.setContentText("Total income: $" + totalIncome);
        alert.showAndWait();
    }


    private void updateMoneyLabel() {
        moneyLabel.setText("Money: $" + player.getMoney());
    }

    private void updateProductInventory(Player player, String productType) {
        int currentProduct = player.getCrops().getOrDefault(productType, 0);
        player.getCrops().put(productType, currentProduct + 1);

        if (!inventoryLabels.containsKey(productType)) {
            HBox productRow = new HBox(5);
            Image productImage = new Image("file:src/main/resources/" + productType.toLowerCase() + ".jpg");
            ImageView productImageView = new ImageView(productImage);
            productImageView.setFitWidth(30);
            productImageView.setFitHeight(30);
            Label productAmount = new Label(productType + ": " + player.getCrops().get(productType));
            productAmount.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Mali';");
            inventoryLabels.put(productType, productAmount);
            productRow.getChildren().addAll(productImageView, productAmount);
            inventoryLayout.getChildren().add(productRow);
        } else {
            inventoryLabels.get(productType).setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Mali'");
        }
    }


    private void updateStatsLabels(UpgradeMarket upgradeMarket, Label increasedYieldLabel, Label fasterGrowthLabel, Label growthPercentageLabel, Label autoPlanterLabel) {
        int increasedYieldLevel = upgradeMarket.getUpgrades().get("Increased Yield").getLevel();
        int fasterGrowthLevel = upgradeMarket.getUpgrades().get("Faster Growth").getLevel();
        int autoPlanterLevel = upgradeMarket.getUpgrades().get("AutoPlanter").getLevel();
        double growthPercentage = (1 - (10.0 - fasterGrowthLevel) / 10.0) * 100;

        increasedYieldLabel.setText("Increased Yield Level: " + increasedYieldLevel);
        fasterGrowthLabel.setText("Faster Growth Level: " + fasterGrowthLevel);
        growthPercentageLabel.setText("Crops Grow " + String.format("%.2f", growthPercentage) + "% Faster");
        autoPlanterLabel.setText("AutoPlanter Level: " + autoPlanterLevel);
    }

    private void playMusic(String musicFile) {
        try {
            InputStream is = getClass().getResourceAsStream(musicFile);
            Files.copy(is, Paths.get("temp.wav"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Media music = new Media(Paths.get("temp.wav").toUri().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setAutoPlay(true);

        // Add an event handler to loop the audio indefinitely
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

        // mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Remove this line
    }



    private void playClickSound(String soundFile) {
        try {
            InputStream is = getClass().getResourceAsStream(soundFile);
            Files.copy(is, Paths.get("temp_click.mp3"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Media clickSound = new Media(Paths.get("temp_click.mp3").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(clickSound);
        mediaPlayer.play();
    }


    public static void main(String[] args) {
        launch(args);
    }


}