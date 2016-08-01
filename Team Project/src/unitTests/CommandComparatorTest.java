package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import GameState.Command;
import GameState.CommandComparator;

/**
 * 
 * @author Toby Sullivan (txs318)
 * 
 *         JUnit tests for CommandComparator
 *
 */
public class CommandComparatorTest {

	private Command com1 = new Command(1, 1, null, 1, 1, 1);
	private Command com2 = new Command(2, 2, null, 2, 2, 2);
	private Command com3 = new Command(3, 3, null, 2, 3, 3);

	private CommandComparator comp = new CommandComparator();

	@Test
	public final void testCompare() {
		assertEquals(1, comp.compare(com1, com2));
		assertEquals(-1, comp.compare(com3, com1));
		assertEquals(0, comp.compare(com2, com3));
	}

}
