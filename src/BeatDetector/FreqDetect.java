package BeatDetector;

import Game.GameUtils.GameScene;
import ddf.minim.*;
import ddf.minim.analysis.*;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;
import javafx.application.Platform;

/**
 * Handles Frequence Detection of the Song
 */
public class FreqDetect {

	private String path;
	private GameScene gameScene;

	public FreqDetect(String path, GameScene scene){
		this.path = path;
		this.gameScene = scene;
	}

	public void run() {
		Minim minim = new Minim(new MinimHelper());
		minim.loadFile(path, 1024);
		AudioInput input = minim.getLineIn(Minim.STEREO, 1024); //Tried different values here
		BeatDetect beatDetect = new BeatDetect(1024, 44100.0f);
		beatDetect.setSensitivity(600); //10 = defaultValue
		while (true) {
			beatDetect.detect(input.mix);

			/*
			if(beatDetect.isHat()) {
				System.out.println("HAT");
			}
			if(beatDetect.isSnare()) {
				System.out.println("SNARE");
			}
			*/
			if (beatDetect.isKick()) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						gameScene.spawnEnemy();
					}
				});

			}

		}
	}
}
