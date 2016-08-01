package GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.SwingWorker;

/**
 * 
 * @author Zhuo Lee adapted from Liam Howe's (ldh304) CombatScreenThread
 * 
 * Class to run the combat screen with an AI opponent in a background thread
 *
 */
public class AICombatScreenThread extends SwingWorker<Void, Player[]> {


	private AICombatScreenPanel aicsp;
	public Player p1;
	public Player p2;
	public SkillsList skillList = new SkillsList();
	public ArrayList<Command> commandList = new ArrayList<Command>();
	public Stack<Command> commandStack = new Stack<Command>();
	public Stack<Command> commandStackDefense = new Stack<Command>();
	public ArrayList<Player> combatants = new ArrayList<Player>();
	public AIPlayer aiplayer = new AIPlayer();
	public String combatLog;
	

	public AICombatScreenThread (AICombatScreenPanel aicsp){
		
		this.aicsp = aicsp;
		
		p1 = aicsp.p1;
		p2 = aicsp.p2;
		aiplayer.newAI(aicsp.difficulty);
		System.out.println("constructor p2 is"+p2.getPlayerID());
		reset();
		combatants.add(p1);
		combatants.add(p2);
	
	}

	/**
	 * Runs the commands gathered by thread whilst combat has not been terminated
	 */
	protected Void doInBackground() throws Exception {
		boolean gameOver = false;
		while (!gameOver) {
			int commandID;
			while (aicsp.commandInt == 10 && !aicsp.targetSelected) {
				Thread.sleep(500);
			}
			//gather commands
			p2 = aicsp.p2;
			combatLog ="";
			System.out.println("adding player command");
			aicsp.command.printString();
			commandStack.push(aicsp.command);
			System.out.println("pushing ai command player 2 ai is "+p2.getPlayerID());
			commandStack.push(aiplayer.getCommand(p2.getPlayerID(), p1, p2));
			System.out.println(commandStack.toString());
			System.out.println("pushed ai command");
			//seperate commandstack into command list and commandstackdefense
			while (!commandStack.isEmpty()) {
				if (commandStack.peek().getCommandInt() == 1) {
					System.out.println("guard detected");
					commandStackDefense.push(commandStack.pop());

				} 
				else 
				{
					commandList.add(commandStack.pop());
				}
			}
			// sorted commandlist
			Collections.sort(commandList, new CommandComparator());
			//execute commands
			commandExecute();
			aicsp.fillCombatLog(combatLog);
			System.out.println(p1.getActualHP());
			System.out.println(p2.getActualHP());
			Player[] updatedPlayers = { p1, p2 };
			publish(updatedPlayers);

			aicsp.commandInt = 10;
			aicsp.command = null;
			aicsp.targetSelected = false;
			if (checkDeath() == true) 
			{
				//upon defeat
				if (p1.isDead())
				{
					//defeated reduce bonus
					if (p1.getBonusPointsLeft() < 5)
					{
						p1.setBonusPointsLeft(0);
					}
					else
					{
						p1.setBonusPointsLeft(p1.getBonusPointsLeft() - 10);
					}
				}
				else
				{
					//check p2 player id set bonus accordingly
					int bonus =0;
					int p2ID = p2.getPlayerID();
					if (p2ID == 101)
						bonus = 5;
					else if (p2ID ==102)
						bonus = 10;
					else if (p2ID ==103)
						bonus = 15;
					else if (p2ID == 104)
						bonus =20;
					else
					{
						bonus = 50;
					}
					
					p1.setBonusPointsLeft(p1.getBonusPointsLeft() + bonus);
				}
				Thread.sleep(2000);
				terminate();
			}

		}

		return null;
	}
	
	/**
	 * Resets attributes needed for combat
	 */
	public void reset()
	{
		p1.reset();
		p2.reset();
		aiplayer.memory.clear();
		aicsp.clearCombatLog();
		commandStack.clear();
		commandList.clear();
		commandStackDefense.clear();
	}

	/**
	 * terminates combat by switching a boolean in aicsp
	 */
	public void terminate()
	{
		//terminate on death of player / ai / run
		aicsp.inCombat = false;
		aicsp.combatFinished = true;
		commandStackDefense.toString();
		reset();
		
	}

	/**
	 * Updates the players on the combat screen
	 */
	protected void process(List<Player[]> NewPlayers) {
		aicsp.p1 = NewPlayers.get(NewPlayers.size() - 1)[0];
		aicsp.p2 = NewPlayers.get(NewPlayers.size() - 1)[1];
		aicsp.repaint();
	}

