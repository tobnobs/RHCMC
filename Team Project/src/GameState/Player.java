package GameState;

import java.io.Serializable;

// TODO add speed
/**
 * Class to hold all information about the player when in combat
 * 
 * @author Zhuo Lee, Toby Sullivan, Tim Ryan, Liam Howe
 *
 */
public class Player implements Serializable {
	private String name;
	private ElementalAttributes ea;
	private double baseHP;
	private double baseMP;
	private double maxHP;
	private double maxMP;
	private double actualHP;
	private double actualMP;
	private double basePower;
	private double baseDefence;
	private double baseSpeed;
	private int powerMulti;
	private int defenseMulti;
	private int speedMulti;
	private double totalBonusPoints;
	private double bonusPointsLeft;
	private int Team;
	private int playerID;
	private boolean guarding;
	private boolean dead;
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private boolean inCave;
	private boolean switchMap;
	private boolean facingLeft;
	private boolean inCombat = false;
	public boolean playersUpdated = false;

	/**
	 * Returns true if the player is in combat
	 * 
	 * @return Whether the player is in combat
	 */
	public boolean isInCombat() {
		return inCombat;
	}

	/**
	 * Set to true when the player is in combat
	 * 
	 * @param inCombat
	 *            Whether the player is in combat
	 */
	public void setInCombat(boolean inCombat) {
		this.inCombat = inCombat;
	}

	public boolean ready = false;

	/**
	 * Toggles whether the player is in combat
	 */
	public void toggleCombat() {
		if (!inCombat)
			inCombat = true;
		else
			inCombat = false;
	}

	private Skill[] skillSet = new Skill[6];

	/**
	 * The constructor to create a new Player
	 * 
	 * @param name
	 *            The player's name
	 * @param ea
	 *            The player's elementall attributes
	 * @param HP
	 *            The player's health points
	 * @param MP
	 *            The player's mana points
	 * @param totalbonusPoints
	 *            The amount of bonus points the player has
	 * @param bonusPointsLeft
	 *            The amount of bonus points the player has left
	 * @param power
	 *            The power of the player's attacks
	 * @param defence
	 *            The strength of the player's defence
	 * @param speed
	 *            The player's speed
	 * @param skillSet
	 *            The player's skill set
	 * @param Team
	 *            The player's team
	 * @param PlayerID
	 *            The player's ID
	 * @param x
	 *            The player's character's x-coordinate on the map
	 * @param y
	 *            The player's character's y-coordinate on the map
	 */
	public Player(String name, ElementalAttributes ea, double HP, double MP,
			double totalbonusPoints, double bonusPointsLeft, double power,
			double defence, double speed, Skill[] skillSet, int Team,
			int PlayerID, int x, int y) {
		this.name = name;
		this.ea = ea;
		this.baseHP = HP;
		this.baseMP = MP;
		this.maxHP = baseHP * 3;
		this.maxMP = baseMP * 2;
		this.actualHP = maxHP;
		this.actualMP = maxMP;
		this.basePower = power;
		this.baseDefence = defence;
		this.baseSpeed = speed;
		this.powerMulti = 3;
		this.defenseMulti = 3;
		this.speedMulti = 3;
		this.totalBonusPoints = totalbonusPoints;
		this.bonusPointsLeft = bonusPointsLeft;
		this.skillSet = skillSet;
		this.Team = Team;
		this.playerID = PlayerID;
		this.guarding = false;
		this.dead = false;
		this.inCave = false;
		this.switchMap = false;
		this.facingLeft = false;
		this.x = x;
		this.y = y;

	}

	/**
	 * Returns the player's name
	 * 
	 * @return The player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Resets the player's name to a new one
	 * 
	 * @param name
	 *            The new name
	 */
	public void setName(String name) {
		if (name.length() > 10) {
			this.name = name.substring(0, 10);
		} else {
			this.name = name;
		}
	}

	/**
	 * Returns the elemental attributes
	 * 
	 * @return the elemental attributes
	 */
	public ElementalAttributes getEA() {
		return ea;
	}

