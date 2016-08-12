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
    private AudioInputStream raw;
    private AudioFormat decodedFormat;
    private boolean loop=false;
    private boolean rewind=false;
    private boolean stop=false;

    public Sample(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this(new File(filename));
    }

    public Sample(File sampleFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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
    }
    
    public synchronized void play() throws InterruptedException {
        if(getState()==State.TERMINATED) join();
        if(this.isAlive()) {
            this.rewind();
        } else {
            this.start();
        }
    }
    
    public void pause() {
        //this.clip.stop();
    }
    
    public void rewind() {
        this.rewind=true;
    }
    
    public void stopPlaying() {
        this.stop=true;
    }
    
    public void loop() {
        loop(true);
    }
    
    public void loop(boolean loop) {
        this.loop=loop;        
    }
    
    public boolean playing() {
        //return this.clip.isRunning();
        return true;
    }
    
}
