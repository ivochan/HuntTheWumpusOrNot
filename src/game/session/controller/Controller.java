package game.session.controller;
//serie di import
import game.session.configuration.Starter;
import game.session.score.Score;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
import game.structure.text.GameMessages;
/** class Controller
 * questa classe definisce i comandi di gioco, cioe' il modo in cui 
 * l'utente potra' interagire, facendo muovere il personaggio giocabile
 * nelle direzioni consentite.
 * @author ivonne
 */
public class Controller {

	//##### metodi per spostare il personaggio giocabile #####
	
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
	public static int movePG(Direction move, GameMap gm, GameMap ge) {
		//vettore della posizione successiva
		int [] cell_pos= new int[2];
		//variabile da restituire
		int status = 0;
		//si preleva la posizione del pg
		int [] pg_pos = PlayableCharacter.getPGposition();
		//si controlla il risultato della direzione scelta
		cell_pos = findCell(move, pg_pos);
		//la cella indicata da next_pos esiste
		if(checkCell(cell_pos, gm)) { 
			//si controlla il contenuto della cella in questione
			CellStatus cs = gm.getMapCell(cell_pos[0], cell_pos[1]).getCellStatus();
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
				System.out.println(Starter.trad_mex.get(CellStatus.FORBIDDEN));
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
				ge.getMapCell(pg_pos[0], pg_pos[1]).setCellStatus(CellStatus.OBSERVED);
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
			Score.updateScore(cs);
		}//fi indici di mossa corretti
		else { 
			//comando non valido, oppure la cella non esiste
			status = -2;
		}//esle
		//si restituisce il codice associato al tipo di mossa
		return status;
	}//movePG(Direction, GameMap, GameMap)
	
	/** metodo makeMove(int, GameMap, GameMap): void
	 * questo metodo effettua la mossa vera e propria nella mappa di gioco
	 * @param status: int, rappresenta il risultato della mossa effettuata;
	 * @param gm: GameMap, mappa di gioco;
	 * @param em: GameMap, mappa di esplorazione;
	 */
	public static void makeMove(int status, GameMap gm, GameMap em) {
		//si preleva la posizione del pg
		int [] pg_pos = PlayableCharacter.getPGposition();
		//realizzazione della mossa
		switch(status) {
			case -2:
				System.out.println("Comando non valido...\nRipeti la mossa!");
				break;
			case -1 : 
				System.out.println("Il passaggio e' bloccato da un sasso.\nRipeti la mossa!");
				break;
			case 0 : 
				//informazioni sulla posizione
				checkEnvironment(pg_pos, gm);
				break;
			case 1:
				//informazioni della cella in cui si e' mosso il pg
				Cell c = gm.getMapCell(pg_pos[0], pg_pos[1]);
				//info sullo stato
				CellStatus s = c.getCellStatus();
				//stampa del messaggio se nemico o pericolo
				System.out.println(Starter.trad_mex.get(s));
				System.out.println(GameMessages.looser);
				//fine della partita
				Starter.setGameStart(false);
				break;
			case 2:
				System.out.println("Wow!"+Starter.trad_mex.get(CellStatus.AWARD));
				System.out.println(GameMessages.winner);
				//fine della partita
				Starter.setGameStart(false);
				break;
			default: 
				break;
		}//end switch
	}//makeMove(int)
	
