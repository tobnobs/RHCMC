package GUIcave;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.Help;
import GUI.selectSkill;
import GameState.ElementalAttributes;
import GameState.Player;
import MovementMap.TheMap;

/**
 * 
 * @author  Liam Howe,Mohamed Omar, Toby Sullivan
 *
 *         Class for the menu from which the player chooses their elemental
 *         attributes after they have gone into the cave
 *
 */
public class ElementsCave extends JFrame implements ActionListener {

	public static JFrame frame;
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
	private Player player;
	private TheMap map;

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
	 * @param player
	 * 			  The old player to be edited, passed through all the menus
	 * @param map
	 * 			  The map of the client, passed through all menus
	 */
	public ElementsCave(String charname, double hp, double mp, double pwr,
			double def, double spd, double point, Player player, TheMap map) {

		this.map = map;
		this.player = player;
		this.hpstored = (int) hp;
		this.mpstored = (int) mp;
		this.defstored = (int) def;
		this.pwrstored = (int) pwr;
		this.pointstored = (int) point;
		this.namestored = charname;
		this.speedstored = (int) spd;
		this.Bpoint = (int) point;
		ElementalAttributes oldEA = player.getEA();
		this.a = oldEA.getAir();
		this.e = oldEA.getEarth();
		this.f = oldEA.getFire();
		this.w = oldEA.getWater();
		this.v = oldEA.getVoid();
		this.eaBonus = oldEA.getEABonus();
		

		Font sfont = new Font("SansSerif", Font.BOLD, 12);

		frame = new JFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		ImageIcon img = new ImageIcon("Images/ElemMenu.jpg");
		JLabel backg = new JLabel(img);
		backg.setSize(framex + 50, framey + 50);
		backg.setLocation(-19, -40);
		frame.add(backg);

		JLabel name = new JLabel(charname);
		name.setVisible(true);
		name.setSize(140, 20);
		name.setLocation(130, 290);
		name.setFont(sfont);
		name.setForeground(Color.WHITE);

		contentPane = new JPanel();
		contentPane.setLayout(null);

		airpoint = new JTextField(convertEAValueStr(a), JTextField.CENTER);
		airpoint.setSize(50, 25);
		airpoint.setLocation(70, 175);
		airpoint.setEditable(false);

		earthpoint = new JTextField(convertEAValueStr(e), JTextField.CENTER);
		earthpoint.setSize(50, 25);
		earthpoint.setLocation(170, 175);
		earthpoint.setEditable(false);

		firepoint = new JTextField(convertEAValueStr(f), JTextField.CENTER);
		firepoint.setSize(50, 25);
		firepoint.setLocation(270, 175);
		firepoint.setEditable(false);

		waterpoint = new JTextField(convertEAValueStr(w), JTextField.CENTER);
		waterpoint.setSize(50, 25);
		waterpoint.setLocation(370, 175);
		waterpoint.setEditable(false);

		voidpoint = new JTextField(convertEAValueStr(v), JTextField.CENTER);
		voidpoint.setSize(50, 25);
		voidpoint.setLocation(470, 175);
		voidpoint.setEditable(false);

		airMinus = new JButton("-");
		airMinus.setSize(50, 30);
		airMinus.setLocation(70, 215);
		airMinus.addActionListener(this);

		airPlus = new JButton("+");
		airPlus.setSize(50, 30);
		airPlus.setLocation(70, 130);
		airPlus.addActionListener(this);

		earthMinus = new JButton("-");
		earthMinus.setSize(50, 30);
		earthMinus.setLocation(170, 215);
		earthMinus.addActionListener(this);

		earthPlus = new JButton("+");
		earthPlus.setSize(50, 30);
		earthPlus.setLocation(170, 130);
		earthPlus.addActionListener(this);

		fireMinus = new JButton("-");
		fireMinus.setSize(50, 30);
		fireMinus.setLocation(270, 215);
		fireMinus.addActionListener(this);

		firePlus = new JButton("+");
		firePlus.setSize(50, 30);
		firePlus.setLocation(270, 130);
		firePlus.addActionListener(this);

		waterMinus = new JButton("-");
		waterMinus.setSize(50, 30);
		waterMinus.setLocation(370, 215);
		waterMinus.addActionListener(this);

		waterPlus = new JButton("+");
		waterPlus.setSize(50, 30);
		waterPlus.setLocation(370, 130);
		waterPlus.addActionListener(this);

		voidMinus = new JButton("-");
		voidMinus.setSize(50, 30);
		voidMinus.setLocation(470, 215);
		voidMinus.addActionListener(this);

		voidPlus = new JButton("+");
		voidPlus.setSize(50, 30);
		voidPlus.setLocation(470, 130);
		voidPlus.addActionListener(this);

		eaPoints = new JTextField(Integer.toString(eaBonus), JTextField.CENTER);
		eaPoints.setSize(30, 30);
		eaPoints.setLocation(425, 280);
		eaPoints.setEditable(false);

		back = new JButton("BACK");
		back.setSize(100, 30);
		back.setLocation(150, 350);
		back.addActionListener(this);

		next = new JButton("NEXT");
		next.setSize(100, 30);
		next.setLocation(300, 350);
		next.addActionListener(this);

		help = new JButton("Help");
		help.setSize(100, 30);
		help.setLocation(420, 350);
		help.addActionListener(this);

		contentPane.add(help);

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
		frame.setVisible(true);

	}

	/**
	 * Controls the buttons, carrying out the required actions when they are
	 * pressed
	 */
	public void actionPerformed(ActionEvent event) {

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
			player.setBaseHP(hpstored);
			player.setBaseMP(mpstored);
			player.setBaseDefence(defstored);
			player.setBasePower(pwrstored);
			player.setBonusPointsLeft(pointstored);
			player.setBaseSpeed(speedstored);
			player.setName(namestored);
			ElementalAttributes newEA = new ElementalAttributes(v, f, w, e, a);
			player.setEa(newEA);
			NameStatCave stat = new NameStatCave(player, map);
			stat.frame.setVisible(true);
			frame.setVisible(false);
			frame.dispose();
		}

		if (event.getSource() == next) {
			player.setBaseHP(hpstored);
			player.setBaseMP(mpstored);
			player.setBaseDefence(defstored);
			player.setBasePower(pwrstored);
			player.setBonusPointsLeft(pointstored);
			player.setBaseSpeed(speedstored);
			player.setName(namestored);
			ElementalAttributes newEA = new ElementalAttributes(v, f, w, e, a);
			player.setEa(newEA);
			SelectSkillCave skill = new SelectSkillCave(namestored, (int) player.getBaseHP(), (int) player.getBaseMP(),
					(int) player.getBaseDefence(), (int) player.getBasePower(), (int) player.getBaseSpeed(), Bpoint, getAir(),
					getEarth(), getFire(), getWater(), getVoid(), getEA(), player, map);
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
