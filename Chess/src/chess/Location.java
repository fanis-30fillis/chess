package chess;

public class Location {
	private int row;
	private int col;
	private String loc;
	Location(int row, int col) throws InvalidLocationException {
		// check whether or not the row is out array bounds
		if(row < 0 || row > 7) {
			throw new InvalidLocationException("Given row is out of bounds");
		}

		// check whether or not the column is out of bounds
		if(col < 0 || col > 7) {
			throw new InvalidLocationException("Given column is out of bounds");
		}

		this.row = row;
		this.col = col;
		// using ASCII table to find the correct letter (adding 97 makes 0 a char 'a')
		char firstLoc = (char) (col+97);
		// using ASCII table to find the correct number (adding 49 makes 0 a char '1')
		char secondLoc = (char) (row+49);
		// uses a StringBuilder to create a string out of the two chars
		loc = new StringBuilder(2).append(firstLoc).append(secondLoc).toString();
	}

	Location(String loc) throws InvalidLocationException {

		// 70 char string builder
		StringBuilder errorMessage = new StringBuilder(70);
		// if the column indicator is not in the correct range
		if(loc.charAt(0) < 'a' || loc.charAt(0) > 'h') {
			// add to the error message to inform the user
			errorMessage.append("Invalid column indicator: " + loc.charAt(0) + "\n");
		}

		// if the row indicator is not correct
		if (loc.charAt(1) < '1' || loc.charAt(1) > '8') {
			// add to the error message to inform the user
			errorMessage.append("Invalid row indicator: " + loc.charAt(1));
		}

		// if strings have been appended to the StringBuilder then an error 
		// has occurred
		if(errorMessage.length() > 0) {
			// throw the exception with the appropriate message
			throw new InvalidLocationException(errorMessage.toString());
		}

		this.loc = loc;
		// using the ASCII table to get the coord ('1' is 49)
		row = loc.charAt(1)-49;
		// using the ASCII table to get the coord ('a' is 97)
		col = loc.charAt(0)-97;
	}

	public String toString() {
		return loc;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
	
	public boolean equals(Location loc) {
		return row == loc.getRow() && col == loc.getCol();
	}
}
