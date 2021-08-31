package game.session.start;
//serie di import
import java.util.Scanner;
import game.player.Player;
import game.session.score.ScoreUtility;
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
	//nome del giocatore
	private static String player_name;
	//flag per la modalita' di gioco
	private static boolean human;
	
	//main
	public static void main(String [] args) {
		//avvio sessione di gioco
		do {
			System.out.println("\nLoading the G4M3.......");
			//stampe all'utente
			System.out.println(GameMessages.command_legend);
			System.out.println(GameMessages.command_request);
			//acquisizione del comando
			command = input.next().charAt(0);
			//verifica del comando ricevuto
			if(command == 'c') {
				System.out.println(GameMessages.credits);
			}//fi credits
			else if(command == 's') {
				System.out.println("Riuscirai a battere questi punteggi?\n");
				//lettura del file dei punteggi
				ScoreUtility.readScoreFile();
			}//fi score
			else if(command == 'g') {
				//variabile ausiliaria per il tipo di giocatore
				human = Player.chooseType();
				//verifica della scelta
				if(human) {
					Player.humanMode();
				}//fi
				else {
					Player.automaticMode();
				}//else
				//aggiornamento del file dei punteggi
				ScoreUtility.updateScoreFile();
			}//fi game
			else if(command == 'q') {
				System.out.println("Chiusura del gioco...");
			}//fi quit
			else if(command == 'n') {
				System.out.println("Inserisci il tuo nome:> ");
				//acquisizione del nome da input
				player_name = input.next(); 
			}//fi setting name
			//comando errato
			else {
				System.out.println("Comando errato!\n");
			}//esle	
		}while(command != 'q') ;//end while avvio dell'applicazione
		System.out.println("Ciao, alla prossima!");
	}//end main
	
	//##### metodi accessori per il nome del giocatore #####
	
	/**
	 * 
	 * @return
	 */
	public static String getPlayerName() {
		return player_name;
	}//getPlayerName()
	
	public static boolean isHumanMode() {
		return human;
	}
}//end LinkStart
