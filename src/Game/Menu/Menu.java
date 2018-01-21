package Game.Menu;

import Game.GameUtils.GameScene;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
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

    public Menu(Stage window, BorderPane menuPane, MenuScene menuScene, GameScene gameScene, MP3Player player, PlaylistManager playlistManager){

        Text header = new Text("MUSIC INVADER");
        header.setId("Header");
        header.setFill(Color.WHITE);

        Button start = new Button("START");
        start.setOnAction(e -> {
            window.setScene(gameScene);
            window.setFullScreen(true);
            player.stop();
            player.changePlaylist(playlistManager.getPlaylist("defaultPlaylist"));
            player.setDontAskMeWhy(0);
            player.play(0);
            gameScene.start();
        });

        Button songs = new Button("SONGS");
        songs.setOnAction(e -> {
            menuPane.setCenter(new SongsPane(menuPane, this, playlistManager.getAllSongs("defaultPlaylist"), player, playlistManager));
            player.stop();
            player.changePlaylist(playlistManager.getPlaylist("defaultPlaylist"));
        });

        Button leaderboards = new Button("LEADERBOARDS");
        leaderboards.setOnAction(e -> menuPane.setCenter(new LeaderboardsPane(menuPane, this)));

        Button options = new Button("OPTIONS");
        options.setOnAction(e -> menuPane.setCenter(new OptionsPane(menuPane, this)));

        Button exit = new Button("EXIT");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
        getChildren().addAll(header, start, songs, leaderboards, options, exit);
        setCursor(Cursor.DEFAULT);
    }


}
