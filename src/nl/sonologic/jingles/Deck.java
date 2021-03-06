/**
 *
 */
package nl.sonologic.jingles;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;

/**
 * @author gmc
 *
 */
public class Deck extends Panel {

	private String label="Untitled";
	private int width;
	private int height;
	/**
	 *
	 */
	private static final long serialVersionUID = -3715191501005877651L;
	private Context context;

	private Vector<Sample> samples = new Vector<Sample>();
	private Vector<JingleButton> buttons = new Vector<JingleButton>();

	/**
	 *
	 */
	public Deck(Context context, String label) {
		this.context = context;
		this.label = label;
		width = context.config.getGridWidth();
		height = context.config.getGridHeight();

		for(int i=0;i<(width*height);i++) {
			Sample sample = new Sample();
			sample.setLabel(Integer.toString(i+1));
			samples.add(sample);
		}
		buildUp();
	}

	public Deck(Context context, String label, ArrayList<Sample> samples) {
		this.context = context;
		this.label = label;
		width = context.config.getGridWidth();
		height = context.config.getGridHeight();

		for(Sample sample : samples) {
			this.samples.add(sample);
		}

		buildUp();
	}

	public void buildUp() {
		removeAll();

		setLayout(new GridLayout(width,height));

		buttons.clear();

		for(int j=0;j<width;j++) {
			for(int k=0;k<height;k++) {
				int sampleIndex = (j*height)+k;

				String label = getSample(sampleIndex).getLabel();
				JingleButton button = new JingleButton(label);
				buttons.add(button);

				button.setSize(100,100);
				button.setMinimumSize(new Dimension(150,150));
				button.addMouseListener(new ButtonMouseListener(context,this,sampleIndex));
				button.setFont(context.buttonFont);
				add(button);
			}
		}

		validate();
		repaint();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		buildUp();
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		buildUp();
	}

	public Sample getSample(int i) {
		return samples.get(i);
	}

	public JButton getButton(int i) {
		return buttons.get(i);
	}

	public Vector<Sample> getSamples() {
		return samples;
	}

	public void setSampleFilename(int i, String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		Sample sample = samples.get(i);
		sample.setFilename(filename);
	}

}
