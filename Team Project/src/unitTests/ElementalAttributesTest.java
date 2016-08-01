package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import GameState.ElementalAttributes;

/**
 * 
 * @author Toby Sullivan (txs318)
 * 
 *         JUnit tests for ElementalAttributes
 *
 */
public class ElementalAttributesTest {

	private ElementalAttributes ea1 = new ElementalAttributes(2, -1, 0, 0, 0);
	private ElementalAttributes ea2 = new ElementalAttributes(0, 2, -1, 0, 0);
	private ElementalAttributes ea3 = new ElementalAttributes(0, 0, 2, -1, 0);
	private ElementalAttributes ea4 = new ElementalAttributes(0, 0, 0, 2, -1);
	private ElementalAttributes ea5 = new ElementalAttributes(-1, 0, 0, 0, 2);

	@Test
	public void testSetVoid() {
		assertEquals(2, ea1.getVoid());
		ea1.setVoid(4);
		assertEquals(4, ea1.getVoid());
		ea1.setVoid(-4);
		assertEquals(-4, ea1.getVoid());
		ea1.setVoid(-4);
		assertEquals(-4, ea1.getVoid());
		ea1.setVoid(2);
		assertEquals(2, ea1.getVoid());
	}

	@Test
	public void testSetFire() {
		assertEquals(2, ea2.getFire());
		ea2.setFire(4);
		assertEquals(4, ea2.getFire());
		ea2.setFire(-4);
		assertEquals(-4, ea2.getFire());
		ea2.setFire(-4);
		assertEquals(-4, ea2.getFire());
		ea2.setFire(2);
		assertEquals(2, ea2.getFire());
	}

	@Test
	public void testSetWater() {
		assertEquals(2, ea3.getWater());
		ea3.setWater(4);
		assertEquals(4, ea3.getWater());
		ea3.setWater(-4);
		assertEquals(-4, ea3.getWater());
		ea3.setWater(-4);
		assertEquals(-4, ea3.getWater());
		ea3.setWater(2);
		assertEquals(2, ea3.getWater());
	}

	@Test
	public void testSetEarth() {
		assertEquals(2, ea4.getEarth());
		ea4.setEarth(4);
		assertEquals(4, ea4.getEarth());
		ea4.setEarth(-4);
		assertEquals(-4, ea4.getEarth());
		ea4.setEarth(-4);
		assertEquals(-4, ea4.getEarth());
		ea4.setEarth(2);
		assertEquals(2, ea4.getEarth());
	}

	@Test
	public void testSetAir() {
		assertEquals(2, ea5.getAir());
		ea5.setAir(4);
		assertEquals(4, ea5.getAir());
		ea5.setAir(-4);
		assertEquals(-4, ea5.getAir());
		ea5.setAir(2);
		assertEquals(2, ea5.getAir());
	}

	@Test
	public void testGetVoid() {
		assertEquals(2, ea1.getVoid());
		assertEquals(-1, ea5.getVoid());
		assertEquals(0, ea4.getVoid());

	}

	@Test
	public void testGetFire() {
		assertEquals(2, ea2.getFire());
		assertEquals(-1, ea1.getFire());
		assertEquals(0, ea5.getFire());
	}

	@Test
	public void testGetWater() {
		assertEquals(2, ea3.getWater());
		assertEquals(-1, ea2.getWater());
		assertEquals(0, ea1.getWater());
	}

	@Test
	public void testGetEarth() {
		assertEquals(2, ea4.getEarth());
		assertEquals(-1, ea3.getEarth());
		assertEquals(0, ea2.getEarth());
	}

	@Test
	public void testGetAir() {
		assertEquals(2, ea5.getAir());
		assertEquals(-1, ea4.getAir());
		assertEquals(0, ea1.getAir());
	}

}
