package agh.cs.lab;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MovableTile extends Tile {


    public MovableTile(int x, int y, int tileSize,String text) {
        super(x, y, tileSize);
        setAsPath();
        Text tileText = new Text(text);
        tileText.setFont(Font.font(tileSize / 2.0 + 4));
        getChildren().add(tileText);
    }

    public void selectTile() {
        this.setStyle("-fx-border-color: red; -fx-border-width: 2");
    }

    public void unselectTile(){
        this.setStyle("");
    }
}
