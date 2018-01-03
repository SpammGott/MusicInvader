package Game.GameUtils.Utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Helper {

    private static boolean controls = true;
    //creates 2D Rectangle with screen width and height
    private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    private static double height = primaryScreenBounds.getHeight();
    private static double relativeHeight = height / 16;
    private static double width = primaryScreenBounds.getWidth();
    private static double relativeWidth = width / 16;
    private static double relation = width / height;
    private static double screenRez = 600;

    public static boolean getControls() {
        return controls;
    }

    public static void setControls(boolean boo) {
        controls = boo;
    }

    public static double getHeight(){return height;}
    //public static double getHeight(){return screenRez;}

    public static double getWidth(){return width;}
    //public static double getWidth(){return screenRez * relation;}

    public static int getAbsoluteWidth(double x){
        return (int)(x * relativeWidth);
    }

    public static int getAbsoluteHeight(double y){
        return (int)(y * relativeHeight);
    }
}
