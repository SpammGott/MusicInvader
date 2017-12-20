package Game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends VBox {

    /*
    private int fontsize = 60;
    private int counter = 0;
    */

    public Menu(Stage window, double height, double width, BorderPane menuScene){

        Text header = new Text("MUSIC INVADER");
        header.setId("Header");
        header.setFill(Color.WHITE);
        /*
        * kinda stupid
        Timeline wiggle = new Timeline(
          new KeyFrame(Duration.millis(500), e -> {
              if (counter < 10){
                  fontsize++;
                  counter++;
              }else if (counter < 30){
                  fontsize--;
                  counter++;
              }else {
                  counter = 0;
              }
              header.setStyle("-fx-font-size: " + fontsize);
          })
        );
        wiggle.setCycleCount(Animation.INDEFINITE);
        wiggle.play();
        */

        Button start = new Button("START");
        start.setOnAction(e -> {
            window.setScene(new GameScene(new Pane(), height, width, window));
            window.setFullScreen(true);
        });
        start.setId("MenuButton");

        Button songs = new Button("SONGS");
        songs.setOnAction(e -> menuScene.setCenter(new SongsPane(menuScene, this)));
        songs.setId("MenuButton");

        Button leaderboards = new Button("LEADERBOARDS");
        leaderboards.setOnAction(e -> menuScene.setCenter(new LeaderboardsPane(menuScene, this)));
        leaderboards.setId("MenuButton");

        Button options = new Button("OPTIONS");
        options.setOnAction(e -> menuScene.setCenter(new OptionsPane(menuScene, this)));
        options.setId("MenuButton");

        Button exit = new Button("EXIT");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        exit.setId("MenuButton");

        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
        getChildren().addAll(header, start, songs, leaderboards, options, exit);
    }
}
