package agh.cs.lab;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {

    protected final Rectangle border;
    private boolean isWall = false;
    private final Effect dropShadowEffect = new DropShadow();
    private Vector2d position;


    public Tile(int x, int y, int tileSize) {
        this.position = new Vector2d(x, y);
        this.border = new Rectangle(tileSize - 2, tileSize - 2);
        setAsUnvisited();
        setTranslateX(x * tileSize);
        setTranslateY(1 + y * tileSize);
        getChildren().addAll(border);
    }


    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isWall() {
        return isWall;
    }

    public void changeType() {
        if (isWall) {
            setAsUnvisited();
        } else {
            setAsWall();
        }
    }

    public void setAsUnvisited() {
        this.isWall = false;
        border.setFill(Color.hsb(0, 0, 1));
        border.setEffect(null);
    }

    public void setAsVisited() {
        border.setFill(Color.hsb(213, 0.56, 1));
    }

    public void setAsPath() {
        border.setFill(Color.hsb(55, 1, 1));
    }

    public void setAsWall() {
        this.isWall = true;
        border.setFill(Color.hsb(223, 1, 0.6));
        border.setEffect(dropShadowEffect);
    }
}