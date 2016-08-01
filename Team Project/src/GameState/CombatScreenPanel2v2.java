package GameState;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameState.Skill.Targets;
import MovementMap.TheMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;


/**
 *  @author Liam Howe (ldh304),Tim Ryan (txr398) and Zhuo Lee (zll308)
 * 
 * Class to run the screen for combat between two teams
 */
public class CombatScreenPanel2v2 extends JPanel implements ActionListener {
	public boolean inCombat = false;
	public boolean combatFinished = false;
	public SkillsList skillist = new SkillsList();
	int t = 5;
	int aggresor;
	public boolean targetSelected = false;
	public boolean ready = false;
	public Player mainPlayer;

	private JButton attack = new JButton("Attack");
	private JButton defend = new JButton("Defend");
	private JButton run = new JButton("Run");
	private JButton skill0 = new JButton();
	private JButton skill1 = new JButton();
	private JButton skill2 = new JButton();
	private JButton skill3 = new JButton();
	private JButton skill4 = new JButton();
	private JButton skill5 = new JButton();
	private JButton TargetYou = new JButton();
	private JButton TargetEnemy = new JButton();
	private JPanel commandPanel = new JPanel();
	public JTextArea chatArea = new JTextArea();
	private JTextArea combatLogArea = new JTextArea();
	private JScrollPane chatScroll = new JScrollPane(chatArea);
	private JScrollPane combatScroll = new JScrollPane(combatLogArea);
	private JButton chat = new JButton("Click here to send message");
	public LinkedList<String> combatLog = new LinkedList<String>();
	public LinkedList<String> chatLog = new LinkedList<String>();
	

	public String message; 

	private static BufferedImage combatImg2v2;

	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public int commandInt = 10;
	public Command command;
	public ArrayList<Player> combatants = new ArrayList<Player>();
	public ArrayList<Integer> teamList = new ArrayList<Integer>();
	public ArrayList<Command> commandList = new ArrayList<Command>();
	public Stack<Command> commandStack = new Stack<Command>();
	public Stack<Command> commandStackDefense = new Stack<Command>();
	public TheMap map;

	final static int framex = 1000;// 1200
	final static int framey = 600;// 720
	final static int buttonWidth = (((framex / 2) / 3) / 2);
	final static int buttonHeight = ((framey / 3) / 2) - 20;
	final static int skillButtonWidth = (2 * (framex / 2) / 3) / 3;
	final static int skillButtonHeight = ((framey / 3) - 40) / 2;;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Create CombatScreenPanel using 2 players as parameters
	 * 
	 * @param p1
	 *            Player 1
	 * @param p2
	 *            Player 2
	 * @param p3
	 *            Player 3
	 * @param p4
	 *            Player 4
	 * 
	 * @param map The map of the client
	 */
	public CombatScreenPanel2v2(Player p1, Player p2,
			Player p3, Player p4, TheMap map) {
		
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.map = map;
		createPanel();
		
		
	}
	
