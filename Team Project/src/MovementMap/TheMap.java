package MovementMap;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import outworld.WorldServer;
import GUIcave.NameStatCave;
import GameState.AICombatScreenPanel;
import GameState.AICombatScreenThread;
import GameState.CombatClientThread;
import GameState.CombatScreenPanel2v2;
import GameState.Command;
import GameState.ElementalAttributes;
import GameState.Player;
import GameState.Skill;
import GameState.SkillsList;



/**
 * 
 * @author Toby Sullivan (txs318), Liam Howe (ldh304), Tim Ryan, Zhuo Lee
 * 
 *         Class displaying and the map and controlling the movement of the
 *         player using the arrow keys. Also starts combat with other players or
 *         AI opponents.
 */
public class TheMap extends JPanel implements ActionListener, KeyListener {

	Timer tm = new Timer(10, this);
	// People in the map
	public static Player main;
	public ClientServer client;
	int speedx; // X axis speed
	int speedy; // Y axis speed
	final static int playerSpeed = 4;
	public static ArrayList<Player> others = new ArrayList<Player>(); // Other
																		// players
	public Player newPlayer = null;
	final static int diameter = 25; // Diameter of players
	private Random rand;
	final static int AIprob = 100;
	// 1/AIprob = probability of encountering an AI opponent
	int prob = 0;
	public int mainPos = 0;
	public boolean playersUpdated = false;
	public boolean gameOver = false;

	// Frame constants
	final static int framex = 1000; // Size of frame (x)
	final static int framey = 510; // Size of frame (y)
	private static BufferedImage outworld;
	private static BufferedImage cave;
	private static BufferedImage characterleft;
	private static BufferedImage characterright;

	// Road dimensions
	final static int roadX = 0;
	final static int roadY = 220;
	final static int roadLength = 820;
	final static int roadHeight = 40;

	public JFrame frame = new JFrame();
	public Label ready2 = new Label("Ready");
	public Label notReady = new Label("Not Ready");
	// static Player conga;
	// static TheMap test = new TheMap(conga);
	public static Random random = new Random();
	public static ElementalAttributes ea1 = new ElementalAttributes(1, 1, 1, 1,
			1);
	public static ElementalAttributes ea2 = new ElementalAttributes(2, 2, 2, 2,
			2);
	public static SkillsList skillList = new SkillsList();
	public static Skill[] player1skillSet = { skillList.getDSkill().get(15),
			skillList.getDSkill().get(16), skillList.getDSkill().get(20),
			skillList.getBSkill().get(6), skillList.getBSkill().get(3),
			skillList.getHSkill().get(0) };
	public static Skill[] player2skillSet = { skillList.getDSkill().get(15),
			skillList.getDSkill().get(16), skillList.getDSkill().get(20),
			skillList.getBSkill().get(6), skillList.getBSkill().get(3),
			skillList.getHSkill().get(0) };
	public final String address;

	public CombatScreenPanel2v2 csp2;
	public AICombatScreenPanel aicsp;
	public int AIdifficulty = 0;

