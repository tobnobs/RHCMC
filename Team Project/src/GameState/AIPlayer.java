package GameState;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * 
 * @author Zhuo Lee
 * 
 * Class to hold the different AI players and create commands
 *
 */
public class AIPlayer {
	//buffing = own team
	//debuff = opposite team
	// heal = self/team
	//damage = enemy
	//null / absorb == dont use skill again
	//spam higher daamge
	//mp conservation?
	private Random rand = new Random();
	private SkillsList skillsList = new SkillsList();
	//ElementalAttributes ea = new ElementalAttributes(void_, fire, water, earth, air)
	//Player p1 = new Player(name, ea, HP, MP, totalbonusPoints, bonusPointsLeft, power, defence, speed, skillSet, Team, PlayerID, x, y)
	private Player ai0 = new Player("Average Joe", new ElementalAttributes(1, 1, 1, 1, 1), 80, 80, 0, 100, 80, 80, 80, 
												new Skill[] {skillsList.DamageSkillList.get(1),skillsList.DamageSkillList.get(2),
												skillsList.DamageSkillList.get(3),skillsList.DamageSkillList.get(4),
												skillsList.DamageSkillList.get(5),skillsList.BuffingSkillList.get(0)},
												
												0, 101, rand.nextInt(200), rand.nextInt(200));
	private Player ai1 = new Player("Volcanic Axe Wolf", new ElementalAttributes(1, -1, 2, 1, 2), 80, 140, 0, 0, 100, 100, 81, 
												new Skill[] {skillsList.DamageSkillList.get(11),skillsList.DamageSkillList.get(1),
												skillsList.DamageSkillList.get(13),skillsList.DamageSkillList.get(15),
												skillsList.BuffingSkillList.get(1),skillsList.BuffingSkillList.get(12)}, 
												
												0, 102, rand.nextInt(200), rand.nextInt(200));
	private Player ai2 = new Player("Ghost Aeon", new ElementalAttributes(0, 2, 1, 1, 1), 120, 140, 0, 100, 120, 80, 100, 
												new Skill[] {skillsList.DamageSkillList.get(15),skillsList.DamageSkillList.get(13),
												skillsList.DamageSkillList.get(14),skillsList.DamageSkillList.get(12),
												skillsList.BuffingSkillList.get(1),skillsList.HealingSkillList.get(1)},
												
												0, 103, rand.nextInt(200),	rand.nextInt(200));
	private Player ai3 = new Player("Maverick", new ElementalAttributes(1, 0, 2, 2, 0), 110, 300, 0, 100, 80, 80, 130, 
												new Skill[] {skillsList.DamageSkillList.get(15),skillsList.DamageSkillList.get(11),
												skillsList.DamageSkillList.get(13),skillsList.BuffingSkillList.get(12),
												skillsList.BuffingSkillList.get(0),skillsList.HealingSkillList.get(2)}, 
												
												0, 104, rand.nextInt(200), rand.nextInt(200));
	private Player ai4 = new Player("The Almighty", new ElementalAttributes(1, 1, 1, 1, 1), 250, 10000, 0, 0, 140, 160, 80, 
												new Skill[] {skillsList.DamageSkillList.get(21),skillsList.DamageSkillList.get(15),
												skillsList.DamageSkillList.get(13),skillsList.DamageSkillList.get(12),
												skillsList.BuffingSkillList.get(12),skillsList.HealingSkillList.get(2)},
												0, 105, rand.nextInt(200),	rand.nextInt(200));
	
	
	public Player currentAI;
	private int buffprob=1;			//0 cannot buff, 1 low tier buff chance, 2 dispel self, 3 dispel opponent
	private int healprob=1;			//0 cannot heal, 1 normal heal chance, 2 likely heal chance, 3 heal straight away
	private String[] commandProb = new String[27]; 
	private int[] ai0atk = {1,2,3,4,5};
	private int[] ai1atk = {11,1,13,15};
	private int[] ai2atk = {15,13,14,12};
	private int[] ai3atk = {15,11,13};
	private int[] ai4atk = {21,15,13,12};
	public ArrayList<AIMemory> memory = new ArrayList<AIMemory>(); 
	
