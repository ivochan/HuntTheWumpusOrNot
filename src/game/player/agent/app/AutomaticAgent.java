package game.player.agent.app;
//serie di import
import game.session.configuration.Starter;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
import game.structure.text.GameMessages;
/** class AutomaricAgent
 * questa classe implementa un giocatore automatico,
 * che si occupera' di risolvere il gioco, effettuando delle scelte
 * casuali, a parita' del contenuto dei sensori e verra' utilizzata nell'app
 * android
 * @author ivonne
 */
public class AutomaticAgent {
	//###### attributi di classe #####
	
	//posizione del pg
	private int[] pg_position = new int[2];

	//mappa di gioco
	private GameMap gm;
	//mappa di esplorazione
	private GameMap em;
	
	//flag che indica il completamento della risoluzione
	private boolean gameSolved;
	//flag che indica il termine della risoluzione della partita per aggiornare la grafica
	private boolean endAutomaticSession;
	//flag per la disponibilita' delle munizioni per lo sparo
	private boolean automaticChanceToHit;
	
	//##### costruttore di classe #####
	
	/**
	 * 
	 * @param gm
	 * @param em
	 */
	public AutomaticAgent(GameMap gm, GameMap em) {
		//si assegnano i parametri agli attributi di classe
		setGameMap(gm);
		setExpMap(em);
		//variabile temporanea per prelevare la posizione del pg
		int [] temp_pg_pos = PlayableCharacter.getPGposition();
		//si assegna questa posizione all'attributo di classe
		this.pg_position[0] = temp_pg_pos[0];
		this.pg_position[1] = temp_pg_pos[1];
		//si resetta il flag della sessione di gioco automatica
		setEndAutomaticSession(false);
        //flag di displonibilita' del colpo
        automaticChanceToHit = true;
        //debug
        //shot= " shot: ";
    }//end AutomaticAgent(GameMap, GameMap)
	
	//##### metodo per la scelta della mossa ######
	
	/** metodo chooseMove(GameMap, GameMap): Direction
	 * questo metodo si occupa di scegliere la mossa 
	 * da effettuare, sulla base del contenuto dei sensori.
	 * @param em: GameMap, mappa di esplorazione;
	 * @param gm: GameMap, mappa di gioco;
	 */
	public void chooseGameMove(GameMap gm, GameMap em, boolean automaticChanceToHit) {
		//variabili per debug
		String sensorInfo = new String("");
		String moveInfo = new String("");
		String shot = new String("");
		//variabile ausiliaria
		String s = new String("");
		//variabile ausiliaria per lo stato della mossa
		int status = 3;
		//variabile ausiliaria per la direzione
		Direction dir;
		//variabile ausiliaria per i sensori
		boolean[] sensors = new boolean[2];
		//si preleva la posizione del pg
		pg_position = PlayableCharacter.getPGposition();
		//si preleva la cella in cui si trova il pg nella matrice di esplorazione
		Cell current = em.getMapCell(pg_position[0],pg_position[1]);
		//si preleva il contenuto del vettore dei sensori
		sensors = current.getSenseVector();
		//per debug
		sensorInfo = current.senseVectorToString(true);
		System.out.println(sensorInfo);
		//verifica del contenuto
		if(sensors[CellStatus.ENEMY_SENSE.ordinal()]) {
			//il nemico e' nelle vicinanze
			moveInfo="Il nemico e' in agguato!";
			//si verifica la disponibilita' del colpo
			if(automaticChanceToHit){
				//si sceglie la direzione in cui colpire
				dir = chooseDirection(pg_position[0],pg_position[1],gm);
				//almeno una delle celle non e' stata visitata se il sensore e' acceso
				moveInfo="Tentativo di sparo verso "+dir;
				//si tenta il colpo
				Controller.hitEnemy(dir, gm);
				//si resetta il flag
				automaticChanceToHit = false;
				shot+="\n shot yep";
			}//fi
			else {
				//non si hanno munizioni
				moveInfo="non si hanno munizioni";
				//si sceglie la direzione in cui fare muovere il pg
				dir = chooseDirection(pg_position[0], pg_position[1], gm);
				//si verifica il risultato della mossa
				status = movePG(dir, gm, em);
				moveInfo+="\nmuovo verso "+dir;
				//si controlla la mossa
				s = makeMove(status, gm);
				moveInfo +="\n"+s;
				//aggiornamento del percorso
			}//else
		}//fi
		else if(sensors[CellStatus.DANGER_SENSE.ordinal()]) {
			//il pericolo e' vicino
			moveInfo="Il pericolo e' vicino...";
			//si preferisce come direzione una cella non visitata
			dir = chooseDirection(pg_position[0], pg_position[1], gm);
			//si verifica il risultato della mossa
			status = movePG(dir, gm, em);
			moveInfo+="\nmuovo verso "+dir;
			//si controlla la mossa
			s = makeMove(status, gm);
			moveInfo+="\n"+s;
				//aggiornamento del percorso
		}
		else {
			//entrambi i sensori sono spenti
			moveInfo="posto sicuro";
			//si sceglie una direzione a caso, tra quelle non esplorate
			dir = chooseDirection(pg_position[0], pg_position[1], gm);
			//si verifica il risultato della della mossa
			status = movePG(dir, gm, em);
			moveInfo+="\nmuovo verso "+dir;
			//si controlla la mossa
			s = makeMove(status, gm);
			moveInfo +="\n"+s;
			//aggiornamento del percorso
			//if(status!=-1)updateRunPath(gm.getMapCell(pg_pos[0], pg_pos[1]));
		}
		moveInfo+=shot;
		System.out.println(moveInfo);
	}//end chooseGameMove()
	
