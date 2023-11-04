package chess;

public class Main {
	public static void main(String[] args) {
		// Board b = new Board();
//		b.init();
//		System.out.println(b.toString());
//		System.out.println(b.board[0][6].rep);
//		try {
//			b.board[0][6].moveTo(new Location(2,5));
//		} catch (Exception e){
//			System.out.println(e);
//		}
//		System.out.println(b.toString());
		Game game = new Game();
		game.play();
	}

}
