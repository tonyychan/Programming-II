package control;


import gui.*;

import logic.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;
import java.util.Collections;


/**
 * battleship.Controller class implements the model view controller design
 * Connected with both the game battleship.logic and the GUI's to run Battleship.
 */
public class Controller implements ActionListener {

	private JPanel container = new JPanel();
	private CardLayout cardLayout = new CardLayout();

	private Human human = new Human();
	private Computer computer = new Computer();

	private BoardSetUpGUI start = new BoardSetUpGUI(human, this); //ship placement view
	private BattleFrameGUI gui; //gameplay view

	public static int MAXROW = 10;
	public static int MAXCOL = 10;

	private int shipCode = 5; //for ship placement
	private boolean shipsAreSetUp = false;
	private boolean nextShipFlag = true; //Allows player to see ship set in multiple directions or places before finalizing placement

	private int boardTotal = 25; //Ensures that all of the ships are on the board & not placed on top of each other
	private int numberOfGuesses;
	private boolean gameEndFlag = false;

	//Variables for creating and maintaining the high scores sheet
	private String filename = "BattleshipHighScores.txt";
	private String userName = "";
	private ArrayList<Score> sortedScores = new ArrayList<Score>();

	/**
	 * Constructor for the class, initializes the JFrame and starts the game
	 */
	public Controller() {

		JFrame frame = new JFrame("Battleship");
		computer.getBoard().placeComputerShip();
		frame.setLayout(new BorderLayout());

		container.setLayout(cardLayout);
		container.add(start, "START");
		cardLayout.show(container, "START");

		frame.add(container, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		readHighScore();
	}

	/**
	 * Main method, initializes the game
	 * Sets the UI Manager for consistency between Operating Systems within the GUI
	 * @param args
	 */
	public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		new Controller();
	}

	/**
	 * Handles all of the user interaction throughout the game
	 * @param actionEvent the interaction command from the GUI
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		String buttonPressed = actionEvent.getActionCommand(); //initialized variable to increase readability
		//System.out.println(buttonPressed); //for debugging

		//actions for during gamePlay
		if (buttonPressed.contains("Computer") && !gameEndFlag) {
			gamePlayActions(buttonPressed);

			//actions for during game set up
		} else if (!gameEndFlag) {
			startGUIActions(buttonPressed);

			//game end actions
		} else {
			gui.getInfoPanel().setPlayerMessage("Game Over!");
			gui.getInfoPanel().setComputerMessage("");
		}

	}


	/**
	 * Manages the playing of the game based upon the user's interaction with the GUI
	 * The computer only gets to have a move if the player's move is valid
	 * @param buttonPressed the interaction command from the GUI
	 */
	public void gamePlayActions(String buttonPressed) {

		int[] coord = convertButtons(buttonPressed);
		human.setShot(coord);

		//Checks that the button has not been pressed already
		if(computer.isShotOK(human.getShot(),computer)) {

			//Checks if player's move hits a ship
			boolean shipHit = computer.HitOrMiss(human.getShot(), computer);

			//Updates the computer's board and displays the result of the user's move
			computer.getBoard().setBoard(shipHit, human.getBoard(), human.getShot());
			gui.getComputerGrid().colorButtons(computer);

			//Updates number of guesses the user has made for the High Score sheet & GUI
			numberOfGuesses++;
			gui.getInfoPanel().setNumberOfGuesses(numberOfGuesses);
			gui.getInfoPanel().setGuesses("Guesses: " + numberOfGuesses);

			gui.getInfoPanel().setPlayerMessage("Human: " + computer.getMessage());

			//Checks for game end conditions where the user has won
			if (computer.lossCheck()) {
				updateHighScore();
				gameEndFlag = true;
				gui.getInfoPanel().setPlayerMessage("Human Wins!");
				gui.getInfoPanel().setComputerMessage("");
			}

			//Gets the computer's move
			//Easy mode
			if(computer.getAI()==1){
				computer.randomShot();
			}
			//Harder mode
			else{
				computer.setShot(computer.isFeedbackHit());
				shipHit = human.HitOrMiss(computer.getShot(), human);
				computer.setFeedback(shipHit);
			}


			//Updates the human's board and displays the result of the computer's move
			human.getBoard().setBoard(shipHit, computer.getBoard(), computer.getShot());
			gui.getInfoPanel().setComputerMessage("Computer: " + human.getMessage());
			gui.getPlayerGrid().colorButtons(human);

			//Checks for the game end conditions where the computer has won
			if (human.lossCheck()) {
				gameEndFlag = true;
				gui.getInfoPanel().setPlayerMessage("Computer Wins!");
				gui.getInfoPanel().setComputerMessage("");
			}
		}
		//Displays an error message in the GUI if the user picks an invalid shot
		else{
			gui.getInfoPanel().setPlayerMessage("Please pick a shot where you have not fired already");
			gui.getInfoPanel().setComputerMessage("");
		}
	}


