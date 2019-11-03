package chat;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/*
 * @Author Morgan Valentin
 * @Source code used to understand => https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 * */

public class Server  
{ 	
	/*Avec cette variable num (qui n'a l'air de rien) je test la modification par plusieurs thread
	* Exemple : Client1 add5 (methode voir plus bas...) qui ajoute 5 a num et affiche ce nombre
	* Avant le Client1 add5 3 fois qui donne 15 et Client2 qui fait add5 => affiche 5
	* 	=> Variable propre a chaque thread.
	* Maintenant, Si ClientA fait 3 fois add5 et ClientB fait add5 il verra 20 et non 5.
	* (Bref, c'est pour que je comprennes mieux)
	*/
	protected static int num=0;//Used for user to add 5 (methode add5) to this num variable. (Testing purposes)
	
	//Colours on ouput => ONLY WORKS ON LINUX (maybe mac ?) NOT WINDOWS
	protected static final String red    = "\u001B[31m";
	protected static final String blue   = "\u001B[34m";
	protected static final String reset  = "\u001B[0m";
	protected static final String purple = "\u001B[35m";
	protected static final String yellow = "\u001B[33m";
	protected static final String white  = "\u001B[37m";
	
    public static void main(String[] args) throws IOException, InputMismatchException  { 
    	//Selecting port number from user to use => Plus tard par interface graphique
    	//Attention => Le port du client devra etre identique sinon il ne "verra" pas le server !!
    	try {
    		Scanner userInput = new Scanner(System.in);//Get port number from user input
        	System.out.println(purple+"Port number ?"+reset);
    		int port = userInput.nextInt();
            ServerSocket servSock = new ServerSocket(port);//Creating a new serverSocket with port given by user. 
            
            System.out.println(red+"Server started !"+reset);
            System.out.println("Waiting for player(s) to connect");
              
            // Infinite loop => Waiting that a client connects {accept() method}
            
            while (true)  { 
                Socket sock = null; //Socket for client communication => "Active" socket
                  
                try { 
                    // socket object to receive incoming client requests => "Passive" socket
                	
                    sock = servSock.accept();
                    
                      
                    System.out.println("A new client is connected via " +yellow+ sock.getLocalAddress()+reset);//To see which ip the client used to connect 
                      
                    // In and out streams => Information received (inputStream) and sent (outputStream) :
                    
                    DataInputStream in = new DataInputStream(sock.getInputStream()); 
                    DataOutputStream out = new DataOutputStream(sock.getOutputStream()); 
                      
                    System.out.println("Assigning new thread for this client"); 
                    System.out.println("-----------------------------------------");	
                    // create a new thread object => Nouveau "processus" en quelque sorte
                    Thread t = new ClientHandler(sock, in, out); 
      
                    // Demarre le processus t (gestion des clients) et mais en "pause" la suite des instructions du server.
                    t.start(); //Start method search the run() method from the Threat class heritence 
                      
                } 
                catch (IOException e){ 
                	servSock.close();
                	userInput.close();
                	sock.close();
                    e.printStackTrace();
                }
            }
    	}
    	catch(InputMismatchException a) {
        	System.out.println(Server.red+"FATAL ERROR :"+Server.reset+Server.purple+" IP and Port must be Integer"+Server.reset);
        }
    	catch(EOFException e) {
    		System.out.println("Et on ne dit même pas au revoir ? Je vois...");
    	}
    	 
    } 
}
//To "create" a new thread (process ?) either implement interface runnable OR inherite from the "Thread" class
class ClientHandler extends Thread  
{ 
    DateFormat myDateFormat = new SimpleDateFormat("EEEE,dd-MMMM-YYYY");//Used for user to ask for Date
    DateFormat myTimeFormat = new SimpleDateFormat("hh:mm:ss");//Used for user to ask for Time
    final DataInputStream in; 
    final DataOutputStream out; 
    final Socket sock; 
    private static int numberOfClientsConnected=0;
      
  
    // Constructor 
    public ClientHandler(Socket sock, DataInputStream in, DataOutputStream out)  
    { 
        this.sock = sock;
        numberOfClientsConnected++;
        this.in = in; 
        this.out = out;
        testConnection();//Tests if there's 0 client OR More than 2 connected => If so Closes ressources and Exit program.
        
        /* Tester avec 3 clients :
         * Des que le 3eme client se connecte, il recoit toute une serie d erreur (a catcher et masquer de l utilisateur)
         * Cependant, le server se couppe correctement.
         * Les 2 autres clients (client 1 et 2) ne voit pas de message disant que la connection est couper ==>>Optimiser
         * Mais une fois une commande entrer => Erreur disant que le serveur n'est plus joignable.
         */
    } 
  
