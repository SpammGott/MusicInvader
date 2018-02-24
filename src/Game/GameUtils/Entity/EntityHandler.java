package Game.GameUtils.Entity;


import Game.GameUtils.Utils.Spawnpoint;
import Game.GameUtils.Utils.Vector2D;
import MP3Player.SoundPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Handles all the entitys including the player
 * It updates entitys when they move, it handles firing, getting hit and spawning new enemies
 */
public class EntityHandler {
    private Image playerImageHeil;
    private Image playerImageKaputt;
    private Image enemyImage;
    private Image projectileImage;
    private Image explosion[];

    private List<Enemy> enemyList = new ArrayList<>();
    private List<Enemy> enemyExp = new ArrayList<>();
    private List<Projectile> playerP = new ArrayList<>();
    private List<Projectile> enemyP = new ArrayList<>();
    private ConcurrentLinkedQueue<Enemy> deleteEnemy = new ConcurrentLinkedQueue<>();

    private Pane root;
    private Player player;

    private boolean playerWasHit = false;
    private int frameToRespawn = 0;

    private IntegerProperty points = new SimpleIntegerProperty();

    private SoundPlayer playerSound = new SoundPlayer(System.getProperty("user.dir") + "/res/Sounds/playerShot1.mp3");
    private SoundPlayer enemeySound = new SoundPlayer(System.getProperty("user.dir") + "/res/Sounds/enemyShot1.mp3");

    public EntityHandler(Pane root, Image playerImageHeil, Image playerImageKaputt, Image enemyImage, Image projectileImage, Image explosion[]){
        this.root = root;
        this.playerImageHeil = playerImageHeil;
        this.playerImageKaputt = playerImageKaputt;
        this.enemyImage = enemyImage;
        this.projectileImage = projectileImage;
        this.player = new Player(this.playerImageHeil);
        this.explosion = explosion;
        root.getChildren().add(player.getBody());
        playerSound.volume(0.25f);
        enemeySound.volume(0.3f);
    }

    public void updateEntitys(){
        Enemy temp;
        player.move();
        moveAllProjectiles();
        moveAllEnemys();
        moveExplosions();

        while((temp = deleteEnemy.poll()) != null){
            enemyExp.remove(temp);
        }

        if(!playerWasHit){
            if(playerIsHit()) {
                playerWasHit = true;
                frameToRespawn = 120;
                player.changeImage(playerImageKaputt);
                player.decHp();
            }
        } else{
            if(frameToRespawn-- == 0){
                playerWasHit = false;
                player.changeImage(playerImageHeil);
            }
        }
        enemyIsHit();
    }

    public void firePlayer(){
        Projectile temp[] = player.fireProjectile(projectileImage);
        for(Projectile act:temp) {
            playerP.add(act);
            root.getChildren().add(act.getBody());
            playerSound.play();
        }
    }

    public void fireAllEnemys(){
        Projectile temp[];
        for(int i = 0; i < enemyList.size(); i++){
            Enemy act = enemyList.get(i);
            temp = act.fireProjectile(projectileImage, new Vector2D(-(act.getPos().getX() - player.getPos().getX()), -(act.getPos().getY() - player.getPos().getY())));
            for(Projectile actPro:temp) {
                enemyP.add(actPro);
                root.getChildren().add(actPro.getBody());
            }
            enemeySound.play();

        }
    }

    private boolean playerIsHit(){
        boolean isHit = false;
        List<Projectile> tempList = new LinkedList<>();
        for(Projectile act : enemyP){
            if(player.isHit(act.getHitbox())) {
                isHit = true;
                tempList.add(act);
            }
        }
        removeProjectile(enemyP, tempList);
        return isHit;
    }

    private void enemyIsHit(){
        List<Projectile> tempProjectileList = new LinkedList<>();
        boolean isHit;
        for(int i = 0; i < enemyList.size(); i++){
            isHit = false;
            Enemy act = enemyList.get(i);
            for(Projectile actPro: playerP){
                if(act.isHit(actPro.getHitbox())){
                    tempProjectileList.add(actPro);
                    isHit = true;
                }
            }
            if(isHit){
                explode(enemyList.get(i));
                enemyExp.add(enemyList.get(i));
                enemyList.remove(i);
                i--;
                points.setValue(points.getValue() + 10);
            }
        }
        removeProjectile(playerP, tempProjectileList);
    }

    private void explode(Enemy enemy){
        Timeline explo = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            enemy.body.setImage(explosion[enemy.incExplosionIndex()]);
            if (enemy.getExplosionIndex() == 4){
                root.getChildren().remove(enemy.getBody());
                deleteEnemy.add(enemy);
            }
        }));
        explo.setCycleCount(4);
        explo.play();
    }

    public void spawnEnemy(Spawnpoint spawnpoint){
        Enemy temp = new Enemy(spawnpoint, enemyImage);
        enemyList.add(temp);
        root.getChildren().add(temp.getBody());
    }

    private void moveAllEnemys(){
        for(int i = 0; i < enemyList.size(); i++){
            enemyList.get(i).move();
        }
    }

    private void moveAllProjectiles(){
        moveProjectileList(playerP);
        moveProjectileList(enemyP);
    }

    private void moveProjectileList(List<Projectile> list){
        for(int i = 0; i < list.size(); i++){
            Entity temp = list.get(i);
            temp.move();
            if (!temp.getPos().xinRange(0, 16) || !temp.getPos().yinRange(0, 16)) {
                root.getChildren().remove(temp.getBody());
                list.remove(i);
                i--;
            }
        }
    }

    private void moveExplosions(){
        for(Enemy actEnemy: enemyExp){
            actEnemy.move();
        }
    }

    public void removeAllEnemys(){
        for(Enemy temp : enemyList){
            root.getChildren().remove(temp.getBody());
        }
        enemyList.clear();
    }

    public void removeAllProjectiles(){
        for(Projectile act:playerP){
            root.getChildren().remove(act.getBody());
        }
        playerP.clear();
        for(Projectile act:enemyP){
            root.getChildren().remove(act.getBody());
        }
        enemyP.clear();
    }

    private void removeProjectile(List<Projectile> removeFrom, List<Projectile> remove){
        for(Projectile act:remove){
            removeFrom.remove(act);
            root.getChildren().remove(act.getBody());
        }
    }

    public Player getPlayer(){return player;}

    public IntegerProperty getHp(){return player.getHp();}

    public IntegerProperty getPoints(){return points;}

    public void setPoints(int i){points.set(i);}

    public void reset(){
        points.setValue(0);
        player.resetHp();
        player.reset();
    }

    public boolean isPlayerWasHit(){return playerWasHit; }

    public boolean isLeer(){
        return enemyList.size() == 0 && enemyP.size() == 0 && playerP.size() == 0;
    }
}
