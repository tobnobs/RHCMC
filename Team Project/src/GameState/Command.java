package GameState;

import java.io.Serializable;
import java.util.Stack;

/**
 * @author Zhuo
 * Class to hold all information needed for a command
 */
public class Command implements Serializable {

	/**
	 * The options for the type of command
	 */
	public enum PlayerCommand {
		ATTACK, DEFEND, SKILL0, SKILL1, SKILL2, SKILL3, SKILL4, SKILL5, RUN;
	}

	private int aggresor;
	private double speed;
	private int commandInt;
	private PlayerCommand command;
	private int skillID;
	private int skillType;
	private Stack<Integer> targets;
	private boolean MPdeducted;

	// 0 : attack 1: Defend
	// 2: run
	// 3: skill 0 4: skill 1 5 skill: 2
	// 6: skill 3 7: skill 4 8 skill: 5
	/**
	 * Constructor
	 * 
	 * @param aggresor
	 *            The command's player
	 * @param commandInt
	 *            The type of the command
	 * @param targets
	 *            The target(s) of the command
	 * @param speed
	 *            The speed of the command
	 * @param skillType
	 *            The type of skill used
	 * @param skillID
	 *            The ID of the skill used
	 */
	public Command(int aggresor, int commandInt, Stack<Integer> targets,
			double speed, int skillType, int skillID) {
		this.aggresor = aggresor;
		this.targets = targets;
		this.speed = speed;
		this.commandInt = commandInt;
		this.skillType = skillType;
		this.skillID = skillID;
		this.MPdeducted = false;

	}
	
	/**
	 * Method prints out details of command
	 * 
	 */
	public void printString()
	{
		System.out.println("Aggressor: "+this.aggresor+ "Targets: "+this.targets.toString());
		System.out.println("Speed: " + this.speed+ " commandInt:  "+this.commandInt +" skillType: "+this.skillType+ " skillID: "+ this.skillID + " MPDeducted: "
				+this.MPdeducted);
		
		
	}
	
	/**
	 * Returns a stack of targets for the command
	 * 
	 * @return
	 */
	public Stack<Integer> getTargets() {
		return targets;
	}

	/**
	 * Sets the target(s) of the command
	 * 
	 * @param targets
	 *            The target(s) of the command
	 */
	public void setTargets(Stack<Integer> targets) {
		this.targets = targets;
	}

	/**
	 * Returns the player carrying out the command
	 * 
	 * @return The command's player
	 */
	public int getAggresor() {
		return aggresor;
	}

	/**
	 * Sets the player carrying out the command
	 * 
	 * @param aggresor
	 *            The command's player
	 */
	public void setAggresor(int aggresor) {
		this.aggresor = aggresor;
	}

	/**
	 * Returns the action contained within the command
	 * 
	 * @return The action for the command
	 */
	public PlayerCommand getPlayerCommand() {
		return command;
	}

	/**
	 * Sets what action the command contains
	 * 
	 * @param command
	 *            The action for the command
	 */
	public void setPlayerCommand(PlayerCommand command) {
		this.command = command;
	}

	/**
	 * Returns the speed of the command
	 * 
	 * @return The speed of the command
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed of the command
	 * 
	 * @param speed
	 *            The speed of the command
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns the type of the command as an int value
	 * 
	 * @return The type of the command
	 */
	public int getCommandInt() {
		return commandInt;
	}

	/**
	 * Sets the type of the command as an int value
	 * 
	 * @param commandInt
	 *            The type of the command
	 */
	public void setCommandInt(int commandInt) {
		this.commandInt = commandInt;
	}

	/**
	 * Returns the ID of the skill in the command
	 * 
	 * @return The ID of the skill in the command
	 */
	public int getSkillID() {
		return skillID;
	}

	/**
	 * Sets the ID of the skill in the command
	 * 
	 * @param skillID
	 *            The ID of the skill in the command
	 */
	public void setSkillID(int skillID) {
		this.skillID = skillID;
	}

	/**
	 * Returns the type of the skill in the command
	 * 
	 * @return The type of the skill in the command
	 */
	public int getSkillType() {
		return skillType;
	}

	/**
	 * Sets the type of the skill in the command
	 * 
	 * @param skillType
	 *            The type of skill
	 */
	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}

	/**
	 * Returns whether the mana cost has been deducted
	 * 
	 * @return Whether the mana cost has been deducted
	 */
	public boolean isMPdeducted() {
		return MPdeducted;
	}

	/**
	 * Sets the amount of mana to be deducted
	 * 
	 * @param mPdeducted
	 *            Whether mana has been deducted
	 */
	public void setMPdeducted(boolean mPdeducted) {
		MPdeducted = mPdeducted;
	}
	
}
