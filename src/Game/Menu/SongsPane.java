package Game.Menu;

import MP3Player.MP3Player;
import MP3Player.Playlist;
import MP3Player.Track;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import MP3Player.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

public class SongsPane extends VBox {

    private int index = 0;

    public SongsPane(BorderPane menuScene, VBox menu, Playlist list, MP3Player player, PlaylistManager playlistManager){
        List<Track> tracks = list.getList();

        Label text = new Label("SONGS:");
        text.setId("Headline");

        Button firstSong = new Button(tracks.get(0).getName());
        firstSong.setOnAction(event -> {
            player.stop();
            player.changePlaylist(playlistManager.getPlaylist("defaultPlaylist"));
            player.play(0);
        });

        Button secondSong = new Button(tracks.get(1).getName());
        secondSong.setOnAction(event -> {
            player.stop();
            player.changePlaylist(playlistManager.getPlaylist("defaultPlaylist"));
            player.play(1);
        });

        Button back = new Button("BACK");
        back.setOnAction(e -> {
            player.stop();
            player.changePlaylist(playlistManager.getPlaylist("titlesong"));
            player.play(0);
            menuScene.setCenter(menu);
        });

        getChildren().addAll(text, firstSong, secondSong, back);
        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void incIndex(){
        setIndex(getIndex() + 1);
    }

    public void decIndex(){
        setIndex(getIndex() - 1);
    }
}
