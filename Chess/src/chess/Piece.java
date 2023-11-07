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
	
	boolean boundsCheck(Location loc) {
		return (loc.getCol() >= 0 && loc.getCol() <= 7) &&
				(loc.getRow() >= 0 && loc.getRow() <= 7);
	}
	
	// performs the standard location checks that are needed
	// in every move operation
	boolean standardLocationChecks(Location newLoc) {
		// if the newLocation is the same as the old one
		if(newLoc.equals(loc)) {
			return false;
		}
		// if we are out of bounds
		if(!boundsCheck(newLoc)) {
			return false;
		}
		// if the final position is not valid
		if(!checkFinalPosition(newLoc)) {
			return false;
		}

		// if none of these triggered then return true
		return true;
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

	boolean checkFinalPosition(Location newLoc) {
		if(board.board[newLoc.getRow()][newLoc.getCol()].isEmpty() ||
			board.board[newLoc.getRow()][newLoc.getCol()].color != this.color) {
			return true;
		} else {
			// if there is an object in the board and it's the same color 
			// as us then we can't move there
			return false;
		}
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		// checks if the move is Legal
		if (!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid");
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
	abstract boolean moveIsLegal(Location newLoc);

	@Override
	public String toString() {
		return rep;
	}
}