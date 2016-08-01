package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import GameState.BuffSkill;
import GameState.DamageSkill;
import GameState.ElementalAttributes;
import GameState.HealingSkill;
import GameState.Player;
import GameState.Skill;
import GameState.DamageSkill.ElementalType;
import GameState.Skill.Targets;

/**
 * 
 * @author Toby Sullivan (txs318)
 * 
 *         JUnit tests for Player
 *
 */
public class PlayerTest {

	private DamageSkill dam1 = new DamageSkill("Damage1", 30, 1,
			Skill.Targets.SINGLE, 50, 90, DamageSkill.ElementalType.AIR, null);
	private HealingSkill heal1 = new HealingSkill(null, 20, 1, null, 100, null);
	private BuffSkill buff1 = new BuffSkill("buff1", 10, 1,
			Skill.Targets.SINGLE, 1, true, null);
	private Skill[] skillset;

	private ElementalAttributes ea1 = new ElementalAttributes(2, -1, 0, 0, 0);
	private ElementalAttributes ea2 = new ElementalAttributes(0, 2, -1, 0, 0);
	private Player play1 = new Player("PlayerA", ea1, 200, 150, 10, 10, 60, 60, 0,
			skillset, 0, 0, 200, 200);
	private Player play2 = new Player("PlayerB", ea2, 150, 300, 5, 5, 5, 5, 0,
			skillset, 1, 0, 100, 100);
	private Player play3 = new Player(null, null, 0, 0, 0, 0, 0, 0, 0, null, 0,
			0, 0, 0);

	@Test
	public final void testGetName() {
		assertEquals("PlayerA", play1.getName());
		assertEquals("PlayerB", play2.getName());
		assertEquals(null, play3.getName());
	}

	@Test
	public final void testSetName() {
		assertEquals("PlayerA", play1.getName());
		play1.setName("PlayerC");
		assertEquals("PlayerC", play1.getName());
//		play1.setName(null);
//		assertEquals(null, play1.getName());
	}

	@Test
	public final void testGetEA() {
		assertEquals(ea1, play1.getEA());
		assertEquals(ea2, play2.getEA());
		assertEquals(null, play3.getEA());
	}

	@Test
	public final void testSetEa() {
		assertEquals(ea1, play1.getEA());
		play1.setEa(ea2);
		assertEquals(ea2, play1.getEA());
		play1.setEa(null);
		assertEquals(null, play1.getEA());
	}

	@Test
	public final void testGetBaseHP() {
		assertEquals(200, play1.getBaseHP(), 0);
		assertEquals(150, play2.getBaseHP(), 0);
		assertEquals(0, play3.getBaseHP(), 0);
	}

	@Test
	public final void testSetBaseHP() {
		assertEquals(200, play1.getBaseHP(), 0);
		play1.setBaseHP(300);
		assertEquals(300, play1.getBaseHP(), 0);
		play1.setBaseHP(-100);
		assertEquals(-100, play1.getBaseHP(), 0);
	}

	@Test
	public final void testGetBaseMP() {
		assertEquals(150, play1.getBaseMP(), 0);
		assertEquals(300, play2.getBaseMP(), 0);
		assertEquals(0, play3.getBaseMP(), 0);
	}

	@Test
	public final void testSetBaseMP() {
		assertEquals(150, play1.getBaseMP(), 0);
		play1.setBaseMP(300);
		assertEquals(300, play1.getBaseMP(), 0);
		play1.setBaseMP(-100);
		assertEquals(-100, play1.getBaseMP(), 0);
	}

	@Test
	public final void testGetBasePower() {
		assertEquals(60, play1.getBasePower(), 0);
		assertEquals(5, play2.getBasePower(), 0);
		assertEquals(0, play3.getBasePower(), 0);
	}

	@Test
	public final void testSetBasePower() {
		assertEquals(60, play1.getBasePower(), 0);
		play1.setBasePower(300);
		assertEquals(300, play1.getBasePower(), 0);
		play1.setBasePower(-100);
		assertEquals(-100, play1.getBasePower(), 0);
	}

	@Test
	public final void testGetBaseDefence() {
		assertEquals(60, play1.getBaseDefence(), 0);
		assertEquals(5, play2.getBaseDefence(), 0);
		assertEquals(0, play3.getBaseDefence(), 0);

	}

