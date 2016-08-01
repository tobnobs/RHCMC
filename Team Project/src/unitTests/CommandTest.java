package unitTests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import GameState.Command;
import GameState.Command.PlayerCommand;
public class CommandTest {
	
	private Command com1;
	private Command com2;
	private Command com3;
	private Command com4;
	private Stack<Integer> targ1;
	private Stack<Integer> targ2;
	
	public CommandTest() {
		targ1 = new Stack<Integer>();
		targ1.push(0);
		targ1.push(1);
		targ1.push(2);
		targ2 = new Stack<Integer>();
		targ2.push(3);
		targ2.push(4);
		com1 = new Command(1, 0, targ1, 0, 0, 0);
		com2 = new Command(2, 1, targ2, 1, 1, 1);
		com3 = new Command(3, 2, null, 2, 2, 2);
		com4 = new Command(0, 4, null, 3, 3, 3);
	}
	
	@Test
	public final void testGetTargets() {
		assertEquals(targ1, com1.getTargets());
		assertEquals(targ2, com2.getTargets());
		assertEquals(null, com3.getTargets());
		assertEquals(null, com4.getTargets());
	}

	@Test
	public final void testSetTargets() {
		assertEquals(null, com3.getTargets());
		com3.setTargets(targ1);
		assertEquals(targ1, com3.getTargets());
		com3.setTargets(targ2);
		assertEquals(targ2, com3.getTargets());
		com3.setTargets(targ2);
		assertEquals(targ2, com3.getTargets());
		com3.setTargets(null);
		assertEquals(null, com3.getTargets());
	}

	@Test
	public final void testGetAggresor() {
		assertEquals(1, com1.getAggresor());
		assertEquals(2, com2.getAggresor());
		assertEquals(3, com3.getAggresor());
		assertEquals(0, com4.getAggresor());
	}

	@Test
	public final void testSetAggresor() {
		assertEquals(1, com1.getAggresor());
		com1.setAggresor(0);
		assertEquals(0, com1.getAggresor());		
		com1.setAggresor(-1);
		assertEquals(-1, com1.getAggresor());	
		com1.setAggresor(-1);
		assertEquals(-1, com1.getAggresor());	
		com1.setAggresor(1);
		assertEquals(1, com1.getAggresor());	
	}

	@Test
	public final void testGetPlayerCommand() {
		assertEquals(null, com1.getPlayerCommand());
		assertEquals(null, com2.getPlayerCommand());
		assertEquals(null, com3.getPlayerCommand());
		assertEquals(null, com4.getPlayerCommand());
	}

	@Test
	public final void testSetPlayerCommand() {
		assertEquals(null, com1.getPlayerCommand());
		com1.setPlayerCommand(PlayerCommand.ATTACK);
		assertEquals(PlayerCommand.ATTACK, com1.getPlayerCommand());
		com1.setPlayerCommand(PlayerCommand.SKILL0);
		assertEquals(PlayerCommand.SKILL0, com1.getPlayerCommand());
		com1.setPlayerCommand(PlayerCommand.SKILL0);
		assertEquals(PlayerCommand.SKILL0, com1.getPlayerCommand());
		com1.setPlayerCommand(PlayerCommand.DEFEND);
		assertEquals(PlayerCommand.DEFEND, com1.getPlayerCommand());
		com1.setPlayerCommand(null);
		assertEquals(null, com1.getPlayerCommand());
	}

	@Test
	public final void testGetSpeed() {
		assertEquals(0, com1.getSpeed(), 0);
		assertEquals(1, com2.getSpeed(), 0);
		assertEquals(2, com3.getSpeed(), 0);
		assertEquals(3, com4.getSpeed(), 0);
	}

	@Test
	public final void testSetSpeed() {
		assertEquals(0, com1.getSpeed(), 0);
		com1.setSpeed(9001);
		assertEquals(9001, com1.getSpeed(), 0);
		com1.setSpeed(-10);
		assertEquals(-10, com1.getSpeed(), 0);
		com1.setSpeed(-10);
		assertEquals(-10, com1.getSpeed(), 0);
		com1.setSpeed(0);
		assertEquals(0, com1.getSpeed(), 0);
	}

	@Test
	public final void testGetCommandInt() {
		assertEquals(0, com1.getCommandInt(), 0);
		assertEquals(1, com2.getCommandInt(), 0);
		assertEquals(2, com3.getCommandInt(), 0);
		assertEquals(4, com4.getCommandInt(), 0);
	}

	@Test
	public final void testSetCommandInt() {
		assertEquals(0, com1.getCommandInt(), 0);
		com1.setCommandInt(4);
		assertEquals(4, com1.getCommandInt(), 0);
		com1.setCommandInt(10);
		assertEquals(10, com1.getCommandInt(), 0);
		com1.setCommandInt(10);
		assertEquals(10, com1.getCommandInt(), 0);
		com1.setCommandInt(0);
		assertEquals(0, com1.getCommandInt(), 0);
	}

	@Test
	public final void testGetSkillID() {
		assertEquals(0, com1.getSkillID());
		assertEquals(1, com2.getSkillID());
		assertEquals(2, com3.getSkillID());
		assertEquals(3, com4.getSkillID());
	}

	@Test
	public final void testSetSkillID() {
		assertEquals(0, com1.getSkillID());
		com1.setSkillID(10);
		assertEquals(10, com1.getSkillID());
		com1.setSkillID(10);
		assertEquals(10, com1.getSkillID());
		com1.setSkillID(-10);
		assertEquals(-10, com1.getSkillID());
		com1.setSkillID(1000000);
		assertEquals(1000000, com1.getSkillID());
		com1.setSkillID(0);
		assertEquals(0, com1.getSkillID());
	}

	@Test
	public final void testGetSkillType() {
		assertEquals(0, com1.getSkillType());
		assertEquals(1, com2.getSkillType());
		assertEquals(2, com3.getSkillType());
		assertEquals(3, com4.getSkillType());
	}

	@Test
	public final void testSetSkillType() {
		assertEquals(0, com1.getSkillType());
		com1.setSkillType(9);
		assertEquals(9, com1.getSkillType());
		com1.setSkillType(9);
		assertEquals(9, com1.getSkillType());
		com1.setSkillType(-1);
		assertEquals(-1, com1.getSkillType());
		com1.setSkillType(0);
		assertEquals(0, com1.getSkillType());
	}

	@Test
	public final void testIsMPdeducted() {
		assertEquals(false, com1.isMPdeducted());
		assertEquals(false, com2.isMPdeducted());
		assertEquals(false, com3.isMPdeducted());
		assertEquals(false, com4.isMPdeducted());
	}

	@Test
	public final void testSetMPdeducted() {
		assertEquals(false, com1.isMPdeducted());
		com1.setMPdeducted(false);
		assertEquals(false, com1.isMPdeducted());
		com1.setMPdeducted(true);
		assertEquals(true, com1.isMPdeducted());
		com1.setMPdeducted(false);
		assertEquals(false, com1.isMPdeducted());
	}

}
