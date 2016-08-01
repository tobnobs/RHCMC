package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Mohamed Omar, Toby Sullivan
 *
 *         Class for the menu from which the player chooses their elemental
 *         attributes
 *
 */
public class Elements extends JFrame implements ActionListener {

	static JFrame frame;
	JPanel contentPane;
	JLabel game;
	JLabel nameLabel;
	JLabel airLabel;
	JLabel earthLabel;
	JLabel fireLabel;
	JLabel waterLabel;
	JLabel voidLabel;
	JTextField airpoint;
	JTextField earthpoint;
	JTextField firepoint;
	JTextField waterpoint;
	JTextField voidpoint;
	private JButton airPlus;
	private JButton airMinus;
	private JButton earthPlus;
	private JButton earthMinus;
	private JButton firePlus;
	private JButton fireMinus;
	private JButton waterPlus;
	private JButton waterMinus;
	private JButton voidPlus;
	private JButton voidMinus;
	private JLabel name;

	JLabel ea;
	JTextField eaPoints;
	private JButton back;
	private JButton next;

	private Integer a;
	private Integer e;
	private Integer f;
	private Integer w;
	private Integer v;
	private Integer eaBonus;
	private Integer eaPoint;
	private int count;
	private int eaCount;

	private int hpstored;
	private int mpstored;
	private int defstored;
	private int pwrstored;
	private int pointstored;
	private String namestored;
	private int speedstored;
	private int Bpoint;
	String one, two, three, four, five, six;
	private final int framex = 640;
	private final int framey = 480;
	private JButton help;
	private JButton exit;

	/**
	 * Creates the frame for the menu, and populates it
	 * 
	 * @param charname
	 *            The player's name
	 * @param hp
	 *            The player's HP value
	 * @param mp
	 *            The player's MP value
	 * @param pwr
	 *            The player's power value
	 * @param def
	 *            The player's defence value
	 * @param spd
	 *            The player's speed value
	 * @param point
	 *            The player's elemental attribute points
	 * @param six2
	 *            The player's sixth skill
	 * @param five2
	 *            The player's fifth skill
	 * @param four2
	 *            The player's fourth skill
	 * @param three2
	 *            The player's third skill
	 * @param two2
	 *            The player's second skill
	 * @param one2
	 *            The player's first skill
	 */
	public Elements(String charname, double hp, double mp, double pwr,
			double def, double spd, double point, int a, int e, int f, int w,
			int v, int ea, String one2, String two2, String three2,
			String four2, String five2, String six2) {

		this.hpstored = (int) hp;
		this.mpstored = (int) mp;
		this.defstored = (int) def;
		this.pwrstored = (int) pwr;
		this.pointstored = (int) point;
		this.namestored = charname;
		this.speedstored = (int) spd;
		this.Bpoint = (int) point;
		this.a = a;
		this.e = e;
		this.f = f;
		this.w = w;
		this.v = v;
		this.eaBonus = ea;
		this.one = one2;
		this.two = two2;
		this.three = three2;
		this.four = four2;
		this.five = five2;
		this.six = six2;

		Font sfont = new Font("SansSerif", Font.BOLD, 12);

		frame = new JFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//background image storing it in the label.
		ImageIcon img = new ImageIcon("Images/ElemMenu.jpg");
		JLabel backg = new JLabel(img);
		backg.setSize(framex + 50, framey + 50);
		backg.setLocation(-19, -20);
		frame.add(backg);

		JLabel name = new JLabel(charname);
		name.setVisible(true);
		name.setSize(140, 20);
		name.setLocation(130, 310);
		name.setFont(sfont);
		name.setForeground(Color.WHITE);

		contentPane = new JPanel();
		contentPane.setLayout(null);

		airpoint = new JTextField(convertEAValueStr(a), JTextField.CENTER);
		setTextField(airpoint, 50, 25, 70, 195);

		earthpoint = new JTextField(convertEAValueStr(e), JTextField.CENTER);
		setTextField(earthpoint, 50, 25, 170, 195);

		firepoint = new JTextField(convertEAValueStr(f), JTextField.CENTER);
		setTextField(firepoint, 50, 25, 270, 195);
		
		waterpoint = new JTextField(convertEAValueStr(w), JTextField.CENTER);
		setTextField(waterpoint, 50, 25, 370, 195);

		voidpoint = new JTextField(convertEAValueStr(v), JTextField.CENTER);
		setTextField(voidpoint, 50, 25, 470, 195);

		eaPoints = new JTextField(Integer.toString(eaBonus), JTextField.CENTER);
		setTextField(eaPoints, 30, 30, 425, 300);
		

		airMinus = new JButton("-");
		normalButton(airMinus, 50, 30, 70, 235);

		airPlus = new JButton("+");
		normalButton(airPlus, 50, 30, 70, 150);

		earthMinus = new JButton("-");
		normalButton(earthMinus, 50, 30, 170, 235);

		earthPlus = new JButton("+");
		normalButton(earthPlus, 50, 30, 170, 150);

		fireMinus = new JButton("-");
		normalButton(fireMinus, 50, 30, 270, 235);

		firePlus = new JButton("+");
		normalButton(firePlus, 50, 30, 270, 150);

		waterMinus = new JButton("-");
		normalButton(waterMinus, 50, 30, 370, 235);

		waterPlus = new JButton("+");
		normalButton(waterPlus, 50, 30, 370, 150);

		voidMinus = new JButton("-");
		normalButton(voidMinus, 50, 30, 470, 235);

		voidPlus = new JButton("+");
		normalButton(voidPlus, 50, 30, 470, 150);

		exit = new JButton("EXIT");
		normalButton(exit, 100, 30, 40, 370);
		
		back = new JButton("BACK");
		normalButton(back, 100, 30, 150, 370);

		next = new JButton("NEXT");
		normalButton(next, 100, 30, 300, 370);

		help = new JButton("HELP");
		normalButton(help, 100, 30, 420, 370);

		contentPane.add(help);
		contentPane.add(exit);
		contentPane.add(name);
		contentPane.add(back);
		contentPane.add(next);
		contentPane.add(eaPoints);
		contentPane.add(airMinus);
		contentPane.add(airPlus);
		contentPane.add(earthMinus);
		contentPane.add(earthPlus);
		contentPane.add(fireMinus);
		contentPane.add(firePlus);
		contentPane.add(waterMinus);
		contentPane.add(waterPlus);
		contentPane.add(voidMinus);
		contentPane.add(voidPlus);

		contentPane.add(airpoint);
		contentPane.add(earthpoint);
		contentPane.add(firepoint);
		contentPane.add(waterpoint);
		contentPane.add(voidpoint);
		contentPane.add(backg);

		frame.setContentPane(contentPane);
		frame.setTitle("Character");
		frame.setSize(framex, framey);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	    frame.setUndecorated(true);
		frame.setVisible(true);

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
	 * Controls the buttons, carrying out the required actions when they are
	 * pressed
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == exit){
			System.exit(0);			
		}
		
