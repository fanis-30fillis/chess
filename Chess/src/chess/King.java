package chess;

class King extends Piece {

	King(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "K" : "k";
	}

	String moveIsLegal(Location newLoc) {

		String result = standardLocationChecks(newLoc);
		if(result.length() != 0) {
			return result;
		}

		// the king can only move to it's immediate neighboring tiles,
		// therefore moving him beyond that limit is invalid
		if(chebyshevDistance(loc, newLoc) > 1) {
			return "The destination location is too far";
		}
		
		// if there is a piece in the board 
		if(!board.board[newLoc.getRow()][newLoc.getCol()].isEmpty()) {
			// if the board tile we are moving to has another piece of the same color
			// we can't move there 
			if(board.board[newLoc.getRow()][newLoc.getCol()].color == color) {
				return "A piece of the same color is in the destination location";
			}
		}

		return "";
	}
}