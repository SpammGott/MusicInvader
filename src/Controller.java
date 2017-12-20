import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Controller extends Application{

    //creates 2D Rectangle with screen width and height
    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window){
        Game game = new Game(primaryScreenBounds.getHeight(), primaryScreenBounds.getWidth());

        Scene gameScene = new Scene(game, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        gameScene.getStylesheets().add("CSS.css");
        gameScene.setCursor(Cursor.NONE);
        //manages movement
        gameScene.setOnKeyPressed(e -> {
            System.out.println("Key Pressed");
            if(e.getCode() == KeyCode.W){
                game.setUp(true);
            }
            if(e.getCode() == KeyCode.A){
                game.setLeft(true);
            }
            if(e.getCode() == KeyCode.S){
                game.setDown(true);
            }
            if(e.getCode() == KeyCode.D){
                game.setRight(true);
            }
        });
        gameScene.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.W){
                game.setUp(false);
            }
            if(e.getCode() == KeyCode.A){
                game.setLeft(false);
            }
            if(e.getCode() == KeyCode.S){
                game.setDown(false);
            }
            if(e.getCode() == KeyCode.D){
                game.setRight(false);
            }
        });


        Menu menu = new Menu(window, gameScene);

        Scene menuScene = new Scene(menu, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        menuScene.getStylesheets().add("Css.css");

        //menu
        gameScene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                gameScene.setCursor(Cursor.DEFAULT);
                window.setScene(menuScene);
                window.setFullScreen(true);
            }
        });

        window.setTitle("Music Invader");
        window.setScene(menuScene);
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();
    }
}
