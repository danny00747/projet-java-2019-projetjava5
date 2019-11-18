package server;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 * //TODO
 */
public class Player extends Thread {

    String userName;
    private myGrid myGrid;
    private enemyGrid enemyGrid;
    private Unit Airport, RadarTower, HeadQuarter, RailwayGun, MMRL, Tank;
    private Unit[] units = new Unit[6];
    private String myKey = "";
    protected boolean isReady = false;
    protected boolean isMyTurn = false;


    private DataInputStream in; 
    private DataOutputStream out; 
    private Socket sock; 
    private static int numberOfClientsConnected=0;

    //Colours on ouput => ONLY WORKS ON LINUX (maybe mac ?) NOT WINDOWS
	protected static final String red    = "\u001B[31m";
	protected static final String blue   = "\u001B[34m";
	protected static final String reset  = "\u001B[0m";
	protected static final String purple = "\u001B[35m";
	protected static final String yellow = "\u001B[33m";
	protected static final String white  = "\u001B[37m";

    /**
     * Constructor
     * //TODO
     * @param sock
     * @param in
     * @param out
     */
    public Player(Socket sock, DataInputStream in, DataOutputStream out)  
    { 
        myGrid = new myGrid();
        enemyGrid = new enemyGrid();

        Airport = new Unit("Airport (2x4)", 8);
        RadarTower = new Unit("Radar Tower (2x3)", 6);
        HeadQuarter = new Unit("HeadQuarter (2x2)", 4);
        RailwayGun = new Unit("Railway Gun (1x6)", 6);
        MMRL = new Unit("MMRL (2x2)", 4);
        Tank = new Unit("Tank (1x2)", 2);
        units[0] = Airport;
        units[1] = RadarTower;
        units[2] = HeadQuarter;
        units[3] = RailwayGun;
        units[4] = MMRL;
        units[5] = Tank;

        this.sock = sock;
        numberOfClientsConnected++;
        this.in = in; 
        this.out = out;
        //testConnection();//Tests if there's 0 client OR More than 2 connected => If so Closes ressources and Exit program. //TODO

        if(Server.Players.get("P1") == null){
            myKey = "P1";
            Server.Players.replace("P1",this);
        }
        else{
            myKey = "P2";
            Server.Players.replace("P2",this);
        }
        
    } 

    /**
     * Method that takes a string and tries to send it to the client.
     * 
     * @param str {String} - A String to send to the client 
     */
    private void sendToClient(String str){
        try{
            out.writeUTF(str);
        }
        catch(IOException e){
            //TODO
        }
    }

    /**
     * Method that waits for string from the client and returns it when received. 
     * 
     * @return a string received from the server
     */
    private String getFormClient(){
        try{
            return in.readUTF();
        }
        catch(IOException e){
            //TODO
        }
        return "";
    }