	@Test
	public final void testSetBaseDefence() {
		assertEquals(60, play1.getBaseDefence(), 0);
		play1.setBaseDefence(300);
		assertEquals(300, play1.getBaseDefence(), 0);
		play1.setBaseDefence(-100);
		assertEquals(-100, play1.getBaseDefence(), 0);
	}

	@Test
	public final void testGetTotalBonusPoints() {
		assertEquals(10, play1.getTotalBonusPoints(), 0);
		assertEquals(5, play2.getTotalBonusPoints(), 0);
		assertEquals(0, play3.getTotalBonusPoints(), 0);
	}

	@Test
	public final void testSetTotalBonusPoints() {
		assertEquals(10, play1.getTotalBonusPoints(), 0);
		play1.setTotalBonusPoints(4);
		assertEquals(4, play1.getTotalBonusPoints(), 0);
		play1.setTotalBonusPoints(-4);
		assertEquals(0, play1.getTotalBonusPoints(), 0);
	}

	@Test
	public final void testGetBonusPointsLeft() {
		assertEquals(10, play1.getBonusPointsLeft(), 0);
		assertEquals(5, play2.getBonusPointsLeft(), 0);
		assertEquals(0, play3.getBonusPointsLeft(), 0);
	}

	@Test
	public final void testSetBonusPointsLeft() {
		assertEquals(10, play1.getBonusPointsLeft(), 0);
		play1.setBonusPointsLeft(4);
		assertEquals(4, play1.getBonusPointsLeft(), 0);
		play1.setBonusPointsLeft(-4);
		assertEquals(0, play1.getBonusPointsLeft(), 0);
	}

	@Test
	public final void testGetMaxHP() {
		assertEquals(600, play1.getMaxHP(), 0);
		assertEquals(450, play2.getMaxHP(), 0);
		assertEquals(0, play3.getMaxHP(), 0);
	}

	@Test
	public final void testGetMaxMP() {
		assertEquals(300, play1.getMaxMP(), 0);
		assertEquals(600, play2.getMaxMP(), 0);
		assertEquals(0, play3.getMaxMP(), 0);
	}

	@Test
	public final void testGetActualHP() {
		assertEquals(600, play1.getActualHP(), 0);
		assertEquals(450, play2.getActualHP(), 0);
		assertEquals(0, play3.getActualHP(), 0);
	}

	@Test
	public final void testSetActualHP() {
		assertEquals(600, play1.getActualHP(), 0);
		play1.setActualHP(300);
		assertEquals(300, play1.getActualHP(), 0);
		play1.setActualHP(-100);
		assertEquals(-100, play1.getActualHP(), 0);
	}

	@Test
	public final void testGetActualMP() {
		assertEquals(300, play1.getActualMP(), 0);
		assertEquals(600, play2.getActualMP(), 0);
		assertEquals(0, play3.getActualMP(), 0);
	}

	@Test
	public final void testSetActualMP() {
		assertEquals(300, play1.getActualMP(), 0);
		play1.setActualMP(300);
		assertEquals(300, play1.getActualMP(), 0);
		play1.setActualMP(-100);
		assertEquals(-100, play1.getActualMP(), 0);
	}

	@Test
	public final void testSetPowerMulti() {
		assertEquals(3, play1.getPowerMulti(), 0);
		play1.setPowerMulti(4);
		assertEquals(4, play1.getPowerMulti(), 0);
		play1.setPowerMulti(-4);
		assertEquals(-4, play1.getPowerMulti(), 0);
	}

	@Test
	public final void testSetDefenseMulti() {
		assertEquals(3, play1.getDefenseMulti(), 0);
		play1.setDefenseMulti(4);
		assertEquals(4, play1.getDefenseMulti(), 0);
		play1.setDefenseMulti(-4);
		assertEquals(-4, play1.getDefenseMulti(), 0);
	}

	@Test
	public final void testGetPowerMulti() {
		assertEquals(3, play1.getPowerMulti(), 0);
		assertEquals(3, play2.getPowerMulti(), 0);
		assertEquals(3, play3.getPowerMulti(), 0);
	}

	@Test
	public final void testGetDefenseMulti() {
		assertEquals(3, play1.getDefenseMulti(), 0);
		assertEquals(3, play2.getDefenseMulti(), 0);
		assertEquals(3, play3.getDefenseMulti(), 0);
	}

