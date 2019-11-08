package chat;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/*
 * @Author Morgan Valentin
 * @Source code used to understand => https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
*/

public class Server2  { 
	
	protected static int num=0;//Used for user to add 5 (methode add5) to this num variable. (Testing purposes)
	protected static HashMap<Long,String> idAndNames = new HashMap<>();
	
	
    public static void main(String[] args) throws IOException, InputMismatchException  { 
    	//Selecting port number from user to use => Plus tard par interface graphique
    	//Attention => Le port du client devra etre identique sinon il ne "verra" pas le server !!
    	try {
    		Scanner userInput = new Scanner(System.in);//Get port number from user input
        	System.out.println("Port number ?");
    		int port = userInput.nextInt();
            ServerSocket servSock = new ServerSocket(port);//Creating a new serverSocket with port given by user. 
            
            System.out.println("Server started !");
            System.out.println("Waiting for player(s) to connect");
              
            // Infinite loop => Waiting that a client connects {accept() method}
            
            while (true)  { 
                Socket sock = null; //Socket for client communication => "Active" socket
                  
                try { 
                    // socket object to receive incoming client requests => "Passive" socket
                    sock = servSock.accept();
                      
                    // In and out streams => Information received (inputStream) and sent (outputStream) :
                    DataInputStream in = new DataInputStream(sock.getInputStream()); 
                    DataOutputStream out = new DataOutputStream(sock.getOutputStream()); 
                    
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
        	System.out.println("FATAL ERROR : IP and Port must be Integer");
        }	 
    } 
}
//To "create" a new thread (process ?) either implement interface Runnable OR inherite from the "Thread" class
class ClientHandler extends Thread  
{ 
    DateFormat myDateFormat = new SimpleDateFormat("EEEE,dd-MMMM-YYYY");//Used for user to ask for Date
    DateFormat myTimeFormat = new SimpleDateFormat("HH:mm:ss");//Used for user to ask for Time
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
        Server2.idAndNames.put(id, name);//Add id and thread name to hashMap
        String userName = Server2.idAndNames.get(id);//userName = valeur de la cle id (nom du process ex: thread-0)
        
        //---------------------
        //Choppe le nom du client (que le client envoie au serveur)
        try {       
        	userName = in.readUTF();//Stocke le nom du client dans le hashMap a la cle (id du Thread) (valeur=>userName)
        	
        } catch (IOException e) { 
            System.out.println("Erreur");
        }
        System.out.println("A new client"+" \""+userName+"\""+" with id" +" ("+id+")"+" joined via " + sock.getLocalAddress().toString().replaceAll("/", ""));//To see which ip the client used to connect 
        System.out.println("-----------------------------------------");
        //--------------------
        
        while (true)  { 
            try { 
  
                // Show the different possibilities (options) available
                out.writeUTF("Welcome "+"\""+userName+"\""+" your id is : "+id+"\n"+
                		"What do you want? Options =>[Date | Time | add5 | name | con(showNumOfClientConnected)]\n"+ 
                            "Type 'end' to terminate connection.\n"
                		+"---------------------------"); 
                  
                // receive the answer from client 
                receivedFromClient = in.readUTF(); 
                  
                if(receivedFromClient.equals("end")) {
                	System.out.println(userName+"> "+receivedFromClient);
                    System.out.println("Client "+"\""+userName+"\""+" with id n°"+id+" (ip= "+this.sock.getInetAddress().toString().replaceAll("/", "") +") sends end command..."); 
                    System.out.println("Bye bye "+"\""+userName+"\""); 
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
                    	Date date = new Date();
                    	sendToClient = myDateFormat.format(date); 
                        out.writeUTF("Server>"+" "+sendToClient+"\n"+"-------------------");
                        System.out.println(userName+"> "+receivedFromClient);//Visualisation des donnees demande par le client
                        break; 
                          
                    case "Time" : 
                    	//Shows the Time
                    	Date date2 = new Date();//Why here ? => Because Time changes ...
                    	sendToClient = myTimeFormat.format(date2); 
                        out.writeUTF("Server> "+sendToClient+"\n"+"-------------------"); 
                        System.out.println(userName+"> "+receivedFromClient);//Visualisation des donnees demande par le client
                        break;
                        
                    case "add5" : 
                    	
                    	//Adds 5 to the "num" variable and displays its value
                        Server2.num += 5;
                        out.writeUTF("Server> Num = "+Server2.num+"\n"+"-------------------");
                        System.out.println(userName+"> "+receivedFromClient);//Visualisation des donnees demande par le client
                        System.out.println("Value of num : "+Server2.num);
                        break; 
                        
                    case "con" : 
                    	//Show how many clients are connected to the server
                        out.writeUTF(userName+"> Number of connections : "+numberOfClientsConnected+"\n"+"-------------------"); 
                        System.out.println(userName+"> "+receivedFromClient);//Visualisation des donnees demande par le client
                        System.out.println("Num of connec : "+numberOfClientsConnected);
                        break;
                        
                    case "name" : //What's my name ? => Answers that...
                    	System.out.println(userName+"> "+receivedFromClient);
                    	receivedFromClient = in.readUTF();//Receives new name from client
                        userName = receivedFromClient;
                        Server2.idAndNames.replace(id, userName);
                        out.writeUTF("Server> Name changed to "+"\""+userName+"\""+"\n"+"-------------------"); 
                        System.out.println("Name changed to => "+userName);
                        break;
                    	
                    default: 
                        out.writeUTF("Invalid input");
                        break; 
                } 
            } catch (IOException e) { 
                //e.printStackTrace();
            	System.out.println("Et on ne dit même pas au revoir ? Je vois...");
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
