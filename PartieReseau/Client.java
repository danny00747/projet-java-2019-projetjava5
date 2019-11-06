package chat;

import java.io.*; 
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner; 
  
/*
 * @Author Morgan Valentin
 * @Source code used to understand => https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
*/

public class Client  { 
	//Colours on ouput => ONLY WORKS ON LINUX (maybe mac ?) NOT WINDOWS
	protected static final String red    = "\u001B[31m";
	protected static final String blue   = "\u001B[34m";
	protected static final String reset  = "\u001B[0m";
	protected static final String purple = "\u001B[35m";
	private static String name;
	
<<<<<<< HEAD
	private static void checkNameClient(String name,Scanner scanner) {//Checks if name (input from user) is between 4 and 12 chracters in length
=======
	private static void checkNameClient(String name,Scanner scanner) {
>>>>>>> 6e17e1bf683c5c1903e19bc0782f467adc430dd8
		if (name.length()<4 || name.length() >13) {
			while (name.length()<4 || name.length() >13) {
				System.out.println("Invalid input. Please re-enter a name (min 4 - max 12 :");
				name = scanner.nextLine();
			}
		}
	}
	
    public static void main(String[] args) throws IOException  { 
        try { 
            Scanner scn = new Scanner(System.in); 
            // Retrieving client's name from user input :
            System.out.println("What's your name ?");
            name = scn.nextLine();
            checkNameClient(name,scn);
            
            // Retrieving port from user input :
            System.out.println(purple+"Port number ?"+reset);
    		int port = scn.nextInt();
    		System.out.println(blue+name+"> "+purple+port);
    		
    		//Retrieving ip adress from user input :
            System.out.println(red+"IP address ? (nothing + enter = localhost)"+reset);
    		String ip = scn.nextLine();
    		ip = scn.nextLine();
    		if (ip.length()==0) {ip = "localhost";}
    		else if (ip.length()!=0) {System.out.println("ip entered");}
    		System.out.println(blue+name+"> "+red+ip+reset);
    		
      
            //Establish the connection with the server with IP and Port from user input
            System.out.println(blue+"Waiting to connect to server..."+reset);
    		Socket sock = new Socket(ip, port);
    		System.out.println(red+"Connected !"+reset);
    		System.out.println("__________________________");
      
            // In and out streams => Information received (inputStream) and sent (outputStream) :
            DataInputStream in = new DataInputStream(sock.getInputStream()); 
            DataOutputStream out = new DataOutputStream(sock.getOutputStream()); 
            
            out.writeUTF(name);//Sends client name to server
            
            //Infinite loop => "Until user input (you) types "end", read whatever
            while (true)  { 

                System.out.println(in.readUTF());//Reads the server's output (stream) 
                String sendToServer = scn.nextLine();//Holds user input in a variale "tosend"
                out.writeUTF(sendToServer);//Sends to server characters contained in String "tosend"
                System.out.println(blue+name+"> "+red+sendToServer+reset);
                 
                // If the client (you) sends "end" => Close the connection
                if(sendToServer.equals("end")) { 
                    System.out.println(blue+name+">"+reset+" Closing this connection...."); 
                    sock.close(); 
                    System.out.println(red+"Connection closed"+reset); 
                    break; 
                }
                if(sendToServer.equals("name")) { 
                	System.out.println("Enter new name (min 4 - max 12 :");
                    name = scn.nextLine();
                    checkNameClient(name,scn);
                    out.writeUTF(name);
                }
                  
                // Displays the answer from the server (Date-Time-Number of connected clients,...etc)
                String receivedFromServer = in.readUTF(); 
                System.out.println(receivedFromServer); 
            } 
              
            // closing resources 
            scn.close(); 
            in.close(); 
            out.close();
            System.exit(0);
            
        }
        catch(IOException e){ 
            //e.printStackTrace(); => To show errors (I don't want users to see thoses...)
            System.out.println(red+"Can't reach server...Check Ip/port and connectivity"+reset);
        }
        catch(InputMismatchException a) {
        	System.out.println(red+"FATAL ERROR :"+reset+purple+" IP and Port must be Integer"+reset);
        }
    } 
} 