	@Test
	public final void testGetSkillSet() {
		assertEquals(skillset, play1.getSkillSet());
		assertEquals(skillset, play2.getSkillSet());
		assertEquals(null, play3.getSkillSet());
	}

	@Test
	public final void testSetSkillSet() {
		assertEquals(skillset, play1.getSkillSet());
		play1.setSkillSet(null);
		assertEquals(null, play1.getSkillSet());
		play3.setSkillSet(skillset);
		assertEquals(skillset, play3.getSkillSet());
	}

	@Test
	public final void testGetTeam() {
		assertEquals(0, play1.getTeam(), 0);
		assertEquals(1, play2.getTeam(), 1);
		assertEquals(0, play3.getTeam(), 0);
	}

	@Test
	public final void testSetTeam() {
		assertEquals(0, play1.getTeam(), 0);
		play1.setTeam(1);
		assertEquals(1, play1.getTeam(), 1);
		play2.setTeam(0);
		assertEquals(0, play2.getTeam(), 0);
	}

	@Test
	public final void testGetPlayerID() {
		assertEquals(0, play1.getPlayerID());
		assertEquals(0, play2.getPlayerID());
		assertEquals(0, play3.getPlayerID());
	}

	@Test
	public final void testSetPlayerID() {
		assertEquals(0, play1.getPlayerID(), 0);
		play1.setPlayerID(1);
		assertEquals(1, play1.getPlayerID(), 1);
		play1.setPlayerID(-200);
		assertEquals(-200, play1.getPlayerID(), 0);
	}

	@Test
	public final void testGetGuarding() {
		assertEquals(false, play1.getGuarding());
		assertEquals(false, play2.getGuarding());
		assertEquals(false, play3.getGuarding());
	}

	@Test
	public final void testSetGuarding() {
		assertEquals(false, play1.getGuarding());
		play1.setGuarding(true);
		assertEquals(true, play1.getGuarding());
		play1.setGuarding(true);
		assertEquals(true, play1.getGuarding());
		play1.setGuarding(false);
		assertEquals(false, play1.getGuarding());
	}
	
	@Test
	public final void testGetX() {
		assertEquals(200, play1.getX());
		assertEquals(100, play2.getX());
		assertEquals(0, play3.getX());
	}
	
	@Test
	public final void testSetX() {
		assertEquals(200, play1.getX());
		play1.setX(500);
		assertEquals(500, play1.getX());
		play1.setX(0);
		assertEquals(0, play1.getX());
		play1.setX(-10);
		assertEquals(0, play1.getX());
		play1.setX(1001);
		assertEquals(980, play1.getX());
		play1.setX(200);
		assertEquals(200, play1.getX());
	}
	
	@Test
	public final void testGetY() {
		assertEquals(200, play1.getY());
		assertEquals(100, play2.getY());
		assertEquals(0, play3.getY());
	}
	
	@Test
	public final void testSetY() {
		assertEquals(200, play1.getY());
		play1.setY(50);
		assertEquals(50, play1.getY());
		play1.setY(0);
		assertEquals(0, play1.getY());
		play1.setY(-10);
		assertEquals(0, play1.getY());
		play1.setY(1001);
		assertEquals(450, play1.getY());
		play1.setY(200);
		assertEquals(200, play1.getY());
	}
	
	@Test
	public final void testIsInCave() {
		assertEquals(false, play1.isInCave());
		assertEquals(false, play3.isInCave());
		assertEquals(false, play2.isInCave());
	}

	@Test
	public final void testSetInCave() {
		assertEquals(false, play1.isInCave());
		play1.setInCave(true);
		assertEquals(true, play1.isInCave());
		play1.setInCave(false);
		assertEquals(false, play1.isInCave());
		play2.setInCave(true);
		assertEquals(false, play1.isInCave());
	}

	@Test
	public final void testIsSwitchMap() {
		assertEquals(false, play1.isSwitchMap());
		assertEquals(false, play3.isSwitchMap());
		assertEquals(false, play2.isSwitchMap());
	}

	@Test
	public final void testSetSwitchMap() {
		assertEquals(false, play1.isSwitchMap());
		play1.setSwitchMap(true);
		assertEquals(true, play1.isSwitchMap());
		play1.setSwitchMap(false);
		assertEquals(false, play1.isSwitchMap());
		play2.setSwitchMap(true);
		assertEquals(false, play1.isSwitchMap());
	}
	
