package Game;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Utils.Spawnpoint;
import javafx.animation.AnimationTimer;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameLoop extends AnimationTimer {

    private EntityHandler entityHandler;
    private int frameToShoot = 0;
    private boolean removeAll = false;
    private boolean pause = false;
    private ConcurrentLinkedQueue<Spawnpoint> spawnQueue = new ConcurrentLinkedQueue<>();

    public GameLoop(EntityHandler entityHandler){
        super();
        this.entityHandler = entityHandler;
    }

    @Override
    public void handle(long now) {
        if(!pause) {
            if(!spawnQueue.isEmpty())
                entityHandler.spawnEnemy(spawnQueue.poll());

            if (frameToShoot % 10 == 0)
                entityHandler.firePlayer();
            if (frameToShoot == 20) {
                entityHandler.fireAllEnemys();
                frameToShoot = 0;
            }
            frameToShoot++;

            entityHandler.updateEntitys();

            if(removeAll) {
                entityHandler.removeAllProjectiles();
                entityHandler.removeAllEnemys();
            }
        }
    }

    public void spawnEnemy(Spawnpoint spawnpoint){
        spawnQueue.add(spawnpoint);
    }

    public void removeAll(){
        removeAll = true;
    }
}
