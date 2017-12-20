import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends VBox {

    public Menu(Stage window, double height, double width){
        Button exit = new Button("EXIT");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        exit.setId("MenuButton");

        Button start = new Button("START");
        start.setOnAction(e -> {
            window.setScene(new GameScene(new Pane(), height, width, window));
            window.setFullScreen(true);
        });
        start.setId("MenuButton");

        Button options = new Button("OPTIONS");
        options.setId("MenuButton");

        Button leaderboards = new Button("LEADERBOARDS");
        leaderboards.setId("MenuButton");

        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
        getChildren().addAll(start, exit);
    }
}
