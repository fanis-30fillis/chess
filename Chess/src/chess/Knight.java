package chess;

class Knight extends Piece {
	Knight(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "N" : "n";
	}

	boolean moveIsLegal(Location newLoc) {

		if(!standardLocationChecks(newLoc)) {
			return false;
		}

		// gets the horizontal and vertical distance to make the if
		// condition more readable
		final int verticalDistance = Math.abs(newLoc.getRow() - loc.getRow());
		final int horizontalDistance = Math.abs(newLoc.getCol() - loc.getCol());

		if((verticalDistance == 1 && horizontalDistance == 2) ||
				(verticalDistance == 2 && horizontalDistance == 1)) {
			// short circuit logic makes sure that if the object is empty
			// then it won't check it's color 
			if(board.board[newLoc.getRow()][newLoc.getCol()].isEmpty() ||
					board.board[newLoc.getRow()][newLoc.getCol()].color != this.color) {
				return true;
			} else {
				// if there is an object in the board and it's the same color 
				// as us then we can't move there
				return false;
			}
		} else {
			// the destination location isn't one where we can jump to
			return false;
		}
	}
}