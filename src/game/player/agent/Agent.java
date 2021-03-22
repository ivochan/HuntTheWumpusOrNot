package game.player.agent;
//serie di import
import java.util.LinkedList;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.map.GameMap;
/** interface Agent
 * questa interfaccia definisce la struttura di un agente generico,
 * che verra' utilizzato per risolvere il gioco in maniera automatica,
 * su richiesta dell'utente.
 * @author ivonne
 */
public interface Agent {
	//##### attribuiti #####
	
	//lista contenente la coppia di indici di ogni cella visitata
	LinkedList<Cell> run = new LinkedList<Cell>();

	//##### metodi #####

	//metodo che valuta la mossa da fare
	abstract void chooseMove(GameMap em, GameMap gm);
	
	//metodo che sceglie la direzione in cui effettuare la mossa
	abstract Direction chooseDirection(int i, int j, GameMap em);
	
	//metodo che tiene traccia del punteggio accumulato: utilizzare quello di Score
	//void updateScore();
	
	//##### metodi per il percorso #####
	
	/** metodo getRunPath(): LinkedList<Cell>
	 * questo metodo restituisce il percorso compiuto dal giocatore
	 * automatico, durante l'ultima partita.
	 */
	public LinkedList<Cell> getRunPath();

	/** metodo runPathToString(): String
	 * questo metodo restituisce il percorso compiuto
	 * dal giocatore automatico, sotto forma di stringa,
	 * fornendo un elenco delle celle che sono state visitate,
	 * rappresentandole come coppia di indici (i,j)
	 * @return run_list: String, lista delle celle visitate.
	 */
	public String runPathToString();
	
	/** metodo updateRunPath(Cell): void
	 * questo metodo inserisce la cella ricevuta come parametro
	 * nella lista delle celle visitate durante la partita del
	 * giocatore automatico.
	 * @param c: Cell, cella della mappa di esplorazione che e'
	 * 					stata gia' visitata.
	 */
	public void updateRunPath(Cell c);
	
	/** metodo showRunPath(): void
	 * questo metodo stampa a video il percorso effettuato dal
	 * giocatore automatico durante l'esplorazione della mappa
	 * di gioco.
	 */
	public void showRunPath();
		
}//end Agent