	@Test
	public final void testGetSpeedMulti() {
		assertEquals(3, play1.getSpeedMulti());
		assertEquals(3, play2.getSpeedMulti());
		assertEquals(3, play3.getSpeedMulti());
	}
	
	@Test
	public final void testSetSpeedMulti() {
		assertEquals(3, play1.getSpeedMulti());
		play1.setSpeedMulti(10);
		assertEquals(10, play1.getSpeedMulti());
		play1.setSpeedMulti(10);
		assertEquals(10, play1.getSpeedMulti());
		play1.setSpeedMulti(-10);
		assertEquals(-10, play1.getSpeedMulti());
		play1.setSpeedMulti(3);
		assertEquals(3, play1.getSpeedMulti());
	}
	
	@Test
	public final void testGetBaseSpeed() {
		assertEquals(0, play1.getBaseSpeed(), 0);
		assertEquals(0, play2.getBaseSpeed(), 0);
		assertEquals(0, play3.getBaseSpeed(), 0);
	}
	
	@Test
	public final void testSetBaseSpeed() {
		assertEquals(0, play1.getBaseSpeed(), 0);
		play1.setBaseSpeed(10);
		assertEquals(10, play1.getBaseSpeed(), 0);
		play1.setBaseSpeed(10);
		assertEquals(10, play1.getBaseSpeed(), 0);
		play1.setBaseSpeed(-10);
		assertEquals(-10, play1.getBaseSpeed(), 0);
		play1.setBaseSpeed(0);
		assertEquals(0, play1.getBaseSpeed(), 0);
	}
	
	@Test
	public final void testIsDead() {
		assertEquals(false, play1.isDead());
		assertEquals(false, play2.isDead());
		assertEquals(false, play3.isDead());
	}
	
	@Test
	public final void testSetDead() {
		assertEquals(false, play1.isDead());
		play1.setDead(true);
		assertEquals(true, play1.isDead());
		play1.setDead(true);
		assertEquals(true, play1.isDead());
		play1.setDead(false);
		assertEquals(false, play1.isDead());
	}
	
	@Test
	public final void testReset() {
		assertEquals(3, play1.getPowerMulti());
		assertEquals(3, play1.getSpeedMulti());
		assertEquals(3, play1.getDefenseMulti());
		assertEquals(600, play1.getActualHP(), 0);
		assertEquals(300, play1.getActualMP(), 0);
		play1.setPowerMulti(10);
		assertEquals(10, play1.getPowerMulti());
		play1.setSpeedMulti(10);
		assertEquals(10, play1.getSpeedMulti());
		play1.setDefenseMulti(10);
		assertEquals(10, play1.getDefenseMulti());
		play1.setActualHP(400);
		assertEquals(400, play1.getActualHP(), 0);
		play1.setActualMP(200);
		assertEquals(200, play1.getActualMP(), 0);
		play1.setGuarding(true);
		assertEquals(true, play1.getGuarding());
		play1.setDead(true);
		assertEquals(true, play1.isDead());
		
		play1.reset();

		assertEquals(3, play1.getPowerMulti());
		assertEquals(3, play1.getSpeedMulti());
		assertEquals(3, play1.getDefenseMulti());
		assertEquals(600, play1.getActualHP(), 0);
		assertEquals(300, play1.getActualMP(), 0);	
		assertEquals(false, play1.getGuarding());
		assertEquals(false, play1.isDead());
	}
	
	@Test
	public final void testIsFacingLeft() {
		assertEquals(false, play1.isFacingLeft());
		assertEquals(false, play2.isFacingLeft());
		assertEquals(false, play3.isFacingLeft());
	}
	
	@Test
	public final void testSetFacingLeft() {
		assertEquals(false, play1.isFacingLeft());
		play1.setFacingLeft(false);
		assertEquals(false, play1.isFacingLeft());
		play1.setFacingLeft(true);
		assertEquals(true, play1.isFacingLeft());
		play1.setFacingLeft(false);
		assertEquals(false, play1.isFacingLeft());
	}
	
	@Test
	public final void testEnterCave() {
		assertEquals(false, play1.isInCave());
		play1.setX(1);
		play1.setY(200);
		assertEquals(true, play1.isInCave());
	}
}
