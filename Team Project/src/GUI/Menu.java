package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Mohamed Omar, Toby Sullivan
 * 
 *         Class for the main menu when the game is started
 *
 */
public class Menu extends JFrame implements ActionListener {

	JPanel namePanel;
	JPanel contentPane;
	JButton playButton;
	JButton helpButton;
	JLabel nameLabel;
	JFrame frame;
	nameStat stat;

	//frame width and height. 
	private final int framex = 610;
	private final int framey = 400;
	//values to set up base values etc. 
	//values for attributes
	private static int a = 80;
	private static int b = 80;
	private static int c = 80;
	private static int d = 80;
	private static int s = 80;
	//points left to allocate
	private static int p = 100;
	//character name
	private static String cname = "";
	//elemental attributes
	private static int air = 1;
	private static int e = 1;
	private static int f = 1;
	private static int w = 1;
	private static int v = 1;
	private static int ea = 0;
	//set the textfield for each skill to empty.
	String one = " ";
	String two = " "; 
	String three = " ";
	String four = " ";
	String five = " ";
	String six = " ";
	

	/**
	 * Creates the frame for the menu, adds the buttons, and prints the
	 * background image
	 */
	public Menu() {

		frame = new JFrame("RHC&MC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setLayout(null);

		playButton = new JButton("Play");
		playButton.setSize(200, 50);
		playButton.setLocation(200, 250);
		playButton.addActionListener(this);

		ImageIcon img = new ImageIcon("Images/Menu.jpg");
		JLabel back = new JLabel(img);
		back.setSize(framex+50, framey+50);
		back.setLocation(-31, -26);
		frame.add(back);

		contentPane.add(back);
		contentPane.add(playButton);

		frame.setContentPane(contentPane);
		frame.setSize(framex, framey);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	    frame.setUndecorated(true);
		frame.setVisible(true);

	}

	/**
	 * Progresses the user to the next menu when the play button is pressed
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == playButton) {
			nameStat stat = new nameStat(a, b, c, d, p, s, cname, air, e, f, w, v, ea, one, two, three, four, five, six);			
			stat.frame.setVisible(true);
			frame.setVisible(false);
			frame.dispose();
		}
	}
	
	public static void main(String[] args) {
		Menu menu = new Menu();
	}

}
