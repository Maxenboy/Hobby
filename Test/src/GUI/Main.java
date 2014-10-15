package GUI;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new Frame("Filmer ©");
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, 600);
				frame.setLocationRelativeTo(null);
			}

		});

	}
}
