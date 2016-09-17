package nl.sonologic.jingles;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public class SampleAnalyzer extends SampleFile<Pair<Long, Float>, Void> {

	public SampleAnalyzer(File sampleFile) throws UnsupportedAudioFileException, IOException {
		super(sampleFile);
	}

	@Override
	protected Pair<Long, Float> doInBackground() throws Exception {
		Long numBytes = 0L;
		Float duration = 0F;

		byte[] b = new byte[512];

		int numread = 0;

        while(numread!=-1) {
            if(numread>0) {
            	numBytes += numread;
            }
            numread=sample.read(b,0,512);
        }

        int frameSize = sample.getFormat().getFrameSize();
        float frameRate = sample.getFormat().getFrameRate();

        System.out.println("frameSize = " + Integer.toString(frameSize) + ", frameRate = " + Float.toString(frameRate));

        duration = (numBytes / frameSize) / frameRate;

        System.out.println("duration = " + Float.toString(duration));

		return new Pair<Long, Float>(numBytes, duration);
	}


}
