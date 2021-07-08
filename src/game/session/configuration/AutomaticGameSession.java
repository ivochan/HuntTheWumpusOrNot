package game.session.configuration;
//serie di import
import game.player.agent.RandomAgent;
import game.session.score.Score;
import game.session.score.ScoreMemo;
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
		System.out.println("Preparazione del terreno di gioco....\n");
		//creazione della mappa
		MapConfiguration.init(gm,em);
		//stampa della mappa
		System.out.println(gm.gameMaptoString());
		//si inizializza il punteggio
		Score.resetScoreData();
		//si inizializza il file
		ScoreMemo.createScoreFile();
	}//start()
		
	/** metodo play(): void
	 * questo metodo si occupa di scegliere la modalita' in cui
	 * giocare e di gestire le mosse effettuate dal giocatore.
	 */
	public static void play() {
		//si istanzia il giocatore automatico
		RandomAgent player = new RandomAgent();
		//avvio della partita
		while(Starter.getGameStart()){
			//si effettua la mossa
			player.chooseMove(em, gm);
			//si mostra il punteggio
			System.out.println("Punteggio parziale: "+Score.printScore());
		}//end while sessione di gioco
		//si stampa la mappa di esplorazione
		System.out.println(em.gameMaptoString());
		//si mostra il percorso compiuto
		player.showRunPath();
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
		Starter.setChanceToHit(true);
		//punteggio
		System.out.println("Questo e' il tuo punteggio:\n"+Score.getScore());
		//si memorizza il punteggio
		ScoreMemo.saveScore(Score.scoreToString());
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
