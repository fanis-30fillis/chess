package chess;

public enum Color {
	BLACK,
	WHITE;
	public Color nextColor() {
		if(this == BLACK) {
			return WHITE;
		} else {
			return BLACK;
		}
	}
}
