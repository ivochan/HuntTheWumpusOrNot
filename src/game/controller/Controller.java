package game.controller;

import game.structure.cell.CellStatus;
import game.structure.map.GameMap;

/** Controller
 * @author ivonne
 * questa classe definisce i comandi di gioco, cioe' il modo in cui si potra'
 * interagire con il programma, facendo muovere il personaggio giocabile, nelle direzioni
 * consentite.
 * Bisogna:
 * -verificare se la direzione in cui si vuole fare la mossa sia valida;
 * -effettuare la mossa;
 * -aggiornare la posizione del pg;
 * -mostrare le informazioni dei sensori;
 * -aggiornare la mappa di esplorazione;
 */
public class Controller {

	/** current_move : int[]
	 * vettore di due celle che contiene gli indici della cella in cui si 
	 * vuole che venga effettuata la mossa del pg
	 */
	private static int [] pg_pos = new int[2];
	/**
	 * 
	 * @param move
	 * @param pg_position
	 * @param gm
	 * @return
	 */
	public static int movePG(Direction move, int [] pg_position, GameMap gm) {
		//variabile da restituire
		int status = 0;
		//flag che indicano la validita' degli indici in cui effettuare la mossa
		boolean im_ok=false;
		boolean jm_ok=false;
		//si inizializzano gli indici della cella in cui si vuole effettuare la mossa
		int im=0; int jm=0;
		//si prelevano gli indici di cella in cui si trova il pg
		int ipg = pg_position[0];
		int jpg = pg_position[1];
		//si controlla la direzione in cui effettuare la mossa
		if(move == Direction.UP) { //move = 0 
			//si stabiliscono gli indici della cella in cui si vuole muovere il pg
			im = ipg-1;
			jm = jpg;
			//si controlla se la cella cosi' indicata esiste
			if(im>=0 && im<gm.getRows())im_ok=true;
			if(jm>=0 && jm<gm.getColumns())jm_ok=true;
		}//fi UP
		if(move == Direction.DOWN) { //move = 1
			//si stabiliscono gli indici della cella in cui si vuole muovere il pg
			im = ipg+1;
			jm = jpg;
			//si controlla se la cella cosi' indicata esiste
			if(im>=0 && im<gm.getRows())im_ok=true;
			if(jm>=0 && jm<gm.getColumns())jm_ok=true;
		}//fi DOWN
		if(move == Direction.LEFT) { //move = 2
			//si stabiliscono gli indici della cella in cui si vuole muovere il pg
			im = ipg;
			jm = jpg-1;
			//si controlla se la cella cosi' indicata esiste
			if(im>=0 && im<gm.getRows())im_ok=true;
			if(jm>=0 && jm<gm.getColumns())jm_ok=true;
		}//fi LEFT
		if (move == Direction.RIGHT) { //move = 3
			//si stabiliscono gli indici della cella in cui si vuole muovere il pg
			im = ipg;
			jm = jpg+1;
			//si controlla se la cella cosi' indicata esiste
			if(im>=0 && im<gm.getRows())im_ok=true;
			if(jm>=0 && jm<gm.getColumns())jm_ok=true;
		}//fi RIGHT
		//si controlla il risultato della direzione scelta
		//se la cella in cui si vuole effettuare la mossa esiste
		if(im_ok && jm_ok) {
			//TODO eliminare il pg dalla cella precedente
			
			System.out.println("Spostamento in ("+im+','+jm+')');
			setPGpos(im,jm);
			//si controlla il contenuto della cella in questione
			String cs = gm.getGameCell(im, jm).getCellStatusID();
			//controllo sullo stato
			if(cs.equals(CellStatus.ENEMY.name())) {
				//il pg e' morto
				status = 1;
			}//fi
			else {
				//ci si spostera' in questa cella, mostrando anche i sensori
				status = 0;
			}//esle
		}//fi indici di mossa corretti
		else { //comando non valido
			//l'input ricevuto non e' tra le direzioni ammesse
			status = -1;
		}//esle
		System.out.println("status = "+status);
		//si restituisce il codice associato al tipo di mossa
		return status;
	}//movePG
	
	/**
	 * 
	 * @return
	 */
	public static int[] getPGpos() {
		return pg_pos;
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 */
	public static void setPGpos(int i, int j) {
		pg_pos[0]=i;
		pg_pos[1]=j;
	}
	
}//Controller
