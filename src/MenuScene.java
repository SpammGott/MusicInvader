import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuScene extends Scene {
    public MenuScene (double heigth, double width, Stage window){
        super(new Menu(window, heigth, width), heigth, width);
        getStylesheets().add("CSS.css");
        setCursor(Cursor.DEFAULT);
    }
}