	/**
	 * Resets the elemental attributes to new values
	 * 
	 * @param ea
	 *            The new elemental attributes
	 */
	public void setEa(ElementalAttributes ea) {
		this.ea = ea;
	}

	/**
	 * Returns the base health points
	 * 
	 * @return The base health points
	 */
	public double getBaseHP() {
		return baseHP;
	}

	/**
	 * Resets the base health points to a new value
	 * 
	 * @param baseHP
	 *            The new base health points value
	 */
	public void setBaseHP(double baseHP) {
		this.baseHP = baseHP;
		this.maxHP = baseHP * 3;
	}

	/**
	 * Returns the base mana points
	 * 
	 * @return The base mana points
	 */
	public double getBaseMP() {
		return baseMP;
	}

	/**
	 * Resets the base mana points to a new value
	 * 
	 * @param baseMP
	 *            The new value for the base mana points
	 */
	public void setBaseMP(double baseMP) {
		this.baseMP = baseMP;
		this.maxMP = baseMP * 1.6;
	}

	/**
	 * Returns the base power
	 * 
	 * @return The base power
	 */
	public double getBasePower() {
		return basePower;
	}

	/**
	 * Resets the base power value of the player
	 * 
	 * @param basePower
	 *            The new base power value
	 */
	public void setBasePower(double basePower) {
		this.basePower = basePower;
	}

	/**
	 * Returns the base defence of the player
	 * 
	 * @return The base defence
	 */
	public double getBaseDefence() {
		return baseDefence;
	}

	/**
	 * Resets the base defence to a new value
	 * 
	 * @param baseDefence
	 *            The new base defence value
	 */
	public void setBaseDefence(double baseDefence) {
		this.baseDefence = baseDefence;
	}

	/**
	 * Returns the total amount of bonus points for attributes
	 * 
	 * @return The total amount of bonus points
	 */
	public double getTotalBonusPoints() {
		return totalBonusPoints;
	}

	/**
	 * Resets the amount of total bonus points to a new value
	 * 
	 * @param totalBonusPoints
	 *            The new value for the amount of bonus points
	 */
	public void setTotalBonusPoints(double totalBonusPoints) {
		if (totalBonusPoints < 0) {
			this.totalBonusPoints = 0;
		} else {
			this.totalBonusPoints = totalBonusPoints;
		}
	}

	/**
	 * Returns the amount of bonus points yet to be assigned
	 * 
	 * @return The amount of spare bonus points
	 */
	public double getBonusPointsLeft() {
		return bonusPointsLeft;
	}

	/**
	 * Resets the amount of bonus points left to a new value
	 * 
	 * @param bonusPointsLeft
	 *            The new value for the amount of bonus points left
	 */
	public void setBonusPointsLeft(double bonusPointsLeft) {
		if (bonusPointsLeft < 0) {
			this.bonusPointsLeft = 0;
		} else {
			this.bonusPointsLeft = bonusPointsLeft;
		}
	}

	/**
	 * Returns the maximum amount of health points
	 * 
	 * @return The maximum health points
	 */
	public double getMaxHP() {
		return maxHP;
	}

	/**
	 * Returns the maximum amount of mana points
	 * 
	 * @return The maximum mana points
	 */
	public double getMaxMP() {
		return maxMP;
	}

	/**
	 * Returns the amount of health points the player currently has
	 * 
	 * @return The amount of health points the player currently has
	 */
	public double getActualHP() {
		return actualHP;
	}

	/**
	 * Resets the amount of health points the player currently has
	 * 
	 * @param actualHP
	 *            The new amount of health points
	 */
	public void setActualHP(double actualHP) {
		this.actualHP = actualHP;
	}

	/**
	 * Returns the amount of mana points the player currently has
	 * 
	 * @return The amount of mana points the player currently has
	 */
	public double getActualMP() {
		return this.actualMP;
	}

	/**
	 * Resets the amount of mana points the player currently has
	 * 
	 * @param actualMP
	 *            The new amount of mana points
	 */
	public void setActualMP(double actualMP) {
		this.actualMP = actualMP;
	}

	/**
	 * Resets the power multiplier for the amount of damage the player can do to
	 * a new value
	 * 
	 * @param newPowerMulti
	 *            The new power multiplier
	 */
	public void setPowerMulti(int newPowerMulti) {
		this.powerMulti = newPowerMulti;
	}

