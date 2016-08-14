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
public class Sample extends Thread {
    private SourceDataLine sourceLine;
    private AudioInputStream sample;
    File file;
    private String filename="";
    private AudioInputStream raw;
    private AudioFormat decodedFormat;
    private boolean loaded=false;
    private boolean playing=false;
    private boolean loop=false;
    private boolean rewind=false;
    private boolean stop=false;

    public Sample() {
    	super();
    }
    
    public Sample(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	super();
    	this.filename = filename;
    	loadFile();
    }
    
    public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		stopPlaying();
		this.filename = filename;
		loadFile();
	}

	private synchronized void loadFile() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File sampleFile = new File(filename);
        raw=AudioSystem.getAudioInputStream(sampleFile);
        file=sampleFile;

        AudioFormat baseFormat = raw.getFormat();
        decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                                                  baseFormat.getSampleRate(),
                                                                                  16,
                                                                                  baseFormat.getChannels(),
                                                                                  baseFormat.getChannels() * 2,
                                                                                  baseFormat.getSampleRate(),
                                                                                  false);
        sample=AudioSystem.getAudioInputStream(decodedFormat,raw);
        
        sourceLine = AudioSystem.getSourceDataLine(decodedFormat);        
        sourceLine.open(decodedFormat);
        
        System.out.println(sourceLine.getFormat().toString());
        
        System.out.println(raw.getFormat());
        
        System.out.println(sample.getFormat().toString());
        
        loaded = true;
    }
    
    public void run() {
        stop=false;
        this.sourceLine.start();
        try {
            playloop();
        } catch (IOException ex) {
            Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.sourceLine.stop();
    }
    
    private void playloop() throws IOException, UnsupportedAudioFileException {
        byte[] b = new byte[512];
        int numread;
        boolean first=true;
        
        playing = true;
        
        while(first || loop) {
            first=false;
            numread=0;
            while(numread!=-1) {
                if(rewind) {
                    sourceLine.flush();
                    sample.close();
                    raw=AudioSystem.getAudioInputStream(file);
                    sample=AudioSystem.getAudioInputStream(decodedFormat,raw);
                    this.rewind=false;
                } else if(stop) {
                    sourceLine.flush();
                    numread=-1; loop=false;
                } else {
                    if(numread>0) this.sourceLine.write(b,0,numread);
                }
                numread=sample.read(b,0,512);
            }
            if(!stop) sourceLine.drain();
            if(loop) rewind();
        }
        
        sourceLine.drain();
        
        playing = false;
    }
    
    public synchronized void play() throws InterruptedException {
        if(getState()==State.TERMINATED) join();
        if(this.isAlive()) {
            this.rewind();
        } else {
            this.start();
        }
    }
    
    public synchronized void pause() {
        //this.clip.stop();
    }
    
    public synchronized void rewind() {
        this.rewind=true;
    }
    
    public synchronized void stopPlaying() throws InterruptedException {
    	this.loop=false;
        this.stop=true;
        if(this.isAlive()) join();
    }
    
    public void loop() {
        loop(true);
    }
    
    public synchronized void loop(boolean loop) {
        this.loop=loop;
    }
    
    public synchronized boolean playing() {
        return playing;
    }
    
}