	/**makeMove(int, GameMap)
	 * 
	 * @param status
	 * @param gm
	 * @return
	 */
	private String makeMove(int status, GameMap gm) {
		//variabile ausilaria per la cella della mappa
		Cell c = new Cell();
		//variabile ausiliaria
		String s  =new String();
		//risultato della mossa effettuata
		switch(status) {
		//codici di uscita associati alla mossa
		case -1 :
			s = "Il passaggio e' bloccato da un sasso.\nRipeti la mossa!";
			break;
		case 0 :
			//informazioni sulla posizione
			s = "ti trovi in "+PlayableCharacter.getPGpositionToString();
			//si preleva la cella in cui si trova il pg
			c = gm.getMapCell(pg_position[0],pg_position[1]);
			//si visualizzano le informazioni dei sensori
			s +="\n"+c.senseVectorToString(true);
			break;
		case 1:
			//informazioni della cella in cui si e' mosso il pg
			c = gm.getMapCell(pg_position[0], pg_position[1]);
			//info sullo stato
			CellStatus cs = c.getCellStatus();
			//stampa del messaggio se nemico
			if(cs.equals(CellStatus.ENEMY)){
				s = "Hai perso, ti ha ucciso il nemico";
			}
			else {
				//stampa del messaggio se pericolo
				s = "Hai perso, sei caduto in trappola";
			}
			//fine della partita
			gameSolved=true;
			//flag di termine della sessione di gioco automatica
			setEndAutomaticSession(true);
			break;
		case 2:
			//hai trovato il premio
			s = "Hai vinto";
			//fine della partita
			gameSolved=true;
			//flag di termine della sessione di gioco automatica
			setEndAutomaticSession(true);
			break;
		case -2:
			s = "Comando non valido...\nRipeti la mossa!";
			break;
		default:
			break;
		}//end switch
		return s;
	}//makeMove(int)
	

		private static int movePG(Direction dir, GameMap gm, GameMap em) {
		// TODO Auto-generated method stub
		return 0;
	}






		/** metodo chooseDirection(int, int, GameMap)
		 * questo metodo serve a determinare la direzione in 
		 * cui muovere il personaggio giocabile, scegliendo in
		 * maniera casuale, una cella tra le direzioni consentite,
		 * a partire dalle cella in cui e' situato il pg.
		 * @param i: int, indice di riga della cella in cui si trova il pg;
		 * @param j: int, indice di colonna della cella in cui si trova il pg;
		 * @param em: GameMap, mappa di esplorazione, visibile al pg;
		 * @return dirn: Direction, direzione in cui si vuole spostare il pg;
		 */
		public static Direction chooseDirection(int i, int j, GameMap em) {
			//vettore ausiliario per le celle accessibili e non visitata
			boolean [] ok_cells = new boolean[4];
			//vettore ausiliario per le celle esistenti
			boolean [] cells = new boolean[4];
			//indice di cella casuale
			int random = 0;
			//variabile ausiliaria per l'indice di cella
			int index = 0;
			//si prelevano le direzioni possibili
			Direction [] directions = Direction.values();
			//si verificano le celle dispoonibili
			if(em.cellExists(i-1, j)) {
				//si calcola l'indice
				index = Direction.UP.ordinal();
				//la cella esiste
				cells[index] = true;
				//si verifica la cella e si aggiorna il vettore
				ok_cells[index] = verifyCell(i-1,j, em);
			}//UP
			if(em.cellExists(i+1, j)) {
				//si calcola l'indice
				index = Direction.DOWN.ordinal();
				//la cella esiste
				cells[index] = true;
				//si verifica la cella e si aggiorna il vettore
				ok_cells[index] = verifyCell(i+1,j, em);
			}//DOWN
			if(em.cellExists(i, j-1)) {
				//si calcola l'indice
				index = Direction.LEFT.ordinal();
				//la cella esiste
				cells[index] = true;
				//si verifica la cella e si aggiorna il vettore
				ok_cells[index] = verifyCell(i,j-1, em);
			}// LEFT
			if(em.cellExists(i, j+1)) {
				//si calcola l'indice
				index = Direction.RIGHT.ordinal();
				//la cella esiste
				cells[index] = true;
				//si verifica la cella e si aggiorna il vettore
				ok_cells[index] = verifyCell(i,j+1, em);
			}//RIGHT
			//controllo sul contenuto del vettore ok_cells
			if(checkCells(ok_cells)){
				//si sceglie casualmente una cella tra quelle idonee
				random = pickCell(ok_cells);
			}//fi
			else {
				//si prende una cella a caso tra quelle accessibili ma gia' visitate 
				random = pickCell(cells);
			}//else
			//si estrae la direzione scelta
			Direction dir = directions[random];
			//si restituisce la direzione
			return dir;
		}//chooseDirection(int, int, GameMap)

