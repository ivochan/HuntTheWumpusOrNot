package game.player.agent.app;
//serie di import
import java.util.LinkedList;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
/** BasicAgent class
 * questa classe fornisce i metodi di cui necessitano i vari tipi di giocatore
 * automatico
 * 
 * @author ivochan
 *
 */
public class BasicAgent {
	
	//##### metodo per la realizzazione della mossa #####
	
	/** metodo movePG(Direction, GameMap, GameMap): int
	* metodo che realizza la mossa del pg
	* @param move
	* @param gm
	* @param ge
	* @return
	*/
	public static int movePG(Direction move, GameMap gm, GameMap ge) {
		//vettore della posizione del pg
		int [] pg_position = PlayableCharacter.getPGposition();
		//vettore della posizione successiva
		int [] cell_pos= new int[2];
		//variabile da restituire
		int status = 0;
		//TODO modifiche
		//controllo sulla direzione
		if(move==null) {
			System.out.println("cella pg");
			//non sono state trovate direzioni utili in cui andare percio'
			//si rimane nella cella del pg
			return 0;
		}
		else {
		System.out.println(move);
		System.out.println("PG "+pg_position[0]+" "+pg_position[1]);
		//si controlla il risultato della direzione scelta
		cell_pos = Controller.findCell(move, pg_position);
		System.out.println("Muovo verso la cella ("+cell_pos[0]+','+cell_pos[1]+')');
		//la cella indicata da next_pos esiste
		if(Controller.checkCell(cell_pos, gm)) {
			//si preleva la cella in cui andare
			Cell current = gm.getMapCell(cell_pos[0], cell_pos[1]);
			System.out.println(current);
			//si controlla il contenuto della cella in questione
			CellStatus cs = gm.getMapCell(cell_pos[0], cell_pos[1]).getCellStatus();
			//debug
			System.out.println("Stato cella "+cs);
			//controllo sullo stato
			if(cs.equals(CellStatus.ENEMY)) {
				//il pg e' stato ucciso dal nemico
				status = 1;
				//si deve aggiornare la posizione del PG
				PlayableCharacter.setPGposition(cell_pos);
			}//fi
			else if(cs.equals(CellStatus.FORBIDDEN)) {
				//questa cella e' vietata perche' e' un sasso
				status = -1;
				//si aggiunge alla mappa di esplorazione
				ge.getMapCell(cell_pos[0], cell_pos[1]).
					copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
				//il pg rimane dove si trova
			}//fi
			else if(cs.equals(CellStatus.AWARD)) {
				//il pg vince
				status = 2 ;
				//si aggiorna la posizione
				PlayableCharacter.setPGposition(cell_pos);
			}//fi
			else if(cs.equals(CellStatus.DANGER)) {
				//il pg e' caduto nella trappola
				status = 1;
				//si aggiorna la posizione
				PlayableCharacter.setPGposition(cell_pos);
			}//fi
			else {//CellStatus.SAFE
				//il pg si trova in una cella libera
				status = 0;
				//la cella in cui si trovava prima il pg si segna come visitata
				ge.getMapCell(pg_position[0], pg_position[1]).setCellStatus(CellStatus.OBSERVED);
				//si aggiorna la posizione del pg
				PlayableCharacter.setPGposition(cell_pos);
				//si preleva il contenuto della cella in cui si trova attualmente il pg
				Cell c = gm.getMapCell(cell_pos[0], cell_pos[1]);
				//si copia questa cella nella matrice di esplorazione
				ge.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(c);
				//il contenuto di questa cella nella mappa di esplorazione e' il pg
				ge.getMapCell(cell_pos[0], cell_pos[1]).setCellStatus(CellStatus.PG);
			}//esle
			//aggiornamento del punteggio
		}//fi indici di mossa corretti
		else {
			System.out.println("Cella non esistente");
			//comando non valido, oppure la cella non esiste
			status = -2;
		}//esle
		//si restituisce il codice associato al tipo di mossa
		return status;
		}
	}//movePG(Direction, GameMap, GameMap)

	//##### metodo per la scelta della mossa #####
	
