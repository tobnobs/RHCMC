package GameState;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameState.Skill.Targets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * @author Zhuo Lee (zll308)(actual combat engine) adapted from Liam Howe CombatScreenThread (ldh304)  
 * 
 *         Class to run the screen for combat between a player and an AI
 *
 */
public class AICombatScreenPanel extends JPanel implements ActionListener {
	public boolean inCombat = false;
	public boolean combatFinished = false;
	public SkillsList skillist = new SkillsList();
	String s = "no";
	boolean targetSelected = false;

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
	private JTextArea chatArea = new JTextArea();
	private JTextArea combatLogArea = new JTextArea();
	private JScrollPane chatScroll = new JScrollPane(chatArea);
	private JScrollPane combatScroll = new JScrollPane(combatLogArea);
	private JButton chat = new JButton("Click here to send message");

	public int commandInt = 10;
	public Command command;
	public ArrayList<Player> combatants = new ArrayList<Player>();
	public ArrayList<Integer> teamList = new ArrayList<Integer>();
	public ArrayList<Command> commandList = new ArrayList<Command>();
	public Stack<Command> commandStack = new Stack<Command>();
	public Stack<Command> commandStackDefense = new Stack<Command>();

	private static BufferedImage combatImg;
	private static BufferedImage combatImgBoss;
	private static BufferedImage combatImgWolf;
	private static BufferedImage combatImgGhost;
	private static BufferedImage combatImgMav;

	public int framex = 1000;// 1200
	public int framey = 600;// 720
	public int buttonWidth = (((framex / 2) / 3) / 2);
	public int buttonHeight = ((framey / 3) / 2) - 20;
	public int skillButtonWidth = (2 * (framex / 2) / 3) / 3;
	public int skillButtonHeight = ((framey / 3) - 40) / 2;;

	public Player p1;
	public Player p2;
	public AIPlayer ai = new AIPlayer();

	public int difficulty = 0;

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	/**
	 * Create CombatScreenPanel using 1 player as a parameter
	 * 
	 * @param p1
	 *            Player 1
	 *
	 */
	public AICombatScreenPanel(Player p1) {
		this.p1 = p1;
		changeAI(0);
		createPanel();
	}

	/**
	 * Clears combat log of text
	 */
	public void clearCombatLog() {
		this.combatLogArea.setText("");
	}

	/**
	 * Method to access combatLogArea and fill it with text
	 * @param move the string representing information of attack
	 */
	public void fillCombatLog(String move) {
		this.combatLogArea.append(move);
	}

	/**
	 * Method to change current ai depending on difficulty of area
	 * @param difficulty of area palyer is currently in
	 */
	public void changeAI(int difficulty) {
		
		this.difficulty = difficulty;
		this.p2 = ai.newAI(difficulty);
		Random rand = new Random();
		while (p2.getTeam() == p1.getTeam()) {
			int newTeam = rand.nextInt(10);
			p2.setTeam(newTeam);
		}
	}

