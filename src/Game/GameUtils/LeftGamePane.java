package Game.GameUtils;

import Game.GameUtils.Entity.EntityHandler;
import MP3Player.MP3Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LeftGamePane extends Pane {

    public LeftGamePane(MP3Player player, EntityHandler entityHandler){
        IntegerProperty a = new SimpleIntegerProperty(0);
        setStyle("-fx-background-color: #333333");

        Label lifePoints = new Label("LIFEPOINTS: ");
        lifePoints.setId("SideText");
        Label actLifePoints = new Label("3");
        actLifePoints.setId("SideText");

        entityHandler.getHp().addListener(e -> actLifePoints.setText(String.valueOf(entityHandler.getHp().get())));
        HBox hp = new HBox(lifePoints, actLifePoints);

        Label points = new Label("POINTS: ");
        points.setId("SideText");
        Label actPoints = new Label("0");
        actPoints.setId("SideText");
        HBox pointCont = new HBox(points, actPoints);

        entityHandler.getPoints().addListener(e -> actPoints.setText(String.valueOf(entityHandler.getPoints().get())));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> a.setValue(a.getValue() + 1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Label actSong = new Label(player.getActualTrack().getName());
        Label actNextSong = new Label(player.getActPlaylist().getNextTrackWithoutChangingIndex().getName());
        a.addListener(e -> {
            actSong.setText(player.getActualTrack().getName());
            actNextSong.setText(player.getActPlaylist().getNextTrackWithoutChangingIndex().getName());
        });

        Label currentSong = new Label("CURRENT SONG: ");
        currentSong.setId("SideText");
        actSong.setId("SideText");
        VBox curSong = new VBox(currentSong, actSong);

        Label nextSong = new Label("NEXT SONG:    ");
        nextSong.setId("SideText");
        actNextSong.setId("SideText");
        VBox nexSong = new VBox(nextSong, actNextSong);

        VBox songInfos = new VBox(curSong, nexSong);

        VBox cont = new VBox(hp, pointCont, songInfos);
        cont.setSpacing(50);

        getChildren().add(cont);
    }
}
