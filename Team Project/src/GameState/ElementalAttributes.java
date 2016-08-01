package GameState;

import java.io.Serializable;

/**
 * @author Zhuo
 * Class for the elemental skills of players.
 *
 */
public class ElementalAttributes implements Serializable{
	private int void_;
	private int fire;
	private int water;
	private int earth;
	private int air;

	/**
	 * Constructor. The multiplier for how much damage the elemental attribute
	 * will do
	 * 
	 * @param void_
	 *            The void damage multiplier
	 * @param fire
	 *            The fire damage multiplier
	 * @param water
	 *            The water damage multiplier
	 * @param earth
	 *            The earth damage multiplier
	 * @param air
	 *            The air damage multiplier
	 */
	public ElementalAttributes(int void_, int fire, int water, int earth,
			int air) {
		this.void_ = void_;
		this.fire = fire;
		this.water = water;
		this.earth = earth;
		this.air = air;
	}

	// Set methods for the Elemental Attributes
	/**
	 * Sets the value for the void damage multiplier
	 * 
	 * @param x
	 *            The void damage multiplier
	 */
	public void setVoid(int x) {
		this.void_ = x;
	}

	/**
	 * Sets the value for the fire damage multiplier
	 * 
	 * @param x
	 *            The fire damage multiplier
	 */
	public void setFire(int x) {
		this.fire = x;
	}

	/**
	 * Sets the value for the water damage multiplier
	 * 
	 * @param x
	 *            The water damage multiplier
	 */
	public void setWater(int x) {
		this.water = x;
	}

	/**
	 * Sets the value for the earth damage multiplier
	 * 
	 * @param x
	 *            The earth damage multiplier
	 */
	public void setEarth(int x) {
		this.earth = x;
	}

	/**
	 * Sets the value for the air damage multiplier
	 * 
	 * @param x
	 *            The air damage multiplier
	 */
	public void setAir(int x) {
		this.air = x;
	}

	// Get methods for the Elemental Attributes
	/**
	 * Returns the void damage multiplier
	 * 
	 * @return The void damage multiplier
	 */
	public int getVoid() {
		return this.void_;
	}

	/**
	 * Returns the fire damage multiplier
	 * 
	 * @return The fire damage multiplier
	 */
	public int getFire() {
		return this.fire;
	}

	/**
	 * Returns the water damage multiplier
	 * 
	 * @return The water damage multiplier
	 */
	public int getWater() {
		return this.water;
	}

	/**
	 * Returns the earth damage multiplier
	 * 
	 * @return The earth damage multiplier
	 */
	public int getEarth() {
		return this.earth;
	}

	/**
	 * Returns the air damage multiplier
	 * 
	 * @return The air damage multiplier
	 */
	public int getAir() {
		return this.air;
	}
	public int getEABonus(){
		return (getAir() + getEarth() + getWater() + getFire() + getVoid()) - 5;
	}
}
