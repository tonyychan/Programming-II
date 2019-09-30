package tests;
import logic.*;

import static org.junit.Assert.*;

import org.junit.Test;


//javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar *.java
//java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore BoardTest
public class BoardTest 
{
	@Test
	public void test_defaultConstructor()
	{
		boolean allEmpty = true;
		Board test = new Board();
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				if (test.getBoard()[col][row] == 0)
				{
					allEmpty = allEmpty && true;
				}
				else
				{
					allEmpty = allEmpty && false;
				}
			}
		}
		assertTrue("Created new Board: should be all empty (0)", allEmpty);
	}
	
	@Test
	public void test_copyConstructor()
	{
		boolean same = true;
		Board test = new Board();
		Board test2 = new Board(test);
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				if (test2.getBoard()[col][row] == test.getBoard()[col][row])
				{
					same = same && true;
				}
				else
				{
					same = same && false;
				}
			}
		}
		assertTrue("Created copy Board: should be identical", same);
	}
	
	@Test
	public void test_convertCoordToPosition()
	{
		Board test = new Board();
		test.setCoord(5, 5);
		test.convertCoordToPosition(1);
		
		int[][] array = test.getBoard();
		boolean same = (array[5][5] == 1);
		
		assertTrue("Tried converting coordinates (5, 5) onto the board: value at (5,5) should be 1", same);
	}

	@Test
	public void test_checkComputerSetup_emptySpot()
	{
		Board test = new Board();
		test.setCoord(5, 5);
		test.convertCoordToPosition(3);

		boolean isSpaceEmpty = test.checkComputerSetup(test.getBoard(), 0, 0, 2, 0);
		
		assertTrue("Created ship at 5, 5: space at 0,0 should be considered empty", isSpaceEmpty);
	}
	
	@Test
	public void test_checkComputerSetup_sameStartingPoint()
	{
		Board test = new Board();
		test.setCoord(5, 5);
		test.convertCoordToPosition(3);

		boolean isSpaceEmpty = test.checkComputerSetup(test.getBoard(), 5, 5, 2, 0);
		
		assertFalse("Created ship at 5, 5: space should not be considered empty", isSpaceEmpty);
	}
	
	@Test
	public void test_checkComputerSetup_checkingCol()
	{
		Board test = new Board();
		test.setCoord(5, 1);
		test.convertCoordToPosition(3);
		test.setCoord(5, 2);
		test.convertCoordToPosition(3);
		test.setCoord(5, 3);
		test.convertCoordToPosition(3);
		
		boolean isSpaceEmpty = test.checkComputerSetup(test.getBoard(), 0, 5, 3, 1);
		
		assertFalse("Created ship at 5, 1 to 5, 3: starting at 5, 0, should not be considered empty.", isSpaceEmpty);
	}
	
	@Test
	public void test_checkComputerSetup_checkingRow()
	{
		Board test = new Board();
		test.setCoord(1, 5);
		test.convertCoordToPosition(3);
		test.setCoord(2, 5);
		test.convertCoordToPosition(3);
		test.setCoord(3, 5);
		test.convertCoordToPosition(3);
		
		boolean isSpaceEmpty = test.checkComputerSetup(test.getBoard(), 5, 0, 3, 0);
		
		assertFalse("Created ship at 1, 5 to 3, 5: starting at 0, 5, should not be considered empty.", isSpaceEmpty);
	}
	
	@Test
	public void test_checkDirection_invalid()
	{
		Board test = new Board();
		int[] coordinate1 =  new int[2];
		coordinate1[0] = 0;
		coordinate1[1] = 0;
		boolean northValid = test.checkDirection(3, 'N', coordinate1);
		
		coordinate1[0] = 10;
		coordinate1[1] = 10;
		boolean southValid = test.checkDirection(3, 'S', coordinate1);
		
		coordinate1[0] = 0;
		coordinate1[1] = 0;
		boolean eastValid = test.checkDirection(3, 'E', coordinate1);
		
		coordinate1[0] = 10;
		coordinate1[1] = 10;
		boolean westValid = test.checkDirection(3, 'W', coordinate1);
		
				
		assertFalse("At 0,0, placing a ship North should be invald", northValid);
		assertFalse("At 10,10, placing a ship South should be invald", southValid);
		assertFalse("At 0,0, placing a ship East should be invald", eastValid);
		assertFalse("At 10,10, placing a ship West should be invald", westValid);
	}
	
	@Test
	public void test_checkDirection_valid()
	{
		Board test = new Board();
		int[] coordinate1 =  new int[2];
		coordinate1[0] = 5;
		coordinate1[1] = 5;
		boolean northValid = test.checkDirection(3, 'N', coordinate1);
		
		coordinate1[0] = 5;
		coordinate1[1] = 5;
		boolean southValid = test.checkDirection(3, 'S', coordinate1);
		
		coordinate1[0] = 5;
		coordinate1[1] = 5;
		boolean eastValid = test.checkDirection(3, 'E', coordinate1);
		
		coordinate1[0] = 5;
		coordinate1[1] = 5;
		boolean westValid = test.checkDirection(3, 'W', coordinate1);
				
		assertTrue("At 5,5, placing a ship North should be vald", northValid);
		assertTrue("At 5,5, placing a ship South should be vald", southValid);
		assertTrue("At 5,5, placing a ship East should be vald", eastValid);
		assertTrue("At 5,5, placing a ship West should be vald", westValid);
	}
	
	
	/*
	@Test
	public void test_Char2Int_lowercase()
	{
		Board test = new Board();
		char letterA = 'a';
		int resultA = test.Char2Int(letterA);
		char letterB = 'b';
		int resultB = test.Char2Int(letterB);
		char letterC = 'c';
		int resultC = test.Char2Int(letterC);
		char letterD = 'd';
		int resultD = test.Char2Int(letterD);
		char letterE = 'e';
		int resultE = test.Char2Int(letterE);
		char letterF = 'f';
		int resultF = test.Char2Int(letterF);
		char letterG = 'g';
		int resultG = test.Char2Int(letterG);
		char letterH = 'h';
		int resultH = test.Char2Int(letterH);
		char letterI = 'i';
		int resultI = test.Char2Int(letterI);
		char letterJ = 'j';
		int resultJ = test.Char2Int(letterJ);
		assertEquals("Entered char 'a' to Char2Int", 0, resultA);
		assertEquals("Entered char 'b' to Char2Int", 1, resultB);
		assertEquals("Entered char 'c' to Char2Int", 2, resultC);
		assertEquals("Entered char 'd' to Char2Int", 3, resultD);
		assertEquals("Entered char 'e' to Char2Int", 4, resultE);
		assertEquals("Entered char 'f' to Char2Int", 5, resultF);
		assertEquals("Entered char 'g' to Char2Int", 6, resultG);
		assertEquals("Entered char 'h' to Char2Int", 7, resultH);
		assertEquals("Entered char 'i' to Char2Int", 8, resultI);
		assertEquals("Entered char 'j' to Char2Int", 9, resultJ);
	}
	
	@Test
	public void test_Char2Int_uppercase()
	{
		Board test = new Board();
		char letterA = 'A';
		int resultA = test.Char2Int(letterA);
		char letterB = 'B';
		int resultB = test.Char2Int(letterB);
		char letterC = 'C';
		int resultC = test.Char2Int(letterC);
		char letterD = 'D';
		int resultD = test.Char2Int(letterD);
		char letterE = 'E';
		int resultE = test.Char2Int(letterE);
		char letterF = 'F';
		int resultF = test.Char2Int(letterF);
		char letterG = 'G';
		int resultG = test.Char2Int(letterG);
		char letterH = 'H';
		int resultH = test.Char2Int(letterH);
		char letterI = 'I';
		int resultI = test.Char2Int(letterI);
		char letterJ = 'J';
		int resultJ = test.Char2Int(letterJ);
		assertEquals("Entered char 'A' to Char2Int", 0, resultA);
		assertEquals("Entered char 'B' to Char2Int", 1, resultB);
		assertEquals("Entered char 'C' to Char2Int", 2, resultC);
		assertEquals("Entered char 'D' to Char2Int", 3, resultD);
		assertEquals("Entered char 'E' to Char2Int", 4, resultE);
		assertEquals("Entered char 'F' to Char2Int", 5, resultF);
		assertEquals("Entered char 'G' to Char2Int", 6, resultG);
		assertEquals("Entered char 'H' to Char2Int", 7, resultH);
		assertEquals("Entered char 'I' to Char2Int", 8, resultI);
		assertEquals("Entered char 'J' to Char2Int", 9, resultJ);
		*/
	
}
