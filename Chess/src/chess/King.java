package chess;

class King extends Piece {

	King(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "K" : "k";
	}

	boolean moveIsLegal(Location newLoc) {

		if(!standardLocationChecks(newLoc)) {
			return false;
		}

		// the king can only move to it's immediate neighboring tiles,
		// therefore moving him beyond that limit is invalid
		if(chebyshevDistance(loc, newLoc) > 1) {return false;}
		
		// if there is a piece in the board 
		if(!board.board[newLoc.getRow()][newLoc.getCol()].isEmpty()) {
			// if the board tile we are moving to has another piece of the same color
			// we can't move there 
			if(board.board[newLoc.getRow()][newLoc.getCol()].color == color) {
				return false;
			}
		}

		return true;
	}
}