	/**
	 * Constructor needed to use methods
	 */
	public AIPlayer()
	{
		
	}
	
	
	/**
	 * Returns one of the five different difficulty AI opponents
	 * @param difficulty The difficulty level of the AI
	 * @return The AI player
	 */
	public Player newAI (int difficulty)
	{
		//depending on difficulty change A.I.
				switch (difficulty)
				{
					case 0:currentAI = ai0;
						break;
					case 1:currentAI = ai1;
						break;
					case 2:currentAI = ai2;
						break;
					case 3:currentAI = ai3;
						break;
					case 4:currentAI =ai4;
						break;
				}
		resetProb();
		return currentAI;
	}
	
	/**
	 * Returns which of two players matches a player ID, used to return the AI player
	 * @param itself The player ID
	 * @param player1 
	 * @param player2
	 * @return Which of the two players matches the ID
	 */
	public Player getItself(int itself,Player player1,Player player2)
	{
		if (player1.getPlayerID()== itself)
		{
			return player1;
		}
		else
		{
			return player2;
		}
	}
	
	/**
	 * Returns the which of two players does not match a player ID, used to return the 
	 * AI's opponent
	 * @param itself The player ID
	 * @param player1
	 * @param player2
	 * @return The player not matching the ID
	 */
	public Player getTarget (int itself,Player player1,Player player2)
	{
		if (player1.getPlayerID()== itself)
		{
			return player2;
		}
		else
		{
			return player1;
		}
	}
	
