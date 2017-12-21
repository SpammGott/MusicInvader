package Game;

import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Controller extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window){

        BorderPane menuPane = new BorderPane();

        MenuScene menuScene = new MenuScene(window, menuPane);

        window.setTitle("Music Invader");
        window.setScene(menuScene);
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.show();
    }
}
