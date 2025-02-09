package chess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Game {

	// the scanner that the game will use for reading from the terminal
	private final Scanner scan = new Scanner(System.in);
	// the Board that will be used
	private final Board gameBoard = new Board();
	// help string that will be printed to the user
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
	
	// the starting color that moves is the WHITE one
	private Color colorMoves = Color.WHITE;
	// will be used to append the white moves performed
	private StringBuilder whiteMoves = new StringBuilder();
	// will be used to append the black moves performed
	private StringBuilder blackMoves = new StringBuilder();

	// prints the help to the user
	private void printHelp() {System.out.println(help);}

	// checks whether a char indicating the column is valid
	private boolean isValidCol(char col) {
		return col >= 'a' && col <= 'h';
	}

	// checks whether a char indicating the row is valid
	private boolean isValidRow(char row) {
		return row >= '1' && row <= '8';
	}

	// checks whether a move sequence is valid
	private boolean isValidString(String move) {
		return isValidCol(move.charAt(0)) && isValidCol(move.charAt(2)) &&
				isValidRow(move.charAt(1)) && isValidRow(move.charAt(3));
	}
	
	// loads a savefile
	private void openFile() 
	{
		// initializes the board removing the pieces and putting them in
		// their initial position
		String answer = "";

		// if white has moved then the user hasn't given a specific answer
		// [y/n]
		while(whiteMoves.length() != 0 && !answer.equals("y") && 
				!answer.equals("n")) {
			// inform the that the game being run will be abandoned
			System.out.print("You have a game running, do you want to abandon it ?[y/n]");
			// gets the user's answer
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
			// if the file to load from doesn't exist then print a message to the user
			System.out.println("Couldn't find the file to load from");
			return;
		} 

		String blackMoveRawString;
		String whiteMoveRawString;
		// if we have a first line take it as the white moves
		if(fileScan.hasNextLine()) {
			whiteMoveRawString = fileScan.nextLine();
		} else {
			// else inform the user
			System.out.println("File is empty");
			fileScan.close();
			return;
		}

		// if a file has a next line
		if(fileScan.hasNextLine()) {
			blackMoveRawString = fileScan.nextLine();
		} else {
			// else the file is incomplete and it's not usable
			System.out.println("Save file is incomplete");
			fileScan.close();
			return;
		}
		fileScan.close();

		// if the files have an appropriate length then 
		// inform the user that the file is malformed
		if(whiteMoveRawString.length() % 4 != 0 || 
				blackMoveRawString.length() % 4 != 0 ) {
			System.out.println("Save file is Malformed");
			return;
		} 
		
		// if the lengths are different and the difference is not one move 
		// from the white then the save file has been tampered
		if(whiteMoveRawString.length() != blackMoveRawString.length() &&
				(whiteMoveRawString.length() - 4) != blackMoveRawString.length()) {
			System.out.println("Save file is Malformed");
			return;
		}

		// parses the moves into 4 letter strings
		String[] whiteMoves = parseMoveString(whiteMoveRawString);
		String[] blackMoves = parseMoveString(blackMoveRawString);

		
		this.gameBoard.init();
		for(int cnt = 0; cnt < whiteMoves.length; cnt++) {
			handleMove(whiteMoves[cnt]);
			if(cnt < blackMoves.length) {
				handleMove(blackMoves[cnt]);
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
		Location loc1;
		Location loc2;
		try {
			// gets the mew location from the substring
			loc1 = new Location(move.substring(0, 2));
			loc2 = new Location(move.substring(2, 4));
		} catch (InvalidLocationException e) {
			System.out.println(e.getMessage());
			return;
		}

		// gets the piece in the board
		Piece p = gameBoard.board[loc1.getRow()][loc1.getCol()];

		// if no piece is in this position
		if(p.isEmpty()) {
			// print the appropriate message to the user
			System.out.println("No piece exists at this point");
			return;
		}

		// if the piece isn't the user's
		if(p.color != colorMoves) {
			// print the appropriate message 
			System.out.println("That Piece isn't yours");
			return;
		}

		try {
			// moves to the location
			p.moveTo(loc2);
		} catch (InvalidMoveException e) {
			// prints the correct message
			System.out.println(e.getMessage());
			return;
		}

		// if the move is made by the white player
		if(colorMoves == Color.WHITE) {
			// append it to the moves made by the white player
			whiteMoves.append(move);
		} else {
			// else append it to the moves made by the white player
			blackMoves.append(move);
		}

		// if the move is completed then the next color gets to go
		colorMoves = colorMoves.nextColor();
	}
	
	private void saveGame() {
		System.out.print("Input File to save to: ");
		// gets the user's input
		String filename = scan.nextLine();
		BufferedWriter bw;
		try {
			// creates a new BufferedWriter from the filename
			bw = new BufferedWriter(new FileWriter(filename));
		} catch(Exception e) {
			// if an exception occured then update the user
			System.out.println("Unable to create file, try a different filename");
			return;
		}

		try {
			// adds the moves to the file
			// first the white players moved
			bw.append(whiteMoves.toString()); bw.append("\n");
			// then the black player's move separated by a newline
			bw.append(blackMoves.toString()); bw.append("\n");
		} catch (IOException e) {
			// if an error occured inform the user
			System.out.println("Error occured when writing to file");
		}

		try {
			// tries to close the BufferedWriter
			bw.close();
		} catch (IOException e) {
			// if an err occured informs the user
			System.out.println("Error occured when closing the BufferedWriter");
		}
	}

	protected void play() {
		this.gameBoard.init();

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
				boolean hasEnteredSwitch = false;
				// switch that acts according to what the user wants
				switch(in.charAt(1)) {
				case 'h':
					printHelp();
					break;
				case 's':
					hasEnteredSwitch = true;
					saveGame();
					break;
				case 'o':
					hasEnteredSwitch = true;
					openFile();
				}

				if(!hasEnteredSwitch) {
					if (in.charAt(1) == 'x') {
						System.out.println("Goodbye");
						break;
					}
					System.out.println("Invalid operation given");
				}
				continue;
			} 

			// if the length of the command is correct
			if(in.length() == 4) {
				// perform the command
				handleMove(in);
			} else {
				// else it's not a valid move
				System.out.println("Invalid move length");
			}
		}
		// closes the System.in scanner
		scan.close();
	}
	
	// parses a move string sequence to a move array
	// used for loading files
	private String[] parseMoveString(String moves) {
		// the array that will hold the moves
		String[] resultingArray = new String[moves.length()/4];
		// for each of the moves 
		for(int cnt = 0; cnt <= moves.length()-4; cnt+=4) {
			// put it into the resulting array
			resultingArray[cnt/4] = moves.substring(cnt, cnt+4);
		}
		// return the move array
		return resultingArray;
	}
}
