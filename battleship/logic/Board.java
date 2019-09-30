package logic;

import java.util.*;

/**
 * Board object is used to represent each player's gameboard.
 * Each board has five ships
 * Class controls ship placement
 */

public class Board
{
	private final static int MAXROW = 10;
	private final static int MAXCOL = 10;
	private final static int MISS = 2;
	private final static int HIT = 3;
	private final static int EMPTY = 0;

	private int[][] board = new int[MAXCOL][MAXROW];
	private int[][] oldBoard = new int[MAXROW][MAXCOL]; //Retains a copy of the board before making changes

	private int[] coord = new int[2]; //0 = row, 1 = col;
	private char direction;
	private boolean boardOK;

	//Initializes the ship objects for the board
	private Ship CARRIER = new Ship(5);
	private Ship BATTLESHIP = new Ship(6);
	private Ship CRUISER = new Ship(7);
	private Ship SUBMARINE = new Ship(8);
	private Ship DESTROYER = new Ship(9);
	private ArrayList<Ship> shipList = new ArrayList<Ship>();

	//Used to update and pass messages to the GUI
	private  String message=" ";


	/**
	 * Default Constructor
	 * Creates the board array and initializes to empty values
	 */

	public Board()
	{
		for (int row = 0; row < MAXROW; row++)
		{
			for (int column = 0; column < MAXCOL; column++)
			{
				board[row][column] = EMPTY;
			}
		}
		addShipsToList();
	}


	/**
	 * Copy Constructor
	 * Copies the values of the board object array
	 * @param toCopy board object to be copied
	 */
	public Board(Board toCopy)
	{
		int[][] copyBoard = toCopy.getBoard();
		for (int row = 0; row < MAXROW; row++)
			for (int col = 0; col < MAXCOL; col++)
				board[row][col] = copyBoard[row][col];
	}



	/**
	 * Adds the ships to an array list
	 */

	public void addShipsToList()
	{
		shipList.add(CARRIER);
		shipList.add(BATTLESHIP);
		shipList.add(CRUISER);
		shipList.add(SUBMARINE);
		shipList.add(DESTROYER);
	}


	/**
	 * Accessor for the board array
	 * Necessary privacy leak to allow display and mutation of the board during the game
	 * @return board board array values
	 */

	public int[][] getBoard()
	{
		return board;
	}


	/**
	 * Copies another board array and sets the board array to the copy's values
	 * @param copyBoard the board array variable to be copied
	 */

	public void copyBoard(int[][] copyBoard){
		for (int row = 0; row < MAXROW; row++)
		{
			for (int column = 0; column < MAXCOL; column++)
			{
				board[row][column] = copyBoard[row][column];
			}
		}
	}

	/**
	 * Sets the oldBoard array variable to the copy's values
	 * @param copyBoard board array variable to be copied
	 */
	public void setOldBoard(int[][] copyBoard){
		for (int row = 0; row < MAXROW; row++)
		{
			for (int column = 0; column < MAXCOL; column++)
			{
				oldBoard[row][column] = copyBoard[row][column];
			}
		}
	}


	/**
	 * Accessor for the board array variable
	 * @return oldBoard the board array
	 */
	public int[][] getOldBoard(){return oldBoard;}


	/**
	 * Resets the board array variables values to zero
	 * @return board the board array variable
	 */
	public int[][] clearBoard(){
		for (int row = 0; row < MAXROW; row++)
		{
			for (int column = 0; column < MAXCOL; column++)
			{
				board[row][column] = EMPTY;
			}
		}
		return board;
	}


	/**
	 * Places ships systematically from largest to smallest
	 * Also used in the GUI to set ships automatically for the player
	 */
	public void placeComputerShip()
	{
		setComputerShip(CARRIER);
		setComputerShip(CRUISER);
		setComputerShip(BATTLESHIP);
		setComputerShip(SUBMARINE);
		setComputerShip(DESTROYER);
	}


