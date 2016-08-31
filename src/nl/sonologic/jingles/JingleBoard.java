/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		Container pane = getContentPane();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(context.buttonFont);
		
		for(int i=0;i<3;i++) {
			Deck d = new Deck(context, "Deck "+Integer.toString(i));
		
			d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
			tabbedPane.addTab(d.getLabel(), d);
			
			context.decks.add(d);
		}
		
		pane.add(tabbedPane);
				
		pack();
		*/
		
		try {
			context.loadConfig();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		setVisible(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JingleBoard();
	}

}
