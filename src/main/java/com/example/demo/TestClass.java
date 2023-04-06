package com.example.demo;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Label;
import org.testng.annotations.Test;

public class TestClass {
    private Inventory idleFarmingGame;
    private Object alert;

    @Test
    public void testSellEntireInventory() {
        // Mock the necessary objects
        Player player = mock(Player.class);
        UpgradeMarket market = mock(UpgradeMarket.class);
        GameController gameController = mock(GameController.class);
        Map<String, Label> inventoryLabels = new HashMap<>();

        // Mock the labels
        int numLabels = 10;
        for (int i = 0; i < numLabels; i++) {
            Label mockLabel = mock(Label.class);
            when(mockLabel.getText()).thenReturn("0");
            inventoryLabels.put("Label " + i, mockLabel);
        }

        // Set up the player's inventory
        Map<String, Integer> crops = new HashMap<>();
        crops.put("Carrot", 3);
        crops.put("Corn", 2);
        crops.put("Pumpkin", 5);
        player.setCrops(crops);
        player.setGoldenEggs(2);
        player.setGoldRats(1);
        player.setGoldCobras(0);
        player.setGoldOxes(1);

        // Set up the market prices
        when(market.getPrice("Carrot")).thenReturn(10);
        when(market.getPrice("Corn")).thenReturn(15);
        when(market.getPrice("Pumpkin")).thenReturn(20);

        // Set up the game controller
        when(gameController.getMarket()).thenReturn(market);
        when(gameController.getPlayer()).thenReturn(player);
        when(gameController.getInventoryLabels()).thenReturn(inventoryLabels);

        // Call the method being tested
        idleFarmingGame.sellEntireInventory(player);

        // Verify that the player's inventory was cleared and the labels were updated
        verify(player).getCrops().clear();
        verify(player).setGoldenEggs(0);
        verify(player).setGoldRats(0);
        verify(player).setGoldCobras(0);
        verify(player).setGoldOxes(0);
        for (Label mockLabel : inventoryLabels.values()) {
            verify(mockLabel).setText("0");
        }

        // Verify that the player's money was updated and the alert was shown
        verify(player).setMoney(187500); // 3*10 + 2*15 + 5*20 + 2*5000 + 1*10000 + 1*20000 = 187500
        verify(gameController).updateMoneyLabel();
        try {
            verify(alert).wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class Inventory {
        public void updateMoneyLabel() {
        }

        public void sellEntireInventory(Player player) {
        }
    }

    private class GameController {
        public Object getMarket() {
            return null;
        }

        public Object getPlayer() {
            return null;
        }

        public Object getInventoryLabels() {
            return null;
        }

        public void updateMoneyLabel() {
        }
    }
}
