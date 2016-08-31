/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sonologic.jingles;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 *
 * @author koenmartens
 */
public class Sample {
    File file;
    private String filename = null;
    private String label = "Untitled";
    private SamplePlayer samplePlayer = null;

    public Sample() {
    }
    
    public Sample(String filename) {
    	this.filename = filename;
    }
    
    public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void play(boolean loop) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(filename!=null) {
            if(samplePlayer==null || !samplePlayer.isAlive()) {
                if(samplePlayer!=null) samplePlayer.join();
                samplePlayer=new SamplePlayer(filename);
            }
            
            samplePlayer.loop(loop);
            samplePlayer.play();
        }
	}
	
	public void stop() {
		if(samplePlayer!=null) {
			samplePlayer.stopPlaying();
		}
	}
}