	/**
	 * Creates a command based on the two players in the combat
	 * @param itself The AI's ID
	 * @param player1
	 * @param player2
	 * @return An AI generated command
	 * heal, attack, dispel, buff
	 */
	public Command getCommand(int itself,Player player1,Player player2)
	{	
		resetProb();
		resetCommandProb();
		Stack<Integer> targets = new Stack<Integer>();
		//Command command = new Command(aggresor, commandInt, targets, speed, skillType, skillID)
		Command command = new Command(itself, 0, targets, 80, 0, 0);
		//calculate need of skills and fill probability array [24]
		//check if healing/buffing prob is urgent set command if it is
		// if not roll probability array
		healingProb( itself, player1, player2);
		System.out.println("healing prob executed");
		buffprob(itself, player1, player2);
		System.out.println("buff prob executed");
		attackProb(itself, player1, player2);
		System.out.println("attack prob executed");
		String chosenEffectiveness = "0";
		//probability array
		// 0 = ineffective reroll
		// 13  first digit attack, second digit is skillID
		// commandint is not too important as long as its not 1
		//first digit    
		// 1 attack
		// 2 buff 
		// 3 heal
		//skilltype 0 = attack 1 = buff 2 = heal
		switch (itself)
		{
			case 101:
				command.setSpeed(ai0.getActualSpeed());
				if(buffprob == 2)
				{
					//buff self with attack
					command.setCommandInt(8);
					command.setSkillType(1);
					command.setSkillID(0);
					command.getTargets().push(itself);		
				}
				else
				{
					//can attack
					//roll
					while (chosenEffectiveness.equals("0"))
					{
						
						int roll = rand.nextInt(27);
						//System.out.println(roll);
						if(!commandProb[roll].equals("0"))
						{
							chosenEffectiveness = commandProb[roll];
							command = parseCommand(command, chosenEffectiveness,  itself, player1, player2);
							//have valid command parse command and return it
						}
					}
					
				}
				break;
			case 102:
				command.setSpeed(ai1.getActualSpeed());
					if(buffprob == 3)
					{
						//dispel self
						command.setCommandInt(8);
						command.setSkillType(1);
						command.setSkillID(12);
						command.getTargets().push(itself); 
					}
					else if(buffprob == 4)
					{
						//dispel opponent
						command.setCommandInt(8);
						command.setSkillType(1);
						command.setSkillID(12);
						command.getTargets().push(getTarget(itself, player1, player2).getPlayerID());	
					}
					else
					{
						//buff self
						while (chosenEffectiveness.equals("0"))
						{
							int roll = rand.nextInt(27);
							if(!commandProb[roll].equals("0"))
							{
								chosenEffectiveness = commandProb[roll];
								command = parseCommand(command, chosenEffectiveness,  itself, player1, player2);
								//have valid command parse command and return it
							}
						}
					}
					
				break;
			case 103:
				command.setSpeed(ai2.getActualSpeed());
				if(buffprob == 2)
				{
						//buff self
						command.setCommandInt(8);
						command.setSkillType(1);
						command.setSkillID(1);
						command.getTargets().push(itself);	
				}
				else
				{
					while (chosenEffectiveness.equals("0"))
					{
						int roll = rand.nextInt(27);
						if(!commandProb[roll].equals("0"))
						{
							chosenEffectiveness = commandProb[roll];
							command = parseCommand(command, chosenEffectiveness,  itself, player1, player2);
							//have valid command parse command and return it
						}
					}
				}
				
				break;
			case 104:
				command.setSpeed(ai0.getActualSpeed());
				if(buffprob == 3)
				{
					//dispel self
					command.setCommandInt(8);
					command.setSkillType(1);
					command.setSkillID(12);
					command.getTargets().push(itself); 
				}
				else if(buffprob == 4)
				{
					//dispel opponent
					command.setCommandInt(8);
					command.setSkillType(1);
					command.setSkillID(12);
					command.getTargets().push(getTarget(itself, player1, player2).getPlayerID());	
				}
				else
				{
					
					while (chosenEffectiveness.equals("0"))
					{
						int roll = rand.nextInt(27);
						if(!commandProb[roll].equals("0"))
						{
							chosenEffectiveness = commandProb[roll];
							command = parseCommand(command, chosenEffectiveness,  itself, player1, player2);
							//have valid command parse command and return it
						}
					}
				}
				
				break;
			case 105:
				
				command.setSpeed(ai0.getActualSpeed());
				if(buffprob == 3)
				{
					//dispel self
					command.setCommandInt(8);
					command.setSkillType(1);
					command.setSkillID(12);
					command.getTargets().push(itself); 
				}
				else if(buffprob == 4)
				{
					//dispel opponent
					command.setCommandInt(8);
					command.setSkillType(1);
					command.setSkillID(12);
					command.getTargets().push(getTarget(itself, player1, player2).getPlayerID());
				}
				if (healprob == 3)
				{
					//heal self
					command.setCommandInt(8);
					command.setSkillType(2);
					command.setSkillID(2);
					command.getTargets().push(itself);
					
				}
				else
				{
					//roll for attack or defense
					while (chosenEffectiveness.equals("0"))
					{
						int roll = rand.nextInt(27);
						if(!commandProb[roll].equals("0"))
						{
							chosenEffectiveness = commandProb[roll];
							command = parseCommand(command, chosenEffectiveness,  itself, player1, player2);
							//have valid command parse command and return it
						}
					}
				}
				
				
				break;
		}
		for (int i = 0; i < commandProb.length; i++)
		{
			System.out.println(commandProb[i]);
		}
		command.printString();
		return command;
	}
	
	/**
	 * Returns a parsed command, adding the command number, the skill type, the ID, and the target
	 * @return The command
	 */
	public Command parseCommand (Command command, String chosenEffectiveness,int itself,Player player1,Player player2)
	{
		//parse string into a command
		//get first digit for type
		//if 3 digits then get last 2 digits for id
		// 1 = attack oponent
		// 2 = buff self
		// 3 = heal self
		
			int type = getType(chosenEffectiveness);
			int id = getID(chosenEffectiveness);
			System.out.println("chosenEffectivenesss: "+ chosenEffectiveness +" type: "+type+" id: "+id);
			switch(type)
			{
				case 1:
					//attack skill
					command.setCommandInt(7);
					command.setSkillType(0);
					command.setSkillID(id);
					command.getTargets().push(getTarget(itself, player1, player2).getPlayerID());	
				break;
				case 2:
					//buff
					command.setCommandInt(7);
					command.setSkillType(1);
					command.setSkillID(id);
					command.getTargets().push(itself);
				break;
				case 3:
					//heal
					command.setCommandInt(7);
					command.setSkillType(2);
					command.setSkillID(id);
					command.getTargets().push(itself);
				break;
				case 4:
					//attack
					command.setCommandInt(0);
					command.setSkillType(0);
					command.setSkillID(0);
					command.getTargets().push(getTarget(itself, player1, player2).getPlayerID());	
				break;
				case 5:
					//defend
					command.setCommandInt(1);
					command.setSkillType(0);
					command.setSkillID(id);
					command.getTargets().push(itself);	
				break;
			}
 		return command;
	}
	
