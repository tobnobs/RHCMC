package GUIcave;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import GUI.Help;
import GameState.DamageSkill.ElementalType;
import GameState.ElementalAttributes;
import GameState.Player;
import GameState.Skill;
import GameState.Skill.Targets;
import GameState.SkillsList;
import MovementMap.TheMap;

/**
 * @author Liam Howe, Mohamed Omar, Toby Sullivan
 *
 *         Class for the menu for re-selecting skills after they have gone into the cave
 */
public class SelectSkillCave extends JFrame implements ActionListener {

	static JFrame frame;
	private JPanel contentPane;
	private JLabel skillmenu;

	private JLabel skillLabel1;
	private JLabel skillLabel2;
	private JLabel skillLabel3;
	private JLabel skillLabel4;
	private JLabel skillLabel5;
	private JLabel skillLabel6;

	private JTextField skill1;
	private JTextField skill2;
	private JTextField skill3;
	private JTextField skill4;
	private JTextField skill5;
	private JTextField skill6;

	private JButton delete1;
	private JButton delete2;
	private JButton delete3;
	private JButton delete4;
	private JButton delete5;
	private JButton delete6;

	private JButton addskill;
	private JButton play;
	private JButton back;
	private JTable table;
	private JTable table2;
	private JTable table3;
	private String selectedSkill;

	String skillone;
	String skilltwo;
	String skillthree;
	String skillfour;
	String skillfive;
	String skillsix;
	private JButton addskill2;
	private JButton addskill3;
	SkillsList skillslist;

	public static TheMap map;
	Player player;
	String name;
	double hp;
	double mp;
	double defense;
	double power;
	double speed;
	double Bpoints;
	int a;
	int e;
	int f;
	int w;
	int v;
	int eaBonus;

	public Random rand = new Random();

	private final int framex = 620;
	private final int framey = 700;
	private JButton help;

	/**
	 * Creates the frame, and populates it
	 * 
	 * @param charname
	 *            The player's name
	 * @param hp
	 *            The player's HP value
	 * @param mp
	 *            The player's MP value
	 * @param def
	 *            The player's defence value
	 * @param pwr
	 *            The player's power value
	 * @param spd
	 *            The player's speed value
	 * @param Bpoints
	 *            The amount of points the player has for allocation to
	 *            attributes
	 * @param a
	 *            The strength of the player's air attributes
	 * @param e
	 *            The strength of the player's earth attributes
	 * @param f
	 *            The strength of the player's fire attributes
	 * @param w
	 *            The strength of the player's water attributes
	 * @param v
	 *            The strength of the player's void attributes
	 * @param eaBonus
	 * 			  The bonus points the player can still put into Elemental Attributes           
	 * @param player
	 * 			  The old player to be edited, passed through all the menus
	 * @param map
	 * 			  The map of the client, passed through all menus
	 */
	public SelectSkillCave(String charname, int hp, int mp, int def, int pwr,
			int spd, int Bpoints, int a, int e, int f, int w, int v,
			int eaBonus, Player player, TheMap map) {
		
		this.map = map;
		this.player = player;
		this.name = charname;
		this.hp = hp;
		this.mp = mp;
		this.defense = def;
		this.power = pwr;
		this.speed = spd;
		this.Bpoints = Bpoints;
		this.a = a;
		this.e = e;
		this.f = f;
		this.w = w;
		this.v = v;
		this.eaBonus = eaBonus;
		Skill[] skills = player.getSkillSet();
		this.skillone = skills[0].getSkillName();
		this.skilltwo = skills[1].getSkillName();
		this.skillthree = skills[2].getSkillName();
		this.skillfour = skills[3].getSkillName();
		this.skillfive = skills[4].getSkillName();
		this.skillsix = skills[5].getSkillName();

		frame = new JFrame();
		frame.setTitle("Character");
		frame.setSize(framex, framey);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);

		ImageIcon img = new ImageIcon("Images/SkillMenu.jpg");
		JLabel backg = new JLabel(img);
		backg.setSize(framex + 200, framey + 200);
		backg.setLocation(-70, -130);
		frame.add(backg);

		skillslist = new SkillsList();

		// Populating the Damage Skills
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable() {
			// Implement table cell tool tips.
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);

