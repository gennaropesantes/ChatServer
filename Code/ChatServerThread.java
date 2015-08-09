package Chat_Server_4;
/**
 * 
 *Version 4:
 *Thread for Group Chat Server
 *This time it sends all text received from any of the connected clients to all clients. 
 *This means that the server has to receive and send, and the client has to send as well as receive
 *
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatServerThread extends Thread{

	private static ChatServer server = null; 
	private Socket socket = null;
	private static int ID = -1;
	private DataInputStream streamIn = null;
	private static DataOutputStream streamOut = null;
	private boolean done = true;


	public ChatServerThread(ChatServer _server, Socket _socket){
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		System.out.println("Chat Server Thread Info: server"+ server + " socket "+ socket + " ID "+ ID);
	}

	public static void send(String msg){
		try{
			streamOut.writeUTF(msg);
			streamOut.flush();
		}
		catch(IOException ioe){
			System.out.println(ID + " ERROR sending: " + ioe.getMessage());
			server.remove(ID);
			ID=-1;//set ID -1 for the thread...
		}
	}

	public int getID(){
		return ID;
	}

	public void run(){
		System.out.println("Server Thread " + ID + " running.");
		while(ID!=-1){
			try{
				server.handle(ID, streamIn.readUTF()); 
			}
			catch(IOException ioe){
				System.out.println(ID + "ERROR reading: " + ioe.getMessage());
				server.remove(ID);
				ID=-1;//set ID to -1 so it will not enter the loop again instead of deprecated stop()
			}
		}
	}
	public void open() throws IOException{
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}

	public void close() throws IOException{
		if(socket != null){
			socket.close();
		}
		if(streamIn != null){
			streamIn.close();
		}
		if(streamOut != null){
			streamOut.close();
		}
	}
}
