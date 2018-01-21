package Game.GameUtils.Entity;


import Game.GameUtils.Utils.Spawnpoint;
import Game.GameUtils.Utils.Vector2D;
import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EntityHandler {
    private Image playerImage;
    private Image enemyImage;
    private Image projectileImage;
    private List<Enemy> enemyList = new ArrayList<>();
    private Pane root;
    private Player player;
    private List<Projectile> playerP = new ArrayList<>();
    private List<Projectile> enemyP = new ArrayList<>();

    public EntityHandler(Pane root, Image playerImage, Image enemyImage, Image projectileImage){
        this.root = root;
        this.playerImage = playerImage;
        this.enemyImage = enemyImage;
        this.projectileImage = projectileImage;
        this.player = new Player(this.playerImage);
        root.getChildren().add(player.getBody());
    }

    public void updateEntitys(){
        player.move();
        moveAllProjectiles();
        moveAllEnemys();
        if(playerIsHit()) {
            if(player.getHp().get() > 0)
                player.decHp();
        }
        enemyIsHit();
    }

    public void firePlayer(){
        Projectile temp[] = player.fireProjectile(projectileImage);
        for(Projectile act:temp) {
            playerP.add(act);
            root.getChildren().add(act.getBody());
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
        for(int i = 0; i < enemyList.size(); i++){
            Enemy act = enemyList.get(i);
            for(Projectile actPro: playerP){
                if(act.isHit(actPro.getHitbox())){
                    tempProjectileList.add(actPro);
                    enemyList.remove(i);
                    root.getChildren().remove(act.getBody());
                    i--;
                }
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
}
