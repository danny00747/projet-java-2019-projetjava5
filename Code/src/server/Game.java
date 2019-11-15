/**
 * @author Martin Michotte
 * @date 12/11/2019
 * 
 * run from terminal (must be VT100 compatible): 
 * compile: javac package/file.java 
 * run: java package/file
*/

package server;

import java.io.*;
import java.util.*;


public class Game {

    private CMD cmd = new CMD();;

    private Player Player1;
    private Player Player2;
    private Player activePlayer = Player1;
    private Player passivePlayer = Player2;


    /**
     * Constructor
     */
    private Game() {
        // TODO
    }

    /**
     * 
     */
    private void createPlayer(){
        Player1 = new Player("Martin"); //TODO initialise when input from client 
        Player2 = new Player("Baptiste"); //TODO initialise when input from client
    }

    /**
     * Function that randomly gives the first turn to one of the players
     */
    private void giveFirstTurn() {
        int chance = ((int) (Math.random() * 50 + 1)) % 2;
        if (chance == 0) {
            activePlayer = Player1;
            passivePlayer = Player2;
        } else {
            activePlayer = Player2;
            passivePlayer = Player1;
        }
    }

    /**
     * Function that gives the turn to tho other player
     */
    private void changeTurn() {
        Player swapPlayer = activePlayer;
        activePlayer = passivePlayer;
        passivePlayer = swapPlayer;
    }

    /**
     * Function that displays the user manual
     */
    private void userManual() {
        int numberOfLines = 0;
        try {
            File myObj = new File("utilities/Manual.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String line = myReader.nextLine();
              System.out.println(line);
              numberOfLines ++;
            }
            myReader.close();
          } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        cmd.println("Enter any key to close the user manual. ");
        cmd.getUserInput();
        cmd.removeLines(numberOfLines+2);
    }

    /**
     * 
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.giveFirstTurn();
        //game.userManual();
    }
}
