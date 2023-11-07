package chess;

// the class is abstract because we wont directly use it
abstract class Piece {
	String rep;
	// a piece doesn't change color
	final Color color;
	Location loc;
	// the ref to the board never changes after the first
	// assignment
	final Board board;

	// this constructor is going to be used by all the 
	// subclasses since they all are going to have the 
	// same fields
	Piece(Location loc, Color color, Board board) {
		this.color = color;
		this.board = board;
		this.loc = loc;
	}

	boolean isEmpty() {
		return false;
	}
	
	int chebyshevDistance(Location loc1, Location loc2) {
		return Math.max(Math.abs(loc1.getRow() - loc2.getRow()),
				loc1.getCol() - loc2.getCol());
	}
	
	String boundsCheck(Location loc) {
		StringBuilder sb = new StringBuilder(100);
		if(loc.getCol() < 0 || loc.getCol() > 7) {
			sb.append("The column given is out of bounds: " + Integer.toString(loc.getCol()));
		}
		if(loc.getRow() < 0 || loc.getRow() > 7) {
			sb.append("The row given is out of bounds: " + Integer.toString(loc.getRow()));
		}
		return sb.toString();
	}
	
	// performs the standard location checks that are needed
	// in every move operation
	String standardLocationChecks(Location newLoc) {
		// if the newLocation is the same as the old one
		if(newLoc.equals(loc)) {
			return "The two positions are the same";
		}
		
		String result = boundsCheck(newLoc);
		// if we are out of bounds
		if(result.length() != 0) {
			return result;
		}

		result = checkFinalPosition(newLoc);

		// if the final position is not valid
		if(result.length() != 0) {
			return result;
		}

		// if none of these triggered then return true
		return "";
	}
	
	boolean checkDiagonalMovement(Location loc, Location newLoc) {
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

	String checkFinalPosition(Location newLoc) {
		if(board.board[newLoc.getRow()][newLoc.getCol()].isEmpty() ||
			board.board[newLoc.getRow()][newLoc.getCol()].color != this.color) {
			return "";
		} else {
			// if there is an object in the board and it's the same color 
			// as us then we can't move there
			return "There is a piece with the same color in the destination location";
		}
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		String checkMoveIsLegal = moveIsLegal(newLoc);
		// checks if the move is Legal
		if (checkMoveIsLegal.length() != 0) {
			throw new InvalidMoveException(checkMoveIsLegal);
		}

		if(!board.board[newLoc.getRow()][newLoc.getCol()].isEmpty()) {
			board.movePieceCapturing(loc, newLoc);
		} else {
			board.movePiece(loc, newLoc);
		}
		this.loc = newLoc;
	}
	
	// this function checks whether or not a move is legal 
	// and is implemented in each piece 
	abstract String moveIsLegal(Location newLoc);

	@Override
	public String toString() {
		return rep;
	}
}