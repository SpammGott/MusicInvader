package Game.GameUtils;

import Game.GameUtils.Utils.Helper;
import Game.Menu.MenuScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DefeatScene extends Scene {

    private Stage window;
    private MenuScene menuScene;
    private Pane root;

    public DefeatScene(Pane root, Stage window, MenuScene menuScene){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        this.window = window;
        this.menuScene = menuScene;
        start();
    }

    public void start(){
        Label defeated = new Label("YOU DIED");
        defeated.setId("Header");

        Button back = new Button("BACK TO MENU");
        back.setOnAction(e -> {
            window.setScene(menuScene);
        });

        VBox vBox = new VBox(defeated, back);
        vBox.setAlignment(Pos.CENTER);

        Pane pane = new Pane(vBox);
        pane.setStyle("-fx-background-color: black");

        root.getChildren().add(pane);
    }
}
