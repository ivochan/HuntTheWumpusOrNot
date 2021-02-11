package game.session;

import java.util.HashMap;

import game.structure.cell.CellStatus;

/** class GameModeTranslation
 * questa e' una classe di utilita', contenente soltanto metodi statici, 
 * che permettera' di avere a disposione le traduzioni degli elementi di
 * gioco, in base alla modalita' che verra' selezionata nella sezione di
 * gioco vera e propria.
 * 
 * PG	ENEMY	GOLD	DANGER	SAFE	FORBIDDEn
 * @author ivonne
 */
public class GameModeTranslation {

	/** HERO_SIDE
	 * Questa HashMap conterra' tutti gli elementi di gioco che costituiscono
	 * la modalita' eroe, cioe' quella in cui l'avventuriero deve sopravvivere
	 * al wumpus, durante la sua esplorazione del terreno di gioco.
	 * 
	 */
	public static final HashMap<CellStatus, String> hero_side_map = new HashMap<CellStatus, String>();
	
	/**
	 * Questa HashMap conterra' tutti gli elementi di gioco che costituiscono
	 * la modalita' wumpus, cioe' quella in cui il wumpus deve sopravvivere
	 * al cacciatore.
	 */
	public static final HashMap<CellStatus, String> wumpus_side_map =new HashMap<CellStatus, String>();
	
	/** GameModeTranslation()
	 * costruttore di default
	 * 
	 */
	public GameModeTranslation(){
		//traduzioni dell'eroe
		heroTranslation();
		//traduzioni del wumpus
		wumpusTranslation();
	}//costruttore di default

	/** metodo heroTranslation(): void
	 * 
	 */
	private void heroTranslation() {
		hero_side_map.put(CellStatus.PG, "Avventuriero");
		hero_side_map.put(CellStatus.ENEMY, "Wumpus");
		hero_side_map.put(CellStatus.AWARD, "Tesoro");
		hero_side_map.put(CellStatus.DANGER, "Fossa");
		hero_side_map.put(CellStatus.SAFE, "Sicura");
		hero_side_map.put(CellStatus.FORBIDDEN, "Sasso");
	}//end heroTranslation()
	
	/** metodo wumpusTranslation(): void
	 * 
	 */
	private void wumpusTranslation() {
		wumpus_side_map.put(CellStatus.PG, "Wumpus");
		wumpus_side_map.put(CellStatus.ENEMY, "Cacciatore");
		wumpus_side_map.put(CellStatus.AWARD, "Via di Fuga");
		wumpus_side_map.put(CellStatus.DANGER, "Trappola");
		wumpus_side_map.put(CellStatus.SAFE, "Sicura");
		wumpus_side_map.put(CellStatus.FORBIDDEN, "Sasso");
		
	}//end wumpusTranslation()

	//TODO messaggi durante il gioco
	
	
}//end class

/*
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
*/