		if (event.getSource() == airPlus) {
			if (a < 3) {
				a++;
				eaCount = getEA();
				eaCount++;
				eaPoints.setText(eaCount + "");
				airpoint.setText(convertEAValueStr((Integer) a));
			}
		}

		if (event.getSource() == airMinus) {
			if (a > -1 && getEA() > 0) {
				a--;
				eaCount = getEA();
				eaCount--;
				eaPoints.setText(eaCount + "");
				airpoint.setText(convertEAValueStr((Integer) a));
			}
		}

		if (event.getSource() == earthPlus) {
			if (e < 3) {
				e++;
				eaCount = getEA();
				eaCount++;
				eaPoints.setText(eaCount + "");
				earthpoint.setText(convertEAValueStr((Integer) e));
			}
		}

		if (event.getSource() == earthMinus) {
			if (e > -1 && getEA() > 0) {
				e--;
				eaCount = getEA();
				eaCount--;
				eaPoints.setText(eaCount + "");
				earthpoint.setText(convertEAValueStr((Integer) e));
			}
		}

		if (event.getSource() == firePlus) {
			if (f < 3) {
				f++;
				eaCount = getEA();
				eaCount++;
				eaPoints.setText(eaCount + "");
				firepoint.setText(convertEAValueStr((Integer) f));
			}
		}

		if (event.getSource() == fireMinus) {
			if (f > -1 && getEA() > 0) {
				f--;
				eaCount = getEA();
				eaCount--;
				eaPoints.setText(eaCount + "");
				firepoint.setText(convertEAValueStr((Integer) f));
			}
		}

