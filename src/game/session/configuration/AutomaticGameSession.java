package game.session.configuration;
import java.util.LinkedList;

import game.automatic_player.AutomaticPlayer;
//serie di import
import game.session.score.Score;
import game.session.score.ScoreUtility;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/** class AutomaticGameSession
 * classe che realizza una sessione di gioco
 * in cui la partita viene svolta utilizzando
 * un giocatore automatico
 * @author ivonne
 */
public class AutomaticGameSession {
	//##### attributi di classe #####
	
	//mappe di gioco
	private static GameMap gm;
	private static GameMap em;
	//punteggio
	//private static Score score;
	//nome del giocatore automatico
	//private static String ia_name = "AutomaticPlayer";	
	//nome del file dei punteggi
	//private static String score_file;
	//percorso compiuto
	private static LinkedList<Cell> run_path = new LinkedList<>();
		
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
		//Starter.setChanceToHit(true);
		//intro al pg
		System.out.println(Starter.trad_mex.get(CellStatus.PG));
		//creazione della mappa di esplorazione
		em = new GameMap(); 
		//creazione della mappa di gioco
		gm = new GameMap();
		//si inizializza il nome del file
		//score_file = new String(Starter.getPath());
		//si inizializza il punteggio
		//score = new Score(ia_name);
		//stampa
		System.out.println("Preparazione del terreno di gioco....\n");
		//creazione della mappa
		MapConfiguration.init(gm,em);
		//stampa della mappa
		System.out.println(gm.gameMapToString());
		//si inizializza il file
		//ScoreUtility.createScoreFile(score_file);
	}//start()
		
	/** metodo play(): void
	 * questo metodo si occupa di scegliere la modalita' in cui
	 * giocare e di gestire le mosse effettuate dal giocatore.
	 */
	public static void play() {
		//risoluzione
		int status = AutomaticPlayer.solveGame(gm, em, run_path);
		//stampa della mappa di esplorazione
		System.out.println("Ecco cosa ho visto:\n"+em);
		//messaggio di fine partita
		System.out.println(AutomaticPlayer.printStatusMessage(status));
		//si stampa la mappa di esplorazione
		System.out.println(em.gameMapToString());
		//si mostra il percorso compiuto
		System.out.println(AutomaticPlayer.runPathToString(run_path));
	}//play()
		
	/** metodo end(): void
	 * questo metodo si occupa delle operazioni che devono essere
	 * eseguite al termine della partita.
	 */
	public static void end() {
		System.out.println("THE E.N.D.");
		//stato della partita attuale
		//Starter.setGameStart(false);
		//si resetta la disponibilita' del colpo
		//Starter.setChanceToHit(true);
		//punteggio
		//System.out.println("Questo e' il tuo punteggio:\n"+score.getScore());
		//si memorizza il punteggio
		//ScoreUtility.saveScore(score_file, score.toString());
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

	/*
	public static Score getCurrentScore() {
		return score;
	}//getCurrentScore()
	*/
}//end GameSession
