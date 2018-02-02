package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Utils.Leaderboard;
import Game.GameUtils.Utils.Helper;
import Game.Menu.MenuScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

class DefeatScene extends Scene {

    private Stage window;
    private MenuScene menuScene;
    private Pane root;
    private EntityHandler entityHandler;
    private int punkte;
    private String userName = "";
    private Leaderboard leaderboard = new Leaderboard();

    DefeatScene(Pane root, Stage window, MenuScene menuScene, EntityHandler entityHandler){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        this.window = window;
        this.menuScene = menuScene;
        this.entityHandler = entityHandler;
        this.punkte = this.entityHandler.getPoints().getValue();
        start();
    }

    private void start(){
        Label defeated = new Label("YOU DIED");
        defeated.setId("Header");

        Region reg1 = new Region();
        Region reg2 = new Region();
        HBox.setHgrow(reg1, Priority.ALWAYS);
        HBox.setHgrow(reg2, Priority.ALWAYS);

        Label enter = new Label("YOUR NAME: ");
        enter.setId("SideText");
        TextField input = new TextField();
        HBox userInput = new HBox(reg1, enter, input, reg2);
        userInput.setAlignment(Pos.CENTER);

        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.ESCAPE){
                userName = input.getText();
                leaderboard.addHighscore(punkte, userName);
                window.setScene(menuScene);
                window.setFullScreen(true);
            }
        });

        Label points = new Label("POINTS: ");
        points.setId("SideText");
        Label actPoints = new Label(String.valueOf(punkte));
        actPoints.setId("SideText");
        HBox pointCont = new HBox(reg1, points, actPoints, reg2);

        VBox vBox = new VBox(defeated, userInput, pointCont);

        Pane pane = new Pane(vBox);
        pane.setPrefSize(Helper.getWidth(), Helper.getHeight());
        pane.setStyle("-fx-background-color: black");

        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(Helper.getWidth(), Helper.getHeight());

        getStylesheets().add("CSS.css");
        root.getChildren().add(pane);
    }

}