		if (event.getSource() == waterPlus) {
			if (w < 3) {
				w++;
				eaCount = getEA();
				eaCount++;
				eaPoints.setText(eaCount + "");
				waterpoint.setText(convertEAValueStr((Integer) w));
			}
		}

		if (event.getSource() == waterMinus) {
			if (w > -1 && getEA() > 0) {
				w--;
				eaCount = getEA();
				eaCount--;
				eaPoints.setText(eaCount + "");
				waterpoint.setText(convertEAValueStr((Integer) w));
			}
		}

		if (event.getSource() == voidPlus) {
			if (v < 3) {
				v++;
				eaCount = getEA();
				eaCount++;
				eaPoints.setText(eaCount + "");
				voidpoint.setText(convertEAValueStr((Integer) v));
			}
		}

		if (event.getSource() == voidMinus) {
			if (v > -1 && getEA() > 0) {
				v--;
				eaCount = getEA();
				eaCount--;
				eaPoints.setText(eaCount + "");
				voidpoint.setText(convertEAValueStr(v));
			}
		}

		if (event.getSource() == back) {
			nameStat stat = new nameStat(hpstored, mpstored,
					pwrstored,defstored, pointstored, speedstored, namestored, getAir(),
					getEarth(), getFire(), getWater(), getVoid(), getEA(), one,
					two, three, four, five, six);
			stat.frame.setVisible(true);
			frame.setVisible(false);
			frame.dispose();
		}

		if (event.getSource() == next) {
			selectSkill skill = new selectSkill(namestored, hpstored, mpstored,
					defstored, pwrstored, speedstored, Bpoint, getAir(),
					getEarth(), getFire(), getWater(), getVoid(), getEA(), one,
					two, three, four, five, six);
			skill.frame.setVisible(true);
			frame.setVisible(false);
			frame.dispose();
		}
		if (event.getSource() == help) {
			Help help = new Help("elementhelp", 1);
			frame.hide();
		}

	}

	/**
	 * Converts an elemental attribute value into a String for players
	 * 
	 * @param eaValue
	 *            The player's EA values
	 * @return The EA value converted to a string
	 */
	public String convertEAValueStr(Integer eaValue) {
		String EAString = "";
		switch (eaValue) {
		case -1:
			EAString = "Absorb";
			break;
		case 0:
			EAString = "Null";
			break;
		case 1:
			EAString = "Strong";
			break;
		case 2:
			EAString = "Normal";
			break;
		case 3:
			EAString = "Weak";
			break;
		}
		return EAString;
	}

	/**
	 * Converts an elemental attribute value into a String for players
	 * 
	 * @param eaValue
	 *            The player's EA values
	 * @return The EA value converted to a string
	 */
	public int convertStringtoEA(String value) {
		int EAvalue = 1;
		switch (value) {
		case "Absorb":
			EAvalue = -1;
			break;
		case "Null":
			EAvalue = 0;
			break;
		case "Strong":
			EAvalue = 1;
			break;
		case "Normal":
			EAvalue = 2;
			break;
		case "Weak":
			EAvalue = 3;
			break;
		}
		return EAvalue;
	}

	/**
	 * Returns the EA points available to the player
	 * 
	 * @return The EA points available to the player
	 */
	public Integer getEA() {
		eaPoint = Integer.parseInt(eaPoints.getText());
		return eaPoint;
	}

	/**
	 * Returns the air EA value
	 * 
	 * @return The air EA value
	 */
	public int getAir() {
		a = convertStringtoEA(airpoint.getText());
		return a;
	}

	/**
	 * Returns the earth EA value
	 * 
	 * @return The earth EA value
	 */
	public Integer getEarth() {
		e = convertStringtoEA(earthpoint.getText());
		return e;
	}

	/**
	 * Returns the fire EA value
	 * 
	 * @return The fire EA value
	 */
	public Integer getFire() {
		f = convertStringtoEA(firepoint.getText());
		return f;
	}

	/**
	 * Returns the water EA value
	 * 
	 * @return The water EA value
	 */
	public Integer getWater() {
		w = convertStringtoEA(waterpoint.getText());
		return w;
	}

	/**
	 * Returns the void EA value
	 * 
	 * @return The void EA value
	 */
	public Integer getVoid() {
		v = convertStringtoEA(voidpoint.getText());
		return v;
	}
}
