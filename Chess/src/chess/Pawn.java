package chess;

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
		board.movePiece(loc, newLoc);
	}

	boolean moveIsLegal(Location newLoc) 
	{

		if(!standardLocationChecks(newLoc)) {
			return false;
		}

		// we can't move backwards or sideways
		if(this.color == Color.WHITE) {
			if(newLoc.getRow() <= loc.getRow()) {
				return false;
			}
		} else {
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