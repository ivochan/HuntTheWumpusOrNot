package game.controller;

import game.session.GameModeTranslation;
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
	
	/* vettore di due celle che contiene gli indici della cella in cui si 
	 * vuole che venga effettuata la mossa del pg
	 */
	private static int [] pg_pos = new int[2];
	
	/* vettore di due celle che contiene gli indici della cella in cui si vuole
	 * tentare di effettuare la mossa successiva 
	 */
	private static int [] next_pos = new int[2];

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

	/** metodo findCell(Direction, GameMap): int[]
	 * questo metodo idenfica gli indici di cella in cui si vuole
	 * muovere il pg, calcolandoli a partire dalla posizione corrente.
	 * @param move
	 * @return cell_indices: int[], coppia di indici della cella; 
	 */
	private static int[] findCell(Direction move){
		//vettore della posizione successiva
		int [] cell_indices = new int[2];
		//si preleva la posizione del pg
		int ipg = pg_pos[0];
		int jpg = pg_pos[1];
		//System.out.println("posizione corrente "+ipg+" e "+jpg);
		//si inizializza la coppia di indici ausiliari
		int im=0;
		int jm=0;
		//si verifica se esiste una cella nella direzione indicata
		switch(move) {
			case UP: //move = 0
				//indici della cella in cui si vuole muovere il pg
				im = ipg-1;
				jm = jpg;
				break;
			case DOWN: //move = 1
				//indici della cella in cui si vuole muovere il pg
				im = ipg+1;
				jm = jpg;
				break;
			case LEFT://move = 2
				//indici della cella in cui si vuole muovere il pg
				im = ipg;
				jm = jpg-1;
				break;
			case RIGHT://move = 3
				//indici della cella in cui si vuole muovere il pg
				im = ipg;
				jm = jpg+1;
				break;
			default: break;
		}//end switch
		//si memorizzano questi indici nel vettore della posizione successiva
		cell_indices[0]=im;
		cell_indices[1]=jm;
		//System.out.println("prossima posizione "+im+" e "+jm);
		//si restituisce il vettore contenente i due indici
		return cell_indices;
	}//findCell()
	
	/** metodo checkCell(GameMap): boolean
	 * questo metodo si occupa di controllare che la cella in cui
	 * si vuole fare la prossima mossa, dati gli indici contenuti
	 * nel vettore che descrive la prossima posizione del pg.
	 * @param gm: GameMap, e' la mappa che contiene gli elementi di gioco.
	 * @return true, se la cella descritta dagli indici di riga e colonna
	 * 				esiste, false altrimenti.
	 */
	private static boolean checkCell(GameMap gm) {
		//variabili boolean ausiliarie
		boolean iok=false;
		boolean jok=false;
		//indice riga
		int i=next_pos[0];
		//indice colonna
		int j=next_pos[1];
		//controllo sull'indice riga
		if(i>=0 && i<gm.getRows())iok=true;
		//controllo sull'indice colonna
		if(j>=0 && j<gm.getColumns())jok=true;
		//la cella esiste se entrambi gli indici sono validi
		return (iok && jok);
	}//checkCell()
	
	/** metodo movePG(Direction, int[], GameMap, GameMap): int
	 * questo metodo si occupa di verificare se la mossa scelta dal giocatore sia
	 * valida oppure meno, controllando se la cella in cui effettuare la mossa esista,
	 * che tipo di contenuto abbia e cosa comporti spostarvici il pg.
	 * Il risultato delle operazioni di controllo verra' indiicato da una variabile
	 * di tipo intero.
	 * @param move: Direction, direzione in cui effettuare lo spostamento del pg;
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
	public static int movePG(Direction move,GameMap gm, GameMap ge) {
		//variabile da restituire
		int status = 0;
		//si controlla il risultato della direzione scelta
		next_pos = findCell(move);
		//si controlla la validita' della mossa
		if(checkCell(gm)) { //la cella in cui si vuole effettuare la mossa esiste
			//si controlla il contenuto della cella in questione
			CellStatus cs = gm.getGameCell(next_pos[0], next_pos[1]).getCellStatus();
			//controllo sullo stato
			if(cs.equals(CellStatus.ENEMY)) {
				//si aggiorna la posizione 
				setPGpos(next_pos[0],next_pos[1]);
				//il pg e' morto
				status = 1;
			}//fi
			else if(cs.equals(CellStatus.FORBIDDEN)) {
				//questa cella non e' selezionabile, e' vietata perche' e' un sasso
				status = -1;
				System.out.println("Il passaggio e' bloccato da un sasso.");
				//si aggiunge alla mappa di esplorazione
				ge.getGameCell(next_pos[0], next_pos[1]).copyCellSpecs(gm.getGameCell(next_pos[0], next_pos[1]));
				//il pg rimane dove si trova
			}
			else if(cs.equals(CellStatus.AWARD)) {
				//si aggiorna la posizione 
				setPGpos(next_pos[0],next_pos[1]);
				//il pg vince
				status = 2 ;
			}
			else if(cs.equals(CellStatus.DANGER)) {
				//si aggiorna la posizione 
				setPGpos(next_pos[0],next_pos[1]);
				//il pg e' morto
				status = 1;
			}
			else {//CellStatus.SAFE				
				//la cella in cui si trovava prima il pg si segna come visitata
				ge.getGameCell(pg_pos[0],pg_pos[1]).setCellStatus(CellStatus.OBSERVED);
				//si preleva il contenuto della cella
				Cell c = gm.getGameCell(next_pos[0], next_pos[1]);
				//si copia questa cella nella matrice di esplorazione
				ge.getGameCell(next_pos[0], next_pos[1]).copyCellSpecs(c);
				//il contenuto di questa cella ora e' il pg
				ge.getGameCell(next_pos[0], next_pos[1]).setCellStatus(CellStatus.PG);
				//ai aggiorna la posizione del pg
				setPGpos(next_pos[0],next_pos[1]);
				//ci si spostera' in questa cella, mostrando anche i sensori
				status = 0;
			}//esle				
		}//fi indici di mossa corretti
		else { //comando non valido, oppure la cella non esiste
			status = -1;
		}//esle
		//si restituisce il codice associato al tipo di mossa
		return status;
	}//movePG()
	
	/** metodo hitEnemy(Direction, GameMap, GameMap): boolean
	 * questo metodo controlla la direzione in cui si vuole provare
	 * a colpire il nemico, verifica se la cella indicata dall'utente
	 * esiste e verifica se in essa sia posizionato il nemico oppure no.
	 * Se il colpo va a segno, il giocatore guadagna punti, altrimenti
	 * ha perso l'unico tentativo di poterlo sconfiggere.
	 * @param dir: Direction, direzione in cui si vuole lanciare il colpo;
	 * @param gm: GameMap, mappa che rappresenta il terreno di gioco;
	 * @param ge: GameMap, mappa di esplorazione, visibile all'utente;
	 * @return defeated: boolean, indica se il nemico e' stato colpito o meno
	 */
	public static boolean hitEnemy(Direction dir, GameMap gm, GameMap ge) {
		//flag per indicare se il nemico e' stato colpito
		boolean defeated=false;
		//vettore degli indici di cella
		int[] enemy_indices=new int[2];
		//variabili ausiliarie per la validita' degli indici
		boolean iok=false;
		boolean jok=false;
		//si preleva la direzione in cui si vuole colpire
		enemy_indices = findCell(dir);
		//indice riga
		int i=enemy_indices[0];
		//indice colonna
		int j=enemy_indices[1];
		//controllo sull'indice riga
		if(i>=0 && i<gm.getRows())iok=true;
		//controllo sull'indice colonna
		if(j>=0 && j<gm.getColumns())jok=true;
		//la cella esiste se entrambi gli indici sono validi
		if(iok && jok) {
			//si controlla se questa cella contiene il nemico
			CellStatus cs = gm.getGameCell(i, j).getCellStatus();
			//si controlla se contiene il nemico
			if(cs.equals(CellStatus.ENEMY)) {
				//si aggiorna la mappa di gioco
				gm.getGameCell(i, j).setCellStatus(CellStatus.SAFE);
				//si aggiorna il flag
				defeated = true;
				//si stampa l'info
				System.out.println(GameModeTranslation.hit);
			}//fi
			else {
				//la cella non contiene il nemico
				System.out.println(GameModeTranslation.wasted_shot);
			}//esle
		}//fi indici cella
		else {
			//gli indici di cella non sono validi
			System.out.println(GameModeTranslation.failed_shot);
		}//esle
		return defeated;
	}//hitEnemy()
	
	
}//Controller