	/**
	 * Calls the particular method to execute the command based on what command
	 * the player chose.
	 */
	public void commandExecute() {
		// set defend first
		// Execute commands
		// check for hp of players
		// if hp == 0
		// set player to dead
		//ignore commands if player is dead
		System.out.println("commandExecute entered");
		while (!commandStackDefense.isEmpty()) 
		{
			// increase mp
			// if mp > max mp mp = max
			// else restore 30 mp
			// set guarding = true
			String move = "Player " + getTarget(commandStackDefense.peek().getAggresor()).getName() + " is Defending"  + " \n\n";
			combatLog= combatLog +move;
			double playermp = getTarget(commandStackDefense.peek().getAggresor()).getActualMP();
			double maxplayerMP = getTarget(commandStackDefense.peek().getAggresor()).getMaxMP();
			if (playermp + 30 >=maxplayerMP )
			{
				getTarget(commandStackDefense.peek().getAggresor()).setActualMP(maxplayerMP);
			}
			else
			{
				getTarget(commandStackDefense.peek().getAggresor()).setActualMP(playermp + 30);
			}
			getTarget(commandStackDefense.pop().getAggresor()).setGuarding(true);

		}
		for (int i = 0; i < commandList.size(); i++)
		{
			commandList.get(i).printString();
		}
		//list still has commands
		while (!commandList.isEmpty())
		{
			if (!commandList.isEmpty())
			{
				Command command = commandList.get(0);
				command.printString();
				System.out.println(getTarget(command.getAggresor()).getActualHP() > 0);
				//only execute commands if aggressor is alive
				if (getTarget(command.getAggresor()).getActualHP() > 0)
				{
					switch (command.getCommandInt()) 
					{
					case 0:
						System.out.println("Attack Execute");
						DamageCalculation(command);
						break;
					case 2:
						//case of running
						//roll
						Random rand = new Random();
						int prob = rand.nextInt(100);
						System.out.println("Running attempt "+prob);
						if (prob <90)
						{
							//escape
							commandList.remove(0);
							terminate();
						}
						break;
					default:
						if (!commandList.isEmpty())
						{
							int skillType = command.getSkillType();
							System.out.println("skillType is: " + skillType);
							switch (skillType) {
							case 0:
								System.out.println("Damage Calculation called: ");
								DamageCalculation(command);
								break;
							case 1:
								System.out.println("Buffing skill called");
								buffingSkillExecute(command);
								break;
							case 2:
								System.out.println("Healing Skill called");
								healingSkillExecute(command);
								break;
							default:
								System.out.println("Case skipped");
								break;
							}
						}
						break;
					}

				}
				if (!commandList.isEmpty())
				{
					commandList.remove(0);
				}
				Collections.sort(commandList, new CommandComparator());
			}
			
		}

	}


