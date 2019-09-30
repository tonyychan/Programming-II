package control;
import gui.*;
import logic.*;


import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controller class for the text based version of the game
 */
public class GamePlay {

	private char direction;
	private Display screen = new Display();


	/**
	 * Runs through a loop to set up ships systematically from largest to smallest
	 * @return human - the user's player object
	 */
	public Human setPlayer() {

		Human human = new Human();
		int maxship = human.getBoard().getShipList().size();
		int boardTotal = 0;

		for (int i = 0; i < maxship; i++) {
			System.out.println("This is your " + (i + 1) + " boat out of " + maxship + ".");
			Ship boat = human.getBoard().getShipList().get(i);
			int shipCode = boat.getShipCode();
			int shipSize = boat.getShipSize();

			boardTotal = boardTotal + (shipCode * shipSize);
			human.setShot(getInputForShot());
			human.setDirection(getInputForDirection());
			setPlayerShips(human, boat, boardTotal);
		}
		return human;
	}

	/**
	 * Responsible for placing the ships on the player's board
	 * Checks that no ships overlap
	 * Checks that ships are placed within the board
	 * @param human the user's player object
	 * @param boat the ship currently being placed
	 * @param boardTotal the expected current sum of the board if all of the ships are placed properly. boardTotal+=shipCode*shipSize
	 */

	public void setPlayerShips(Human human, Ship boat, int boardTotal) {

		//Checks that the ship is placed within the board
		if (human.getBoard().checkDirection(boat.getShipCode(), human.getDirection(),human.getShot())) {
			human.getBoard().setBoard(boat, human.getShot());
			human.getBoard().checkBoard(boardTotal);

			//Checks that ships are not placed on top of each other
			if (!human.getBoard().getBoardOK()) {
				human.getBoard().copyBoard(human.getBoard().getOldBoard());
				System.out.println("Please select a valid position on the board. Note that you cannot place a ship ontop of another.");
				System.out.println();
				human.setShot(getInputForShot());
				human.setDirection(getInputForDirection());
				setPlayerShips(human, boat, boardTotal);
			}
			else {
				//resets board to before the current ship was placed
				human.getBoard().setOldBoard(human.getBoard().getBoard());
			}
		}

		else {
			System.out.println("Direction is out of bounds.");
			System.out.println("Please select again.");
			human.getBoard().copyBoard(human.getBoard().getOldBoard());
			human.setShot(getInputForShot());
			human.setDirection(getInputForDirection());
			setPlayerShips(human, boat, boardTotal);
		}
		screen.showPlayerBoard(human.getBoard().getBoard());
	}

	/**
	 * Creates Computer object, sets the computer's ship and sets the AI difficulty level.
	 * @return computer the computer's object
	 */
	public Computer setComputer(){
		Computer computer = new Computer();
		computer.getBoard().placeComputerShip();
		computer.setAI(getInputForAI());
		return computer;
	}


	/**
	 * Used to get input from the user for ship placement and shot firing
	 * Recursive if shot is not within the range of the board or the input type is incorrect
	 * @return shot - two element array with {row,column} of the coordinates
	 */
	public int[] getInputForShot(){
		int[] shot = new int[2];
		System.out.println("Enter your coordinates...");
		Scanner keyboard = new Scanner(System.in);
		System.out.println(" Horizontal (A-J):");
		shot[1] = Char2Int(keyboard.next().charAt(0));

		//Handles if the horizontal value is not between A-J
		if(shot[1]==-1){
			System.out.println("Please select a letter from A to J");
			getInputForShot();
		}

		//Exception for if Vertical is not a number,
		//or if Direction is not a letter.
		try {
			System.out.println(" Vertical (1-10):");
			shot[0] = keyboard.nextInt() - 1;

			//Handles if shot value is not within the array
			if(shot[0]<0 ||shot[0]>9){
				System.out.println("Please select a value from 1-10");
				getInputForShot();
			}

		} catch (InputMismatchException e) {
			System.out.println("Invalid input - try again.");
			shot = getInputForShot();
		}
		return shot;
	}


