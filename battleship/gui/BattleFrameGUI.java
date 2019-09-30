
package gui;
import logic.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * GUI class that is used to display the game as it is played
 */
public class BattleFrameGUI extends JPanel{

    private InfoPanel info; //info panel that explains the current game status
    private Buttons playerGrid; //Button array displaying the player's board
    private Buttons computerGrid; //Button array displaying the computer's board


    /**
     * initializes the game play view
     * @param human the human playing the game
     * @param computer the computer playing the game
     * @param listener to use the implemented Action from the Controller class
     */
    public BattleFrameGUI(Human human, Computer computer, ActionListener listener) {

        JPanel content = new JPanel();
        JPanel gameBoard = new JPanel();
        JPanel boardLabels = new JPanel();

        playerGrid = new Buttons(human, listener);
        computerGrid = new Buttons(computer, listener);

        setPreferredSize(new Dimension(1000,800));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
        setVisible(true);

        boardLabels = createBoardLabels(boardLabels);
        content.add(boardLabels);

        gameBoard.setLayout(new GridLayout(1,2,10,10));
        gameBoard.add(playerGrid);
        gameBoard.add(computerGrid);
        content.add(gameBoard);

        info = new InfoPanel();
        content.add(info);
        add(content);
    }


    /**
     *creates the Labels above the game grids that display who's board is the player's and the computer's for the user
     * @param boardLabels JPanel to hold the labels Human and Computer
     * @return boardLabels the adjusted JPanel that is filled in
     */
    public JPanel createBoardLabels(JPanel boardLabels){

        boardLabels.setLayout(new BoxLayout(boardLabels,BoxLayout.X_AXIS));
        JLabel playerLbl = new JLabel("Human");
        JLabel computerLbl = new JLabel("Computer");
        playerLbl.setFont(new Font("Serif", Font.BOLD, 18));
        computerLbl.setFont(new Font("Serif", Font.BOLD, 18));
        boardLabels.add(Box.createHorizontalGlue());
        boardLabels.add(playerLbl);
        boardLabels.add(Box.createHorizontalGlue());
        boardLabels.add(Box.createHorizontalGlue());
        boardLabels.add(computerLbl);
        boardLabels.add(Box.createHorizontalGlue());
        return boardLabels;
    }


    /**
     * Accessor for the computer grid button array
     * @return computerGrid button array
     */
    public Buttons getComputerGrid(){return computerGrid;}


    /**
     * Accessor for the player grid button array
     * @return playerGrid button array
     */
    public Buttons getPlayerGrid(){return playerGrid;}


    /**
     * Accessor for the Info Panel object
     * @return info the Info Panel
     */
    public InfoPanel getInfoPanel() {return info;}

}
