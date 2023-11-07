package chess;

class Queen extends Piece {

	Queen(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "Q" : "q";
	}

	boolean moveIsLegal(Location newLoc) {

		if(!standardLocationChecks(newLoc)) {
			return false;
		}

		// if the movement is not diagonal
		if(Math.abs(newLoc.getRow() - loc.getRow()) != Math.abs(newLoc.getCol() - loc.getCol())) {
			// if it's not horizontal then return false
			if(newLoc.getRow() != loc.getRow() && newLoc.getCol() != loc.getCol()) {
				return false;
			}
			// if the move is within the same row
			if(newLoc.getRow() == loc.getRow() ) {
				// check the horizontal path
				return board.freeHorizontalPath(loc, newLoc);
			} else {
				// else the move is vertical
				// check vertical path
				return board.freeVerticalPath(loc, newLoc);
			}
		} else {
			// the movement is diagonal
			return checkDiagonalMovement(loc, newLoc);
		}
	}

}