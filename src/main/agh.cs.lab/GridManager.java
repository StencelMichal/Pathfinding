package agh.cs.lab;

import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.*;

public class GridManager {

    private final Random random = new Random();

    private int xTiles;
    private int yTiles;
    private int tileSize;

    private MovableTile source;
    private MovableTile target;
    private MovableTile selectedTile;

    private Tile[][] grid;

    private final Pane gridPane;


    public GridManager(Pane gridPane, int xTiles, int yTiles) {
        this.gridPane = gridPane;
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        generateGrid(xTiles, yTiles);
    }

    public HashMap<Vector2d, ArrayList<Vector2d>> constructGraph() {
        HashMap<Vector2d, ArrayList<Vector2d>> graph = new HashMap<>();
        for (int x = 0; x < xTiles; x++) {
            for (int y = 0; y < yTiles; y++) {
                if (!grid[x][y].isWall()) {
                    graph.put(new Vector2d(x, y), new ArrayList<>());
                }
            }
        }

        for (Vector2d nodePosition : graph.keySet()) {
            for (Vector2d unitVector : Vector2d.UNIT_VECTORS) {
                Vector2d childPosition = nodePosition.add(unitVector);
                if (graph.containsKey(childPosition)) {
                    graph.get(nodePosition).add(childPosition);
                }
            }
        }

        return graph;
    }

    public void generateGrid(int xTiles, int yTiles) {
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        gridPane.getChildren().clear();
        this.grid = new Tile[xTiles][yTiles];
        int height = 800;
        int gridWidth = 1360;
        tileSize = Math.min(height / yTiles, gridWidth / xTiles);

        for (int x = 0; x < xTiles; x++) {
            for (int y = 0; y < yTiles; y++) {
                grid[x][y] = new Tile(x, y, tileSize);
                int finalX = x;
                int finalY = y;
                grid[x][y].setOnMouseClicked(e -> clickTile(grid[finalX][finalY]));
                gridPane.getChildren().add(grid[x][y]);
            }
        }
        source = new MovableTile(0, 0, tileSize, "S");
        target = new MovableTile(xTiles - 1, yTiles - 1, tileSize, "T");
        source.setOnMouseClicked(e -> clickTile(source));
        target.setOnMouseClicked(e -> clickTile(target));
        grid[0][0] = source;
        grid[xTiles - 1][yTiles - 1] = target;
        gridPane.getChildren().addAll(grid[0][0], grid[xTiles - 1][yTiles - 1]);
    }

    public void generateWalls() {
        generateGrid(xTiles, yTiles);
        int wallsNumber = (int) (xTiles * yTiles * 0.3);
        Set<Vector2d> walls = new HashSet<>();
        while (walls.size() < wallsNumber) {
            Vector2d wall = new Vector2d(random.nextInt(xTiles), random.nextInt(yTiles));
            if (!wall.equals(source.getPosition()) && !wall.equals(target.getPosition()))
                walls.add(wall);
        }
        for (Vector2d wall : walls) {
            grid[wall.x][wall.y].setAsWall();
        }
    }

    private void clickTile(Tile tile) {
        if (tile.equals(source) || tile.equals(target)) {
            if (selectedTile == null) {
                selectedTile = (MovableTile) tile;
                ((MovableTile) tile).selectTile();
            } else if (selectedTile != tile) {
                swapTiles(source, target);
                selectedTile.unselectTile();
                selectedTile = null;
            } else {
                selectedTile.unselectTile();
                selectedTile = null;
            }
        } else {
            if (selectedTile == null) {
                tile.changeType();
            } else if (!tile.isWall()) {
                swapTiles(selectedTile, tile);
                selectedTile.unselectTile();
                selectedTile = null;
            }
        }
    }

    private void swapTiles(Tile tile1, Tile tile2) {
        Vector2d tile1Position = tile1.getPosition();
        Vector2d tile2Position = tile2.getPosition();
        grid[tile2Position.x][tile2Position.y] = tile1;
        grid[tile1Position.x][tile1Position.y] = tile2;
        tile1.setTranslateX(tile2Position.x * tileSize);
        tile1.setTranslateY(1 + tile2Position.y * tileSize);
        tile2.setTranslateX(tile1Position.x * tileSize);
        tile2.setTranslateY(1 + tile1Position.y * tileSize);
        tile1.setPosition(tile2Position);
        tile2.setPosition(tile1Position);
    }

    public void setAsVisited(Vector2d position) {
        grid[position.x][position.y].setAsVisited();
    }

    public void setAsPath(Vector2d position) {
        grid[position.x][position.y].setAsPath();
    }

    public Vector2d getSource() {
        return source.getPosition();
    }

    public Vector2d getTarget() {
        return target.getPosition();
    }
}