package Game.GameUtils.Utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Static Helper class, handles controltype and knows how large the screen is
 */
public class Helper {

    private static boolean controls = false;

    private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
    private static double gameHeight = primaryScreenBounds.getHeight();
    private static double relativeHeight = gameHeight / 16;
    private static double width = primaryScreenBounds.getWidth();
    private static double gameWidth = width * 0.5;
    private static double relativeWidth = gameWidth / 16;

    public static boolean getControls() {
        return controls;
    }

    public static void setControls(boolean boo) {
        controls = boo;
    }

    public static double getHeight(){return primaryScreenBounds.getHeight();}
    public static double getGameHeight(){return gameHeight;}

    public static double getWidth(){return width;}
    public static double getGameWidth(){return gameWidth;}

    public static int getAbsoluteWidth(double x){
        return (int)(x * relativeWidth);
    }

    public static int getAbsoluteHeight(double y){
        return (int)(y * relativeHeight);
    }
}
