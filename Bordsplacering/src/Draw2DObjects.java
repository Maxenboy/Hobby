import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PrinterException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ActionMapUIResource;

public class Draw2DObjects extends JFrame {
	private static final long serialVersionUID = 1L;
	Shape shape = new Ellipse2D.Double();
	private ArrayList<String> guests;
	private MyCanvas canvas;

	public Draw2DObjects(String shapetext, ArrayList<String> guests) {
		this.guests = guests;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		canvas = new MyCanvas(shapetext, screenSize);
		add("Center", canvas);
		setSize(screenSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	class MyCanvas extends Canvas {
		private static final long serialVersionUID = 1L;
		private String tableType;
		private Dimension screenSize;
		private JFrame frame;
		private JTextArea text;
		private JScrollPane scroll;
		private KeyStroke ctrlp = KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask());
		public MyCanvas(String shapetext, Dimension screenSize) {
			tableType = shapetext;
			this.screenSize = screenSize;
			frame = new JFrame();
			text=new JTextArea();
			text.setEditable(false);
			scroll=new JScrollPane(text);
			
			frame.add(scroll);
			frame.pack();
			frame.setSize(500, 500);
			frame.setLocationRelativeTo(null);
			

		}

		public void paint(Graphics graphics) {
			Graphics2D g = (Graphics2D) graphics;

			switch (tableType) {
			case "Rectangular":
				shape = new Rectangle2D.Double(screenSize.getWidth() / 2 - 100,
						screenSize.getHeight() / 2 - 300, 200, 600);
				break;
			case "Square":
				shape = new Rectangle2D.Double(screenSize.getWidth() / 2 - 300,
						screenSize.getHeight() / 2 - 300, 600, 600);
				System.out.println();
				break;
			case "Circular":
				shape = new Ellipse2D.Double(screenSize.getWidth() / 2 - 250,
						screenSize.getHeight() / 2 - 250, 500, 500);
				break;
			case "Oval":
				shape = new Ellipse2D.Double(screenSize.getWidth() / 2 - 200,
						screenSize.getHeight() / 2 - 300, 400.0, 600.0);
				break;
			}
			g.setStroke(new BasicStroke(5));
			g.draw(shape);
			int h = 0;
			g.setFont(this.getFont().deriveFont(10f));
			for (int i = 0; i < guests.size(); i++) {
				text.append(i + 1 + ". " + guests.get(i)+"\n");
				
			}
			text.setCaretPosition(0);
			int index = 1;
			g.drawString(Integer.toString(index) + ".", shape.getBounds().x
					+ shape.getBounds().width / 2
					- Integer.toString(index).length() / 2,
					shape.getBounds().y - 10);
			int y = shape.getBounds().y + 30;
			for (int i = 1; i < (guests.size() / 2) + guests.size() % 2; i++) {
				g.drawString(
						Integer.toString(i + 1) + ".",
						shape.getBounds().x
								- g.getFontMetrics().stringWidth(
										Integer.toString(i + 1) + ".") - 10, y);
				y += shape.getBounds().height
						/ ((guests.size() / 2) + guests.size() % 2 - 1);
				index++;
			}
			index++;
			g.drawString(Integer.toString(index) + ".", shape.getBounds().x
					+ shape.getBounds().width / 2
					- Integer.toString(index).length() / 2, shape.getBounds().y
					+ shape.getBounds().height + 30);
			y=shape.getBounds().y+shape.getBounds().height-30;
			for (int i = index; i < (guests.size() / 2) + index - 1; i++) {
				g.drawString(Integer.toString(i + 1) + ".",
						shape.getBounds().x +shape.getBounds().width+10, y);
				y -= shape.getBounds().height
						/ ((guests.size() / 2) - 1);
			}
			
			frame.setVisible(true);
			// AffineTransform affineTransform = new AffineTransform();
			// affineTransform.rotate(Math.toRadians(-90), 0, 0);
			// Font rotatedFont = this.getFont().deriveFont(affineTransform);
			// g.setFont(rotatedFont);
			ActionMap actionMap = new ActionMapUIResource();
			actionMap.put("action_print", new AbstractAction() {

				private static final long serialVersionUID = 5217184895784554242L;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						text.print();
					} catch (PrinterException e1) {
						JOptionPane.showMessageDialog(text, e1);
					}
				}
			});

			InputMap keyMap = new ComponentInputMap(text);
			keyMap.put(ctrlp, "action_print");

			SwingUtilities.replaceUIActionMap(text, actionMap);
			SwingUtilities.replaceUIInputMap(text,
					JComponent.WHEN_IN_FOCUSED_WINDOW, keyMap);

		}

	}
}
