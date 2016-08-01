package outworld;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.SwingWorker;

import outworld.AppendingObjectOutputStream;
import outworld.WorldServer;
import serialized.Data;
import GameState.CombatScreenPanel2v2;
import GameState.Command;
import GameState.CommandComparator;
import GameState.DamageSkill;
import GameState.Player;
import GameState.Skill.Targets;
import GameState.SkillsList;

/**
 * @author Liam Howe, Tim Ryan, Zhuo Lee
 * Class to hold all views seen by the four players in combat, and send the commands between them
 */
public class CombatThreadServer extends SwingWorker<Void, Player[]>{

	private CombatScreenPanel2v2 csp1;
	private CombatScreenPanel2v2 csp2;
	private CombatScreenPanel2v2 csp3;
	private CombatScreenPanel2v2 csp4;
	Player p1;
	Player p2;
	Player p3;
	Player p4;
	public SkillsList skillList = new SkillsList();
	public ArrayList<Command> commandList = new ArrayList<Command>();
	public Stack<Command> commandStack = new Stack<Command>();
	public Stack<Command> commandStackDefense = new Stack<Command>();
	public ArrayList<Player> combatants = new ArrayList<Player>();
	public LinkedList<String> combatLogServer = new LinkedList<String>();
	boolean finishedUpdating = false;
	public OutputQueue queue;
	
	/**
	 * Constructor, takes in the four players and adds them to the ArrayList of players
	 * @param p1 Player 1
	 * @param p2 Player 2
	 * @param p3 Player 3
	 * @param p4 Player 4
	 */
	public CombatThreadServer(Player p1, Player p2, Player p3, Player p4, OutputQueue queue){
		System.out.println("Thread constructor");
		p1 = p1;
		p2 = p2;
		p3 = p3;
		p4 = p4;
		this.queue = queue;
		combatants.add(p1);
		combatants.add(p2);
		combatants.add(p3);
		combatants.add(p4);
	}
	