	/**
	 * Executes a command where the player has chosen a buff skill
	 * 
	 * @param command
	 *            The player's command
	 */
	public void buffingSkillExecute(Command command) {
		//stat affected 3 = dispel
		//				0 = power
		//				1 = defense
		//				2 = power
		//isstatup = buff/debuff
		//decrease mp, check isStatup
		//check stat affected and inflict buff/debuff to attribute
		String attribute ="";
		String attributeChange ="";
		String max ="";
		System.out.println("Buffing skill execute called " );
		command.printString();
		while (!command.getTargets().isEmpty()) 
		{
			int multiplier;
			if (!command.isMPdeducted()) 
			{
				getTarget(command.getAggresor()).setActualMP(
					getTarget(command.getAggresor()).getActualMP() - skillList.getBSkill() .get(command.getSkillID()).getMP());
				command.setMPdeducted(true);
			}

			// stat affected == 3 dispel
			if (skillList.BuffingSkillList.get(command.getSkillID())
					.getstatAffected() == 3) {
				System.out.println("Reset power multi called");
				getTarget(command.getTargets().peek()).setPowerMulti(3);
				getTarget(command.getTargets().peek()).setDefenseMulti(3);
				String move = "Player " + getTarget(command.getAggresor()).getName() + " used on " + skillList.getBSkill().get(command.getSkillID()).getSkillName() +
						" Player " + getTarget(command.getTargets().peek()).getName() + ", all stat modfiers have been reset." + " \n\n";
				combatLog= combatLog +move;
			}
			// 0 = 0.1
			// 1 = 0.5
			// 2 = 0.75
			// 3 = 1
			// 4 = 1.5
			// 5 = 2
			// 6 = 3
			// statup true
			if (skillList.BuffingSkillList.get(command.getSkillID()).isStatUp()) 
			{
				attributeChange = " increased ";
				System.out.println("IS STAT UP? TRUE");
				// stat affected is power
				if (skillList.BuffingSkillList.get(command.getSkillID())
						.getstatAffected() == 0) {
					attribute =" power ";
					
					multiplier = getTarget(command.getTargets().peek()).getPowerMulti();
					System.out.println("multiplier before " + multiplier);
					multiplier++;
					System.out.println("multiplier after " + multiplier);
					if (multiplier > 6) 
					{
						System.out.println("MULTIPLIER BE TOO HIGH ");
						multiplier = 6;
						max =" but it's already maxed out...";
					}
					System.out.println("SETTIN DAT MULTIPLIER" + multiplier);
					getTarget(command.getTargets().peek()).setPowerMulti(multiplier);

				} 
				else if (skillList.BuffingSkillList.get(command.getSkillID())
						.getstatAffected() == 1) 
				{
					attribute =" defense ";
					System.out.println("NOT POWER GOTTA BE DEFENSE");
					multiplier = getTarget(command.getTargets().peek()).getDefenseMulti();
					multiplier++;
					if (multiplier > 6)
					{
						multiplier = 6;
						max =" but it's already maxed out...";
					}
					getTarget(command.getTargets().peek()).setDefenseMulti(multiplier);
					}
				else
				{
					attribute = " speed ";
					System.out.println("SPEED");
					multiplier = getTarget(command.getTargets().peek()).getSpeedMulti();
					multiplier++;
					if (multiplier > 6) 
					{
						max =" but it's already maxed out...";
						multiplier = 6;
					}
					getTarget(command.getTargets().peek()).setSpeedMulti(multiplier);
				}
				String move = "Player " + getTarget(command.getAggresor()).getName() + attributeChange + " Player " + getTarget(command.getTargets().peek()).getName() +
						attribute + max +" \n\n";
				combatLog= combatLog +move;
			}
			// stat down
			else 
			{
				attributeChange ="decreased";
				System.out.println("GOTTA BE STAT DOWN BRAH");
				if (skillList.BuffingSkillList.get(command.getSkillID())
						.getstatAffected() == 0) 
				{
					attribute =" power ";
					multiplier = getTarget(command.getTargets().peek()).getPowerMulti();
					multiplier--;
				if (multiplier < 0) 
				{
					max =" but it could not be decreased any further...";
					multiplier = 0;
				}
				getTarget(command.getTargets().peek()).setPowerMulti(multiplier);
				System.out.println("SETTING DAT DEBUFF POWER");
				} 
				else if (skillList.BuffingSkillList.get(command.getSkillID()).getstatAffected() == 1) 
				{
					attribute =" defense ";
					multiplier = getTarget(command.getTargets().peek()).getDefenseMulti();
					multiplier = multiplier--;
					if (multiplier < 0) 
					{
						max =" but it could not be decreased any further...";
						multiplier = 0;
					}
					getTarget(command.getTargets().peek()).setDefenseMulti(multiplier);
					System.out.println("SETTIN DAT DEBUFF DEFENSE");
				}
				else 
				{
					attribute =" speed ";
					multiplier = getTarget(command.getTargets().peek()).getSpeedMulti();
					multiplier--;
					if (multiplier < 0) 
					{
						max =" but it could not be decreased any further...";
						multiplier = 0;
					}
					getTarget(command.getTargets().peek()).setSpeedMulti(multiplier);
					System.out.println("SETTIN DAT DEBUFF SPEED");
				}
				String move = "Player " + getTarget(command.getAggresor()).getName() + attributeChange + " Player " + getTarget(command.getTargets().peek()).getName() +
						attribute + max +" \n\n";
				combatLog= combatLog +move;
			}
			command.getTargets().pop();	
		}
	}

