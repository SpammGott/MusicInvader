package Game.GameUtils.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class Spawnpoint {

    private Vector2D pos;
    private Vector2D direction[];

    public Spawnpoint(Vector2D pos, Vector2D... direction){
        this.pos = pos;
        int i = 0;
        this.direction = new Vector2D[direction.length];
        for(Vector2D temp:direction){
            this.direction[i++] = MathUtils.getEinheitsvektor(temp);
        }
    }

    public Vector2D getRandomDirection(){
        return new Vector2D(direction[ThreadLocalRandom.current().nextInt(0, direction.length)]);
    }

    public Vector2D getPos() {return pos;}
}
