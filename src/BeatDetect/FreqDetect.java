package BeatDetect;

import java.io.IOException;
import java.io.InputStream;

import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.AudioBuffer;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;

public class FreqDetect {

	AudioPlayer song;
	
	public static void main(String[]args){
		FreqDetect f = new FreqDetect();
		//f.start();
		f.run();
		
	}

	public void start(){
		MinimInput mi = new MinimInput();
		mi.sketchPath("D:\\Benutzer\\Jens\\IdeaProjects\\res\\01-02- Initiation [Explicit].mp3");
		mi.createInput("D:\\Benutzer\\Jens\\IdeaProjects\\res\\01-02- Initiation [Explicit].mp3");
		run();
	}
	
	class MinimInput {
		String sketchPath(String fileName) {
			return fileName;
		}

		InputStream createInput(String fileName) {
			return new InputStream() {
				@Override
				public int read() throws IOException {
					return 0;
				}
			};
		}
	}
		public void run() {
	        Minim minim = new Minim(new MinimHelper());

	        song = minim.loadFile("D:\\Benutzer\\Jens\\IdeaProjects\\res\\01-02- Initiation [Explicit].mp3", 1024);
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
