package BeatDetector;

import Game.GameUtils.GameScene;
import ddf.minim.*;
import ddf.minim.analysis.*;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;

public class FreqDetect {

	private String path;
	private GameScene gameScene;

	public FreqDetect(String path, GameScene scene){
		this.path = path;
		this.gameScene = scene;
		run();
	}

	public void run() {
		Minim minim = new Minim(new MinimHelper());
		minim.loadFile(path, 1024);
		AudioInput input = minim.getLineIn(Minim.STEREO, 1024); //Tried differen values here
		BeatDetect beatDetect = new BeatDetect(1024, 44100.0f);
		beatDetect.setSensitivity(300); //10 = defaultValue
		while (true) {
			beatDetect.detect(input.mix);

			if(beatDetect.isHat()) {
				//System.out.println("HAT");
			}
			if(beatDetect.isSnare()) {
				//System.out.println("SNARE");
			}
			if (beatDetect.isKick()) {
				gameScene.spawnEnemy();
				System.out.println("Spawned");
			}

		}
	}
}
