package game_player;
/** Enum Direction
 * Questa enumerazione definisce la direzione in cui il personaggio giocabile
 * puo' effettuare una mossa e quindi muoversi sulla mappa di gioco.
 * I movimenti consenti sono, quindi, quelli nelle quattro direzioni: 
 * sopra, sotto, destra e sinistra.
 */
enum Direction {
	UP,
	DOWN,
	LEFT,
	RIGHT;
}//Direction

/** GameController
 * @author ivonne
 * questa classe definisce i comandi di gioco, cioe' il modo in cui si potra'
 * interagire con il programma, facendo muovere il personaggio giocabile, nelle direzioni
 * consentite.
 */
public class GameController {
	//TODO messaggi a video
	//dinamica di gioco
	String denied_position = new String("Posizione non consentita!");
	//messaggi fine partita
	String looser = new String("Hai perso!");
	String winner = new String("Hai vinto !!!");
	//pericoli
	String trap = new String("Sei caduto in trappola...");
	String pit = new String("Sei caduto nella fossa piena d'acqua...");
	//sensori: nemico
	String hero_closed= new String("Swiiiish...si muovono le foglie...c'e' qualcuno che ti osserva...");
	String wumpus_closed= new String("Pufff...che cattivo odore...");
	//sensori: pericoli
	String pit_closed= new String();
	String trap_closed= new String();
	
	//viene creata la mappa di gioco
	
	//viene creata la mappa di esplorazione
	
	
	//posizione in cui si trova il personaggio all'inizio
	private int [] start_pg_position; 
	
	
	private int getPGIposition() {
		//si cerca dove si trova il personaggio giocabile nella mappa
		return 0;
	}
	
	private int getPGJposition() {
		//si cerca dove si trova il personaggio giocabile nella mappa
		return 0;
	}
	
	
	
	private boolean moveTo(Direction d) {
		//controllo sul parametro
		if(d==null)return false;
		//si acquisisce la posizione corrente del pg sulla mappa
		int i = getPGIposition();
		int j = getPGJposition();
		//si esegue uno switch case per stabilire la direzione ricevuta come parametro
		//e se sia valida in base alla posizione del pg sulla mappa 
		switch(d) {
			case UP:
				break;
			case DOWN: 
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			default:
				break;
		}//switch
		return false;
	}
	
	private boolean moveIn(int i, int j) {
		//indici di cella in cui fare lo spostamento
		return false;
	}
	
	//TODO aggiornare la mappa di esplorazione
	//il parametro isVisited della cella in cui ci si e' mossi deve essere true
}//GameController
