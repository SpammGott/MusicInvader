package Game.GameUtils.Utils;

import Game.GameUtils.Entity.Enemy;
import Game.GameUtils.Entity.Spawnpoint;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class EnemyHandler {

    private List<Enemy> enemyList = new ArrayList<>();
    private Pane root;

    public EnemyHandler(Pane root){
        this.root = root;
    }


    public void spawnEnemy(Spawnpoint spawnpoint){
        Enemy temp = new Enemy(spawnpoint);
        enemyList.add(temp);
        root.getChildren().add(temp.getBody());
    }

    public void moveAll(){
        for(Enemy act : enemyList){
            act.move();
        }
    }
}
