/**
 *
 */
package nl.sonologic.jingles;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 * @author gmc
 *
 */
public class ButtonMouseListener extends MouseAdapter {

	private Context context;
	private int sampleIndex;
	private Deck deck;

	/**
	 *
	 */
	public ButtonMouseListener(Context context, Deck deck, int sampleIndex) {
		super();

		this.context = context;
		this.deck = deck;
		this.sampleIndex = sampleIndex;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);

		if (SwingUtilities.isRightMouseButton(e)) {
			context.sampleConfigurationFrame.configureSample(deck, sampleIndex);
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			Sample sample = deck.getSample(sampleIndex);

			if ((e.getModifiers() & ActionEvent.SHIFT_MASK) != 0) {
				sample.stop();
				JButton b = deck.getButton(sampleIndex);
				b.setBackground(Color.LIGHT_GRAY);
				b.paint(b.getGraphics());
			} else {
				try {
					sample.play((e.getModifiers() & ActionEvent.CTRL_MASK) != 0);
					JButton b = deck.getButton(sampleIndex);
					b.setBackground(Color.RED);
					b.paint(b.getGraphics());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		System.out.println(e);
	}

}
