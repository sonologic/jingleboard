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
	private int tabIndex;
	private int sampleIndex;

	/**
	 * 
	 */
	public ButtonMouseListener(Context context, int tabIndex, int sampleIndex) {
		super();
		
		this.context = context;
		this.tabIndex = tabIndex;
		this.sampleIndex = sampleIndex;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		if(SwingUtilities.isRightMouseButton(e)) {
			context.sampleConfigurationFrame.configureSample(tabIndex, sampleIndex);
		}
		if(SwingUtilities.isLeftMouseButton(e)) {
			
		}
		
		System.out.println(e);
	}
	
	

}