    @Override
    public void run()  { 
        String receivedFromClient; 
        String sendToClient;
        final String name = Thread.currentThread().getName();
        final long id = Thread.currentThread().getId(); 
        Date date = new Date();//Creating Date object (used in Time and Date)
        while (true)  { 
            try { 
  
                // Show the different possibilities (options) available
                out.writeUTF("Welcome "+Server.blue+name+Server.reset+" your id is : "+Server.blue+id+Server.reset+"\n"+
                		"What do you want? Options =>"+Server.purple+"[Date | Time | add5 | con(showNumOfClientConnected)]"+Server.reset+"\n"+ 
                            "Type "+Server.red+"'end'"+Server.reset+" to terminate connection.\n"
                		+"---------------------------"); 
                  
                // receive the answer from client 
                receivedFromClient = in.readUTF(); 
                  
                if(receivedFromClient.equals("end")) {  
                    System.out.println("Client " +Server.yellow+ this.sock.getInetAddress() +Server.reset+ " sends end command..."); 
                    System.out.println("Closing THIS connection."); 
                    this.sock.close();
                    numberOfClientsConnected--;
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                  
                // write on output stream based on the 
                // answer from the client 
                switch (receivedFromClient) { 
                //The "case" correspond to what the client sent (if none of the command => default : "invalid output"
                  
                    case "Date" : 
                    	//Shows the date
                    	sendToClient = myDateFormat.format(date); 
                        out.writeUTF(Server.yellow+"Server>"+Server.white+" "+sendToClient+Server.reset+"\n"+"-------------------");
                        System.out.println(sendToClient);//Visualisation des donnees envoyes au client cote serveur
                        break; 
                          
                    case "Time" : 
                    	//Shows the Time
                    	sendToClient = myTimeFormat.format(date); 
                        out.writeUTF(Server.yellow+"Server>"+Server.white+" "+sendToClient+Server.reset+"\n"+"-------------------"); 
                        System.out.println(sendToClient);
                        break;
                        
                    case "add5" : 
                    	
                    	//Adds 5 to the "num" variable and displays its value
                        Server.num += 5;
                        out.writeUTF(Server.yellow+"Server>"+Server.white+" Num = "+Server.num+Server.reset+"\n"+"-------------------"); 
                        System.out.println("Value of num : "+Server.num);
                        break; 
                        
                    case "con" : 
                    	//Show how many clients are connected to the server
                        out.writeUTF(Server.yellow+"Server>"+Server.white+" Number of connections : "+numberOfClientsConnected+Server.reset+"\n"+"-------------------"); 
                        System.out.println("Num of connec : "+numberOfClientsConnected);
                        break;
                        
                    case "" :
                    	out.writeUTF("");
                    	break;
                    	
                          
                    default: 
                        out.writeUTF("Invalid input");
                        break; 
                } 
            } catch (IOException e) { 
                //e.printStackTrace();
            	System.out.println(Server.red+"Et on ne dit même pas au revoir ?"+Server.reset+Server.blue+" Je vois..."+Server.reset);
            	System.exit(0);
            }
        } 
          
        testConnection();//Tests if there's 0 client OR More than 2 connected => If so Closes ressources and Exit program.
    }
    public void testConnection() {
    	try { 
            // closing resources 
            if (numberOfClientsConnected==0) {
            	System.out.println("No more clients...ENDING Server!");
            	this.in.close(); 
                this.out.close(); 
                this.sock.close();
            	System.exit(0);//Exit program => Thread stop
            }
            if (numberOfClientsConnected>2) {
            	System.out.println("More than 2 clients... ENDING Server!");
            	this.in.close(); 
                this.out.close(); 
                this.sock.close();
            	System.exit(0);
            }
              
        }catch(IOException e){ 
            e.printStackTrace();
        }
    }
} 
  
