/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author gmc
 *
 */
public class JingleBoard extends JFrame {

	private Context context = new Context(this);

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
		tabbedPane.setFont(context.buttonFont);
		
		for(int i=0;i<3;i++) {
			Deck d = new Deck(context, "Deck "+Integer.toString(i));
		
			d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
			tabbedPane.addTab(d.getLabel(), d);
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