		/** metodo pickCell(boolean []): int
		 * questo metodo seleziona casualmente una cella
		 * tra quelle indicate come true, nel vettore ricevuto come
		 * parametro.
		 * @param vcells: boolean[], vettore delle celle tra cui
		 * 							 selezionare quella in cui effettuare
		 * 							 la mossa;
		 * @return random: int, indice della cella da estrarre.
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
		
		/** metodo verifyCell(int, int, GameMap): boolean
		 * questo metodo verifica se la cella in cui si vuole spostare
		 * il personaggio giocabile sia idonea o no, in base al suo contenuto.
		 * Infatti, per essere una scelta valida, la cella non deve essere ne'+
		 * un sasso ne' essere stata gia' visitata.
		 * @param i: int, indice di riga della cella in cui si trova il pg;
		 * @param j: int, indice di colonna della cella in cui si trova il pg;
		 * @param em: GameMap, mappa di esplorazione, visibile al pg;
		 * @return boolean: true, se la cella e' idonea, false altrimenti,. .
		 */
		public static boolean verifyCell(int i, int j, GameMap em) {
			//si preleva la cella
			CellStatus cs = em.getMapCell(i, j).getCellStatus();
			//si verifica che non e' un sasso
			if(!cs.equals(CellStatus.FORBIDDEN)) {
				//si verifica che la cella non e' stata visitata
				if(!cs.equals(CellStatus.OBSERVED)) {
					//la cella e' idonea
					return true;
				}//fi
				return false;
			}//fi
			//la cella non e' idonea
			return false;	
		}//verifyCell(int, int, GameMap)
		
		/** metodo checkCells(boolean []): boolean
		 * questo metodo verifica se questo vettore contiene almeno una
		 * cella che sia pari a true. Se cosi' non e' sceglie una cella
		 * a caso tra quelle gia' visitate.
		 * @param ok_cells: boolean [], vettore di celle risultate idonee
		 * @return check: boolean, questo flag sara' true se almeno una cella
		 * 						   del vettore e' risultata idonea, false altrimenti.
		 */
		public static boolean checkCells(boolean [] ok_cells) {
			//variabile ausiliaria per il controllo
			boolean check = false;
			//si itera il vettore
			for(int i=0; i<ok_cells.length;i++) {
				//System.out.println("cella "+i+" = "+ok_cells[i]);
				//si preleva il contenuto della cella
				if(ok_cells[i]) check = true;
			}//end for
			return check;
		}//checkCells(boolean [])
		
		
	    /**
	     *
	     * @return
	     */
	    public static boolean getEndAutomaticSession(){
	        //si restituisce il flag
	        return endAutomaticSession;
	    }//getEndAutomaticSession()

	    /**
	     *
	     * @param flag
	     * @return
	     */
	    public static void setEndAutomaticSession(boolean flag){
	        //si assegna il flag all'attributo di classe
	        endAutomaticSession=flag;
	    }//setEndAutomaticSession(boolean)



		//##### metodi per il percorso #####
		

		
		/** metodo updateRunPath(Cell): void
		 * questo metodo inserisce la cella ricevuta come parametro
		 * nella lista delle celle visitate durante la partita del
		 * giocatore automatico.
		 * @param c: Cell, cella della mappa di esplorazione che e'
		 * 					stata gia' visitata.
		 */
		@Override
		public void updateRunPath(Cell c) {
			//si richiama il metodo della super classe
			super.updateRunPath(c);
		}//updateRunPath(Cell)

		//##### metodi accessori per le mappe #####
		
		public GameMap getGameMap() {
			return gm;
		}//getGameMap()

		public void setGameMap(GameMap gm) {
			//controllo sui parametri
			if(gm == null) throw new IllegalArgumentException("mappa di gioco nulla");
			//assegnamento
			this.gm = gm;
		}//setGameMap()
		
		public GameMap getExpMap() {
			return em;
		}//getExpMap()
		
		public void setExpMap(GameMap em) {
			//controllo sui parametri
			if(em == null) throw new IllegalArgumentException("mappa di esplorazione nulla");
			//assegnamento
			this.em = em;
		}//setExpMap()
		
		//##### metodi accessori per i flag #####
		
}//end AutomaticAgent
