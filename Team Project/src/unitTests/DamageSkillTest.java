package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import GameState.DamageSkill;
import GameState.Skill;
import GameState.DamageSkill.ElementalType;
import GameState.Skill.Targets;

/**
 * 
 * @author Toby Sullivan (txs318)
 * 
 *         JUnit tests for DamageSkill
 *
 */
public class DamageSkillTest {

	private DamageSkill dam1 = new DamageSkill("Damage1", 30, 1,
			Skill.Targets.SINGLE, 50, 90, DamageSkill.ElementalType.AIR, null);
	private DamageSkill dam2;
	private DamageSkill dam3;

	public DamageSkillTest() {
		dam2 = new DamageSkill(null, 0, 0, null, 0, 0,
				DamageSkill.ElementalType.VOID, null);
		dam3 = new DamageSkill("Damage1", 303333, 121, Skill.Targets.TEAM,
				534230, 11110, DamageSkill.ElementalType.FIRE, null);
	}

	@Test
	public void testGetPower() {
		assertEquals(50, dam1.getPower());
		assertEquals(0, dam2.getPower());
		assertEquals(534230, dam3.getPower());
	}

	@Test
	public void testGetAccuracy() {
		assertEquals(90, dam1.getAccuracy());
		assertEquals(0, dam2.getAccuracy());
		assertEquals(11110, dam3.getAccuracy());
	}

	@Test
	public void testGetElementalType() {
		assertEquals(DamageSkill.ElementalType.AIR, dam1.getElementalType());
		assertEquals(DamageSkill.ElementalType.VOID, dam2.getElementalType());
		assertEquals(DamageSkill.ElementalType.FIRE, dam3.getElementalType());
	}

	@Test
	public void testSetPower() {
		assertEquals(50, dam1.getPower());
		dam1.setPower(-80);
		assertEquals(-80, dam1.getPower());
		dam1.setPower(30);
		assertEquals(30, dam1.getPower());
		dam1.setPower(30);
		assertEquals(30, dam1.getPower());
	}

	@Test
	public void testSetAccuracy() {
		assertEquals(90, dam1.getAccuracy());
		dam1.setAccuracy(-80);
		assertEquals(-80, dam1.getAccuracy());
		dam1.setAccuracy(30);
		assertEquals(30, dam1.getAccuracy());
		dam1.setAccuracy(30);
		assertEquals(30, dam1.getAccuracy());
	}

	@Test
	public void testSetElementalType() {
		assertEquals(DamageSkill.ElementalType.AIR, dam1.getElementalType());
		dam1.setElementalType(DamageSkill.ElementalType.VOID);
		assertEquals(DamageSkill.ElementalType.VOID, dam1.getElementalType());
		dam1.setElementalType(DamageSkill.ElementalType.VOID);
		assertEquals(DamageSkill.ElementalType.VOID, dam1.getElementalType());
		dam1.setElementalType(DamageSkill.ElementalType.FIRE);
		assertEquals(DamageSkill.ElementalType.FIRE, dam1.getElementalType());
	}

}
