package client;

import java.io.*; 
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner; 
  
/**
 * @author Morgan Valentin
 * @Source code used to understand => https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
*/

/**
 * //TODO
 */
public class Client  { 

    private String name;
    private Scanner scn;
    private int port;
    private String ip;
    private Socket sock;
    protected DataInputStream in;
    protected DataOutputStream out;

    private CmdGridDisplay gridDisplay = new CmdGridDisplay();

	//Colours on ouput => ONLY WORKS ON UNIX NOT WINDOWS
	private static final String red    = "\u001B[31m";
	private static final String blue   = "\u001B[34m";
	private static final String reset  = "\u001B[0m";
    private static final String purple = "\u001B[35m";
    
     /**
     * Constructor
     * 
     */
    public Client(){
        scn = new Scanner(System.in); 
    }
	
    /**
     * Method that checks if name (input from user) is between 4 and 12 chracters in length
     * @param name {String} - //TODO
     * @param scanner {Scanner} - //TODO
     */
	private void checkNameClient(String name,Scanner scanner) {
        while (name.length()<4 || name.length() >13) {
            System.out.println("Invalid input. Please re-enter a name (min 4 - max 12 :");
            name = scanner.nextLine();
        }
    }
    
    /**
     * Method thet initialises a connection with the server
     * 
     */
    private void InitConnection(){

        // Retrieving client's name from user input :
        System.out.println("What's your name ?");
        name = scn.nextLine();
        checkNameClient(name,scn);

        // Retrieving port from user input :
        System.out.println(purple+"Port number ?"+reset);
        port = scn.nextInt();
        System.out.println(blue+name+"> "+purple+port);
        

        //Retrieving ip adress from user input :
        System.out.println(red+"IP address ? (nothing + enter = localhost)"+reset);
        ip = scn.nextLine();
        ip = scn.nextLine();
        if (ip.length()==0) {ip = "localhost";}
        else if (ip.length()!=0) {System.out.println("ip entered");}
        System.out.println(blue+name+"> "+red+ip+reset);

        try{
        //Establish the connection with the server with IP and Port from user input
        System.out.println(blue+"Waiting to connect to server..."+reset);
        sock = new Socket(ip, port);
        System.out.println(red+"Connected !"+reset);
        System.out.println("__________________________");

        // In and out streams => Information received (inputStream) and sent (outputStream) :
        in = new DataInputStream(sock.getInputStream()); 
        out = new DataOutputStream(sock.getOutputStream()); 

        out.writeUTF(name);//Sends client name to server

        }
        catch(IOException e){
            //e.printStackTrace(); => To show errors (I don't want users to see thoses...)
            System.out.println(red+"Can't reach server...Check Ip/port and connectivity"+reset);
        }
        catch(InputMismatchException a) {
        	System.out.println(red+"FATAL ERROR :"+reset+purple+" IP and Port must be Integer"+reset);
        }

    }
    
    /**
     * Method that takes a string and tries to send it to the server.
     * 
     * @param str {String} - A String to send to the server 
     */
    private void sendToServer(String str){
        try{
            out.writeUTF(str);
        }
        catch(IOException e){
            //TODO
        }
    }

    /**
     * Method that waits for string from the server and returns it when received. 
     * 
     * @return a string received from the server
     */
    private String getFormServer(){
        try{
            return in.readUTF();
        }
        catch(IOException e){
            //TODO
        }
        return "";
    }


    /**
     * //TODO
     */
    private void listenToServer(){
        String strFromServer ="";
        String strToServer ="";

        while (true)  { 
            strFromServer = getFormServer();
            //System.out.print(strFromServer); 
            switch(strFromServer){
                case "displayGrid":
                    System.out.print("disp"); 
                    gridDisplay.displayGrid();
                    break;
                    
                case "Q?": //Server ask a question
                    System.out.print(getFormServer()); //print the question 
                    strToServer = scn.nextLine();
                    sendToServer(strToServer);
                    break;

                case "insertUnit":
                    strFromServer = getFormServer();
                    gridDisplay.insertInGrid("Unit", strFromServer, false);
                    break;
                
                case "Hit": 
                    strFromServer = getFormServer();
                    gridDisplay.insertInGrid("Hit", strFromServer, true);
                    break;

                case "noHit":
                    strFromServer = getFormServer();
                    gridDisplay.insertInGrid("noHit", strFromServer, true);
                    break;
                
                case "Destroyed":
                    strFromServer = getFormServer();
                    gridDisplay.insertInGrid("Destroyed", strFromServer, true);
                    break;
                
                case "myDestroyed":
                    strFromServer = getFormServer();
                    gridDisplay.insertInGrid("Destroyed", strFromServer, false);
                    break;
                
                case "myHit": 
                    strFromServer = getFormServer();
                    gridDisplay.insertInGrid("Hit", strFromServer, false);
                    break;

                case "myNoHit":
                    strFromServer = getFormServer();
                    gridDisplay.insertInGrid("noHit", strFromServer, false);
                    break;

                case "Rem": //remove lines
                    strFromServer = getFormServer();
                    gridDisplay.removeLines(Integer.parseInt(strFromServer));
                    break;

                default:
                    System.out.print(strFromServer);
            }
        }

    }


    public static void main(String[] args){ 
        Client client = new Client();
        client.InitConnection();
        client.listenToServer();  
    } 

}

/*
System.out.print(getFromServer);
sendToServer = scn.nextLine();
out.writeUTF(sendToServer);
if(sendToServer.equals("end")) { 
    System.out.println(blue+name+">"+reset+" Closing this connection...."); 
    sock.close(); 
    System.out.println(red+"Connection closed"+reset); 
    scn.close(); 
    in.close(); 
    out.close(); 
    System.exit(0);
    break; 
}
*/