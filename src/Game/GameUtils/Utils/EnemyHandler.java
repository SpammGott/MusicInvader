package Game.GameUtils.Utils;

import Game.GameUtils.Entity.Enemy;
import Game.GameUtils.Entity.Player;
import Game.GameUtils.Entity.Spawnpoint;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class EnemyHandler {

    private List<Enemy> enemyList = new ArrayList<>();
    private Pane root;
    private ProjectileHandler projectileHandler;
    private Player player;

    public EnemyHandler(Pane root, ProjectileHandler projectileHandler, Player player){
        this.root = root;
        this.projectileHandler = projectileHandler;
        this.player = player;
    }


    public void spawnEnemy(Spawnpoint spawnpoint){
        Enemy temp = new Enemy(spawnpoint, projectileHandler);
        enemyList.add(temp);
        root.getChildren().add(temp.getBody());
        System.out.println(temp.toString2());
    }

    public void moveAll(){
        for(int i = 0; i < enemyList.size(); i++){
            enemyList.get(i).move();
        }
    }

    public void fireAll(){
        for(int i = 0; i < enemyList.size(); i++){
            Enemy act = enemyList.get(i);
            act.fireProjectile(new Vector2D(-(act.getPos().getX() - player.getPos().getX()), -(act.getPos().getY() - player.getPos().getY())));
        }
    }
}
