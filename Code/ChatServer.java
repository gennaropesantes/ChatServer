package Chat_Server_4;



import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * 
 *Version 4:
 *Group Chat
 *This time it sends all text received from any of the connected clients to all clients. 
 *This means that the server has to receive and send, and the client has to send as well as receive
 *
 */
public class ChatServer implements Runnable{

	private ChatServerThread clients[] = new ChatServerThread[50];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;
	static boolean worked;

	public ChatServer(int port){
		try{
			System.out.println("Binding to port " + port + " please wait...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			start();
		}
		catch(IOException ioe){
			System.out.println("Cannot bind to port " + port + ": " +ioe.getMessage());
		}
	}


	public void start(){
		if(thread==null){
			thread = new Thread(this);
			thread.start();
		}
	}	
	
	public void stop(){
		if(thread !=null){
			thread=null;
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while(thread != null){
			try{
				System.out.println("Waiting for a client...");
				addThread(server.accept());
			}
			catch(IOException ioe){
				System.out.println("error accepting the client "+ioe.getMessage());
			}
		}
	}

	public synchronized void handle(int ID, String input){
		
		String privateMsg = "private message";
		
		if(input.startsWith(privateMsg)) {
			
			Scanner keyboard = new Scanner(System.in);
			String selection;
			
			System.out.print("Would you like to encrypt this message? ");
			selection = keyboard.next();
			if(selection.equalsIgnoreCase("yes")){
				boolean worked = true;
				OneTimePad oneTime = new OneTimePad(privateMsg);
				String encryptedMessage = oneTime.getEncryptedMessage();
				int ID_SendTo = Integer.parseInt(input.substring(privateMsg.length(), privateMsg.length() + 5));
				if(findClient(ID_SendTo)!=-1){
				clients[findClient(ID_SendTo)].send(privateMsg + " From " + ID + ":" + encryptedMessage);
				}
			}
			else{
			int ID_SendTo = Integer.parseInt(input.substring(privateMsg.length(), privateMsg.length() + 5));
			String message = input.substring(privateMsg.length() + 6);
			if(findClient(ID_SendTo)!=-1){
				clients[findClient(ID_SendTo)].send(privateMsg + " From " + ID + ": "+ message);
				}
			}
		}
		else{
			System.out.println("Message from " + ID+ ": " + input);
			for(int i=0; i<clientCount; i++){
				clients[i].send("User " + ID + ": " + input);
			}
		}
	}

	public synchronized void remove(int ID){
		int pos = findClient(ID);
		if(pos >= 0){
			ChatServerThread toTerminate = clients[pos];
			System.out.println("Removing client thread " + ID + " at " + pos);
			if ( pos < clientCount-1){
				for (int i = pos+1; i < clientCount; i++){
					clients[i-1] = clients[i];
				}
				clientCount--;
			}
			try{
				toTerminate.close();
			}
			catch(IOException ioe){
				System.out.println("Error closing thread: " + ioe);
			}
		}
	}


	private int findClient(int ID){
		for(int i=0; i<clientCount; i++){
			if(clients[i].getID() == ID){
				return i;
			}
		}
		return -1; //if ID not found in array	
	}

	private synchronized void addThread(Socket socket){
		if(clientCount < clients.length){
			System.out.println("Client "+ clientCount + " accepted on : " + socket);
			clients[clientCount] = new ChatServerThread(this, socket);
			try{
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			}
			catch(IOException ioe){
				System.out.println("Error opening thread: " + ioe);
			}			
		}
		else{
			System.out.println("Client was refused: maximum " + clients.length + " reached.");
		}
	}

	public static void main(String[] args){
		ChatServer server = null;
		if(args.length !=1){
			System.out.println("To run the server you need to specify a port");
		}
		else{
			server = new ChatServer(Integer.parseInt(args[0]));

		}
	}
}
