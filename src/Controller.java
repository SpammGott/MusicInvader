import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.beans.EventHandler;

public class Controller extends Application{

    private int speed = 8;
    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window) throws Exception{
        //needed to manage movement
        BooleanProperty up = new SimpleBooleanProperty();
        BooleanProperty down = new SimpleBooleanProperty();
        BooleanProperty left = new SimpleBooleanProperty();
        BooleanProperty right = new SimpleBooleanProperty();

        //PC
        Rectangle rect = new Rectangle(10, 10);

        Pane pane = new Pane(rect);

        Scene scene = new Scene(pane, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        //manage movement
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

        //inital postion of PC
        rect.setX((scene.getWidth() / 2) - (rect.getWidth() / 2));
        rect.setY((scene.getHeight() - 80));

        //actual movement
        AnimationTimer shipControls = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (up.get())
                    rect.setY(rect.getY() - speed);
                if (left.get())
                    rect.setX(rect.getX() - speed);
                if (down.get())
                    rect.setY(rect.getY() + speed);
                if (right.get())
                    rect.setX(rect.getX() + speed);

            }
        };
        shipControls.start();

        window.setTitle("Testing");
        window.setScene(scene);
        window.setFullScreen(true);
        window.show();
    }


}
