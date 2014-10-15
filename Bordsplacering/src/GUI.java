import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import se.datadosen.component.RiverLayout;

public class GUI {

	private JFrame frame;
	private JComboBox<String> tables;
	private JTextArea men, women;
	private JScrollPane menPane, womenPane;
	private JButton generateButton;
	private Container c;
	private ArrayList<String> guests, menList, womenList,
			result = new ArrayList<String>();
	private JRadioButton random, gender;
	private ButtonGroup bg;
	private JSplitPane split;

	public GUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame = new JFrame("Table seating");
		c = frame.getContentPane();
		c.setLayout(new RiverLayout());
		tables = new JComboBox<String>();
		tables.setPrototypeDisplayValue("Rectangular");
		String[] types = { "Rectangular", "Square", "Circular", "Oval" };
		fillList(types);
		men = new JTextArea();
		men.setFont(men.getFont().deriveFont(20f));
		women = new JTextArea();
		women.setFont(men.getFont().deriveFont(20f));
		menPane = new JScrollPane(men);
		womenPane = new JScrollPane(women);
		split = new JSplitPane();
		split.setLeftComponent(menPane);
		split.setRightComponent(womenPane);
		split.setDividerLocation(190);
		random = new JRadioButton("Random seating");
		gender = new JRadioButton("Gender seating");
		bg = new ButtonGroup();
		bg.add(random);
		bg.add(gender);
		generateButton = new JButton("Generate");
		generateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!men.getText().equals("") && !men.getText().equals(" ")
						&& !women.getText().equals("")
						&& !women.getText().equals(" ")) {
					if (!men.getText().startsWith(" ")
							&& !women.getText().startsWith(" ")) {
						String[] m = men.getText().split(
								"[[ +&&\n]||[ ]||[\n]||[\t]]");
						String[] w = women.getText().split(
								"[[ +&&\n]||[ ]||[\n]||[\t]]");
						menList = new ArrayList<String>(Arrays.asList(m));
						womenList = new ArrayList<String>(Arrays.asList(w));
						result = new ArrayList<String>();
					}

				} else {
					JOptionPane
							.showMessageDialog(men,
									"Sorry cant' be a party without guests from both sex");

				}
				if (random.isSelected()) {
					guests = new ArrayList<String>(menList);
					guests.addAll(womenList);
					for (int i = 0; i < guests.size(); i++) {
						Random rand = new Random();
						int index = rand.nextInt(guests.size());
						result.add(guests.get(index));
						guests.remove(index);
						i--;
					}
					new Draw2DObjects(tables.getSelectedItem().toString(),
							result);
				} else {
					Random mrand = new Random();
					Random wrand = new Random();
					guests = new ArrayList<String>();
					while (menList.size() != 0 && womenList.size() != 0) {
						int indexMan = mrand.nextInt(menList.size());
						guests.add(menList.get(indexMan));
						menList.remove(indexMan);
						int indexWoman = wrand.nextInt(womenList.size());
						guests.add(womenList.get(indexWoman));
						womenList.remove(indexWoman);
					}
					Random left = new Random();
					Random place = new Random();
					if (menList.size() != 0) {
						while (menList.size() != 0) {
							int index = left.nextInt(menList.size());
							int tableplace = place.nextInt(guests.size());
							guests.add(tableplace, menList.get(index));
							menList.remove(index);
						}
					} else {
						while (womenList.size() != 0) {
							int index = left.nextInt(womenList.size());
							int tableplace = place.nextInt(guests.size());
							guests.add(tableplace, womenList.get(index));
							womenList.remove(index);
						}
					}
					new Draw2DObjects(tables.getSelectedItem().toString(),
							guests);
				}
			}
		});

		c.add("p left", new JLabel("Type of table:"));
		c.add("tab", tables);
		c.add("br tab", random);
		c.add("tab", gender);
		c.add("br tab", new JLabel("Men"));
		c.add("tab", new JLabel("                         "));
		c.add("tab", new JLabel("Women"));
		c.add("br", new JLabel("Names:"));
		c.add("tab vfill hfill", split);
		c.add("p center", generateButton);
		random.setSelected(true);
		frame.pack();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void fillList(String[] list) {
		ArrayList<String> tabletypes = new ArrayList<String>(
				Arrays.asList(list));
		for (String s : tabletypes) {
			tables.addItem(s);

		}
	}

}
