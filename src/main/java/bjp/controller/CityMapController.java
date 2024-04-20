package bjp.controller;

import bjp.controller.PopupController;
import bjp.utility.GameEngine;
import bjp.utility.Bus;
import bjp.utility.EnvironmentalPopup;
import bjp.utility.Gem;
import bjp.utility.Luas;
import bjp.utility.Obstacles;
import bjp.utility.StaticTransportConfig;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import bjp.Main;

public class CityMapController {
    public static final int WIDTH = ((int) Main.scene.getHeight() - 30)/20;
    public static final int HEIGHT = ((int) Main.scene.getHeight() - 30)/20;
    @FXML
    private GridPane cityMapGrid;

    @FXML
    private StackPane cityMainStack;
    public static final int ROWS = 20;
    public static final int COLS = 36;
    
    @FXML
    private void initialize() {
        generateGrid(ROWS, COLS);
        Bus.makeBusRoad(StaticTransportConfig.BUS1, StaticTransportConfig.BUS1_STOPS, cityMapGrid);
        Bus.makeBusRoad(StaticTransportConfig.BUS2, StaticTransportConfig.BUS2_STOPS, cityMapGrid);
        Luas.makeLuasLane(StaticTransportConfig.LUAS, StaticTransportConfig.LUAS_STOPS, cityMapGrid);   //addesd bu 
        Gem.placeGem(cityMainStack, cityMapGrid);
        GameEngine.newPlayer.placePlayer(cityMapGrid);
        Obstacles.placeTrees(cityMapGrid);
    
        PopupController.showPopupMessage(cityMainStack, "Welcome to Gem World");
        cityMapGrid.setGridLinesVisible(true);


        Thread thread = new Thread(() -> {
            while (true) {
                try
                {
                    // Sleep for 10 seconds before showing the popup
                    Thread.sleep(10000); 
                    // Execute the popup display on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        EnvironmentalPopup.showEduPopup(cityMainStack, EnvironmentalPopup.selectMsg());
                    });
                } 
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        GameEngine.mainEventHandler(cityMainStack, cityMapGrid);
       
        cityMapGrid.setGridLinesVisible(true);
    }
    
    private void generateGrid(int rows, int cols) {
        cityMapGrid.getChildren().clear();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle rect = new Rectangle(WIDTH, HEIGHT);
                rect.setFill(Color.WHITE);
                cityMapGrid.add(rect, col, row);
            }
        }
    }
}