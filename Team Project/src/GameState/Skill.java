package GameState;

/**
 * @author Zhuo and Liam Howe
 * Abstract class to be a base for all skills
 */
public abstract class Skill {
	
	/**
	 * The target(s) of the skill; a single target or an entire team
	 */
	public enum Targets
	{
		SINGLE, TEAM;
	}
	
	private int MPCost;
	private int SkillType;			//0 = Damage, 1 = Buff, 2 = Healing
	private int SkillID;			//Unique ID for each skill
	private String SkillName;
	private Targets Target;
	private String FlavourText;
	
	/**
	 * Returns the mana cost of the skill
	 * @return The mana cost of the skill
	 */
	public int getMP ()
	{
		return this.MPCost;
	}
	
	/**
	 * Returns the type of the skill; damage, healing, buff.
	 * @return The type of the skill
	 */
	public int getSkilltype ()
	{
		return this.SkillType;
	}
	
	/**
	 * Returns the ID of the skill
	 * @return The skill ID
	 */
	public int getSKillID()
	{
		return this.SkillID;
	}
	
	/**
	 * Returns the name of the skill
	 * @return The skill's name
	 */
	public String getSkillName ()
	{
		return this.SkillName;
	}
	
	/**
	 * Returns the player the skill will be cast on
	 * @return The target of the skill
	 */
	public Targets getTarget()
	{
		return this.Target;
	}
	
	/**
	 * Sets the mana cost of the skill to an integer value
	 * @param mpCost The value for the mana cost
	 */
	public void setMP(int mpCost)
	{
		this.MPCost = mpCost;
	}
	
	/**
	 * Sets the type of the skill to an integer value
	 * @param skillType The value for the skill type
	 */
	public void setSkillType(int skillType)
	{
		this.SkillType = skillType;
	}
	
	/**
	 * Sets the ID of the skill to an integer input
	 * @param skillID The value for the skill ID
	 */
	public void setSkillID(int skillID)
	{
		this.SkillID = skillID;
	}
	
	/**
	 * Sets the name of the skill to a string input
	 * @param skillName The string for the skill name
	 */
	public void setSkillName(String skillName)
	{
		this.SkillName = skillName;
	}
	
	/**
	 * Sets the target of the skill
	 * @param target The target
	 */
	public void setTarget (Targets target)
	{
		this.Target = target;
	}

	/**
	 * Returns description of skill Flavour Text
	 * @return
	 */
	public String getFlavourText() {
		return FlavourText;
	}

	/**
	 * Sets the flavour text
	 * @param flavourText
	 */
	public void setFlavourText(String flavourText) {
		FlavourText = flavourText;
	}
}