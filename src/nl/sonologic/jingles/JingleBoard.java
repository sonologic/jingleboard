/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * @author gmc
 *
 */
public class JingleBoard extends JFrame {

	private Context context = new Context();

	/**
	 * 
	 */
	private static final long serialVersionUID = -8394157563937317386L;

	
	/**
	 * @throws HeadlessException
	 */
	public JingleBoard() throws HeadlessException {
		System.out.println("Initializing");
		
		Configuration config = context.config;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = getContentPane();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		for(int i=0;i<3;i++) {
			Container panel = new Panel();
			panel.setLayout(new GridLayout(config.getGridWidth(),config.getGridHeight()));
			tabbedPane.addTab("tab"+Integer.toString(i), panel);
			
			for(int j=0;j<config.getGridWidth();j++) {
				for(int k=0;k<config.getGridHeight();k++) {
					int sampleIndex = (j*config.getGridHeight())+k+1;
					JButton button = new JButton(Integer.toString(sampleIndex));
					
					button.setSize(100,100);
					button.setMinimumSize(new Dimension(150,150));
//					button.setAction(new ButtonAction(context));
					button.addMouseListener(new ButtonMouseListener(context,i,sampleIndex));
					panel.add(button);
				}
			}
		}
		
		pane.add(tabbedPane);
				
		pack();
		setVisible(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JingleBoard jingleboard = new JingleBoard();
	}

}
