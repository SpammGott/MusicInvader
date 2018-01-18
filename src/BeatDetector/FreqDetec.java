package BeatDetector;

import ddf.minim.*;
import ddf.minim.analysis.*;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;

import java.awt.*;

public class FreqDetec extends Frame {
    Minim minim;
    AudioPlayer song;
    BeatDetect beat;
    BeatListener bl;

    float kickSize, snareSize, hatSize;

    class BeatListener implements AudioListener {
        private BeatDetect beat;
        private AudioPlayer source;

        BeatListener(BeatDetect beat, AudioPlayer source) {
            this.source = source;
            this.source.addListener(this);
            this.beat = beat;
        }

        public void samples(float[] samps)
        {
            beat.detect(source.mix);
        }

        public void samples(float[] sampsL, float[] sampsR)
        {
            beat.detect(source.mix);
        }
    }

    void setup(){
        setSize(512, 200);

        minim = new Minim(new MinimHelper());

        song = minim.loadFile("D:\\Benutzer\\Jens\\IdeaProjects\\res\\01-02- Initiation [Explicit].mp3", 1024);
        song.play();
        // a beat detection object that is FREQ_ENERGY mode that
        // expects buffers the length of song's buffer size
        // and samples captured at songs's sample rate
        beat = new BeatDetect(song.bufferSize(), song.sampleRate());
        // set the sensitivity to 300 milliseconds
        // After a beat has been detected, the algorithm will wait for 300 milliseconds
        // before allowing another beat to be reported. You can use this to dampen the
        // algorithm if it is giving too many false-positives. The default value is 10,
        // which is essentially no damping. If you try to set the sensitivity to a negative value,
        // an error will be reported and it will be set to 10 instead.
        // note that what sensitivity you choose will depend a lot on what kind of audio
        // you are analyzing. in this example, we use the same BeatDetector object for
        // detecting kick, snare, and hat, but that this sensitivity is not especially great
        // for detecting snare reliably (though it's also possible that the range of frequencies
        // used by the isSnare method are not appropriate for the song).
        beat.setSensitivity(300);
        kickSize = snareSize = hatSize = 16;
        // make a new beat listener, so that we won't miss any buffers for the analysis
        bl = new BeatListener(beat, song);
        //textFont(createFont("Helvetica", 16));
        //textAlign(CENTER);
    }

    void draw(){
        //background(0);
        setBackground(Color.WHITE);

        // draw a green rectangle for every detect band
        // that had an onset this frame
        int rectW = getWidth() / beat.dectectSize();
        for(int i = 0; i < beat.dectectSize(); ++i){
            // test one frequency band for an onset
            if ( beat.isOnset(i) ){
                //fill(0,200,0);
                new Rectangle( i*rectW, 0, rectW, getHeight());
            }
        }

        // draw an orange rectangle over the bands in
        // the range we are querying
        int lowBand = 5;
        int highBand = 15;
        // at least this many bands must have an onset
        // for isRange to return true
        int numberOfOnsetsThreshold = 4;
        if ( beat.isRange(lowBand, highBand, numberOfOnsetsThreshold)){
            //fill(232,179,2,200);
            new Rectangle(rectW*lowBand, 0, (highBand-lowBand)*rectW, getHeight());

        }

        if (beat.isKick())
            kickSize = 32;
        if (beat.isSnare())
            snareSize = 32;
        if (beat.isHat())
            hatSize = 32;

       //fill(255);

       //textSize(kickSize);
       //text("KICK", getWidth()/4, getHeight()/2);

       //textSize(snareSize);
       //text("SNARE", getWidth()/2, getHeight()/2);

       //textSize(hatSize);
       //text("HAT", 3*getWidth()/4, getHeight()/2);



       kickSize = constrain((float)(kickSize * 0.95), 16, 32);
       snareSize = constrain((float)(snareSize * 0.95), 16, 32);
       hatSize = constrain((float)(hatSize * 0.95), 16, 32);
    }

    private float constrain(float value, float min, float max){
        return Math.min(Math.max(value, min), max);
    }

    public static void main(String args[]){
        FreqDetec f = new FreqDetec();
        f.setup();
        f.draw();
    }
}
