package agh.cs.lab;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Visualization implements EventHandler<ActionEvent> {

    private final int width = 1800;
    private final int height = 800;
    private final int gridWidth = 1360;

    private static int xTiles = 34;
    private static int yTiles = 20;

    private final int[] sizes = {80, 40, 20, 16, 10, 8};

    private final Button visualizeButton = addButton("Visualize", 1590, 225);
    private final Button generateWallsButton = addButton("Generate walls", 1450, 225);
    private ChoiceBox<Algorithm> algorithmBox;

    private boolean readyToVisualize = false;

    private final GridManager gridManager;


    public Visualization(Stage stage, MutableInt timeGap) {

        Pane root = new Pane();
        root.setPrefSize(width, height);


        Pane gridPane = new Pane();
        gridPane.setStyle("-fx-background-color: black");
        gridPane.setPrefSize(1360, height);
        gridManager = new GridManager(gridPane, xTiles, yTiles);

        // labels
        Label sizeLabel = addLabel(1560, 55, "Size");
        Label timeGapLabel = addLabel(1550, 125, "Speed");

        // sliders
        Slider sizeSlider = addSlider(1430, 75, 0, sizes.length - 1, 2);
        sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int size = sizes[newValue.intValue()];
            xTiles = gridWidth / size;
            yTiles = height / size;
            gridManager.generateGrid(xTiles, yTiles);
        });
        Slider speedSlider = addSlider(1430, 150, 0, 980, 0);
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            timeGap.change((1000 - newValue.intValue()));
        });

        addAlgorithmChoiceBox(root);

        addLegend(root);

        root.getChildren().addAll(sizeSlider, speedSlider, sizeLabel, timeGapLabel,
                visualizeButton, generateWallsButton, gridPane);

        stage.setScene(new Scene(root));
        stage.setTitle("Pathfinding");
        stage.show();
    }

    public void update(Vector2d position) {
        gridManager.setAsVisited(position);
    }

    public HashMap<Vector2d, ArrayList<Vector2d>> constructGraph() {
        return gridManager.constructGraph();
    }

    public void visualizePath(List<Vector2d> path) throws InterruptedException {
        for (Vector2d node : path) {
            TimeUnit.MILLISECONDS.sleep(50);
            gridManager.setAsPath(new Vector2d(node.x, node.y));
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == generateWallsButton) {
            gridManager.generateGrid(xTiles, yTiles);
            gridManager.generateWalls();
        } else if (event.getSource() == visualizeButton) {
            readyToVisualize = true;
        }
    }


    public AlgorithmInfo getAlgorithmInfo(){
        return new AlgorithmInfo(gridManager.getSource(),
                gridManager.getTarget(), algorithmBox.getValue().getAlgorithmsClass());
    }

    public boolean readyToVisualize() {
        return readyToVisualize;
    }

    public void waitForNextVisualization() {
        readyToVisualize = false;
    }


    private Rectangle addRectangle(int sizeX, int sizeY, int x, int y, Color color) {
        Rectangle rectangle = new Rectangle(sizeX, sizeY);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        rectangle.setFill(color);
        rectangle.setStyle("-fx-border-color: black; -fx-border-width: 10");

        return rectangle;
    }

    private Slider addSlider(int x, int y, int min, int max, int value) {
        Slider slider = new Slider();
        slider.setTranslateX(1430);
        slider.setTranslateY(y);
        slider.setMax(max);
        slider.setMin(min);
        slider.setValue(value);
        slider.setPrefSize(300, 15);

        return slider;
    }

    private Label addLabel(int x, int y, String text) {
        Label label = new Label();
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setText(text);
        label.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 16));

        return label;
    }

    private Button addButton(String name, int x, int y) {
        Button button = new Button(name);
        button.setPrefSize(120, 45);
        button.setTranslateX(x);
        button.setTranslateY(y);
        button.setOnAction(this);
        button.setFont(Font.font(14));

        return button;
    }

    private void addLegend(Pane root){
        Rectangle darkBlueTile = addRectangle(30, 30, 1430, 600, Color.hsb(223, 1, 0.6));
        Label darBlueTileLabel = addLabel(1470, 605, " - Wall");
        Rectangle lightBlueTile = addRectangle(30, 30, 1430, 640, Color.hsb(213, 0.56, 1));
        Label lightBlueTileLabel = addLabel(1470, 645, " - Visited Node");
        Rectangle yellowTile = addRectangle(30, 30, 1430, 680, Color.hsb(55, 1, 1));
        Label yellowTileLabel = addLabel(1470, 685, " - Path");
        Rectangle whiteTile = addRectangle(30, 30, 1430, 720, Color.hsb(0, 0, 1));
        Label whiteTileLabel = addLabel(1470, 725, " - Not Visited Node");
        Pane colorsPane = new Pane();
        colorsPane.setPrefSize(250, 170);
        colorsPane.setTranslateX(1415);
        colorsPane.setTranslateY(590);
        colorsPane.setStyle("-fx-background-color: #abb8cb");

        root.getChildren().addAll(colorsPane, darBlueTileLabel,darkBlueTile, lightBlueTile, lightBlueTileLabel, yellowTile,
                yellowTileLabel, whiteTile, whiteTileLabel);

    }

    private void addAlgorithmChoiceBox(Pane root){
        algorithmBox = new ChoiceBox<>();
        algorithmBox.setTranslateX(1480);
        algorithmBox.setTranslateY(325);
        algorithmBox.getItems().addAll(Algorithm.values());
        algorithmBox.setValue(Algorithm.DIJKSTRA);
        algorithmBox.setPrefSize(200, 30);
        algorithmBox.getStyleClass().add("center-aligned");

        root.getChildren().add(algorithmBox);
    }

    public void noConnectionInfo() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information");
        alert.setHeaderText("No connection between source and target");
        alert.showAndWait();
    }
}
