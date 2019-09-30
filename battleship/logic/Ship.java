package logic;

/** Ship class contains the ship specific attributes of shipSize and shipCode
* Ship Code 5 = Carrier Ship
* Ship Code 6 = Battleship
* Ship Code 7 = Cruiser
* Ship Code 8 = Submarine
* Ship Code 9 = Destroyer
**/

public class Ship {

    private int shipSize;
    private int shipCode;

    /** Constructor for the Ship class
    * @param shipCode the code for type of ship
    * shipCode effects the length of the ship
    **/

    public Ship(int shipCode){
        this.shipCode = shipCode;
        setShipSize(shipCode);
    }

    /** Copy constructor for the Ship class
    * @param toCopy: the original ship to create a copy off of.
    * shipCode effects the length of the ship
    **/

    public Ship(Ship toCopy){
        this.shipCode = toCopy.getShipCode();
        setShipSize(toCopy.getShipCode());
    }

    /** accessor for the shipCode variable
    * @return shipCode the code for type of ship
    **/
    public int getShipCode(){
        return shipCode;
    }

    /** mutator for the shipSize variable
    * @param shipCode the code for type of ship
    **/
    public void setShipSize(int shipCode){

        if (shipCode == 5){
            shipSize = 5;
        }
        else if (shipCode == 6){
            shipSize = 4;
        }
        else if (shipCode == 7){
            shipSize = 3;
        }
        else if (shipCode == 8){
            shipSize = 3;
        }
        else if (shipCode == 9){
            shipSize = 2;
        }
    }

    /** accessor for the shipSize variable
    * @return shipSize
    **/
    public int getShipSize(){
        return shipSize;
    }

}
