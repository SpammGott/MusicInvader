package Game.GameUtils.Utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * a Spawnpoint has a direction and position
 * The direction defines the direction of the entitys being spawned
 */
public class Spawnpoint {

    private Vector2D pos;
    private Vector2D direction[];

    public Spawnpoint(Vector2D pos, Vector2D... direction){
        this.pos = pos;
        int i = 0;
        //Halb drei Nachts, ist das so richtig?^^
        this.direction = new Vector2D[direction.length];
        for(Vector2D temp:direction){
            this.direction[i++] = MathUtils.getEinheitsvektor(temp);
        }
    }

    public Vector2D getRandomDirection(){
        return new Vector2D(direction[ThreadLocalRandom.current().nextInt(0, direction.length)]);
    }

    //public void setPos(Vector2D pos) {this.pos = pos;}

    public Vector2D getPos() {return pos;}

    //public void setDirection(Vector2D[] direction) {this.direction = direction;}

    public Vector2D[] getDirection() {return direction;}


}
