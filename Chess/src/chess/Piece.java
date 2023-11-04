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

	int chebyshevDistance(Location loc1, Location loc2) {
		return Math.max(Math.abs(loc1.getRow() - loc2.getRow()),
				loc1.getCol() - loc2.getCol());
	}
	
	boolean boundsCheck(Location loc) {
		return (loc.getCol() >= 0 && loc.getCol() <= 7) &&
				(loc.getRow() >= 0 && loc.getRow() <= 7);
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		// checks if the move is Legal
		if (!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid");
		}

		// assuming that all the proper check have been handled in moveIsLegal
		board.board[newLoc.getRow()][newLoc.getCol()] = this;
		board.board[loc.getRow()][loc.getCol()] = null;
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

class King extends Piece {

	King(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "K" : "k";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		if(!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid move");
		}
	}

	boolean moveIsLegal(Location newLoc) {

		if(newLoc.equals(loc)) {
			return false;
		}
		// if we are out of board bounds then we can't move there
		if(!boundsCheck(newLoc)) {
			return false;
		}

		// TODO maybe implement castling 

		// the king can only move to it's immediate neighboring tiles,
		// therefore moving him beyond that limit is invalid
		if(chebyshevDistance(loc, newLoc) > 1) {
			return false;
		}
		
		// if there is a piece in the board 
		if(board.board[newLoc.getRow()][newLoc.getCol()] != null) {
			// if the board tile we are moving to has another piece of the same color
			// we can't move there 
			if(board.board[newLoc.getRow()][newLoc.getCol()].color == color) {
				return false;
			}
		}

		return true;
	}
}

class Pawn extends Piece {
	boolean hasMoved;
	Pawn (Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "P" : "p";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		if(!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid move");
		}
		hasMoved = true;
	}

	boolean moveIsLegal(Location newLoc) 
	{

		if(newLoc.equals(loc)) {
			return false;
		}
		if(!boundsCheck(newLoc)) {
			return false;
		}

		// can't move backwards or sideways
		if(newLoc.getRow() <= loc.getRow()) {
			return false;
		}

		// after the first move the pawn can only move one tile up
		// or diagonally only to capture an enemy piece
		if(hasMoved && chebyshevDistance(loc, newLoc) > 1) {return false;} 
		if(!hasMoved && chebyshevDistance(loc, newLoc) > 2) {return false;}

		if(newLoc.getCol() != loc.getCol())
		{
			if(Math.abs(newLoc.getCol() - loc.getCol()) > 1) {
				return false;
			}

			// if a piece exists on the board
			if(board.board[newLoc.getRow()][newLoc.getCol()] != null) {
				// if it's the same color as ours then it's not a valid move
				if (board.board[newLoc.getRow()][newLoc.getCol()].color == color) {
					return false;
				}
			} else {
				// can't make diagonal moves if an enemy piece isn't at the tile
				return false;
			}
		}
		return true;
	}
}

class Queen extends Piece {

	Queen(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "Q" : "q";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		if(!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid move");
		}
	}

	boolean moveIsLegal(Location newLoc) {

		if(newLoc.equals(loc)) {
			return false;
		}
		if(!boundsCheck(newLoc)) {
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

		if(newLoc.equals(loc)) {
			return false;
		}
		if(!boundsCheck(newLoc)) {
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

class Knight extends Piece {
	Knight(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "N" : "n";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		if(!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid move");
		}
	}

	boolean moveIsLegal(Location newLoc) {

		if(newLoc.equals(loc)) {
			return false;
		}
		if(!boundsCheck(newLoc)) {
			return false;
		}

		if(Math.abs(newLoc.getRow()-loc.getRow()) == 1 &&
				Math.abs(newLoc.getCol() - loc.getCol()) == 2 ) {
			// short circuit logic makes sure that if the object doesn't exist
			// then it won't check it's color therefore won't throw a 
			// Not found exception 
			if(board.board[newLoc.getRow()][newLoc.getCol()] == null ||
					board.board[newLoc.getRow()][newLoc.getCol()].color != this.color) {
				return true;
			} else {
				// if there is an object in the board and it's the same color 
				// as us then we can't move there
				return false;
			}
		}

		return true;
	}
}

class Bishop extends Piece {
	Bishop(Location loc, Color c, Board b) {
		super(loc, c, b);
		rep = c == Color.WHITE ? "B" : "b";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		if(!moveIsLegal(newLoc)) {
			throw new InvalidMoveException("Invalid move");
		}
	}

	boolean moveIsLegal(Location newLoc) {
		if(newLoc.equals(loc)) {
			return false;
		}
		if(!boundsCheck(newLoc)) {
			return false;
		}

		// if it's not diagonal then we can't move there
		if(Math.abs(loc.getRow() - newLoc.getRow()) !=
				Math.abs(loc.getCol() - newLoc.getCol())) {
			return false;
		}

		return true;
	}
}