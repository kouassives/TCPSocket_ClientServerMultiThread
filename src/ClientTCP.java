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

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Classe correspondant à un client TCP.
 * Le client envoie la chaine 'Bonjour' et lit une reponse de la part du serveur.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une reponse.
 * Le numero de port du serveur est specifie dans la classe ServeurTCP.
 * 
 */
public class ClientTCP {

    public static void main(String[] args) {
	// Creation de la socket
	Socket socket = null;
	try {
	    socket = new Socket("localhost", ServeurTCP.portEcoute);
	    System.out.println("Vous êtes connecté au serveur");
	} catch(UnknownHostException e) {
	    System.err.println("Erreur sur l'hôte : " + e);
	    System.exit(-1);
	} catch(IOException e) {
	    System.err.println("Creation de la socket impossible : " + e);
	    System.exit(-1);
	}
	
	// Association d'un flux d'entree et de sortie
	BufferedReader input = null;
	PrintWriter output = null;
	try {
	    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	} catch(IOException e) {
	    System.err.println("Association des flux impossible : " + e);
	    System.exit(-1);
	}
	
	try {
		boolean continuer = true;
			
	while(continuer) {
		// Envoi
		Scanner sc = new Scanner(System.in);
		String message= sc.nextLine();
		output.println(message);

		// Lecture
				String messageDuServer = input.readLine();
				System.out.println("Recu du Serveur: " + messageDuServer);
				
		if(message.equals("0x23")) {
			continuer =false;
		}
		else System.out.println("Saisissez votre prochain message ou 0x23 pour quitter");
		
		}
	}
	catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();

	   
	}

	// Fermeture des flux et de la socket
		try {
		    input.close();
		    output.close();
		    socket.close();
		    System.out.println("Vous êtes déconnecté");
		} catch(IOException e) {
		    System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
		    System.exit(-1);
		}

    }

}