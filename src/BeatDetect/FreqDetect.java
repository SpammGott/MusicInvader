import java.io.IOException;
import java.io.InputStream;

import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.AudioBuffer;

public class FreqDetect {

	AudioPlayer song;
	
	public static void main(String[]args){

		
	}
	
	class MinimInput {
        String sketchPath(String fileName ) {
            return "";
        }
        InputStream createInput(String fileName) {
            return new InputStream() {
                @Override
                public int read() throws IOException {
                    return 0;
                }
            };
        }
		public void run() {
	        Minim minim = new Minim(new MinimInput());
	
	        song = minim.loadFile("marcus_kellis_theme.mp3", 2048);
			song.play();
	        AudioInput input = minim.getLineIn(Minim.STEREO, 1024); //Tried differen values here
	        BeatDetect beatDetect = new BeatDetect(1024, 44100.0f);
	        beatDetect.setSensitivity(1000); //Tried different values here
	
	        int i=0;
	
	        while (true) {
	            beatDetect.detect(input.mix);
	
	            if(beatDetect.isHat()) {
	                System.out.print("HAT");
	            }
	
	            if(beatDetect.isSnare()) {
	                System.out.print("SNARE");
	            }
	
	            if (beatDetect.isKick()) {
	                System.out.print("KICK");
	            }
	        }
	    }
	}
}
