package Game;

import Game.GameUtils.GameScene;
import Game.GameUtils.Utils.Helper;
import Game.Menu.Menu;
import Game.Menu.MenuScene;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class Controller extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window){
        //Font.loadFont(Controller.class.getResource(".." + File.separatorChar + "PIXELED.TTF").toExternalForm(), 10);

        MP3Player player;
        PlaylistManager playlistManager = new PlaylistManager(System.getProperty("user.dir") + "/res/Songs");
        player = new MP3Player(playlistManager.getPlaylist("titlesong"));
        player.play(0);

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                player.startAutomaticSkipper();
                return null;
            }
        };
        Thread repeater = new Thread(task);
        repeater.setDaemon(true);
        repeater.start();

        BorderPane menuPane = new BorderPane();

        MenuScene menuScene = new MenuScene(window, menuPane, player, playlistManager);
        menuScene.setCursor(Cursor.NONE);
        menuScene.getStylesheets().add("CSS.css");

        Pane root = new Pane();
        GameScene gameScene = new GameScene(root, window, menuScene, player, playlistManager);

        player.setGameScene(gameScene);

        menuPane.setCenter(new Menu(window, menuPane, menuScene, gameScene, player, playlistManager));

        window.setHeight(Helper.getHeight());
        window.setWidth(Helper.getWidth());
        window.setScene(menuScene);
        window.setTitle("Music Invader");
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();

    }
}
