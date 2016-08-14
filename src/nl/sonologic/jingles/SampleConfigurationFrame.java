/**
 * 
 */
package nl.sonologic.jingles;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	private JTextField labelTextField;
	private Deck deck;

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
	
	class LabelAction extends AbstractAction {
		private static final long serialVersionUID = -6534202435866768164L;
		
		public LabelAction(Sample sample) {
			
		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println(e);
		}
	}
	
	class SampleConfigurationWindowListener extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent event) {
			// TODO Auto-generated method stub
			super.windowClosing(event);
			System.out.println(event);
			SampleConfigurationFrame frame = (SampleConfigurationFrame)event.getSource();
			
			Sample sample = frame.getSample();
			String label = frame.getLabel();
			
			if(sample.getLabel() != label) {
				sample.setLabel(label);
				frame.getDeck().buildUp();
			}
		}

	}
	
	public SampleConfigurationFrame(Context context) throws HeadlessException {
		super("Sample configuration");
		this.context = context;
		
		SpringLayout layout = new SpringLayout();
		
		setLayout(layout);
		
		JLabel fileLabel = new JLabel("File");
		fileLabel.setFont(context.configFont);
				
		fileNameTextField = new JTextField("", 35);
		fileNameTextField.setFont(context.configFont);
		
		JButton button = new JButton("browse");
		button.setFont(context.configFont);
		button.setAction(new BrowseButtonAction(context, this, "browse"));

		JLabel labelLabel = new JLabel("Label");
		labelLabel.setFont(context.configFont);
		
		labelTextField = new JTextField("", 35);
		labelTextField.setFont(context.configFont);
		
		statusLabel = new JLabel();
		statusLabel.setFont(context.configFont);
		
		layout.putConstraint(SpringLayout.WEST, fileLabel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, fileLabel, 5, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, fileNameTextField, 5, SpringLayout.EAST, fileLabel);
		layout.putConstraint(SpringLayout.NORTH, fileNameTextField, 5, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.EAST, fileNameTextField);
		layout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, labelLabel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, labelLabel, 5, SpringLayout.SOUTH, fileLabel);
		
		layout.putConstraint(SpringLayout.WEST, labelTextField, 5, SpringLayout.EAST, labelLabel);
		layout.putConstraint(SpringLayout.NORTH, labelTextField, 5, SpringLayout.SOUTH, fileNameTextField);
		
		layout.putConstraint(SpringLayout.NORTH, statusLabel, 5, SpringLayout.SOUTH, fileLabel);

		add(fileLabel);
		add(fileNameTextField);
		add(button);
		add(labelLabel);
		add(labelTextField);
		add(statusLabel);
		
		//this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new SampleConfigurationWindowListener());
		
		pack();
	}
	
	public void configureSample(Deck deck, int i) {
		setTitle("Configure " + deck.getLabel() + ", sample "+Integer.toString(i));
		statusLabel.setText("");
		
		this.deck = deck;
		sample = deck.getSample(i);
		
		fileNameTextField.setText(sample.getFilename());
		labelTextField.setText(sample.getLabel());
		
		setVisible(true);
	}

	public Sample getSample() {
		return sample;
	}
	
	public String getFilename() {
		return fileNameTextField.getText();
	}
	
	public String getLabel() {
		return labelTextField.getText();
	}
	
	public Deck getDeck() {
		return deck;
	}
}
