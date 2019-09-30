import static org.junit.Assert.*;

import org.junit.Test;

public class HumanTest{

  //Tests for functionality of Player abstract class through Human subclass
  @Test
  public void test_getter_Shot() {
    Human test = new Human();
    test.shot[0] = 5;
    test.shot[1] = 3;
    assertEquals("Shot y was not copied", 5, test.getShot()[0]);
    assertEquals("Shot x was not copied", 3, test.getShot()[1]);
  }

  @Test
  public void test_privacy_getter_shot() {
    Human test = new Human();
    test.shot[0] = 6;
    test.shot[1] = 8;
    int[] tempShot = test.getShot();
    tempShot[0] = 3;

    assertEquals("Privacy leak occurred when retrieving shot coordinates", 6, test.getShot()[0]);
  }

  @Test
  public void test_lossCheck_emptyBoard() {
    Human test = new Human();
    assertTrue("Empty board, but game is won and over.", test.lossCheck());
  }

  //Tests for constructors in Human class
  @Test
	public void testDefaultConstructor(){
        Human h= new Human();
        int[] shot = h.getShot();
        assertEquals("Default of shot[0] is not equal to 0",0 , shot[0]);
        assertEquals("Default of shot[1] is not equal to 0",0, shot[1]);
        assertEquals("Default of win boolean is not false", false, h.getWin());
        assertEquals("Default message is not empty", "", h.getMessage());
    }

    //Copy constructor for the Human class should only copy the board and the shot
    @Test
    public void testCopyConstructor(){
          Human h = new Human();
          h.setDirection('n');
          h.setMessage("hello");
          h.setWin(true);
          int[] hShot= {9,1};
          h.setShot(hShot);

          Human copy= new Human(h);
          int[] shot = copy.getShot();
          assertEquals("Shot[0] within copied Human is not equal to set value",9 , shot[0]);
          assertEquals("Shot[1] within copied Human is not equal to set value",1, shot[1]);
          assertEquals("win within copied Human is not the default value (false)", false, copy.getWin());
          assertEquals("message within copied Human is not the default value (empty)", "", copy.getMessage());
      }



}
