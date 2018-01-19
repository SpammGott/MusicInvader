package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Entity.Player;
import Game.GameUtils.Utils.Spawnpoint;
import Game.GameUtils.Utils.*;
import Game.Menu.MenuScene;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
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
    private MP3Player mp3Player;
    private PlaylistManager playlistManager;

    private EntityHandler entityHandler;
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
    private Image projectileImage;

    public GameScene(Pane root, Stage window, MenuScene menuScene, MP3Player player, PlaylistManager playlistManager){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        this.mp3Player = player;
        this.playlistManager = playlistManager;
        game = new Pane();
        game.setPrefSize(Helper.getGameWidth(), Helper.getGameHeight());
        left = new Pane();
        left.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());
        this.window = window;
        this.menuScene = menuScene;
        playerImage = loadImage("Assets/MirrorFighter_no1.png");
        enemyImage = loadImage("Assets/Triwing_no1.png");
        projectileImage = loadImage("Assets/ProjektilFüller.png");
    }

    public void start(){
        //PC = PlayerCharacter
        entityHandler = new EntityHandler(game, playerImage, enemyImage, projectileImage);
        entityHandler.spawnEnemy(spawnpoint[0]);

        //actual movement
        AnimationTimer gameLoop = new AnimationTimer() {
            int frameToShoot = 0;
            @Override
            public void handle(long now) {
                entityHandler.updateEntitys();
                //if(++frameToShoot == 10)
                   // entityHandler.firePlayer();
                if(++frameToShoot == 20){
                    entityHandler.firePlayer();
                    entityHandler.fireAllEnemys();
                    frameToShoot = 0;
                }
                /*
                try {
                    Thread.sleep(10);
                }catch (Exception e){

                }
                */
            }
        };
        gameLoop.start();

        setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                gameLoop.stop();
                mp3Player.stop();
                mp3Player.changePlaylist(playlistManager.getPlaylist("titlesong"));
                mp3Player.play(0);
                escClicked(window, menuScene);
            } else {
                entityHandler.getPlayer().changeMovement(keyEvent);
            }
        });

        setOnKeyReleased(keyEvent ->{
            entityHandler.getPlayer().changeMovement(keyEvent);
        });

        this.setCursor(Cursor.NONE);
        getStylesheets().add("CSS.css");
        game.setStyle("-fx-background-color: black");

        mainPane = new HBox(left, game, gameInfos);
        root.getChildren().add(mainPane);
    }

    private void escClicked(Stage window, MenuScene menuScene){
        this.setCursor(Cursor.DEFAULT);
        window.setScene(menuScene);
        window.setFullScreen(true);
        reset();
    }

    private void reset(){
        entityHandler.removeAllEnemys();
        entityHandler.removeAllProjectiles();
        entityHandler.getPlayer().setPos(new Vector2D((getWidth() / 2) - (entityHandler.getPlayer().getWidth() / 2), (getHeight())));
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
}
