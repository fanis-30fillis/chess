package chess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game {

	final Board gameBoard = new Board();
	final String help = """
			Chess game \n
			:h print help\n
			:o load game\n
			:s save game\n
			After that command is given the program will ask you to input\n
			the name of the file and it will save the game to this folder\n
			:x exit program\n\n

			In general the commands are given in the following format:\n
			a0b0\n
			where a0 selects the piece that is in the position a0 and\n
			moves it to the position b0\n
			The commands to move pieces are given in coordinates like\n
			a0a1\n
			where the piece in the position a0 is moved to the a1 position\n
			depending if it's possible.\n
			The program displays 
			""";
	Color colorMoves = Color.WHITE;
	StringBuilder whiteMoves = new StringBuilder();
	StringBuilder blackMoves = new StringBuilder();

	private void printHelp() {System.out.println(help);}

	private boolean isValidString(String move) {

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
	
	private void openFile() {
		Scanner scan = new Scanner(System.in);
		String answer = "";

		while(whiteMoves.length() != 0 && !answer.equals("y") && !answer.equals("n")) {
			System.out.print("You have a game running, do you want to abandon it ?[y/n]");
			answer = scan.nextLine();
		}

		scan.close();
		if(answer.equals("n")) {
			// cancel the op
			return;
		}
		System.out.print("Input the name of the file to open: ");
	}

	private void handleMove(String move)
	{
		if(!isValidString(move)) {
			System.out.println("String isn't a valid move indicator");
			return;
		}

		// calculates the locations
		Location loc1 = new Location(move.substring(0, 2));
		Location loc2 = new Location(move.substring(2, 4));
		Piece p = gameBoard.board[loc1.getRow()][loc1.getCol()];

		if(p == null) {
			System.out.println("No piece exists at this point");
			return;
		}

		try {
			p.moveTo(loc2);
		} catch (InvalidMoveException e) {
			System.out.println("Invalid Move");
			return;
		}

		if(colorMoves == Color.WHITE) {
			whiteMoves.append(move);
		} else {
			blackMoves.append(move);
		}

		// if the move is completed then the next color gets to go
		colorMoves.nextColor();
	}
	
	void saveGame() {
		System.out.print("Input File to save to: ");
		Scanner scan = new Scanner(System.in);
		String filename = scan.nextLine();
		scan.close();
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(filename));
		} catch(Exception e) {
			System.out.println("Unable to create file, try a different filename");
			scan.close();
			return;
		}
		try {
			bw.append(whiteMoves.toString()); bw.append("\n");
			bw.append(blackMoves.toString()); bw.append("\n");
		} catch (IOException e) {
			System.out.println("Error occured when writing to file");
		}

		try {
			bw.close();
		} catch (IOException e) {
			System.out.println("Error occured when closing the BufferedWriter");
		}
	}

	void play() {
		this.gameBoard.init();
		final Scanner scan = new Scanner(System.in);

		while(true) {
			System.out.println(this.gameBoard.toString());
			System.out.println("\n\n");
			System.out.print(colorMoves);
			System.out.println(" TURN");
			System.out.println("Input command");
			String in = scan.nextLine();
			// if the user hasn't given anything continue
			if(in.length() == 0 || in.length() == 1) {continue;}

			// if we have a command to the program
			if(in.charAt(0) == ':') {
				switch(in.charAt(1)) {
				case 'h':
					printHelp();
					break;
				case 's':
					saveGame();
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
		scan.close();
	}
}
