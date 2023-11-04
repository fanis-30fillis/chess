package chess;

public class Location {
	private int row;
	private int col;
	String loc;
	Location(int row, int col) {
		this.row = row;
		this.col = col;
		// using ASCII table to find the correct letter (adding 97 makes 0 a char 'a')
		char firstLoc = (char) (row+97);
		// using ASCII table to find the correct number (adding 49 makes 0 a char '1')
		char secondLoc = (char) (col+49);
		// uses a StringBuilder to create a string out of the two chars
		loc = new StringBuilder(2).append(firstLoc).append(secondLoc).toString();
	}

	Location(String loc) {
		this.loc = loc;
		// using the ASCII table to get the coord ('a' is 97)
		row = loc.charAt(0)-97;
		// using the ASCII table to get the coord ('1' is 49)
		col = loc.charAt(1)-49;
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
