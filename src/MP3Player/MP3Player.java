package MP3Player;

import Game.GameUtils.GameScene;
import ddf.minim.AudioListener;
import ddf.minim.ugens.TickRate;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Handles all the actions directly related with the song
 */
public class MP3Player {
    private SimpleMinim minim;
    private SimpleAudioPlayer audioPlayer;
    private Playlist actPlaylist;
    private PropertyChangeSupport changes;
    private PropertyChangeSupport playing;
    private GameScene gameScene;
    private TickRate tickRate = new TickRate(1);
    private BooleanProperty isSkipped = new SimpleBooleanProperty(false);
    private boolean isStop = false;


    private boolean hasSong;

    /**
     * Constructor, creates new minim and audioPlayer
     */
    public MP3Player(){
        minim  = new SimpleMinim(true);
        this.playing = new PropertyChangeSupport(false);
    }

    /**
     * Constructor, creates new minim and audioPlayer.
     * Also sets a given MP3Player.Playlist active and loads the first song.
     * Creates propertyChangeSupport to signal song and playing/not playing changes
     * @param actPlaylist the playlist that should be loaded
     */
    public MP3Player(Playlist actPlaylist){
        minim  = new SimpleMinim(true);
        this.actPlaylist = actPlaylist;
        audioPlayer = minim.loadMP3File(actPlaylist.getTrack().getFilename());
        hasSong = true;
        this.changes = new PropertyChangeSupport(actPlaylist.getTrack().getMp3file());
        this.playing = new PropertyChangeSupport(audioPlayer.isPlaying());
    }

    public void setTickrate(float f){
        tickRate.setSampleRate(f);
        tickRate.setInterpolation(true);
    }

    /**
     * Plays a given song
     * @param fileName path of the song to be played
     */
    public void play(String fileName){
       if(hasSong)
           stop();
       this.changes = new PropertyChangeSupport(fileName);
       audioPlayer = minim.loadMP3File(fileName);
       audioPlayer.play();
       playing.firePropertyChange("Song is now playing", !audioPlayer.isPlaying(), audioPlayer.isPlaying());
    }

    /**
     * Plays current song
     */
    public void play(){
        if(hasSong) {
            audioPlayer.play();
            System.out.println(actPlaylist.getName());
            playing.firePropertyChange("Song is now playing", !audioPlayer.isPlaying(), audioPlayer.isPlaying());
            isStop = false;
            startAutomaticSkipper();
        }
    }

    public void startAutomaticSkipper(){
        Timeline automaticSkip = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            if(!isStop) {
                if (!audioPlayer.isPlaying())
                    isSkipped.setValue(true);
                else if (audioPlayer.isPlaying() && isSkipped.getValue())
                    isSkipped.setValue(false);
            }
        }));
        automaticSkip.setCycleCount(Animation.INDEFINITE);
        automaticSkip.play();
        isSkipped.addListener(e -> {
            if(isSkipped.getValue()) {
                skip();
                System.out.println("Skipped\n--------------------------------------------------------------------------");
            }
        });
    }

    /**
     * Plays a song out of a playlist
     * @param index the position of the song to be played
     */
    public void play(int index){
        stop();
        Track oldTrack = actPlaylist.getTrack();
        Track newTrack = actPlaylist.getTrack(index);
        if(newTrack != null) {
            audioPlayer = minim.loadMP3File(newTrack.getFilename());
        }
        play();
        changes.firePropertyChange(oldTrack.getFilename(), oldTrack, newTrack);
    }

    /**
     * Pauses current song
     */
    public void pause(){
        if(audioPlayer == null)
            System.out.println("Fehler");
        else if (audioPlayer.isPlaying()){
            audioPlayer.pause();
            playing.firePropertyChange("Song is now playing", !audioPlayer.isPlaying(), audioPlayer.isPlaying());
        }
    }

    /**
     * Rewinds current song and pauses it
     */
    public void stop() {
        audioPlayer.rewind();
        isStop = true;
        pause();
    }

    /**
     * Plays the next Song of the playlist
     */
    public void skip(){
        stop();
        Track oldTrack = actPlaylist.getTrack();
        play(actPlaylist.getNextTrack().getFilename());
        System.out.println("gefeuert");
        changes.firePropertyChange(oldTrack.getFilename(), oldTrack, actPlaylist.getTrack());
    }

    /**
     * Gets the currently active track from the currently active playlist
     * @return the currently playing track
     */
    public Track getActualTrack(){
        return actPlaylist.getTrack();
    }

    public Playlist getActPlaylist() {
        return actPlaylist;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public BooleanProperty getIsSkipped(){return isSkipped;}
}
