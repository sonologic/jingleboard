/**
 *
 */
package nl.sonologic.jingles;

import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTabbedPane;

import org.yaml.snakeyaml.Yaml;

/**
 * @author gmc
 *
 */
public class Context {

	static public class ConfigNotFoundException extends Exception {
		private static final long serialVersionUID = 4731134595912630246L;

		public ConfigNotFoundException() { super(); }
	}

	public Configuration config;
	public SampleConfigurationFrame sampleConfigurationFrame;

	//public Vector<Vector<Sample>> samples = new Vector<Vector<Sample>>();
	public JingleBoard board;

	public Vector<Deck> decks = new Vector<Deck>();

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

	public String generateConfig() {
		Yaml yaml = new Yaml();


		LinkedHashMap<String, Object> configDecks = new LinkedHashMap<String, Object>();

		for(Deck deck : decks) {
			configDecks.put(deck.getLabel(), deck.getSamples());
		}

		return yaml.dump(configDecks);
	}

	public void saveConfig() {
		String homePath = System.getProperty("user.home");

		File configDir = new File(homePath, ".jingleboard");

		if(!configDir.isDirectory()) {
			configDir.mkdir();
		}

		File newConfigFile = new File(configDir, "decks.yaml.new");

		try {
			PrintWriter configWriter = new PrintWriter(newConfigFile);
			configWriter.print(generateConfig());
			configWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadConfig() throws IOException {
		String homePath = System.getProperty("user.home");

		File configDir = new File(homePath, ".jingleboard");

		if(!configDir.isDirectory()) {
			throw new FileNotFoundException();
		}

		File configFile = new File(configDir, "decks.yaml");

		//try {

		String s = "";

		byte[] encoded = Files.readAllBytes(Paths.get(configFile.getAbsolutePath()));
		s = new String(encoded, Charset.forName("UTF-8"));
		loadConfig(s);
	}

	public void loadConfig(String s) {
		Container pane = board.getContentPane();

		pane.removeAll();

		//for(int i=0;i<3;i++) {
		//	Deck d = new Deck(context, "Deck "+Integer.toString(i));
		//
		//	d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
		//	tabbedPane.addTab(d.getLabel(), d);
		//
		//	context.decks.add(d);
		//}

		//pane.add(tabbedPane);

		//pack();

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(buttonFont);

		Yaml yaml = new Yaml();

		Object o = yaml.load(s);
		System.out.println("Loaded: ");
		System.out.println(o.getClass());
		System.out.println(o);

		@SuppressWarnings("unchecked")
		Map<String,Object> config = (Map<String, Object>)o;

		for (Map.Entry<String, Object> entry : config.entrySet())
		{
		    System.out.println(entry.getKey() + "/" + entry.getValue());

		    @SuppressWarnings("unchecked")
			ArrayList<Sample> samples = (ArrayList<Sample>)entry.getValue();

		    Deck deck = new Deck(this, entry.getKey(), samples);

		    deck.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
		    tabbedPane.addTab(deck.getLabel(), deck);

		    decks.add(deck);
		}

		pane.add(tabbedPane);

		board.pack();
		board.validate();
		board.repaint();
	}
}
