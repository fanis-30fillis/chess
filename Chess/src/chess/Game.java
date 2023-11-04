package chess;

import java.util.Scanner;

public class Game {
	final String help = "help placeholder";

	boolean isValidString(String move) {

		for(int cnt = 0; cnt < move.length(); cnt++) {

			char currentChar = move.charAt(cnt);
			// if the currentChar isn't a letter or a number
			// return false
			if((currentChar < 'a' || currentChar > 'h') &&
					(currentChar < '0' || currentChar > '8')) {
				return false;
			}
		}
		return true;
	}

	void handleMove(String move) {

		if(!isValidString(move)) {
			System.out.println("String isn't a valid move indicator");
			return;
		}

		// calculates the locations
		Location loc1 = new Location(move.substring(0, 2));
		Location loc2 = new Location(move.substring(2, 4));
		System.out.println(loc1.loc);
		System.out.println(loc1.getRow());
		System.out.println(loc1.getCol());
		System.out.println("------------");

		System.out.println(loc2.loc);
		System.out.println(loc2.getRow());
		System.out.println(loc2.getCol());
		System.out.println("------------");

	}

	void play() {
		final Scanner scan = new Scanner(System.in);

		while(true) {
			System.out.println("Input command");
			String in = scan.nextLine();
			// if the user hasn't given anything continue
			if(in.length() == 0 || in.length() == 1) {continue;}

			if(in.charAt(0) == ':') {
				switch(in.charAt(1)) {
				case 'h':
					System.out.println(help);
					break;
				case 's':
					System.out.println("save");
					break;
				case 'o':
					System.out.println("load");
				}

				if(in.charAt(1) == 'x') {
					System.out.println("Goodbye");
					break;
				}
				continue;
			} 

			if(in.length() == 4) {
				handleMove(in);
			}
		}
	}
}
