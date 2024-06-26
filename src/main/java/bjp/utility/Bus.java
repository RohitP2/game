package bjp.utility;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import bjp.controller.CityMapController;

public class Bus extends StaticTransport {
    private static final Image roadHori = new Image(Luas.class.getResourceAsStream("/img/road-hori.png"));
    private static final Image roadConn = new Image(Luas.class.getResourceAsStream("/img/road-conn.png"));
    private static final Image roadVerti = new Image(Luas.class.getResourceAsStream("/img/road-verti.png"));
    private static final Image bus1Stop = new Image(Luas.class.getResourceAsStream("/img/bus1-stop.png"));
    private static final Image bus2Stop = new Image(Luas.class.getResourceAsStream("/img/bus2-stop.png"));
    private static final Image bus3Stop = new Image(Luas.class.getResourceAsStream("/img/bus3-stop.png"));

    public Bus(String transportName, double co2Emissions, long timeTaken,
            HashMap<Location, Pair<Location, Location>> bus1Stops) {
        super(transportName, co2Emissions, timeTaken, bus1Stops);
    }

    public static void makeBusRoad(Bus bus, HashMap<Location, Pair<Location, Location>> busStops, GridPane grid, int number) {
        for (Location start : busStops.keySet()) {
            Location end = busStops.get(start).getValue();
            bus.drawRoad(start, end, grid);
        }
        Image busStop = bus1Stop;
        if (number == 1){
            busStop = bus1Stop;
        }
        else if (number == 2){
            busStop = bus2Stop;
        }
        else if (number == 3){
            busStop = bus3Stop;
        }
        for (Location stop : busStops.keySet()) {
            bus.fillGridCellWithImage(grid, stop.getX(), stop.getY(), busStop);
        }
    }

    @Override
    public void drawRoad(Location start, Location end, GridPane grid) {
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();

        int minX = Math.min(startX, endX);
        int maxX = Math.max(startX, endX);
        int minY = Math.min(startY, endY);
        int maxY = Math.max(startY, endY);

        for (int x = minX; x <= maxX; x++) {
            if (x == minX || x == maxX) {
                fillGridCellWithImage(grid, x, startY, roadConn);
            } else {
                fillGridCellWithImage(grid, x, startY, roadHori);
            }
        }

        for (int y = minY; y <= maxY; y++) {
            if (y == minY || y == maxY) {
                fillGridCellWithImage(grid, endX, y, roadConn);
            } else {
                fillGridCellWithImage(grid, endX, y, roadVerti);
            }
        }
    }

    @Override
    public void fillGridCellWithImage(GridPane grid, int col, int row, Image roadImage) {
        Node node = getNodeFromGridPane(grid, col, row);
        if (node instanceof ImageView) {
            ((ImageView) node).setImage(roadImage);
        } else {
            ImageView imageView = new ImageView(roadImage);
            imageView.setFitWidth(CityMapController.WIDTH);
            imageView.setFitHeight(CityMapController.HEIGHT);
            imageView.setSmooth(true);
            grid.add(imageView, col, row);
        }
    }

    @Override
    public void fillGridCell(GridPane grid, int row, int col, Color color) {
        Node node = getNodeFromGridPane(grid, col, row);
        if (node instanceof Rectangle) {
            ((Rectangle) node).setFill(color);
        }
    }

    @Override
    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    @Override
    public int getManhattanDistance(Location l1, Location l2) {
        return Math.abs(l1.getX() - l2.getX()) + Math.abs(l1.getY() - l2.getY());
    }

}
