package game.automatic_player;

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
	
	//lista delle celle visitate
	private static LinkedList<Cell> run_path = new LinkedList<>();

	//posizione attuale del pg
	private static int[] cur_pg_pos = new int[2];
	//posizione precedente del pg
	private static int [] pre_pg_pos = new int[2];

	
	//##### metodo di risoluzione della partita #####
	
	/**
	 * 
	 * @param gm
	 * @param em
	 * @param pg_pos_pre
	 * @param pg_pos_cur
	 */
	public static int solveGame(GameMap gm, GameMap em) {
		//variabili ausiliarie
		LinkedList<Cell> adjacent_cells = new LinkedList<>();
		LinkedList<Cell> covered_cells = new LinkedList<>();

		//risultato della mossa
		int result = 0;
		//DA DOVE VENGO : la posizione corrente che avevo prima diventa quella precendente
		pre_pg_pos = cur_pg_pos;
		//DOVE SONO: cella attuale
		//si preleva la posizione del pg nella mappa di gioco
		cur_pg_pos = PlayableCharacter.getPGposition();
		//si prelevano gli indici di cella
		int icur = cur_pg_pos[0];
		int jcur = cur_pg_pos[1];
		//si preleva la cella corrente
		Cell current_cell = gm.getMapCell(icur,jcur);
		//contenuto della cella attuale
		CellStatus current_status = current_cell.getCellStatus();
		//System.out.println("cell "+current_status);
		
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
			return 0;
		}
		//##### TERMINE CASI DI USCITA #####
		
		//DOVE POSSO ANDARE: celle adiacenti
		findAdjacentCells(adjacent_cells, em, current_cell);
		//celle da visitare tra quelle adiacenti
		findCoveredCells(covered_cells, em, adjacent_cells);
		
		//##### CONTROLLO SULLA CELLA ATTUALE #####
		//si preleva il vettore dei sensori
		boolean [] current_sensors = current_cell.getSenseVector();
		//System.out.println(current_cell.senseVectorToString(false));
		//TODO trattare i due sensori separatamente
		//si controlla il sensore di pericolo o di nemico
		if(current_sensors[0] || current_sensors[1]) {
			//SONO IN UNA SITUAZIONE DI PERICOLO
			//tutte le celle adiacenti potrebbero contenere un pericolo
			//DA DOVE VENGO
			if(pre_pg_pos==null) {
				//PRIMA MOSSA: mi trovo nella prima e unica cella visitata
				for(Cell adjacent_cell: covered_cells) {
					//visito la prima cella a me sconosciuta contenuta nella lista 
					//delle celle da scoprire 
					//prelevo gli indici di questa cella
					int [] adj_cell_pos = adjacent_cell.getPosition();
					//prelevo le informazioni di questa cella dalla mappa di gioco
					Cell next_cell = gm.getMapCell(adj_cell_pos[0], adj_cell_pos[1]);
					//la aggiungo alla lista delle celle visitate
					run_path.add(next_cell);
					//si inserisce questa cella nella mappa di esplorazione
					em.getMapCell(adj_cell_pos[0], adj_cell_pos[1]).copyCellSpecs(next_cell);
					//si aggiorna la posizione attuale del pg nella classe apposita
					PlayableCharacter.setPGposition(adjacent_cell.getPosition());
					//si valuta la mossa appena compiuta
					result = solveGame(gm,em);
					//controllo sul risultato
					if(result == 1 || result == -1) {
						//FINE PARTITA
						return result;
					}
				}//for
				//NON CI SONO CELLE ADIACENTI OPPURE SONO TUTTE POTENZIALMENTE PERICOLOSE
				return -3;
			}//fi prima mossa
			//NON SONO ALLA PRIMA MOSSA e la cella corrente indica un pericolo nei dintorni
			return -2;
		}//fi sensori accesi
		else {
			//SONO IN UNA SITUAZIONE TRANQUILLA
			//le celle adiacenti non hanno pericoli
			for(Cell adjacent_cell: covered_cells) {
				//visito la cella corrente
				//prelevo gli indici di questa cella
				int [] indices = adjacent_cell.getPosition();
				//prelevo le informazioni di questa cella dalla mappa di gioco
				Cell next_cell = gm.getMapCell(indices[0], indices[1]);
				//la aggiungo alla lista delle celle visitate
				run_path.add(next_cell);
				//si inserisce questa cella nella mappa di esplorazione
				em.getMapCell(indices[0], indices[1]).copyCellSpecs(next_cell);
				//si aggiorna la posizione attuale del pg nella classe apposita
				PlayableCharacter.setPGposition(adjacent_cell.getPosition());
				//si valuta la mossa appena compiuta
				result = solveGame(gm,em);
				//TODO stack
				//System.out.println(result);
				//si controlla il risultato
				if(result == 1 || result == -1) {
					//FINE PARTITA
					return result;
				}
			}//for
			//NON CI SONO CELLE ADIACENTI OPPURE SONO TUTTE POTENZIALMENTE PERICOLOSE
			return -2;
		}//esle sensori spenti
	}
	
	
	
	
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
		//controllo della cella DOWN
		if(em.cellExists(i+1, j)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i+1,j);
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
		//controllo della cella RIGHT
		if(em.cellExists(i, j+1)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i, j+1);
			//si aggiunge alla lista
			list.add(cell);
		}//
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
			//si verifica se e' gia' stata visitata
			if(status==null || !status.equals(CellStatus.OBSERVED)) {
				//se non e' stata visitata, allora si aggiunge alla lista delle celle da scoprire
				//if(!list.contains(c))list.add(c);
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
	public static String runPathToString() {
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
			game_status = new String("danger - first move");
			break;
		default:
			break;
		}//end
		return game_status;
	}//printStatusMessage
	
}
