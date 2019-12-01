package model;

import java.util.Observable;

/**
 * This class sets indirectly the Player class as an Observable class.
 * This class had to be created because the Player class already extends from the Thread class. 
 */
public class PlayerModel extends Observable{
    
    public Player player;

    /**
     * Constructor - creates a local reference to the player class
     */
    public PlayerModel(Player player){
        this.player = player;
    }

    /**
     * Method that will call the setChanged() method from the Observable class when called
     */
    protected void Changed(){
        setChanged();
    }

    /**
     * Method that will call the notifyObservers() method from the Observable class when called
     */
    protected void toNotify(){
        notifyObservers();
    }

}
