package Game.Menu;

import Game.GameUtils.GameScene;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends VBox {

    public Menu(Stage window, BorderPane menuPane, MenuScene menuScene, GameScene gameScene){

        Text header = new Text("MUSIC INVADER");
        header.setId("Header");
        header.setFill(Color.WHITE);

        Button start = new Button("START");
        start.setOnAction(e -> {
            window.setScene(gameScene);
            window.setFullScreen(true);
            gameScene.start();
        });
        start.setId("MenuButton");

        Button songs = new Button("SONGS");
        songs.setOnAction(e -> menuPane.setCenter(new SongsPane(menuPane, this)));
        songs.setId("MenuButton");

        Button leaderboards = new Button("LEADERBOARDS");
        leaderboards.setOnAction(e -> menuPane.setCenter(new LeaderboardsPane(menuPane, this)));
        leaderboards.setId("MenuButton");

        Button options = new Button("OPTIONS");
        options.setOnAction(e -> menuPane.setCenter(new OptionsPane(menuPane, this)));
        options.setId("MenuButton");

        Button exit = new Button("EXIT");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        exit.setId("MenuButton");

        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
        getChildren().addAll(header, start, songs, leaderboards, options, exit);
        setCursor(Cursor.DEFAULT);
    }


}
