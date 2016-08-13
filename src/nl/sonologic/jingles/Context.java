/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.Font;
import java.util.Vector;

/**
 * @author gmc
 *
 */
public class Context {

	public Configuration config;
	public SampleConfigurationFrame sampleConfigurationFrame;

	public Vector<Vector<Sample>> samples = new Vector<Vector<Sample>>();
	private JingleBoard board;
	
	public Font buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
	public Font configFont = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
	/**
	 * 
	 */
	public Context(JingleBoard board) {
		this.config = new Configuration();
		this.board = board;
		this.sampleConfigurationFrame = new SampleConfigurationFrame(this);
	}
	
	

}
