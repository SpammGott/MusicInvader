package Game.GameUtils;

import Game.GameUtils.Entity.Player;
import Game.GameUtils.Entity.Spawnpoint;
import Game.GameUtils.Utils.EnemyHandler;
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
    private EnemyHandler enemyHandler;
    private Spawnpoint spawnpoint[] = {new Spawnpoint(new Vector2D(-0.75, 7), new Vector2D(1, 0), new Vector2D(1, -0.05)), new Spawnpoint(new Vector2D(500, 500), new Vector2D(-1, 0))};

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
        enemyHandler = new EnemyHandler(root, projectileHandler, player);
        enemyHandler.spawnEnemy(spawnpoint[0]);

        //actual movement
        AnimationTimer gameLoop = new AnimationTimer() {
            int frameToShoot = 0;
            @Override
            public void handle(long now) {
                player.move();
                if(++frameToShoot == 20){
                    player.fireProjectile();
                    enemyHandler.fireAll();
                    frameToShoot = 0;
                }
                enemyHandler.moveAll();
                projectileHandler.moveAllProjectiles();
            }
        };
        gameLoop.start();

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
        reset();
        this.setCursor(Cursor.DEFAULT);
        window.setScene(menuScene);
        window.setFullScreen(true);
    }

    private void reset(){
        projectileHandler.removeAll();
        enemyHandler.removeAll();
        player.setPos(new Vector2D((getWidth() / 2) - (player.getBody().getWidth() / 2), (getHeight())));
    }
}
