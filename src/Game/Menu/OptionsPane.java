package Game.Menu;

import Game.GameUtils.Utils.Helper;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Lets the player choose between different control types
 */
public class OptionsPane extends VBox {

    public OptionsPane(BorderPane menuScene, VBox menu){
        Label text = new Label("CONTROLS:");
        Button wasd = new Button("WASD");
        Button arrows = new Button("ARROW KEYS");
        arrows.setStyle("-fx-font-size: 30");

        text.setId("Headline");

        wasd.setOnAction(e -> {
            Helper.setControls(true);
            wasd.setStyle("-fx-font-size: 30");
            arrows.setStyle("-fx-font-size: 20");
        });

        arrows.setOnAction(e -> {
            Helper.setControls(false);
            arrows.setStyle("-fx-font-size: 30");
            wasd.setStyle("-fx-font-size: 20");
        });

        Button back = new Button("BACK");
        back.setOnAction(e -> menuScene.setCenter(menu));

        getChildren().addAll(text, wasd, arrows, back);
        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
    }
}
