package GameState;

/**
 * 
 * @author Zhuo Lee
 * 
 *         Class to hold information about the effectiveness of previous
 *         commands for use by the AI opponents
 *
 */
public class AIMemory {

	public int aggresor;
	public int targetID;
	public int effectiveness;
	public int skillType;
	public int skillID;

	/**
	 * Creates a new piece of AI memory for a particular command
	 * 
	 * @param aggresor
	 *            The command's player
	 * @param targetID
	 *            The target
	 * @param damagedone
	 *            The amount of damage dealt
	 * @param SkillType
	 *            The skill type used
	 * @param SkillID
	 *            The skill ID
	 */
	AIMemory(int aggresor, int targetID, double damagedone, int SkillType,
			int SkillID) {
		// measure damage done
		// create effectiveness
		if (damagedone <= 0) {
			this.effectiveness = 0;
		} else {
			this.effectiveness = 1;
		}
		this.aggresor = aggresor;
		this.targetID = targetID;
		this.skillType = SkillType;
		this.skillID = SkillID;
	}

	/**
	 * Returns the command's player
	 * 
	 * @return
	 */
	public int getAggresor() {
		return aggresor;
	}

	/**
	 * Sets the command's player
	 * 
	 * @param aggresor
	 */
	public void setAggresor(int aggresor) {
		this.aggresor = aggresor;
	}

	/**
	 * Returns the target(s) of the attack
	 * 
	 * @return
	 */
	public int getTargetID() {
		return targetID;
	}

	/**
	 * Sets the target(s) of the attack
	 * 
	 * @param targetID
	 */
	public void setTargetID(int targetID) {
		this.targetID = targetID;
	}

	/**
	 * Returns the effectiveness of the attack (whether the amount of damage it
	 * dealt was greater than 0)
	 * 
	 * @return
	 */
	public int getEffectiveness() {
		return effectiveness;
	}

	/**
	 * Sets the effectiveness of the attack
	 * 
	 * @param effectiveness
	 */
	public void setEffectiveness(int effectiveness) {
		this.effectiveness = effectiveness;
	}

	/**
	 * Returns the type of the skill
	 * 
	 * @return
	 */
	public int getSkillType() {
		return skillType;
	}

	/**
	 * Sets the skill type
	 * 
	 * @param skillType
	 */
	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}

	/**
	 * Returns the ID of the skill
	 * 
	 * @return
	 */
	public int getSkillID() {
		return skillID;
	}

	/**
	 * Sets the ID of the skill
	 * 
	 * @param skillID
	 */
	public void setSkillID(int skillID) {
		this.skillID = skillID;
	}

	/**
	 * Prints all the stored information in the console
	 */
	public void printString() {
		System.out.println("aggresor " + this.aggresor + " effectiveness "
				+ this.effectiveness);
		System.out.println("targetID: " + this.targetID + " skillID: "
				+ this.skillID + " skillType: " + this.skillType);
	}
}
