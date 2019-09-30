
package gui;
import logic.*;


import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

//Source for water image: http://www.how-to-draw-funny-cartoons.com/vector-wave.html
//Source for fire image: https://www.123rf.com/photo_64993445_stock-vector-cartoon-comic-graphic-design-for-explosion-blast-dialog-box-background.html

/**
 * Used to display the button array to represent the Computer & User's game board and game play
 */
public class Buttons extends  JPanel{

    public static int MAXROW = 10;
    public static int MAXCOL = 10;

    private JButton[][] button = new JButton[MAXROW][MAXCOL];
    private JPanel playerGrid = new JPanel();
    private ImageIcon fire = new ImageIcon(getFire()); //initialized as an instance variable to increase application speed
    private ImageIcon water = new ImageIcon(getWater());//initialized as an instance variable to increase application speed

    //board value constants
    public static final int EMPTY = 0;
    public static final int HIT = 3;
    public static final int MISS = 2;
    public static final int CARRIER = 5;
    public static final int BATTLESHIP = 6;
    public static final int CRUISER = 7;
    public static final int SUBMARINE = 8;
    public static final int DESTROYER = 9;


    /**
     * initializes the gameBoard button arrays
     * @param aPlayer player that is being initialized
     * @param listener to implement the Action in the Controller Class
     */
    public Buttons(Player aPlayer, ActionListener listener) {

        playerGrid.setVisible(true);
        playerGrid.setLayout(new GridLayout(MAXROW,MAXCOL));

        for (int row = 0; row < MAXROW; row++) {
            for (int col = 0; col < MAXCOL; col++) {
                button[row][col] = new JButton();
                button[row][col].setPreferredSize(new Dimension(48, 48));

                button[row][col].addActionListener(listener);
                button[row][col].setActionCommand(aPlayer.getClass().getName()+" "+Integer.toString(row)+ " "+Integer.toString(col));

                button[row][col].setBackground(Color.blue);
                button[row][col].setIcon(water);

                playerGrid.add(button[row][col]);
            }
        }
        colorButtons(aPlayer);
        add(playerGrid);
    }


    /**
     * method to grab the fire (hit) image from the image file
     * IO Exception is handled through creation of a new image that is red
     * If the new image creation fails the icon is set to null and the button will display as it's background color
     * @return fireImage the image file
     */
        public Image getFire() {
        BufferedImage fireImage = null;

        try {
            fireImage = ImageIO.read(new File("lit.jpg"));
        }
        catch(IOException ioe) {
            try {
                //Creates a red file if image cannot be found
                fireImage = new BufferedImage(48, 48, BufferedImage.TYPE_INT_RGB);
                File fire = new File("Fire.png");
                Color red = new Color(255, 0, 0);
                for (int x = 0; x < 48; x++) {
                    for (int y = 0; y < 48; y++) {
                        fireImage.setRGB(x, y, red.getRGB());
                    }
                }
                ImageIO.write(fireImage, "PNG", fire);

            } catch (IOException e) {
                //Set the image to null if there is another error
                fireImage=null;
            }
        }
        return fireImage;
    }


    /**
     * method to grab the water background from the image file
     * IO Exception is handled through creation of a new image that is blue
     * If the new image creation fails the icon is set to null and the button will display as it's background color
     * @return waterImage the image file
     */
    public Image getWater() {
        BufferedImage waterImage = null;

        try {
            waterImage = ImageIO.read(new File("wave.jpg"));
        }
        catch(IOException ioe) {
            try {
                //Creates a blue file if image cannot be found
                waterImage = new BufferedImage(45, 45, BufferedImage.TYPE_INT_RGB);

                File water = new File("Water.png");
                int red = 0;
                int green = 0;
                int blue = 255;
                int col = red | green | blue;
                for (int x = 0; x < 45; x++) {
                    for (int y = 0; y < 45; y++) {
                        waterImage.setRGB(x, y, col);
                    }
                }
                ImageIO.write(waterImage, "PNG", water);

            } catch (IOException e) {
                //Set the image to null if there is another error
                waterImage=null;
            }
        }
        return waterImage;
    }


    /**
     * updates the colors of the buttons to show the user where their ships are placed
     * also shows if the user or computer has hit or missed during the game play
     * @param aPlayer the player that is being updated
     */
    public void colorButtons(Player aPlayer)  {

        for (int row = 0; row < MAXROW; row++) {
            for (int col = 0; col < MAXCOL; col++) {
                if (aPlayer.getBoard().getBoard()[row][col] == EMPTY) { //empty
                    button[row][col].setIcon(water);
                    button[row][col].setBackground(Color.blue);

                } else if (aPlayer.getBoard().getBoard()[row][col] == MISS) {
                    button[row][col].setIcon(null);
                    button[row][col].setBackground(Color.green);

                } else if (aPlayer.getBoard().getBoard()[row][col] == HIT) {
                   button[row][col].setIcon(fire);
                    button[row][col].setBackground(Color.red);
                }
                if(aPlayer.getClass().getName().contains("logic.Human")) {
                    if (aPlayer.getBoard().getBoard()[row][col] == CARRIER) {
                        button[row][col].setIcon(null);
                        button[row][col].setBackground(Color.yellow);

                    } else if (aPlayer.getBoard().getBoard()[row][col] == BATTLESHIP) {
                        button[row][col].setIcon(null);
                        button[row][col].setBackground(Color.cyan);

                    } else if (aPlayer.getBoard().getBoard()[row][col] == CRUISER) {
                        button[row][col].setIcon(null);
                        button[row][col].setBackground(Color.orange);

                    } else if (aPlayer.getBoard().getBoard()[row][col] == SUBMARINE) {
                        button[row][col].setIcon(null);
                        button[row][col].setBackground(Color.pink);

                    } else if (aPlayer.getBoard().getBoard()[row][col] == DESTROYER) {
                        button[row][col].setIcon(null);
                        button[row][col].setBackground(Color.magenta);
                    }
                }
            }
        }
    }


    /**
     * To show the user where they have clicked while they are setting up their ships
     * @param aPlayer the player who is placing their ships
     * @param coord the coordinates of the button that was pressed
     */
    public void colorSingleButton(Player aPlayer, int[] coord) {
        colorButtons(aPlayer);
        int row = coord[0];
        int col = coord[1];
        button[row][col].setIcon(null);
        button[row][col].setBackground(Color.green);
            }


}