	/**
	 * Placing the ships onto the computer's board.
	 * Also used in the GUI to place ships automatically on the Player's board
	 * Recursive function to ensure ships are not placed on top of each other
	 * @param boat which ship to place
	 */
	public void setComputerShip(Ship boat)
	{
		int shipLength = boat.getShipSize();
		int shipCode = boat.getShipCode();
		//Chooses a random point on the array to start off.
		Random rand = new Random();
		//Limits starting point based on the ship's length.
		int startingRow = rand.nextInt(MAXROW - shipLength) + 1;
		int startingCol = rand.nextInt(MAXCOL - shipLength) + 1;
		//0 = Vertical, 1 = Horizontal
		int vertOrHor = rand.nextInt(2);

		//Checks if the ship will occupy any spaces.
		if (checkComputerSetup(board, startingRow,
				startingCol, shipLength, vertOrHor))
		{
			//Changes row/col of board w/o changing ship's row/col.
			int shipCol = startingCol;
			int shipRow = startingRow;
			for (int sLength = 0; sLength < (shipLength); sLength++)
			{
				//replaces 0 with a number, indicating it is placed.
				if (vertOrHor == 0)
				{
					board[shipCol][startingRow] = shipCode;
					shipCol++;
				}
				else
				{
					board[startingCol][shipRow] = shipCode;
					shipRow++;
				}
			}
		}
		else
			//Finds a new startingRow and Col if the space is occupied.
			//Recursive, so repeats until it finds an empty space.
			setComputerShip(boat);
	}


	/**
	 * Checks if the space is empty for the ship to be placed in
	 * @param boardToCheck board array variable to check values against
	 * @param row the row the ship is to be placed in
	 * @param col the column the ship is to be placed in
	 * @param shipLength the length of the ship being placed
	 * @param vertOrHor whether the ship is being placed vertical or horizontally, 0 = Vertical, 1 = Horizontal
	 * @return emptySpace boolean that returns true if the board value is empty(zero).
	 */
	public boolean checkComputerSetup(int[][] boardToCheck, int row,
	int col, int shipLength, int vertOrHor)
	{
		boolean emptySpace = true;
		//Cycles through the array for the length of shipLength
		for (int sLength = 0; sLength < shipLength; sLength++)
		{
			//0 = space is empty
			if (boardToCheck[col][row] != 0)
			emptySpace = false;
			else
			emptySpace = emptySpace && true;
			//Alters what spaces to check, depending on ship placement.
			if (vertOrHor == 0) //Vertical checking
			col++;
			else //Horizontal checking
			row++;
		}
		return emptySpace;
	}


	/**
	 * Used to check that the ship placement is not on top of another when the user is manually placing ships
	 * @param currentBoardSum the sum based on ships that have been placed. shipcode * shiplength
	 */
	public void checkBoard(int currentBoardSum)
	{
		int sumBoard = 0;
		for (int i = 0; i < board[0].length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{

				sumBoard += board[i][j];
			}
		}
		if (sumBoard == currentBoardSum)
		{
			setBoardOK(true);
		}
		else
		{
			setBoardOK(false);
		}
	}


	/**
	 * Checks that the ship is placed inside the board based on the direction
	 * @param shipSize the size of the ship
	 * @param directionCopy the direction to be copied
	 * @param coordCopy the coordinate to be copied
	 * @return validDirection boolean, returns true if the ship will be placed within the board
	 */
	public boolean checkDirection(int shipSize, char directionCopy, int[] coordCopy)
	{
		setCoord(coordCopy[0],coordCopy[1]);
		direction=directionCopy;
		boolean validDirection = true;
		//Checks if the ship would be placed out of bounds.
		if (direction == 'N' || direction == 'n')
		{
			if ((coord[0] - (shipSize-1)) <0)
				validDirection = false;
		}
		else if (direction == 'S' || direction == 's')
		{
			if ((coord[0] + shipSize)> MAXROW)
				validDirection = false;
		}
		else if (direction == 'E' || direction == 'e')
		{
			if ((coord[1] - (shipSize-1))<0)
				validDirection = false;

		}
		else if (direction == 'W' || direction == 'w')
		{
			if ((coord[1] + shipSize)>MAXCOL)
				validDirection = false;
		}
		else
		{
			//If N, S, E, or W is not entered as a direction.
			System.out.println("Invalid direction selected.");
			System.out.println("Enter N, S, E, or W.");
			validDirection = false;
		}
		return validDirection;
	}


	/**
	 * Mutator for the boardOK instance variable (whether or not the ship placement is correct)
	 * @param boardOKCopy boolean to be copied
	 */
	public void setBoardOK(boolean boardOKCopy){
		boardOK=boardOKCopy;
	}


