package game.automatic_player;
//serie di import
import java.util.LinkedList;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
/**
 * 
 * @author ivochan
 *
 */
public class AutomaticPlayer {

	//##### attributi di classe #####
	
	//vettore della posizione corrente del pg
	private static int[] cur_pg_pos = PlayableCharacter.getPGposition();
	//munizioni
	private static int chance_to_hit = 1;
	
	//##### metodo di risoluzione della partita #####
	
	/** metodo solveGame(GameMap, GameMap): int
	 * metodo pubblico da invocare per la risoluzione del gioco
	 * i parametri che dovranno essere aggiornati verranno gestiti dall'omonimo
	 * metodo privato
	 * @param gm: GameMap, mappa di gioco
	 * @param em: GameMap, mappa di esplorazione
	 * @return solveGame(int[], int[], GameMap,GameMap): int, metodo ricorsivo privato
	 */
	public static int solveGame(GameMap gm, GameMap em, LinkedList<Cell> run_path) {
		System.out.println("Risolvo!");
		//si inserisce la cella di partenza
		run_path.add(gm.getMapCell(cur_pg_pos[0], cur_pg_pos[1]));
		//la posizione precedente e' uguale a quella corrente nella prima mossa
		//si richiama il metodo ricorsivo
		return solveGame(cur_pg_pos, cur_pg_pos, gm, em, run_path);
	}//end solveGame
	
