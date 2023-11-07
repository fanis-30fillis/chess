package chess;

class Bishop extends Piece {
	Bishop(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "B" : "b";
	}

	// checks whether or not the move is legal
	String moveIsLegal(Location newLoc) {

		// gets the resulting string (possibly an error message)
		// after performing standard checks
		String result = standardLocationChecks(newLoc);
		// if the string is non empty
		if(result.length() != 0) {
			// returns the messages returned
			return result;
		}

		// if it's not diagonal then we can't move there
		if(Math.abs(loc.getRow() - newLoc.getRow()) !=
				Math.abs(loc.getCol() - newLoc.getCol())) {
			return "The final location is not diagonal to the starting one";
		}

		// since the movement is diagonal determine what sort 
		// of diagonal it is
		if(checkDiagonalMovement(loc, newLoc)) {
			return "";
		} else {
			return "A piece exists between the two locations";
		}
	}
}