	/**
	 * Executes a command where the player has chosen a healing skill
	 * 
	 * @param command
	 *            The player's command
	 */
	public void healingSkillExecute(Command command) {
		// subtract mp
		// get healing power = power aggresor * power multi
		// healing power * healing power /70 = healing done
		// if healing done > max hp then
		// actual hp = max hp
		//else
		//actual hp = actual hp + healing done
		command.printString();

		while (!command.getTargets().isEmpty()) 
		{
			if (!command.isMPdeducted())
			{
				//player mp - skill mp cost
				getTarget(command.getAggresor()).setActualMP(
					getTarget(command.getAggresor()).getActualMP() - skillList.getHSkill().get(command.getSkillID()).getMP());
																		
				command.setMPdeducted(true);
			}

			double aggresorPower = getTarget(command.getAggresor()).getBasePower()
					* getTarget(command.getAggresor()).getPowerMulti();
			System.out.println("Aggresor Power: " + aggresorPower);
			double healingDone = round((aggresorPower * skillList.HealingSkillList
					.get(command.getSkillID()).getHealingPower()) / 70);
			System.out.println("Healing Done: " + healingDone);
			double ActualHP = getTarget(command.getTargets().peek()).getActualHP();
			System.out.println("Actual HP: " + ActualHP);
			if (healingDone + ActualHP > getTarget(command.getTargets().peek()).getMaxHP()) 
			{
				// set target hp as their max hp
				getTarget(command.getTargets().peek()).setActualHP(
						getTarget(command.getTargets().peek()).getMaxHP());
			} 
			else
			{
				getTarget(command.getTargets().peek()).setActualHP(
						ActualHP+healingDone);
			}
			
			String move = "Player " + getTarget(command.getAggresor()).getName() + " healed with " + skillList.getHSkill().get(command.getSkillID()).getSkillName() +
					" on Player " + getTarget(command.getTargets().peek()).getName() + ", Healed " + healingDone + " HP" + " \n\n";
			combatLog= combatLog +move;
			command.getTargets().pop();
		}
	}

