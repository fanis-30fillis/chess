package chess;

class Bishop extends Piece {
	Bishop(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "B" : "b";
	}

	boolean moveIsLegal(Location newLoc) {
		if(!standardLocationChecks(newLoc)) {
			return false;
		}

		// if it's not diagonal then we can't move there
		if(Math.abs(loc.getRow() - newLoc.getRow()) !=
				Math.abs(loc.getCol() - newLoc.getCol())) {
			return false;
		}

		// since the movement is diagonal determine what sort 
		// of diagonal it is
		return checkDiagonalMovement(loc, newLoc);
	}
}