	/** metodo checkEnvironment(int[], GameMap): void
	 * questo metodo fornisce il contenuto del vettore dei sensori
	 * per la cella in cui si trova il pg, in modo da informare
	 * il giocatore sull'ambiente circostante.
	 * @parm pg_pos: int, vettore che contiene la posizione del pg
	 * 				 all'interno della mappa di gioco;
	 * @param gm: GameMap, mappa di gioco;
	 */
	private static void checkEnvironment(int[] pg_pos, GameMap gm) {
		//si fornisce il contenuto del vettore dei sensori
		//per la cella in cui si trova il pg
		//vettore dei sensori
		boolean [] sensors = new boolean[2];
		//stampa della posizione corrente del pg
		//System.out.println("Ti trovi nella cella ("+pg_pos[0]+','+pg_pos[1]+')');
		//si acquisiscono le informazioni del vettore dei sensori
		sensors=gm.getMapCell(pg_pos[0], pg_pos[1]).getSenseVector();
		//vicinanza del nemico
		if(sensors[0])System.out.println(Starter.trad_mex.
				get(CellStatus.ENEMY_SENSE));
		//vicinanza del pericolo
		if(sensors[1])System.out.println(Starter.trad_mex.
				get(CellStatus.DANGER_SENSE));
		//nessun tipo di pericolo
		if(!sensors[0] && !sensors[1])
			System.out.println(GameMessages.safe_place);
	}//checkEnvironment(int[], GameMap)
	
