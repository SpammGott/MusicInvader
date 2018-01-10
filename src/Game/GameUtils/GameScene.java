package Game.GameUtils;

import Game.GameUtils.Entity.Player;
import Game.GameUtils.Entity.Spawnpoint;
import Game.GameUtils.Utils.*;
import Game.Menu.MenuScene;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class GameScene extends Scene {

    private Player player;
    private ProjectileHandler projectileHandler;
    private EnemyHandler enemyHandler;
    private Spawnpoint spawnpoint[] = {new Spawnpoint(new Vector2D(-0.75, 7), new Vector2D(1, 0), new Vector2D(1, -0.05)), new Spawnpoint(new Vector2D(500, 500), new Vector2D(-1, 0))};

    private Pane root;
    private HBox mainPane;
    private Pane game;
    private Pane left;
    private Pane gameInfos = new Pane();
    private Stage window;
    private MenuScene menuScene;

    private Image playerImage;
    private Image enemyImage;

    public GameScene(Pane root, Stage window, MenuScene menuScene){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        game = new Pane();
        game.setPrefSize(Helper.getGameWidth(), Helper.getGameHeight());
        left = new Pane();
        left.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());
        this.window = window;
        this.menuScene = menuScene;
        playerImage = loadImage("Assets/MirrorFighter_no1.png");
        enemyImage = loadImage("Assets/Triwing_no1.png");
    }

    public void start(){
        //PC = PlayerCharacter
        projectileHandler = new ProjectileHandler(game);
        player = new Player(projectileHandler, playerImage);
        enemyHandler = new EnemyHandler(game, projectileHandler, player);
        enemyHandler.spawnEnemy(spawnpoint[0], enemyImage);

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
                escClicked(window, menuScene);
            } else {
                player.changeMovement(keyEvent);
            }
        });

        setOnKeyReleased(keyEvent ->{
            player.changeMovement(keyEvent);
        });

        this.setCursor(Cursor.NONE);
        getStylesheets().add("CSS.css");
        game.getChildren().add(player.getBody());
        game.setStyle("-fx-background-color: black");

        mainPane = new HBox(left, game, gameInfos);
        root.getChildren().add(mainPane);
    }

    private Image loadImage(String name){
        BufferedImage image = null;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(name)) {
            image = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    private void escClicked(Stage window, MenuScene menuScene){
        reset();
        this.setCursor(Cursor.DEFAULT);
        window.setScene(menuScene);
        window.setFullScreen(true);
    }

    private void reset(){
        projectileHandler.removeAll();
        enemyHandler.removeAll();
        player.setPos(new Vector2D((getWidth() / 2) - (player.getWidth() / 2), (getHeight())));
    }
}
