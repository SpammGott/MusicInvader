package Game.GameUtils;

import Game.Helper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Player extends Rectangle{
    private Scene scene;
    private double x;
    private double y;
    //needed to manage movement
    private BooleanProperty up = new SimpleBooleanProperty();
    private BooleanProperty down = new SimpleBooleanProperty();
    private BooleanProperty left = new SimpleBooleanProperty();
    private BooleanProperty right = new SimpleBooleanProperty();

    private int speed;

    public Player(Scene scene, int speed){
        super(50, 50);
        this.scene = scene;
        this.speed = speed;
        setFill(Color.WHITE);
        setStyle("-fx-background-color: white");

        setX((scene.getWidth() / 2) - (getWidth() / 2));
        x = getX();
        setY((scene.getHeight() - 80));
        y = getY();

        if(!Helper.getControls())
            wasdKeys();
        else
            arrowKeys();
    }

    public void updatePlayer(){

    }

    public void movePlayer(){
        if (up.get() && y > 0){
            y -= speed;
            setY(y);
        }
        if (left.get() && x > 5) {
            x -= speed;
            setX(x);
        }
        if (down.get() && y + getHeight() * 1.25 < scene.getHeight()){
            y += speed;
            setY(y);
        }
        if (right.get() && x + getWidth() * 1.25 < scene.getWidth()) {
            x += speed;
            setX(x);
        }
    }

    public Projectile fireProjectile(){
        Projectile temp = new Projectile(this);
        //scene.getRoot().getChildrenUnmodifiable().add(temp);
        return temp;
    }

    private void wasdKeys(){
        scene.setOnKeyPressed(keyEvent -> {
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
        scene.setOnKeyReleased(keyEvent -> {
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
    }

    private void arrowKeys() {
        scene.setOnKeyPressed(keyEvent -> {
            //to manage movement
            if (keyEvent.getCode() == KeyCode.UP) {
                up.setValue(true);
            }
            if (keyEvent.getCode() == KeyCode.LEFT) {
                left.setValue(true);
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                down.setValue(true);
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                right.setValue(true);
            }
        });
        scene.setOnKeyReleased(keyEvent -> {
            //to manage movement
            if (keyEvent.getCode() == KeyCode.UP) {
                up.setValue(false);
            }
            if (keyEvent.getCode() == KeyCode.LEFT) {
                left.setValue(false);
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                down.setValue(false);
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                right.setValue(false);
            }
        });
    }
}
