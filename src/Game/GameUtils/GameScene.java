package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Utils.Spawnpoint;
import Game.GameUtils.Utils.*;
import Game.Menu.MenuScene;
import MP3Player.MP3Player;
import MP3Player.SoundPlayer;
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

/**
 * The scene which shows the actual game
 * starts a new GameLoop and the FreqDetect function
 * ends the game if player HP fall under one
 */
public class GameScene extends Scene {
    private MP3Player mp3Player;
    private PlaylistManager playlistManager;

    private EntityHandler entityHandler;

    private Spawnpoint spawnpoint[] = { new Spawnpoint(new Vector2D(-0.75, 1), new Vector2D(1, 0.1)),
                                        new Spawnpoint(new Vector2D(-0.75, 0), new Vector2D(1, 0.2)),
                                        new Spawnpoint(new Vector2D(16.75, 1), new Vector2D(-1, 0.1)),
                                        new Spawnpoint(new Vector2D(16.75, 0), new Vector2D(-1, 0.2))};

    private Pane root;
    private HBox mainPane;
    private Pane game;
    private Pane left  = new Pane();
    private LeftGamePane gameInfos;
    private Stage window;
    private MenuScene menuScene;
    private ImageView background;
    private ImageView background2;
    private SoundPlayer sp;

    private Image playerImage;
    private Image playerImageKaputt;
    private Image enemyImage;
    private Image projectileImage;
    private Image backgroundImage;
    private Image explosion[] = new Image[5];

    private GameLoop gameLoop;
    private Thread beatDet;

    public GameScene(Pane root, Stage window, MenuScene menuScene, MP3Player player, PlaylistManager playlistManager, SoundPlayer sp){
        super(root, Helper.getHeight(), Helper.getWidth());
        this.root = root;
        this.mp3Player = player;
        this.playlistManager = playlistManager;

        backgroundImage = loadImage("Stars1.png");
        background = new ImageView(backgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(Helper.getAbsoluteWidth(16));
        background.setY(0);
        background2 = new ImageView(backgroundImage);
        background2.setPreserveRatio(true);
        background2.setFitWidth(Helper.getAbsoluteWidth(16));
        background2.setY(-background2.getImage().getHeight()/2);

        game = new Pane(background, background2);
        game.setPrefSize(Helper.getGameWidth(), Helper.getGameHeight());
        left.setStyle("-fx-background-color: #333333");
        left.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());

        this.window = window;
        this.menuScene = menuScene;
        this.sp = sp;

        initImages();
        entityHandler = new EntityHandler(game, playerImage, playerImageKaputt, enemyImage, projectileImage, explosion);
        gameInfos = new LeftGamePane(player, entityHandler);
        gameInfos.setPrefSize(Helper.getWidth() / 4, Helper.getHeight());
    }

    public void start(){
        ImageView temp[] = {background, background2};
        gameLoop = new GameLoop(entityHandler, temp);
        gameLoop.start();

        entityHandler.setPoints(0);

        BeatDetector.FreqDetect beater = new BeatDetector.FreqDetect(System.getProperty("user.dir") + "/res/Songs/" + mp3Player.getActualTrack().getName() + ".mp3", mp3Player.getGameScene());

        Task taskBeat = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("BeatDetect started");
                beater.run();
                return null;
            }
        };
        beatDet = new Thread(taskBeat);
        beatDet.setDaemon(true);
        beatDet.start();

        entityHandler.getHp().addListener(e -> {
            if (entityHandler.getHp().get() <= 0){
                try{
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_F4);
                }catch (Exception ex){
                    System.out.println("Robot in GameScene fucked up.");
                }
            }
        });

        mp3Player.getIsSkipped().addListener(e -> {
            if(mp3Player.getIsSkipped().getValue()) {
                gameLoop.pause(60);
                gameLoop.removeAll();
                long time = mp3Player.getActPlaylist().getPrevTrackWithoutChangingIndex().getLength()/1000;
                int points = entityHandler.getPoints().getValue();
                if(time < 120){
                    points *= 1.25;
                } else if(time < 180){
                    points *= 1.5;
                } else if(time < 240){
                    points *= 1.75;
                }
                entityHandler.setPoints(points);
                entityHandler.getPlayer().incHp();
            }
        });

        setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                reset();
                gameLoop.stopGameLoop();
                mp3Player.stop();
                sp.play();
                escClicked(window, menuScene);
            }else if(keyEvent.getCode() == KeyCode.F4){
                gameLoop.stopGameLoop();
                mp3Player.stop();
                sp.play();
                Pane test = new Pane();
                System.out.println(entityHandler.getPoints());
                DefeatScene defeat = new DefeatScene(test, window, menuScene, entityHandler);
                window.setScene(defeat);
                window.setFullScreen(true);
                reset();

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
        window.setScene(menuScene);
        window.setFullScreen(true);
    }

    private void reset(){
        entityHandler.getPlayer().setPos(new Vector2D(8 - (entityHandler.getPlayer().getWidth() / 2), 16 - (entityHandler.getPlayer().getHeight() /2)));
        entityHandler.reset();

        beatDet.interrupt();
        gameInfos.stop();
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

}
