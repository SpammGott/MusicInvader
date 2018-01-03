package Game.GameUtils.Entity;

import Game.GameUtils.Utils.Vector2D;

import java.util.concurrent.ThreadLocalRandom;

public class Spawnpoint {

    private Vector2D pos;
    private Vector2D direction[];

    public Spawnpoint(Vector2D pos, Vector2D... direction){
        this.pos = pos;
        int i = 0;
        //Halb drei Nachts, ist das so richtig?^^
        this.direction = new Vector2D[direction.length];
        for(Vector2D temp:direction){
            if(temp.getX() != 0)
                temp.setX(1);
            if(temp.getY() != 0)
                temp.setY(1);
            this.direction[i++] = temp;
        }
    }

    public Vector2D getRandomDirection(){
        return direction[ThreadLocalRandom.current().nextInt(0, direction.length)];
    }

    public void setPos(Vector2D pos) {this.pos = pos;}

    public Vector2D getPos() {return pos;}

    public void setDirection(Vector2D[] direction) {this.direction = direction;}

    public Vector2D[] getDirection() {return direction;}


}
