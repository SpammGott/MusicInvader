package Game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LeaderboardsPane extends VBox {

    public LeaderboardsPane(BorderPane menuScene, VBox menu){
        Label text = new Label("LEADERBOARDS:");
        text.setId("Headline");

        Button back = new Button("BACK");
        back.setOnAction(e -> menuScene.setCenter(menu));
        back.setId("MenuButton");

        getChildren().addAll(text, back);
        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
    }
}