	/**
	 * 
	 * @param pre_pg_pos
	 * @param cur_pg_pos
	 * @param gm
	 * @param em
	 * @return
	 */
	private static int solveGame(int[] pre_pg_pos, int[] cur_pg_pos, GameMap gm, GameMap em, LinkedList<Cell> run_path) {
		//variabili ausiliarie per le liste di celle
		LinkedList<Cell> adjacent_cells = new LinkedList<>();
		LinkedList<Cell> covered_cells = new LinkedList<>();
		//variabili ausiliarie per gli indici della cella da esaminare
		int [] next_pos = new int[2];
		//risultato della mossa
		int result = 0;	
		//si preleva la cella attuale
		Cell current_cell = gm.getMapCell(cur_pg_pos[0],cur_pg_pos[1]);
		//contenuto della cella attuale
		CellStatus current_status =  current_cell.getCellStatus();
		
		//##### CASI DI USCITA #####
		
		//il pg ha trovato il premio
		if(current_status.equals(CellStatus.AWARD)) {
			return 1;
		}
		//il pg ha trovato il nemico o il pericolo
		if(current_status.equals(CellStatus.ENEMY) || current_status.equals(CellStatus.DANGER)) {
			return -1;
		}
		//il pg ha trovato il passaggio bloccato
		if(current_status.equals(CellStatus.FORBIDDEN)) {
			run_path.add(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
			return 0;
		}
		//##### TERMINE CASI DI USCITA #####
		
		//DOVE POSSO ANDARE: celle adiacenti
		findAdjacentCells(adjacent_cells, em, current_cell);
		//COSA POSSO VEDERE DI NUOVO: celle da visitare tra quelle adiacenti, perche' mai esplorate
		findCoveredCells(covered_cells, em, adjacent_cells);
		
		
		//##### CONTROLLO SULLA CELLA ATTUALE #####
		
		//si preleva il vettore dei sensori
		boolean [] current_sensors = gm.getMapCell(cur_pg_pos[0],cur_pg_pos[1]).getSenseVector();
		
		if(current_sensors[0] && chance_to_hit>0) {
			//si prende la prima cella tra quelle adiacenti e non visitate
			Cell cell_to_hit = covered_cells.get(0);
			//si preleva la posizione della cella
			int [] pos = cell_to_hit.getPosition();
			//si preleva lo stato della cella dalla mappa di gioco
			CellStatus cth_status = gm.getMapCell(pos[0], pos[1]).getCellStatus();
			//provo a sparare
			System.out.println("sono in "+'('+cur_pg_pos[0]+','+cur_pg_pos[1]+')');
			System.out.println("Sparo verso: "+'('+pos[0]+','+pos[1]+')');
			System.out.println("Risultato:\n"+em);
			//si verifica se contiene il nemico
			if(cth_status.equals(CellStatus.ENEMY)) {
				//il nemico e' stato colpito
				//TODO aggiornamento del punteggio
				System.out.println("Colpito");
				//la cella diventa safe
				gm.getMapCell(pos[0],pos[1]).setCellStatus(CellStatus.SAFE);
				//si disabilita il sensore del nemico della cella corrente
				current_sensors[0]=false;
			}//fi nemico colpito
			//si decrementano le munizioni
			chance_to_hit--;
		}//fi sensore nemico e possibilita' di colpire
		
		//sono attivi i sensori di pericolo: ENEMY_SENSE e/o DANGER_SENSE
		if(current_sensors[0] || current_sensors[1]){
			//SONO IN UNA SITUAZIONE DI PERICOLO
			//tutte le celle adiacenti potrebbero contenere un pericolo
			if(pre_pg_pos.equals(cur_pg_pos)) {
				//PRIMA MOSSA: mi trovo nella prima e unica cella visitata
				for(Cell adjacent_cell: covered_cells) {
					//visito la prima cella a me sconosciuta contenuta nella lista 
					//delle celle da scoprire 
					next_pos = adjacent_cell.getPosition();
					//prelevo le informazioni di questa cella dalla mappa di gioco
					Cell next_cell = gm.getMapCell(next_pos[0], next_pos[1]);
					//si aggiunge la cella corrente al ercorso delle celle visitate
					run_path.add(next_cell);
					
					//si inserisce questa cella nella mappa di esplorazione
					em.getMapCell(next_pos[0], next_pos[1]).copyCellSpecs(next_cell);
					//si valuta la mossa asuccessiva
					result = solveGame(cur_pg_pos, next_pos, gm,em, run_path);
					
					//controllo sul risultato: FINE PARTITA
					if(result == 1 || result == -1) return result;
			
				}//for
				
				//NON CI SONO CELLE ADIACENTI OPPURE SONO TUTTE POTENZIALMENTE PERICOLOSE
				
				//si aggiorna il percorso: ritorno alla cella precedente
				run_path.add(gm.getMapCell(pre_pg_pos[0], pre_pg_pos[1]));
				
				return -3;
				
			}//fi prima mossa
			
			//NON SONO ALLA PRIMA MOSSA e la cella corrente indica un pericolo nei dintorni		
			
			//si aggiorna il percorso: ritorno alla cella precedente
			run_path.add(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
			
			return -2;
		}//fi sensori accesi					
		
		else {	
			//SONO IN UNA SITUAZIONE TRANQUILLA
			
			//le celle adiacenti non hanno pericoli, sono apposto
			for(Cell adjacent_cell: covered_cells) {
				//visito la cella corrente
				//prelevo gli indici di questa cella
				next_pos = adjacent_cell.getPosition();
				//prelevo le informazioni di questa cella dalla mappa di gioco
				Cell next_cell = gm.getMapCell(next_pos[0], next_pos[1]);
				//si aggiunge la cella corrente al ercorso delle celle visitate
				run_path.add(next_cell);
				//si inserisce questa cella nella mappa di esplorazione
				em.getMapCell(next_pos[0], next_pos[1]).copyCellSpecs(next_cell);
				//si valuta la mossa successiva
				result = solveGame(cur_pg_pos, next_pos, gm,em, run_path);
	
				//si controlla il risultato: FINE PARTITA
				if(result == 1 || result == -1) return result;
			}//for
			
			//NON CI SONO CELLE ADIACENTI OPPURE SONO TUTTE POTENZIALMENTE PERICOLOSE
			run_path.add(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
			
			return -2;
		}//esle sensori spenti
		
	}

	
	/*
	//se e' acceso il sensore del nemico
	if(current_sensors[0]) {
		//si verifica la disponibilita' di munizioni
		if(chance_to_hit>0) {
			//si prende la prima cella tra quelle adiacenti e non visitate
			Cell cell_to_hit = covered_cells.get(0);
			//si preleva la posizione della cella
			int [] pos = cell_to_hit.getPosition();
			//si preleva il vettore dei sensori
			boolean [] hit_sensors = gm.getMapCell(pos[0], pos[1]).getSenseVector();
			//si preleva lo stato della cella
			CellStatus cth_status = cell_to_hit.getCellStatus();
			//si verifica se contiene il nemico
			if(cth_status.equals(CellStatus.ENEMY)) {
				//il nemico e' stato colpito
				//TODO aggiornamento del punteggio
				//la cella diventa safe
				gm.getMapCell(pos[0],pos[1]).setCellStatus(CellStatus.SAFE);
				//si disabilita il sensore del nemico
				hit_sensors[0]=false;
			}
		}//se non si hanno munizini si prosegue come se fosse un pericolo
	*/
	
	
	
	
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String printStatusMessage(int number) {
		//variabile ausiliaria 
		String game_status = new String("processing");
		//switch case
		switch(number){
		case 0:
			game_status = new String("denied");
			break;
		case 1 : 
			game_status = new String("winner");
			break;
		case -1: 
			game_status = new String("looser");
			break;
		case -2:
			game_status = new String("danger");
			break;
		case -3:
			game_status = new String("danger sensor - first move");
			break;
		default:
			break;
		}//end
		return game_status;
	}//printStatusMessage
	
	
	
	
	//##### metodi di supporto al calcolo della soluzione #####
	
	/** metodo findAdjacentCells(Cell)
	 * questo metodo ricerca tutte le celle adiacenti a quella data
	 * e le inserisce in una lista, dopo aver verificato che esistano
	 * nella mappa di gioco che e' stata generata.
	 * @param em: GameMap, mappa di esplorazione, conosciuta dal giocatore
	 * @param c: Cell, cella da cui partire per cercare quelle ad essa adiacenti
	 * @return
	 */
	private static void findAdjacentCells(LinkedList<Cell> list, GameMap em, Cell c) {
		//controllo sul parametro list
		if(list==null)throw new IllegalArgumentException("lista nulla");
		//controllo sul parametro mappa
		if(em==null)throw new IllegalArgumentException("mappa di gioco nulla");
		//controllo sul parametro cella
		if(c==null)throw new IllegalArgumentException("cella nulla");
		//oggetto cella
		Cell cell;
		//si prelevano gli indici di cella di quella ricevuta come parametro
		int [] cell_index = c.getPosition();
		//indice riga
		int i = cell_index[0];
		//indice colonna
		int j = cell_index[1];
		//controllo dell'esistenza della cella UP
		if(em.cellExists(i-1, j)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i-1,j);
			//si aggiunge alla lista
			list.add(cell);
		}//fi
		//controllo della cella LEFT
		if(em.cellExists(i, j-1)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i, j-1);
			//si aggiunge alla lista
			list.add(cell);
		}//fi
		//controllo della cella DOWN
		if(em.cellExists(i+1, j)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i+1,j);
			//si aggiunge alla lista
			list.add(cell);
		}//fi
		//controllo della cella RIGHT
		if(em.cellExists(i, j+1)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i, j+1);
			//si aggiunge alla lista
			list.add(cell);
		}//fi
	}//findAdjacentCells

	/** metodo findCoveredCells(): LinkedList
	 * questo identifica tutte le celle adiacenti che ancora non sono state visitate
	 * @param
	 * @param 
	 * @return
	 */
	private static void findCoveredCells(LinkedList<Cell> list, GameMap em, LinkedList<Cell> adjacent_cells){
		//controllo sul parametro list
		if(list==null)throw new IllegalArgumentException("lista celle adiacenti e non visitate nulla");
		//controllo sul parametro che contiene le celle adiacenti
		if(adjacent_cells == null)throw new IllegalArgumentException("lista celle adiacenti nulla");
		//si itera la lista che contiene le celle adiacenti
		for(Cell c: adjacent_cells) {
			//si preleva lo stato della cella
			CellStatus status = c.getCellStatus();
			//si verifica se non e' gia' stata visitata
			if(status.equals(CellStatus.UNKNOWN)) {
				//se non e' stata visitata, allora si aggiunge alla lista delle celle da scoprire
				list.add(c);
			}//fi
		}//for
	}//findCoveredCells()	
	
	/** metodo runPathToString(): String
	 * questo metodo restituisce il percorso compiuto
	 * dal giocatore automatico, sotto forma di stringa,
	 * fornendo un elenco delle celle che sono state visitate,
	 * rappresentandole come coppia di indici (i,j)
	 * @return run_list: String, lista delle celle visitate.
	 */
	public static String runPathToString(LinkedList<Cell> run_path) {
		//variabile ausiliaria
		int [] position = new int[2];
		//stringa da stampare
		String run_list = new String("");
		//si iterano le celle visitate
		for(Cell c: run_path) {
			//si preleva la posizione della cella in esame
			position = c.getPosition();
			//si inserisce nella lista
			run_list += "("+position[0]+','+position[1]+")\n";
		}//end for
		return run_list;
	}//runPathToString()
	
}//end AutomaticPlayer

