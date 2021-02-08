package game_player;

import game_structure.GameMap;

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
	
	//messaggio di errore mossa
	String denied_move = new String("Posizione non consentita!");
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
	String pit_closed= new String("Brrr...che aria fredda che arriva..dev'esserci una fossa piena d'acqua nelle vicinanze.");
	String trap_closed= new String("Crick, crock...Il terreno e' instabile.");
	
	//posizione corrente
	private static int ic;
	private static int jc;
	
	

	
	
	private boolean moveTo(Direction d) {
		//controllo sul parametro
		if(d==null)return false;
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
	
	/** metodo getCurrentIposition(): int
	 * metodo accessorio che restituisce l'indice di riga della cella in cui si trova
	 * attualmente il personaggio giocabile
	 * @return ic: int, l'indice di riga della cella che contiene il personaggio giocabile.
	 */
	public int getCurrentIposition() {
		//questo metodo restituisce l'indice di riga in cui si trova il pg
		return ic;
	}//getPGiPosition()
	
	/** metodo getCurrentJposition(): int
	 * metodo accessorio che restituisce l'indice di colonna della cella in cui si trova
	 * attualmente il personaggio giocabile
	 * @return jc: int, l'indice di colonna della cella che contiene il personaggio giocabile.
	 */
	public int getCurrentJposition() {
		//questo metodo restituisce l'indice di colonna in cui si trova il pg
		return jc;
	}//getPGiPosition()
	
	
	public String getCurrentPosition(boolean info) {
		//restituisce l'indice di riga e colonna in cui si trova il pg
		String p_info = new String("Ti trovi nella cella ");
		String p = new String("("+ic+","+jc+")");
		//si restuisce l'oggetto stringa
		if(info) {
			return ""+p_info+p;
		}//fi
		else {
			return ""+p;
		}//esle
	}//getCurrentPosition()
	
	//TODO cancellare?
	public static boolean isAllowedMove(int i, int j) {
		//si controlla se il movimento, indicato dalla coppia di indici che rappresenta
		//la cella di arrivo, e' lecito
		boolean r_ok=false;
		boolean c_ok=false;
		boolean dims_ok=false;
		//ovvero se non si sfori la dimensione della matrice
		int [] dim= GameMap.getMapDimensions();
		if(i>=0 && i< dim[0])r_ok=true;
		if(j>=0 && i< dim[1])c_ok=true;
		dims_ok = r_ok && c_ok;
		//controllare che la cella in cui effettuare la mossa sia adiacente alla posizione corrente
		//del pg
		//controllo sull'adiacenza
		if(i==ic && j==jc-1) {
			//cella a sinistra
			if(jc-1>=0 && jc-1<4) {
				//la cella adiacente esiste
				//mossa valida
				return dims_ok && true;
			}
			System.out.println("movimento non consentito in "+i+","+j);
			return false;
		}
		//cella a destra
		//cella in alto
		//cella in basso
		
		
		return false;
		
	}
}//GameController
