package nhf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * A TopListElement toString() f�ggv�ny�nek tesztel�se.
 * @author Sivado
 *
 */
public class TestTopListElement {
	TopListElement tle;
	
	@Before
	public void setUp() {
		tle = new TopListElement(8, 2, "Player");
		
	}
	
	@Test
	public void test() {
		Assert.assertEquals("2. Player 8 k�r", tle.toString());
	}

}