	/**
	 * Creates and populates the combat screen
	 */
	public void createPanel() {
		try {
			combatImg = ImageIO.read(new File("Images/combat.jpg"));
			combatImgBoss = ImageIO.read(new File("Images/combatBoss.jpg"));
			combatImgWolf = ImageIO.read(new File("Images/combatLavaWolf.jpg"));
			combatImgGhost = ImageIO.read(new File("Images/combatGhost.jpg"));
			combatImgMav = ImageIO.read(new File("Images/combatMav.jpg"));
		} catch (IOException e) {
			System.out.println("Cannot get images");
		}

		combatants.clear();
		combatants.add(p1);
		combatants.add(p2);
		System.out.println("ggggggggggggggggG");
		System.out.println(combatants.get(0));
		System.out.println(combatants.get(1));
		for (int i = 0; i < combatants.size(); i++) {
			System.out.println("Player" + (i + 1) + " "
					+ (Integer) combatants.get(i).getTeam());
			Integer playerTeam = (Integer) combatants.get(i).getTeam();
			if (!teamList.contains(playerTeam)) {
				teamList.add(playerTeam);
			}
		}

		this.setSize(framex, framey);
		attack.setToolTipText(skillist.getDSkill().get(0).getFlavourText());
		attack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				s = getTargets(skillist.getDSkill().get(0));
				System.out.println(s);
				try{
					if (!s.equals(null)) {
						System.out.println("1");
						Stack<Integer> teamTargets = new Stack<Integer>();
						int t = 0;
						if (s == p1.getName())
							t = p1.getPlayerID();
						else if (s == p2.getName())
							t = p2.getPlayerID();
						teamTargets.add(t);

						commandInt = 0;
						// comman = new Command(aggresor, commandInt, targets,
						// speed, skillType, skillID)
						command = new Command(0, commandInt, teamTargets, p1
								.getActualSpeed(), 0, 0);
						s = "no";
					}
				}
				catch (NullPointerException e2)
				{
					
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
					s = getTargets(p1.getSkillSet()[0]);
					try{
					if (!s.equals(null)) {
						targetSelected = true;
						Stack<Integer> teamTargets = new Stack<Integer>();
						int t = 0;
						if (s == p1.getName())
							t = p1.getPlayerID();
						else if (s == p2.getName())
							t = p2.getPlayerID();
						teamTargets.add(t);
						commandInt = 3;
						// comman = new Command(aggresor, commandInt, targets,
						// speed, skillType, skillID)
						command = new Command(0, commandInt, teamTargets, p1
								.getActualSpeed(), p1.getSkillSet()[0]
								.getSkilltype(), p1.getSkillSet()[0]
								.getSKillID());
						s = "no";
						}
					}
					catch (NullPointerException e3)
					{
							
					}
					
				}
				else 
				{
					MPMessage();
				}
			}
		});
		this.skill0.setBounds(framex / 4, 2 * (framey / 3), skillButtonWidth,
				skillButtonHeight);

		skill1.setText(p1.getSkillSet()[1].getSkillName());
		skill1.setToolTipText(p1.getSkillSet()[1].getFlavourText());
		skill1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[1].getMP()) {
					s = getTargets(p1.getSkillSet()[1]);
					try {
					if (!s.equals(null)) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						int t = 0;
						if (s == p1.getName())
							t = p1.getPlayerID();
						else if (s == p2.getName())
							t = p2.getPlayerID();
						teamTargets.add(t);
						commandInt = 4;
						command = new Command(0, commandInt, teamTargets, p1
								.getActualSpeed(), p1.getSkillSet()[1]
								.getSkilltype(), p1.getSkillSet()[1]
								.getSKillID());
						s = "no";
					}
					}
					catch (NullPointerException e4)
					{
						
					}
				} else {
					MPMessage();
				}
			}
		});
		skill1.setBounds(framex / 4 + skillButtonWidth, 2 * (framey / 3),
				skillButtonWidth, skillButtonHeight);

		skill2.setText(p1.getSkillSet()[2].getSkillName());
		skill2.setToolTipText(p1.getSkillSet()[2].getFlavourText());
		skill2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[2].getMP()) {
					s = getTargets(p1.getSkillSet()[2]);
					try{
					if (!s.equals(null)) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						int t = 0;
						if (s == p1.getName())
							t = p1.getPlayerID();
						else if (s == p2.getName())
							t = p2.getPlayerID();
						teamTargets.add(t);
						commandInt = 5;
						command = new Command(0, commandInt, teamTargets, p1
								.getActualSpeed(), p1.getSkillSet()[2]
								.getSkilltype(), p1.getSkillSet()[2]
								.getSKillID());
						s = "no";
					}
					}
					catch(NullPointerException e5)
					{	
					}
				} else {
					MPMessage();
				}
			}
		});
		skill2.setBounds((framex / 4) + 2 * skillButtonWidth, 2 * (framey / 3),
				skillButtonWidth, skillButtonHeight);

		skill3.setText(p1.getSkillSet()[3].getSkillName());
		skill3.setToolTipText(p1.getSkillSet()[3].getFlavourText());
		skill3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[3].getMP()) {
					s = getTargets(p1.getSkillSet()[3]);
					try{
					if (!s.equals(null)) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						int t = 0;
						if (s == p1.getName())
							t = p1.getPlayerID();
						else if (s == p2.getName())
							t = p2.getPlayerID();
						teamTargets.add(t);
						commandInt = 6;
						command = new Command(0, commandInt, teamTargets, p1
								.getActualSpeed(), p1.getSkillSet()[3]
								.getSkilltype(), p1.getSkillSet()[3]
								.getSKillID());
						s = "no";
					}
					}
					catch(NullPointerException e6)
					{
						
					}
				} else {
					MPMessage();
				}
			}
		});
		skill3.setBounds((framex / 4), 2 * (framey / 3) + skillButtonHeight,
				skillButtonWidth, skillButtonHeight);

		skill4.setText(p1.getSkillSet()[4].getSkillName());
		skill4.setToolTipText(p1.getSkillSet()[4].getFlavourText());
		skill4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[4].getMP()) {
					s = getTargets(p1.getSkillSet()[4]);
					try{
					if (!s.equals(null)) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						int t = 0;
						if (s == p1.getName())
							t = p1.getPlayerID();
						else if (s == p2.getName())
							t = p2.getPlayerID();
						teamTargets.add(t);
						commandInt = 7;
						command = new Command(0, commandInt, teamTargets, p1
								.getActualSpeed(), p1.getSkillSet()[4]
								.getSkilltype(), p1.getSkillSet()[4]
								.getSKillID());
						s = "no";
					}
					}catch(NullPointerException e6)
					{
					}
				} else {
					MPMessage();
				}
			}
		});
		skill4.setBounds((framex / 4) + skillButtonWidth, 2 * (framey / 3)
				+ skillButtonHeight, skillButtonWidth, skillButtonHeight);

		skill5.setText(p1.getSkillSet()[5].getSkillName());
		skill5.setToolTipText(p1.getSkillSet()[5].getFlavourText());
		skill5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p1.getActualMP() >= p1.getSkillSet()[5].getMP()) {
					s = getTargets(p1.getSkillSet()[5]);
					try{
					if (!s.equals(null)) {
						Stack<Integer> teamTargets = new Stack<Integer>();
						int t = 0;
						if (s == p1.getName())
							t = p1.getPlayerID();
						else if (s == p2.getName())
							t = p2.getPlayerID();
						teamTargets.add(t);
						commandInt = 8;
						command = new Command(0, commandInt, teamTargets, p1
								.getActualSpeed(), p1.getSkillSet()[5]
								.getSkilltype(), p1.getSkillSet()[5]
								.getSKillID());
						s = "no";
					}
					}
					catch(NullPointerException e6)
					{
						
					}
				} else {
					MPMessage();
				}
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
				command = new Command(0, 1, teamTargets, p1.getActualSpeed(),
						0, 0);

			}
		});
		defend.setBounds((framex / 4) + (2 * (framex / 2) / 3) + buttonWidth,
				2 * (framey / 3), buttonWidth, buttonHeight);

		JButton run = new JButton("Run");
		run.setToolTipText("Run away from combat");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Run pressed!");
				Stack<Integer> teamTargets = new Stack<Integer>();
				commandInt = 2;
				command = new Command(0, 2, teamTargets, p1.getActualSpeed(),
						0, 0);

			}
		});
		run.setBounds((framex / 4) + (2 * (framex / 2) / 3) + buttonWidth, 2
				* (framey / 3) + buttonHeight, buttonWidth, buttonHeight);

		chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = JOptionPane
						.showInputDialog("Enter message to send: ");
				chatArea.append(message + " \n");
			}
		});
		chat.setBounds(0, 8 * (framey / 10), framex / 4, 2 * (framey / 10) - 10);

		//chat log
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

		//buttons for command panel
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
		// int borderSize = 3;
		super.paintComponent(g);

		System.out.println("Combat Screen being painted difficulty is: "+p2.getPlayerID());

		Graphics2D g2 = (Graphics2D) g;
		if (difficulty == 4) {
			g.drawImage(combatImgBoss, 0, 0, null);
		} else if (difficulty == 3) {
			g.drawImage(combatImgMav, 0, 0, null);
		} else if (difficulty == 2) {
			g.drawImage(combatImgGhost, 0, 0, null);
		} else if (difficulty == 1) {
			g.drawImage(combatImgWolf, 0, 0, null);
		} else {
			g.drawImage(combatImg, 0, 0, null);
		}

		// --- Player 1 ------------------------

		// calculate values
		double percentageHealth1 = ((double) p1.getActualHP() / (double) p1
				.getMaxHP()) * 100;
		double percentageMP1 = ((double) p1.getActualMP() / (double) p1
				.getMaxMP()) * 100;

		// Draw health bar
		g.setColor(Color.RED);
		g.fillRect(299, 129, (int) percentageHealth1, 20);
		// Draw mana bar
		g.setColor(Color.BLUE);
		g.fillRect(299, 149, (int) percentageMP1, 20);

		g.setColor(Color.BLACK);
		// health int
		g2.drawString(((Integer) (int) p1.getActualHP()).toString(), 273, 143);
		// mana int
		g2.drawString(((Integer) (int) p1.getActualMP()).toString(), 273, 163);

		// --- Player 2 ------------------------
		// calculate values
		double percentageHealth3 = ((double) p2.getActualHP() / (double) p2
				.getMaxHP()) * 100;
		double percentageMP3 = ((double) p2.getActualMP() / (double) p2
				.getMaxMP()) * 100;

		// health bar
		g.setColor(Color.RED);
		g.fillRect(599, 129, (int) percentageHealth3, 20);
		// mana bar
		g.setColor(Color.BLUE);
		g.fillRect(599, 149, (int) percentageMP3, 20);

		g.setColor(Color.BLACK);
		// health value
		g2.drawString(((Integer) (int) p2.getActualHP()).toString(), 736, 143);
		// mana value
		g2.drawString(((Integer) (int) p2.getActualMP()).toString(), 736, 163);

		if (difficulty == 4) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}
		g2.drawString(p1.getName(), framex / 4 + 155, (framey / 5));
		g2.drawString(p2.getName(), (framex / 4) * 3 - 235, framey / 5);

	}
	
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
	 * Asks the player for the target(s) of the skill
	 * 
	 * @param skill
	 *            The skill to get target(s) for
	 * @return The target(s) of the skill
	 */
	public String getTargets(Skill skill) {
		JFrame frame = new JFrame();
		Object[] possibilities = { p1.getName(), p2.getName() };
		String s;
		if (skill.getTarget() == Targets.SINGLE) {
			s = (String) JOptionPane.showInputDialog(frame, "Select Target:\n",

			"Target Selection", JOptionPane.PLAIN_MESSAGE, null, possibilities,
					p1.getName());

		} else {
			s = (String) JOptionPane.showInputDialog(frame, "Select Target:\n",

			"Target Selection", JOptionPane.PLAIN_MESSAGE, null, possibilities,
					p1.getName());
		}
		System.out.println(s);
		try{
			if (s.equals(null)) 
			{
				targetSelected = false;
			}
			}
			catch(NullPointerException e)
			{
				
			}
		return s;

	}

	/**
	 * Displays a message stating "Not enough mp"
	 */
	public void MPMessage() {
		JOptionPane.showMessageDialog(commandPanel, "Not enough mp");
	}
}