	/**
	 * Returns the type of an input AI memory attack
	 * @param chosenEffectiveness
	 * @return The type of the attack
	 */
	public int getType(String chosenEffectiveness)
	{
		String temp = chosenEffectiveness.substring(0, 1);
		int type = Integer.parseInt(temp);
		return type;
	}
	
	/**
	 * Returns the ID of an input AI memory attack
	 * @param chosenEffectiveness
	 * @return The ID
	 */
	public int getID(String chosenEffectiveness)
	{
		String temp = "0";
		if(chosenEffectiveness.length() > 2)
		{
			 temp = chosenEffectiveness.substring(1, 3);
			
		}
		else
		{
			 temp = chosenEffectiveness.substring(1, 2);
		}
		int type = Integer.parseInt(temp);
		System.out.println("ID is: "+type);
		return type;
	}
	
	/**
	 * Calculates the probability that the AI should heal itself
	 * @param itself The AI's ID
	 * @param player1
	 * @param player2
	 */
	public void healingProb(int itself,Player player1,Player player2)
	{
		//0 cannot heal, 1 normal heal chance, 2 likely heal chance, 3 heal straight away	
		switch (itself)
		{
			
			case 101:
				healprob = 0;
				break;
			case 102:
				healprob = 0;
				break;
			case 103:
				if ((getItself(itself, player1, player2).getActualMP())< 40)
				{
					healprob = 0;
					break;
				}
				else if ((getItself(itself, player1, player2).getActualHP()) <= 70 && getItself(itself, player1, player2).getActualMP()< 40)
				{
					healprob = 2;
					commandProb[21] = "31";
					commandProb[22] = "31";
					commandProb[23] = "31";
					break;
				}
				else if ( getItself(itself, player1, player2).getActualMP()> 40)
				{
					healprob = 1;
					commandProb[21] = "31";
					break;
				}
				break;
			case 104:
				if ((getItself(itself, player1, player2).getActualMP())< 55)
				{
					healprob = 0;
					break;
				}
				else if ((getItself(itself, player1, player2).getActualHP()) <= 130 &&getItself(itself, player1, player2).getActualMP()>= 55)
				{
					healprob = 2;
					commandProb[21] = "31";
					commandProb[22] = "31";
					commandProb[23] = "31";
					break;
				}
				else if (getItself(itself, player1, player2).getActualMP()> 55)
				{
					healprob = 1;
					commandProb[21] = "31";
					break;
				}
				break;
			case 105:
				if ((getItself(itself, player1, player2).getActualMP())< 55 )
				{
					healprob = 0;
					break;
				}
				else if ((getItself(itself, player1, player2).getActualHP()) <= 200 && getItself(itself, player1, player2).getActualMP()>= 55)
				{
					healprob = 3;
					break;
				}
				else
				{
					healprob = 0;
					
				}
				
				break;
		}
		
	}
	
	
	
