import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends VBox {

    public Menu(Stage window, double height, double width, BorderPane menuScene){

        Button start = new Button("START");
        start.setOnAction(e -> {
            window.setScene(new GameScene(new Pane(), height, width, window));
            window.setFullScreen(true);
        });
        start.setId("MenuButton");

        Button songs = new Button("SONGS");
        songs.setId("MenuButton");

        Button leaderboards = new Button("LEADERBOARDS");
        leaderboards.setId("MenuButton");

        Button options = new Button("OPTIONS");
        options.setOnAction(e -> menuScene.setCenter(new OptionsPane(menuScene, this)));
        options.setId("MenuButton");

        Button exit = new Button("EXIT");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        exit.setId("MenuButton");

        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
        getChildren().addAll(start, songs, leaderboards, options, exit);
    }
}
