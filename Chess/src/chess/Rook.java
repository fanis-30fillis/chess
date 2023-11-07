package chess;

class Rook extends Piece {

	Rook(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "R" : "r";
	}

	String moveIsLegal(Location newLoc) {

		String result = standardLocationChecks(newLoc);
		if(result.length() != 0) {
			return result;
		}

		// if its not vertical or horizontal the movement is invalid
		if(newLoc.getRow() != loc.getRow() && newLoc.getCol() != loc.getCol() ) {
			return "The movement isn't horizontal or vertical";
		}
		
		if(newLoc.getRow() == loc.getRow()) {
			if(board.freeHorizontalPath(loc, newLoc)) {
				return "";
			} else {
				return "The Horizontal path isn't free of obstacles";
			}
		} else {
			if(board.freeVerticalPath(loc, newLoc)) {
				return "";
			} else {
				return "The Vertical path isn't free of obstacles";
			}
		}
	}
}