	public Command newCommand;
	private boolean readyAdded = false;
	private boolean readyChanged = false;
	private boolean combatReady = false;
	public boolean inCharCreation = false;
	public boolean charCreationCompleted = false;
	private JButton exit;
	public JButton ready = new JButton("Ready");
	/**
	 * Creates the test, starts the timer, sets coordinates for the AI opponents
	 * 
	 * @param main
	 *            The main character playable by the user.
	 * @param address
	 *            The address of the server to connect to.
	 */
	public TheMap(Player main, String address) {

		this.main = main;
		aicsp = new AICombatScreenPanel(main);
		csp2 = new CombatScreenPanel2v2(main, main, main, main, this);
		this.address = address;
		connect();
		client.sendPlayer();
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		rand = new Random();
		//this.main.setPlayerID(WorldServer.playerArray.size()-1);
		
		try {
			outworld = ImageIO.read(new File("Images/outworld.jpg"));
			cave = ImageIO.read(new File("Images/cave.jpg"));
			characterleft = ImageIO.read(new File("Images/characterleft.png"));
			characterright = ImageIO
					.read(new File("Images/characterright.png"));
		} catch (IOException e) {
			System.out.println("Cannot get images");
		}

		InputMap im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = this.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),
				"RightArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "LeftArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "UpArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "DownArrow");

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true),
				"RightArrowR");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "LeftArrowR");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "UpArrowR");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "DownArrowR");

		am.put("RightArrow", this.new ArrowAction("RightArrow"));
		am.put("LeftArrow", this.new ArrowAction("LeftArrow"));
		am.put("UpArrow", this.new ArrowAction("UpArrow"));
		am.put("DownArrow", this.new ArrowAction("DownArrow"));
		am.put("RightArrowR", this.new ArrowAction("RightArrowR"));
		am.put("LeftArrowR", this.new ArrowAction("LeftArrowR"));
		am.put("UpArrowR", this.new ArrowAction("UpArrowR"));
		am.put("DownArrowR", this.new ArrowAction("DownArrowR"));
		
		frame.setSize(framex, framey);
		frame.setTitle("Red Hot Conga And The Magic Cave");
		frame.add(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		//	    frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	 * Paints the map and players on the frame.
	 */
	protected void paintComponent(Graphics g) {
		// Setting the start position of the player when they change map.
		super.paintComponent(g);
		if (main.switchMap() && main.isInCave()) {
			main.setX((framex - diameter) / 2);
			main.setY(framey - 100);
			main.changeMap(false);
		} else if (main.switchMap() && !main.isInCave()) {
			main.setX(diameter * 4);
			main.setY((framey - diameter) / 2);
			main.changeMap(false);
		}
		if (main.isInCave()) {
			// Cave Map
			super.paintComponent(g);
			g.drawImage(cave, 0, 0, null);

			// Character
			if (main.getFacingLeft()) {
				g.drawImage(characterleft, main.getX(), main.getY(), null);
			} else {
				g.drawImage(characterright, main.getX(), main.getY(), null);
			}
		} else {
			// Out-world Map
			super.paintComponent(g);
			g.drawImage(outworld, 0, 0, null);

			// Character
			g.setColor(Color.BLUE);
			for (int i = 0; i < others.size(); i++) {
				if (!others.get(i).isInCave()) {
					if (others.get(i).getFacingLeft()) {
						g.drawImage(characterleft, others.get(i).getX(), others
								.get(i).getY(), null);
					} else {
						g.drawImage(characterright, others.get(i).getX(),
								others.get(i).getY(), null);
					}
				}
			}
		}
		if (!readyAdded) {
			
			ready.setToolTipText("Click to toggle status for 2v2 combat");
			ready.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (csp2.ready)
					{
						csp2.ready = false;
						readyChanged = true;

					} 
					else 
					{
						csp2.ready = true;
						readyChanged = true;
					}

					client.send("5");
				}
			});
			ready.setBounds(850, 0, 300, 150);
			this.add(ready);
			readyAdded = true;
		}
		
		if (csp2.ready && readyChanged) {
			this.remove(notReady);
			ready2.setForeground(Color.GREEN);
			this.add(ready2);
			readyChanged = false;
		}
		if (!csp2.ready && readyChanged) {
			this.remove(ready2);
			notReady.setForeground(Color.RED);
			this.add(notReady);
			readyChanged = false;
		}
	}

	/**
	 * Connects to the client server
	 */
	public void connect() {

		try {

			final int port = 4444;
			Socket socket = new Socket(address, port);

			System.out.println("Connected to " + address);
			client = new ClientServer(socket, this);

			Thread t = new Thread(client);
			t.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Prints the main players coordinates and their amount of remaining bonus
	 * points
	 */
	public void printmapstats() {
		System.out
				.println("X is: " + main.getX() + "     Y is: " + main.getY());
		System.out.println("bonus is " + main.getBonusPointsLeft());
	}

	/**
	 * Controls the arrow buttons
	 *
	 */
	public class ArrowAction extends AbstractAction {

		private String cmd;

		/**
		 * The input key press
		 * @param cmd
		 */
		public ArrowAction(String cmd) {
			this.cmd = cmd;
		}

		/**
		 * Controls what action to take in response to different arrow keys
		 */
		public void actionPerformed(ActionEvent e) {
			int mainsx = main.getX();
			int mainsy = main.getY();

			if (cmd.equalsIgnoreCase("LeftArrow")) {
				if (main.getFacingLeft()) {
					speedx = -playerSpeed;
					if (mainsx > 200
							&& !(mainsx < roadLength
									&& mainsy < roadY + roadHeight && mainsy > roadY)) {
						prob = rand.nextInt(AIprob);
					}
					main.setFacingLeft(true);
					client.send("3");
				} else {
					client.send("l");
					main.setFacingLeft(true);
				}
			} else if (cmd.equalsIgnoreCase("RightArrow")) {
				if (!main.getFacingLeft()) {
					speedx = playerSpeed;
					if (mainsx > 200
							&& !(mainsx < roadLength
									&& mainsy < roadY + roadHeight && mainsy > roadY)) {
						prob = rand.nextInt(AIprob);
					}
					main.setFacingLeft(false);
					client.send("4");
				} else {
					client.send("r");
					main.setFacingLeft(false);
				}
			} else if (cmd.equalsIgnoreCase("UpArrow")) {
				speedy = -playerSpeed;
				if (mainsx > 200
						&& !(mainsx < roadLength && mainsy < roadY + roadHeight && mainsy > roadY)) {
					prob = rand.nextInt(AIprob);
				}
				client.send("2");
			} else if (cmd.equalsIgnoreCase("DownArrow")) {
				speedy = playerSpeed;
				if (mainsx > 200
						&& !(mainsx < roadLength && mainsy < roadY + roadHeight && mainsy > roadY)) {
					prob = rand.nextInt(AIprob);
				}
				client.send("1");
			} else if (cmd.equalsIgnoreCase("LeftArrowR")) {
				speedx = 0;
				speedy = 0;
				main.setFacingLeft(true);
			} else if (cmd.equalsIgnoreCase("RightArrowR")) {
				speedx = 0;
				speedy = 0;
				main.setFacingLeft(false);
			} else if (cmd.equalsIgnoreCase("UpArrowR")) {
				speedx = 0;
				speedy = 0;
			} else if (cmd.equalsIgnoreCase("DownArrowR")) {
				speedx = 0;
				speedy = 0;
			}
			printmapstats();
			main.setX(main.getX() + speedx);
			main.setY(main.getY() + speedy);
		}
	}

	/**
	 * Moves the player on the map and checks their location for collisions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (main.getLocation() == 6) {
			main.setInCave(true);
			main.changeMap(true);
			client.send("8");
		}

		// // --------- CAVE ----------------------------------
		if (main.isInCave()) {
			// Left boundary
			if (main.getX() < 200) {
				speedx = 0;
				main.setX(200);
			}
			// Corridor left boundary
			if (main.getX() < framex / 2 - diameter
					&& main.getY() > framey - 95 - diameter) {
				speedx = 0;
				main.setX(framex / 2 - diameter);
			}
			// Corridor right boundary
			if (main.getX() > framex / 2
					&& main.getY() > framey - 95 - diameter) {
				speedx = 0;
				main.setX(framex / 2);
			}
			// Right boundary
			if (main.getX() > framex - 200 - diameter) {
				speedx = 0;
				main.setX(framex - 200 - diameter);
			}
			// Top boundary
			if (main.getY() < 58) {
				speedy = 0;
				main.setY(58);
			}
			// Bottom boundaries
			if (main.getY() > framey - 105 - diameter
					&& (main.getX() < framex / 2 - diameter || main.getX() > framex
							/ 2 + diameter)) {
				speedy = 0;
				main.setY(framey - 105 - diameter);
			}
			// Menu areas
			if (main.getX() < 320) {
				System.out.println("Set up fights with other players");
			}
			if (main.getX() > framex - 320 - diameter) {
				if(!inCharCreation){
					NameStatCave nsc = new NameStatCave(main, this);
					inCharCreation = true;
				}
				if(charCreationCompleted){
					main.setName(newPlayer.getName());
					main.setBaseHP(newPlayer.getBaseHP());
					main.setBaseMP(newPlayer.getBaseMP());
					main.setBaseDefence(newPlayer.getBaseDefence());
					main.setBasePower(newPlayer.getBasePower());
					main.setBaseSpeed(newPlayer.getBaseSpeed());
					main.setBonusPointsLeft(newPlayer.getBonusPointsLeft());
					main.setEa(newPlayer.getEA());
					main.setSkillSet(newPlayer.getSkillSet());
					main.setTotalBonusPoints(newPlayer.getTotalBonusPoints());
					charCreationCompleted = false;
					client.sendPlayerBYTES(main);
					csp2.updateSkillButtons(main);
					csp2.p1 = main;
					aicsp.updateSkillButtons(main);
					aicsp.p1 = main;
				}
				
			}
			if(!(main.getX() > framex - 320 - diameter)) inCharCreation = false;
			// Leaving the cave.
			if (main.getY() > framey - diameter * 2
					&& main.getX() >= framex / 2 - diameter
					&& main.getX() <= framex / 2 + diameter) {
				main.setInCave(false);
				main.changeMap(true);
				client.send("9");
			}

			repaint();
		}
		//Constant check to start 2v2 combat
		combat2v2();
		aiBattle();
	
		repaint();
		revalidate();

	}
	
	/**
	 * Checks for ai fight
	 */
	public void aiBattle()
	{
		if (main.getX() > 200
				&& !(main.getX() < roadLength - diameter && main.getY() > roadY && main
						.getY() < roadY + roadHeight - diameter)) {

			// get coordinates for areas to set difficulty for new ai
			if (prob == 1 && !aicsp.inCombat && !main.isInCave()) {
				// zone 1 x= 0 - 230
				// zone 2 x = 231- 470
				// zone 3 x = 471 - 720
				// zone 4 x = 721 - 980
				System.out.println(" entering ai selecting again ");
				
				//turn off ready

				if (csp2.ready)
				{
					csp2.ready = false;
					readyChanged = true;
					client.send("5");
				} 
				
				if (!csp2.ready && readyChanged) {
					this.remove(ready2);
					notReady.setForeground(Color.RED);
					this.add(notReady);
					readyChanged = false;
				}
				
				Random rand = new Random();
				if (main.getX() > 231 && main.getX() < 470 )
				{
					AIdifficulty = 0;
					
				}
				
				else if (main.getX() > 471 && main.getX() < 720)
				{
					AIdifficulty = 1;
				}
					
				else if (main.getX() > 721 && main.getX() < 980)
				{
					AIdifficulty = 2;
				}
					
				if (rand.nextInt(100) < 5)
				{
					AIdifficulty = 3;
				}
					
				if (main.getX() > 940 && main.getY() > 205 && main.getY() < 240)
				{
					AIdifficulty = 4;
				}
					
				speedx = 0;
				speedy = 0;
				prob = 4;
				aicsp.changeAI(AIdifficulty);
				aicsp.inCombat = true;
				aicsp.setVisible(true);
				this.setVisible(false);
				frame.remove(this);
				frame.add(aicsp);

				AICombatScreenThread aicst = new AICombatScreenThread(aicsp);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				aicst.execute();

				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println("AI encountered!");
			}
		}

		if (aicsp.combatFinished && !aicsp.inCombat) {
			aicsp.setVisible(false);
			frame.remove(aicsp);
			frame.add(this);
			this.setVisible(true);
			aicsp.combatFinished = false;
		}
		if (csp2.combatFinished && !aicsp.inCombat) {
			csp2.setVisible(false);
			frame.remove(csp2);
			frame.add(this);
			this.setVisible(true);
			csp2.combatFinished = false;
		}
	}

	/**
	 * Changes the movement of the player when direction keys are released.
	 */
	public void keyReleased(KeyEvent e) {
		speedx = 0;
		speedy = 0;
	}

	/**
	 * Adds a player to the ArrayList of other player
	 * 
	 * @param newPlayer
	 *            The player to add
	 */
	public void addPlayer(Player newPlayer) {
		others.add(newPlayer);
	}

	/**
	 * Removes a player from the ArrayList of other players
	 * 
	 * @param oldPlayer
	 *            The player to remove
	 */
	public void removePlayer(Player oldPlayer) {
		others.remove(oldPlayer);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Returns the array list of other players on the map
	 * 
	 * @return The list of other players
	 */
	public ArrayList<Player> getPlayerArray() {
		return others;
	}

	/**
	 * Returns the main player, controlled by the local user
	 * 
	 * @return The main player
	 */
	public Player getMain() {
		return main;
	}

	/**
	 * Creates a 2v2 combat screen with the 4 players connected in the map
	 */
	public void combat2v2() {
		if (others.size() == 4 && !csp2.inCombat) {

			for (int x = 0; x < 4; x++) {

				if (others.get(x).getName() == main.getName()) {
					mainPos = x;
					main.setPlayerID(mainPos);
				}
				if (!others.get(x).isInCombat()) {
					combatReady = false;
					break;
				} else {
					combatReady = true;
				}
			}
			if (combatReady && !csp2.inCombat) {
				System.out.println("2v2 combat started");

				csp2.p1 = others.get(mainPos);
				if (mainPos == 0) {
					csp2.p2 = others.get(1);
					csp2.p3 = others.get(2);
					csp2.p4 = others.get(3);
					System.out.println("Your player was in position: "
							+ mainPos);
				} else if (mainPos == 1) {
					csp2.p2 = others.get(0);
					csp2.p3 = others.get(2);
					csp2.p4 = others.get(3);
					System.out.println("Your player was in position: "
							+ mainPos);
				} else if (mainPos == 2) {
					csp2.p2 = others.get(0);
					csp2.p3 = others.get(1);
					csp2.p4 = others.get(3);
					System.out.println("Your player was in position: "
							+ mainPos);
				} else if (mainPos == 3) {
					csp2.p2 = others.get(1);
					csp2.p3 = others.get(2);
					csp2.p4 = others.get(3);
					System.out.println("Your player was in position: "
							+ mainPos);
				}
				System.out.println("adding frame");
				csp2.inCombat = true;
				this.setVisible(false);
				frame.remove(this);
				frame.add(csp2);
				csp2.setVisible(true);

				System.out.println("Frame added");
				frame.repaint();
				csp2.repaint();
				System.out.println("main id: " + csp2.p1.getPlayerID());
				System.out.println("player 2: " + csp2.p2.getPlayerID());
				System.out.println("player 3: " + csp2.p3.getPlayerID());
				System.out.println("player 4: " + csp2.p4.getPlayerID());

				for (Player player : others) {
					if (player.getName().equals(main.getName()))
						main = player;
				}

				csp2.mainPlayer = main;
				CombatClientThread cct = new CombatClientThread(this, csp2.p1,
						csp2.p2, csp2.p3, csp2.p4);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				cct.execute();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}

		}
		if (gameOver) {
			csp2.showSkills();
			System.out.println("The Map Game Over");
			csp2.setVisible(false);
			frame.remove(csp2);
			csp2.combatLog.clear();
			frame.add(this);
			this.setVisible(true);
			csp2.combatFinished = false;
			gameOver = false;
			csp2.inCombat = false;
			System.out.println("");
			System.out.println("************************");
			client.send("5");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
			csp2.ready = false;
			readyChanged = true;
			repaint();
			revalidate();
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("*********************");
			client.send("7");
			System.out.println("?????????????????????");
		}
	}

	/**
	 * Sends the players command to the client server
	 * 
	 * @param newCommand2
	 *            The players command to send
	 */
	public void send(Command newCommand2) {

		client.sendCommandBYTES(newCommand2);

	}

	/**
	 * Returns the address of the server the player is connected to
	 * 
	 * @return The address of the server the player is connected to
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Resets the main character in the map, used for editing player stats.
	 * @param main The new character in the map
	 */
	public static void setMain(Player main) {
		TheMap.main = main;
	}

}
