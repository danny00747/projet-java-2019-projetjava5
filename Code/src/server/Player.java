/**
 * @author Martin Michotte
 * @date 12/11/2019
 */

package server;

import java.io.*;
import java.util.Arrays;


public class Player {

    private CMD cmd;
    private CMD_grid cmdGrid = new CMD_grid();
    private String name;
    private myGrid myGrid;
    private enemyGrid enemyGrid;
    private Unit Airport, RadarTower, HeadQuarter, RailwayGun, MMRL, Tank;

    /**
     * Constructor
     */
    protected Player(String name) {
        this.name = name;

        myGrid = new myGrid();
        enemyGrid = new enemyGrid();
        cmdGrid.displayGrid();

        Airport = new Unit("Airport (2x4)", 8);
        RadarTower = new Unit("Radar Tower (2x3)", 6);
        HeadQuarter = new Unit("HeadQuarter (2x2)", 4);
        RailwayGun = new Unit("Railway Gun (1x6)", 6);
        MMRL = new Unit("MMRL (2x2)", 4);
        Tank = new Unit("Tank (1x2)", 2);

        cmd = new CMD();
    }

    /**
     * Function that asks the player to place his unit on the grid and saves it.
     * 
     * @param unit {Unit} - //TODO
     */
    private void unitPlacer(Unit unit) {
        String userInput, coord1, coord2;
        String[] unitCoords;
        int[] coord1Index, coord2Index;
        boolean isPlaced = false;
        int numberOfRows, numberOfCols;
        int failCount = 0;

        cmd.println("\nWhere do you want to place the " + unit.getName()
                + "? Enter top-left and bottom-right coordinates separated by a whitespace.");
        unitCoords = new String[unit.getSize()];
        while (!isPlaced) {
            userInput = cmd.getUserInput();
            try {
                coord1 = userInput.split(" ")[0];
                coord2 = userInput.split(" ")[1];
                coord1Index = myGrid.getCoordIndex(coord1);
                coord2Index = myGrid.getCoordIndex(coord2);
                numberOfRows = coord2Index[0] - coord1Index[0] + 1;
                numberOfCols = coord2Index[1] - coord1Index[1] + 1;
                // check if input is correct and add if place is empty:
                int k = 0;
                if (numberOfRows * numberOfCols == unit.getSize()) {
                    for (int i = 0; i < numberOfRows; i++) {
                        for (int j = 0; j < numberOfCols; j++) {
                            unitCoords[k] = myGrid.rowNames[coord1Index[0] + i] + myGrid.colNames[coord1Index[1] + j];
                            k++;
                        }
                    }
                    for (int i = 0; i < unitCoords.length; i++) {
                        if (myGrid.getGridCell(unitCoords[i]) != null) {
                            isPlaced = false;
                            break;
                        } else {
                            isPlaced = true;
                        }
                    }

                    if (isPlaced) {
                        unit.initCoordState(unitCoords);
                        cmd.removeLines(3);
                        for (int i = 0; i < unitCoords.length; i++) {
                            myGrid.setGridCell(unitCoords[i], unit);
                            cmdGrid.insertInGrid("Unit", unitCoords[i], false);
                        }
                    } else {
                        cmd.removeLines(failCount + 1);
                        cmd.println("Input not valid. Units can not overlap eachother. Please enter valid input");
                        failCount = 1;
                    }
                } else {
                    isPlaced = false;
                    cmd.removeLines(failCount + 1);
                    cmd.println("Input not valid. Please enter valid input");
                    failCount = 1;
                }
            } catch (Exception e) {
                isPlaced = false;
                cmd.removeLines(failCount + 1);
                cmd.println("Input not valid. Please enter valid input");
                failCount = 1;
            }
        }
    }

    /**
     * //TODO
     */
    protected void placeUnits() {
        unitPlacer(Airport);
        unitPlacer(RadarTower);
        unitPlacer(HeadQuarter);
        unitPlacer(RailwayGun);
        unitPlacer(MMRL);
        unitPlacer(Tank);
        cmd.println("All units are placed, press any key to start playing.");
        cmd.getUserInput();
        cmd.removeLines(2);
    }

    /**
     * //TODO
     * 
     * @param shotType   {String} - the type of the shot : Single-shot -> "S",
     *                   Airstrike -> "A", Radar Discovery -> "D", Big Shot -> "B",
     *                   Rocketstrike -> "R"
     * @param coord      {String} - the coordinate of the shot. If special shot, the
     *                   coordinate of the center of the shot. ex: "H4"
     * @param isOutGoing {boolean} - tells if the shot is outgoing or incomming
     */
    protected void shoot(String shotType, String coord, boolean isOutGoing) {
        // TODO - check user input for errors : not in range, not a correct string, ...
        // check if bonus is available
        switch (shotType) {
        // TODO - add send information to other player
        case "S":
            if (isOutGoing) {
                // TODO
            } else {
                if (myGrid.getGridCell(coord) != null) {
                    cmdGrid.insertInGrid("Hit", coord, isOutGoing);
                    //TODO add info into unit
                } else {
                    cmdGrid.insertInGrid("noHit", coord, isOutGoing);
                }

            }
            break;

        case "A":
            break;

        case "D":
            break;

        case "B":
            break;

        case "R":
            break;

        default:

        }
    }

    /**
     * //TODO
     */
    protected void quitGame() {
        // TODO
    }

    // only for debugging !! 
    public static void main(String[] args) {
        Player p = new Player("Martin");
        p.placeUnits();
        p.shoot("S", "H3", false);
        p.shoot("S", "J10", false);
    }

}