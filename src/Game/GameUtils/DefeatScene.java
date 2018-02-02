package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Utils.Helper;
import Game.Menu.MenuScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DefeatScene extends Scene {

    private Stage window;
    private MenuScene menuScene;
    private Pane root;
    private EntityHandler entityHandler;
    private int punkte;

    public DefeatScene(Pane root, Stage window, MenuScene menuScene, EntityHandler entityHandler){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        this.window = window;
        this.menuScene = menuScene;
        this.entityHandler = entityHandler;
        punkte = this.entityHandler.getPoints().getValue();
        start();
    }

    public void start(){
        Label defeated = new Label("YOU DIED");
        defeated.setId("Header");

        Region reg1 = new Region();
        Region reg2 = new Region();
        HBox.setHgrow(reg1, Priority.ALWAYS);
        HBox.setHgrow(reg2, Priority.ALWAYS);

        Label points = new Label("POINTS: ");
        points.setId("SideText");
        Label actPoints = new Label(String.valueOf(punkte));
        actPoints.setId("SideText");
        HBox pointCont = new HBox(reg1, points, actPoints, reg2);

        Button back = new Button("BACK TO MENU");
        back.setOnAction(e -> {
            window.setScene(menuScene);
            window.setFullScreen(true);
        });

        VBox vBox = new VBox(defeated, pointCont, back);

        Pane pane = new Pane(vBox);
        pane.setPrefSize(Helper.getWidth(), Helper.getHeight());
        pane.setStyle("-fx-background-color: black");

        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(Helper.getWidth(), Helper.getHeight());

        getStylesheets().add("CSS.css");
        root.getChildren().add(pane);
    }
}
