package Game.GameUtils.Entity;


import Game.GameUtils.Utils.Spawnpoint;
import Game.GameUtils.Utils.Vector2D;
import MP3Player.SoundPlayer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EntityHandler {
    private Image playerImageHeil;
    private Image playerImageKaputt;
    private Image enemyImage;
    private Image projectileImage;
    private List<Enemy> enemyList = new ArrayList<>();
    private Pane root;
    private Player player;
    private List<Projectile> playerP = new ArrayList<>();
    private List<Projectile> enemyP = new ArrayList<>();
    private boolean playerWasHit = false;
    private int frameToRespawn = 0;

    private IntegerProperty points = new SimpleIntegerProperty();

    private SoundPlayer playerSound = new SoundPlayer(System.getProperty("user.dir") + "/res/Sounds/playerShot1.mp3");
    private SoundPlayer enemeySound = new SoundPlayer(System.getProperty("user.dir") + "/res/Sounds/enemyShot1.mp3");

    public EntityHandler(Pane root, Image playerImageHeil, Image playerImageKaputt, Image enemyImage, Image projectileImage){
        this.root = root;
        this.playerImageHeil = playerImageHeil;
        this.playerImageKaputt = playerImageKaputt;
        this.enemyImage = enemyImage;
        this.projectileImage = projectileImage;
        this.player = new Player(this.playerImageHeil);
        root.getChildren().add(player.getBody());
        playerSound.volume(0.2f);
    }

    public void updateEntitys(){
        player.move();
        moveAllProjectiles();
        moveAllEnemys();
        if(!playerWasHit){
            if(playerIsHit()) {
                playerWasHit = true;
                frameToRespawn = 120;
                player.changeImage(playerImageKaputt);
                if (player.getHp().get() > 0)
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
                enemyList.remove(i);
                root.getChildren().remove(act.getBody());
                i--;
                points.setValue(points.getValue() + 1);
            }
        }
        removeProjectile(playerP, tempProjectileList);
    }


    public void spawnEnemy(Spawnpoint spawnpoint){
        Enemy temp = new Enemy(spawnpoint, enemyImage);
        enemyList.add(temp);
        root.getChildren().add(temp.getBody());
    }

    public void moveAllEnemys(){
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

    public void removeAllEnemys(){
        for(Enemy temp : enemyList){
            root.getChildren().remove(temp.getBody());
        }
        enemyList.clear();
    }

    public void removeAllProjectiles(){
        removeProjectile(playerP, playerP);
        removeProjectile(enemyP, enemyP);
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

    public void resetPoints(){points.setValue(0);}

    public boolean isPlayerWasHit(){return playerWasHit; }

    public boolean isLeer(){
        if(enemyList.size() == 0 && enemyP.size() == 0 && playerP.size() == 0){
            return true;
        }
        return false;
    }
}
