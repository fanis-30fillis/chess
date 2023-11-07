package chess;

public enum Color {
	// the enum values
	BLACK,
	WHITE;
	// changes the current value of the enum
	Color nextColor() {
		// if the current value is BLACK return WHITE
		if(this == BLACK) {
			return WHITE;
		} else {
			// else the current value is WHITE return BLACK
			return BLACK;
		}
	}
}
