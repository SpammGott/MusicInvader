import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.beans.EventHandler;

public class Controller extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window) throws Exception{
        BooleanProperty up = new SimpleBooleanProperty();
        BooleanProperty down = new SimpleBooleanProperty();
        BooleanProperty left = new SimpleBooleanProperty();
        BooleanProperty right = new SimpleBooleanProperty();

        Rectangle rect = new Rectangle(10, 10);

        Pane pane = new Pane(rect);

        Scene scene = new Scene(pane, 500, 600);
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.W){
                up.setValue(true);
            }
            if(e.getCode() == KeyCode.A){
                left.setValue(true);
            }
            if(e.getCode() == KeyCode.S){
                down.setValue(true);
            }
            if(e.getCode() == KeyCode.D){
                right.setValue(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.W){
                up.setValue(false);
            }
            if(e.getCode() == KeyCode.A){
                left.setValue(false);
            }
            if(e.getCode() == KeyCode.S){
                down.setValue(false);
            }
            if(e.getCode() == KeyCode.D){
                right.setValue(false);
            }
        });

        AnimationTimer shipControls = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (up.get())
                    rect.setY(rect.getY() - 2);
                if (left.get())
                    rect.setX(rect.getX() - 2);
                if (down.get())
                    rect.setY(rect.getY() + 2);
                if (right.get())
                    rect.setX(rect.getX() + 2);

            }
        };
        shipControls.start();

        window.setTitle("Testing");
        window.setScene(scene);
        window.show();
    }
}
