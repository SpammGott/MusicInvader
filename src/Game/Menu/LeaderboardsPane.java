package Game.Menu;

import Game.GameUtils.Utils.Leaderboard;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * shows the data from the leaderboards txt file
 */
public class LeaderboardsPane extends VBox {

    public LeaderboardsPane(BorderPane menuScene, VBox menu){
        Leaderboard leaderboard = new Leaderboard();
        String[] s = leaderboard.getLeaderboard();

        Label text = new Label("LEADERBOARDS:");
        text.setId("Headline");

        VBox links = new VBox();
        VBox rechts = new VBox();

        String tempString[];
        for (int i = 0; i < s.length; i++){
            if(s[i] != null) {
                tempString = s[i].split(";");
                System.out.println(tempString.length);
                Label temp = new Label(tempString[0] + " " + tempString[1]);
                temp.setId("SideText");
                if (i % 2 == 0){
                    links.getChildren().add(temp);
                }else {
                    rechts.getChildren().add(temp);
                }
            }

        }

        HBox blubb = new HBox(links, rechts);
        blubb.setAlignment(Pos.CENTER);

        Button back = new Button("BACK");
        back.setOnAction(e -> menuScene.setCenter(menu));

        getChildren().addAll(text, blubb, back);
        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
    }
}
