package Game.Menu;

import MP3Player.MP3Player;
import MP3Player.Playlist;
import MP3Player.Track;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import MP3Player.PlaylistManager;
import MP3Player.SoundPlayer;

import java.util.List;

/**
 * shows the songs which are played inGame
 * allows to listen to specific songs and to start the game with the selected song
 */
public class SongsPane extends VBox {

    private int index = 0;

    public SongsPane(BorderPane menuScene, VBox menu, Playlist list, MP3Player player, PlaylistManager playlistManager, SoundPlayer soundp){
        List<Track> tracks = list.getList();

        Label text = new Label("SONGS:");
        text.setId("Headline");

        Button buttons[] = new Button[tracks.size()];
        VBox songs = new VBox();
        songs.setAlignment(Pos.CENTER);

        for(int i = 0; i < tracks.size(); i++){
            Button temp = new Button(tracks.get(i).getName());
            temp.setOnAction(event -> {
                int pos = -1;
                for(int j = 0; j < buttons.length || pos == -1; j++){
                    if(buttons[j] == temp)
                        pos = j;
                }
                player.stop();
                player.play(pos);
            });
            buttons[i] = temp;
            songs.getChildren().add(temp);
        }
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollBarPolicy.NEVER);
        sp.setContent(songs);
        sp.setFitToWidth(true);

        Button back = new Button("BACK");
        back.setOnAction(e -> {
            player.stop();
            soundp.play();
            menuScene.setCenter(menu);
        });

        getChildren().addAll(text, sp);
        //for(Button temp:buttons){
        //    getChildren().add(temp);
        //}
        getChildren().addAll(back);
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
