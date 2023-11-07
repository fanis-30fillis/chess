package chess;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Testing {

	private int getLocationRow(String locat) {
		Location loc;
		try {
			loc = new Location(locat);
		} catch (InvalidLocationException e) {
			return -1;
		}
		return loc.getRow();
	}
	private int getLocationCol(String locat) {
		Location loc;
		try {
			loc = new Location(locat);
		} catch (InvalidLocationException e) {
			return -1;
		}
		return loc.getCol();
	}
	private int getLocationRow(int row, int col) {
		Location loc;
		try {
			loc = new Location(row, col);
		} catch (InvalidLocationException e) {
			return -1;
		}
		return loc.getRow();
	}
	private int getLocationCol(int row, int col) {
		Location loc;
		try {
			loc = new Location(row, col);
		} catch (InvalidLocationException e) {
			return -1;
		}
		return loc.getCol();
	}

	private String getLocationWithErrHandling(int row, int col) {
		Location loc;
		try {
			loc = new Location(row, col);
		} catch (InvalidLocationException e) {
			return "-1";
		}
		return loc.loc;
	}
	private String getLocationWithErrHandling(String locat) {
		Location loc;
		try {
			loc = new Location(locat);
		} catch (InvalidLocationException e) {
			return "-1";
		}
		return loc.loc;
	}


	@Test
	void boardTesting() throws InvalidLocationException, InvalidMoveException {
		Board testBoard = new Board();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				testBoard.board[row][col] = new EmptyPiece(new Location(row, col), testBoard);
			}
		}
		testBoard.board[5][5] = new Bishop(new Location(5,5), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		assertEquals("f6", testBoard.board[5][5].loc.toString());
		testBoard.board[5][5].moveTo(new Location(3,3));
		assertEquals("d4", testBoard.board[3][3].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[3][3].moveTo(new Location(0,0));
		assertEquals("a1", testBoard.board[0][0].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[0][0].moveTo(new Location(7,7));
		assertEquals("h8", testBoard.board[7][7].loc.toString());
		testBoard.board[5][5] = new Pawn(new Location(5,5), Color.BLACK, testBoard);
		assertEquals("f6", testBoard.board[5][5].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[7][7].moveTo(testBoard.board[5][5].loc);
		System.out.println(testBoard.toString());
		testBoard.board[6][6] = new King(new Location(6,6), Color.BLACK, testBoard);
		assertEquals("g7", testBoard.board[6][6].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[6][6].moveTo(testBoard.board[5][5].loc);
		assertEquals("f6", testBoard.board[5][5].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[0][0] = new Queen(new Location(0,0), Color.WHITE, testBoard);
		assertEquals("a1", testBoard.board[0][0].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[0][0].moveTo(testBoard.board[5][5].loc);
		assertEquals("f6", testBoard.board[5][5].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[4][3] = new Knight(new Location(4,3), Color.BLACK, testBoard);
		assertEquals("d5", testBoard.board[4][3].loc.toString());
		System.out.println(testBoard.toString());
		testBoard.board[4][3].moveTo(testBoard.board[5][5].loc);
		assertEquals("f6", testBoard.board[5][5].loc.toString());
		System.out.println(testBoard.toString());
	}
	
	// tests the locations strings
	@Test
	void testLocations()  {

		assertEquals("a6", getLocationWithErrHandling("a6"));
		assertEquals("b2", getLocationWithErrHandling("b2"));
		assertEquals("f2", getLocationWithErrHandling("f2"));
		assertEquals("e5", getLocationWithErrHandling("e5"));
		assertEquals("a1", getLocationWithErrHandling(0,0));
		assertEquals("c1", getLocationWithErrHandling(0,2));
		assertEquals("e5", getLocationWithErrHandling(4,4));
		assertEquals("h8", getLocationWithErrHandling(7,7));
		Exception e = assertThrows(Exception.class, () -> {
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

		e = assertThrows(Exception.class, () -> {
			new Location("a0");
		});
		expectedString = "Invalid row indicator: 0";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			new Location("x0");
		});

		expectedString = """
				Invalid column indicator: x
				Invalid row indicator: 0""";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);
		
		e = assertThrows(Exception.class, () -> {
			new Location("a9");
		});

		expectedString = "Invalid row indicator: 9";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			new Location("i1");
		});

		expectedString = """
				Invalid column indicator: i
				""";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);


		assertEquals(0, getLocationCol("a2"));
		assertEquals(1, getLocationRow("a2"));
		assertEquals(7, getLocationRow("a8"));
		assertEquals(2, getLocationCol("c8"));

	}
}
