package chat;

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
// Client class 
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
        try
        { 
            Scanner scn = new Scanner(System.in); 
              
            // Retrieving IP address and port from user input:
            
            System.out.println("IP address ?");
    		String ip = scn.nextLine();
    		System.out.println("Port number ?");
    		int port = scn.nextInt();
      
            // establish the connection with the server with IP and Port from user input
            Socket sock = new Socket(ip, port); 
      
            // In and out streams => Information received (inputStream) and sent (outputStream) :
            DataInputStream in = new DataInputStream(sock.getInputStream()); 
            DataOutputStream out = new DataOutputStream(sock.getOutputStream()); 
      
            //Little test to show (on server side) the number of connections 
            System.out.println(in.readUTF()); 
            String test = "con"; 
            out.writeUTF(test);
            //Infinite loop => "Until user input (you) types "end", read whatever
            while (true)  
            { 
                System.out.println(in.readUTF());//Reads the server's output (stream) 
                String tosend = scn.nextLine();//Holds user input in a variale "tosend" 
                out.writeUTF(tosend);//Sends to server characters contained in String "tosend" 
                  
                // If the client (you) sends "end" => Close the connection
                if(tosend.equals("end")) 
                { 
                    System.out.println("Closing this connection : " + sock); 
                    sock.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // Displays the answer from the server (Date-Time-Number of connected clients,...etc)
                String received = in.readUTF(); 
                System.out.println(received); 
            } 
              
            // closing resources 
            scn.close(); 
            in.close(); 
            out.close();
            System.exit(0);
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
} 
