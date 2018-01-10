package Game.GameUtils.Utils;

import Game.GameUtils.Entity.Projectile;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class ProjectileHandler {
    private Pane root;
    private List<Projectile> playerP = new ArrayList<>();
    private List<Projectile> enemyP = new ArrayList<>();

    public ProjectileHandler(Pane root){
        this.root = root;
    }

    public void addProjectile(Projectile temp){
        if(temp.isFromPlayer())
            playerP.add(temp);
        else
            enemyP.add(temp);
        root.getChildren().add(temp.getBody());
    }

    public void moveAllProjectiles(){
        moveList(playerP);
        moveList(enemyP);
    }

    public void removeAll(){
        remove(playerP);
        remove(enemyP);
    }

    private void remove(List<Projectile> list){
        for(Projectile temp : list){
            root.getChildren().remove(temp.getBody());
        }
        list.clear();
    }

    private void moveList(List<Projectile> list){
        for(int i = 0; i < list.size(); i++){
            Projectile temp = list.get(i);
            temp.move();
            if (!temp.getPos().xinRange(0, 16) || !temp.getPos().yinRange(0, 16)) {
                root.getChildren().remove(temp.getBody());
                list.remove(i);
                i--;
            }
        }
    }
}