				try {
					tip = getValueAt(rowIndex, 0).toString();

					for (int i = 0; i < skillslist.DamageSkillList.size(); i++) {
						if (tip.equals(skillslist.DamageSkillList.get(i)
								.getSkillName())) {
							tip = skillslist.DamageSkillList.get(i)
									.getFlavourText();
						}
					}

				} catch (RuntimeException e1) {
					// catch null pointer exception if mouse is over an empty
					// line
				}
				return tip;
			}
		};

		table.setModel(model);
		model.addColumn("Skillname");
		model.addColumn("MP Cost");
		model.addColumn("Target");
		model.addColumn("Power");
		model.addColumn("Accurracy");
		model.addColumn("Type");

		for (int i = 1; i < skillslist.DamageSkillList.size(); i++) {

			String skillName = skillslist.DamageSkillList.get(i).getSkillName();
			int MPCost = skillslist.DamageSkillList.get(i).getMP();
			String Target = convertTargetValue(skillslist.DamageSkillList
					.get(i).getTarget());
			int Power = skillslist.DamageSkillList.get(i).getPower();
			int Accurracy = skillslist.DamageSkillList.get(i).getAccuracy();
			String Type = convertDamageTypeValue(skillslist.DamageSkillList
					.get(i).getElementalType());

			Object[] row = { skillName, MPCost, Target, Power, Accurracy, Type };
			model.addRow(row);
		}

		// Populate the Buffs Skills
		DefaultTableModel model2 = new DefaultTableModel();

		table2 = new JTable() {
			// Implement table cell tool tips.
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);

				try {
					tip = getValueAt(rowIndex, 0).toString();

					for (int i = 0; i < skillslist.HealingSkillList.size(); i++) {
						if (tip.equals(skillslist.HealingSkillList.get(i)
								.getSkillName())) {
							tip = skillslist.HealingSkillList.get(i)
									.getFlavourText();
						}
					}

				} catch (RuntimeException e1) {
					// catch null pointer exception if mouse is over an empty
					// line
				}
				return tip;
			}
		};

		table2.setModel(model2);

		model2.addColumn("Skillname");
		model2.addColumn("MP Cost");
		model2.addColumn("Target");
		model2.addColumn("Healing Power");

		for (int i = 0; i < skillslist.HealingSkillList.size(); i++) {

			String skillName = skillslist.HealingSkillList.get(i)
					.getSkillName();
			int MPCost = skillslist.HealingSkillList.get(i).getMP();
			String Target = convertTargetValue(skillslist.HealingSkillList.get(
					i).getTarget());
			int Healing = skillslist.HealingSkillList.get(i).getHealingPower();

			Object[] row = { skillName, MPCost, Target, Healing };
			model2.addRow(row);
		}

		// Buffskills

		DefaultTableModel model3 = new DefaultTableModel();
		table3 = new JTable() {
			// Implement table cell tool tips.
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);

				try {
					tip = getValueAt(rowIndex, 0).toString();

					for (int i = 0; i < skillslist.BuffingSkillList.size(); i++) {
						if (tip.equals(skillslist.BuffingSkillList.get(i)
								.getSkillName())) {
							tip = skillslist.BuffingSkillList.get(i)
									.getFlavourText();
						}
					}

				} catch (RuntimeException e1) {
					// catch null pointer exception if mouse is over an empty
					// line
				}
				return tip;
			}
		};

		table3.setModel(model3);
		model3.addColumn("Skillname");
		model3.addColumn("MP Cost");
		model3.addColumn("Target");
		model3.addColumn("Stat affected");

		for (int i = 0; i < skillslist.BuffingSkillList.size(); i++) {

			String skillName = skillslist.BuffingSkillList.get(i)
					.getSkillName();
			int MPCost = skillslist.BuffingSkillList.get(i).getMP();
			String Target = convertTargetValue(skillslist.BuffingSkillList.get(
					i).getTarget());
			String Stat = convertStatAffectedValue(skillslist.BuffingSkillList
					.get(i).getstatAffected());

			Object[] row = { skillName, MPCost, Target, Stat };
			model3.addRow(row);
		}

		// stat affected
		// 0 = power
		// 1 = defense
		// 2 = speed
		// 3 = nullify stat penalties and buffs

		JScrollPane tableSP = new JScrollPane(table);
		tableSP.setPreferredSize(new Dimension(600, 100));

		JScrollPane tableSP2 = new JScrollPane(table2);
		tableSP2.setPreferredSize(new Dimension(600, 100));

		JScrollPane tableSP3 = new JScrollPane(table3);
		tableSP3.setPreferredSize(new Dimension(600, 100));

		JPanel tablePanel = new JPanel();

		addskill = new JButton("<html> Add <br> Damage Skill</html>");
		addskill.setSize(100, 30);
		addskill.setLocation(500, 400);
		addskill.addActionListener(this);

		addskill2 = new JButton("<html> Add <br> Healing Skill</html>");
		addskill2.setSize(100, 30);
		addskill2.setLocation(500, 110);
		addskill2.addActionListener(this);
		addskill3 = new JButton("<html> Add <br> Buff Skill</html>");
		addskill3.setSize(100, 30);
		addskill3.setLocation(500, 110);
		addskill3.addActionListener(this);

		tablePanel.add(tableSP);
		tablePanel.add(addskill);
		tablePanel.add(tableSP2);
		tablePanel.add(addskill2);
		tablePanel.add(tableSP3);
		tablePanel.add(addskill3);
		tablePanel.setBackground(new Color(50, 80, 50));

		tablePanel.setPreferredSize(new Dimension(320, 470));

		contentPane.setPreferredSize(new Dimension(320, 240));

		// skill text fields.
		skill1 = new JTextField(skillone, JTextField.CENTER);
		skill1.setSize(90, 20);
		skill1.setLocation(5, 110);
		skill1.setEditable(false);

		skill2 = new JTextField(skilltwo, JTextField.CENTER);
		skill2.setSize(90, 20);
		skill2.setLocation(165, 110);
		skill2.setEditable(false);

		skill3 = new JTextField(skillthree, JTextField.CENTER);
		skill3.setSize(90, 20);
		skill3.setLocation(325, 110);
		skill3.setEditable(false);

		skill4 = new JTextField(skillfour, JTextField.CENTER);
		skill4.setSize(90, 20);
		skill4.setLocation(5, 160);
		skill4.setEditable(false);

		skill5 = new JTextField(skillfive, JTextField.CENTER);
		skill5.setSize(90, 20);
		skill5.setLocation(165, 160);
		skill5.setEditable(false);

		skill6 = new JTextField(skillsix, JTextField.CENTER);
		skill6.setSize(90, 20);
		skill6.setLocation(325, 160);
		skill6.setEditable(false);

		// Delete Buttons

		delete1 = new JButton("X");
		delete1.setSize(50, 20);
		delete1.setLocation(105, 110);
		delete1.addActionListener(this);

		delete2 = new JButton("X");
		delete2.setSize(50, 20);
		delete2.setLocation(265, 110);
		delete2.addActionListener(this);

		delete3 = new JButton("X");
		delete3.setSize(50, 20);
		delete3.setLocation(425, 110);
		delete3.addActionListener(this);

		delete4 = new JButton("X");
		delete4.setSize(50, 20);
		delete4.setLocation(105, 160);
		delete4.addActionListener(this);

		delete5 = new JButton("X");
		delete5.setSize(50, 20);
		delete5.setLocation(265, 160);
		delete5.addActionListener(this);

		delete6 = new JButton("X");
		delete6.setSize(50, 20);
		delete6.setLocation(425, 160);
		delete6.addActionListener(this);

		// add/back/next

		play = new JButton("PLAY!");
		play.setSize(90, 30);
		play.setLocation(500, 25);
		play.addActionListener(this);

		back = new JButton("BACK");
		back.setSize(90, 30);
		back.setLocation(390, 25);
		back.addActionListener(this);
		help = new JButton("Help");
		help.setSize(90, 30);
		help.setLocation(280, 25);
		help.addActionListener(this);

		contentPane.add(help);

		// add skills text fields
		contentPane.add(skill1);
		contentPane.add(skill2);
		contentPane.add(skill3);
		contentPane.add(skill4);
		contentPane.add(skill5);
		contentPane.add(skill6);

		contentPane.add(delete1);
		contentPane.add(delete2);
		contentPane.add(delete3);
		contentPane.add(delete4);
		contentPane.add(delete5);
		contentPane.add(delete6);

		// contentPane.add(addskill);
		contentPane.add(play);
		contentPane.add(back);
		contentPane.add(backg);

		frame.add(tablePanel, BorderLayout.SOUTH);
		frame.add(contentPane, BorderLayout.NORTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void main(String[] args) {

	}

	/**
	 * Converts the target type value to a string
	 * 
	 * @param targets
	 *            The target type; either SINGLE, or TEAM
	 * @return The type converted to a string
	 */
	public String convertTargetValue(Targets targets) {
		String t = "";
		switch (targets) {
		case SINGLE:
			t = "Single";
			break;
		case TEAM:
			t = "TEAM";
			break;
		}
		return t;
	}

	/**
	 * Converts different elemental attribute types to strings
	 * 
	 * @param type
	 *            The elemental attribute type to be converted
	 * @return The type converted to a string
	 */
	public String convertDamageTypeValue(ElementalType type) {
		String t = "";
		switch (type) {
		case AIR:
			t = "Air";
			break;
		case EARTH:
			t = "Earth";
			break;
		case FIRE:
			t = "Fire";
			break;
		case WATER:
			t = "Water";
			break;
		case VOID:
			t = "Void";
			break;
		}
		return t;
	}

	/**
	 * Converts different command options to a string
	 * 
	 * @param count
	 *            The option type ID
	 * @return The command option as a string
	 */
	String convertStatAffectedValue(Integer count) {
		String t = "";
		switch (count) {
		case 0:
			t = "Power";
			break;
		case 1:
			t = "Defense";
			break;
		case 2:
			t = "Speed";
			break;
		case 3:
			t = "Nullify";
			break;
		}
		return t;

	}

	/**
	 * Controls all the buttons, updating the player depending on which buttons
	 * are pressed
	 */
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == play) {
			String one = skill1.getText();
			String two = skill2.getText();
			String three = skill3.getText();
			String four = skill4.getText();
			String five = skill5.getText();
			String six = skill6.getText();

			Skill[] skills = new Skill[6];
			int count = 0;
			String empty = " ";

			if (one.contentEquals(empty) || two.contentEquals(empty)
					|| three.contentEquals(empty) || four.contentEquals(empty)
					|| five.contentEquals(empty) || six.contentEquals(empty)) {

				UIManager UI = new UIManager();
				UI.put("OptionPane.background", new ColorUIResource(180, 200,
						180));
				UI.put("Panel.background", new ColorUIResource(190, 200, 190));

				JOptionPane
						.showMessageDialog(frame, "Choose 6 skills to Play!");
			} else {

				if (!one.contentEquals(empty) && !two.contentEquals(empty)
						&& !three.contentEquals(empty)
						&& !four.contentEquals(empty)
						&& !five.contentEquals(empty)
						&& !six.contentEquals(empty)) {
					System.out.println("nooo");
					
					for (int i = 0; i < skillslist.DamageSkillList.size(); i++) {
				
						if (one.equals(skillslist.DamageSkillList.get(i)
								.getSkillName())) {
							count = 0;
							skills[count] = skillslist.DamageSkillList.get(i);
						}
						if (two.equals(skillslist.DamageSkillList.get(i)
								.getSkillName())) {
							count = 1;
							skills[count] = skillslist.DamageSkillList.get(i);

						}
						if (three.equals(skillslist.DamageSkillList.get(i)
								.getSkillName())) {
							count = 2;
							skills[count] = skillslist.DamageSkillList.get(i);

						}
						if (four.equals(skillslist.DamageSkillList.get(i)
								.getSkillName())) {
							count = 3;
							skills[count] = skillslist.DamageSkillList.get(i);

						}
						if (five.equals(skillslist.DamageSkillList.get(i)
								.getSkillName())) {
							count = 4;
							skills[count] = skillslist.DamageSkillList.get(i);

						}
						if (six.equals(skillslist.DamageSkillList.get(i)
								.getSkillName())) {
							count = 5;
							skills[count] = skillslist.DamageSkillList.get(i);
						}

					}
					for (int i = 0; i < skillslist.HealingSkillList.size(); i++) {
						if (one.equals(skillslist.HealingSkillList.get(i)
								.getSkillName())) {
							count = 0;
							skills[count] = skillslist.HealingSkillList.get(i);
							
						}
						if (two.equals(skillslist.HealingSkillList.get(i)
								.getSkillName())) {
							count = 1;
							skills[count] = skillslist.HealingSkillList.get(i);
						}
						if (three.equals(skillslist.HealingSkillList.get(i)
								.getSkillName())) {
							count = 2;
							skills[count] = skillslist.HealingSkillList.get(i);
						}
						if (four.equals(skillslist.HealingSkillList.get(i)
								.getSkillName())) {
							count = 3;
							skills[count] = skillslist.HealingSkillList.get(i);
						}
						if (five.equals(skillslist.HealingSkillList.get(i)
								.getSkillName())) {
							count = 4;
							skills[count] = skillslist.HealingSkillList.get(i);
							
						}
						if (six.equals(skillslist.HealingSkillList.get(i)
								.getSkillName())) {
							count = 5;
							skills[count] = skillslist.HealingSkillList.get(i);
						}

					}
					for (int i = 0; i < skillslist.BuffingSkillList.size(); i++) {
						if (one.equals(skillslist.BuffingSkillList.get(i)
								.getSkillName())) {
							count = 0;
							skills[count] = skillslist.BuffingSkillList.get(i);
						}
						if (two.equals(skillslist.BuffingSkillList.get(i)
								.getSkillName())) {
							count = 1;
							skills[count] = skillslist.BuffingSkillList.get(i);
						}
						if (three.equals(skillslist.BuffingSkillList.get(i)
								.getSkillName())) {
							count = 2;
							skills[count] = skillslist.BuffingSkillList.get(i);
				
						}
						if (four.equals(skillslist.BuffingSkillList.get(i)
								.getSkillName())) {
							count = 3;
							skills[count] = skillslist.BuffingSkillList.get(i);

						}
						if (five.equals(skillslist.BuffingSkillList.get(i)
								.getSkillName())) {
							count = 4;

							skills[count] = skillslist.BuffingSkillList.get(i);

						}
						if (six.equals(skillslist.BuffingSkillList.get(i)
								.getSkillName())) {
							count = 5;
							skills[count] = skillslist.BuffingSkillList.get(i);
						}
					}
				}
				for (int i = 0; i < 6; i++) {
					System.out.println( i + ((Skill) skills[i]).getSkillName());
				}

				ElementalAttributes ea1 = new ElementalAttributes(v, f, w, e, a);

				Player player = new Player(name, ea1, hp, mp, 100, Bpoints,
						power, defense, speed, skills, this.player.getTeam(), this.player.getPlayerID(), this.player.getX(),
						this.player.getY());

				this.player = player;
				map.newPlayer = this.player;
				map.charCreationCompleted = true;
				frame.setVisible(false);
				frame.dispose();
			}
		}

		if (event.getSource() == addskill) {
			if (table.getSelectedRow() != (-1)) {
				selectedSkill = (String) table.getModel().getValueAt(
						table.getSelectedRow(), 0);
				System.out.println(selectedSkill);
				skillone = skill1.getText();
				skilltwo = skill2.getText();
				skillthree = skill3.getText();
				skillfour = skill4.getText();
				skillfive = skill5.getText();
				skillsix = skill6.getText();
				String empty = " ";

				if (skillone.equals(empty)) {
					skill1.setText(selectedSkill);
					skillone = " ";
				}

				if (skillone != empty && skilltwo.equals(empty)) {
					skill2.setText(selectedSkill);
					skilltwo = " ";
				}
				if (skilltwo != empty && skillone != empty
						&& skillthree.equals(empty)) {
					skill3.setText(selectedSkill);
					skillthree = " ";
				}

				if (skillthree != empty && skilltwo != empty
						&& skillone != empty && skillfour.equals(empty)) {
					skill4.setText(selectedSkill);
					skillfour = " ";
				}
				if (skillfour != empty && skillthree != empty
						&& skilltwo != empty && skillone != empty
						&& skillfive.equals(empty)) {
					skill5.setText(selectedSkill);
					skillfive = " ";
				}
				if (skillfive != empty && skillfour != empty
						&& skillthree != empty && skilltwo != empty
						&& skillone != empty && skillsix.equals(empty)) {
					skill6.setText(selectedSkill);
					skillsix = " ";
				}
			}

		}

		if (event.getSource() == addskill2) {
			if (table2.getSelectedRow() != (-1)) {
				selectedSkill = (String) table2.getModel().getValueAt(
						table2.getSelectedRow(), 0);
				System.out.println(selectedSkill);
				skillone = skill1.getText();
				skilltwo = skill2.getText();
				skillthree = skill3.getText();
				skillfour = skill4.getText();
				skillfive = skill5.getText();
				skillsix = skill6.getText();
				String empty = " ";

				if (skillone.equals(empty)) {
					skill1.setText(selectedSkill);
					skillone = " ";
				}

				if (skillone != empty && skilltwo.equals(empty)) {
					skill2.setText(selectedSkill);
					skilltwo = " ";
				}
				if (skilltwo != empty && skillone != empty
						&& skillthree.equals(empty)) {
					skill3.setText(selectedSkill);
					skillthree = " ";
				}

				if (skillthree != empty && skilltwo != empty
						&& skillone != empty && skillfour.equals(empty)) {
					skill4.setText(selectedSkill);
					skillfour = " ";
				}
				if (skillfour != empty && skillthree != empty
						&& skilltwo != empty && skillone != empty
						&& skillfive.equals(empty)) {
					skill5.setText(selectedSkill);
					skillfive = " ";
				}
				if (skillfive != empty && skillfour != empty
						&& skillthree != empty && skilltwo != empty
						&& skillone != empty && skillsix.equals(empty)) {
					skill6.setText(selectedSkill);
					skillsix = " ";
				}
			}

		}

		if (event.getSource() == addskill3) {
			if (table3.getSelectedRow() != (-1)) {
				selectedSkill = (String) table3.getModel().getValueAt(
						table3.getSelectedRow(), 0);
				System.out.println(selectedSkill);
				skillone = skill1.getText();
				skilltwo = skill2.getText();
				skillthree = skill3.getText();
				skillfour = skill4.getText();
				skillfive = skill5.getText();
				skillsix = skill6.getText();
				String empty = " ";

				if (skillone.equals(empty)) {
					skill1.setText(selectedSkill);
					skillone = " ";
				}

				if (skillone != empty && skilltwo.equals(empty)) {
					skill2.setText(selectedSkill);
					skilltwo = " ";
				}
				if (skilltwo != empty && skillone != empty
						&& skillthree.equals(empty)) {
					skill3.setText(selectedSkill);
					skillthree = " ";
				}

				if (skillthree != empty && skilltwo != empty
						&& skillone != empty && skillfour.equals(empty)) {
					skill4.setText(selectedSkill);
					skillfour = " ";
				}
				if (skillfour != empty && skillthree != empty
						&& skilltwo != empty && skillone != empty
						&& skillfive.equals(empty)) {
					skill5.setText(selectedSkill);
					skillfive = " ";
				}
				if (skillfive != empty && skillfour != empty
						&& skillthree != empty && skilltwo != empty
						&& skillone != empty && skillsix.equals(empty)) {
					skill6.setText(selectedSkill);
					skillsix = " ";
				}
			}

		}

		if (event.getSource() == delete1) {
			skill1.setText(" ");
		}
		if (event.getSource() == delete2) {
			skill2.setText(" ");
		}
		if (event.getSource() == delete3) {
			skill3.setText(" ");
		}
		if (event.getSource() == delete4) {
			skill4.setText(" ");
		}
		if (event.getSource() == delete5) {
			skill5.setText(" ");
		}
		if (event.getSource() == delete6) {
			skill6.setText(" ");
		}
		if (event.getSource() == help) {
			Help help = new Help("selectskillhelp", 2);
			frame.hide();

		}

		if (event.getSource() == back) {
			ElementsCave elements = new ElementsCave(player.getName(), player.getBaseHP(), player.getBaseMP(), player.getBaseDefence(), player.getBasePower(),
					player.getBaseSpeed(), Bpoints, player, map);
			
			elements.frame.setVisible(true);
			frame.setVisible(false);
			frame.dispose();
		}
	}

}
