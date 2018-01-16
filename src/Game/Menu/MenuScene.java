package Game.Menu;

import Game.GameUtils.GameScene;
import Game.GameUtils.Utils.Helper;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuScene extends Scene {

    public MenuScene (Stage window, BorderPane menuPane, MP3Player player, PlaylistManager playlistManager){
        super(menuPane, Helper.getHeight(), Helper.getWidth());

        Pane root = new Pane();
        GameScene gameScene = new GameScene(root, window, this);

        setCursor(Cursor.NONE);
        menuPane.setCenter(new Menu(window, menuPane, this, gameScene, player, playlistManager));
        getStylesheets().add("CSS.css");
    }
}