	/** metodo findCell(Direction, int[]): int[]
	 * questo metodo idenfica gli indici di cella in cui si vuole
	 * muovere il pg, calcolandoli a partire dalla posizione corrente.
	 * @param move:Direction, enum che rappresenta la direzione in cui 
	 * 						  si vuole compiere lo spostamento.
	 * @param pg_pos: int[], vettore che contiene la posizione attuale
	 * 						 del pg all'interno della mappa di gioco;
	 * @return cell_pos: int[], vettore della posizione che e' stata
	 * 						 calcolata a partire dalla posizione corrente
	 * 						 del pg.
	 */
	public static int[] findCell(Direction move, int[] pg_pos){
		//vettore della posizione da restituire
		int [] cell_pos= new int[2];
		//si prelevano gli indici della posizione del pg
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
			default: 
				break;
		}//end switch
		//si memorizzano questi indici nel vettore della posizione successiva
		cell_pos[0]=im;
		cell_pos[1]=jm;
		//System.out.println("verifica della prossima posizione "+im+" e "+jm);
		return cell_pos;
	}//findCell(Direction , int[])
	
	/** metodo checkCell(int[], GameMap): boolean
	 * questo metodo si occupa di controllare che la cella in cui
	 * si vuole fare la prossima mossa, dati gli indici contenuti
	 * nel vettore che descrive la prossima posizione del pg.
	 * @param cell_pos: int[], vettore che contiene l'indice riga e colonna
	 * 						   della cella che e' stata calcolata come successiva;
	 * @param gm: GameMap, e' la mappa che contiene gli elementi di gioco.
	 * @return true, se la cella descritta dagli indici di riga e colonna
	 * 				esiste, false altrimenti.
	 */
	public static boolean checkCell(int[] cell_pos, GameMap gm) {
		//variabili boolean ausiliarie
		boolean iok=false;
		boolean jok=false;
		//indice riga
		int i=cell_pos[0];
		//indice colonna
		int j=cell_pos[1];
		//controllo sull'indice riga
		if(i>=0 && i<gm.getRows())iok=true;
		//controllo sull'indice colonna
		if(j>=0 && j<gm.getColumns())jok=true;
		//la cella esiste se entrambi gli indici sono validi
		return (iok && jok);
	}//checkCell()
	
	//##### metodi per colpire il nemico #####
	
	/** metodo hitEnemy(Direction, GameMap, GameMap): void
	 * questo metodo controlla la direzione in cui si vuole provare
	 * a colpire il nemico, verifica se la cella indicata dall'utente
	 * esiste e verifica se in essa sia posizionato il nemico oppure no.
	 * Se il colpo va a segno, il giocatore guadagna punti, altrimenti
	 * ha perso l'unico tentativo di poterlo sconfiggere.
	 * @param dir: Direction, direzione in cui si vuole lanciare il colpo;
	 * @param gm: GameMap, mappa che rappresenta il terreno di gioco;
	 * @param em: GameMap, mappa di esplorazione, visibile all'utente;
	 * @return defeated: boolean, indica se il nemico e' stato colpito o meno
	 */
	public static void hitEnemy(Direction dir, GameMap gm) {
		//si preleva la posizione attuale del pg
		int [] pg_pos = PlayableCharacter.getPGposition();
		//flag per indicare se il nemico e' stato colpito
		boolean defeated;
		//vettore degli indici di cella
		int[] enemy_indices=new int[2];
		//variabili ausiliarie per la validita' degli indici
		boolean iok=false;
		boolean jok=false;
		//si preleva la direzione in cui si vuole colpire
		enemy_indices = findCell(dir, pg_pos);
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
			//si cerca se il nemico si trova in una delle celle in questa direzione
			defeated = searchForEnemy(i, j, dir, gm);
			//si controlla se e' stato colpito
			if(defeated) {
				//colpo andato a segno
				System.out.println(GameMessages.hit);
				//si aggiornano i sensori
				resetEnemySensor(gm);
				//si aggiorna il punteggio
				Score.hitScore();
			}//fi
			else {
				//colpo errato
				System.out.println(GameMessages.wasted_shot);
			}//esle
		}//fi indici cella
		else {
			//gli indici di cella non sono validi
			System.out.println(GameMessages.failed_shot);
		}//esle
	}//hitEnemy()
	
	/** metodo searchForEnemy(int, int, Direction, GameMap): boolean
	 * questo metodo verifica se e' presente il nemico in una
	 * delle celle che si trovano nella direzione indicata.
	 * @param i: int, indice di riga della cella da verificare;
	 * @param j: int, indice di colonna della cella da verificare;
	 * @param dir: Direction, direzione in cui si e' scelto di colpire;
	 * @param gm: GameMap, mappa di gioco;
	 * @return true, se il nemico e' stato colpito, false altrimenti.
	 */
	public static boolean searchForEnemy(int i, int j, Direction dir, GameMap gm) {
		//vettore per gli indici della  cella da verificare
		int [] cell = new int[2];
		//si assegnano gli indici
		cell[0] = i;
		cell[1] = j;
		//si verifica se la cella attuale contiene il nemico
		while(gm.cellExists(cell[0], cell[1])) {
			//si esamina la cella attuale
			CellStatus cs = gm.getMapCell(cell[0], cell[1]).getCellStatus();
			//si controlla se contiene il nemico
			if(cs.equals(CellStatus.ENEMY)) {
				//il nemico e' stato colpito percio' si elimina dalla mappa
				gm.setMapCell(cell[0], cell[1], CellStatus.SAFE);
				//il metodo termina con successo
				return true;
			}//fi
			else if(cs.equals(CellStatus.FORBIDDEN)) {
				//il colpo e'stato bloccato dal sasso quindi il metodo termina
				System.out.println("Caspita, hai preso un sasso in pieno!");
				return false;
			}//fi
			else {
				//si calcolano gli indici della prossima cella da verificare
				cell = findCell(dir, cell);
			}//esle
		}//end while
		return false;
	}//searchForEnemy(int, int, Direction, GameMap)
	
	/** metodo resetEnemySensor(GameMap): void
	 * questo metodo azzera il contenuto del sensore
	 * relativo al nemico, per ogni cella della mappa,
	 * dopo che il nemico e' stato colpito.
	 * @param gm: GameMap, mappa di gioco.
	 */
	public static void resetEnemySensor(GameMap gm) {
		//si iterano le righe della mappa
		for(int i=0;i<gm.getRows(); i++) {
			//si iterano le colonne della mappa
			for(int j=0;j<gm.getColumns();j++) {
				//si preleva la cella corrente
				Cell c = gm.getMapCell(i, j);
				//si resetta il sensore
				c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(), false);
			}//for colonne
		}//for righe
	}//resetEnemySensor(GameMap)
	
}//end Controller
