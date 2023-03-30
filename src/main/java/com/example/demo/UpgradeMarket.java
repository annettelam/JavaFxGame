package com.example.demo;



import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UpgradeMarket {

    private GridPane upgradePane;
    private Player player;
    private Stage upgradeStage;
    private HashMap<String, Upgrade> upgrades;

    private Runnable onUpgradePurchased;

    public UpgradeMarket(Player player, Stage upgradeStage, IdleFarmingGame game) {
        this.player = player;
        this.upgradeStage = upgradeStage;
        upgradePane = new GridPane();
        upgradePane.setPrefSize(400, 300);
        upgradePane.setHgap(10);
        upgradePane.setVgap(10);
        upgradePane.setAlignment(Pos.CENTER);
        upgradePane.setPadding(new Insets(20, 20, 20, 20));

        Label upgradeLabel = new Label("Upgrades");
        upgradeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-font-family: 'Mali'");
        upgradePane.add(upgradeLabel, 0, 0, 3, 1);

        // Initialize the upgrades HashMap
        upgrades = new HashMap<>();
        upgrades.put("Faster Growth", new Upgrade("Faster Growth", 20));
        upgrades.put("Increased Yield", new Upgrade("Increased Yield", 30));
        upgrades.put("AutoPlanter", new Upgrade("AutoPlanter", 100));


        // Add upgrades to the upgrade pane
        int row = 1;
        for (String upgradeName : upgrades.keySet()) {
            Upgrade upgrade = upgrades.get(upgradeName);

            Label upgradeNameLabel = new Label(upgradeName);
            upgradeNameLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");
            upgradePane.add(upgradeNameLabel, 0, row);

            Label upgradeCostLabel = new Label("$" + upgrade.getCost());
            upgradeCostLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");
            upgradePane.add(upgradeCostLabel, 1, row);

            Button upgradeButton = new Button("Upgrade");
            upgradeButton.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: white; -fx-font-size: 14; -fx-font-family: 'Mali'");



            upgradeButton.setOnAction(e -> {
                if (player.getMoney() >= upgrade.getCost()) {
                    player.setMoney(player.getMoney() - upgrade.getCost());
                    upgrade.incrementLevel();
                    upgrade.setCost(upgrade.getCost() * 2);
                    upgradeCostLabel.setText("$" + upgrade.getCost());
                    game.updateLabels(player);


                    upgradePurchased(); // Move the upgradePurchased() call outside the if condition

                }

            });

            // Set the cursor to hand when hovering over the upgradeButton
            upgradeButton.setOnMouseEntered(e -> upgradeButton.setCursor(Cursor.HAND));
            upgradeButton.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");

            // Reset the cursor when the mouse exits the upgradeButton
            upgradeButton.setOnMouseExited(e -> upgradeButton.setCursor(Cursor.DEFAULT));
            upgradeButton.setStyle("-fx-font-size: 16; -fx-font-family: 'Mali'");

            upgradePane.add(upgradeButton, 2, row);

            row++;
        }

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-font-family: 'Mali'");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                upgradeStage.close();
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

        upgradePane.add(closeButton, 2, row);
    }

    public void setOnUpgradePurchased(Runnable onUpgradePurchased) {
        this.onUpgradePurchased = onUpgradePurchased;
    }

    // Call this method after purchasing an upgrade
    private void upgradePurchased() {
        if (onUpgradePurchased != null) {
            onUpgradePurchased.run();
        }}


    public GridPane getUpgradePane() {
        return upgradePane;
    }

    public HashMap<String, Upgrade> getUpgrades() {
        return upgrades;
    }

    public Stage getStage() {
        return upgradeStage;
    }
}

