package game.session.player;
//serie di import
import game.session.configuration.AutomaticGameSession;
import game.session.configuration.GameSession;
import game.structure.text.GameMessages;
import java.util.Scanner;
/** class Player
 * questa classe implementa i macro-metodi che riguardano il concetto
 * di giocatore, sia che questo sia controllato
 * dall'utente, dandogli dei comandi tramite console,
 * sia che si tratti del giocatore automatico.
 * Infatti richiama i metodi delle classi dedite alla configurazione della
 * sessione di gioco.
 * 
 * @author ivonne
 */
public class Player {
	//##### attribuiti di classe #####
	private static Scanner input = new Scanner(System.in);
	
	/** metodo humanMode()
	 * questo metodo richiama tutti i metodi della classe
	 * per fare in modo che l'utente possa giocare in prima persona
	 */
	public static void humanMode() {
		System.out.println(GameMessages.human_player);
		//sessione di gioco iniziata
		GameSession.start();
		//gioco in corso
		GameSession.play();
		//sessione di gioco terminata
		GameSession.end();
	}//humanPlayerSession()
	
	/** metodo chooseType()
	 * questo metodo permette all'utente di risolvere il gioco,
	 * la partita che si sta per avviare, in maniera automatica,
	 * utilizzando un agente.
	 * @return true, se si e' scelto di giocare in prima persona, false altrimenti,
	 * 				 quindi la partita verra' svolta dal giocatore automatico.
	 */
	public static boolean chooseType() {
		//variabile ausiliarie
		char player_type = ' '; 
		//acquisizione del tipo di giocatore
		System.out.println(GameMessages.player_type);
		//scelta 
		while(true) {
			//acquisizione da input
			player_type = input.next().charAt(0);
			//controllo della variabile
			if(player_type =='n') {
				//giocatore automatico
				return false;
			}//fi
			else if(player_type == 'y') {
				//giocatore umano
				return true;
			}//fi
			//scelta non valida
			else {
				System.out.println("Forza, andiamo!");
			}//esle
		}//end while	
	}//chooseType()
	
	/** metodo automaticMode(): void
	 * scelta della modalita' di gioco automatica
	 */
	public static void automaticMode() {
		System.out.println(GameMessages.automatic_player);
		//inizio della partita
		AutomaticGameSession.start();
		//partita in corso
		AutomaticGameSession.play();
		//fine della partita
		AutomaticGameSession.end();
	}//automaticMode()
	
}//end Player
