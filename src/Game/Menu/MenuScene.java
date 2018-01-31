package Game.Menu;

import Game.GameUtils.Utils.Helper;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuScene extends Scene {
    BorderPane menuPane;

    public MenuScene (Stage window, BorderPane menuPane, MP3Player player, PlaylistManager playlistManager) {
        super(menuPane, Helper.getHeight(), Helper.getWidth());
        this.menuPane = menuPane;
    }

    public BorderPane getMenuPane(){
        return menuPane;
    }
}
