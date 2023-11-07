package chess;

class Pawn extends Piece {

	boolean hasMoved = false;

	Pawn (Location loc, Color c, Board b) {
		// calls the super constructor so that we don't repeat code
		super(loc, c, b);
		// sets the representation 
		rep = c == Color.WHITE ? "P" : "p";
	}

	void moveTo(Location newLoc) throws InvalidMoveException {
		// pawn moveTo is the same as the Piece's 
		super.moveTo(newLoc);
		// sets the hasMoved which changes the pawns possible movements
		hasMoved = true;
	}

	boolean moveIsLegal(Location newLoc) 
	{

		// performs the standard location checks
		if(!standardLocationChecks(newLoc)) {
			return false;
		}

		// we can't move backwards or sideways
		// if the pawn is WHITE
		if(this.color == Color.WHITE) {
			// we can't move backwards
			if(newLoc.getRow() <= loc.getRow()) {
				return false;
			}
		} else {
			// the pawn is BLACK 
			// we can't move backwards
			if(newLoc.getRow() >= loc.getRow()) {
				return false;
			}
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
			if(!board.board[newLoc.getRow()][newLoc.getCol()].isEmpty()) {
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