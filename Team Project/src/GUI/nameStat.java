package GUI;

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

/**
 * 
 * @author Mohamed, (Graphics by Toby)
 *
 *         Class for the menu where the user selects the attributes for their
 *         character
 *
 */
public class nameStat extends JFrame implements ActionListener{

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
	private static int a = 80;
	private static int b = 80;
	private static int c = 80;
	private static int d = 80;
	private static int s = 80;
	private static int p = 100;
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
	/**
	 * Creates the frame and populates it
	 * 
	 * @param a
	 *            HP value
	 * @param b
	 *            MP value
	 * @param c
	 *            Power value
	 * @param d
	 *            Defence value
	 * @param p
	 *            Remaining points
	 * @param s
	 *            Speed value
	 * @param name2
	 *            The player's name
	 * @param six 
	 * @param five 
	 * @param four 
	 * @param three 
	 * @param two 
	 * @param one 
	 */

	private Timer timer;
	private int currentButton;
	private JButton exit;


	public nameStat(int a, int b, int c, int d, int p, int s, String name2, int air, int e, int f, int w, int v, int ea, String one, String two, String three, String four, String five, String six) {
		this.cname = name2;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.s = s;
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
		back.setLocation(-30, -26);
		frame.add(back);

		contentPane = new JPanel();
		contentPane.setLayout(null);

		name = new JTextField();
		name.setDocument(new JTextFieldLimit(10));
		name.setText(cname);
		name.setSize(150, 30);
		name.setLocation(460, 90);
		
		HP = new JTextField(a + "", JTextField.CENTER);		
		setTextField(HP, 25, 30, 510, 170);

		MP = new JTextField(b + "", JTextField.CENTER);
		setTextField(MP, 25, 30, 510, 220);


		Power = new JTextField(c + "", JTextField.CENTER);
		setTextField(Power, 25, 30, 510, 270);

		Defense = new JTextField(d + "", JTextField.CENTER);
		setTextField(Defense, 25, 30, 510, 320);


		Speed = new JTextField(s + "", JTextField.CENTER);
		setTextField(Speed, 25, 30, 510, 370);

		counter = new JTextField(p + "", JTextField.CENTER);
		setTextField(counter, 25, 30, 510, 130);


		setTimer();
		
		HPplus = new JButton("+");
		attributeButton(HPplus, 50, 30, 545, 170, 0);
		
		HPminus = new JButton("-");
		attributeButton(HPminus, 50, 30, 450, 170, 1);

		MPplus = new JButton("+");
		attributeButton(MPplus, 50, 30, 545, 220, 2);
		
		MPminus = new JButton("-");
		attributeButton(MPminus, 50, 30, 450, 220, 3);
	
		Powerplus = new JButton("+");
		attributeButton(Powerplus, 50, 30, 545, 270, 4);
		
		Powerminus = new JButton("-");
		attributeButton(Powerminus, 50, 30, 450, 270, 5);
		
		Defplus = new JButton("+");
		attributeButton(Defplus, 50, 30, 545, 320, 6);
		
		Defminus = new JButton("-");
		attributeButton(Defminus, 50, 30, 450, 320, 7);
		
		Speedplus = new JButton("+");
		attributeButton(Speedplus, 50, 30, 545, 370, 8);
		
		Speedminus = new JButton("-");
		attributeButton(Speedminus, 50, 30, 450, 370, 9);
		
		next = new JButton("NEXT");
		normalButton(next, 100, 30, 500, 420);
		
		help = new JButton("HELP");
		normalButton(help, 100, 30, 380, 420);
		
		exit = new JButton("EXIT");
		normalButton(exit, 100, 30, 60, 420);

		contentPane.add(help);
		contentPane.add(exit);
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
		frame.setResizable(false);
	    frame.setUndecorated(true);
		frame.setVisible(true);
	}

/**
 * Method used to create buttons, helps cluster in constructor
  * @param button1 the name of the button
 * @param width the width of the button
 * @param height the height of the button
 * @param x the x location of the button
 * @param y the y location of the button
 */
	private void normalButton(JButton button1, int width, int height, int x, int y) {
		button1.setSize(width, height);
		button1.setLocation(x, y);
		button1.addActionListener(this);
	}
/**
 * Method used to create text fields, helps cluster in constructor
 * @param field name of text field
 * @param width	the width of text field
 * @param height the height of the text field
 * @param x the x location of the text field
 * @param y the y location of the text field
 */
	private void setTextField(JTextField field, int width, int height, int x, int y) {
		field.setSize(width, height);
		field.setLocation(x, y);
		field.setEditable(false);
	}
/**
 * SetTimer method which is used to increment while button is pressed down. 
 */
	private void setTimer() {
		timer = new Timer(10, new ActionListener(){

			public void actionPerformed(ActionEvent e){
				//when timer start check if there is any remaining point
				count = Integer.parseInt(counter.getText());
				if (count != 0) {
					
				switch (currentButton) {
				//reduce/add attribute point depending on button pressed. 
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
 * Method to create multiple + and - buttons. 
 * @param button the name of the button
 * @param width the width of the button
 * @param height the height of the button
 * @param x the x location of the button
 * @param y the y location of the button
 * @param current assigning value to the button to use for the timer method to see.
 * 
 */
	private void attributeButton(JButton button, int width, int height, int x, int y, final int current) {
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
	 * @return
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
	//increment value depending on which button is pressed. 
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
			if (count != 100 && countspeed != 80) {
				count++;
				countspeed--;
				counter.setText(count + "");
				Speed.setText(countspeed + "");
			}
		}

		if (event.getSource() == next) {
			//when next button pressed if name give error otherwise display elemental GUI
			if(name.getText().equals("")){

				 UIManager UI=new UIManager();
				 UI.put("OptionPane.background",new ColorUIResource(180, 200, 180));
				 UI.put("Panel.background",new ColorUIResource(190, 200, 190));
				 
				JOptionPane.showMessageDialog(frame,
					    "Please Enter a Name to Continue.");
			}else{
			Elements elements = new Elements(getname(), getHP(), getMP(), getPower(),
					getDefense(), getSpeed(), getPoints(), air, e, f, w, v, ea, one, two, three, four, five, six);
			elements.frame.setVisible(true);
			frame.setVisible(false);
			}}
		if(event.getSource() == help){
			frame.hide();
			Help help = new Help("characterhelp", 0);
			
		}
		if(event.getSource() == exit){
			System.exit(0);			
		}
	}

	// reference limitation...
	public static void main(String[] args) {

	}

}

