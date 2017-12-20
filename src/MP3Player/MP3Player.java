package MP3Player;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
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

    private boolean hasSong;

    /**
     * Constructor, creates new minim and audioPlayer
     */
    public MP3Player(){
        minim  = new SimpleMinim(true);
        audioPlayer = minim.loadMP3File("res/test.mp3");
        hasSong = true;

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

    /**
     * Plays a given song
     * @param fileName path of the song to be played
     */
    private void play(String fileName){
        stop();
        audioPlayer = minim.loadMP3File(fileName);
        hasSong = true;
        play();
        playing.firePropertyChange("Song is now playing", !audioPlayer.isPlaying(), audioPlayer.isPlaying());
    }

    /**
     * Plays current song
     */
    public void play(){
        if(hasSong && !audioPlayer.isPlaying()) {
            audioPlayer.play();
            playing.firePropertyChange("Song is now playing", !audioPlayer.isPlaying(), audioPlayer.isPlaying());
        }
    }

    /**
     * Plays a song out of a playlist
     * @param index the position of the song to be played
     */
    public void play(int index){
        stop();
        Track oldTrack = actPlaylist.getTrack();
        Track newTrack = actPlaylist.getTrack(index);
        if(newTrack != null)
            audioPlayer = minim.loadMP3File(newTrack.getFilename());
        play();
        changes.firePropertyChange(oldTrack.getFilename(), oldTrack, newTrack);
    }

    /**
     * Sets the song to a certain position, doesn't affect playstate
     * @param time in miliseconds
     */
    public void cue(int time){
        audioPlayer.cue(time);
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
        pause();
    }

    /**
     * Plays the next Song of the playlist
     */
    public void skip(){
        stop();
        Track oldTrack = actPlaylist.getTrack();
        play(actPlaylist.getNextTrack().getFilename());
        changes.firePropertyChange(oldTrack.getFilename(), oldTrack, actPlaylist.getTrack());
    }

    /**
     * Plays the previous song of the playlist
     */
    public void previous(){
        stop();
        Track oldTrack = actPlaylist.getTrack();
        play(actPlaylist.getPreviousTrack().getFilename());
        changes.firePropertyChange(oldTrack.getFilename(), oldTrack, actPlaylist.getTrack());
    }

    /**
     * Sets the volume (actually just sets gain, may lower sound quality)
     * @param newVolume value between 0 and 1
     */
    public void volume(float newVolume){
        float gain;
        if(newVolume < 0f || newVolume > 1f){
            return;
        }

        if(newVolume == 0)
            gain = -100f;
        else
            gain = -30 * ((newVolume-1)*(newVolume-1)) + 6;
        audioPlayer.setGain(gain);
    }

    /**
     * Activates or deactivates the shuffel functionality
     */
    public void shuffel(){
        if(actPlaylist.isShuffle())
            actPlaylist.setShuffle(false);
        else
            actPlaylist.setShuffle(true);
    }

    /**
     * Gets the position of the current song in seconds
     * @return position of the current song in seconds
     */
    public int getPosition(){
        return audioPlayer.position() / 1000;
    }

    /**
     * Changes the active playlist
     * @param newPlaylist playlist that will be set to active
     */
    public void changePlaylist(Playlist newPlaylist){
        if(newPlaylist != null)
            actPlaylist = newPlaylist;
    }

    /**
     * Return if the player shuffles or not
     * @return if shuffle is active or not
     */
    public boolean isShuffle(){return actPlaylist.isShuffle();}

    /**
     * Change listener, needed to update current song infos
     * @param propChngListn a property Change Listener
     */
    public void addPropertyChangeListenerSongInfos(PropertyChangeListener propChngListn){
        changes.addPropertyChangeListener(propChngListn);
    }
    /**
     * Change listener, needed to update play button according to the play status
     * @param propChngListn a property Change Listener
     */
    public void addPropertyChangeListenerPlayInfo(PropertyChangeListener propChngListn){
        playing.addPropertyChangeListener(propChngListn);
    }

    /**
     * Gets the currently active track from the currently active playlist
     * @return the currently playing track
     */
    public Track getActualTrack(){
        return actPlaylist.getTrack();
    }

    /**
     * To check if song is playing
     * @return playing status
     */
    public boolean isPlaying(){return audioPlayer.isPlaying();}
}
