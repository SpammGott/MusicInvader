package MP3Player;

import ddf.minim.AudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;


public class Soundplayer {

    private SimpleMinim minim;
    private AudioPlayer audioPlayer;
    private boolean hasSong;


    public Soundplayer(){
        minim = new SimpleMinim(true);
        hasSong = true;
    }

    public void play(String fileName){
        if(hasSong == true)
            stop();
        audioPlayer = minim.loadMP3File(fileName);
        audioPlayer.play();
    }

    public void stop(){
        audioPlayer.pause();
        audioPlayer.rewind();
    }

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

}
