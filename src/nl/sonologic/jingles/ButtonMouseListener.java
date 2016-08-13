/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		
		if(SwingUtilities.isRightMouseButton(e)) {
			context.sampleConfigurationFrame.configureSample(deck, sampleIndex);
		}
		if(SwingUtilities.isLeftMouseButton(e)) {
			
		}
		
		System.out.println(e);
	}
	
	

}
