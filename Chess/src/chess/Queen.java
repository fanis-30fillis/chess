package chess;

class Queen extends Piece {

	Queen(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "Q" : "q";
	}

	String moveIsLegal(Location newLoc) {
		
		String result = standardLocationChecks(newLoc);
		if(result.length() != 0) {
			return result;
		}

		// if the movement is not diagonal
		if(Math.abs(newLoc.getRow() - loc.getRow()) != Math.abs(newLoc.getCol() - loc.getCol())) {
			// if it's not horizontal 
			if(newLoc.getRow() != loc.getRow() && newLoc.getCol() != loc.getCol()) {
				return "The movement isn't horizontal, vertical or valid diagonal";
			}
			// if the move is within the same row
			if(newLoc.getRow() == loc.getRow() ) {

				if(board.freeHorizontalPath(loc, newLoc)) {
					return "";
				} else {
					return "The horizontal path isn't empty";
				}
			} else {
				// else the move is vertical
				// check vertical path
				if(board.freeVerticalPath(loc, newLoc)) {
					return "";
				} else {
					return "The vertical path isn't empty";
				}
			}
		} else {
			if(checkDiagonalMovement(loc, newLoc)) {
				return "";
			} else {
				return "The diagonal path isn't empty";
			}
		}
	}

}