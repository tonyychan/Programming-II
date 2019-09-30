package logic;

import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Computer class represents the user's opponent in the game
 *
 * Contains two difficulty levels:
 *
 * Easy level aiDiff=1, finds a random shot and fires there
 *
 * Harder level aiDiff=2, fires randomly until there is a hit.
 * Then it fires around that point to find the direction that the ship lays
*/

public class Computer extends Player
{
	/*
	* Direction booleans, used as switches to determine the nature and direction of the Computer's shots
	*/
	private boolean lastGood = false;
	private boolean trueN = false;
	private boolean trueS = false;
	private boolean trueE = false;
	private boolean trueW = false;
	private boolean oldMove;

	private boolean feedbackHit = false;

	/*
	 * Instances used within the class
	 * An instance of random, an archaic implementation of weighted random selection, and an array for shot storage
	 */
	private Random rand = new Random();
	private int[] boardChoice = new int[] {0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 8, 9};
	private ArrayList<Point> shotStore = new ArrayList<Point>(); //Storing of shots

	/*
	 * Integers to be used as shot storage and switches
	 */

	private int x = 0;
	private int y = 0;
	private int aiDiff = 1;
	private int shotType = 0;

	public static final int weighted = 0;

	public static final int shotNorth = -2;
	public static final int shotEast = -3;
	public static final int shotWest = -4;
	public static final int shotSouth = -5;

	/**
	 * Default constructor, calls the default constructor in the Player Class
	 */
	public Computer()
	{
		super();
	}


	/**
	 * An overridden setShot of Player
	 * Represents the standard difficulty of the Computer
	 * While-loop implementation
	 * Will adjust x and y based on boolean input
	 * Contains multiple private methods for readability
	 * @param shotFeedback boolean representing whether or not the last shot hit.
	 */
	public void setShot(Boolean shotFeedback) {
			shotChoice(shotFeedback);
			oldMove = false;
			while (shotStore.contains(new Point(x, y))) {
				if (shotType == weighted) {
					x = boardChoice[rand.nextInt(boardChoice.length)]; //X
					y = boardChoice[rand.nextInt(boardChoice.length)]; //Y

				} else if (shotType == shotNorth) {
					checkNorth();
					oldMoveCheck();
					oldMove = true;

				} else if (shotType == shotSouth) {
					checkSouth();
					oldMoveCheck();
					oldMove = true;

				} else if (shotType == shotEast) {
					checkEast();
					oldMoveCheck();
					oldMove = true;

				} else if (shotType == shotWest) {
					checkWest();
					oldMoveCheck();
					oldMove = true;
				}

			}
			outofBounds();
			shotStore.add(new Point(x, y));
			int[] coord = {y,x};
			setShot(coord);
//			shot[1] = x;
//			shot[0] = y;
			oldMove = false;

	}


	/**
	 * randomShot class represents an 'easy' Computer to play against
	 * Coordinates will be purely pseudo-randomly
	 */
	public void randomShot() {
//		while (shotStore.contains(new Point(x,y))) {
//			x = rand.nextInt(10);
//			y = rand.nextInt(10);
//		}
		x= rand.nextInt(10);
		y=rand.nextInt(10);
		int[] coord = {y,x};
		setShot(coord);
//		return coord;
//		shot[1] = x;
//		shot[0] = y;
	}


	/**
	 * Private method to improve readability of setShot method
	 * Receives the input of setShot, will assign a method to obtaining the next coordinates
	 * @param shotFeedback whether or not last shot hit
	 */
	private void shotChoice(Boolean shotFeedback) {
		if (!shotFeedback) {
			lastGood = false;
			trueN = false;
			trueE = false;
			trueW = false;
			trueS = false;
			shotType = weighted;
		}

		else if (shotFeedback && lastGood == false) {
			int tempDir = rand.nextInt(4);
			shotType = -(tempDir + 2);
			if (shotType == shotNorth) {
				trueN = true;
			} else if (shotType == shotEast) {
				trueE = true;
			} else if (shotType == shotWest) {
				trueW = true;
			} else if (shotType == shotSouth) {
				trueS = true;
			}
			lastGood = true;
		}

		else if (shotFeedback && lastGood == true) {
			//Shot-type should not change
			if (trueN) {
				shotType = shotNorth;
			} else if (trueS) {
				shotType = shotSouth;
			} else if (trueW) {
				shotType = shotWest;
			} else if (trueE) {
				shotType = shotEast;
			}
		}
		else {
			lastGood = false;
			trueN = false;
			trueE = false;
			trueW = false;
			trueS = false;
			shotType = weighted;
		}
	}

	/**
	 * Private method that will catch out of bound selections in setShot
	 */
	private void outofBounds() {
		shotStore.add(new Point(x,y));
		if (x > 9 || x < 0) {
			x = 5;
		}
		if (y > 9 || y < 0) {
			y = 5;
		}
		shotType = weighted;
	}


	/**
	 * Private method that will prevent infinite loops in setShot
	 */
	private void oldMoveCheck() {
		if (oldMove) {
			shotType = weighted;
			trueN = false;
			trueS = false;
			trueE = false;
			trueW = false;
		}
	}


	/**
	 * Private method to improve readability of setShot method
	 * Will adjust vertical coordinate to represent an up-direction
	 */
	private void checkNorth() {
		y--;
		if (y<0) {
			y = y + 2;
			trueN = false;
			trueS = true;
			shotType = shotSouth;
		}
	}

	/**
	 * Private method to improve readability of setShot method
	 * Will adjust vertical coordinate to represent a down-direction
	 */
	private void checkSouth() {
		y++;
		if (y>9) {
			y = y - 2;
			trueS = false;
			trueN = true;
			shotType = shotNorth;
		}
	}

	/**
	 * Private method to improve readability of setShot method
	 * Will adjust horizontal coordinate to represent a right-direction
	 */
	private void checkEast() {
		x++;
		if (x>9) {
			x = x - 2;
			trueE = false;
			trueW = true;
			shotType = shotWest;
		}
	}

	/**
	 * Private method to improve readability of setShot method
	 * Will adjust horizontal coordinate to represent a left-direction
	 */

	private void checkWest() {
		x--;
		if (x>0) {
			x = x + 2;
			trueW = false;
			trueE = true;
			shotType = shotEast;
		}
	}


	/**
	 * Mutator for the feedbackHit variable
	 * @param shipHit whether or not the last shot hit a ship
	 */
	public void setFeedback(boolean shipHit) {
		feedbackHit = shipHit;
	}

	/**
	 * Accessor for the feedbackHit variable
	 * @return feedback hit
	 */
	public boolean isFeedbackHit() {
		return feedbackHit;
	}


	/*
	 * Returns the boolean of remembering the previous shot
	 * Used for testing purposes
	 */
	public boolean getGood() {
		return lastGood;
	}


	/**
	 * Will set the difficulty of the computer through an int switch
	 * @param AI the input from the user to adjust the AI
	 */
	public void setAI(int AI)
	{
		aiDiff = AI;
	}


	/**
	 * Accessor for the variable for the difficulty level of the Computer
	 * @return aiDiff
	 */
	public int getAI()
	{
		return aiDiff;
	}


}
