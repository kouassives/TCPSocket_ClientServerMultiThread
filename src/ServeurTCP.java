/*
 * Author: KOUASSI Yves Anselme Magloire
 * mail: kouassives@gmail.com
 * Git account: https://github.com/kouassives
 * 
 */
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe correspondant à un serveur TCP.
 * Le client envoie la chaine 'Bonjour' et lit une reponse de la part du serveur.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une reponse.
 * Le numero de port du serveur est specifie dans la classe ServeurTCP.
 * 
 */
public class ServeurTCP {

    public static final int portEcoute = 5001;

    public static void main(String[] args) {
	// Creation de la socket serveur
	ServerSocket socketServeur = null;
	try {	
	    socketServeur = new ServerSocket(portEcoute);
	} catch(IOException e) {
	    System.err.println("Creation de la socket impossible : " + e);
	    System.exit(-1);
	}

	// Attente d'une connexion d'un client
	int i = 1;
	while(true)
		{
		Socket socketClient = null;
			try {
				socketClient = socketServeur.accept();
			    ThreadTcp thread = new ThreadTcp("thread"+i);
			    thread.setSocketClient(socketClient);
			    thread.setSocketServer(socketServeur);
			    thread.start();
			    i++;
			} catch(IOException e) {
			    System.err.println("Erreur lors de l'attente d'une connexion : " + e);
			    System.exit(-1);
			}
		}
	
	
    }

}