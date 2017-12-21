package Game;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Controller extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window){

        MenuScene menuScene = new MenuScene(window);
        Scene scene = new Scene(menuScene);

        window.setTitle("Music Invader");
        window.setScene(scene);
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();
    }
}
