package Game.Menu;

import Game.GameUtils.GameScene;
import Game.GameUtils.Utils.Helper;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuScene extends Scene {

    public MenuScene (Stage window, BorderPane menuPane){
        super(menuPane, Helper.getHeight(), Helper.getWidth());

        Pane root = new Pane();
        GameScene gameScene = new GameScene(root, window, this);

        menuPane.setCenter(new Menu(window, menuPane, this, gameScene));
        getStylesheets().add("CSS.css");
    }
}
