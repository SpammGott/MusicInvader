import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameScene extends Scene {

    private int speed = 15;
    //needed to manage movement
    private BooleanProperty up = new SimpleBooleanProperty();
    private BooleanProperty down = new SimpleBooleanProperty();
    private BooleanProperty left = new SimpleBooleanProperty();
    private BooleanProperty right = new SimpleBooleanProperty();

    public GameScene(Pane root, double height, double width, Stage window){
        super(root, height, width);

        //PC = PlayerCharacter
        Rectangle rect = new Rectangle(50, 50);
        rect.setFill(Color.WHITE);
        rect.setStyle("-fx-background-color: white");

        rect.setX((width / 2) - (rect.getWidth() / 2));
        rect.setY((height - 80));

        setOnKeyPressed(keyEvent -> {
            //menu
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                setCursor(Cursor.DEFAULT);
                window.setScene(new MenuScene(height, width, window));
                window.setFullScreen(true);
            }
            //to manage movement
            if(keyEvent.getCode() == KeyCode.W){
                up.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.A){
                left.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.S){
                down.setValue(true);
            }
            if(keyEvent.getCode() == KeyCode.D){
                right.setValue(true);
            }
        });
        setOnKeyReleased(keyEvent -> {
            //to manage movement
            if(keyEvent.getCode() == KeyCode.W){
                up.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.A){
                left.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.S){
                down.setValue(false);
            }
            if(keyEvent.getCode() == KeyCode.D){
                right.setValue(false);
            }
        });

        //actual movement
        AnimationTimer shipControls = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double x = rect.getX();
                double y = rect.getY();

                if (up.get() && y > 0){
                    rect.setY(y - speed);
                }
                if (left.get() && x > 5) {
                    rect.setX(x - speed);
                }
                if (down.get() && y + 15 < height){
                    rect.setY(y + speed);
                }
                if (right.get() && x + rect.getWidth() + 5 < width) {
                    rect.setX(x + speed);
                }
            }
        };
        shipControls.start();

        getStylesheets().add("CSS.css");
        root.getChildren().add(rect);
        root.setStyle("-fx-background-color: black");
        setCursor(Cursor.NONE);
    }
}
