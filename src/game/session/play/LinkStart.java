package game.session.play;
//serie di import
import java.util.Scanner;
import game.session.configuration.GameSession;
import game.session.score.ScoreMemo;
import game.structure.text.GameMessages;
/** class LinkStart
 * classe che si occupa di avviare l'applicazione, 
 * chiedendo all'utente cosa voglia fare.
 * @author Ivonne
 */
public class LinkStart {
	//acquisizione input
	private static Scanner input = new Scanner(System.in);
	//acquisizione comando
	private static char command = ' ';
	//main
	public static void main(String [] args) {
		//avvio sessione di gioco
		do {
			System.out.println(GameMessages.command_legend);
			System.out.println(GameMessages.command_request);
			//acquisizione del comando
			command = input.next().charAt(0);
			//verifica del comando ricevuto
			if(command == 'c') {
				System.out.println(GameMessages.credits);
			}//fi credits
			else if(command == 's') {
				System.out.println("Riuscirai a battere questi punteggi?");
				ScoreMemo.readScoreFile();
			}//fi score
			else if(command == 'g') {
				//sessione di gioco iniziata
				GameSession.start();
				//gioco
				GameSession.play();
				//sessione di gioco terminata
				GameSession.end();
			}//fi game
			else if(command == 'q') {
				System.out.println("Chiusura del gioco...");
			}//fi quit
			//comando errato
			else {
				System.out.println("Comando errato!\n");
			}//esle	
		}while(command != 'q') ;//end while avvio dell'applicazione
		//si cancella il file dei punteggi
		ScoreMemo.deleteScoreFile();
		System.out.println("Ciao, alla prossima!");
	}//end main
}//end LinkStart
