package MP3Player;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;


public class SoundPlayer {

    private SimpleMinim minim;
    private SimpleAudioPlayer audioPlayer;
    private boolean hasSong;


    public SoundPlayer(String fileName){
        minim = new SimpleMinim(true);
        audioPlayer = minim.loadMP3File(fileName);
        hasSong = true;
    }

    public void play(){
        if(hasSong)
            stop();
        audioPlayer.play(0);
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

    public void loop(){
        audioPlayer.loop();

    }

}
