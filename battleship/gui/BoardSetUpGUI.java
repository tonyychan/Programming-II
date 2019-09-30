
package gui;
import logic.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates the starting game view, where the user can place their ships
 */

public class BoardSetUpGUI extends JPanel {

    private JPanel doneResetPanel = new JPanel(); //contains the done and reset buttons
    private Buttons playerGrid; //contains the players game grid
    private JPanel directionPanel = new JPanel();
    private JLabel directionMsg = new JLabel("Please select a square then a direction for placing your ships.");
    private JPanel shipSetUpPanel = new JPanel();
    private JPanel difficultyPanel = new JPanel();


    /**
     * Initializes the view for starting the game and setting up the ships
     * @param human the user's player object
     * @param listener to use the implemented Action from the Controller class
     */
    public BoardSetUpGUI(Human human, ActionListener listener){

        JPanel content = new JPanel(); //holds the content of the GameStarting screen
        JLabel msg = new JLabel("Welcome to Battleship!");
        playerGrid = new Buttons(human, listener);

        setPreferredSize(new Dimension(1000,800));
        setSize(550,550);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
        setVisible(true);

        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(msg);
        content.add(playerGrid);

        setDoneResetPanel(listener);
        content.add(doneResetPanel);

        directionMsg.setFont(new Font("Serif", Font.BOLD,14));
        directionMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(directionMsg);

        setShipSetUpPanel(listener);
        content.add(shipSetUpPanel);

        setDifficultyPanel(listener);
        content.add(difficultyPanel);
        add(content);
    }


    /**
     * accessor for the player's button array that display's the user's board
     * @return playerGrid button array
     */
    public Buttons getPlayerGrid() {
        return playerGrid;
    }


    /**
     * creates the panel with the direction buttons and the next ship button for the user
     * @param listener to implement the actions declared in the Controller class
     */
    public void setShipSetUpPanel(ActionListener listener){
        setDirectionPanel(listener);
        shipSetUpPanel.setLayout(new BoxLayout(shipSetUpPanel, BoxLayout.X_AXIS));

        shipSetUpPanel.add(directionPanel);
        shipSetUpPanel.add(Box.createHorizontalGlue());

        JButton shipSet = new JButton("Next Ship");
        shipSet.addActionListener(listener);
        shipSet.setActionCommand("NEXT_SHIP");

        shipSetUpPanel.add(shipSet);
        shipSetUpPanel.add(Box.createHorizontalGlue());
    }


    /**
     * creates the button options to set the ships, complete ship setup or reset their board.
     * @param listener to implement the Actions in the Controller Class
     */
    public void setDoneResetPanel(ActionListener listener){

        doneResetPanel.setLayout(new BoxLayout(doneResetPanel, BoxLayout.X_AXIS));
        doneResetPanel.setBackground(Color.black);

        JButton done = new JButton("Done");
        done.addActionListener(listener);
        done.setActionCommand("DONE");

        JButton reset = new JButton("Reset");
        reset.addActionListener(listener);
        reset.setActionCommand("RESET");

        JButton autoSetShips = new JButton("Set Ships For Me");
        autoSetShips.addActionListener(listener);
        autoSetShips.setActionCommand("AUTO_SET_SHIPS");

        doneResetPanel.add(Box.createHorizontalGlue());
        doneResetPanel.add(done);
        doneResetPanel.add(Box.createHorizontalGlue());
        doneResetPanel.add(reset);
        doneResetPanel.add(Box.createHorizontalGlue());
        doneResetPanel.add(autoSetShips);
        doneResetPanel.add(Box.createHorizontalGlue());
    }


    /**
     * direction panel holds the buttons (North, South, East, West) for placing ships.
     * @param listener to implement the Actions in the Controller Class
     */
    public void setDirectionPanel(ActionListener listener){

        directionPanel.setLayout(new GridLayout(3,3));
        directionPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        directionPanel.setPreferredSize(new Dimension(300,100));
        directionPanel.add(new JLabel()); //for spacing out the buttons in a nice way

        JButton north = new JButton("North");
        north.addActionListener(listener);
        north.setActionCommand("NORTH");

        directionPanel.add(north);
        directionPanel.add(new JLabel());

        JButton west = new JButton("West");
        west.addActionListener(listener);
        west.setActionCommand("WEST");
        directionPanel.add(west);

        directionPanel.add(new JLabel());//for spacing out the buttons in a nice way

        JButton east = new JButton("East");
        east.addActionListener(listener);
        east.setActionCommand("EAST");
        directionPanel.add(east);

        JButton south = new JButton("South");
        south.addActionListener(listener);
        south.setActionCommand("SOUTH");

        directionPanel.add(new JLabel());
        directionPanel.add(south);
        directionPanel.add(new JLabel());
    }


    /**
     * creates the panel for the user to select their opponent's difficulty level
     * @param listener to implement the Actions in the Controller Class
     */
    public void setDifficultyPanel(ActionListener listener){

        difficultyPanel.setBackground(Color.LIGHT_GRAY);
        JLabel difficultyMessage = new JLabel("Please select your difficulty: ");
        difficultyMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup group = new ButtonGroup();

        JRadioButton easy = new JRadioButton("Easy");
        easy.addActionListener(listener);
        easy.setActionCommand("EASY");
        easy.setSelected(true);

        JRadioButton medium =new JRadioButton("Harder");
        medium.addActionListener(listener);
        medium.setActionCommand("HARDER");

        group.add(easy);
        group.add(medium);

        difficultyPanel.add(difficultyMessage);
        difficultyPanel.add(easy);
        difficultyPanel.add(medium);
    }


    /**
     * Gets the user's name for the the High Score file
     * @return name string to represent the user's name
     */

    public String getUserName(){
        JOptionPane getName = new JOptionPane();
        String name = getName.showInputDialog(getName,"What is your name?","What is your name?",JOptionPane.PLAIN_MESSAGE);
        return name;

    }

    /**
     * mutator for the JLabel directionMsg that sits below the board.
     * @param message the message to be copied and displayed
     */
    public void updateDirectionMsg(String message){directionMsg.setText(message);}

}