	public void createPanel(){
		try {
			combatImg2v2 = ImageIO.read(new File("Images/combat2v2.jpg"));
		} catch (IOException e) {
			System.out.println("Cannot get images");
		}
		combatants.clear();
		combatants.add(p1);
		combatants.add(p2);
		combatants.add(p3);
		combatants.add(p4);
		for (int i = 0; i < combatants.size(); i++) {
			Integer playerTeam = (Integer) combatants.get(i).getTeam();
			if (!teamList.contains(playerTeam)) {
				teamList.add(playerTeam);
			}
		}

		this.setSize(framex, framey);
		attack.setToolTipText(skillist.getDSkill().get(0).getFlavourText());
		attack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				t = getTargets(skillist.getDSkill().get(0));
				
				System.out.println(t);
				if (t != 5) {
					System.out.println("1");
					Stack<Integer> teamTargets = new Stack<Integer>();
					if (skillist.getDSkill().get(0).getTarget() == Targets.SINGLE) {
						// for 4 players
						teamTargets.add(t);
					} else {
						teamTargets.add(t);
					}
					
					commandInt = 0;
					// comman = new Command(aggresor, commandInt, targets,
					// speed, skillType, skillID)
					command = new Command(aggresor, commandInt, teamTargets,
							p1.getActualSpeed(), 0, 0);
					command.printString();
					System.out.println("Command sent to server: ");
					t = 5;
					hideSkills();
				}
			}
		});
		attack.setBounds((framex / 4) + (2 * (framex / 2) / 3),
				2 * (framey / 3), buttonWidth, buttonHeight);

		System.out.println(p1.getSkillSet()[0].getSkillName());
		skill0.setText(p1.getSkillSet()[0].getSkillName());
		skill0.setToolTipText(p1.getSkillSet()[0].getFlavourText());
		skill0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get skill from skill set
				// get target
				
				if (p1.getActualMP() >= p1.getSkillSet()[0].getMP()) {
					t = getTargets(p1.getSkillSet()[0]);
					if (t != 5) {
						targetSelected = true;
						Stack<Integer> teamTargets = new Stack<Integer>();
						if (p1.getSkillSet()[0].getTarget() == Targets.SINGLE) {
							// for 4 players
							teamTargets.add(t);
						} else {
							if(t == 0 && mainPlayer.getTeam() == 0){
								teamTargets.add(0);
								teamTargets.add(1);
							}
							else if(t == 0 && mainPlayer.getTeam() == 1){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 0){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 1){
								teamTargets.add(0);
								teamTargets.add(1);
							}
						}
						commandInt = 3;
						// comman = new Command(aggresor, commandInt, targets,
						// speed, skillType, skillID)
						command = new Command(aggresor, commandInt, teamTargets,
								p1.getActualSpeed(), p1.getSkillSet()[0]
										.getSkilltype(), p1.getSkillSet()[0]
										.getSKillID());
						command.printString();
						System.out.println("Command sent to server: ");
						t = 5;
						hideSkills();
					}
				} else{ MPMessage();}
			}
		});
		this.skill0.setBounds(framex / 4, 2 * (framey / 3), skillButtonWidth,
				skillButtonHeight);

		skill1.setText(p1.getSkillSet()[1].getSkillName());
		skill1.setToolTipText(p1.getSkillSet()[1].getFlavourText());
		skill1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[1].getMP()) {
					t = getTargets(p1.getSkillSet()[1]);
					if (t != 5) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						if (p1.getSkillSet()[1].getTarget() == Targets.SINGLE) {
							// for 4 players
							teamTargets.add(t);
						} else {
							if(t == 0 && mainPlayer.getTeam() == 0){
								teamTargets.add(0);
								teamTargets.add(1);
							}
							else if(t == 0 && mainPlayer.getTeam() == 1){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 0){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 1){
								teamTargets.add(0);
								teamTargets.add(1);
							}
						}
						commandInt = 4;
						command = new Command(aggresor, commandInt, teamTargets,
								p1.getActualSpeed(), p1.getSkillSet()[1]
										.getSkilltype(), p1.getSkillSet()[1]
										.getSKillID());
						System.out.println("Command sent to server: ");
						command.printString();
						t = 5;
						hideSkills();
					}
				} else{ MPMessage();}
			}
		});
		skill1.setBounds(framex / 4 + skillButtonWidth, 2 * (framey / 3),
				skillButtonWidth, skillButtonHeight);

		skill2.setText(p1.getSkillSet()[2].getSkillName());
		skill2.setToolTipText(p1.getSkillSet()[2].getFlavourText());
		skill2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[2].getMP()) {
					t = getTargets(p1.getSkillSet()[2]);
					if (t != 5) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						if (p1.getSkillSet()[2].getTarget() == Targets.SINGLE) {
							// for 4 players
							teamTargets.add(t);
						} else {
							if(t == 0 && mainPlayer.getTeam() == 0){
								teamTargets.add(0);
								teamTargets.add(1);
							}
							else if(t == 0 && mainPlayer.getTeam() == 1){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 0){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 1){
								teamTargets.add(0);
								teamTargets.add(1);
							}
						}
						commandInt = 5;
						command = new Command(aggresor, commandInt, teamTargets,
								p1.getActualSpeed(), p1.getSkillSet()[2]
										.getSkilltype(), p1.getSkillSet()[2]
										.getSKillID());
						System.out.println("Command sent to server: ");
						command.printString();
						t = 5;
						hideSkills();
					}
				} else{ MPMessage();}
			}
		});
		skill2.setBounds((framex / 4) + 2 * skillButtonWidth, 2 * (framey / 3),
				skillButtonWidth, skillButtonHeight);

		skill3.setText(p1.getSkillSet()[3].getSkillName());
		skill3.setToolTipText(p1.getSkillSet()[3].getFlavourText());
		skill3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[3].getMP()) {
					t = getTargets(p1.getSkillSet()[3]);
					if (t != 5) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						if (p1.getSkillSet()[3].getTarget() == Targets.SINGLE) {
							// for 4 players
							teamTargets.add(t);
						} else {
							if(t == 0 && mainPlayer.getTeam() == 0){
								teamTargets.add(0);
								teamTargets.add(1);
							}
							else if(t == 0 && mainPlayer.getTeam() == 1){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 0){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 1){
								teamTargets.add(0);
								teamTargets.add(1);
							}
						}
						commandInt = 6;
						command = new Command(aggresor, commandInt, teamTargets,
								p1.getActualSpeed(), p1.getSkillSet()[3]
										.getSkilltype(), p1.getSkillSet()[3]
										.getSKillID());
						System.out.println("Command sent to server: ");
						command.printString();
						t = 5;
						hideSkills();
					}
				} else{ MPMessage();}
			}
		});
		skill3.setBounds((framex / 4), 2 * (framey / 3) + skillButtonHeight,
				skillButtonWidth, skillButtonHeight);

		skill4.setText(p1.getSkillSet()[4].getSkillName());
		skill4.setToolTipText(p1.getSkillSet()[4].getFlavourText());
		skill4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[4].getMP()) {
					t = getTargets(p1.getSkillSet()[4]);
					if (t != 5) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						if (p1.getSkillSet()[4].getTarget() == Targets.SINGLE) {
							// for 4 players
							teamTargets.add(t);
						} else {
							if(t == 0 && mainPlayer.getTeam() == 0){
								teamTargets.add(0);
								teamTargets.add(1);
							}
							else if(t == 0 && mainPlayer.getTeam() == 1){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 0){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 1){
								teamTargets.add(0);
								teamTargets.add(1);
							}
						}
						commandInt = 7;
						command = new Command(aggresor, commandInt, teamTargets,
								p1.getActualSpeed(), p1.getSkillSet()[4]
										.getSkilltype(), p1.getSkillSet()[4]
										.getSKillID());
						System.out.println("Command sent to server: ");
						command.printString();
						t = 5;
						hideSkills();
					}
				} else{ MPMessage();}
			}
		});
		skill4.setBounds((framex / 4) + skillButtonWidth, 2 * (framey / 3)
				+ skillButtonHeight, skillButtonWidth, skillButtonHeight);

		skill5.setText(p1.getSkillSet()[5].getSkillName());
		skill5.setToolTipText(p1.getSkillSet()[5].getFlavourText());
		skill5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[5].getMP()) {
					t = getTargets(p1.getSkillSet()[5]);
					if (t != 5) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						if (p1.getSkillSet()[5].getTarget() == Targets.SINGLE) {
							// for 4 players
							teamTargets.add(t);
						} else {
							if(t == 0 && mainPlayer.getTeam() == 0){
								teamTargets.add(0);
								teamTargets.add(1);
							}
							else if(t == 0 && mainPlayer.getTeam() == 1){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 0){
								teamTargets.add(2);
								teamTargets.add(3);
							}
							else if(t == 1 && mainPlayer.getTeam() == 1){
								teamTargets.add(0);
								teamTargets.add(1);
							}
						}
						commandInt = 8;
						command = new Command(aggresor, commandInt, teamTargets,
								p1.getActualSpeed(), p1.getSkillSet()[5]
										.getSkilltype(), p1.getSkillSet()[5]
												.getSKillID());
						System.out.println("Command sent to server: ");
						command.printString();
						t = 5;
						hideSkills();
					}
				} else{ MPMessage();}
			}
		});
		skill5.setBounds((framex / 4) + 2 * skillButtonWidth, 2 * (framey / 3)
				+ skillButtonHeight, skillButtonWidth, skillButtonHeight);

		JButton defend = new JButton("Defend");
		defend.setToolTipText("Defends against incoming attacks, will always go first");
		defend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Stack<Integer> teamTargets = new Stack<Integer>();
				commandInt = 1;
				command = new Command(aggresor, 1, teamTargets, p1.getActualSpeed(), 0, 0);
				hideSkills();
			}
		});
		defend.setBounds((framex / 4) + (2 * (framex / 2) / 3) + buttonWidth,
				2 * (framey / 3), buttonWidth, buttonHeight);

		JButton run = new JButton("Run");
		run.setToolTipText("Can not run from a 2 vs 2!");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Can not run during 2 vs 2!");
			}
		});
		run.setBounds((framex / 4) + (2 * (framex / 2) / 3) + buttonWidth, 2
				* (framey / 3) + buttonHeight, buttonWidth, buttonHeight);

		chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String message = JOptionPane
						.showInputDialog("Enter message to send: ");
				map.client.sendMessage(message);
			}
		});
		chat.setBounds(0, 8 * (framey / 10), framex / 4, 2 * (framey / 10) - 10);

		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		chatScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatScroll.setPreferredSize(new Dimension(200, 200));

		combatLogArea.setEditable(false);
		combatLogArea.setLineWrap(true);
		combatLogArea.setWrapStyleWord(true);
		combatScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		combatScroll.setPreferredSize(new Dimension(200, 200));

		commandPanel.add(skill0);
		commandPanel.add(skill1);
		commandPanel.add(skill2);
		commandPanel.add(attack);
		commandPanel.add(defend);
		commandPanel.add(skill3);
		commandPanel.add(skill4);
		commandPanel.add(skill5);
		commandPanel.add(run);
		commandPanel.add(chat);

		// initialise buttons, chat and combat area add them to commandPanel
		GridLayout newLayout = new GridLayout(0, 5);
		commandPanel.setLayout(newLayout);
		this.setLayout(new BorderLayout());
		this.add(commandPanel, BorderLayout.PAGE_END);

		this.add(combatScroll, BorderLayout.EAST);
		this.add(chatScroll, BorderLayout.WEST);
		this.setVisible(true);
	}

	/**
	 * Draws the players and combat information on the frame
	 * 
	 * @param Graphics
	 */
	public void paintComponent(Graphics g) {
		System.out.println("Combat Screen 2v2 draw called");
		super.paintComponent(g);
		g.drawImage(combatImg2v2, 0, 0, null);
		System.out.println("Combat Screen being painted");
		Graphics2D g2 = (Graphics2D) g;

		// Health Bars
		g.setColor(Color.RED);
		// P1
		double percentageHealth1 = ((double) p1.getActualHP() / (double) p1
				.getMaxHP()) * 100;
		g.fillRect(framex / 4 + 50, (framey / 5) - 50, (int) percentageHealth1,
				20);
		// P2
		double percentageHealth2 = ((double) p2.getActualHP() / (double) p2
				.getMaxHP()) * 100;
		g.fillRect(framex / 4 + 50, (framey / 5) + 50, (int) percentageHealth2,
				20);
		// P3
		double percentageHealth3 = ((double) p3.getActualHP() / (double) p3
				.getMaxHP()) * 100;
		g.fillRect(((framex / 4) * 3) - 150, framey / 5 - 50,
				(int) percentageHealth3, 20);
		// P4
		double percentageHealth4 = ((double) p4.getActualHP() / (double) p4
				.getMaxHP()) * 100;
		g.fillRect(((framex / 4) * 3) - 150, framey / 5 + 50,
				(int) percentageHealth4, 20);

		// Mana Bars
		g.setColor(Color.BLUE);
		// P1
		double percentageMP1 = ((double) p1.getActualMP() / (double) p1
				.getMaxMP()) * 100;
		g.fillRect(framex / 4 + 50, (framey / 5) + 20 - 50,
				(int) percentageMP1, 20);
		// P2
		double percentageMP2 = ((double) p2.getActualMP() / (double) p2
				.getMaxMP()) * 100;
		g.fillRect(framex / 4 + 50, (framey / 5) + 20 + 50,
				(int) percentageMP2, 20);
		// P3
		double percentageMP3 = ((double) p3.getActualMP() / (double) p3
				.getMaxMP()) * 100;
		g.fillRect((framex / 4) * 3 - 150, (framey / 5) + 20 - 50,
				(int) percentageMP3, 20);
		// P4
		double percentageMP4 = ((double) p4.getActualMP() / (double) p4
				.getMaxMP()) * 100;
		g.fillRect((framex / 4) * 3 - 150, (framey / 5) + 20 + 50,
				(int) percentageMP4, 20);

		// Player Names, health and mana values
		g.setColor(Color.BLACK);
		// P1
		g2.drawString(p1.getName(), framex / 4 + 150, (framey / 5) - 50);
		g2.drawString("" + (int) p1.getActualHP(), 270, (framey / 5) + 15 - 50);
		g2.drawString("" + (int) p1.getActualMP(), 270, (framey / 5) + 35 - 50);
		// P2
		g2.drawString(p2.getName(), framex / 4 + 150, (framey / 5) + 50);
		g2.drawString("" + (int) p2.getActualHP(), 270, (framey / 5) + 15 + 50);
		g2.drawString("" + (int) p2.getActualMP(), 270, (framey / 5) + 35 + 50);
		// P3
		g2.drawString(p3.getName(), (framex / 4) * 3 - 250, framey / 5 - 50);
		g2.drawString("" + (int) p3.getActualHP(), 740, (framey / 5) + 15 - 50);
		g2.drawString("" + (int) p3.getActualMP(), 740, (framey / 5) + 35 - 50);
		// P4
		g2.drawString(p4.getName(), (framex / 4) * 3 - 250, framey / 5 + 50);
		g2.drawString("" + (int) p4.getActualHP(), 740, (framey / 5) + 15 + 50);
		g2.drawString("" + (int) p4.getActualMP(), 740, (framey / 5) + 35 + 50);
		
		combatLogArea.setText("");;
		for(String move : combatLog){
			combatLogArea.append(move);
		}
		chatArea.setText("");
		for(String message: chatLog){
			chatArea.append(message);
		}
		
	}
	
	/**
	 * updates the skill buttons after the skills have been reselected in the cave
	 * @param player The new player models whose skills will be used to update the panel
	 */
	public void updateSkillButtons(Player player){
		Skill[] newSkillSet = player.getSkillSet();
		skill0.setText(newSkillSet[0].getSkillName());
		skill1.setText(newSkillSet[1].getSkillName());
		skill2.setText(newSkillSet[2].getSkillName());
		skill3.setText(newSkillSet[3].getSkillName());
		skill4.setText(newSkillSet[4].getSkillName());
		skill5.setText(newSkillSet[5].getSkillName());
	}


	/**
	 * Asks the user for the target(s) of their skill
	 * 
	 * @param skill
	 *            The skill to get target(s) for
	 * @return The users selected target(s) of the skill
	 */
	public int getTargets(Skill skill) {
		JFrame frame = new JFrame();
		String s;
		int t = 5;
		if (skill.getTarget() == Targets.SINGLE) {
			Object[] possibilities = { p1.getName(), p2.getName(),
					p3.getName(), p4.getName() };
			s = (String) JOptionPane.showInputDialog(frame, "Select Target:\n",

			"Target Selection", JOptionPane.PLAIN_MESSAGE, null, possibilities,
					p1.getName());

		} else {
			Object[] possibilities = { "Your Team", "Other Team" };
			s = (String) JOptionPane.showInputDialog(frame, "Select Target:\n",

			"Target Selection", JOptionPane.PLAIN_MESSAGE, null, possibilities,
					"Your Team");
		}
		System.out.println(s);
		try{
			if (s.equals(null)) {
				targetSelected = false;
			}
			else if (s.equals(p1.getName())){
				t = p1.getPlayerID();
				System.out.println("s = " + s + " Name: " + p1.getName() + " Equals: " + s.equals(p1.getName()) + " == " + s==p1.getName());
				System.out.println("Target chosen p1 ID: " + p1.getPlayerID());}
			else if (s.equals(p2.getName())){
				t = p2.getPlayerID();
				System.out.println("s = " + s + " Name: " + p2.getName() + " Equals: " + s.equals(p2.getName()) + " == " + s==p2.getName());
				System.out.println("Target chosen p2 ID: " + p2.getPlayerID());}
			else if (s.equals(p3.getName())){
				t = p3.getPlayerID();
				System.out.println("s = " + s + " Name: " + p3.getName() + " Equals: " + s.equals(p3.getName()) + " == " + s==p3.getName());
				System.out.println("Target chosen p3 ID: " + p3.getPlayerID());}
			else if (s.equals(p4.getName())){
				t = p4.getPlayerID();
				System.out.println("s = " + s + " Name: " + p4.getName() + " Equals: " + s.equals(p4.getName()) + " == " + s==p4.getName());
				System.out.println("Target chosen p4 ID: " + p4.getPlayerID());}
			else if ( s.equals("Your Team")){
				
				t = 0;
			}
			else if ( s.equals("Other Team")){
				t = 1;
			}
		}
		catch(NullPointerException e)
		{
			
		}
		if(mainPlayer.getName().equals(p1.getName())){
			aggresor = p1.getPlayerID();
		}
		else if(mainPlayer.getName().equals(p2.getName())){
			aggresor = p2.getPlayerID();
		}
		else if(mainPlayer.getName().equals(p3.getName())){
			aggresor = p3.getPlayerID();
		}
		else if(mainPlayer.getName().equals(p4.getName())){
			aggresor = p4.getPlayerID();
		}
		System.out.println(t);
		return t;

	}

	
	/**
	 * helper function to hide the buttons after user has sent a command
	 */
	public void hideSkills(){
		
		attack.setVisible(false);
		defend.setVisible(false);
		skill0.setVisible(false);
		skill1.setVisible(false);
		skill2.setVisible(false);
		skill3.setVisible(false);
		skill4.setVisible(false);
		skill5.setVisible(false);
		run.setEnabled(false);

	}
	/**
	 * helper function to show the buttons after user has sent a command
	 */
	public void showSkills(){
		attack.setVisible(true);
		defend.setVisible(true);
		skill0.setVisible(true);
		skill1.setVisible(true);
		skill2.setVisible(true);
		skill3.setVisible(true);
		skill4.setVisible(true);
		skill5.setVisible(true);
		run.setEnabled(true);
	}
	/**
	 * @returns message returns the message to send
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param  message sets the message to be sent
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Error message if you do not have enough MP to cast a skill
	 */
	public void MPMessage() {
		JOptionPane.showMessageDialog(commandPanel, "Not enough mp");
	}
}
