/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
	private Sample sample = null;
	private JTextField fileNameTextField;
	private JLabel statusLabel;

	public SampleConfigurationFrame(Context context) throws HeadlessException {
		super("Sample configuration");
		this.context = context;
		
		SpringLayout layout = new SpringLayout();
		
		setLayout(layout);
		
		JLabel label = new JLabel("File");
		label.setFont(context.configFont);
				
		fileNameTextField = new JTextField("", 35);
		fileNameTextField.setFont(context.configFont);
		
		JButton button = new JButton("browse");
		button.setFont(context.configFont);
		
		class BrowseButtonAction extends AbstractAction {
			private static final long serialVersionUID = 2165346479772650227L;
			private Context context;
			private SampleConfigurationFrame frame;
			
			public BrowseButtonAction(Context context, SampleConfigurationFrame frame, String label)
			{
				super(label);
				this.context = context;
				this.frame = frame;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				//setFileChooserFont(fc.getComponents(), context.configFont);
				int result = fc.showOpenDialog(frame.fileNameTextField);
				
				if(result==JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					frame.fileNameTextField.setText(file.getAbsolutePath());
					statusLabel.setText("Loading sample...");
					
					boolean failed = false;
					try {
						frame.getSample().setFilename(frame.getFilename());
					} catch (UnsupportedAudioFileException e1) {
						statusLabel.setText("Error: Unsupported audio format");
						failed = true;
					} catch (IOException e1) {
						statusLabel.setText("Error: IO error (unable to read file)");
						failed = true;
					} catch (LineUnavailableException e1) {
						statusLabel.setText("Error: line unavailable");
						failed = true;
					} catch (InterruptedException e1) {
						statusLabel.setText("Error: interrupted, try again");
						failed = true;
					}
					if(!failed)
						statusLabel.setText("Sample loaded");
				}
			}
		}
		
		button.setAction(new BrowseButtonAction(context, this, "browse"));

		statusLabel = new JLabel();
		statusLabel.setFont(context.configFont);
		
		layout.putConstraint(SpringLayout.WEST, label,
                5,
                SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label,
                5,
                SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, fileNameTextField,
                5,
                SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.NORTH, fileNameTextField,
                5,
                SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, button,
                5,
                SpringLayout.EAST, fileNameTextField);
		layout.putConstraint(SpringLayout.NORTH, button,
                5,
                SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.NORTH, statusLabel,
				5,
				SpringLayout.SOUTH, label);
		
		add(label);
		add(fileNameTextField);
		add(button);
		add(statusLabel);
		
		pack();
	}
	
	public void configureSample(Deck deck, int i) {
		setTitle("Configure " + deck.getLabel() + ", sample "+Integer.toString(i));
		statusLabel.setText("");
		
		sample = deck.getSample(i);
		
		this.fileNameTextField.setText(sample.getFilename());
		
		setVisible(true);
	}

	public Sample getSample() {
		return sample;
	}
	
	public String getFilename() {
		return fileNameTextField.getText();
	}
}
