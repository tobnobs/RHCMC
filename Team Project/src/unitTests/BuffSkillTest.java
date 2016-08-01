package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import GameState.BuffSkill;
import GameState.Skill;
import GameState.Skill.Targets;

/**
 * 
 * @author Toby Sullivan (txs318)
 * 
 *         JUnit tests for BuffSkill
 *
 */
public class BuffSkillTest {

	private BuffSkill buff1 = new BuffSkill("buff1", 10, 1,
			Skill.Targets.SINGLE, 1, true, null);
	private BuffSkill buff2;
	private BuffSkill buff3;
	private BuffSkill buff4;

	public BuffSkillTest() {
		buff2 = new BuffSkill(null, 0, 0, null, 0, false, null);
		buff3 = new BuffSkill("", 9001, 4, Skill.Targets.TEAM, 155, true, null);
		buff4 = new BuffSkill("bound", -10, -10, Skill.Targets.TEAM, -10, false, null);
	}

	@Test
	public void testGetstatAffected() {
		assertEquals(1, buff1.getstatAffected());
		assertEquals(0, buff2.getstatAffected());
		assertEquals(155, buff3.getstatAffected());
		assertEquals(-10, buff4.getstatAffected());
	}

	@Test
	public void testSetStatAffected() {
		assertEquals(1, buff1.getstatAffected());
		buff1.setStatAffected(2);
		assertEquals(2, buff1.getstatAffected());
		buff1.setStatAffected(-1);
		assertEquals(-1, buff1.getstatAffected());
		buff1.setStatAffected(166666);
		assertEquals(166666, buff1.getstatAffected());
	}

	@Test
	public void testIsStatUp() {
		assertEquals(true, buff1.isStatUp());
		assertEquals(false, buff2.isStatUp());
		assertEquals(true, buff3.isStatUp());
		assertEquals(false, buff4.isStatUp());
	}

	@Test
	public void testSetStatUp() {
		assertEquals(true, buff1.isStatUp());
		buff1.setStatUp(false);
		assertEquals(false, buff1.isStatUp());
		buff1.setStatUp(false);
		assertEquals(false, buff1.isStatUp());
		buff1.setStatUp(true);
		assertEquals(true, buff1.isStatUp());
		buff1.setStatUp(true);
		assertEquals(true, buff1.isStatUp());
	}

}