	/**
	 * Manages the interaction with the user for when they are placing their ships at the start of the game
	 * @param buttonPressed the interaction command from the GUI
	 */

	public void startGUIActions(String buttonPressed) {

		//Manages the actions if the user presses the done, reset or set my ships for me buttons
		if (buttonPressed.equals("RESET") || buttonPressed.equals("DONE") || buttonPressed.equals("AUTO_SET_SHIPS")) {
			useDoneResetSetShips(buttonPressed);

			//Allows the user to select direction of their ship placement
		} else if (buttonPressed.equals("NORTH") || buttonPressed.equals("WEST") || buttonPressed.equals("EAST") || buttonPressed.equals("SOUTH")) {
			setDirection(buttonPressed);
			start.getPlayerGrid().colorButtons(human);

			//When the user selects a button on the screen to place their ships.
		} else if (buttonPressed.contains("Human") && !shipsAreSetUp) {
			int[] coord = convertButtons(buttonPressed);
			human.setShot(coord);
			if (shipCode < 10) {
				start.getPlayerGrid().colorSingleButton(human, coord);
			}

			//Allows the user to try multiple ship placements before they finalize their choice
		} else if (buttonPressed.equals("NEXT_SHIP")) {
			if (shipCode < 10) {
				human.getBoard().checkBoard(boardTotal);
				if (human.getBoard().getBoardOK()) {
					nextShipFlag = true;
					shipCode++;
					boardTotal = boardTotal + (shipCode * human.getBoard().getShip(shipCode).getShipSize());
					if (shipCode >= 10) {
						String message = "You have placed all of your ships!";
						start.updateDirectionMsg(message);
					}
				} else {
					String message = "Place your ship first.";
					start.updateDirectionMsg(message);
				}
			}

			//Interaction from the difficulty toggle buttons in the GUI
		} else if (buttonPressed.equals("EASY") || buttonPressed.equals("HARDER")) {
			setDifficulty(buttonPressed);
		}

	}

	/**
	 * converts the button the user pressed to coordinates on the board
	 * @param buttonPressed the interaction command from the GUI
	 * @return coord a two element array that represents the coordinate on the board {row, column}
	 */
	public int[] convertButtons(String buttonPressed) {

		String sentenceArray[] = buttonPressed.split(" ");
		int row = Integer.parseInt(sentenceArray[1]);
		int column = Integer.parseInt(sentenceArray[2]);

		int[] coord = new int[2];
		coord[0] = row;
		coord[1] = column;
		return coord;
	}


