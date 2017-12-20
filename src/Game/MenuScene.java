package Game;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuScene extends BorderPane {

    public MenuScene (double heigth, double width, Stage window){
        //super(new Game.Menu(window, heigth, width), heigth, width);
        setCenter(new Menu(window, heigth, width, this));
        getStylesheets().add("CSS.css");
        setCursor(Cursor.DEFAULT);
    }
}
