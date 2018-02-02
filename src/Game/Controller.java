package Game;

import Game.GameUtils.GameScene;
import Game.GameUtils.Utils.Helper;
import Game.Menu.Menu;
import Game.Menu.MenuScene;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
import MP3Player.SoundPlayer;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Loads the font
 * Starts the MP3Player and the soundPlayer
 * Creates the game and menu scenes
 */
public class Controller extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window){
        try {
            Font.loadFont(Controller.class.getResource("../PIXELED.TTF").toExternalForm(), 10);
        }catch(Exception e){
            System.out.println("File konnte nicht geladen werden\n" + e.getMessage());
        }

        MP3Player player;
        PlaylistManager playlistManager = new PlaylistManager(System.getProperty("user.dir") + "/res/Songs");
        player = new MP3Player(playlistManager.getPlaylist("defaultPlaylist"));
        SoundPlayer splayer = new SoundPlayer(getClass().getClassLoader().getResource("Songs/Titlesong/BoxCat_Games_-_10_-_Epic_Song.mp3").getPath());
        splayer.loop();
        splayer.play();

        BorderPane menuPane = new BorderPane();

        MenuScene menuScene = new MenuScene(window, menuPane, player, playlistManager);
        menuScene.setCursor(Cursor.NONE);
        menuScene.getStylesheets().add("CSS.css");

        Pane root = new Pane();
        GameScene gameScene = new GameScene(root, window, menuScene, player, playlistManager, splayer);

        player.setGameScene(gameScene);

        menuPane.setCenter(new Menu(window, menuPane, gameScene, player, playlistManager, splayer));

        window.setHeight(Helper.getHeight());
        window.setWidth(Helper.getWidth());
        window.setScene(menuScene);
        window.setTitle("Music Invader");
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();

    }
}