	/**
	 * The probability the AI should use a buff skill
	 * @param itself The AI's ID
	 * @param player1
	 * @param player2
	 */
	public void buffprob(int itself,Player player1,Player player2)
	{
		//0 cannot buff, 1 low buff chance, 2 buff self! , 3 dispel self!, 4 dispel opponent!
		switch (itself)
		{
			case 101:
				if ((getItself(itself, player1, player2).getActualMP())< 10)
				{
					buffprob = 0;
					commandProb[(ai0atk.length+1)*3] = "0";
					commandProb[(ai0atk.length+1)*3+1] = "0";
					commandProb[(ai0atk.length+1)*3+2] = "0";
					break;
				}
				else if((getItself(itself, player1, player2).getPowerMulti())== 0
							&& getItself(itself, player1, player2).getActualMP()>= 10)
				{
					buffprob = 2;
					commandProb[(ai0atk.length+1)*3] = "20";
					commandProb[(ai0atk.length+1)*3+1] = "20";
					commandProb[(ai0atk.length+1)*3+2] = "20";
					break;
				}
				else if (getItself(itself, player1, player2).getActualMP()> 10)
				{
					buffprob =1;
					commandProb[(ai0atk.length+1)*3] = "20";
					commandProb[(ai0atk.length+1)*3+1] = "0";
					commandProb[(ai0atk.length+1)*3+2] = "0";
					break;
				}
				break;
			case 102:
				if ((getItself(itself, player1, player2).getActualMP())< 10)
				{
					commandProb[(ai1atk.length+1)*3] = "0";
					commandProb[(ai1atk.length+1)*3+1] = "0";
					commandProb[(ai1atk.length+1)*3+2] = "0";
					break;
					
				}
				else if((getItself(itself, player1, player2).getDefenseMulti())== 0 && getItself(itself, player1, player2).getActualMP()>= 30||
						(getItself(itself, player1, player2).getPowerMulti())== 0 && getItself(itself, player1, player2).getActualMP()>= 30)
				{
					buffprob = 3;
					break;
				
				}
				else if ((getTarget(itself, player1, player2).getDefenseMulti())== 6 && getItself(itself, player1, player2).getActualMP()>= 30||
						(getTarget(itself, player1, player2).getDefenseMulti())== 6 && getItself(itself, player1, player2).getActualMP()>= 30)
				{
					buffprob =4;
					break;
				}
				else if(getItself(itself, player1, player2).getActualMP()> 10)
				{
					buffprob =1;
					commandProb[(ai1atk.length+1)*3] = "21";
					break;
				}
				break;
			case 103:
				if ((getItself(itself, player1, player2).getActualMP())< 10)
				{
					buffprob = 0;
					break;
				}
				else if((getItself(itself, player1, player2).getPowerMulti())== 0 && getItself(itself, player1, player2).getActualMP()>= 10)
				{
					buffprob = 2;
					break;
				}
				else if (getItself(itself, player1, player2).getActualMP()>= 10)
				{
					buffprob =1;
					commandProb[(ai2atk.length+1)*3] = "21";
					break;
				}
				break;
			case 104:
				if ((getItself(itself, player1, player2).getActualMP())< 10)
				{
					buffprob = 0;
				}
				else if((getItself(itself, player1, player2).getDefenseMulti())== 0 && getItself(itself, player1, player2).getActualMP()>= 30||
						(getItself(itself, player1, player2).getPowerMulti())== 0 && getItself(itself, player1, player2).getActualMP()>= 30)
				{
					buffprob = 3;
				}
				else if ((getTarget(itself, player1, player2).getDefenseMulti())== 6 && getItself(itself, player1, player2).getActualMP()>= 30||
						(getTarget(itself, player1, player2).getDefenseMulti())== 6 && getItself(itself, player1, player2).getActualMP()>= 30)
				{
					buffprob =4;
				}
				else if (getItself(itself, player1, player2).getActualMP()>= 10)
				{
					buffprob =1;
					commandProb[(ai3atk.length+1)*3] = "20";
					commandProb[(ai3atk.length+1)*3+1] = "20";
					commandProb[(ai3atk.length+1)*3+2] = "20";
				}
				break;
			case 105:
				if ((getItself(itself, player1, player2).getActualMP())< 30)
				{
					buffprob = 0;

				}
				else if((getItself(itself, player1, player2).getDefenseMulti())== 0 && getItself(itself, player1, player2).getActualMP()>= 30||
						(getItself(itself, player1, player2).getPowerMulti())== 0 && getItself(itself, player1, player2).getActualMP()>= 30)
				{
					buffprob = 3;
				}
				else if ((getTarget(itself, player1, player2).getDefenseMulti())== 6 && getItself(itself, player1, player2).getActualMP()>= 30||
						(getTarget(itself, player1, player2).getDefenseMulti())== 6 && getItself(itself, player1, player2).getActualMP()>= 30)
				{
					buffprob =4;
				}
				else
				{
					buffprob =0;

				}
				break;
		}
	}
	
