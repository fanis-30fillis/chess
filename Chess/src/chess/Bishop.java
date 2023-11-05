package chess;

class Bishop extends Piece {
	Bishop(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "B" : "b";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		if(!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid move");
		}
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

		// the movement is diagonal
		if(loc.getRow() < newLoc.getRow()) {
			if(loc.getCol() < newLoc.getCol()) {
				return board.freeDiagonalPath(loc, newLoc);
			} else {
				return board.freeAntidiagonalPath(loc, newLoc);
			}
		} else {
			if(loc.getCol() < newLoc.getCol()) {
				return board.freeAntidiagonalPath(loc, newLoc);
			} else {
				return board.freeDiagonalPath(loc, newLoc);
			}
		}
	}
}