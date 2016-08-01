package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import GUI.Elements;

public class ElementsGUITest {

	private Elements elem = new Elements("Player", 0, 0, 0, 0, 0, 0,0,0,0,0, 0, 0, null, null, null, null, null, null);
	
	@Test
	public final void testConvertEAValueStr() {
		assertEquals("Absorb", elem.convertEAValueStr(-1));
		assertEquals("Null", elem.convertEAValueStr(0));
		assertEquals("Strong", elem.convertEAValueStr(1));
		assertEquals("Normal", elem.convertEAValueStr(2));
		assertEquals("Weak", elem.convertEAValueStr(3));
	}
}
