package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Utils.Spawnpoint;
import Game.GameUtils.Utils.*;
import Game.Menu.MenuScene;
import MP3Player.MP3Player;
import MP3Player.PlaylistManager;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Pane left  = new Pane();
    private Pane gameInfos;
    private Stage window;
    private MenuScene menuScene;
    private ImageView background;

    private Image playerImage;
    private Image playerImageKaputt;
    private Image enemyImage;
    private Image projectileImage;
    private Image backgroundImage;
    private Image explosion[] = new Image[5];

    private GameLoop gameLoop;

    public GameScene(Pane root, Stage window, MenuScene menuScene, MP3Player player, PlaylistManager playlistManager){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        this.mp3Player = player;
        this.playlistManager = playlistManager;
        backgroundImage = loadImage("Stars1.png");
        background = new ImageView(backgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(Helper.getAbsoluteWidth(16));
        //background.setY(-(background.getFitHeight()/2));
        game = new Pane(background);
        game.setPrefSize(Helper.getGameWidth(), Helper.getGameHeight());
        left.setStyle("-fx-background-color: #333333");
        left.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());
        this.window = window;
        this.menuScene = menuScene;
        initImages();
        entityHandler = new EntityHandler(game, playerImage, playerImageKaputt, enemyImage, projectileImage, explosion);
        gameInfos = new LeftGamePane(player, entityHandler);
        gameInfos.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());
    }

    public void start(){
        gameLoop = new GameLoop(entityHandler, background);
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
            //mp3Player.setTickrate(1 + 0.25f * entityHandler.getHp().get());
            mp3Player.setTickrate(2);
            if (entityHandler.getHp().get() == 0){
                try{
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_F4);
                }catch (Exception ex){

                    System.out.println("Robot in GameScene fucked up.");
                }
            }
        });

        setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                reset();
                gameLoop.stopGameLoop();
                mp3Player.stop();
                escClicked(window, menuScene);
            }else if(keyEvent.getCode() == KeyCode.F4){
                reset();
                gameLoop.stopGameLoop();
                mp3Player.stop();
                Pane test = new Pane();
                DefeatScene defeat = new DefeatScene(test, window, menuScene, entityHandler);
                window.setScene(defeat);
                window.setFullScreen(true);
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
    }

    private void reset(){
        entityHandler.getPlayer().setPos(new Vector2D(8 - (entityHandler.getPlayer().getWidth() / 2), 16 - (entityHandler.getPlayer().getHeight() /2)));
        mp3Player.changePlaylist(playlistManager.getPlaylist("titlesong"));
        mp3Player.play(0);
    }

    private void initImages(){
        playerImage = loadImage("Assets/MirrorFighter_no1.png");
        playerImageKaputt = loadImage("Assets/MirrorFighter_no1-red.png");
        enemyImage = loadImage("Assets/Triwing_no1.png");
        projectileImage = loadImage("Assets/ProjektilFüller.png");
        for(int i = 0; i < explosion.length; i++){
            explosion[i] = loadImage("Assets/Explo" + (i+1) + ".png");
        }
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
        gameLoop.spawnEnemy(spawnpoint[(int)(Math.random() * spawnpoint.length)]);
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }
}
