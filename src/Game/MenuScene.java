package Game;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuScene extends BorderPane {

    public MenuScene (Stage window){
        setCenter(new Menu(window,this));
        getStylesheets().add("CSS.css");
        setCursor(Cursor.DEFAULT);
    }
}
