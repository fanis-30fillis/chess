package chess;

public class Board {
	Color currentColor = Color.BLACK;
	// creates the array that will hold all the pieces
	final Piece board[][] = new Piece[8][8];

	// moves the piece from the starting location to 
	// the last one assuming that it's checked
	void movePiece(Location from, Location to) {
		// grabs the piece from the starting location 
		Piece p = board[from.getRow()][from.getCol()];
		// moves it to it's new place
		board[to.getRow()][to.getCol()] = p;
		// removes it from it's previous place
		board[from.getRow()][from.getCol()] = new EmptyPiece(from, this);
	}
	
	void movePieceCapturing(Location from, Location to) {
		movePiece(from, to);
	}

	boolean freeAntidiagonalPath(Location from, Location to) 
	{
		// assumes the starting row and column are the to position
		int currentRow = to.getRow();
		int currentCol = to.getCol();
		// assumes that the from positions are upper than the to
		// and makes them the upper limits initially
		int upperLimitRow = from.getRow();
		int upperLimitCol = from.getCol();

		// if the destination location is higher than the current one
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
			if(!board[currentRow][currentCol].isEmpty()) {
				return false;
			}
		}

		return true;
	}

	// checks whether or not a Diagonal path is free from any 
	// obstacles
	boolean freeDiagonalPath(Location from, Location to) 
	{
		// initially assumes that the from location is upper than the to
		int currentRow = to.getRow();
		int currentCol = to.getCol();
		int upperLimitRow = from.getRow();
		int upperLimitCol = from.getCol();

		// if the destination is higher than the initial position
		if(from.getRow() < to.getRow()) {
			currentRow = from.getRow();
			currentCol = from.getCol();
			upperLimitRow = to.getRow();
			upperLimitCol = to.getCol();
		}

		// from the lower limits to the higher limits
		while(++currentRow < upperLimitRow && ++currentCol < upperLimitCol) {
			// if there is a pawn in the middle (regardless of color)
			if(!board[currentRow][currentCol].isEmpty()) {
				// it's not free
				return false;
			}
		}

		// if it hasn't found an object between the positions then we can make 
		// the move
		return true;
	}
	
	boolean freeVerticalPath(Location from, Location to)
	{
		// since it's vertical we only care about the rows
		// assumes that the destination is higher than the 
		// source
		int lowerLimit = to.getRow() + 1; 
		int upperLimit = from.getRow() - 1;

		// if the source is higher than the destination flip 
		// the limits
		if(from.getRow() < to.getRow()) {
			lowerLimit = from.getRow() + 1; 
			upperLimit = to.getRow() - 1;
		}

		// for each position in between them
		for(int cnt = lowerLimit; cnt <= upperLimit; cnt++) {
			// if there is an obstacle return false
			if(!board[cnt][from.getCol()].isEmpty()) {
				return false;
			}
		}

		// since it hasn't returned false then it's free
		return true;
	}

	boolean freeHorizontalPath(Location from, Location to)
	{
		// we only care about the columns since it's horizontal
		// assumes that the rightmost piece is the from
		int lowerLimit = to.getCol() + 1; 
		int upperLimit = from.getCol() - 1;

		// if the starting point is the 
		if(from.getCol() < to.getCol()) {
			lowerLimit = from.getCol() + 1; 
			upperLimit = to.getCol() - 1;
		}

		// for each of the intermediate positions
		for(int cnt = lowerLimit; cnt < upperLimit; cnt++) {
			// if a piece exists there return false
			if(!board[from.getRow()][cnt].isEmpty()) {
				return false;
			}
		}

		// since it hasn't returned false no objects exist between
		// the target and the source
		return true;
	}

	void init()
	{
		// initializes the array
		for(int row = 0; row < 8; row++) {
			for(int col=0; col < 8; col++) {
				try {
					board[row][col] = new EmptyPiece(new Location(row, col), this);
				} catch (InvalidLocationException e) {
					// it should never throw this exception
					System.out.println("Fatal Error: " + e.getMessage());
					return;
				}
			}
		}

		int row = 6;
		try { 
			for(int cnt = 0; cnt < 8; cnt++) {
				board[row][cnt] = new Pawn(new Location(row, cnt), Color.BLACK, this);
			}

			board[7][0] = new Rook(new Location(7, 0), Color.BLACK, this);
			board[7][7] = new Rook(new Location(7, 7), Color.BLACK, this);

			board[7][1] = new Knight(new Location(7, 1), Color.BLACK, this);
			board[7][6] = new Knight(new Location(7, 6), Color.BLACK, this);

			board[7][2] = new Bishop(new Location(7, 2), Color.BLACK, this);
			board[7][5] = new Bishop(new Location(7, 5), Color.BLACK, this);

			board[7][3] = new Queen(new Location(7, 3), Color.BLACK, this);
			board[7][4] = new King(new Location(7, 4), Color.BLACK, this);
		} catch (InvalidLocationException e) {
			System.out.println(e.getMessage());
			return;
		}

		row = 1;

		try {
			for(int cnt = 0; cnt < 8; cnt++) {
				board[row][cnt] = new Pawn(new Location(row, cnt), Color.WHITE, this);
			}

			board[0][0] = new Rook(new Location(0, 0), Color.WHITE, this);
			board[0][7] = new Rook(new Location(0, 7), Color.WHITE, this);

			board[0][1] = new Knight(new Location(0, 1), Color.WHITE, this);
			board[0][6] = new Knight(new Location(0, 6), Color.WHITE, this);

			board[0][2] = new Bishop(new Location(0, 2), Color.WHITE, this);
			board[0][5] = new Bishop(new Location(0, 5), Color.WHITE, this);

			board[0][3] = new Queen(new Location(0, 3), Color.WHITE, this);
			board[0][4] = new King(new Location(0, 4), Color.WHITE, this);
		} catch (InvalidLocationException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private String checkValidLocation (Location loc) {
		StringBuilder resultingString = new StringBuilder(90);
		if(loc.getRow() < 0 || loc.getRow() > 7 ) {
			resultingString.append("Row must be within the [0,7] range inclusive");

		}
		if(loc.getCol() < 0 || loc.getCol() > 7) {
			resultingString.append("Column must be within the [0,7] range inclusive");
		}
		return resultingString.toString();
	}

	// returns the piece at that position
	public Piece getPieceAt(Location loc) throws InvalidLocationException {
		String resultingString = checkValidLocation(loc);
		if(resultingString.length() > 0) {
			throw new InvalidLocationException(resultingString);
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
				if(this.board[row-1][col].isEmpty()) {
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