	/**
	 * Gets input from the user for selecting the difficulty of their computer opponnent
	 * @return AI an integer representing either Easy or Harder
	 */
	public int getInputForAI(){
		System.out.println("Select AI Difficulty:");
		System.out.println("1. Easy");
		System.out.println("2. Harder");
		Scanner kb = new Scanner(System.in);
		int AI = kb.nextInt();
		if (AI == 1 || AI == 2 )
		{
			return AI;
		}
		else
		{
			System.out.println("Please only enter 1 or 2.");
			getInputForAI();
		}
		return AI;
	}

	/**
	 * Converts the letters for row of ship placement into int.
	 * @param row the character the user entered for the row
	 * @return num an integer representing the row of the coordinate in the array
	 */
	public int Char2Int(char row){
		int num = -1;

		if (row == 'A' || row == 'a') {
			num = 0;
		} else if (row == 'B' || row == 'b') {
			num = 1;
		} else if (row == 'C' || row == 'c') {
			num = 2;
		} else if (row == 'D' || row == 'd') {
			num = 3;
		} else if (row == 'E' || row == 'e') {
			num = 4;
		} else if (row == 'F' || row == 'f') {
			num = 5;
		} else if (row == 'G' || row == 'g') {
			num = 6;
		} else if (row == 'H' || row == 'h') {
			num = 7;
		} else if (row == 'I' || row == 'i') {
			num = 8;
		} else if (row == 'J' || row == 'j') {
			num = 9;
		}
		return num;
	}

	/**
	 * Gets input from the user for direction of their ship placement
	 * @return direction - char to represent either north, south, east or west
	 */
	public char getInputForDirection(){ ;

		try {
			Scanner keyboard = new Scanner(System.in);
			System.out.println(" Direction of ship (N,S,E,W):");
			direction= (keyboard.next().charAt(0));
		}
		//calls method again to try again if input is wrong.
		catch (InputMismatchException e) {
			System.out.println("Invalid input - try again.");
			getInputForDirection();
		}
		return direction;
	}


	/**
	 * Main method, runs the text based version of the Battleship game
	 * @param args
	 */
	public static void main(String args[]) {

		GamePlay game = new GamePlay();
		Display screen = new Display();

		//sets up Human's board
		Human human = game.setPlayer();
		Board playerBoard = human.getBoard();
		screen.showPlayerBoard(playerBoard.getBoard());

		//sets up Computer's board
		Computer computer = game.setComputer();
		Board computerBoard = computer.getBoard();
		screen.showComputerBoard(computerBoard.getBoard());
		boolean compHit = false;

		while (!human.getWin() && !computer.getWin())
		{
			//gets player's move
			human.setWin(computer.lossCheck());
			human.setShot(game.getInputForShot());

			//Checks that the shot location  has not already been used
			if(computer.isShotOK(human.getShot(),computer)) {

				//checks if hit or miss, updates computer board and human.win
				boolean playerHit = computer.HitOrMiss(human.getShot(), computer);
				if (playerHit) {
					System.out.println("You hit!");
				} else {
					System.out.println("You missed!");
				}

				//updates the computer's board based on the user's input.
				computer.getBoard().setBoard(playerHit, human.getBoard(), human.getShot());
				computer.setWin(human.lossCheck());

				//gets computer's move
				//Harder AI
				if(computer.getAI()==2) {
					computer.setShot(compHit);
				}
				//Easy AI
				else{
					computer.randomShot();
				}

				//checks if hit or miss, updates human board and computer.win
				compHit = human.HitOrMiss(computer.getShot(), human);
				if (compHit) {
					System.out.println("Computer hit!");
				} else {
					System.out.println("Computer missed!");
				}

				//Displays the game progress
				human.getBoard().setBoard(compHit, computer.getBoard(), computer.getShot());
				System.out.println("P1: ");
				screen.showPlayerBoard(human.getBoard().getBoard());
				System.out.println("C1: ");
				screen.showComputerBoard(computer.getBoard().getBoard());

				//Reviews if either player has met the winning conditions
				human.setWin(computer.lossCheck());
				computer.setWin(human.lossCheck());
			}

			else{
				System.out.println("You have already fired there!");
			}
		}

		//displays game result messages
		if (human.getWin() && !computer.getWin()) {
			System.out.println("You win!");
		}
		else if (!human.getWin() && !computer.getWin()) {
			System.out.println("Computer wins! You lose!");
		}
		else if (human.getWin() && computer.getWin()) {
			System.out.println("It's a tie!");
		}

		System.out.println("GAME OVER");
	}
}
