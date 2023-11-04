package chess;

public class Board {
	Color currentColor = Color.BLACK;
	// creates the array that will hold all the pieces
	final Piece board[][] = new Piece[8][8];

	void movePiece(Location from, Location to) {
		Piece p = board[from.getRow()][from.getCol()];
		board[to.getRow()][to.getCol()] = p;
		board[from.getRow()][from.getCol()] = null;
	}

	boolean freeAntidiagonalPath(Location from, Location to) 
	{
		int currentRow = to.getRow();
		int currentCol = to.getCol();
		int upperLimitRow = from.getRow();
		int upperLimitCol = from.getCol();

		if(from.getRow() < to.getRow()) {
			currentRow = from.getRow();
			currentCol = from.getCol();
			upperLimitRow = to.getRow();
			upperLimitCol = to.getCol();
		}

		// due to the prefix increment it will skip the first and last points 
		// on it's own
		while(++currentRow < upperLimitRow && --currentCol > upperLimitCol) {
			// if a Piece exists on this board then the move can't be performed
			if(board[currentRow][currentCol] != null) {
				return false;
			}
		}

		return true;
	}

	boolean freeDiagonalPath(Location from, Location to) 
	{
		int currentRow = to.getRow();
		int currentCol = to.getCol();
		int upperLimitRow = from.getRow();
		int upperLimitCol = from.getCol();

		if(from.getRow() < to.getRow()) {
			currentRow = from.getRow();
			currentCol = from.getCol();
			upperLimitRow = to.getRow();
			upperLimitCol = to.getCol();
		}

		while(++currentRow <= upperLimitRow && ++currentCol <= upperLimitCol) {
			if(board[currentRow][currentCol] != null) {
				return false;
			}
		}

		return true;
	}
	
	boolean freeVerticalPath(Location from, Location to)
	{
		int lowerLimit = to.getRow() + 1; 
		int upperLimit = from.getRow() - 1;

		if(from.getRow() < to.getRow()) {
			lowerLimit = from.getRow() + 1; 
			upperLimit = to.getRow() - 1;
		}

		for(int cnt = lowerLimit; cnt < upperLimit; cnt++) {
			if(board[cnt][from.getCol()] != null) {
				return false;
			}
		}

		return true;
	}

	boolean freeHorizontalPath(Location from, Location to)
	{
		int lowerLimit = to.getCol() + 1; 
		int upperLimit = from.getCol() - 1;

		if(from.getCol() < to.getCol()) {
			lowerLimit = from.getCol() + 1; 
			upperLimit = to.getCol() - 1;
		}

		for(int cnt = lowerLimit; cnt < upperLimit; cnt++) {
			if(board[from.getRow()][cnt] != null) {
				return false;
			}
		}

		return true;
	}

	void init()
	{
		// TODO empty the board before

		int row = 6;

		for(int cnt = 0; cnt < 8; cnt++) {
			board[row][cnt] = new Pawn(new Location(row, cnt), currentColor, this);
		}

		board[7][0] = new Rook(new Location(7, 0), currentColor, this);
		board[7][7] = new Rook(new Location(7, 7), currentColor, this);

		board[7][1] = new Knight(new Location(7, 1), currentColor, this);
		board[7][6] = new Knight(new Location(7, 6), currentColor, this);

		board[7][2] = new Bishop(new Location(7, 2), currentColor, this);
		board[7][5] = new Bishop(new Location(7, 5), currentColor, this);

		board[7][3] = new Queen(new Location(7, 3), currentColor, this);
		board[7][4] = new King(new Location(7, 4), currentColor, this);


		currentColor = currentColor.nextColor();
		row = 1;

		for(int cnt = 0; cnt < 8; cnt++) {
			board[row][cnt] = new Pawn(new Location(row, cnt), currentColor, this);
		}

		board[0][0] = new Rook(new Location(0, 0), currentColor, this);
		board[0][7] = new Rook(new Location(0, 7), currentColor, this);

		board[0][1] = new Knight(new Location(0, 1), currentColor, this);
		board[0][6] = new Knight(new Location(0, 6), currentColor, this);

		board[0][2] = new Bishop(new Location(0, 2), currentColor, this);
		board[0][5] = new Bishop(new Location(0, 5), currentColor, this);

		board[0][3] = new Queen(new Location(0, 3), currentColor, this);
		board[0][4] = new King(new Location(0, 4), currentColor, this);
	}
	
	private boolean checkValidLocation (Location loc) {
		if(loc.getRow() < 0 || loc.getCol() < 0) {
			return false;
		}
		if(loc.getRow() > 7 || loc.getCol() > 7) {
			return false;
		}
		return true;
	}

	// returns the piece at that position
	// could be null
	public Piece getPieceAt(Location loc) throws InvalidLocationException {
		if(!checkValidLocation(loc)) {
			throw new InvalidLocationException("Invalid");
		}
		return board[loc.getRow()][loc.getCol()];
	}

	public String toString()
	{
		// will use the stringbuilder to make the final representation of the array
		StringBuilder builder = new StringBuilder(107);
		builder.append(" abcdefgh\n");
		for(int row = 8; row > 0; row--) {
			builder.append(Integer.toString(row));
			for(int col = 0; col < 8; col++) {
				if(this.board[row-1][col] == null) {
					builder.append(" ");
				} else {
					builder.append(board[row-1][col].rep);
				}
			}
			builder.append(Integer.toString(row));
			builder.append("\n");
		}
		builder.append(" abcdefgh");
		return builder.toString();
	}
}