	/**
	 * Runs the combat, collecting commands from players and executing them
	 */
	protected Void doInBackground() throws Exception {
		/*for(int x = (int) p1.getActualHP(); x > 0; x--){
			System.out.println("Background process running");
			//Thread.sleep(50);
			p1.setActualHP(p1.getActualHP()-1);
			publish(p1);
			Thread.sleep(1000);
		}*/
		System.out.println("doInBackground server thread");
		WorldServer.commandArray.clear();
		boolean gameOver = false;
		while(!gameOver){
			finishedUpdating = false;
			int commandID;
			combatants.get(0).playersUpdated = false;
			combatants.get(1).playersUpdated = false;
			combatants.get(2).playersUpdated = false;
			combatants.get(3).playersUpdated = false;
			for(int x = 0; x < combatants.size(); x++){
				combatants.get(x).setGuarding(false);
			}
			
			while(WorldServer.commandArray.size() != 4 ){
				Thread.sleep(500);
				System.out.println("Commands registered: " + WorldServer.commandArray.size());
			}
			System.out.println("dummy check loop entered");
			
			int count = WorldServer.commandArray.size();
			ArrayList<Integer> itemsToRemove = new ArrayList<Integer>();
			for(int i = 0; i < count; i++)
			{
//				WorldServer.commandArray.get(i).printString();
				System.out.println("Dummy command check called");
				if(dummyCommandCheck(WorldServer.commandArray.get(i))){
					itemsToRemove.add(i);
				}
			}
			System.out.println("dummy command loop completed");
			
			for(int j = 0; j < WorldServer.commandArray.size(); j++){
				System.out.println("Item " + j + " in WorldServer.commandArray " + WorldServer.commandArray.get(j).getSkillID());
			}
			for(Integer item : itemsToRemove) System.out.println("Item number " + item + " in WorldServer.commandArray to be removed = " + WorldServer.commandArray.get(item).getSkillID());
			for(Integer item : itemsToRemove){
		
				System.out.println("Item " + (int) item + " removed " + WorldServer.commandArray.get((int) item).getSkillID());
				if(WorldServer.commandArray.get(0).getAggresor() == 99)
				WorldServer.commandArray.remove(0);
				else if(WorldServer.commandArray.get(1).getAggresor() == 99)
					WorldServer.commandArray.remove(1);
				else if(WorldServer.commandArray.get(2).getAggresor() == 99)
					WorldServer.commandArray.remove(2);
				else if(WorldServer.commandArray.get(3).getAggresor() == 99)
					WorldServer.commandArray.remove(3);
			}
			for(Command command : WorldServer.commandArray){
				System.out.println("Command in WorldServer : " + command.getSkillID());
			}
			commandList = WorldServer.commandArray;
			for(Command command : commandList){
				System.out.println("Command in commandList : " + command.getSkillID());
			}
			System.out.println("Player 1 before: " + combatants.get(0).getActualHP());
			System.out.println("Player 2 before: " + combatants.get(1).getActualHP());
			System.out.println("Player 3 before: " + combatants.get(2).getActualHP());
			System.out.println("Player 4 before: " + combatants.get(3).getActualHP());
			for(int y = 0; y < commandList.size(); y++){
				commandStack.push(commandList.get(y));
			}
		
			commandList.clear();
			while(!commandStack.isEmpty())
			{
			
				//command stack split into
				//commandstack defense = stack of defensive commands 
				//command list = list of normal commands sort by speed
				
			    if (commandStack.peek().getCommandInt() == 1)
			    {
			    	System.out.println("guard detected");
			    	commandStackDefense.push(commandStack.pop());
			    	
			    }
			    else
			    {
			    	System.out.println("non guard command added");
			    	commandList.add(commandStack.pop());
			    }
			}
			//sorted commandlist
			Collections.sort(commandList, new CommandComparator());
			//aww snap
			System.out.println("Player 1 id: " + combatants.get(0).getPlayerID());
			System.out.println("Player 2 id: " + combatants.get(1).getPlayerID());
			System.out.println("Player 3 id: " + combatants.get(2).getPlayerID());
			System.out.println("Player 4 id: " + combatants.get(3).getPlayerID());
			commandExecute();
			
			System.out.println("Player " + combatants.get(0).getName() + " dead status = " + combatants.get(0).isDead());
			System.out.println("Player " + combatants.get(1).getName() + " dead status = " + combatants.get(1).isDead());
			System.out.println("Player " + combatants.get(2).getName() + " dead status = " + combatants.get(2).isDead());
			System.out.println("Player " + combatants.get(3).getName() + " dead status = " + combatants.get(3).isDead());
			
			WorldServer.commandArray.clear();
			
			p1 = combatants.get(0);
			p2 = combatants.get(1);
			p3 = combatants.get(2);
			p4 = combatants.get(3);
			System.out.println("player 1 after calculation: " + p1.getActualHP());
			System.out.println("player 2 after calculation: " + p2.getActualHP());
			System.out.println("player 3 after calculation: " + p3.getActualHP());
			System.out.println("player 4 after calculation: " + p4.getActualHP());
			Player[] updatedPlayers = {p1, p2, p3, p4};
			publish(updatedPlayers);
			
			if(p1.isDead() && p2.isDead() || p3.isDead() && p4.isDead())
			{
				if (p1.isDead() && p2.isDead())
				{
					for(int i = 0; i < WorldServer.playerArray.size();i++)
					{
						if (WorldServer.playerArray.get(i).getPlayerID() == p3.getPlayerID()|| 
								WorldServer.playerArray.get(i).getPlayerID() == p4.getPlayerID())
						{
							WorldServer.playerArray.get(i).setBonusPointsLeft(WorldServer.playerArray.get(i).getBonusPointsLeft()
									+30);
						}
					}
				}
				if (p3.isDead() && p4.isDead())
				{
					for(int i = 0; i < WorldServer.playerArray.size();i++)
					{
						if (WorldServer.playerArray.get(i).getPlayerID() == p1.getPlayerID()|| 
								WorldServer.playerArray.get(i).getPlayerID() == p2.getPlayerID())
						{
							WorldServer.playerArray.get(i).setBonusPointsLeft(WorldServer.playerArray.get(i).getBonusPointsLeft()
									+30);
						}
					}
				}
				gameOver = true;
			}
		
		} 

		/*WorldServer.playerArray.get(0).setInCombat(false);
		WorldServer.playerArray.get(1).setInCombat(false);
		WorldServer.playerArray.get(2).setInCombat(false);
		WorldServer.playerArray.get(3).setInCombat(false);
		WorldServer.playerArray.get(0).reset();
		WorldServer.playerArray.get(1).reset();
		WorldServer.playerArray.get(2).reset();
		WorldServer.playerArray.get(3).reset();*/
		while(!finishedUpdating){
			System.out.print(" ");
		}
		combatLogServer.clear();
		combatLogServer.add(" ");
		WorldServer.gameFinished = true;
		return null;
	}

