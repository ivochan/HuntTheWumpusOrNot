package game.structure.map;

import java.util.HashMap;
import java.util.Scanner;

import game.controller.Direction;
import game.session.GameModeTranslation;
import game.structure.cell.CellStatus;

public class Starter {
	//acquisizione input
	static Scanner input = new Scanner(System.in);
	//flag sessione di gioco
	private static boolean game_start=false;
	//acquisizione modalita' gioco
	private static char game_mode = ' '; 
	private static boolean game_mode_choosen=false;
	//flag modalita' di gioco
	private static boolean hero_side=false;
	//traduzioni modalita' di gioco
	public static HashMap<CellStatus, String> trad_mex;
	//acquisizione mossa 
	private static char move = ' ';
	private static Direction pg_move;	
	//

	
	/** metodo chooseMove(): void
	 * questo metodo si occupa di acquisire il comando che 
	 * rappresenta la mossa che si vuole effettui il pg,
	 * controlla se e' valida ed assegna alla variabile pg_move
	 * la direzione che e' stata scelta.
	 * I comandi vengono inseriti secondo lo schema WASD
	 */
	public static void chooseMove() {
		//si acquisisce il comando
		System.out.println("Inserisci comando :> ");
		move = input.next().charAt(0);
		//si valuta il comando
		switch(move) {
			case 'w':
				setPGmove(Direction.UP);
				break;
			case 'a':
				setPGmove(Direction.LEFT);
				break;
			case 's':
				setPGmove(Direction.DOWN);
				break;
			case 'd':
				setPGmove(Direction.RIGHT);
				break;
			case 'i':
				game_start=false;
				System.out.println("Interruzione della partita ...");
				break;
			default:
				System.out.println("Mossa errata!");
				break;
			}//end switch
		}//chooseMove()
	
	/** metodo getGameStart(): boolean
	 * quesot metodo accessorio fornisce lo stato della variabile
	 * di interesse.
	 * @return game_start: boolean, flag che indica che la partita, 
	 * 						la sessione di gioco e' ancora in corso.
	 */
	public static boolean getGameStart() {
		return game_start;
	}//getGameStart()

	/** metodo setGameStart(boolean)
	 * questo metodo accessorio assegna il valore ricevuto come parametro
	 * alla variabile game_start
	 * @param flag: boolean, valore che si vuole assegnare.
	 */
	public static void setGameStart(boolean flag) {
		game_start=flag;
	}//setGameStart()
	
	/** metodo initGameData(): void
	 * questo metodo si occupa di inizializzare le mappe delle 
	 * traduzioni del gioco e stampare la legenda dei comandi
	 * per interfacciarsi con l'applicazione.
	 */
	public static void initGameData() {
		//traduzioni
		GameModeTranslation.initHeroTranslation();
		GameModeTranslation.initWumpusTranslation();
		//intro al gioco
		System.out.println("Link... Start-o!");
	}//initGameData
	
	/** metodo resetGameData(GameMap): void
	 * questo metodo si occupa di resettare le variabili che 
	 * permettono, rispettivamente, di specificare la modalita'
	 * di gioco e di iniziare una nuova partita, oltre che di
	 * svuotare la mappa di esplorazione.
	 * @param ge:GameMap, la mappa di esplorazione.
	 */
	public static void resetGameData(GameMap ge) {
		//punteggio del giocatore
		
		//scelta della modalita' di gioco
		game_mode_choosen=false;
		//stato della partita attuale
		game_start=false;
		//pulizia della mappa di esplorazione
		ge.clear();
		//pulizia della console
		clearConsole();
	}//resetGameData()

	/** metodo chooseGameMode(): void
	 * questo metodo si occupa di impostare la modalita' di gioco
	 */
	public static void chooseGameMode() {
		System.out.println("Ciao :3\nDimmi di te..."+GameModeTranslation.mode);
		//scelta della modalita' di gioco
		while(!game_mode_choosen) {
			//acquisizione da input
			game_mode = input.next().charAt(0);
			//hero side
			if(game_mode=='h') {
				hero_side=true;
				game_mode_choosen=true;
			}//fi 'h'
			//wumpus side
			else if(game_mode == 'w') {
				hero_side=false;
				game_mode_choosen=true;
			}//fi 'w'
			//scelta non valida
			else {
				System.out.println("Dai, scegli!");
			}//esle
		}//end while scelta modalita'
		//traduzioni messaggi messaggi di gioco
		trad_mex = (hero_side?(GameModeTranslation.hero_side_mex)
				:(GameModeTranslation.wumpus_side_mex));
	}//chooseGameMode
	
	/** metodo verifyPGpos(GameMap, int [])
	 * questo metodo controlla in che cella si trova il pg
	 * fornendo all'utente le informazioni dei sensori
	 * riguardo l'ambiente circostante
	 * @param gm: GameMap, mappa che descrive il terreno di gioco;
	 * @param pg_pos: int[], posizione corrente del pg nella mappa
	 * 						di esplorazione.
	 */
	public static void verifyPGpos(GameMap gm, int [] pg_pos) {
		//vettore dei sensori
		boolean [] sensors = new boolean[2];
		//stampa della posizione corrente del pg
		System.out.println("Ti trovi nella cella ("+pg_pos[0]+','+pg_pos[1]+')');
		//si acquisiscono le informazioni del vettore dei sensori
		sensors=gm.getGameCell(pg_pos[0], pg_pos[1]).getSenseVector();
		//vicinanza del nemico
		if(sensors[0]) {
			System.out.println(trad_mex.get(CellStatus.ENEMY_SENSE));
			//si chiede all'utente se vuole sparare
			
		}
		//vicinanza del pericolo
		if(sensors[1])System.out.println(trad_mex.get(CellStatus.DANGER_SENSE));
		//nessun tipo di pericolo
		if(!sensors[0] && !sensors[1])System.out.println("Tutto a posto all'orizzonte!");
	}//verifyPGpos()
			
	/** metodo getPGmove(): Direction
	 * questo metodo fornisce la direzione in cui si vuole muovere
	 * il pg, in base alla mossa acquisita da input.
	 * @return pg_move: Direction, direzione in cui si vuole
	 * 					muovere il pg.
	 */
	public static Direction getPGmove() {
		return pg_move;
	}//getPGmove()

	/** metodo setPGmove(Direction)
	 * questo metodo accessorio permette di impostare la direzione
	 * scelta per il pg, assegnando questo valore alla variabile
	 * di classe.
	 * @param pg_move: Direction, direzione in cui muovere il pg.
	 */
	public static void setPGmove(Direction pg_move) {
		Starter.pg_move = pg_move;
	}//setPGmove()
	
	/** metodo clearConsole(): void
	 * utilizzato per pulire la console dopo ogni stampa 
	 * della matrice di gioco
	 */
	public static void clearConsole() {
		//linux
		//System.out.print("\033[H\033[2J");
		//System.out.flush();
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
	}//clearConsole()


	
}//class