	/** metodo chooseDirection(int, int, GameMap)
	* questo metodo serve a determinare la direzione in 
	* cui muovere il personaggio giocabile, scegliendo in
	* maniera casuale, una cella tra le direzioni consentite,
	* a partire dalle cella in cui e' situato il pg.
	* @param i: int, indice di riga della cella in cui si trova il pg;
	* @param j: int, indice di colonna della cella in cui si trova il pg;
	* @param em: GameMap, mappa di esplorazione, visibile al pg;
	* @return dirn: Direction, direzione in cui si vuole spostare il pg;
	*
	*/
	public static Direction chooseDirection(int i, int j, GameMap em) {
		//variabile ausiliaria per la direzione
		Direction dir = null;
		//vettore ausiliario per le celle esistenti adiacenti a quella attuale
		boolean [] available_cells = new boolean[4];
		//vettore ausiliario per le celle accessibili e non visitate
		boolean [] covered_cells = new boolean[4];
		//vettore ausiliario per le celle accessibili e visitate
		boolean [] observed_cells = new boolean[4];
		//variabile ausiliaria
		boolean face_up;
		//vettore che conterra' i sensori della cella corrente
		boolean [] sensors = new boolean[2];
		//si preleva la cella corrente
		Cell current = em.getMapCell(i,j);
		//si prelevano i sensori della cella corrente
		sensors = current.getSenseVector();	
		//indice di cella casuale
		int random = 0;
		//variabile ausiliaria per l'indice di cella
		int index = 0;
		//si prelevano le direzioni possibili
		//UP=0, DOWN=1, LEFT=2, RIGHT=3
		Direction [] directions = Direction.values();
		//si verificano le celle disponibili
		if(em.cellExists(i-1, j)) {
			//si calcola l'indice
			index = Direction.UP.ordinal();
			//la cella esiste
			available_cells[index] = true;
			//si verifica se la cella sia accessibile e gia' visitata
			face_up = isObservedCell(i-1,j, em);
			System.out.println("Sopra");
			//si aggiorna il vettore
			observed_cells[index] = face_up;
			//si verifica se la cella sia non visitata
			covered_cells[index] = !face_up;
		}//UP
		if(em.cellExists(i+1, j)) {
			//si calcola l'indice
			index = Direction.DOWN.ordinal();
			//la cella esiste
			available_cells[index] = true;
			//si verifica se la cella sia accessibile e gia' visitata
			face_up = isObservedCell(i+1,j, em);
			System.out.println("Sotto");
			//si aggiorna il vettore
			observed_cells[index] = face_up;
			//si verifica se la cella sia non visitata
			covered_cells[index] = !face_up;
		}//DOWN
		if(em.cellExists(i, j-1)) {
			//si calcola l'indice
			index = Direction.LEFT.ordinal();
			//la cella esiste
			available_cells[index] = true;
			//si verifica se la cella sia accessibile e gia' visitata
			face_up = isObservedCell(i,j-1, em);
			System.out.println("Sinistra");
			//si aggiorna il vettore
			observed_cells[index] = face_up;
			//si verifica se la cella sia non visitata
			covered_cells[index] = !face_up;
		}// LEFT
		if(em.cellExists(i, j+1)) {
			//si calcola l'indice
			index = Direction.RIGHT.ordinal();
			//la cella esiste
			available_cells[index] = true;
			//si verifica se la cella sia accessibile e gia' visitata
			face_up = isObservedCell(i,j+1, em);
			System.out.println("Destra");
			//si aggiorna il vettore
			observed_cells[index] = face_up;
			//si verifica se la cella sia non visitata
			covered_cells[index] = !face_up;
		}//RIGHT
		//controllo sui sensori della cella corrente
		if(!sensors[0] && !sensors[1]) {
			//entrambi i sensori sono spenti
			//si cerca di andare in una cella mai visitata
			if(checkCells(covered_cells)) {
				//tra le celle adiacenti non visitate si sceglie a caso
				random = pickCell(covered_cells);
				System.out.println("posto sicuro e ci sono celle nuove da esplorare");
			}//fi
			else {
				System.out.println("posto sicuro ma non ci sono celle nuove da esplorare");
				//se non ci sono celle adiacenti non visitate si sceglie tra quelle gia' visitate
				if(areThereAvailableCells(observed_cells)) {
					random = pickCell(observed_cells);
				}
				else {
					//cella in cui si trova gia' il pg
					System.out.println("resto dove sono");
					return dir;
				}
			}//esle
		}//fi
		else {
			System.out.println("posto non sicuro, si torna indietro");
			//almeno uno dei sensori e' accesso, percio' si torna indietro
			if(areThereAvailableCells(observed_cells)){	
				random = pickCell(observed_cells);
			}
			else {
				System.out.println("torno da dove sono venuto");
				//cella del pg
				//direzione NONE
				return dir;
			}
			
		}//esle
		//si estrae la direzione scelta
		dir = directions[random];
		//debug
		System.out.println("Direzione scelta: "+dir);
		//si restituisce la direzione
		return dir;
	}//chooseDirection(int, int, GameMap)
	
