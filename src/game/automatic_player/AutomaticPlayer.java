package game.automatic_player;
//serie di import
import java.util.Collections;
import java.util.LinkedList;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
/** class AutomaticPlayer
 * questa classe implementa un agente, un giocatore automatico
 * che risolvera' la partita tenendo conto dei sensori
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
		return solvingGameFirstStrategy(cur_pg_pos, cur_pg_pos, gm, em, run_path);
	}//end solveGame
	
	/** solvingGameFirstStrategy(int[],int[],GameMap,GameMap,LinkedList): int
	 * 
	 * @param pre_pg_pos
	 * @param cur_pg_pos
	 * @param gm
	 * @param em
	 * @return
	 */
	private static int solvingGameFirstStrategy(int[] pre_pg_pos, int[] cur_pg_pos, GameMap gm, GameMap em, LinkedList<Cell> run_path) {
		//variabili ausiliarie per le liste di celle
		LinkedList<Cell> face_up_cells = new LinkedList<>();
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
		
		//##### TENTATIVO DI SPARO #####
		//controllo se e' acceso il sensore del nemico
		if(current_sensors[CellStatus.ENEMY_SENSE.ordinal()] && chance_to_hit>0) {
			//variabile ausiliaria per la lista di celle
			LinkedList<Cell> adjacent_list = new LinkedList<>();
			//considero la cella in cui mi trovo
			//prendo le celle ad essa adiacenti
			findFaceUpAdjacentCells(face_up_cells, gm, current_cell);
			//elimino la cella da cui provengo perche' e' sicura
			face_up_cells.remove(gm.getMapCell(pre_pg_pos[0], pre_pg_pos[1]));
			//scorro le celle adiacenti a quella attuale, in cui mi trovo
			for(Cell c: face_up_cells) {
				//prelevo le celle adiacenti e visitate
				findFaceUpAdjacentCells(adjacent_list, gm, c);
				//itero queste celle scoperte ed adiacenti
				for(Cell adj: adjacent_list) {
					//prelevo il vettore dei sensori
					boolean [] adj_sensor = adj.getSenseVector();
					//controllo se ognuna di queste celle ha il sensore del nemico acceso
					if(adj_sensor[CellStatus.ENEMY_SENSE.ordinal()] && chance_to_hit>0) {
						//la cella c allora e' quella verso cui sparare
						//si decrementano le munizioni
						chance_to_hit--;
						//prendo la posizione della cella verso cui sparare
						int[] hit_pos = c.getPosition();
						//prelevo lo stato di questa cella dalla mappa di gioco	
						CellStatus hit_status = gm.getMapCell(hit_pos[0], hit_pos[1]).getCellStatus();
						//provo a sparare
						System.out.println("Mi trovo in: "+'('+cur_pg_pos[0]+','+cur_pg_pos[1]+')');
						System.out.println("Sparo verso: "+'('+hit_pos[0]+','+hit_pos[1]+')');
						//si verifica se contiene il nemico
						if(hit_status.equals(CellStatus.ENEMY)) {
							//il nemico e' stato colpito
							System.out.println("Nemico colpito");
							//la cella diventa safe
							gm.getMapCell(hit_pos[0],hit_pos[1]).setCellStatus(CellStatus.SAFE);
							//si spengono tutti i sensori del nemico delle celle adiacenti a c
							updateEnemySensor(gm, hit_pos);
						}//fi nemico colpito
						else {
							System.out.println("Nemico mancato");
						}//else
					}//fi sensore nemico e possibilita' di colpire;
				}//for celle adiacenti a quelle adiacenti di quella del pg
			}//for celle adiacenti a quella corrente del pg
		}//fi tentativo di sparo
		
		//##### SUPERAMENTO DEL PERICOLO #####
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
					//DEBUGG
					//System.out.println("add pericolo "+next_pos[0]+','+next_pos[1]);
					
					//aggiornare la posizione del pg
					//PlayableCharacter.setPGposition(next_pos);//cella attuale
					PlayableCharacter.setPGposition(cur_pg_pos);//cella precedente
					
					//si inserisce questa cella nella mappa di esplorazione
					em.getMapCell(next_pos[0], next_pos[1]).copyCellSpecs(next_cell);
					//si valuta la mossa asuccessiva
					result = solvingGameFirstStrategy(cur_pg_pos, next_pos, gm,em, run_path);
					
					//controllo sul risultato: FINE PARTITA
					if(result == 1 || result == -1) return result;
				}//for
				
				//NON CI SONO CELLE ADIACENTI non visitate OPPURE SONO TUTTE POTENZIALMENTE PERICOLOSE
							
				
				//si aggiorna il percorso: ritorno alla cella precedente
				//TODO superfluo
				//run_path.add(gm.getMapCell(pre_pg_pos[0], pre_pg_pos[1]));
								
				return -3;
				
			}//fi prima mossa
			
			//NON SONO ALLA PRIMA MOSSA e la cella corrente indica un pericolo nei dintorni	
	
			//si aggiorna il percorso: ritorno alla cella precedente
			//System.out.println("pre "+pre_pg_pos[0]+','+pre_pg_pos[1]);
			//TODO
			run_path.add(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
			
			//aggiornare la posizione del pg 
			PlayableCharacter.setPGposition(pre_pg_pos);//cella precedente
			
			//TODO
			//si inserisce nella mappa di esplorazione
			em.getMapCell(pre_pg_pos[0], pre_pg_pos[1]).copyCellSpecs(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
			
			return -2;
			
		}//fi sensori accesi					
		
		else {	
			//SONO IN UNA SITUAZIONE TRANQUILLA
			
			//le celle adiacenti non hanno pericoli, sono apposto
			//quindi si sceglie una cella qualsiasi tra quelle coperte
			for(Cell adjacent_cell: covered_cells) {
			//for(Cell adjacent_cell: adjacent_cells) {
				//visito la cella corrente
				//prelevo gli indici di questa cella
				next_pos = adjacent_cell.getPosition();
				//prelevo le informazioni di questa cella dalla mappa di gioco
				Cell next_cell = gm.getMapCell(next_pos[0], next_pos[1]);
				//si aggiunge la cella corrente al ercorso delle celle visitate
				run_path.add(next_cell);
				//si inserisce questa cella nella mappa di esplorazione
				em.getMapCell(next_pos[0], next_pos[1]).copyCellSpecs(next_cell);
				
				//aggiornare la posizione del pg
				//PlayableCharacter.setPGposition(next_pos);//cella attuale
				PlayableCharacter.setPGposition(cur_pg_pos);//cella precedente
				
				//si valuta la mossa successiva
				result = solvingGameFirstStrategy(cur_pg_pos, next_pos, gm,em, run_path);
	
				//si controlla il risultato: FINE PARTITA
				if(result == 1 || result == -1) return result;
			}//for
			
			//NON CI SONO CELLE ADIACENTI OPPURE SONO TUTTE POTENZIALMENTE PERICOLOSE
			
			//STRATEGIA DI EMERGENZA: scelta di un nodo casuale
			//sono sul punto iniziale

			//si aggiorna il percorso: ritorno alla cella precedente
			//TODO
			//run_path.add(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
		
			
			if(pre_pg_pos[0]==cur_pg_pos[0] && pre_pg_pos[1]==cur_pg_pos[1]) {
					
				for(Cell adjacent_cell: adjacent_cells) {
					//DEBUGG
					System.out.println("Situazione attuale:\n"+em);
					//visito la prima cella a me sconosciuta contenuta nella lista 
					//delle celle da scoprire 
					next_pos = adjacent_cell.getPosition();
					//prelevo le informazioni di questa cella dalla mappa di gioco
					Cell next_cell = gm.getMapCell(next_pos[0], next_pos[1]);
					//si aggiunge la cella corrente al ercorso delle celle visitate
					run_path.add(next_cell);
					
					//si inserisce questa cella nella mappa di esplorazione
					em.getMapCell(next_pos[0], next_pos[1]).copyCellSpecs(next_cell);
					
					//aggiornare la posizione del pg
					//PlayableCharacter.setPGposition(next_pos);//cella attuale
					PlayableCharacter.setPGposition(cur_pg_pos);//cella precedente
					
					//si valuta la mossa asuccessiva
					result = solvingGameEmergencyStrategy(cur_pg_pos, next_pos, gm,em, run_path);
					
					//controllo sul risultato: FINE PARTITA
					if(result == 1 || result == -1) return result;
				}//for
				
			}	
			
			run_path.add(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
		
			//TODO
			//si inserisce nella mappa di esplorazione
			em.getMapCell(pre_pg_pos[0], pre_pg_pos[1]).copyCellSpecs(gm.getMapCell(pre_pg_pos[0],pre_pg_pos[1]));
			
			//aggiornare la posizione del pg
			PlayableCharacter.setPGposition(pre_pg_pos);//cella precedente
			
			return -4;
		}//esle sensori spenti
	}
	
		
	
	private static int solvingGameEmergencyStrategy(int[] pre_pg_pos, int[] cur_pg_pos, GameMap gm, GameMap em, LinkedList<Cell> run_path) {
		//variabili ausiliarie per le liste di celle
		LinkedList<Cell> face_up_cells = new LinkedList<>();
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
				
		//##### TENTATIVO DI SPARO #####
		
		//controllo se e' acceso il sensore del nemico
		if(current_sensors[CellStatus.ENEMY_SENSE.ordinal()] && chance_to_hit>0) {
			//variabile ausiliaria per la lista di celle
			LinkedList<Cell> adjacent_list = new LinkedList<>();
			//considero la cella in cui mi trovo
			//prendo le celle ad essa adiacenti
			findFaceUpAdjacentCells(face_up_cells, gm, current_cell);
			//elimino la cella da cui provengo perche' e' sicura
			face_up_cells.remove(gm.getMapCell(pre_pg_pos[0], pre_pg_pos[1]));
			//scorro le celle adiacenti a quella attuale, in cui mi trovo
			for(Cell c: face_up_cells) {
				//prelevo le celle adiacenti e visitate
				findFaceUpAdjacentCells(adjacent_list, gm, c);
				//itero queste celle scoperte ed adiacenti
				for(Cell adj: adjacent_list) {
					//prelevo il vettore dei sensori
					boolean [] adj_sensor = adj.getSenseVector();
					//controllo se ognuna di queste celle ha il sensore del nemico acceso
					if(adj_sensor[CellStatus.ENEMY_SENSE.ordinal()] && chance_to_hit>0) {
						//la cella c allora e' quella verso cui sparare
						//si decrementano le munizioni
						chance_to_hit--;
						//prendo la posizione della cella verso cui sparare
						int[] hit_pos = c.getPosition();
						//prelevo lo stato di questa cella dalla mappa di gioco	
						CellStatus hit_status = gm.getMapCell(hit_pos[0], hit_pos[1]).getCellStatus();
						//provo a sparare
						System.out.println("Mi trovo in: "+'('+cur_pg_pos[0]+','+cur_pg_pos[1]+')');
						System.out.println("Sparo verso: "+'('+hit_pos[0]+','+hit_pos[1]+')');
						//si verifica se contiene il nemico
						if(hit_status.equals(CellStatus.ENEMY)) {
							//il nemico e' stato colpito
							System.out.println("Nemico colpito");
							//la cella diventa safe
							gm.getMapCell(hit_pos[0],hit_pos[1]).setCellStatus(CellStatus.SAFE);
							//si spengono tutti i sensori del nemico delle celle adiacenti a c
							updateEnemySensor(gm, hit_pos);
						}//fi nemico colpito
						else {
							System.out.println("Nemico mancato");
						}//else
					}//fi sensore nemico e possibilita' di colpire;
				}//for celle adiacenti a quelle adiacenti di quella del pg
			}//for celle adiacenti a quella corrente del pg
		}//fi tentativo di sparo
				
		//##### SUPERAMENTO DEL PERICOLO #####		

		//si sceglie una cella a caso
		Cell random_cell;
		//
		if(!covered_cells.isEmpty()) {
			int random_index = (int)(Math.random()*covered_cells.size());
			System.out.println("Random: "+random_index);
			random_cell = covered_cells.get(random_index);
		}
		else {
			random_cell = adjacent_cells.getFirst();
			//System.out.println("adjacent");
		}
		//si estrae una cella casuale in cui forzare la mossa
		next_pos = random_cell.getPosition();
		//DEBUGG
		//System.out.println("sono in: "+cur_pg_pos[0]+','+cur_pg_pos[1]);
		//System.out.println("scelta: "+next_pos[0]+','+next_pos[1]);
		//prelevo le informazioni di questa cella dalla mappa di gioco
		Cell next_cell = gm.getMapCell(next_pos[0], next_pos[1]);
		//si aggiunge la cella corrente al ercorso delle celle visitate
		run_path.add(next_cell);
		//System.out.println("add "+next_pos[0]+','+next_pos[1]);
		em.getMapCell(next_pos[0], next_pos[1]).copyCellSpecs(next_cell);
		
		//aggiornare la posizione del pg
		//PlayableCharacter.setPGposition(next_pos);//cella attuale
		PlayableCharacter.setPGposition(cur_pg_pos);//cella precedente
		
		//si valuta la mossa successiva
		result = solvingGameEmergencyStrategy(cur_pg_pos, next_pos, gm,em, run_path);
		//si controlla il risultato: FINE PARTITA
		if(result == 1 || result == -1) return result;
		//codice di uscita
		return -5;		
	}
		

	/** metodo printStatusMessage(int)
	 * questo metodo stampa una stringa indicativa del codice di
	 * uscita con cui e' terminato il metodo di risoluzione
	 * @param number: int
	 * @return game_status: String
	 */
	public static String printStatusMessage(int number) {
		//variabile ausiliaria 
		String game_status = new String("processing");
		//switch case
		switch(number){
		//casi possibili
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
			game_status = new String("danger sensor");
			break;
		case -3:
			game_status = new String("danger sensor - first move");
			break;
		case -4:
			game_status = new String("danger sensor - sensors off");
			break;
		case -5:
			game_status = new String("EmergencyStrategy Exit Code");
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
		//si svuota la lista
		list.clear();
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
		//shuffle
		Collections.shuffle(list);
		/*
		String cells = new String("");
		for(Cell ce:adjacent_cells) {
			cells += ce.positionToString();
		}
		System.out.println(cells);
		*/
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
	
	/** metodo findFaceUpAdjacentCells(Cell)
	 * questo metodo ricerca tutte le celle adiacenti a quella data
	 * e le inserisce in una lista, dopo aver verificato che esistano
	 * nella mappa di gioco che e' stata generata e che siano gia' state visitate
	 * @param em: GameMap, mappa di esplorazione, conosciuta dal giocatore
	 * @param c: Cell, cella da cui partire per cercare quelle ad essa adiacenti
	 * @return
	 */
	private static void findFaceUpAdjacentCells(LinkedList<Cell> list, GameMap em, Cell c) {
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
			//si verifica se e' stata visitata
			if(!cell.getCellStatus().equals(CellStatus.UNKNOWN)) {
				//si aggiunge alla lista
				list.add(cell);
			}
		}//fi
		//controllo della cella LEFT
		if(em.cellExists(i, j-1)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i, j-1);
			//si verifica se e' stata visitata
			if(!cell.getCellStatus().equals(CellStatus.UNKNOWN)) {
				//si aggiunge alla lista
				list.add(cell);
			}
		}//fi
		//controllo della cella DOWN
		if(em.cellExists(i+1, j)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i+1,j);
			//si verifica se e' stata visitata
			if(!cell.getCellStatus().equals(CellStatus.UNKNOWN)) {
				//si aggiunge alla lista
				list.add(cell);
			}
		}//fi
		//controllo della cella RIGHT
		if(em.cellExists(i, j+1)) {
			//si preleva la cella in esame
			cell = em.getMapCell(i, j+1);
			//si verifica se e' stata visitata
			if(!cell.getCellStatus().equals(CellStatus.UNKNOWN)) {
				//si aggiunge alla lista
				list.add(cell);
			}
		}//fi
	}//findAdjacentCells
	
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
			run_list += "("+position[0]+','+position[1]+") ";
			//run_list += "("+position[0]+','+position[1]+")\n";
		}//end for
		return run_list;
	}//runPathToString()
	
	/** metodo updateEnemySensor(GameMap, int[]): void
	 * questo metodo aggiorna i sensori del nemico per tutte le celle
	 * adiacenti a quella che lo conteneva, dopo che il nemico e' stato
	 * eliminato, mettendo false come valore.
	 * @param gm
	 * @param enemy_indices
	 */
	private static void updateEnemySensor(GameMap gm, int[] enemy_indices) {
		//variabile ausiliaria
		Cell c;
		//si prelevano gli indici del nemico
		int ie = enemy_indices[0];
		int je = enemy_indices[1];
		//si specifica il vettore dei sensori per le celle attorno al nemico
		if(je>0) {
			//System.out.println("cella "+ie+","+(je-1));
			c = gm.getMapCell(ie, je-1);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(), false);
			//System.out.println(c.senseVectorToString(false));
		}// fi cella a SINISTRA
		if(ie>0) {
			//System.out.println("cella "+(ie-1)+","+je);
			c = gm.getMapCell(ie-1, je);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(), false);
			//System.out.println(c.senseVectorToString(false));
		}//fi cella in ALTO
		if(je<3) {
			//System.out.println("cella "+ie+","+(je+1));
			c = gm.getMapCell(ie, je+1);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(), false);
			//System.out.println(c.senseVectorToString(false));
		}//fi cella a DESTRA
		if(ie<3) {
			//System.out.println("cella "+(ie+1)+","+je);
			c = gm.getMapCell(ie+1, je);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(), false);
			//System.out.println(c.senseVectorToString(false));
		}//fi cella in BASSO
		
	}//enemySensor(Map, int[])
	
	/** metodo updateExplorationMap(GameMap): void
	 * questo metodo aggiorna la mappa di gioco in modo che il pg venga
	 * visualizzato nella cella immediatamente precedente all'ultima in
	 * cui e' stata effettuata la mossa ed in modo che le celle visitate
	 * siano mostrate come OBSERVED
	 * @param em: GameMap, mappa di esplorazione.
	 */
	public static void updateExplorationMap(GameMap em) {
		//si prende la mappa di esplorazione e si itera
		for(int i=0;i<em.getRows();i++) {
			for(int j=0;j<em.getColumns();j++) {
				//si preleva lo status della cella corrente
				CellStatus cs = em.getMapCell(i,j).getCellStatus();
				//si controlla lo stato della cella corrente
				if(!cs.equals(CellStatus.UNKNOWN)) {
					//se la cella e' stata visitata
					if(cs.equals(CellStatus.SAFE) || cs.equals(CellStatus.PG)) {
						//tutte le celle esistenti (non vuote) ma SAFE
						//oppure la cella che contiene il PG all'inizio
						//vengono etichettate come OBSERVED
						em.getMapCell(i,j).setCellStatus(CellStatus.OBSERVED);
					}
				}		
			}//for colonne
		}//for righe
		//si inserisce nella mappa la penultima posizione del pg prima della fine della partita
		int[] pg_pos = PlayableCharacter.getPGposition();
		//si cambia lo status della cella corrispondente
		em.getMapCell(pg_pos[0],pg_pos[1]).setCellStatus(CellStatus.PG);	
	}//updateExplorationMap(GameMap)
	
	
}//end AutomaticPlayer

