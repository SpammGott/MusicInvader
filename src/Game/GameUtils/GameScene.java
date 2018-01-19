package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Entity.Player;
import Game.GameUtils.Utils.Spawnpoint;
import Game.GameUtils.Utils.*;
import Game.Menu.MenuScene;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class GameScene extends Scene {
    private MP3Player mp3Player;
    private PlaylistManager playlistManager;

    private EntityHandler entityHandler;
    //first spawn is top left, to the right slightly downwards
    //second spawn as first just inverted
    private Spawnpoint spawnpoint[] = {new Spawnpoint(new Vector2D(-0.75, 1), new Vector2D(1, 0.1)), new Spawnpoint(new Vector2D(17, 1), new Vector2D(-1, 0.1))};

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
        gameInfos.setStyle("-fx-background-color: #333333");
        gameInfos.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());
        this.window = window;
        this.menuScene = menuScene;
        playerImage = loadImage("Assets/MirrorFighter_no1.png");
        enemyImage = loadImage("Assets/Triwing_no1.png");
        projectileImage = loadImage("Assets/ProjektilFüller.png");
        entityHandler = new EntityHandler(game, playerImage, enemyImage, projectileImage);
        left = new LeftGamePane(player, entityHandler);
        left.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());
    }

    public void start(){

        //actual movement
        AnimationTimer gameLoop = new AnimationTimer() {
            int frameToShoot = 0;
            @Override
            public void handle(long now) {
                entityHandler.updateEntitys();
                if(frameToShoot % 10 == 0)
                    entityHandler.firePlayer();
                if(frameToShoot == 35){
                    entityHandler.fireAllEnemys();
                    frameToShoot = 0;
                }
                frameToShoot++;
            }
        };
        gameLoop.start();

        BeatDetector.FreqDetect beater = new BeatDetector.FreqDetect(System.getProperty("user.dir") + "/res/Songs/" + mp3Player.getActualTrack().getName() + ".mp3", mp3Player.getGameScene());

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Task started");
                beater.run();
                return null;
            }
        };
        Thread beatDet = new Thread(task);
        beatDet.setDaemon(true);
        beatDet.start();

        entityHandler.getHp().addListener(e -> {
            if (entityHandler.getHp().get() == 0){
                gameLoop.stop();
                reset();
                mp3Player.stop();
                mp3Player.changePlaylist(playlistManager.getPlaylist("titlesong"));
                mp3Player.play(0);
                DefeatScene defeat = new DefeatScene(root, window, menuScene);
                window.setScene(defeat);
                window.setFullScreen(true);

                /*
                try{
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_ESCAPE);
                }catch (Exception ex){
                    System.out.println("Robot in GameScene fcked up.");
                }
                */
            }
        });

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

    public void spawnEnemy(){
        entityHandler.spawnEnemy(spawnpoint[(int)(Math.random() * spawnpoint.length)]);
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }
}
