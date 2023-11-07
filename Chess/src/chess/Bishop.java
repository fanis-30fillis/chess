package chess;

class Bishop extends Piece {
	Bishop(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "B" : "b";
	}

//	void moveTo(Location newLoc) throws InvalidMoveException {
//		if(!moveIsLegal(newLoc)) {
//			throw new InvalidMoveException("Invalid move");
//		}
//
//		if(board.board[newLoc.getRow()][newLoc.getCol()].isEmpty()) {board.movePiece(loc, newLoc);}
//		else {board.movePieceCapturing(loc, newLoc);}
//		loc = newLoc;
//	}

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
		// if the new location is in the top
		if(loc.getRow() < newLoc.getRow()) {
			// if the new location column is to the right of 
			// the starting position we have diagonal move /
			if(loc.getCol() < newLoc.getCol()) {
				return board.freeDiagonalPath(loc, newLoc);
			} else {
				// else we have antidiagonan \
				return board.freeAntidiagonalPath(loc, newLoc);
			}
		} else {
			// if the new location is to the right and lower of the 
			// starting we have antidiagonal \
			if(loc.getCol() < newLoc.getCol()) {
				return board.freeAntidiagonalPath(loc, newLoc);
			} else {
				// if the new location is to the left and higher than
				// the starting location /
				return board.freeDiagonalPath(loc, newLoc);
			}
		}
	}
}