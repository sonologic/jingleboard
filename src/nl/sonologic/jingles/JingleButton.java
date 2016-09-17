/**
 *
 */
package nl.sonologic.jingles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

/**
 * @author gmc
 *
 */
public class JingleButton extends JButton {

	private static final long serialVersionUID = -3643892818460454950L;
	private double fraction = 0;

	/**
	 * @param text
	 */
	public JingleButton(String text) {
		super(text);
		setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();
		int leftWidth = (int)(getWidth() * fraction);
		int rightWidth = (int)(getWidth() * (1-fraction));
		g2.setColor(Color.RED);
		g2.fillRect(0, 0, leftWidth, getHeight());
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(leftWidth, 0, rightWidth, getHeight());
		g2.dispose();
		super.paintComponent(g);
	}


}