	/**
	 * Functionality for the Done & Reset buttons in the GUI, toggles movement from Game set up to Game Play view
	 * Ship placement functionality to reset board or place ships for the user
	 * @param buttonPressed the interaction command from the GUI
	 */
	public void useDoneResetSetShips(String buttonPressed) {

		//Clears the player's board and removes all ship placements
		if (buttonPressed.equals("RESET")) {
			human.getBoard().clearBoard();
			shipCode = 5;
			start.getPlayerGrid().colorButtons(human);
		}

		//Places the user's ships for them
		if (buttonPressed.equals("AUTO_SET_SHIPS")) {
			human.getBoard().clearBoard();
			human.getBoard().placeComputerShip();
			start.getPlayerGrid().colorButtons(human);
			shipCode = 10;

			//Allows user to finalize all of their ship placement and start the game
		} else if (buttonPressed.equals("DONE")) {

			//Checks the sum of the elements in the board is correct for all the ships to be placed
			int sumBoard = 0;
			for (int i = 0; i < MAXROW; i++) {
				for (int j = 0; j < MAXCOL; j++) {
					sumBoard += human.getBoard().getBoard()[i][j];
				}
			}

			if (sumBoard == 112) {

				//Initializes the game play view with the user & computer's data
				gui = new BattleFrameGUI(human, computer, this);
				shipsAreSetUp = true;

				//Gets a name from the user for the High Score sheet
				//If empty, assigns a random name to the user
				userName = start.getUserName();
				if (userName.isEmpty() || userName == null) {
					userName = getRandomName();
				}

				//Changes the view for the user to start the game play
				container.setPreferredSize(new Dimension(1000, 600));
				container.add(gui, "PLAY");
				cardLayout.show(container, "PLAY");

			} else {
				//Provides error message if the user has not set up their ships yet
				start.updateDirectionMsg("Please set up your ships first.");
			}
		}

	}


	/**
	 * Converts the button the user presses into a direction for ship placement
	 * @param buttonPressed the interaction command from the GUI
	 */
	public void setDirection(String buttonPressed) {
		char direction;

		//Checks that there are still ships to be placed
		if (shipCode < 10) {
			if (buttonPressed.equals("NORTH")) {
				direction = 'N';
				human.setDirection(direction);
			} else if (buttonPressed.equals("SOUTH")) {
				direction = 'S';
				human.setDirection(direction);
			}

			//The GUI displays backwards for East & West
			else if (buttonPressed.equals("WEST")) {
				direction = 'E';
				human.setDirection(direction);
			} else if (buttonPressed.equals("EAST")) {
				direction = 'W';
				human.setDirection(direction);
			}
			placePlayerShips();
		}
	}


	/**
	 * Sets the difficulty for the computer from the user's selection
	 * @param buttonPressed the interaction command from the GUI
	 */
	public void setDifficulty(String buttonPressed) {
		if (buttonPressed.equals("EASY")) {
			computer.setAI(1);
		} else if (buttonPressed.equals("HARDER")) {
			computer.setAI(2);
		}
	}

	/**
	 * Interacts with the human class to display the current board in the GUI and update the human's board object
	 */
	public void placePlayerShips() {

		//Checks that there are still ships to be placed
		if (shipCode <= 9) {

			//nextShipFlag allows the user to try multiple positions of their ships then finalize that position
			//Resets the flag to false and copies the board from the last time the ship that was finalized
			if (nextShipFlag) {
				nextShipFlag = false;
				human.getBoard().setOldBoard(human.getBoard().getBoard());
				human.getBoard().placeShips(boardTotal, shipCode, human.getShot(), human.getDirection());

				//Places ships in positions
			} else if (!nextShipFlag) {
				human.getBoard().copyBoard(human.getBoard().getOldBoard());
				human.getBoard().placeShips(boardTotal, shipCode, human.getShot(), human.getDirection());
			}

			//Checks that no ships are placed on top of each other
			human.getBoard().checkBoard(boardTotal);
			if (!human.getBoard().getBoardOK()) {
				human.getBoard().copyBoard(human.getBoard().getOldBoard());
			}

			//Gets error messages from the Board Class to display in the GUI
			String message = human.getBoard().getMessage();
			start.updateDirectionMsg(message);
			start.getPlayerGrid().colorButtons(human);

			//Message to user to display if their ships have already been placed.
		} else {
			start.updateDirectionMsg("You have placed all of your ships.");
		}
	}