	/**
	 * Calculates the probability the AI should attack
	 * @param itself The AI's ID
	 * @param player1
	 * @param player2
	 */
	public void attackProb(int itself,Player player1,Player player2)
	{
		//for each attack
		//look into memory
		//
		//System.out.println("inside attack prob");
		switch (itself)
		{
	
			case 101:
				System.out.println("case 101");
				for(int i = 0; i < ai0atk.length; i++)
				{
					boolean foundID = false;
					int count = 0;
					//System.out.println("memory size: "+memory.size() +" count is: "+count);
				//	System.out.println("is empty "+ memory.isEmpty());
					//memory is not empty
					if (!memory.isEmpty())
					{
						//found = true or count > size then terminate
						while (foundID == false && count < memory.size())
						{
							//System.out.println("memory size: "+memory.size() +" count is: "+count);
							//if memory node id  = ai atk id
							if(memory.get(count).getSkillID()== ai0atk[i])
							{
								//found it
								//if effectiveness = 0 then don't change
								//if not 0 then change commandProb
								foundID = true;
								if(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai0atk[i]).getMP())
								{
									commandProb[3*i]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+1]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+2]= "1"+memory.get(count).getSkillID();
								}
							}
							//did not find attack inside memory then 
							//check can cast ability
							//yes = yes change
							//if no do nothing
							count++;
							if (count == memory.size())
							{
								if (getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai0atk[i]).getMP())
								{
									commandProb[3*i]= "1"+ai0atk[i];
									commandProb[3*i+1]= "1"+ai0atk[i];
									commandProb[3*i+2]= "1"+ai0atk[i];
								}
							}
						} 
					}
					else
					{
						if (getItself(itself, player1, player2).getActualMP()
									>=	skillsList.DamageSkillList.get(ai0atk[i]).getMP())
						{
							commandProb[3*i]= "1"+ai0atk[i];
							commandProb[3*i+1]= "1"+ai0atk[i];
							commandProb[3*i+2]= "1"+ai0atk[i];
						}
					}
					//System.out.println("outside while loop");
					
				}
				break;
			case 102:
				for(int i = 0; i < ai1atk.length; i++)
				{
					boolean foundID = false;
					int count =0;
					if (!memory.isEmpty())
					{
						while (foundID == false && count < memory.size())
						{
							if(memory.get(count).getSkillID()== ai1atk[i])
							{
								foundID = true;
								//System.out.println(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
								//		>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP());
								if(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP())
								{
									commandProb[3*i]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+1]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+2]= "1"+memory.get(count).getSkillID();
								}
							}
							count++;
							if (count == memory.size())
							{
								if (getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP())
								{
									commandProb[3*i]= "1"+ai1atk[i];
									commandProb[3*i+1]= "1"+ai1atk[i];
									commandProb[3*i+2]= "1"+ai1atk[i];
								}
							}
						} 
					}
					else
					{
						if (getItself(itself, player1, player2).getActualMP()
									>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP())
						{
							commandProb[3*i]= "1"+ai1atk[i];
							commandProb[3*i+1]= "1"+ai1atk[i];
							commandProb[3*i+2]= "1"+ai1atk[i];
						}
					}
					/*while (foundID ==false && count <= memory.size())
					{
						if(memory.get(count).getSkillID()== ai1atk[i])
						{
							foundID = true;
							if(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
									>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP())
							{
								commandProb[3*i]= "1"+memory.get(count).getSkillID();
								commandProb[3*i+1]= "1"+memory.get(count).getSkillID();
								commandProb[3*i+2]= "1"+memory.get(count).getSkillID();
							}
						}
						count++;
					}*/
				}
				break;
			case 103:
				//ghost aeon
				for(int i = 0; i < ai2atk.length; i++)
				{
					boolean foundID = false;
					int count =0;
					if (!memory.isEmpty())
					{
						while (foundID == false && count < memory.size())
						{
							if(memory.get(count).getSkillID()== ai2atk[i])
							{
								foundID = true;
								//System.out.println(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
								//		>=	skillsList.DamageSkillList.get(ai2atk[i]).getMP());
								if(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai2atk[i]).getMP())
								{
									commandProb[3*i]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+1]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+2]= "1"+memory.get(count).getSkillID();
								}
							}
							count++;
							if (count == memory.size())
							{
								if (getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP())
								{
									commandProb[3*i]= "1"+ai2atk[i];
									commandProb[3*i+1]= "1"+ai2atk[i];
									commandProb[3*i+2]= "1"+ai2atk[i];
								}
							}
						} 
					}
					else
					{
						if (getItself(itself, player1, player2).getActualMP()
									>=	skillsList.DamageSkillList.get(ai2atk[i]).getMP())
						{
							commandProb[3*i]= "1"+ai2atk[i];
							commandProb[3*i+1]= "1"+ai2atk[i];
							commandProb[3*i+2]= "1"+ai2atk[i];
						}
					}
				}
				break;
			case 104:
				//maverick
				for(int i = 0; i < ai3atk.length; i++)
				{
					boolean foundID = false;
					int count =0;
					if (!memory.isEmpty())
					{
						while (foundID == false && count < memory.size())
						{
							if(memory.get(count).getSkillID()== ai3atk[i])
							{
								foundID = true;
								//System.out.println(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
								//		>=	skillsList.DamageSkillList.get(ai3atk[i]).getMP());
								if(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai3atk[i]).getMP())
								{
									commandProb[3*i]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+1]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+2]= "1"+memory.get(count).getSkillID();
								}
							}
							count++;
							if (count == memory.size())
							{
								if (getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP())
								{
									commandProb[3*i]= "1"+ai3atk[i];
									commandProb[3*i+1]= "1"+ai3atk[i];
									commandProb[3*i+2]= "1"+ai3atk[i];
								}
							}
						} 
					}
					else
					{
						if (getItself(itself, player1, player2).getActualMP()
									>=	skillsList.DamageSkillList.get(ai3atk[i]).getMP())
						{
							commandProb[3*i]= "1"+ai3atk[i];
							commandProb[3*i+1]= "1"+ai3atk[i];
							commandProb[3*i+2]= "1"+ai3atk[i];
						}
					}
				}
				break;
			case 105:
				//almighty
				for(int i = 0; i < ai4atk.length; i++)
				{
					boolean foundID = false;
					int count =0;
					if (!memory.isEmpty())
					{
						while (foundID == false && count < memory.size())
						{
							if(memory.get(count).getSkillID()== ai4atk[i])
							{
								foundID = true;
								//System.out.println(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
								//		>=	skillsList.DamageSkillList.get(ai4atk[i]).getMP());
								if(memory.get(count).getEffectiveness()!= 0 && getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai4atk[i]).getMP())
								{
									commandProb[3*i]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+1]= "1"+memory.get(count).getSkillID();
									commandProb[3*i+2]= "1"+memory.get(count).getSkillID();
								}
							}
							count++;
							if (count == memory.size())
							{
								if (getItself(itself, player1, player2).getActualMP()
										>=	skillsList.DamageSkillList.get(ai1atk[i]).getMP())
								{
									commandProb[3*i]= "1"+ai4atk[i];
									commandProb[3*i+1]= "1"+ai4atk[i];
									commandProb[3*i+2]= "1"+ai4atk[i];
								}
							}
						} 
					}
					else
					{
						if (getItself(itself, player1, player2).getActualMP()
									>=	skillsList.DamageSkillList.get(ai4atk[i]).getMP())
						{
							commandProb[3*i]= "1"+ai4atk[i];
							commandProb[3*i+1]= "1"+ai4atk[i];
							commandProb[3*i+2]= "1"+ai4atk[i];
						}
					}
				}
				break;
		}

	}
	
	/**
	 * Resets the probabilities calculated
	 */
	public void resetCommandProb()
	{
		for (int i = 0; i< commandProb.length; i++)
		{
			commandProb[i]= "0";
			
		}
		//type for defend and attack
		//attack = 4
		//defend = 5
		commandProb[21] ="41";
		commandProb[22] ="41";
		commandProb[23] ="41";
		commandProb[24] ="51";
		commandProb[25] ="51";
		commandProb[26] ="51";
	}
	
	/**
	 * Resets the probabilities for healing and buffing
	 */
	public void resetProb()
	{
		this.healprob = 1;
		this.buffprob =1;
		
	}
}
