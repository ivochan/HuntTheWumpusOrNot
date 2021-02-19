package game.controller;

import game.structure.cell.Cell;
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
	
	/** metodo movePG(Direction, int[], GameMap, GameMap): int
	 * questo metodo si occupa di verificare se la mossa scelta dal giocatore sia
	 * valida oppure meno, controllando se la cella in cui effettuare la mossa esista,
	 * che tipo di contenuto abbia e cosa comporti spostarvici il pg.
	 * Il risultato delle operazioni di controllo verra' indiicato da una variabile
	 * di tipo intero.
	 * @param move: Direction, direzione in cui effettuare lo spostamento del pg;
	 * @param pg_position: int[], vettore che contiene la posizione corrente del pg,
	 * 							  espressa come coppia di indici i,j della cella in cui
	 * 							  si trova attualmente;
	 * @param gm: GameMap, mappa che racchiude le informazioni con cui e' stato configurata
	 * 					   la partita di gioco corrente;
	 * @param ge: GameMap, mappa che racchiude le infomazioni del gioco conosciute all'utente,
	 * 					   come le celle gia' visitate;
	 * @return status: int, intero che indica il risultato dell'esecuzione di questo metodo,
	 * 				   nello specifico, se questa variabile assume il valore:
	 * 				 -  1, allora il pg e' finito nella cella del nemico, ha perso;
	 * 				 -  2, il pg e' finito nella cella con il premio, ha vinto;
	 * 				 - -1, la mossa non era valida oppure prevedeva di andare in una cella non accessibile;
	 * 				 -  0, la mossa e' valida e il pg viene spostato, aggiornando la mappa di esplorazione
	 * 					   e la sua posizione corrente, segnando la cella in cui si trovata prima come visitata.
	 */
	public static int movePG(Direction move, int [] pg_position, GameMap gm, GameMap ge) {
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
		if(im_ok && jm_ok) { //la cella in cui si vuole effettuare la mossa esiste
			//si aggiorna la posizione del pg
			//System.out.println("Spostamento in ("+im+','+jm+')');
			//setPGpos(im,jm);
			//si controlla il contenuto della cella in questione
			String cs = gm.getGameCell(im, jm).getCellStatusID();
			//controllo sullo stato
			if(cs.equals(CellStatus.ENEMY.name())) {
				//il pg e' morto
				status = 1;
			}//fi
			else if(cs.equals(CellStatus.FORBIDDEN.name())) {
				//questa cella non e' selezionabile, e' vietata perche' e' un sasso
				status = -1;
				System.out.println("Il passaggio e' bloccato da un sasso.");
				//si aggiunge alla mappa di esplorazione
				ge.getGameCell(im, jm).copyCellSpecs(gm.getGameCell(im, jm));
				//il pg rimane dove si trova
			}
			else if(cs.equals(CellStatus.AWARD.name())) {
				//il pg vince
				status = 2 ;
			}
			else if(cs.equals(CellStatus.DANGER.name())) {
				//il pg e' morto
				status = 1;
			}
			else {
				//la cella in cui si trovava prima il pg si segna come visitata
				//gm.getGameCell(ipg,jpg).setIsVisited(true);
				//si preleva il contenuto della cella
				Cell c = gm.getGameCell(im, jm);
				//si copia questa cella nella matrice di esplorazione
				ge.getGameCell(im, jm).copyCellSpecs(c);
				//il contenuto di questa cella ora e' il pg
				ge.getGameCell(im, jm).setCellStatus(CellStatus.PG);
				//ai aggiorna la posizione del pg
				setPGpos(im,jm);
				//ci si spostera' in questa cella, mostrando anche i sensori
				status = 0;
			}//esle
		}//fi indici di mossa corretti
		else { //comando non valido, oppure la cella non esiste
			//l'input ricevuto non e' tra le direzioni ammesse
			status = -1;
		}//esle
		//System.out.println("status = "+status);
		//si restituisce il codice associato al tipo di mossa
		return status;
	}//movePG
	
	/** metodo getPGpos(): int[]
	 * questo metodo restiutisce la posizione del pg all'interno della mappa di
	 * gioco, espressa come indice di riga e di colonna della cella in cui si trova.	 * 
	 * @return pg_pos: int [], vettore che contiene la posizione del pg.
	 */
	public static int[] getPGpos() {
		return pg_pos;
	}//getPGpos()
	
	/** metodo setPGpos(int, int): void
	 * questo metodo imposta la posizione corrente del pg, sulla base degli indici
	 * di riga e di colonna ricevuti come parametro, per indicare la cella nella
	 * quale verra' posizionato
	 * @param i: int, indice riga della cella;
	 * @param j: int, indice colonna della cella;
	 */
	public static void setPGpos(int i, int j) {
		//controllo sull'indice di riga
		if(i<0 || j<0) {
			//indici negativi
			System.err.println("Indici negativi");
		}else {
			//indici validi
			pg_pos[0]=i;
			pg_pos[1]=j;
		}
	}//setPGpos()
	
}//Controller
