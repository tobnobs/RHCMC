package GUIcave;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;
import java.util.concurrent.Delayed;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.View;

import GUI.Elements;
import GUI.Help;
import GameState.Player;
import GUI.JTextFieldLimit;
import MovementMap.TheMap;

/**
 * 
 * @author Liam Howe, Mohamed Omar, Toby Sullivan
 *
 *         Class for the menu where the user can change the attributes for their
 *         character after they have gone into the cave
 *
 */
public class NameStatCave extends JFrame implements ActionListener{

	static JFrame frame;
	JPanel contentPane;
	JLabel game;
	JLabel nameLabel;
	JLabel HPLabel;
	JLabel MPLabel;
	JLabel PowerLabel;
	JLabel DefenseLabel;
	JLabel charlimit;
	JTextField limit;
	JTextField name;
	JTextField HP;
	JTextField MP;
	JTextField Power;
	JTextField Defense;
	JButton HPminus;
	JButton HPplus;
	JButton MPminus;
	JButton MPplus;
	JButton Powerminus;
	JButton Powerplus;
	JButton Defminus;
	JButton Defplus;
	JButton next;

	JLabel points;
	JTextField counter;
	private int count;
	private int counthp;
	private int countmp;
	private int countpwr;
	private int countdef;
	private int countspeed;
	private static int a;
	private static int b;
	private static int c;
	private static int d;
	private static int s;
	private static int p;
	private static String cname = "";
	private JButton Speedminus;
	private JButton Speedplus;
	private JTextField Speed;
	private JLabel speedLabel;
	private JButton help;
	
	private final int framex = 640;
	private final int framey = 480;
	private int air = 1;
	private int e = 1;
	private int f = 1;
	private int w = 1;
	private int v = 1;
	private int ea;
	String one, two, three, four, five, six;
	
	private Player player;
	private TheMap map;
	private Timer timer;
	private int currentButton;
	/**
	 * Creates the frame and populates it
	 * 
	 * @param player
	 * 			  The old player to be edited, passed through all the menus
	 * @param map
	 * 			  The map of the client, passed through all menus
	*/
	
