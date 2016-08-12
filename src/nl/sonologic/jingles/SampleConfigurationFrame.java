/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * @author gmc
 *
 */
public class SampleConfigurationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6964754221771534749L;

	public SampleConfigurationFrame() throws HeadlessException {
		super("Sample configuration");
	}

	public void configureSample(int tab, int i) {
		setTitle("Configure sample " + Integer.toString(tab)+"/"+Integer.toString(i));
		setVisible(true);
	}
}