	//##### metodi di controllo della cella scelta come mossa successiva #####
	
	/** metodo pickCell(boolean []): int
	 * questo metodo seleziona casualmente una cella
	 * tra quelle indicate come true, nel vettore ricevuto come
	 * parametro.
	 * @param vcells: boolean[], vettore delle celle tra cui
	 * 							 selezionare quella in cui effettuare
	 * 							 la mossa;
	 * @return random: int, indice della cella da estrarre.
	 * 
	 */
	public static int pickCell(boolean [] vcells) {
		//range del numero casuale
		int range = vcells.length;
		//variabile ausiliaria
		boolean found = false;
		//numero casuale
		int random = 0;
		//si sceglie casualmente una cella tra quelle idonee
		while(!found) {
			//si genera un numero casuale
			random = (int)(Math.random()*range);
			//System.out.println("random "+random);
			//si accede alla cella corrispondente
			found = vcells[random];
			//se true si esce dal ciclo
		}//end while
		//si restituisce l'indice della cella selezionata
		return random;
	}//pickCell(boolean[])

	public static boolean areThereAvailableCells(boolean [] cells) {
		//variabile ausiliaria
		boolean flag;
		//almeno una casella del vettore contiene una cella pari a true
		for(int i=0;i<cells.length;i++) {
			//si preleva il contenuto della cella
			flag = cells[i];
			//se true si permina il metodo
			if(flag)return true;
		}
		//non e' stata trovata nessuna cella pari a true
		return false;
	}
	
	/** metodo isObservedCell(int, int, GameMap): boolean
	 * questo metodo verifica se la cella in cui si vuole spostare
	 * il personaggio giocabile sia idonea o no, in base al suo contenuto.
	 * Infatti, per essere una scelta valida, la cella non deve essere 
	 * ancora stata visitata
	 * @param i: int, indice di riga della cella in cui si trova il pg;
	 * @param j: int, indice di colonna della cella in cui si trova il pg;
	 * @param em: GameMap, mappa di esplorazione, visibile al pg;
	 * 
	 */
	public static boolean isObservedCell(int i, int j, GameMap em) {
		//si preleva la cella
		CellStatus cs = em.getMapCell(i, j).getCellStatus();
		//se lo stato della cella e' nullo allora non e' mai stata visitata
		if(cs==null)return false;
		//si verifica che la cella non e' stata visitata
		return cs.equals(CellStatus.OBSERVED);
	}//isCoveredCell(int, int, GameMap)
	
	/** metodo checkCells(boolean []): boolean
	 * questo metodo verifica se questo vettore contiene almeno una
	 * cella che sia pari a true.
	 * @param cells: boolean [], vettore di celle risultate idonee
	 * @return check: boolean, questo flag sara' true se almeno una cella
	 * 						   del vettore e' risultata idonea
	 */
	public static boolean checkCells(boolean [] cells) {
		//variabile ausiliaria per il controllo
		boolean check = false;
		//si itera il vettore
		for(int i=0; i<cells.length;i++) {
			//System.out.println("cella "+i+" = "+ok_cells[i]);
			//si preleva il contenuto della cella
			if(cells[i]) check = true;
		}//end for
		return check;
	}//checkCells(boolean [])
	
	//##### metodi per la gestione del percorso effettuato #####
	
	/** metodo runPathToString(): String
	 * questo metodo restituisce il percorso compiuto
	 * dal giocatore automatico, sotto forma di stringa,
	 * fornendo un elenco delle celle che sono state visitate,
	 * rappresentandole come coppia di indici (i,j)
	 * @return run_list: String, lista delle celle visitate.
	 */
	public static String runPathToString(LinkedList<Cell> run) {
		//variabile ausiliaria
		int [] position = new int[2];
		//stringa da stampare
		String run_list = new String("");
		//si iterano le celle visitate
		for(Cell c: run) {
			//si preleva la posizione della cella in esame
			position = c.getPosition();
			//si inserisce nella lista
			run_list += "("+position[0]+','+position[1]+")\n";
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
	public static void updateRunPath(LinkedList<Cell> run, Cell c) {
		//controllo sul parametro lista
		if(run==null)throw new IllegalArgumentException("Lista contenente il percorso delle"
				+ " celle visitate nulla");
		//controllo sul parametro cella
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
	public static void showRunPath(LinkedList<Cell> run) {
		//si stampa a video la lista delle celle visitate
		System.out.println("Ecco il percorso:\n");
		System.out.println(runPathToString(run));
	}//showRunPath()
	
}//end BasicAgentUtility




