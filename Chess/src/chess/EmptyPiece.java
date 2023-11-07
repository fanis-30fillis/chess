package chess;

public class EmptyPiece extends Piece {

	// color of the piece doesn't matter
	EmptyPiece(Location loc, Board board) {
		super(loc, Color.WHITE, board);
	}

	boolean isEmpty() {
		return true;
	}

	// the object has no moves and therefore it doesn't 
	// matter what it returns
	@Override
	boolean moveIsLegal(Location newLoc) {
		return false;
	}
}
