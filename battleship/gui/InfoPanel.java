package gui;

//Taken from
//https://www.cs.montana.edu/courses/spring2006/221/programs/program2.html

import javax.swing.*;

import java.awt.*;

/**
 * Creates a panel for displaying game play information.  Extends the JPanel class.
 */

public class InfoPanel extends JPanel {

   // integer representing the number of guesses the player has made
    private int numberOfGuesses = 0;

    //JLabel containing the integer numberOfGuesses
    private JLabel guesses;
    private JLabel playerMessage;
    private JLabel computerMessage;


    /**
     * InfoPanel Default Constructor
     */
    public InfoPanel() {

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(1000, 120));
        guesses = new JLabel(" ");
        guesses = new JLabel("Guesses: " + numberOfGuesses);
        guesses.setForeground(Color.red);
        add(Box.createHorizontalGlue());
        add(guesses);
        add(Box.createHorizontalGlue());

        playerMessage = new JLabel("Hello!");
        playerMessage.setForeground(Color.green);
        computerMessage = new JLabel(" ");
        computerMessage.setForeground(Color.green);
        add(playerMessage);
        add(Box.createHorizontalGlue());
        add(computerMessage);
        add(Box.createHorizontalGlue());

    }


    /**
     * Controls and sets the number of guesses to be displayed
     * @param num the number of guesses the player has made
     */
    public void setNumberOfGuesses(int num){
        //System.out.println(num);
        numberOfGuesses=num;
    }


    /**
     * Controls the text display for the number of guesses
     * @param guessesText the text that displays to the user for number of guesses
     */
    public void setGuesses (String guessesText)
    {
    	guesses.setText(guessesText);
    }


    /**
     * Controls the message displayed to the user on the Player side of the Info Panel
     * @param playerText the text that displays to the user for their message and game progress
     */
    public void setPlayerMessage(String playerText){
        playerMessage.setText(playerText);
    }


    /**
     * Controls the message displayed to the user on the Computer side of the Info Panel
     * @param computerText the text that displays to the user for the Computer's message and game progress
     */
    public void setComputerMessage(String computerText){
        computerMessage.setText(computerText);
    }


    /**
     * Paints the panel with the necessary information
     *
     * @param g the graphics instance to be painted
     */

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setBackground(Color.white);
        //Paints the square outline
        g.setColor(Color.black);
        g.drawRect(0, 0, 1000, 120);

        //Creates the Miss box and label
        g.setColor(Color.green);
        g.fillRect(5, 15, 10, 10);
        g.setColor(Color.black);
        g.drawString("Miss", 20, 25);

        //Creates the Hit box and label
        g.setColor(Color.black);
        g.drawString("Hit", 20, 40);
        g.setColor(Color.red);
        g.fillRect(5, 30, 10, 10);

        //Creates the Destroyer box and label
        g.setColor(Color.black);
        g.drawString("Destroyer (2)", 20, 55);
        g.setColor(Color.magenta);
        g.fillRect(5, 45, 10, 10);

        //Creates the Submarine box and label
        g.setColor(Color.black);
        g.drawString("Submarine (3)", 20, 70);
        g.setColor(Color.pink);
        g.fillRect(5, 60, 10, 10);

        //Creates the Cruiser box and label
        g.setColor(Color.black);
        g.drawString("Cruiser (3)", 20, 85);
        g.setColor(Color.orange);
        g.fillRect(5, 75, 10, 10);

        //Creates the Battleship box and label
        g.setColor(Color.black);
        g.drawString("Battleship (4)", 20, 100);
        g.setColor(Color.cyan);
        g.fillRect(5, 90, 10, 10);

        //Creates the Carrier box and label
        g.setColor(Color.black);
        g.drawString("Carrier (5)", 20, 115);
        g.setColor(Color.yellow);
        g.fillRect(5, 105, 10, 10);
    }


}
