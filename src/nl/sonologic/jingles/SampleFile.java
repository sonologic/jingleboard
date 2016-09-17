/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sonologic.jingles;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingWorker;

/**
 *
 * @author koenmartens
 */
public abstract class SampleFile<T, V> extends SwingWorker<T, V> {
    protected AudioInputStream sample;
    protected File file;
    protected AudioInputStream raw;
    protected AudioFormat decodedFormat;

    public SampleFile(File sampleFile) throws UnsupportedAudioFileException, IOException {
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

        System.out.println(raw.getFormat());

        System.out.println(sample.getFormat().toString());

    }
}