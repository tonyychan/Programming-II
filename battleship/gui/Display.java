
package gui;


/**
 * Used to represent the game boards in the text based version of the game
 */
public class Display {

	private final static int MAXROW = 10;
	private final static int MAXCOL = 10;


	/**
	 * used to display the Player's board
	 * shows ship placement, hits and misses
	 * @param board the board array variable
	 */
	public void showPlayerBoard(int[][] board) {
		//shows the tracking view of where the player has tried,
		//symbolizing hits and misses
		System.out.println();
		System.out.println("\tA \tB \tC \tD \tE \tF \tG \tH \tI \tJ");

		for(int row = 0 ; row < MAXROW ; row++ ){
			System.out.print((row+1)+"   ");
			for(int column = 0 ; column < MAXCOL ; column++ ){
				if(board[row][column]== -1){
					//for off the board
					System.out.print("\t"+"0");

					//not hit yet
				}else if(board[row][column] == 0){
					//to account for the extra spacing needed in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"     "+"| ");
					}
					else{
						System.out.print(" "+"|"+"     "+"|");
					}

				}
				else if(board[row][column] == 2){
					//miss

					//to account for the extra spacing needed in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"  0  "+"| ");
					}
					else{
						System.out.print(" "+"|"+"  0  "+"|");
					}

				}
				else if(board[row][column] == 3){
					//for if it hits the ship

					//to account for the extra spacing needed in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"  X  "+"| ");
					}
					else{
						System.out.print(" "+"|"+"  X  "+"|");
					}

				}
				else{
					//to account for the extra spacing needed in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"  S  "+"| ");
					}
					else{
						System.out.print(" "+"|"+"  S  "+"|");
					}
					//to show ship placement
				}
			}
			System.out.println();
		}
	}

	/**
	 * used to display the Computer's board
	 * shows hits and misses
	 * @param board the board array variable
	 */

	public void showComputerBoard(int[][] board) {
		//shows the tracking view of where the computer has tried,
		//symbolizing hits and misses

		System.out.println();
		System.out.println("\tA \tB \tC \tD \tE \tF \tG \tH \tI \tJ");

		for(int row = 0 ; row < MAXROW ; row++ ){
			System.out.print((row+1)+"   ");
			for(int column = 0 ; column < MAXCOL ; column++){
				if(board[row][column] == -1){
					//for off the board?/not hit yet
					System.out.print("\t"+"0");
				}
				else if(board[row][column] == 0){

					//to account for the extra spacing required in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"     "+"| ");
					}
					else{
						System.out.print(" "+"|"+"     "+"|");
					}

				}
				else if(board[row][column] == 2){
					//to account for the extra spacing needed in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"  0  "+"| ");
					}
					else{
						System.out.print(" "+"|"+"  0  "+"|");
					}
					//for miss
				}
				else if(board[row][column] == 3){
					//for if it hits the ship

					//to account for the extra spacing needed in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"  X  "+"| ");
					}
					else{
						System.out.print(" "+"|"+"  X  "+"|");
					}

				}
				else{
					//to account for the extra spacing required in the column displayed as 10
					if(row == 9){
						System.out.print("|"+"     "+"| ");
					//	System.out.print("|"+"  S  "+"| ");

					}
					else{
						System.out.print(" "+"|"+"     "+"|");
					//	System.out.print(" "+"|"+"  S  "+"|");
					}

					//to show ship placement (for debugging)
				}
			}
			System.out.println();
		}
	}
}
