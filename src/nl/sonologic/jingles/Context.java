/**
 * 
 */
package nl.sonologic.jingles;

/**
 * @author gmc
 *
 */
public class Context {

	public Configuration config;
	public SampleConfigurationFrame sampleConfigurationFrame;

	/**
	 * 
	 */
	public Context() {
		this.config = new Configuration();
		this.sampleConfigurationFrame = new SampleConfigurationFrame();
	}

}
