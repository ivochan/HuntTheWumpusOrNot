package game.session.configuration;
import game.player.agent.RandomAgent;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.session.score.Score;
import game.session.score.ScoreMemo;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
import game.structure.text.GameMessages;
/** class AutomaticGameSession
 * classe che realizza una sessione di gioco
 * in cui la partita viene svolta utilizzando
 * un giocatore automatico
 * @author ivonne
 */
public class AutomaticGameSession {
	//##### attributi di classe #####
	//TODO revisionare
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
			System.out.println("Giochiamo");
			//si effettua la mossa
			player.chooseMove(em, gm);
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
		//percorso compiuto
		//TODO
		//punteggio
		Score.totalScore();
		System.out.println("Questo e' il tuo punteggio:\n"+Score.getScore());
		//si memorizza il punteggio
		ScoreMemo.saveScore(Score.ScoreToString());
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
		
	/**
	 * 
	 */
	public void HumanPlayer() {
		
	}
		
}//end GameSession
/*
 * public static void play() {
		//si istanzia il giocatore automatico
		RandomAgent player = new RandomAgent();
		//variabile ausiliaria per il colpo
		Direction dir;
		//variabile asusiliaria per la mossa
		Direction move;
		//variabile ausiliaria per lo stato della mossa
		int status = 0;
		//avvio della partita
		while(Starter.getGameStart()){
			//si effettua la mossa
			player.chooseMove(em, gm);
			//acquisizione dell'azione del giocatore
			player.chooseMove(gm, em);
			//si verifica se si vuole sparare
			if(Starter.getTryToHit()) {
				//si verifica se si ha un colpo a disposizione
				if(Starter.getChanceToHit()) {
					//si chiede la direzione in cui sparare
					player.chooseDirection();
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
				//reset flag
				Starter.setWalk(false);
			}//fi
		}//end while sessione di gioco
	}//play()
*/