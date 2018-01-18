package Game;

import Game.Menu.MenuScene;
import MP3Player.MP3Player;
import MP3Player.Playlist;
import MP3Player.PlaylistManager;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window){

         MP3Player player;
         PlaylistManager playlistManager = new PlaylistManager(System.getProperty("user.dir") + "/res/Songs");
         player = new MP3Player(playlistManager.getPlaylist("titlesong"));
         player.play(0);

        BorderPane menuPane = new BorderPane();

        MenuScene menuScene = new MenuScene(window, menuPane, player, playlistManager);
        menuScene.setCursor(Cursor.NONE);

        window.setScene(menuScene);

        window.setTitle("Music Invader");
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();

    }
}
