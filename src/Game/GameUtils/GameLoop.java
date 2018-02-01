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
    private int timeToPause = 0;
    private boolean removeAll = false;
    private boolean pause = false;
    private boolean stop = false;
    private ConcurrentLinkedQueue<Spawnpoint> spawnQueue = new ConcurrentLinkedQueue<>();

    private ImageView background1;
    private ImageView background2;

    public GameLoop(EntityHandler entityHandler, ImageView background[]){
        super();
        this.entityHandler = entityHandler;
        this.background1 = background[0];
        this.background2 = background[1];

    }

    @Override
    public void handle(long now) {
        if(!pause && timeToPause == 0) {
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
            entityHandler.enemyExplosion();

            if (background1.getY() > Helper.getAbsoluteHeight(16))
                background1.setY(-background1.getImage().getHeight()/2);
            if (background2.getY() > Helper.getAbsoluteHeight(16))
                background2.setY(-background2.getImage().getHeight()/2);
            background1.setY(background1.getY() + Helper.getAbsoluteHeight(0.03));
            background2.setY(background2.getY() + Helper.getAbsoluteHeight(0.03));

        } else if(timeToPause > 0){
            timeToPause--;
            spawnQueue.clear();
        } else {
            spawnQueue.clear();
        }


        if(removeAll) {
            entityHandler.removeAllProjectiles();
            entityHandler.removeAllEnemys();
            removeAll = false;
        }

        if(stop){
            if(entityHandler.isLeer()){
                stop = false;
                stop();
            }
        }
    }

    public void spawnEnemy(Spawnpoint spawnpoint){
        spawnQueue.add(spawnpoint);
    }

    public void removeAll(){
        removeAll = true;
    }

    private void pause(){
        pause = true;
    }

    public void pause(int time){
        timeToPause = time;
    }

    public void stopGameLoop(){
        pause();
        removeAll();
        stop = true;
    }

    public void continueLoop(){
        pause = false;
    }
}
