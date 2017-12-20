import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Game extends Pane {

    private int speed = 15;
    //needed to manage movement
    private BooleanProperty up = new SimpleBooleanProperty();
    private BooleanProperty down = new SimpleBooleanProperty();
    private BooleanProperty left = new SimpleBooleanProperty();
    private BooleanProperty right = new SimpleBooleanProperty();

    public Game(double height, double width){

        //PC = PlayerCharacter
        Rectangle rect = new Rectangle(50, 50);
        rect.setFill(Color.WHITE);
        setStyle("-fx-background-color: black");

        rect.setX((width / 2) - (rect.getWidth() / 2));
        rect.setY((height - 80));

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
                if (down.get() && y < height){
                    rect.setY(y + speed);
                }
                if (right.get() && x + rect.getWidth() + 5 < width) {
                    rect.setX(x + speed);
                }
            }
        };
        shipControls.start();

        getChildren().add(rect);
    }

    //not needed at the moment, maybe to add enemies and shit
    public void addToPane(Node... children){
        getChildren().addAll(children);
    }

    public void setUp(boolean up) {
        this.up.setValue(up);
    }

    public void setDown(boolean down) {
        this.down.setValue(down);
    }

    public void setLeft(boolean left) {
        this.left.setValue(left);
    }

    public void setRight(boolean right) {
        this.right.setValue(right);
    }
}