	/**
	 * Accessor for the boardOK instance variable
	 * @return boardOK boolean, whether or not the ship placement is correct
	 */
	public boolean getBoardOK(){
		return boardOK;
	}


	/**
	 * Method to handle ship placement in the GUI
	 * @param boardTotal the current board array's sum
	 * @param shipCode  the current ship being placed
	 * @param coordCopy the position the user has selected to place the ship
	 * @param directionCopy the direction the user has selected to place the ship
	 */
	public void placeShips(int boardTotal, int shipCode, int[] coordCopy, char directionCopy) {
		Ship boat= getShip(shipCode);
		setMessage("Let's place the ships!");
		if(checkDirection(boat.getShipSize(),directionCopy,coordCopy)){
			setBoard(boat, coordCopy);
			checkBoard(boardTotal);
			if (!getBoardOK()) {
				setMessage("Please select a valid position on the board.");
			}
		}
		else{
			setMessage("Please select a valid position on the board.");
		}


	}


	/**
	 * Updates the board array by placing the ships
	 * direction - which direction the ships are being placed
	 * @param boat which ship is currently being placed
	 * @param coordCopy the position on the board the ship is being placed
	 */
	public void setBoard(Ship boat, int[] coordCopy){
		setCoord(coordCopy[0],coordCopy[1]);
		int shipSize = boat.getShipSize();
		int shipCode = boat.getShipCode();
		for (int i = 0; i < shipSize; i++)
		{
			if (direction == 'N' || direction == 'n') {
				board[coord[0] - i][coord[1]] = shipCode;
			}
			if (direction == 'S' || direction == 's') {
				board[coord[0] + i][coord[1]] = shipCode;
			}
			if (direction == 'E' || direction == 'e') {
				board[coord[0]][coord[1] - i] = shipCode;
			}
			if (direction == 'W' || direction == 'w') {
				board[coord[0]][coord[1] + i] = shipCode;
			}
		}
	}


	/**
	 * Updates the board array to change if ship is hit or miss.
	 * @param shipHit boolean (true means the ship was hit)
	 * @param gameBoard the board array to be updated
	 * @param coordCopy the position on the board array to update
	 */
	public void setBoard(boolean shipHit, Board gameBoard, int[] coordCopy)
	{ setCoord(coordCopy[0],coordCopy[1]);
		if (shipHit == true)
		{
			convertCoordToPosition(HIT);
		}
		else if (shipHit == false)
		{
			convertCoordToPosition(MISS);
		}
	}


	/**
	 * Updates the values of the coordinates in the board array during game play
	 * @param code whether the position value should be HIT, MISS or EMPTY
	 */
	public void convertCoordToPosition(int code)
	{
		int row = coord[0];
		int col = coord[1];
		board[row][col] = code;
	}

	/**
	 * Accessor for the shipList variable
	 * @return shipList
	 */
	public ArrayList<Ship> getShipList()
	{
		ArrayList<Ship> newShipList = new ArrayList<Ship>();
		for (int length = 0; length < shipList.size(); length++)
		{
			Ship copiedShip = new Ship(shipList.get(length));
			newShipList.add(copiedShip);
		}
		return shipList;

	}


	/**
	 * Mutator for the coord variable
	 * @param row the row value
	 * @param col the column value
	 */
	public void setCoord(int row, int col)
	{
		coord[0]=row;
		coord[1]=col;
	}


	/**
	 * Accessor for the ship objects based upon the shipCode
	 * @param shipCode the value of the shipCode related to the ship
	 * @return boat the ship object that was requested
	 */
	public Ship getShip(int shipCode){
		Ship boat= new Ship(5);
		if(shipCode==5){
			boat = CARRIER;
		}
		else if(shipCode==6){
			boat = BATTLESHIP;
		}
		else if(shipCode==7){
			boat = CRUISER;
		}
		else if(shipCode==8){
			boat = SUBMARINE;
		}
		else if(shipCode==9){
			boat = DESTROYER;
		}
		return boat;
	}


	/**
	 * Mutator for the message variable, used to display messages to the user in the GUI
	 * @param messageCopy
	 */
	public void setMessage(String messageCopy){message=messageCopy;}


	/**
	 * Accessor for the message variable to display message to the user in the GUI
	 * @return message the current value of the message variable
	 */
	public String getMessage(){return message;}

}
