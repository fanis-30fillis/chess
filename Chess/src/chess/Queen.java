package chess;

class Queen extends Piece {

	Queen(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "Q" : "q";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		if(!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid move");
		}
		if(board.board[newLoc.getRow()][newLoc.getCol()] == null) {board.movePiece(loc, newLoc);}
		else {board.movePieceCapturing(loc, newLoc);}
		loc = newLoc;
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
			if(newLoc.getRow() == loc.getRow() ) {
				return board.freeHorizontalPath(loc, newLoc);
			} else {
				return board.freeVerticalPath(loc, newLoc);
			}
		} else {
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

}