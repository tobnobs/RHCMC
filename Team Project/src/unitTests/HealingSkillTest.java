package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import GameState.HealingSkill;

/**
 * 
 * @author Toby Sullivan (txs318)
 * 
 *         JUnit tests for HealingSkill
 *
 */
public class HealingSkillTest {

	private HealingSkill heal1 = new HealingSkill(null, 20, 1, null, 100, null);
	private HealingSkill heal2 = new HealingSkill(null, 40, 1, null, 180, null);
	private HealingSkill heal3 = new HealingSkill(null, 60, 1, null, 250, null);

	@Test
	public void testGetHealingPower() {
		assertEquals(100, heal1.getHealingPower());
		assertEquals(180, heal2.getHealingPower());
		assertEquals(250, heal3.getHealingPower());
	}

	@Test
	public void testSetHealingPower() {
		assertEquals(100, heal1.getHealingPower());
		heal1.setHealingPower(0);
		assertEquals(0, heal1.getHealingPower());
		heal1.setHealingPower(-20);
		assertEquals(-20, heal1.getHealingPower());
		heal1.setHealingPower(200);
		assertEquals(200, heal1.getHealingPower());
		heal1.setHealingPower(200);
		assertEquals(200, heal1.getHealingPower());
	}

}
