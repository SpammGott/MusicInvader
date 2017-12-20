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

public class GameScene extends Scene {

    private int speed = 15;
    private boolean arrowKeys = false;
    //needed to manage movement
    private BooleanProperty up = new SimpleBooleanProperty();
    private BooleanProperty down = new SimpleBooleanProperty();
    private BooleanProperty left = new SimpleBooleanProperty();
    private BooleanProperty right = new SimpleBooleanProperty();

    public GameScene(Pane root, double height, double width, Stage window){
        super(root, height, width);

        //PC = PlayerCharacter
        Rectangle player = new Rectangle(50, 50);
        player.setFill(Color.WHITE);
        player.setStyle("-fx-background-color: white");

        player.setX((width / 2) - (player.getWidth() / 2));
        player.setY((height - 80));

        if(!Helper.getControls())
            wasdKeys(window, height, width);
        else
            arrowKeys(window, height, width);

        //actual movement
        AnimationTimer shipControls = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double x = player.getX();
                double y = player.getY();

                if (up.get() && y > 0){
                    player.setY(y - speed);
                }
                if (left.get() && x > 5) {
                    player.setX(x - speed);
                }
                if (down.get() && y + 15 < height){
                    player.setY(y + speed);
                }
                if (right.get() && x + player.getWidth() + 5 < width) {
                    player.setX(x + speed);
                }
            }
        };
        shipControls.start();

        //creates a projectile every 333ms and adds it to an arraylist
        ArrayList<Ellipse> projectiles = new ArrayList();
        Timeline pcProjectiles = new Timeline(
                new KeyFrame(Duration.millis(100),
                e -> {
                    Ellipse temp = new Ellipse(8, 15);
                    temp.setCenterX(player.getX() + player.getWidth() / 2);
                    temp.setCenterY(player.getY());
                    temp.setFill(Color.WHITE);
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
                    if (temp.getCenterY() < 0) {
                        root.getChildren().remove(temp);
                        projectiles.remove(i);
                    }else {
                        temp.setCenterY(temp.getCenterY() - 10);
                        temp.setFill(Color.WHITE);
                    }
                }
            }
        };
        projectilMover.start();

        getStylesheets().add("CSS.css");
        root.getChildren().add(player);
        root.setStyle("-fx-background-color: black");
        setCursor(Cursor.NONE);
    }

    private void wasdKeys(Stage window, double height, double width){
        setOnKeyPressed(keyEvent -> {
            //menu
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                setCursor(Cursor.DEFAULT);
                window.setScene(new Scene(new MenuScene(height, width, window)));
                window.setFullScreen(true);
            }
            //to manage movement
            if(keyEvent.getCode() == KeyCode.W){
                up.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.A){
                left.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.S){
                down.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.D){
                right.setValue(true);
            }
        });
        setOnKeyReleased(keyEvent -> {
            //to manage movement
            if(keyEvent.getCode() == KeyCode.W){
                up.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.A){
                left.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.S){
                down.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.D){
                right.setValue(false);
            }
        });
    }

    private void arrowKeys(Stage window, double height, double width){
        setOnKeyPressed(keyEvent -> {
            //menu
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                setCursor(Cursor.DEFAULT);
                window.setScene(new Scene(new MenuScene(height, width, window)));
                window.setFullScreen(true);
            }
            //to manage movement
            if(keyEvent.getCode() == KeyCode.UP){
                up.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.LEFT){
                left.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.DOWN){
                down.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.RIGHT){
                right.setValue(true);
            }
        });
        setOnKeyReleased(keyEvent -> {
            //to manage movement
            if(keyEvent.getCode() == KeyCode.UP){
                up.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.LEFT){
                left.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.DOWN){
                down.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.RIGHT){
                right.setValue(false);
            }
        });
    }

    public void setArrowKeys(boolean boo){
        this.arrowKeys = boo;
    }
}
