import static org.junit.Assert.*;

import org.junit.Test;

public class ShipTest{

	@Test
	public void test_Carrier(){

        Ship shipTesting1 = new Ship(5);
        assertEquals("Gets ship code", 5, shipTesting1.getShipCode());
        assertEquals("Gets ship size", 5, shipTesting1.getShipSize());
    }

    @Test
    public void test_Battleship(){

        Ship shipTesting2 = new Ship(6);
        assertEquals("Gets ship code", 6, shipTesting2.getShipCode());
        assertEquals("Gets ship size", 4, shipTesting2.getShipSize());
    }

    @Test
    public void test_Cruiser(){

        Ship shipTesting3 = new Ship(7);
        assertEquals("Gets ship code", 7, shipTesting3.getShipCode());
        assertEquals("Gets ship size", 3, shipTesting3.getShipSize());
    }

    @Test
    public void test_Submarine(){

        Ship shipTesting4 = new Ship(8);
        assertEquals("Gets ship code", 8, shipTesting4.getShipCode());
        assertEquals("Gets ship size", 3, shipTesting4.getShipSize());
    }

    @Test
    public void test_Destroyer(){

        Ship shipTesting5 = new Ship(9);
        assertEquals("Gets ship code", 9, shipTesting5.getShipCode());
        assertEquals("Gets ship size", 2, shipTesting5.getShipSize());
    }

		//will still initialize a ship with code 10, but cannot be placed on the board since it has zero length.
		@Test
    public void test_outOfRangeShipCode(){

        Ship shipTesting7 = new Ship(10);
        assertEquals("Gets ship code", 10, shipTesting7.getShipCode());
        assertEquals("Gets ship size", 0, shipTesting7.getShipSize());
    }
		@Test
		public void test_zeroShipCode(){

				Ship shipTesting8 = new Ship(0);
				assertEquals("Gets ship code", 0, shipTesting8.getShipCode());
				assertEquals("Gets ship size", 0, shipTesting8.getShipSize());
		}


}
