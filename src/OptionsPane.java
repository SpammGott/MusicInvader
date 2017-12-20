import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OptionsPane extends VBox {

    public OptionsPane(BorderPane menuScene, VBox menu){
        Label text = new Label("CONTROLS:");
        Button wasd = new Button("WASD");
        Button arrows = new Button("ARROW KEYS");

        text.setId("Headline");

        wasd.setOnAction(e -> {
            Helper.setControls(false);
            wasd.setId("PressedMenuButton");
            arrows.setId("MenuButton");
        });
        wasd.setId("PressedMenuButton");

        arrows.setOnAction(e -> {
            Helper.setControls(true);
            arrows.setId("PressedMenuButton");
            wasd.setId("MenuButton");
        });
        arrows.setId("MenuButton");

        Button back = new Button("BACK");
        back.setOnAction(e -> menuScene.setCenter(menu));
        back.setId("MenuButton");

        getChildren().addAll(text, wasd, arrows, back);
        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
    }
}
