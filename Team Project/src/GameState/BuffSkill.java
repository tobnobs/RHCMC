package GameState;

import java.io.Serializable;

/**
 * @author Zhuo zll308
 * Class for Buffs. (Skills to improve attributes)
 */
public class BuffSkill extends Skill implements Serializable {
	// Stat Affected
	// 0 for Power
	// 1 for Defense
	// 2 for Speed
	// 3 for Dispel
	private int statAffected;
	private boolean statUp;

	/**
	 * Constructor
	 * 
	 * @param skillName
	 *            Name of the skill
	 * @param MPCost
	 *            The mana cost of the skill
	 * @param skillID
	 *            The ID of the skill
	 * @param buffTarget
	 *            The target player for the skill
	 * @param statAffected
	 *            The attributes affected by the skill
	 * @param statUp
	 *            Whether the buff has been applied to the attribute
	 * @param flavourText
	 */
	public BuffSkill(String skillName, int MPCost, int skillID,
			Targets buffTarget, int statAffected, boolean statUp,
			String flavourText) {
		this.setSkillName(skillName);
		this.setMP(MPCost);
		this.setSkillType(1);
		this.setSkillID(skillID);
		this.setTarget(buffTarget);
		this.setFlavourText(flavourText);
		this.statAffected = statAffected;
		this.statUp = statUp;

	}

	/**
	 * Returns the int representing the stat affected
	 * 
	 * @return int representing stat affected
	 */
	public int getstatAffected() {
		return this.statAffected;
	}

	/**
	 * Changes the attribute affected by the skill
	 * 
	 * @param statAffected
	 *            The new attribute affected
	 */
	public void setStatAffected(int statAffected) {
		this.statAffected = statAffected;
	}

	/**
	 * Returns whether the buff has been applied to the attribute
	 * 
	 * @return Whether the buff has been applied to the attribute
	 */
	public boolean isStatUp() {
		return statUp;
	}

	/**
	 * Sets whether the buff has been applied to the attribute
	 * 
	 * @param statUp
	 *            Whether the buff has been applied to the attribute
	 */
	public void setStatUp(boolean statUp) {
		this.statUp = statUp;
	}
}
