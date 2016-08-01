package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.IIOImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Mohammed Omar
 * 
 *         Class for the help menu GUI
 *
 */
public class Help implements ActionListener {

	JFrame frame;
	JButton close;

	private int y = 510;
	private int x = 720;
	private JPanel contentPanel;
	int count;

	/**
	 * Constructor; creates the frame and places the correct image for the
	 * particular menu onto it
	 * 
	 * @param image
	 *            The particular image for the menu its helping
	 * @param count
	 *            The ID for the menu the user was on before clicking help
	 */
	public Help(String image, int count) {
		this.count = count;
		frame = new JFrame("RHC&MC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel = new JPanel();
		contentPanel.setLayout(null);

		ImageIcon img = new ImageIcon("Images/" + image + ".jpg");
		JLabel back = new JLabel(img);
		back.setSize(x + 50, y + 50);
		back.setLocation(-21, -23);
		frame.add(back);

		close = new JButton("CLOSE");
		close.setSize(100, 30);
		close.setLocation(550, 50);
		close.addActionListener(this);

	
		contentPanel.add(close);
		frame.add(contentPanel);

		frame.setSize(x, y);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	    frame.setUndecorated(true);
		frame.setVisible(true);

	}

	public static void main(String args[]) {
	}

	/**
	 * Takes the user back to the menu they were on upon clicking the close
	 * button
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close) {
			if (count == 0) {
				nameStat.frame.setVisible(true);
				frame.dispose();

			}
			if (count == 1) {
				Elements.frame.setVisible(true);
				frame.dispose();

			}
			if (count == 2) {
				selectSkill.frame.setVisible(true);
				frame.dispose();

			}

		}

	}

}
