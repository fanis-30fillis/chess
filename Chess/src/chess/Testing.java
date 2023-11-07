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
	
	@Test
	void testPawnMovements() throws InvalidLocationException, InvalidMoveException {
		Board testBoard = new Board();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				testBoard.board[row][col] = new EmptyPiece(new Location(row, col), testBoard);
			}
		}
		testBoard.board[1][5] = new Pawn(new Location(1,5), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[2][4] = new Pawn(new Location(2,4), Color.BLACK, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[1][5].moveTo(new Location(2,4));
		System.out.println(testBoard.toString());
		testBoard.board[1][7] = new Pawn(new Location(1,7), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[1][7].moveTo(new Location(3,7));
		System.out.println(testBoard.toString());

		Exception e = assertThrows(Exception.class, () -> {
			testBoard.board[3][7].moveTo(new Location(5,7));
		});
		String expectedString = "Invalid";
		String exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[3][7].moveTo(new Location(4,6));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[3][7].moveTo(new Location(3,6));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);
	}
	
	@Test
	void testRookMovements() throws InvalidLocationException, InvalidMoveException {
		Board testBoard = new Board();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				testBoard.board[row][col] = new EmptyPiece(new Location(row, col), testBoard);
			}
		}
		testBoard.board[1][5] = new Rook(new Location(1,5), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[2][5] = new Pawn(new Location(2,5), Color.BLACK, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[1][5].moveTo(new Location(2,5));
		System.out.println(testBoard.toString());
		testBoard.board[1][7] = new Rook(new Location(1,7), Color.BLACK, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[1][7].moveTo(new Location(2,7));
		System.out.println(testBoard.toString());
		testBoard.board[2][7].moveTo(new Location(2,5));
		System.out.println(testBoard.toString());

		Exception e = assertThrows(Exception.class, () -> {
			testBoard.board[2][5].moveTo(new Location(0,1));
		});
		String expectedString = "Invalid";
		String exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		testBoard.board[0][5] = new King(new Location(0,5), Color.BLACK, testBoard);
		System.out.println(testBoard.toString());

		e = assertThrows(Exception.class, () -> {
			testBoard.board[2][5].moveTo(new Location(0,5));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[2][5].moveTo(new Location(3,6));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);
	}
	
	@Test
	void testQueenMovements() throws InvalidLocationException, InvalidMoveException {
		Board testBoard = new Board();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				testBoard.board[row][col] = new EmptyPiece(new Location(row, col), testBoard);
			}
		}
		testBoard.board[1][5] = new Queen(new Location(1,5), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[1][5].moveTo(new Location(3,7));
		System.out.println(testBoard.toString());
		testBoard.board[1][5] = new King(new Location(1,5), Color.BLACK, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[3][7].moveTo(new Location(1,5));
		System.out.println(testBoard.toString());
		testBoard.board[1][5].moveTo(new Location(3,7));
		System.out.println(testBoard.toString());
		testBoard.board[3][7].moveTo(new Location(0,4));
		System.out.println(testBoard.toString());

		Exception e = assertThrows(Exception.class, () -> {
			testBoard.board[3][7].moveTo(new Location(0,1));
		});
		String expectedString = "Invalid";
		String exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		testBoard.board[0][7] = new King(new Location(0,7), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());

		e = assertThrows(Exception.class, () -> {
			testBoard.board[3][7].moveTo(new Location(0,7));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[3][7].moveTo(new Location(1,6));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);
	}
	
	@Test
	void testKingMovements() throws InvalidMoveException, InvalidLocationException {
		Board testBoard = new Board();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				testBoard.board[row][col] = new EmptyPiece(new Location(row, col), testBoard);
			}
		}

		testBoard.board[1][5] = new King(new Location(1,5), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[1][5].moveTo(new Location(2,5));
		assertTrue(testBoard.board[1][5].isEmpty());
		assertEquals("K",testBoard.board[2][5].rep);
		System.out.println(testBoard.toString());
		testBoard.board[2][5].moveTo(new Location(2,6));
		assertTrue(testBoard.board[2][5].isEmpty());
		assertEquals("K",testBoard.board[2][6].rep);
		System.out.println(testBoard.toString());
		testBoard.board[1][5] = new Rook(new Location(1,5), Color.BLACK, testBoard);
		testBoard.board[2][6].moveTo(new Location(2,5));
		assertTrue(testBoard.board[2][6].isEmpty());
		assertEquals("K",testBoard.board[2][5].rep);
		System.out.println(testBoard.toString());
		testBoard.board[2][5].moveTo(new Location(1,5));
		assertTrue(testBoard.board[1][6].isEmpty());
		assertEquals("K",testBoard.board[1][5].rep);
		System.out.println(testBoard.toString());

		Exception e = assertThrows(Exception.class, () -> {
			testBoard.board[1][5].moveTo(new Location(0,1));
		});
		String expectedString = "Invalid";
		String exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[1][5].moveTo(new Location(5,1));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		testBoard.board[1][5].moveTo(new Location(0,4));
		assertTrue(testBoard.board[1][5].isEmpty());
		assertEquals("K",testBoard.board[0][4].rep);
		System.out.println(testBoard.toString());
	}

	@Test
	void testBishopMovements() throws InvalidLocationException, InvalidMoveException {

		Board testBoard = new Board();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				testBoard.board[row][col] = new EmptyPiece(new Location(row, col), testBoard);
			}
		}
		testBoard.board[1][5] = new Bishop(new Location(1,5), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[1][5].moveTo(new Location(2,6));
		assertTrue(testBoard.board[1][5].isEmpty());
		assertEquals("B",testBoard.board[2][6].rep);
		System.out.println(testBoard.toString());
		testBoard.board[2][6].moveTo(new Location(0,4));
		assertTrue(testBoard.board[1][5].isEmpty());
		assertEquals("B",testBoard.board[0][4].rep);
		System.out.println(testBoard.toString());

		testBoard.board[0][4].moveTo(new Location(3,7));
		assertTrue(testBoard.board[0][4].isEmpty());
		assertEquals("B",testBoard.board[3][7].rep);
		System.out.println(testBoard.toString());

		testBoard.board[1][5] = new Bishop(new Location(1,5), Color.BLACK, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[3][7].moveTo(new Location(1,5));
		assertTrue(testBoard.board[3][7].isEmpty());
		assertEquals("B",testBoard.board[1][5].rep);
		System.out.println(testBoard.toString());

		Exception e = assertThrows(Exception.class, () -> {
			testBoard.board[1][5].moveTo(new Location(0,1));
		});
		String expectedString = "Invalid";
		String exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[1][5].moveTo(new Location(7,7));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[1][5].moveTo(new Location(1,6));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);
	}

	@Test
	void testKnightMovements() throws InvalidLocationException, InvalidMoveException {

		Board testBoard = new Board();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				testBoard.board[row][col] = new EmptyPiece(new Location(row, col), testBoard);
			}
		}
		testBoard.board[0][0] = new Knight(new Location(0,0), Color.WHITE, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[0][0].moveTo(new Location(2,1));
		assertTrue(testBoard.board[0][0].isEmpty());
		assertEquals("N",testBoard.board[2][1].rep);
		System.out.println(testBoard.toString());
		testBoard.board[0][0] = new Knight(new Location(0,0), Color.BLACK, testBoard);
		System.out.println(testBoard.toString());
		testBoard.board[0][0].moveTo(new Location(2,1));
		assertTrue(testBoard.board[0][0].isEmpty());
		assertEquals("n",testBoard.board[2][1].rep);
		System.out.println(testBoard.toString());
		testBoard.board[2][1].moveTo(new Location(3,3));
		assertTrue(testBoard.board[2][1].isEmpty());
		assertEquals("n",testBoard.board[3][3].rep);
		System.out.println(testBoard.toString());

		Exception e = assertThrows(Exception.class, () -> {
			testBoard.board[3][3].moveTo(new Location(3,4));
		});
		String expectedString = "Invalid";
		String exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

		e = assertThrows(Exception.class, () -> {
			testBoard.board[3][3].moveTo(new Location(3,6));
		});
		expectedString = "Invalid";
		exceptionMessage = e.getMessage();
		assertEquals(expectedString, exceptionMessage);

	}
}

