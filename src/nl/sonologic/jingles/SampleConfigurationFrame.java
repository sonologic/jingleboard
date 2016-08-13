/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author gmc
 *
 */
public class SampleConfigurationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6964754221771534749L;
	private Context context;

	public SampleConfigurationFrame(Context context) throws HeadlessException {
		super("Sample configuration");
		this.context = context;
	}

	private void setFileChooserFont(Component[] comp, Font font)
	{  
	  for(int x = 0; x < comp.length; x++)  
	  {  
	    if(comp[x] instanceof Container) setFileChooserFont(((Container)comp[x]).getComponents(), font);  
	    try{comp[x].setFont(font);}  
	    catch(Exception e){}//do nothing  
	  }  
	}  
	
	public void configureSample(Deck deck, int i) {
		setTitle("Configure " + deck.getLabel() +", sample "+Integer.toString(i));
		
		SpringLayout layout = new SpringLayout();
		
		setLayout(layout);
		
		JLabel label = new JLabel("File");
		label.setFont(context.configFont);
				
		JTextField textField = new JTextField("", 35);
		textField.setFont(context.configFont);
		
		JButton button = new JButton("browse");
		button.setFont(context.configFont);
		
		class BrowseButtonAction extends AbstractAction {
			private static final long serialVersionUID = 2165346479772650227L;

			private JTextField textField;

			private Context context;
			
			public BrowseButtonAction(Context context, JTextField textField, String label)
			{
				super(label);
				this.context = context;
				this.textField = textField;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				//setFileChooserFont(fc.getComponents(), context.configFont);
				int result = fc.showOpenDialog(textField);
				
				if(result==JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
			}
		}
		
		button.setAction(new BrowseButtonAction(context, textField, "browse"));
		
		layout.putConstraint(SpringLayout.WEST, label,
                5,
                SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label,
                5,
                SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, textField,
                5,
                SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.NORTH, textField,
                5,
                SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, button,
                5,
                SpringLayout.EAST, textField);
		layout.putConstraint(SpringLayout.NORTH, button,
                5,
                SpringLayout.NORTH, this);

		add(label);
		add(textField);
		add(button);
		
		pack();
		
		setVisible(true);
	}
}
