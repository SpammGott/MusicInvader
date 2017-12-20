package Game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SongsPane extends VBox {

    public SongsPane(BorderPane menuScene, VBox menu){
        Button back = new Button("BACK");
        back.setOnAction(e -> menuScene.setCenter(menu));
        back.setId("MenuButton");

        getChildren().addAll(back);
        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
    }
}
