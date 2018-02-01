package Game.Menu;

import Game.GameUtils.GameScene;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
import MP3Player.SoundPlayer;
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

    private SoundPlayer titlesong;

    public Menu(Stage window, BorderPane menuPane, MenuScene menuScene, GameScene gameScene, MP3Player player, PlaylistManager playlistManager, SoundPlayer titlesong){
        this.titlesong = titlesong;
        Text header = new Text("MUSIC INVADER");
        header.setId("Header");
        header.setFill(Color.WHITE);

        Button start = new Button("START");
        start.setOnAction(e -> {
            this.titlesong.stop();
            window.setScene(gameScene);
            window.setFullScreen(true);

            player.play();
            gameScene.start();
        });

        Button songs = new Button("SONGS");
        songs.setOnAction(e -> {
            titlesong.stop();
            menuPane.setCenter(new SongsPane(menuPane, this, playlistManager.getAllSongs("defaultPlaylist"), player, playlistManager, titlesong));
            player.stop();
            //player.changePlaylist(playlistManager.getPlaylist("defaultPlaylist"));
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

    public void playTitlesong(){
        this.titlesong.play();
    }


}
