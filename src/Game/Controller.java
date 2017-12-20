package Game;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Controller extends Application{

    //creates 2D Rectangle with screen width and height
    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    private double height = primaryScreenBounds.getHeight();
    private double width = primaryScreenBounds.getWidth();

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window){

        MenuScene menuScene = new MenuScene(height, width, window);
        Scene scene = new Scene(menuScene);

        window.setTitle("Music Invader");
        window.setScene(scene);
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();
    }
}
