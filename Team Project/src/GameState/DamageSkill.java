package GameState;

import java.io.Serializable;

/**
 * @author Zhuo
 * Class for damaging skills.
 */
public class DamageSkill extends Skill implements Serializable {
	public enum ElementalType {
		VOID, FIRE, WATER, EARTH, AIR;
	}

	private int power;
	private int accuracy;
	private ElementalType type;

	/**
	 * Constructor
	 * 
	 * @param skillName
	 *            The name of the skill
	 * @param MPCost
	 *            The mana cost of the skill
	 * @param skillID
	 *            The ID of the skill
	 * @param target
	 *            The target player for the skill to be used on
	 * @param power
	 *            The amount of damage the skill can do
	 * @param accuracy
	 *            The likelihood the skill will hit
	 * @param type
	 *            The elemental type of the skill
	 */
	public DamageSkill(String skillName, int MPCost, int skillID,
			Targets target, int power, int accuracy, ElementalType type, String flavourText) {
		// super(name, MPcost);
		this.setSkillName(skillName);
		this.setMP(MPCost);
		this.setSkillType(0);
		this.setSkillID(skillID);
		this.setTarget(target);
		this.setFlavourText(flavourText);
		this.power = power;
		this.accuracy = accuracy;
		this.type = type;
		
	}

	/**
	 * Returns the amount of damage the skill can do
	 * 
	 * @return The amount of damage the skill can do
	 */
	public int getPower() {
		return this.power;
	}

	/**
	 * Returns the likelihood the skill will hit
	 * 
	 * @return The likelihood the skill will hit
	 */
	public int getAccuracy() {
		return this.accuracy;
	}

	/**
	 * Returns the elemental type of the skill
	 * 
	 * @return The elemental type of the skill
	 */
	public ElementalType getElementalType() {
		return this.type;
	}

	/**
	 * Changes the amount of damage the skill can do
	 * 
	 * @param power
	 *            The new power value
	 */
	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * Changes the accuracy value
	 * 
	 * @param accuracy
	 *            The new accuracy value
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * Changes the elemental type of the skill
	 * 
	 * @param type
	 *            The new elemental type
	 */
	public void setElementalType(ElementalType type) {
		this.type = type;
	}

}
