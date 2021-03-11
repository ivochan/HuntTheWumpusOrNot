package game.player.agent;

import java.util.LinkedList;

import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.map.GameMap;
/** abstract class BasicAgent
 * questa classe definisce le funzioni di base di un 
 * generico giocatore automatico
 * @author Ivonne
 */
public abstract class BasicAgent implements Agent {
	//##### attribuiti di classe #####
	
	//lista contenente la coppia di indici di ogni cella visitata
	protected LinkedList<Cell> run = new LinkedList<Cell>();
	
	//##### metodi per la scelta della mossa #####
	
	/**
	 * questo metodo valuta la mossa da fare
	 * @param em: GameMap
	 */
	public abstract void chooseMove(GameMap em, GameMap gm);
	
	//##### metodi per il percorso #####
	
	/** metodo getRunPath(): LinkedList<Cell>
	 * questo metodo restituisce la lista che tiene traccia
	 * del percorso svolto dal giocatore automatico
	 * @return run: LinkedList<Cell>, lista delle celle visitate.
	 */
	public LinkedList<Cell> getRunPath(){
		//si restituisce la lista del percorso compiuto
		return run;
	}//getRunPath()
	
	/** metodo runPathToString(): String
	 * questo metodo restituisce il percorso compiuto
	 * dal giocatore automatico, sotto forma di stringa,
	 * fornendo un elenco delle celle che sono state visitate,
	 * rappresentandole come coppia di indici (i,j)
	 * @return run_list: String, lista delle celle visitate.
	 */
	public String runPathToString() {
		//variabile ausiliaria
		int [] position = new int[2];
		//stringa da stampare
		String run_list = new String("Percorso:\n");
		for(Cell c: run) {
			//si preleva la posizione della cella in esame
			position = c.getPosition();
			//si inserisce nella lista
			run_list += '['+position[0]+','+position[1]+"]\n";
		}//end for
		return run_list;
	}//runPathToString()
	
	/** metodo updateRunPath(Cell): void
	 * questo metodo inserisce la cella ricevuta come parametro
	 * nella lista delle celle visitate durante la partita del
	 * giocatore automatico.
	 * @param c: Cell, cella della mappa di esplorazione che e'
	 * 					stata gia' visitata.
	 */
	public void updateRunPath(Cell c) {
		//controllo sul parametro
		if(c==null) throw new IllegalArgumentException("Cella indicata come"
				+ " visitata dal valore nullo");
		//si inserisce la cella in coda, nella lista di quelle visitate
		run.add(c);
	}//updateRunPath(Cell)
		
	/** metodo showRunPath(): void
	 * questo metodo stampa a video il percorso effettuato dal
	 * giocatore automatico durante l'esplorazione della mappa
	 * di gioco.
	 */
	public void showRunPath() {
		//si stampa a video la lista delle celle visitate
		System.out.println("Ecco il percorso:\n");
		System.out.println(runPathToString());
	}//showRunPath()
		
}//BasicAgent
