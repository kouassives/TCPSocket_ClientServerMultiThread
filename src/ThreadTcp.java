/*
 * Author: KOUASSI Yves Anselme Magloire
 * mail: kouassives@gmail.com
 * Git account: https://github.com/kouassives
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadTcp extends Thread{
	public ThreadTcp(String nom) {
		super(nom);
	}
	Socket socketClient = null;
	ServerSocket socketServer = null;
	
	public void setSocketClient(Socket socketClient) {
		this.socketClient=socketClient;
	}
	public void setSocketServer(ServerSocket socketServer) {
		this.socketServer = socketServer;
	}
	public void run() {
		try{
				//System.out.println(this.getName());
			
			// Association d'un flux d'entree et de sortie
			BufferedReader input = null;
			PrintWriter output = null;
			try {
			    input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
			} catch(IOException e) {
			    System.err.println("Association des flux impossible : " + e);
			    System.exit(-1);
			}
			
	
			// Lecture
			String message = "";
			boolean continuer = true;
			
			while(continuer) {
				try{
					if(!(message = input.readLine()).equals("0x23")) {
						System.out.println("Recu du client : " + message);
		
						// Envoi
						String msgAEnvoyer= message.toUpperCase()+socketClient.getInetAddress()+" "+socketClient.getPort();
						System.out.println("Envoi : "+ msgAEnvoyer);
						output.println(msgAEnvoyer);	
						output.flush();
						
						
					}else {
						System.out.println("Deconnexion client: "+socketClient.getInetAddress()+" "+socketClient.getPort());
						output.println("Bye "+socketClient.getInetAddress()+" "+socketClient.getPort());	
						output.flush();
						continuer=false;
						}
						
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
		}
		catch(Exception e) {
					try {
						e.printStackTrace();
					    socketServer.close();
					} catch(IOException eIO) {
					    System.err.println("Erreur lors de la fermeture des flux et des sockets : " + eIO);
					    System.exit(-1);
					}
				}
			
			
			
			
	
	}
}