	/**
	 * Resets the defence multiplier for the amount of damage the player can
	 * block to a new value
	 * 
	 * @param newDefenseMulti
	 *            The defence multiplier
	 */
	public void setDefenseMulti(int newDefenseMulti) {
		this.defenseMulti = newDefenseMulti;
	}

	/**
	 * Returns the power multiplier for the amount of damage the player can do
	 * 
	 * @return The power multiplier
	 */
	public int getPowerMulti() {
		return this.powerMulti;
	}

	/**
	 * Returns the defence multiplier for the amount of damage the player can
	 * block
	 * 
	 * @return The defence multiplier
	 */
	public int getDefenseMulti() {
		return this.defenseMulti;
	}

	/**
	 * Returns the list of skills the player can use
	 * 
	 * @return The player's list of skills
	 */
	public Skill[] getSkillSet() {
		return skillSet;
	}

	/**
	 * Resets the list of skills the player can use
	 * 
	 * @param skillSet
	 *            The new list of skills the player can use
	 */
	public void setSkillSet(Skill[] skillSet) {
		this.skillSet = skillSet;
	}

	/**
	 * Returns the player's team
	 * 
	 * @return The player's team
	 */
	public int getTeam() {
		return this.Team;
	}

	/**
	 * Sets the player to a team
	 * 
	 * @param newTeam
	 *            The team the player will be in
	 */
	public void setTeam(int newTeam) {
		this.Team = newTeam;
	}

	/**
	 * Returns the player's ID
	 * 
	 * @return The player's ID
	 */
	public int getPlayerID() {
		return this.playerID;
	}

	/**
	 * Sets the player's ID to a value
	 * 
	 * @param playerID
	 *            The ID value for the player
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	/**
	 * Returns whether the player is guarding
	 * 
	 * @return Whether the player is guarding
	 */
	public boolean getGuarding() {
		return this.guarding;
	}

	/**
	 * Sets whether the player is guarding
	 * 
	 * @param guard
	 *            The value for if the player is guarding
	 */
	public void setGuarding(Boolean guard) {
		this.guarding = guard;
	}

	/**
	 * Return plaer's base speed
	 * 
	 * @return baseSpeed player's base speed
	 */
	public double getBaseSpeed() {
		return baseSpeed;
	}

