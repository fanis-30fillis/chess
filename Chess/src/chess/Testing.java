package chess;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Testing {

	private String getLocationWithErrHandling(int row, int col) {
		Location loc;
		try {
			loc = new Location(row, col);
		} catch (InvalidLocationException e) {
			return "-1";
		}
		return loc.loc;
	}
	// tests the locations strings
	@Test
	void testLocations()  {
		assertEquals("a1", getLocationWithErrHandling(0,0));
		assertEquals("c1", getLocationWithErrHandling(0,2));
		assertEquals("e5", getLocationWithErrHandling(4,4));
		assertEquals("f8", getLocationWithErrHandling(7,7));
		Exception e = assertThrows(InvalidLocationException.class, () -> {
			new Location(8,10);
		});
		String expectedString = "Given row is out of bounds";
		String exceptionMessage = e.getMessage();

		assertEquals(expectedString, exceptionMessage);
		e = assertThrows(InvalidLocationException.class, () -> {
			new Location(7,18);
		});
		expectedString = "Given column is out of bounds";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);
	}

}