	/**
	 * Executes a command where the player has chosen a damage skill.
	 * 
	 * @param command
	 *            The player's command
	 */
	public void DamageCalculation(Command command) 
	{
		System.out.println();
		System.out.println("Entered Damage Calculation Targets: ");
		command.printString();
		while (!command.getTargets().isEmpty()) 
		{
			//deduct mp
			if (!command.isMPdeducted()) 
			{
				getTarget(command.getAggresor()).setActualMP(
						getTarget(command.getAggresor()).getActualMP()-skillList.getDSkill().get(command.getSkillID()).getMP());
				// player mp - skill mp cost														
				command.setMPdeducted(true);
			}

			Random r = new Random();
			double randomPower = 0.8 + (1 - 0.8) * r.nextDouble();
			// ////////////Calculate EA multiplier///////////////////////////////////////
			DamageSkill.ElementalType skillEAType = skillList.DamageSkillList
					.get(command.getSkillID()).getElementalType();
			int EAResistance = 1;
			switch (skillEAType) {
			case AIR:
				
				EAResistance = getTarget(command.getTargets().peek())
						.getEA().getAir();
				break;
			case EARTH:
				EAResistance = getTarget(command.getTargets().peek())
						.getEA().getEarth();
				break;
			case FIRE:
				EAResistance = getTarget(command.getTargets().peek())
						.getEA().getFire();
				break;
			case WATER:
				EAResistance = getTarget(command.getTargets().peek())
						.getEA().getWater();
				break;
			case VOID:
				EAResistance =getTarget(command.getTargets().peek())
						.getEA().getVoid();
				break;
			}
			double EAmultiplier = 1;
			switch (EAResistance) {
			// -1 0 1 2 3
			// absorb 0% 75% 125% 250%
			case -1:
				EAmultiplier = -1;// heal + 100%
				break;
			case 0:
				EAmultiplier = 0; // 0%
				break;
			case 1:
				EAmultiplier = 0.75; // 75%
				break;
			case 2:
				EAmultiplier = 1.25; // 125%
				break;
			case 3:
				EAmultiplier = 2.5; // 250%
				break;
			}
			System.out.println("EAmultiplier is: " + EAmultiplier);

			double guardmultiplier;
			if(getTarget(command.getTargets().peek()).getGuarding() == false)
			{
				guardmultiplier = 1;
			} else {
				guardmultiplier = 0.45;
			}
			// //////////Calculate Buffing
			// multipliers///////////////////////////////////////////////////
			// 0 = 0.1
			// 1 = 0.5
			// 2 = 0.75
			// 3 = 1
			// 4 = 1.5
			// 5 = 2
			// 6 = 3
			
			int powerMultiplierInt = getTarget(command.getAggresor()).getPowerMulti();
			double powerMultiplier = 3;
			// calculate power multiplier
			switch (powerMultiplierInt) {
			case 0:
				powerMultiplier = 0.1;
				break;
			case 1:
				powerMultiplier = 0.5;
				break;
			case 2:
				powerMultiplier = 0.75;
				break;
			case 3:
				powerMultiplier = 1;
				break;
			case 4:
				powerMultiplier = 1.5;
				break;
			case 5:
				powerMultiplier = 2;
				break;
			case 6:
				powerMultiplier = 3;
				break;
			}
			// calculate defense multiplier
			int defenseMultiplierInt =getTarget(command.getAggresor()).getDefenseMulti();
			double defenseMultiplier = 3;
			// calculate power multiplier
			switch (defenseMultiplierInt) {
			case 0:
				defenseMultiplier = 0.1;
				break;
			case 1:
				defenseMultiplier = 0.5;
				break;
			case 2:
				defenseMultiplier = 0.75;
				break;
			case 3:
				defenseMultiplier = 1;
				break;
			case 4:
				defenseMultiplier = 1.5;
				break;
			case 5:
				defenseMultiplier = 2;
				break;
			case 6:
				defenseMultiplier = 3;
				break;
			}
			double totalPower = getTarget(command.getAggresor()).getBasePower() * powerMultiplier;
			double totalDefense = getTarget(command.getTargets().peek()).getBaseDefence() * defenseMultiplier;
			double modifer = EAmultiplier * randomPower * guardmultiplier;
			double damagedealt = round((0.84*(totalPower / totalDefense)
					* skillList.getDSkill().get(command.getSkillID())
							.getPower() + 2) * modifer);
			System.out.println("");
			System.out.println("total power: " + totalPower);
			System.out.println("total defense: " + totalDefense);
			System.out.println("modfier: " + modifer);
			System.out.println("DAMAGE: " + damagedealt);
			System.out.println("");

			// ///////////////////Damage
			// Calculation///////////////////////////////////////////////////////////////////////////////////////////
			// damage = ((aggresor base power * power multi /victim base defense
			// * defense multi) + BASE SKILL POWER +1)* modifer
			// total power = aggresor base power * aggresor power multiplier
			// total defense = victim base defense * victim defense multiplier
			// modifier = EAMulti * random * guard multiplier

			int hitRoll = r.nextInt((100));
			if (hitRoll > skillList.DamageSkillList.get(command.getSkillID()).getAccuracy()) 
			{
				System.out.println("MISSED!");
				for (AIMemory m : aiplayer.memory)
				{
					m.printString();
				}
				
				String move = "Player " + getTarget(command.getAggresor()).getName() + " used " + skillList.getDSkill().get(command.getSkillID()).getSkillName() +
						" on Player " + getTarget(command.getTargets().peek()).getName() + ", Missed!" + " \n\n";
				combatLog= combatLog +move;
			} 
			else
			{
				// damage absorbed exceed max hp
				// actual health + damage dealt > max hp
				// damage dealt - actualhp > max hp
				
				//hp absorbed exceeds max hp
				
				if (-damagedealt + getTarget(command.getTargets().peek()).getActualHP() > getTarget(command.getTargets().peek()).getMaxHP())
				{
					// damage exceed target max hp
					damagedealt = 0;
					getTarget(command.getTargets().peek()).setActualHP(getTarget(command.getTargets().peek()).getMaxHP());
				}
				
				else if (damagedealt >getTarget(command.getTargets().peek()).getActualHP()) 
				{
					damagedealt = getTarget(command.getTargets().peek()).getActualHP();

				}

				System.out.println("damage dealt is:  " + damagedealt);
				getTarget(command.getTargets().peek()).setActualHP(
							getTarget(command.getTargets().peek()).getActualHP()-damagedealt);
				if (getTarget(command.getTargets().peek()).getActualHP() <= 0) 
				{
					getTarget(command.getTargets().peek()).setDead(true);
				}
				//create memory node for ai to remember
				AIMemory memoryNode = new AIMemory(command.getAggresor(), command.getTargets().peek(),
						damagedealt, command.getSkillType(), command.getSkillID());
				aiplayer.memory.add(memoryNode);
				for (AIMemory m : aiplayer.memory)
				{
					m.printString();
				}
				String move = "Player " + getTarget(command.getAggresor()).getName() + " used " + skillList.getDSkill().get(command.getSkillID()).getSkillName() +
						" on Player " + getTarget(command.getTargets().peek()).getName() + ", Damage Dealt " + damagedealt + " \n\n";
				combatLog= combatLog +move;
				
			}
			command.getTargets().pop();
		}
	}

	/**
	 * Casts a double to a int
	 * 
	 * @param number
	 *            The input number as a double
	 * @return The number rounded to an int
	 */
	public static int round(double number) {
		return (int) number;
	}

	/**
	 * Checks if any of the players in combat are dead
	 * @return Whether any of the players are dead
	 */
	public Boolean checkDeath() {
		if (p1.isDead()|| p2.isDead()) 
		{
			return true;
		} 
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param playerID
	 * @return
	 */
	public Player getTarget(int playerID)
	{
		for (Player p : combatants)
		{
			if (p.getPlayerID() == playerID)
			{
				return p;
			}
		}
		return null;
	}
}
