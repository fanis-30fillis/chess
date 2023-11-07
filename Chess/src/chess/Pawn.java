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

	String moveIsLegal(Location newLoc) 
	{

		String result = standardLocationChecks(newLoc);
		// performs the standard location checks
		if(result.length() != 0) {
			return result; 
		}

		// we can't move backwards or sideways
		// if the pawn is WHITE
		if(this.color == Color.WHITE) {
			// we can't move backwards
			if(newLoc.getRow() <= loc.getRow()) {
				return "A pawn can't move backwards or sideways";
			}
		} else {
			// the pawn is BLACK 
			// we can't move backwards
			if(newLoc.getRow() >= loc.getRow()) {
				return "A pawn can't move backwards or sideways";
			}
		}

		// after the first move the pawn can only move one tile up
		// or diagonally only to capture an enemy piece
		if(hasMoved && chebyshevDistance(loc, newLoc) > 1) {return "Can't move this far";} 
		if(!hasMoved && chebyshevDistance(loc, newLoc) > 2) {return "Can't move this far";}

		if(newLoc.getCol() != loc.getCol())
		{
			if(Math.abs(newLoc.getCol() - loc.getCol()) > 1) {
				return "Can't move sideways";
			}

			// if a piece exists on the board
			if(!board.board[newLoc.getRow()][newLoc.getCol()].isEmpty()) {
				// if it's the same color as ours then it's not a valid move
				if (board.board[newLoc.getRow()][newLoc.getCol()].color == color) {
					return "A piece of the same color is in the destination location";
				}
			} else {
				// can't make diagonal moves if an enemy piece isn't at the tile
				return "Can't move diagonally if an enemy piece isn't at this location";
			}
		}
		return "";
	}
}