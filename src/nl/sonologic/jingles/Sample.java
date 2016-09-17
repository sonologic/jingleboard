/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sonologic.jingles;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingWorker;

/**
 *
 * @author koenmartens
 */
public class Sample {
    File file;
    private String filename = null;
    private String label = "Untitled";
    private SamplePlayer samplePlayer = null;
    protected Long numBytes = 0L;
    protected Float duration = 0F;

    public Sample() {
    }

    public Sample(String filename) {
    	this.filename = filename;

		this.analyze();
    }

    public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
		this.analyze();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void analyze() {
		class SampleAnalyzerListener implements PropertyChangeListener {
			private Sample sample;

			public SampleAnalyzerListener(Sample sample) {
				this.sample = sample;
			}

			@Override
			public void propertyChange(PropertyChangeEvent changeEvent) {
				System.out.println("Event " + changeEvent.getPropertyName() + " = " + changeEvent.getNewValue());
				if(changeEvent.getPropertyName()=="state") {
					if(changeEvent.getNewValue()==SwingWorker.StateValue.DONE) {
						SampleAnalyzer analyzer = (SampleAnalyzer)changeEvent.getSource();
						Pair<Long, Float> result;
						try {
							result = analyzer.get();
							sample.numBytes = result.v1;
							sample.duration = result.v2;
							System.out.println("Set numBytes to "+Long.toString(sample.numBytes)+", duration to "+Float.toString(sample.duration));
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}


		}

		if(this.filename!=null) {
			SampleAnalyzer analyzer;

			try {
				analyzer = new SampleAnalyzer(new File(this.filename));
				analyzer.addPropertyChangeListener(new SampleAnalyzerListener(this));
				analyzer.execute();
			} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void play(boolean loop) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(filename!=null) {
            if(samplePlayer==null || samplePlayer.isDone()) {
            	samplePlayer=new SamplePlayer(new File(filename));
            }

            samplePlayer.execute();
            //samplePlayer.loop(loop);
            //samplePlayer.play();
        }
	}

	public void stop() {
		if(samplePlayer!=null) {
			samplePlayer.stopPlaying();
		}
	}
}
