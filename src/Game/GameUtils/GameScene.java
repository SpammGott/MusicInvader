package Game.GameUtils;

import Game.Helper;
import Game.Menu.MenuScene;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene {

    private int speed = 15;
    private List<Projectile> projectiles = new ArrayList<>();
    private Player player;
    private double height;
    private double width;

    public GameScene(Pane root, Stage window, MenuScene menuScene){
        super(root, Helper.getHeight(), Helper.getWidth());

        height = Helper.getHeight();
        width = Helper.getWidth();

        //PC = PlayerCharacter
        player = new Player(this, speed);

        //actual movement
        AnimationTimer shipControls = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.movePlayer();
            }
        };
        shipControls.start();

        //creates a projectile every 333ms and adds it to an arraylist
        Timeline pcProjectiles = new Timeline(
                new KeyFrame(Duration.millis(100),
                e -> {
                    Projectile temp = player.fireProjectile();
                    root.getChildren().add(temp);
                    projectiles.add(temp);
                })
        );
        pcProjectiles.setCycleCount(Animation.INDEFINITE);
        pcProjectiles.play();

        //goes through list of projectiles every frame and advances their movement
        AnimationTimer projectilMover = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for(int i = 0; i < projectiles.size(); i++){
                    Ellipse temp = projectiles.get(i);
                    if (temp.getCenterY() < -temp.getRadiusY() * 2) {
                        root.getChildren().remove(temp);
                        projectiles.remove(i);
                    }else {
                        temp.setCenterY(temp.getCenterY() - 10);
                        temp.setFill(Color.WHITE);
                    }
                }
                try{
                    Thread.sleep(30);
                }catch (Exception e){
                    System.out.println("Projectile thread fcked up.");
                }
            }
        };
        projectilMover.start();

        this.setCursor(Cursor.NONE);
        getStylesheets().add("CSS.css");
        root.getChildren().add(player);
        root.setStyle("-fx-background-color: black");
    }

    private void escClicked(Stage window, MenuScene menuScene, Pane root){
        reset(root);
        window.setScene(menuScene);
        window.setFullScreen(true);
    }

    private void reset(Pane root){
        for(int i = 0; i < projectiles.size(); i++){
            root.getChildren().remove(projectiles.get(i));
        }
        player.setX((getWidth() / 2) - (player.getWidth() / 2));
        player.setY((getHeight() - 80));
        projectiles.clear();
    }
}
