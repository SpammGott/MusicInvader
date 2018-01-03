package Game.GameUtils;

import Game.GameUtils.Entity.Player;
import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.ProjectileHandler;
import Game.GameUtils.Utils.Vector2D;
import Game.Menu.MenuScene;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameScene extends Scene {

    private Player player;
    private ProjectileHandler projectileHandler;

    private Pane root;
    private Stage window;
    private MenuScene menuScene;

    public GameScene(Pane root, Stage window, MenuScene menuScene){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        this.window = window;
        this.menuScene = menuScene;
    }

    public void start(){
        //PC = PlayerCharacter
        projectileHandler = new ProjectileHandler(root);
        player = new Player(this, projectileHandler);

        //actual movement
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.move();
                projectileHandler.moveAllProjectiles();
            }
        };
        gameLoop.start();

        //creates a projectile every 333ms and adds it to an arraylist
        Timeline pcProjectiles = new Timeline(
                new KeyFrame(Duration.millis(300),
                        e -> {
                            player.fireProjectile();
                        })
        );
        pcProjectiles.setCycleCount(Animation.INDEFINITE);
        pcProjectiles.play();

        setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                escClicked(window, menuScene, root);
            } else {
                player.changeMovement(keyEvent);
            }
        });

        setOnKeyReleased(keyEvent ->{
            player.changeMovement(keyEvent);
        });

        this.setCursor(Cursor.NONE);
        getStylesheets().add("CSS.css");
        root.getChildren().add(player.getBody());
        root.setStyle("-fx-background-color: black");
    }

    private void escClicked(Stage window, MenuScene menuScene, Pane root){
        reset(root);
        this.setCursor(Cursor.DEFAULT);
        window.setScene(menuScene);
        window.setFullScreen(true);
    }

    private void reset(Pane root){
        projectileHandler.removeAll();
        player.setPos(new Vector2D((getWidth() / 2) - (player.getBody().getWidth() / 2), (getHeight()/2)));
    }
}