	/**
	 * Sorts the previous high scores that have been saved from lowest (least number of guesses until win) to highest)
	 * Overwrites the High Score file with the latest results
	 * IOException results in an error message displayed to the GUI
	 */
	public void updateHighScore() {

		//Adding the current score then sorting the list
		sortedScores.add(new Score(numberOfGuesses,userName));
		Collections.sort(sortedScores,new ScoreComparator());

		//Writing the High Scores to a text file
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			writer.println(" HIGH SCORES ");
			writer.println("-----------");

			//Writes each score
			for(Score score: sortedScores){
				writer.println("*" + score.getName() + "*");
				writer.println("Guesses: " + score.getGuesses());
				writer.println("-----------");
			}
			writer.flush();
			writer.close();

			//Exceptions cause an error message to display to the GUI
		} catch (IOException e) {
			gui.getInfoPanel().setPlayerMessage("There has been an error & the High Scores will not be updated");
			gui.getInfoPanel().setComputerMessage("");
		}
	}


	/**
	 * Reads the high scores from the current file and adds them to the sortedScores list
	 * IOExceptions display an error message for the user to the GUI
	 */
	public void readHighScore() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();

			while (line != null) {
				if (line.charAt(0) == '*' && line != null) {
					//Getting the previous user's names
					String name = line.substring(1);
					name = name.substring(0, name.length() - 1);
					//System.out.println("HUM " + name); //for debugging

					//Getting the previous user's scores
					String guesses = reader.readLine();
					guesses = guesses.substring(9);
					int score = Integer.parseInt(guesses);
					//System.out.println(score); //for debugging

					//Creating a score object and adding each score to the sortedScores array List
					Score highScore = new Score(score,name);
					sortedScores.add(highScore);
				}

				line = reader.readLine();
			}

			//Displays an error message to the user
		} catch (IOException e) {
			start.updateDirectionMsg("There has been an error & the High Score sheet can not be opened.");
		}
	}


	/**
	 * Creates a random five character lowercase string in the event the user does not enter a name
	 * @return name a string to represent the user's name
	 */
	public String getRandomName() {
		String name = "";
		for (int i = 0; i <= 5; i++) {
			Random random = new Random();
			char letter = (char) (random.nextInt(26) + 'a');
			name = name + letter;
		}
		return name;
	}


	/**
	 * score class represents score objects with the user's name and number of guesses
	 * Used in creating and sorting the High Score sheet
	 */
	class Score {
		private int guesses;
		private String name;

		/**
		 * Constructor for the Score object
		 * @param guesses the number of guesses that were made
		 * @param name the name of the user
		 */
		public Score(int guesses, String name) {
			this.guesses = guesses;
			this.name = name;
		}

		/**
		 *Accessor for the name variable
		 * @return name string to represent name of the user
		 */
		public String getName() {
			return name;
		}


		/**
		 * Accessor for the guesses variable
		 * @return guesses an int to represent the user's score
		 */
		public int getGuesses() {
			return guesses;
		}


	}


	/**
	 * compares score objects to sort them by lowest number of guesses
	 */
	class ScoreComparator implements Comparator<Score>{

		/**
		 * comparing score objects based upon the guesses variable
		 * @param firstScore the first score object passed in
		 * @param secondScore the second score object passed in
		 * @return 0 if the scores are even, 1 if the first score is greater and -1 if if is lower
		 */
		public int compare(Score firstScore,Score secondScore){
			if(firstScore.guesses==secondScore.guesses)
				return 0;
			else if(firstScore.guesses>secondScore.guesses)
				return 1;
			else
				return -1;
		}
	}

}
