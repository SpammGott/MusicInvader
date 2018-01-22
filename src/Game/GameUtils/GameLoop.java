package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import Game.GameUtils.Utils.Helper;
import Game.GameUtils.Utils.Spawnpoint;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameLoop extends AnimationTimer {

    private EntityHandler entityHandler;
    private int frameToShoot = 0;
    private boolean removeAll = false;
    private boolean pause = false;
    private ConcurrentLinkedQueue<Spawnpoint> spawnQueue = new ConcurrentLinkedQueue<>();

    private ImageView background;

    public GameLoop(EntityHandler entityHandler, ImageView background){
        super();
        this.entityHandler = entityHandler;
        this.background = background;
    }

    @Override
    public void handle(long now) {
        if(!pause) {
            if(!spawnQueue.isEmpty())
                entityHandler.spawnEnemy(spawnQueue.poll());

            if (frameToShoot % 10 == 0)
                if (!entityHandler.isPlayerWasHit())
                    entityHandler.firePlayer();
            if (frameToShoot == 30) {
                entityHandler.fireAllEnemys();
                frameToShoot = 0;
            }
            frameToShoot++;

            entityHandler.updateEntitys();

            background.setY(background.getY() + Helper.getAbsoluteHeight(0.05));

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

    public void pause(){
        pause = true;
    }

    public void continueLoop(){
        pause = false;
    }
}