    /**
     * 
     */
    private void getClientInfo(){
        long id = Thread.currentThread().getId(); 

        userName = getFormClient();
        System.out.println("A new "+Server.purple+"client"+Server.blue+" \""+userName+"\""+Server.reset+" with id" +Server.red+" ("+id+")"+Server.reset+" joined via " +Server.yellow+ sock.getLocalAddress().toString().replaceAll("/", "")+Server.reset);//To see which ip the client used to connect 
        System.out.println("-----------------------------------------");
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

        sendToClient("Q?");
        sendToClient("\nWhere do you want to place the " + unit.getName()+ "? Enter top-left and bottom-right coordinates separated by a whitespace.\n");
        unitCoords = new String[unit.getSize()];
        while (!isPlaced) {
            userInput = getFormClient();
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
                        sendToClient("Rem");
                        sendToClient("3");
                        for (int i = 0; i < unitCoords.length; i++) {
                            myGrid.setGridCell(unitCoords[i], unit);
                            sendToClient("insertUnit");
                            sendToClient(unitCoords[i]);
                        }
                    } else {
                        sendToClient("Rem");
                        sendToClient(""+(failCount + 1));
                        sendToClient("Q?");
                        sendToClient("Input not valid. Units can not overlap eachother. Please enter valid input\n");
                        failCount = 1;
                    }
                } else {
                    isPlaced = false;
                    sendToClient("Rem");
                    sendToClient(""+(failCount + 1));
                    sendToClient("Q?");
                    sendToClient("Input not valid. Please enter valid input\n");
                    failCount = 1;
                }
            } catch (Exception e) {
                isPlaced = false;
                sendToClient("Rem");
                sendToClient(""+(failCount + 1));
                sendToClient("Q?");
                sendToClient("Input not valid. Please enter valid input\n");
                failCount = 1;
            }
        }
    }

    /**
     * //TODO
     */
    protected void placeUnits() {
        //unitPlacer(Airport);
        //unitPlacer(RadarTower);
        //unitPlacer(HeadQuarter); //TODO
        //unitPlacer(RailwayGun);
        //unitPlacer(MMRL);
        unitPlacer(Tank);
        sendToClient("Q?");
        sendToClient("All units are placed, press any key to start playing.\n");
        getFormClient();
        sendToClient("Rem");
        sendToClient("2");
        isReady = true;
    }

    /**
     * Method that returns the reference to the other player 
     * 
     * @return {Player} - The object Player of the other client 
     */
    protected Player otherPlayer(){
        if(myKey.equals("P1")){
            return Server.Players.get("P2");
        }
        else{
            return Server.Players.get("P1");
        }
    }

    /**
     * //TODO
     * @param shotCoord
     */
    protected void sendHit(String shotCoord){
        sendToClient("Rem"); sendToClient("2");
        sendToClient("Hit");
        sendToClient(shotCoord);
        otherPlayer().sendToClient("myHit");
        otherPlayer().sendToClient(shotCoord);
    }

    /**
     * //TODO
     * @param shotCoord
     */
    protected void sendNoHit(String shotCoord){
        sendToClient("Rem"); sendToClient("2");
        sendToClient("noHit");
        sendToClient(shotCoord);
        otherPlayer().sendToClient("myNoHit");
        otherPlayer().sendToClient(shotCoord);
    }

    /**
     * //TODO
     * @param shotCoord
     */
    protected void sendDestroyed(String shotCoord){
        sendToClient("Rem"); sendToClient("2");
        sendToClient("Destroyed");
        sendToClient(shotCoord);
        otherPlayer().sendToClient("myDestroyed");
        otherPlayer().sendToClient(shotCoord);
    }

    protected String getAvailableShotTypes(){
        String availableShotTypes = "S ";
        if(Airport.getIsAlive()){
            availableShotTypes += "- A ";
        }
        if(RadarTower.getIsAlive()){
            availableShotTypes += "- D ";
        }
        if(RailwayGun.getIsAlive()){
            availableShotTypes += "- B ";
        }
        if(MMRL.getIsAlive()){
            availableShotTypes += "- R ";
        }
        return availableShotTypes;
    }
    /**
     * //TODO
     * 
     */
    protected void shoot() {
        //TODO -> check for user input errors etc.
        String shotType, shotCoord;

        sendToClient("Q?");
        sendToClient("What type of shot do you want to use?     Available: "+ getAvailableShotTypes() +"\n"+
        "S : Singleshot; A : Airstrike; D : Radar discovery; B : Bigshot; R : Rocketstrike\n");
        shotType = getFormClient();
        //check userinput
        switch (shotType) {
            case "S":
                sendToClient("Rem"); sendToClient("3");
                sendToClient("Q?");
                sendToClient("Enter the coordinate of the shot. Ex: H4 \n");
                shotCoord = getFormClient();
                //TODO - check if coord as already been shot 
                if(otherPlayer().myGrid.getGridCell(shotCoord) != null){ //hit
                    Unit enemyUnit = otherPlayer().myGrid.getGridCell(shotCoord);
                    enemyUnit.setCoordState(shotCoord);
                    enemyGrid.setGridCell(shotCoord, 1);
                    if(enemyUnit.getIsAlive()){
                        sendHit(shotCoord);
                    }
                    else{
                        for ( String key : enemyUnit.coordState.keySet() ) {
                            sendDestroyed(key);
                        }
                    }
                }
                else{ //no hit
                    enemyGrid.setGridCell(shotCoord, -1);
                    sendNoHit(shotCoord);
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
    protected void play(){
        while(true){
            if(isMyTurn){
                shoot();
                this.isMyTurn = false;
                otherPlayer().isMyTurn = true;
            }
            else{
                try{Thread.sleep(100);}catch(InterruptedException ex){}
            }
        }
    }

    /**
     * //TODO
     */
    @Override
    public void run()  { 
        String strFromClient ="";
        String strToClient ="";

        getClientInfo();
        sendToClient("displayGrid");
        placeUnits();
        sendToClient("Waiting for other player\n");
        while(!Server.allPlayerConnected){
            try{Thread.sleep(100);}catch(InterruptedException ex){}
        };
        sendToClient("Rem");sendToClient("1");
        play();
    }
}
