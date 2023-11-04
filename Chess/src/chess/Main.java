package chess;

public class Main {
	static final String header = " _____  _                     \n"
			+ "/  __ \\| |                    \n"
			+ "| /  \\/| |__    ___  ___  ___ \n"
			+ "| |    | '_ \\  / _ \\/ __|/ __|\n"
			+ "| \\__/\\| | | ||  __/\\__ \\\\__ \\\n"
			+ " \\____/|_| |_| \\___||___/|___/\n";
	public static void main(String[] args) {
		System.out.println(header);
		Game game = new Game();
		game.play();
	}
}
