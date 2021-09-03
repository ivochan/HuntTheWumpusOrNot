package game.player.agent.app;
//serie di import
import java.util.LinkedList;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
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
	int[] pg_position = new int[2];
	
	//lista contenente la coppia di indici di ogni cella visitata
	private LinkedList<Cell> run = new LinkedList<Cell>();
	
	//flag che indica il completamento della risoluzione
	private boolean gameSolved;
	//flag che indica il termine della risoluzione della partita per aggiornare la grafica
	private boolean automaticSessionEnded;
	//flag per la disponibilita' delle munizioni per lo sparo
	private boolean automaticChanceToHit;
	
	//##### costruttore di classe #####
	
	/** costruttore AutomaticAgent(GameMap, GameMap)
	 * 
	 * @param gm
	 * @param em
	 */
	public AutomaticAgent() {
		//si resetta il flag della sessione di gioco automatica
		setAutomaticSessionEnded(false);
		//flag di displonibilita' del colpo
		setAutomaticChanceToHit(true);
		//debug
		//shot= " shot: ";
		//posizione del pg
		pg_position  =PlayableCharacter.getPGposition();
	}//end AutomaticAgent(GameMap, GameMap)
	
	//##### metodo per la scelta della mossa ######
	
	/** metodo chooseMove(GameMap, GameMap): Direction
	 * questo metodo si occupa di scegliere la mossa 
	 * da effettuare, sulla base del contenuto dei sensori.
	 * @param em: GameMap, mappa di esplorazione;
	 * @param gm: GameMap, mappa di gioco;
	 */
	public void chooseGameMove(GameMap gm, GameMap em) {
		//vettore ausiliario per la posizione del pg
		int [] pg_position = new int[2];
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
		//debug
		System.out.println(current.getCellPosition());
		//debug
		System.out.println(current);
		//si preleva il contenuto del vettore dei sensori
		sensors = current.getSenseVector();
		//per debug
		sensorInfo = current.senseVectorToString(true);
		//DEBUG
		System.out.println(sensorInfo+"\n");
		//verifica del contenuto
		if(sensors[CellStatus.ENEMY_SENSE.ordinal()]) {
			//il nemico e' nelle vicinanze
			//DEBUG
			moveInfo="Il nemico e' in agguato!";
			//si verifica la disponibilita' del colpo
			if(automaticChanceToHit){
				//si sceglie la direzione in cui colpire
				dir = BasicAgent.chooseDirection(pg_position[0],pg_position[1],gm);
				//almeno una delle celle non e' stata visitata se il sensore e' acceso
				moveInfo="Tentativo di sparo verso "+dir;
				//si tenta il colpo
				//Controller.hitEnemy(dir, gm);
				//si resetta il flag
				automaticChanceToHit = false;
				shot+="\n shot yep ";
			}//fi
			else {
				//non si hanno munizioni
				moveInfo="non si hanno munizioni";
				//si sceglie la direzione in cui fare muovere il pg
				dir = BasicAgent.chooseDirection(pg_position[0], pg_position[1], gm);
				//si verifica il risultato della mossa
				status = BasicAgent.movePG(dir, gm, em);
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
			dir = BasicAgent.chooseDirection(pg_position[0], pg_position[1], gm);
			//si verifica il risultato della mossa
			status = BasicAgent.movePG(dir, gm, em);
			moveInfo+="\nmuovo verso "+dir;
			//si controlla la mossa
			s = makeMove(status, gm);
			moveInfo+="\n"+s;
			//aggiornamento del percorso
			if(status!=-1)
				BasicAgent.updateRunPath(run,gm.getMapCell(pg_position[0], pg_position[1]));
		}
		else {
			//entrambi i sensori sono spenti
			moveInfo="posto sicuro";
			//si sceglie una direzione a caso, tra quelle non esplorate
			dir = BasicAgent.chooseDirection(pg_position[0], pg_position[1], gm);
			//si verifica il risultato della della mossa
			status = BasicAgent.movePG(dir, gm, em);
			moveInfo+="\nmuovo verso "+dir;
			//si controlla la mossa
			s = makeMove(status, gm);
			moveInfo +="\n"+s;
			//aggiornamento del percorso
			if(status!=-1)
				BasicAgent.updateRunPath(run,gm.getMapCell(pg_position[0], pg_position[1]));
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
			setGameSolved(true);
			//flag di termine della sessione di gioco automatica
			setAutomaticSessionEnded(true);
			break;
		case 2:
			//hai trovato il premio
			s = "Hai vinto";
			//fine della partita
			setGameSolved(true);
			//flag di termine della sessione di gioco automatica
			setAutomaticSessionEnded(true);
			break;
		case -2:
			s = "Comando non valido...\nRipeti la mossa!";
			break;
		default:
			break;
		}//end switch
		return s;
	}//makeMove(int)

	//##### metodi accessori per i flag #####
		
	/** metodo isAutomaticSessionEnded(): boolean
	 *
	 * @return
	 */
	public boolean isAutomaticSessionEnded(){
		//si restituisce il flag
		return automaticSessionEnded;
	}//isAutomaticSessionEnded()

	/** metodo setEndAutomaticSessionEnded
	 *
	 * @param flag
	 * @return
	 */
	public void setAutomaticSessionEnded(boolean flag){
		//si assegna il flag all'attributo di classe
		automaticSessionEnded=flag;
	}//setEndAutomaticSession(boolean)

	/** metodo isGameSolved(): boolean
	 * 
	 * @return
	 */
	public boolean isGameSolved() {
		return gameSolved;
	}//isGameSolved()

	/** metodo setGameSolved(boolean): void
	 * 
	 * @param gameSolved
	 */
	public void setGameSolved(boolean gameSolved) {
		this.gameSolved = gameSolved;
	}//setGameSolved()

	/** metodo getAutomaticChanceToHit(): boolean
	 * 
	 * @return
	 */
	public boolean getAutomaticChanceToHit() {
		return automaticChanceToHit;
	}//getAutomaticChanceToHit()

	/** metodo setAutomaticChanceToHit(boolean): void
	 * 
	 * @param automaticChanceToHit
	 */
	public void setAutomaticChanceToHit(boolean automaticChanceToHit) {
		this.automaticChanceToHit = automaticChanceToHit;
	}//setAutomaticChanceToHit()
	
	//##### metodi accessori per la lista delle celle visitate #####
	
	/** metodo getRunPath(): LinkedList<Cell>
	 * questo metodo restituisce il percorso compiuto dal giocatore
	 * automatico, durante l'ultima partita.
	 */
	public LinkedList<Cell> getRunPath(){
		//si restituisce la lista del percorso compiuto
		return run;
	}//getRunPath()

}//end AutomaticAgent
