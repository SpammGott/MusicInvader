import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends VBox {

    public Menu(Stage window, Scene gameScene){
        Button exit = new Button("EXIT");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        exit.setId("MenuButton");

        Button start = new Button("START");
        start.setOnAction(e -> {
            gameScene.setCursor(Cursor.NONE);
            window.setScene(gameScene);
            window.setFullScreen(true);
        });
        start.setId("MenuButton");

        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
        getChildren().addAll(start, exit);
    }
}
