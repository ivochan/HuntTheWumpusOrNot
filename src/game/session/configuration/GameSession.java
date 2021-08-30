package game.session.configuration;
//serie di import
import java.util.Scanner;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.session.score.Score;
import game.session.score.ScoreMemo;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
import game.structure.text.GameMessages;
import game.session.start.LinkStart;
/** class GameSession
 * questa classe implementa la sessione di gioco
 * avviata nella classe LinkStart
 * @author ivonne
 */
public class GameSession {
	//##### attributi di classe #####
	
	//mappa di gioco
	private static GameMap gm;
	//mappa di esplorazione
	private static GameMap em;	
	//punteggio
	private static Score score;
	//nome del giocatore
	private static String player_name;
	
	/** metodo start(): void
	 * questo metodo avvia la sezione di gioco, preparando il terreno
	 * su cui si svolgera' la partita, popolando la mappa di tutti
	 * gli elementi.
	 */
	public static void start() {
		//intro al gioco
		System.out.println("Link... Start-o!");
		//scelta della modalita' di gioco
		Starter.chooseGameMode();
		//flag avvio partita
		Starter.setGameStart(true);
		//flag disponibilta' del colpo
		Starter.setChanceToHit(true);
		//intro al pg
		System.out.println(Starter.trad_mex.get(CellStatus.PG));
		//creazione della mappa di esplorazione
		em = new GameMap(); 
		//creazione della mappa di gioco
		gm = new GameMap();
		//si preleva il nome del giocatore
		player_name = LinkStart.getPlayerName();
		//si inizializza il punteggio
		score = new Score(player_name);
		//riempimento della mappa della mappa
		MapConfiguration.init(gm, em);
		//DEBUG: stampa della mappa di gioco
		System.out.println(gm.gameMaptoString());
		//stampa per l'utente
		System.out.println("Preparazione del terreno di gioco....\n");
		//mappa di esplorazione
		System.out.println(em.mapToStringAndLegend());
		//si visualizzano le informazioni iniziali per la partita
		startInfo();
		//si inizializza il file
		ScoreMemo.createScoreFile();
	}//start()
	
	/** metodo startInfo(): void
	 * si visualizzano a video tutte le informazioni iniziali relative
	 * alla posizione del personaggio giocabile sulla mappa
	 */
	private static void startInfo() {
		//stampa della posizione del pg
		System.out.println("Ti trovi nella cella "+PlayableCharacter.getPGpositionToString());
		//si prelevano gli indici della cella in cui si trova il pg
		int [] pg_pos = PlayableCharacter.getPGposition();
		//si preleva la cella associata a questi indici
		Cell pg_cell = gm.getMapCell(pg_pos[0],pg_pos[1]);
		//si preleva il vettore dei sensori
		boolean [] sensors = pg_cell.getSenseVector();
		//si stampa lo stato del sensore del nemico
		if(sensors[0])System.out.println(Starter.trad_mex.get(CellStatus.ENEMY_SENSE));
		//si stampa lo stato del sensore del pericolo
		if(sensors[1])System.out.println(Starter.trad_mex.get(CellStatus.DANGER_SENSE));
		//se i sensori sono entrambi false si stampa il seguente messaggio
		if(!sensors[0] && !sensors[1])System.out.println(Starter.trad_mex.get(CellStatus.SAFE));
		//riga vuota
		System.out.println();
	}//startInfo()
	
	/** metodo play(): void
	 * questo metodo si occupa di scegliere la modalita' in cui
	 * giocare e di gestire le mosse effettuate dal giocatore.
	 */
	public static void play() {
		//variabile ausiliaria per il colpo
		Direction dir;
		//variabile asusiliaria per la mossa
		Direction move;
		//variabile ausiliaria per lo stato della mossa
		int status = 0;
		//avvio della partita
		while(Starter.getGameStart()){
			//acquisizione dell'azione del giocatore
			Starter.chooseMove();
			//si verifica se si vuole sparare
			if(Starter.getTryToHit()) {
				//si verifica se si ha un colpo a disposizione
				if(Starter.getChanceToHit()) {
					//si chiede la direzione in cui sparare
					Starter.chooseDirection();
					//si preleva la direzione
					dir = Starter.getShotDir();
					//si tenta il colpo
					Controller.hitEnemy(dir, gm);
					//il colpo a disposizione e' stato utilizzato
					Starter.setChanceToHit(false);
				}//fi
				//non si ja piu' la possibilita' di colpire
				else {
					System.out.println(GameMessages.no_hit);
				}//esle
				//reset flag relativo al tentare il colpo
				Starter.setTryToHit(false);
			}//fi
			//si verifica se si deve spostare il personaggio
			if(Starter.getWalk()) {
				//si preleva la direzione in cui muovere il pg
				move = Starter.getPGmove();
				//verifica dell'azione
				status = Controller.movePG(move, gm, em);
				//si effettua la mossa
				Controller.makeMove(status, gm, em);
				//si stampa la mappa con la legenda
				if(status==0)System.out.println(em.mapToStringAndLegend());
				//reset flag
				Starter.setWalk(false);
			}//fi
			//punteggio parziale
			System.out.println("Punteggio attuale: "+score.printScore());
		}//end while sessione di gioco
	}//play()
	
	/** metodo end(): void
	 * questo metodo si occupa delle operazioni che devono essere
	 * eseguite al termine della partita.
	 */
	public static void end() {
		System.out.println("THE E.N.D.");
		//stato della partita attuale
		Starter.setGameStart(false);
		//si resetta la disponibilita' del colpo
		Starter.setChanceToHit(true);
		//punteggio
		System.out.println("Questo e' il tuo punteggio: "+score.getScore());
		//si memorizza il punteggio
		ScoreMemo.saveScore(score.toString());
		//pulizia della console
		clearConsole();
	}//end()
	
	/** metodo clearConsole(): void
	 * utilizzato per pulire la console dopo ogni stampa 
	 * della matrice di gioco
	 */
	private static void clearConsole() {
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
	}//clearConsole()
	
}//end GameSession
