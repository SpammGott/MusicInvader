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

        try {
            PlaylistManager playlistManager = new PlaylistManager("C:/Users/Josh/IdeaProjects/MusicInvaderGIT/res/Songs");
            player = new MP3Player(playlistManager.getPlaylist("titlesong"));
            player.play();

            BorderPane menuPane = new BorderPane();

            MenuScene menuScene = new MenuScene(window, menuPane, player, playlistManager);
            menuScene.setCursor(Cursor.NONE);

            window.setScene(menuScene);
        }catch (Exception e){
            System.out.println("Probably need to update music path in Controller:24");
        }

        window.setTitle("Music Invader");
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();
    }
}