	public NameStatCave(Player player, TheMap map) {
		this.map = map;
		this.player = player;
		this.cname = player.getName();
		this.a = (int) player.getBaseHP();
		this.b = (int) player.getBaseMP();
		this.c = (int) player.getBasePower();
		this.d = (int) player.getBaseDefence();
		this.s = (int) player.getBaseSpeed();
		this.p = (int) player.getBonusPointsLeft();
		this.air = air;
		this.e = e;
		this.f = f;
		this.w = w;
		this.v = v;
		this.ea = ea;
		this.one = one;
		this.two = two;
		this.three = three;
		this.four = four;
		this.five = five;
		this.six = six;
		
		System.out.println("new name:" + cname);
		frame = new JFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		ImageIcon img = new ImageIcon("Images/CharacterMenu2.jpg");
		JLabel back = new JLabel(img);
		back.setSize(framex+50, framey+50);
		back.setLocation(-30, -46);
		frame.add(back);

		contentPane = new JPanel();
		contentPane.setLayout(null);

		name = new JTextField();
		name.setDocument(new JTextFieldLimit(10));
		name.setText(cname);
		name.setSize(150, 30);
		name.setLocation(460, 70);

		HP = new JTextField(a + "", JTextField.CENTER);
		HP.setSize(25, 30);
		HP.setLocation(510, 150);
		HP.setEditable(false);

		MP = new JTextField(b + "", JTextField.CENTER);
		MP.setSize(25, 30);
		MP.setLocation(510, 200);
		MP.setEditable(false);

		Power = new JTextField(c + "", JTextField.CENTER);
		Power.setSize(25, 30);
		Power.setLocation(510, 250);
		Power.setEditable(false);

		Defense = new JTextField(d + "", JTextField.CENTER);
		Defense.setSize(25, 30);
		Defense.setLocation(510, 300);
		Defense.setEditable(false);

		Speed = new JTextField(s + "", JTextField.CENTER);
		Speed.setSize(25, 30);
		Speed.setLocation(510, 350);
		Speed.setEditable(false);
		
		counter = new JTextField(p + "", JTextField.CENTER);
		counter.setSize(30, 30);
		counter.setLocation(510, 110);
		counter.setEditable(false);

		setTimer();
		
		HPplus = new JButton("+");
		initButton(HPplus, 50, 30, 545, 150, 0);
		
		HPminus = new JButton("-");
		initButton(HPminus, 50, 30, 450, 150, 1);

		MPplus = new JButton("+");
		initButton(MPplus, 50, 30, 545, 200, 2);
		
		MPminus = new JButton("-");
		initButton(MPminus, 50, 30, 450, 200, 3);
	
		Powerplus = new JButton("+");
		initButton(Powerplus, 50, 30, 545, 250, 4);
		
		Powerminus = new JButton("-");
		initButton(Powerminus, 50, 30, 450, 250, 5);
		
		Defplus = new JButton("+");
		initButton(Defplus, 50, 30, 545, 300, 6);
		
		Defminus = new JButton("-");
		initButton(Defminus, 50, 30, 450, 300, 7);
		
		Speedplus = new JButton("+");
		initButton(Speedplus, 50, 30, 545, 350, 8);
		
		Speedminus = new JButton("-");
		initButton(Speedminus, 50, 30, 450, 350, 9);
				
		next = new JButton("NEXT");
		next.setSize(100, 30);
		next.setLocation(500, 400);
		next.addActionListener(this);

		help = new JButton("Help");
		help.setSize(100, 30);
		help.setLocation(380, 400);
		help.addActionListener(this);
		
		contentPane.add(help);
		
		contentPane.add(next);
		contentPane.add(name);
		contentPane.add(HP);
		contentPane.add(HPminus);
		contentPane.add(HPplus);
		contentPane.add(MP);
		contentPane.add(MPminus);
		contentPane.add(MPplus);
		contentPane.add(Power);
		contentPane.add(Powerminus);
		contentPane.add(Powerplus);
		contentPane.add(Defense);
		contentPane.add(Defminus);
		contentPane.add(Defplus);
		contentPane.add(counter);
		contentPane.add(Speedminus);
		contentPane.add(Speedplus);
		contentPane.add(Speed);
		contentPane.add(back);

		frame.setContentPane(contentPane);
		frame.setTitle("Character");
		frame.setSize(framex, framey);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	/**
	 * SetTimer method which is used to increment while button is pressed down. 
	 */
	private void setTimer() {
		timer = new Timer(10, new ActionListener(){

			public void actionPerformed(ActionEvent e){
				if (count != 0) {
					count = Integer.parseInt(counter.getText());

				switch (currentButton) {
			
					case 0:
						count--;
						counter.setText(count + "");
						
						counthp = getHP();
						counthp++;
						HP.setText(counthp + "");
						break;
					case 1:
						counthp = getHP();
						if(counthp != 80){
							count++;
							counter.setText(count + "");
							counthp--;
							HP.setText(counthp + "");
						}
					break;
					case 2:
						count--;
						counter.setText(count + "");
						countmp = getMP();
						countmp++;
						MP.setText(countmp + "");
						break;
					case 3:	
						countmp = getMP();
						if(countmp != 80){
							count++;
							counter.setText(count + "");
							countmp--;
							MP.setText(countmp + "");
						}
						break;
					case 4:
						count--;
						counter.setText(count + "");
						countpwr = getPower();
						countpwr++;
						Power.setText(countpwr + "");
					break;
					case 5:
						countpwr = getPower();
						if(countpwr != 80){
							count++;
							counter.setText(count + "");
							countpwr--;
							Power.setText(countpwr + "");
						}
						break;
					case 6:
						count--;
						counter.setText(count + "");
						
						countdef = getDefense();
						countdef++;
						Defense.setText(countdef + "");
					break;
					case 7:
						countdef = getDefense();
						if(countdef != 80){
							count++;
							counter.setText(count + "");
							countdef--;
							Defense.setText(countdef + "");
						}
						break;
					case 8:
						count--;
						counter.setText(count + "");
						countspeed = getSpeed();
						countspeed++;
						Speed.setText(countspeed + "");
					break;
					case 9:
						
						countspeed = getSpeed();
						if(countspeed != 80){
							count++;
							counter.setText(count + "");
							countspeed--;
							Speed.setText(countspeed + "");	
						}
						break;
					default:
						break;
					}
				}
			}
			

		});	
		timer.setRepeats(true);
	}
	/**
	 * used as a helper function to create buttons
	 * @param button	The button to be created
	 * @param width     The width of the button
	 * @param height	The height of the button
	 * @param x			The x coordinate of the button
	 * @param y			The y coordinate of the button
	 * @param current	The assigning value to the button to use for the timer method to see.
	 */
	private void initButton(JButton button, int width, int height, int x, int y, final int current) {
		button.setSize(width, height);
		button.setLocation(x, y);
		button.addActionListener(this);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				timer.stop();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				currentButton = current;
				timer.restart();
			}
		});
	}

	/**
	 * Returns the player's inputed name
	 * 
	 * @return The player's name
	 */
	public String getname() {
		cname = name.getText();
		return cname;
	}

	/**
	 * Returns the player's inputed HP value
	 * 
	 * @return The player's inputed HP value
	 */
	public Integer getHP() {
		a = Integer.parseInt(HP.getText());
		return a;
	}

	/**
	 * Returns the player's inputed MP value
	 * 
	 * @return The player's inputed MP value
	 */
	public Integer getMP() {
		b = Integer.parseInt(MP.getText());
		return b;
	}

	/**
	 * Returns the player's inputed power value
	 * 
	 * @return The player's inputed power value
	 */
	public Integer getPower() {
		c = Integer.parseInt(Power.getText());
		return c;
	}

	/**
	 * Returns the player's inputed defence value
	 * 
	 * @return The player's inputed defence value
	 */
	public Integer getDefense() {
		d = Integer.parseInt(Defense.getText());
		return d;
	}

	/**
	 * Returns the player's inputed speed value
	 * 
	 * @return The player's inputed speed value
	 */
	public Integer getSpeed() {
		s = Integer.parseInt(Speed.getText());
		return s;
	}

	/**
	 * Returns the amount of points remaining to be allocated
	 * 
	 * @return  The amount of points remaining to be allocated
	 */
	public Integer getPoints() {
		p = Integer.parseInt(counter.getText());
		return p;

	}

	/**
	 * Controls all the buttons, performing the required actions when they are
	 * pressed
	 */
	public void actionPerformed(ActionEvent event) {
			if (event.getSource() == HPplus) {
	
		count = Integer.parseInt(counter.getText());
			counthp = getHP();
			if (count != 0) {
				count--;
				counthp++;
				counter.setText(count + "");
				HP.setText(counthp + "");
			}
		}
		if (event.getSource() == HPminus) {
			count = Integer.parseInt(counter.getText());
			counthp = getHP();
			// System.out.println(counthp);
			if (count != 100 && counthp != 80) {
				count++;
				counthp--;
				counter.setText(count + "");
				HP.setText(counthp + "");
			}
		}

		if (event.getSource() == MPplus) {
			count = Integer.parseInt(counter.getText());
			countmp = getMP();
			if (count != 0) {
				count--;
				countmp++;
				counter.setText(count + "");
				MP.setText(countmp + "");
			}
		}
		if (event.getSource() == MPminus) {
			count = Integer.parseInt(counter.getText());
			countmp = getMP();
			// System.out.println(counthp);
			if (count != 100 && countmp != 80) {
				count++;
				countmp--;
				counter.setText(count + "");
				MP.setText(countmp + "");
			}
		}

		if (event.getSource() == Powerplus) {
			count = Integer.parseInt(counter.getText());
			countpwr = getPower();
			if (count != 0) {
				count--;
				countpwr++;
				counter.setText(count + "");
				Power.setText(countpwr + "");
			}
		}
		if (event.getSource() == Powerminus) {
			count = Integer.parseInt(counter.getText());
			countpwr = getPower();
			// System.out.println(counthp);
			if (count != 100 && countpwr != 80) {
				count++;
				countpwr--;
				counter.setText(count + "");
				Power.setText(countpwr + "");
			}
		}

		if (event.getSource() == Defplus) {
			count = Integer.parseInt(counter.getText());
			countdef = getDefense();
			if (count != 0) {
				count--;
				countdef++;
				counter.setText(count + "");
				Defense.setText(countdef + "");
			}
			}
		
		if (event.getSource() == Defminus) {
			count = Integer.parseInt(counter.getText());
			countdef = getDefense();
			// System.out.println(counthp);
			if (count != 100 && countdef != 80) {
				count++;
				countdef--;
				counter.setText(count + "");
				Defense.setText(countdef + "");
			}
			}
		if (event.getSource() == Speedplus) {
			count = Integer.parseInt(counter.getText());
			countspeed = getSpeed();
			if (count != 0) {
				count--;
				countspeed++;
				counter.setText(count + "");
				Speed.setText(countspeed + "");
			}
		}
		if (event.getSource() == Speedminus) {
			count = Integer.parseInt(counter.getText());
			countspeed = getSpeed();
			// System.out.println(counthp);
			if (count != 100 && countspeed != 80) {
				count++;
				countspeed--;
				counter.setText(count + "");
				Speed.setText(countspeed + "");
			}
		}

		if (event.getSource() == next) {
			
			if(name.getText().equals("")){

				 UIManager UI=new UIManager();
				 UI.put("OptionPane.background",new ColorUIResource(180, 200, 180));
				 UI.put("Panel.background",new ColorUIResource(190, 200, 190));
				 
				JOptionPane.showMessageDialog(frame,
					    "Please Enter a Name to Continue.");
			}else{
				ElementsCave elements = new ElementsCave(getname(), getHP(), getMP(), getPower(),
						getDefense(), getSpeed(), getPoints(), player, map);
			elements.frame.setVisible(true);
			frame.setVisible(false);
			}}
		if(event.getSource() == help){
			frame.hide();
			Help help = new Help("characterhelp", 0);
			
		}
	}

	// reference limitation...
	public static void main(String[] args) {

	}

}