	/**
	 * Sets the player's base speed
	 * 
	 * @param baseSpeed
	 *            player's base speed
	 */
	public void setBaseSpeed(double baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	/**
	 * Return the player's speed multiplier
	 * 
	 * @return speedMulti player's speed multiplier
	 */
	public int getSpeedMulti() {
		return speedMulti;
	}

	/**
	 * Sets the player's speed multiplier
	 * 
	 * @param speedMulti
	 *            player's Speed multiplier
	 */
	public void setSpeedMulti(int speedMulti) {
		this.speedMulti = speedMulti;
	}

	/**
	 * return if player is dead
	 * 
	 * @return dead death status of player
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * change death status of player
	 * 
	 * @param dead
	 *            death status of player
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * Resets the combat related stats of the player
	 */
	public void reset() {
		System.out.println("Actual HP before " + actualHP);
		this.actualHP = maxHP;
		System.out.println("Actual HP after " + actualHP);
		this.actualMP = maxMP;
		this.powerMulti = 3;
		this.defenseMulti = 3;
		this.speedMulti = 3;
		this.guarding = false;
		this.dead = false;
	}

	/**
	 * Returns the x-coordinate for the player on the map
	 * 
	 * @return The player's x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate for the player on the map
	 * 
	 * @param x
	 *            The new x-coordinate
	 */
	public void setX(int newx) {
		if (!this.isInCave()) {
			if (newx < 0) {
				this.x = 0;
			} else if (newx > 965) {
				this.x = 965;
			} else if (y < 267 && y > 192 && x < 5 / 2) {
				this.setInCave(true);
				this.changeMap(true);
			} else {
				this.x = newx;
			}
		} else {
			this.x = newx;
		}
	}

	/**
	 * Returns the y-coordinate for the player on the map
	 * 
	 * @return The player's y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the player's y-coordinate on the map
	 * 
	 * @param y
	 */
	public void setY(int newy) {
		if (!this.isInCave()) {
			if (newy < 0) {
				this.y = 0;
			} else if (newy > 450) {
				this.y = 450;
			} else if (y < 267 && y > 192 && x < 5 / 2) {
				this.setInCave(true);
				this.changeMap(true);
			} else {
				this.y = newy;
			}
		} else {
			this.y = newy;
		}
	}

	/**
	 * Returns whether the player is in the cave
	 * 
	 * @return Whether the player is in the cave
	 */
	public boolean isInCave() {
		return inCave;
	}

	/**
	 * Set to true if the player's character is in the cave
	 * 
	 * @param inCave
	 *            Whether the player is in the cave
	 */
	public void setInCave(boolean inCave) {
		this.inCave = inCave;
	}

	/**
	 * Returns whether the player has switched map
	 * 
	 * @return Whether the player has switched map
	 */
	public boolean isSwitchMap() {
		return switchMap;
	}

	/**
	 * Set to true if the player has changed map
	 * 
	 * @param switchMap
	 *            Whether the player has changed map
	 */
	public void setSwitchMap(boolean switchMap) {
		this.switchMap = switchMap;
	}

	/**
	 * Returns whether the player is facing left
	 * 
	 * @return Whether the player is facing left
	 */
	public boolean isFacingLeft() {
		return facingLeft;
	}

	/**
	 * Sets whether the players character is facing left on the map
	 * 
	 * @param fl
	 *            Whether they are facing left
	 */
	public void setFacingLeft(boolean fl) {
		this.facingLeft = fl;
	}

	/**
	 * Returns whether the player is changing map, used to place the player in a
	 * particular start point in the new map
	 * 
	 * @return Whether the player is changing map
	 */
	public boolean switchMap() {
		return switchMap;
	}

	/**
	 * Set to true when the player is changing map
	 * 
	 * @param b
	 *            True if the player is changing map
	 */
	public void changeMap(boolean b) {
		switchMap = b;
	}

	/**
	 * Returns whether the player is facing left
	 * 
	 * @return Whether the player is facing left
	 */
	public boolean getFacingLeft() {
		return facingLeft;
	}

	/**
	 * Returns whether the player is ready to join a 2v2 combat with the other players
	 * @return Whether the player is ready to join a 2v2 combat with the other players
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * Sets whether the player is ready to join a 2v2 combat with the other players
	 * @param ready Whether the player is ready to join a 2v2 combat with the other players
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	/**
	 * Calculates and returns the value for the player's speed
	 * 
	 * @return The player's speed
	 */
	public int getActualSpeed() {
		double speedMultiplier = 1;
		// calculate power multiplier
		switch (speedMulti) {
		case 0:
			speedMultiplier = 0.1;
			break;
		case 1:
			speedMultiplier = 0.5;
			break;
		case 2:
			speedMultiplier = 0.75;
			break;
		case 3:
			speedMultiplier = 1;
			break;
		case 4:
			speedMultiplier = 1.5;
			break;
		case 5:
			speedMultiplier = 2;
			break;
		case 6:
			speedMultiplier = 3;
			break;
		}
		double actualSpeed = baseSpeed * speedMultiplier;
		return (int) actualSpeed;
	}

	/**
	 * Returns an int value defining where the player is on the map
	 * 
	 * @return The location ID int value
	 */
	public int getLocation() {
		if (y < 267 && y > 192 && x < 5 / 2) {
			// CAVE ENTRANCE
			return 6;
		} else if (x > 200 && !(x < 795 && y > 220 && y < 235)) {
			// ROAD
			return 5;
		} else if (x > 940 && y < 210 && y > 245) {
			// BOSS
			return 4;
		} else if (x > 740) {
			// THIRD LEVEL
			return 3;
		} else if (x > 485) {
			// SECOND LEVEL
			return 2;
		} else if (x > 250) {
			// FIRST LEVEL
			return 1;
		} else {
			// SAFE LEVEL
			return 0;
		}
	}
}