	/**
	 * Sends the updated list of players to the world server
	 * @param NewPlayers The list of updated players
	 */
	protected void process(List<Player[]> NewPlayers){
		Player[] players = NewPlayers.get(NewPlayers.size()-1);
		for(Player player : players){
			player.playersUpdated = true;
		}
		ArrayList<Player> sendable = new ArrayList<Player>();
		for(int i = 0; i<4; i++){
			sendable.add(players[i]);
			System.out.println("Player " + players[i].getName() + " added to sendable with dead status " + players[i].isDead());
		}
		
		WorldServer.data.setPlayers(sendable);
		WorldServer.data.setCombatLog(combatLogServer);
		System.out.println("Updated Players Sent");
		
		Data data = WorldServer.data;
		
		queue.add(data);
		
		finishedUpdating = true;
		
	}
	
	/**
	 * Calls the particular method to execute the command based on what command
	 * the player chose.
	 */
	public void commandExecute()
	{
		//set defense first
		System.out.println("stack defense is: "+commandStackDefense.size());
		
		while (!commandStackDefense.isEmpty())
		{

			//increase mp
			// if mp > max mp mp = max
			// else restore 30 mp
			//set guarding = true
			String move = "Player " + combatants.get(commandStackDefense.peek().getAggresor()).getName() + " is Defending"  + " \n\n";
			combatLogServer.add(move);

			double playermp = combatants.get(commandStackDefense.peek().getAggresor()).getActualMP();
			double maxplayerMP = combatants.get(commandStackDefense.peek().getAggresor()).getMaxMP();
			if (playermp + 30 >=maxplayerMP )
			{
				combatants.get(commandStackDefense.peek().getAggresor()).setActualMP(maxplayerMP);
			}
			else
			{
				combatants.get(commandStackDefense.peek().getAggresor()).setActualMP(playermp + 30);
			}
			combatants.get(commandStackDefense.pop().getAggresor()).setGuarding(true);

		}
		System.out.println("stack defense is: "+commandStackDefense.size());
		
		for (int i = 0; i< commandList.size(); i++)
		{
			Command command = commandList.get(i); 
			if(combatants.get(command.getAggresor()).getActualHP() > 0){
				System.out.println("********************* Command Execute ***********************");
				command.printString();
				switch(command.getCommandInt())
				{
					case 0:
						System.out.println("Attack Execute");
						DamageCalculation(command);;
					case 2:;
					default:
						int skillType = command.getSkillType();
						System.out.println("skillType is: "+skillType); 
						switch (skillType)
						{
							case 0: System.out.println("Damage Calculation called, name is: "+combatants.get(command.getAggresor()).getName()); 
									DamageCalculation(command);
									break;
							case 1: System.out.println("Buffing skill called");
									buffingSkillExecute(command);
									break;
							case 2: System.out.println("Healing Skill called");
									healingSkillExecute(command);
									break;
							default: System.out.println("Case skipped"); ;
						}
						break;
				}
		
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
		// while there are targets
		// subtract mp
		// check if stat up
		// true = check affectpower
		// true = powermulti * 2
		// false = defensemulti * 2
		// false = check affectpower
		// true = powermulti * 0.5
		// false = defensemulti * 0.5
		System.out.println("Buffing skill execute called "
				+ command.getTargets().toString());
		String attribute ="";
		String attributeChange ="";
		String max ="";
		while (!command.getTargets().isEmpty()) {
			int multiplier;
			if (!command.isMPdeducted()) {
				combatants.get(command.getAggresor()).setActualMP(combatants.get(command.getAggresor()).getActualMP() - skillList.getBSkill()
										.get(command.getSkillID()).getMP());												
				command.setMPdeducted(true);
				//decrease mp
			}

			// stat affected == 3 dispel
			if (skillList.BuffingSkillList.get(command.getSkillID())
					.getstatAffected() == 3) {
				System.out.println("Reset power multi called");
				combatants.get(command.getTargets().peek()).setPowerMulti(3);
				combatants.get(command.getTargets().peek()).setDefenseMulti(3);
				String move = "Player " + combatants.get(command.getAggresor()).getName()  + " used on " + skillList.getBSkill().get(command.getSkillID()).getSkillName() +
						" Player " +combatants.get(command.getTargets().peek()).getName() + ", all stat modfiers have been reset." + " \n\n";
				combatLogServer.add(move);
				System.out.println("*** String '" + move + "' added to combat log");
			}
			// 0 = 0.1
			// 1 = 0.5
			// 2 = 0.75
			// 3 = 1
			// 4 = 1.5
			// 5 = 2
			// 6 = 3
			// statup true
			if (skillList.BuffingSkillList.get(command.getSkillID()).isStatUp()) {
				System.out.println("IS STAT UP? TRUE");
				// stat affected is power
				if (skillList.BuffingSkillList.get(command.getSkillID())
						.getstatAffected() == 0) {
					attribute =" power ";

					multiplier = combatants.get(command.getTargets().peek())
							.getPowerMulti();
					System.out.println("multiplier before " + multiplier);
					multiplier++;
					System.out.println("multiplier after " + multiplier);
					if (multiplier > 6) {
						System.out.println("MULTIPLIER BE TOO HIGH ");
						multiplier = 6;
						max =" but it's already maxed out...";

					}
					System.out.println("SETTIN DAT MULTIPLIER" + multiplier);
					combatants.get(command.getTargets().peek()).setPowerMulti(
							multiplier);

				} else if (skillList.BuffingSkillList.get(command.getSkillID())
						.getstatAffected() == 1) {
					attribute =" defense ";

					System.out.println("NOT POWER GOTTA BE DEFENSE");
					multiplier = combatants.get(command.getTargets().peek())
							.getDefenseMulti();
					multiplier++;
					if (multiplier > 6) {
						multiplier = 6;
						max =" but it's already maxed out...";

					}
					combatants.get(command.getTargets().peek()).setDefenseMulti(
							multiplier);
				} else {
					attribute = " speed ";

					System.out.println("SPEED");
					multiplier = combatants.get(command.getTargets().peek())
							.getSpeedMulti();
					multiplier++;
					if (multiplier > 6) {
						multiplier = 6;
						max =" but it's already maxed out...";

					}
					combatants.get(command.getTargets().peek()).setSpeedMulti(
							multiplier);
				}
			}
			// stat down
			else {
				attributeChange ="decreased";

				System.out.println("GOTTA BE STAT DOWN BRAH");
				if (skillList.BuffingSkillList.get(command.getSkillID())
						.getstatAffected() == 0) {
					attribute =" power ";
					multiplier = combatants.get(command.getTargets().peek())
							.getPowerMulti();
					multiplier--;
					if (multiplier < 0) {
						max =" but it could not be decreased any further...";
						multiplier = 0;
					}
					combatants.get(command.getTargets().peek()).setPowerMulti(
							multiplier);
					System.out.println("SETTING DAT DEBUFF POWER");
				} else if (skillList.BuffingSkillList.get(command.getSkillID())
						.getstatAffected() == 1) {
					attribute =" defense ";
					multiplier = combatants.get(command.getTargets().peek())
							.getDefenseMulti();
					multiplier = multiplier--;
					if (multiplier < 0) {
						multiplier = 0;
						max =" but it could not be decreased any further...";

					}
					combatants.get(command.getTargets().peek()).setDefenseMulti(
							multiplier);
					System.out.println("SETTIN DAT DEBUFF DEFENSE");
				} else {
					attribute =" speed ";

					multiplier = combatants.get(command.getTargets().peek())
							.getSpeedMulti();
					multiplier--;
					if (multiplier < 0) {
						max =" but it could not be decreased any further...";
						multiplier = 0;
					}
					combatants.get(command.getTargets().peek()).setSpeedMulti(
							multiplier);
					System.out.println("SETTIN DAT DEBUFF SPEED");
				}	
				String move = "Player " +combatants.get(command.getAggresor()).getName() + attributeChange + " Player " + combatants.get(command.getTargets().peek()).getName() +
						attribute + max +" \n\n";
				System.out.println("*** String '" + move + "' added to combat log");
				combatLogServer.add(move);
			}
			
			
			command.getTargets().pop();
		}
		// get targets increase or decrease power and defense
	}
	
	/**
	 * Executes a command where the player has chosen a healing skill
	 * @param command The player's command
	 */
	public void healingSkillExecute(Command command)
	{
		//subtract mp
		//get healing power =  power aggresor * power multi
		//healing power * healing power /70
		//if healing power > max hp then
			
		while(!command.getTargets().isEmpty())
		{
			if (!command.isMPdeducted())
			{
				combatants.get(command.getAggresor()).setActualMP(combatants.get(command.getAggresor()).getActualMP()-
						skillList.getHSkill().get(command.getSkillID()).getMP());//player mp - skill mp cost
				command.setMPdeducted(true);
			}
			
			double aggresorPower = combatants.get(command.getAggresor()).getBasePower() * combatants.get(command.getAggresor()).getPowerMulti();
			System.out.println("Aggresor Power: "+ aggresorPower);
			double healingDone = round((aggresorPower * skillList.HealingSkillList.get(command.getSkillID()).getHealingPower())/70);
			System.out.println("Healing Done: "+ healingDone);
			double ActualHP = combatants.get(command.getTargets().peek()).getActualHP();
			if(healingDone+ActualHP > combatants.get(command.getTargets().peek()).getMaxHP() )
			{
				//set target hp as their max hp
				combatants.get(command.getTargets().peek()).setActualHP(combatants.get(command.getTargets().peek()).getMaxHP());
			}
			else
			{
				combatants.get(command.getTargets().peek()).setActualHP(ActualHP+healingDone);
			}
			String move = "Player " + combatants.get(command.getAggresor()).getName() + " healed with " + skillList.getHSkill().get(command.getSkillID()).getSkillName() +
					" on Player " + combatants.get(command.getTargets().peek()).getName() + ", Healed " + healingDone + " HP" + " \n\n";
			combatLogServer.add(move);
			command.getTargets().pop();
		}
	}
	
	/**
	 * Executes a command where the player has chosen a damage skill.
	 * 
	 * @param command
	 *            The player's command
	 */
	public void DamageCalculation(Command command) {
		System.out.println();
		System.out.println("Entered Damage Calculation Targets: "
				+ command.getTargets().toString());
		while (!command.getTargets().isEmpty()) {
			// combatants.get(command.getAggresor()) = player
			// take away mp//damage = ((aggresor base power * power multi
			// /victim base defense * defense multi) + BASE SKILL POWER +1)*
			// modifer
			// total power = aggresor base power * aggresor power multiplier
			// total defense = victim base defense * victim defense multiplier
			// modifier = EAMulti * guard multiplier*random
			if (!command.isMPdeducted()) {
				combatants.get(command.getAggresor()).setActualMP( combatants.get(command.getAggresor()).getActualMP()- skillList.getDSkill()
										.get(command.getSkillID()).getMP());
				command.setMPdeducted(true);
				//deduct mp
			}

			Random r = new Random();
			double randomPower = 0.8 + (1 - 0.8) * r.nextDouble();
			// ////////////Calculate EA
			// multiplier///////////////////////////////////////
			DamageSkill.ElementalType skillEAType = skillList.DamageSkillList
					.get(command.getSkillID()).getElementalType();
			int EAResistance = 1;
			switch (skillEAType) {
			case AIR:
				EAResistance = combatants.get(command.getTargets().peek())
						.getEA().getAir();
				break;
			case EARTH:
				EAResistance = combatants.get(command.getTargets().peek())
						.getEA().getEarth();
				break;
			case FIRE:
				EAResistance = combatants.get(command.getTargets().peek())
						.getEA().getFire();
				break;
			case WATER:
				EAResistance = combatants.get(command.getTargets().peek())
						.getEA().getWater();
				break;
			case VOID:
				EAResistance = combatants.get(command.getTargets().peek())
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
				EAmultiplier = 0.75; // 50%
				break;
			case 2:
				EAmultiplier = 1.25; // 100%
				break;
			case 3:
				EAmultiplier = 2.5; // 200%
				break;
			}
			System.out.println("EAmultiplier is: " + EAmultiplier);

			double guardmultiplier;
			if (combatants.get(command.getTargets().peek()).getGuarding() == false) {
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
			int powerMultiplierInt = combatants.get(command.getAggresor())
					.getPowerMulti();
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
			int defenseMultiplierInt = combatants.get(command.getTargets().peek())
					.getDefenseMulti();
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

			double totalPower = combatants.get(command.getAggresor()).getBasePower() * powerMultiplier;
			double totalDefense = combatants.get(command.getTargets().peek()).getBaseDefence() * defenseMultiplier;
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
			if (hitRoll > skillList.DamageSkillList.get(command.getSkillID())
					.getAccuracy()) 
			{	
				System.out.println("MISSED!");
				String move = "Player " + combatants.get(command.getAggresor()).getName() + " used " + skillList.getDSkill().get(command.getSkillID()).getSkillName() +
						" on Player " + combatants.get(command.getTargets().peek()).getName() + ", Missed!" + " \n\n";
				combatLogServer.add(move);
			} 
			else 
			{
				// damage absorbed exceed max hp
				// actual health + damage dealt > max hp
				// damage dealt - actualhp > max hp
				if (-damagedealt+ combatants.get(command.getTargets().peek())
								.getActualHP() > combatants.get(
						command.getTargets().peek()).getMaxHP()) 
					{

					// damage exceed target max hp
						damagedealt = 0;
						combatants.get(command.getTargets().peek()).setActualHP(
							combatants.get(command.getTargets().peek())
									.getMaxHP());
					}
				else if (damagedealt > combatants.get(
						command.getTargets().peek()).getActualHP()) 
				{
					damagedealt = combatants.get(command.getTargets().peek())
							.getActualHP();
				}

				System.out.println("damage dealt is:  " + damagedealt);
				combatants.get(command.getTargets().peek()).setActualHP(
						combatants.get(command.getTargets().peek())
								.getActualHP() - damagedealt);
				System.out.println("SET DEATH");
				System.out.println("Hp is: "
						+ combatants.get(command.getTargets().peek())
								.getActualHP());
				if (combatants.get(command.getTargets().peek()).getActualHP() <= 0)
				{
					combatants.get(command.getTargets().peek()).setDead(true);
					System.out.println("Player " + combatants.get(command.getTargets().peek()).getName() + "set dead to " + combatants.get(command.getTargets().peek()).isDead());
					System.out.println("Dead Hp is: "
							+ combatants.get(command.getTargets().peek())
									.getActualHP());
				}
				String move = "Player " + combatants.get(command.getAggresor()).getName() + " used " + skillList.getDSkill().get(command.getSkillID()).getSkillName() +
						" on Player " + combatants.get(command.getTargets().peek()).getName() + ", Damage Dealt " + damagedealt + " \n\n";
				combatLogServer.add(move);
			}
			command.getTargets().pop();
		}
	}
	
	
	/**
	 * Casts a double to a int
	 * @param number The input number as a double
	 * @return The number rounded to an int
	 */
	public static int round(double number)
	{
		return (int)number;
	}
	public boolean dummyCommandCheck(Command command){
		if (command.getAggresor() == 99){
			System.out.println("Dummy command received");
			return true;
		}
		else { 
			System.out.println("Not a Dummy Command");
			return false; 
		} 
	}
	
}

