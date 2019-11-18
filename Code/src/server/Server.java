package server;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 * @author Morgan Valentin
 * @Source code used to understand => https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
*/

public class Server  { 	
    
    private Scanner userInput = new Scanner(System.in);
    private int port;
    private ServerSocket servSock;
    private Socket sock;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected static boolean allPlayerConnected = false;

	//Colours on ouput => ONLY WORKS ON LINUX (maybe mac ?) NOT WINDOWS
	protected static final String red    = "\u001B[31m";
	protected static final String blue   = "\u001B[34m";
	protected static final String reset  = "\u001B[0m";
	protected static final String purple = "\u001B[35m";
	protected static final String yellow = "\u001B[33m";
	protected static final String white  = "\u001B[37m";
	
    protected static HashMap<String,Player> Players = new HashMap<>();

    /**
     * Function that randomly gives the first turn to one of the players
     */
    protected void giveFirstTurn() {
        int chance = ((int) (Math.random() * 50 + 1)) % 2;
        if (chance == 0) {
            Players.get("P1").isMyTurn = true;
        } else {
            Players.get("P2").isMyTurn = true;
        }
    }


    /**
     * //TODO
     * 
     */
    protected void initServer(){
        Players.put("P1",null);
        Players.put("P2",null);

        //Selecting port number from user to use => Plus tard par interface graphique
    	//Attention => Le port du client devra etre identique sinon il ne "verra" pas le server !!
    	try {
        	System.out.println(purple+"Port number ?"+reset);
    		port = userInput.nextInt();
            servSock = new ServerSocket(port); //Creating a new serverSocket with port given by user. 
            
            System.out.println(red+"Server started !"+reset);
            System.out.println("Waiting for player(s) to connect");
            int clientCount =0;
            // Infinite loop => Waiting that a client connects {accept() method}
            while (true)  { 
                sock = null; //Socket for client communication => "Active" socket
                try { 
                    // socket object to receive incoming client requests => "Passive" socket
                    sock = servSock.accept(); 
                    // In and out streams => Information received (inputStream) and sent (outputStream) :
                    in = new DataInputStream(sock.getInputStream()); 
                    out = new DataOutputStream(sock.getOutputStream()); 
                    
                    // create a new thread object => Nouveau "processus" en quelque sorte
                    Thread t = new Player(sock, in, out); 
                    
                    // Demarre le processus t (gestion des clients) et mais en "pause" la suite des instructions du server.
                    t.start(); //Start method search the run() method from the Threat class heritence 

                    clientCount ++;
                    if(clientCount == 2){
                        while(!(Players.get("P1").isReady && Players.get("P2").isReady)){}
                        giveFirstTurn();
                        allPlayerConnected = true;
                        System.out.println("All players connected, game started");
                        //TODO
                        break;
                    } 
                    
                } 
                catch (IOException e){ 
                	servSock.close();
                	userInput.close();
                	sock.close();
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e){ 
            //TODO
        }
    	catch(InputMismatchException a) {
        	System.out.println(Server.red+"FATAL ERROR :"+Server.reset+Server.purple+" IP and Port must be Integer"+Server.reset);
        }	 
    } 


    public static void main(String[] args) throws Exception{
        Server server = new Server();
        server.initServer();
    }
    	
}


  
