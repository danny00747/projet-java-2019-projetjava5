package chat;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/* A implementer :
 * -) Stocker le nombre de connections (joueurs)--OK
 * -) Limiter ce nombre a 2 max. --OK
 * -) Exit le program si le nombre est egale a 0 (0 clients) ou plus que 2 (Plus de 2 clients/joueurs) --OK
 * -) Documenter le code ... --Pas fini
 * Envoyer des infos entre clients --Reste a implementer
 */

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
	
    public static void main(String[] args) throws IOException  { 
    	//Selecting port number from user to use => Plus tard par interface graphique
    	//Attention => Le port du client devra etre identique sinon il ne "verra" pas le server !!
    	
    	Scanner scn = new Scanner(System.in);//Get port number from user input
    	System.out.println("Port number ?");
		int port = scn.nextInt();
        ServerSocket servSock = new ServerSocket(port);//Creating a new serverSocket with port given by user. 
        
        System.out.println("Server started !");
        System.out.println("Waiting for player(s) to connect");
          
        // Infinite loop => Waiting that a client connects {accept() method}
        
        while (true)  { 
            Socket sock = null; //Socket for client communication => "Active" socket
              
            try { 
                // socket object to receive incoming client requests => "Passive" socket
            	
                sock = servSock.accept();
                
                  
                System.out.println("A new client is connected via " + sock);//To see which ip the client used to connect 
                  
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
            catch (Exception e){ 
            	sock.close();
                e.printStackTrace(); 
            } 
        } 
    } 
}
//To "create" a new thread (process ?) either implement interface runnable OR inherite from the "Thread" class
class ClientHandler extends Thread  
{ 
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");//Used for user to ask for Date
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");//Used for user to ask for Time
    final DataInputStream in; 
    final DataOutputStream out; 
    final Socket sock; 
    //private int num=0;
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
         * Mais une fois une commande entrer => Erreur disant que le serveur n est plus joignable.
         */
    } 
  
    @Override
    public void run()  
    { 
        String received; 
        String toreturn;
        while (true)  
        { 
            try { 
  
                // Show the different possibilities (options) available
                out.writeUTF("What do you want?[Date | Time | add5 | con(Show number of clients connected]..\n"+ 
                            "Type end to terminate connection."); 
                  
                // receive the answer from client 
                received = in.readUTF(); 
                  
                if(received.equals("end")) 
                {  
                    System.out.println("Client " + this.sock + " sends end command..."); 
                    System.out.println("Closing THIS connection."); 
                    this.sock.close();
                    numberOfClientsConnected--;
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // creating Date object 
                Date date = new Date(); 
                  
                // write on output stream based on the 
                // answer from the client 
                switch (received) { 
                //The "case" correspond to what the client sent (if none of the command => default : "invalid output"
                  
                    case "Date" : 
                    	//Shows the date
                        toreturn = fordate.format(date); 
                        out.writeUTF("Server> "+toreturn);
                        System.out.println(toreturn);
                        break; 
                          
                    case "Time" : 
                    	//Shows the Time
                        toreturn = fortime.format(date); 
                        out.writeUTF("Server> "+toreturn); 
                        System.out.println(toreturn);
                        break;
                        
                    case "add5" : 
                    	
                    	//Adds 5 to the "num" variable and displays its value
                        Server.num += 5;
                        out.writeUTF("Server> Num = "+Server.num); 
                        System.out.println("Value of num : "+Server.num);
                        break; 
                        
                    case "con" : 
                    	//Show how many clients are connected to the server
                        out.writeUTF("Server> Number of connections : "+numberOfClientsConnected); 
                        System.out.println("Num of connec : "+numberOfClientsConnected);
                        break;
                          
                    default: 
                        out.writeUTF("Invalid input"); 
                        break; 
                } 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        testConnection();//Tests if there's 0 client OR More than 2 connected => If so Closes ressources and Exit program.
    }
    public void testConnection() {
    	try
        { 
            // closing resources 
            if (numberOfClientsConnected==0) {
            	System.out.println("No more clients...ENDING Server!");
            	this.in.close(); 
                this.out.close(); 
                this.sock.close();
            	 System.exit(0);//Close console (terminal) waiting error.
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
  
