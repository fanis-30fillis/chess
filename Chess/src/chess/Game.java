package chess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Game {

	private final Scanner scan = new Scanner(System.in);
	private final Board gameBoard = new Board();
	private final String help = """
			Chess game 
			:h print help
			:o load game
			:s save game
			After that command is given the program will ask you to input
			the name of the file and it will save the game to this folder
			:x exit program\n

			In general the commands are given in the following format:\n
			a0b0\n
			where a0 selects the piece that is in the position a0 and\n
			moves it to the position b0
			For example the command a2a4 in the initial table moves the Pawn
			in position a2 to position a4.
			""";
	private Color colorMoves = Color.WHITE;
	private StringBuilder whiteMoves = new StringBuilder();
	private StringBuilder blackMoves = new StringBuilder();

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
	
	private void openFile() 
	{
		this.gameBoard.init();
		String answer = "";

		while(whiteMoves.length() != 0 && !answer.equals("y") && !answer.equals("n")) {
			System.out.print("You have a game running, do you want to abandon it ?[y/n]");
			answer = scan.nextLine();
		}

		if(whiteMoves.length() != 0 && answer.equals("n")) {
			// cancel the op
			return;
		}

		answer = "";
		while(answer.length() == 0) {
			System.out.print("Input the filename to read from: ");
			answer = scan.nextLine();
		}

		Scanner fileScan = null;
		File fileToRead;
		try {
			fileToRead = new File(answer);
		} catch(NoSuchElementException e) {
			System.out.println("No such file exists");
			return;
		}

		try {
			fileScan = new Scanner(fileToRead);
		} catch (FileNotFoundException e) {
//			 if the file to load from doesn't exist then print a message to the user
			System.out.println("Couldn't find the file to load from");
			return;
		} 

		String blackMoveRawString;
		String whiteMoveRawString;
		if(fileScan.hasNextLine()) {
			whiteMoveRawString = fileScan.nextLine();
		} else {
			System.out.println("File is empty");
			fileScan.close();
			return;
		}

		if(fileScan.hasNextLine()) {
			blackMoveRawString = fileScan.nextLine();
		} else {
			System.out.println("File is incomplete");
			fileScan.close();
			return;
		}
		fileScan.close();

		if(whiteMoveRawString.length() % 4 != 0 || blackMoveRawString.length() % 4 != 0 ) {
			System.out.println("Savefile is Malformed");
			return;
		} 

		String[] whiteMoves = parseMoveString(whiteMoveRawString);
		String[] blackMoves = parseMoveString(blackMoveRawString);

		// determines the next move player based on the moves that have been played
		if(whiteMoves.length > blackMoves.length) {
			this.colorMoves = Color.BLACK;
		} else {
			this.colorMoves = Color.WHITE;
		}
		
		// the inequity comparison doesn't take into account the fact that both 
		// of the lengths are counted starting from 1 and not from 0
		for(int cnt = 0; cnt < whiteMoves.length+blackMoves.length-1; cnt++) {
			// if the last bit of the number is enabled then it's an odd number
			if(colorMoves == Color.WHITE) {
				// division by two using right shifting by one bit
				handleMove(whiteMoves[cnt >> 1]);
			} else {
				// division by two using right shifting by one bit
				handleMove(blackMoves[cnt >> 1]);
			}
		}

		if(fileScan != null) {
			fileScan.close();
		}

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
		if(p.color != colorMoves) {
			System.out.println("That Piece isn't yours");
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
		colorMoves = colorMoves.nextColor();
	}
	
	private void saveGame() {
		System.out.print("Input File to save to: ");
		String filename = scan.nextLine();
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(filename));
		} catch(Exception e) {
			System.out.println("Unable to create file, try a different filename");
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

	protected void play() {
		this.gameBoard.init();
//		final Scanner scan = new Scanner(System.in);

		while(true) {
			System.out.println(this.gameBoard.toString());
			System.out.println("\n");
			System.out.print(colorMoves);
			System.out.println(" TURN");
			System.out.println("Input command: ");
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
					openFile();
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
	
	private String[] parseMoveString(String moves) {
		String[] resultingArray = new String[moves.length()/4];
		for(int cnt = 0; cnt <= moves.length()-4; cnt+=4) {
			resultingArray[cnt/4] = moves.substring(cnt, cnt+4);
		}
		return resultingArray;
	}
}
