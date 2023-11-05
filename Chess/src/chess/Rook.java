package chess;

class Rook extends Piece {

	Rook(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "R" : "r";
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

		// if its not vertical or horizontal the movement is invalid
		if(newLoc.getRow() != loc.getRow() && newLoc.getCol() != loc.getCol() ) {
			return false;
		}
		
		if(newLoc.getRow() == loc.getRow()) {
			return board.freeHorizontalPath(loc, newLoc);
		} else {
			return board.freeVerticalPath(loc, newLoc);
		}
	}
}