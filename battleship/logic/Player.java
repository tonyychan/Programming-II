package logic;

/**
 * Player class is the abstract parent of Computer & Human
 * Manages the repeated information between the two classes
 */
public abstract class Player {
	private boolean win = false;
	private Board board;
	private int[] shot = new int[2];

	public static final int MAXROW = 10;
	public static final int MAXCOL = 10;

	private char direction;
	private String message="";


	/**
	 * Default Player Constructor
	 * Creates a new board object
	 */
	public Player() {
		board = new Board();
	}


	/**
	 * Copy Player Constructor
	 * @param toCopy the player object that is to be copied
	 * saves board and shot from the player to copy
	 */
	public Player(Player toCopy){
		board = new Board(toCopy.getBoard());
		setShot(toCopy.getShot());
	}


	/**
	 * Accessor for the shot variable
	 * @return shotCopy the shot currently saved in the Player Class
	 */
	public int[] getShot() {
		int[] shotCopy = new int[2];
		shotCopy[0] = shot[0];
		shotCopy[1] = shot[1];
		return shotCopy;
	}


	/**
	 * Mutator for the Player's shot variable
	 * @param shotCoordinate copies the coordinates to the Player's shot variable
	 */
	public void setShot(int[] shotCoordinate) {
		shot[0] = shotCoordinate[0];
		shot[1] = shotCoordinate[1];
	}


	/**
	 * Checks current state of current player board at the coordinate of the opponent's shot
	 * Returns true if the player board at opponent's shot coordinate is equal to a ship value
	 * sets the message according to the result
	 * @param oppShot the shot that the opponent has selected
	 * @param curPlayer the player whose board is being fired at
	 * @return oppHit whether or not the opponent hit the Player's ship
	 */
	public boolean HitOrMiss(int[] oppShot, Player curPlayer){
		boolean oppHit = false;
		if (curPlayer.getBoard().getBoard()[oppShot[0]][oppShot[1]] == 5||curPlayer.getBoard().getBoard()[oppShot[0]][oppShot[1]] == 6||
		curPlayer.getBoard().getBoard()[oppShot[0]][oppShot[1]] == 7||curPlayer.getBoard().getBoard()[oppShot[0]][oppShot[1]] == 8||
		curPlayer.getBoard().getBoard()[oppShot[0]][oppShot[1]] == 9) {

			oppHit = true;
			setMessage("Hit!");

		} else {

			oppHit = false;
			setMessage("Miss!");

		}
		return oppHit;
	}


	/**
	 * checks the values inside of the player's board for remaining unhit ship values
	 * returns false (opponent has not won) if there are still unhit ships within the player's board
	 * @return oppwin  whether or not the opponent has won the game
	 */
	public boolean lossCheck() {
		boolean oppwin = true;
		int[][] gameBoard = board.getBoard();

		for (int j = 0; j < MAXCOL; j++) {
			for (int i = 0; i < MAXROW; i++)
			{
				if (gameBoard[j][i] == 5 || gameBoard[j][i] == 6 || gameBoard[j][i] == 7 ||
				gameBoard[j][i] == 8 || gameBoard[j][i] == 9) {
					oppwin = false;
				}
			}
		}
		return oppwin;
	}


	/**
	 * Checks current state of current player board at the coordinate of the opponent's shot
	 * Returns false if the player board at opponent's shot coordinate is equal to a hit or miss value
	 * @param oppShot the coordinate that the opponent has selected
	 * @param curPlayer the Player whose board is being fired at
	 * @return shotOK whether or not the shot has been used already
	 */
	public boolean isShotOK(int[] oppShot, Player curPlayer){
		boolean shotOK=true;
		if (curPlayer.getBoard().getBoard()[oppShot[0]][oppShot[1]] == 2||curPlayer.getBoard().getBoard()[oppShot[0]][oppShot[1]] == 3){
			shotOK=false;
		}
		return shotOK;
	}


	/**
	 * returns whether or not player has won
	 * @return win boolean that the Player has won
	 */
	public boolean getWin() {
		return win;
	}


	/**
	 * sets the win boolean in player
	 * @param result boolean whether or not the player has won
	 */
	public void setWin(boolean result) {
		win = result;
	}


	/**
	 * returns player's board object
	 * @return board object
	 */
	public Board getBoard() {
		return board;
	}


	/**
	 * sets the direction in player for placing ships
	 * @param aDirection to copy and set the direction variable
	 */
	public void setDirection(char aDirection){
		direction = aDirection;
	}


	/**
	 * returns the direction set in player for placing ships
	 * @return direction
	 */
	public char getDirection(){
		return direction;
	}

	/**
	 * sets the message in Player for during game play
	 * @param aMessage a String to copy for the message
	 */
	public void setMessage(String aMessage){
		message = aMessage;
	}


	/**
	 * returns the message saved in player for display
	 * @return message the player's current message variable
	 */
	public String getMessage(){
		return message;
	}

}
