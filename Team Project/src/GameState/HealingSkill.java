package GameState;

import java.io.Serializable;

/**
 * @author Zhuo
 * Class for healing skills
 */
public class HealingSkill extends Skill implements Serializable {
	private int HealingPower;
	
	/**
	 * Constructor
	 * @param skillName The name of the skill
	 * @param MPCost The mana cost
	 * @param skillID The skill ID
	 * @param healingTarget The target player
	 * @param healingPower The amount of HP which can be healed
	 */
	public HealingSkill(String skillName, int MPCost, int skillID,Targets healingTarget, int healingPower, String flavourText) {
		this.setSkillName(skillName);
		this.setMP(MPCost);
		this.setSkillType(2);
		this.setSkillID(skillID);
		this.setTarget(healingTarget);
		this.setFlavourText(flavourText);
		this.HealingPower = healingPower;
	} 
	
	/**
	 * Returns the healing power
	 * @return The healing power
	 */
	public int getHealingPower()
	{
		return this.HealingPower;
	}

	/**
	 * Changes the healing power
	 * @param healingPower The new value for the healing power
	 */
	public void setHealingPower(int healingPower)
	{
		this.HealingPower = healingPower;
	}
}
