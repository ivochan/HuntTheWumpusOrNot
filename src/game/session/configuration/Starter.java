package game.session.configuration;
//serie di import
import java.util.HashMap;
import java.util.Scanner;
import game.session.controller.Direction;
import game.structure.cell.CellStatus;
import game.structure.text.GameMessages;
import game.structure.text.GameTranslations;
/** class Starter
 * classe che si occupa di tutte le configurazioni di gioco
 * @author ivonne
 */
public class Starter {
	//##### attributi di classe #####

	//nome del file dei punteggi
	private static String name = new String("/Scores.txt");
	//path in cui creare il file
	private static String path = System.getProperty("user.dir") + name;
	
	//acquisizione da input
	private static Scanner input = new Scanner(System.in);
	//flag sessione di gioco
	private static boolean game_start;
	//traduzioni modalita' di gioco
	public static HashMap<CellStatus, String> trad_mex;
	
	//direzione in cui si vuole muovere il pg
	private static Direction pg_move;
	//flag della richiesta di movimento del pg
	private static boolean walk;
	
	//flag della richiesta del tentare il colpo
	private static boolean try_to_hit;
	//colpo a disposizione
	private static boolean chance_to_hit;
	//direzione del colpo
	private static Direction shot_dir;
		
	/** metodo chooseGameMode(): void
	 * questo metodo si occupa di impostare la modalita' di gioco
	 */
	public static void chooseGameMode() {
		//variabili ausiliarie
		char game_mode = ' '; 
		boolean game_mode_choosen=false;
		//acquisizione modalita' gioco
		System.out.println("Ciao :3\nDimmi di te..."+GameMessages.mode);
		//scelta della modalita' di gioco
		while(!game_mode_choosen) {
			//acquisizione da input
			game_mode = input.next().charAt(0);
			//hero side
			if(game_mode=='h') {
				//modalita' cacciatore
				game_mode_choosen=true;
				//inizializzazione delle traduzioni
				GameTranslations.initHeroTranslation();
				//assegnamento alla mappa dei messaggi
				trad_mex = GameTranslations.hero_side;
			}//fi 'h'
			//wumpus side
			else if(game_mode == 'w') {
				//modalita' wumpus
				game_mode_choosen=true;
				//inizializzaione delle traduzioni
				GameTranslations.initWumpusTranslation();
				//assegnamento alla mappa dei messaggi
				trad_mex = GameTranslations.wumpus_side;
			}//fi 'w'
			//scelta non valida
			else {
				System.out.println("Dai, scegli!");
			}//esle
		}//end while
	}//chooseGameMode()
	
	/** metodo chooseMove(): void
	 * questo metodo acquisisce la mossa che vuole effettuare il 
	 * giocatore.
	 */
	public static void chooseMove() {
		//variabile ausiliaria per la ricezione del comando
		char move  =' ';
		//si acquisisce il comando
		System.out.println("\nInserisci comando :> ");
		move = input.next().charAt(0);
		//si valuta il comando
		switch(move) {
			case 'w':
				//sopra
				setPGmove(Direction.UP);
				walk = true;
				break;
			case 'a':
				//sinistra
				setPGmove(Direction.LEFT);
				walk = true;
				break;
			case 's':
				//sotto
				setPGmove(Direction.DOWN);
				walk = true;
				break;
			case 'd':
				//destra
				setPGmove(Direction.RIGHT);
				walk = true;
				break;
			case 'i':
				//interruzione
				game_start=false;
				walk = false;
				System.out.println("Interruzione della partita ...");
				break;
			case 'l':
				//tentativo di colpire il nemico
				walk = false;
				try_to_hit=true;
				break;
			default:
				System.out.println("Mossa errata!");
				break;
		}//end switch
	}//chooseMove()
	
	/** metodo chooseMove(): void
	 * questo metodo acquisisce la mossa che vuole effettuare il 
	 * giocatore.
	 */
	public static void chooseDirection() {
		//variabile ausiliaria per la validita' del comando
		boolean valid_dir = false;
		//variabile ausiliaria per la ricezione del comando
		char dir  =' ';
		//ciclo di acquisizione
		while(!valid_dir) {
			//si acquisisce il comando
			System.out.println("Inserisci la direzione :> ");
			dir = input.next().charAt(0);
			//si valuta il comando
			switch(dir) {
				case 'w':
					//sopra
					setShotDir(Direction.UP);
					chance_to_hit = true;
					valid_dir = true;
					break;
				case 'a':
					//sinistra
					setShotDir(Direction.LEFT);
					chance_to_hit = true;
					valid_dir = true;
					break;
				case 's':
					//sotto
					setShotDir(Direction.DOWN);
					chance_to_hit = true;
					valid_dir = true;
					break;
				case 'd':
					//destra
					setShotDir(Direction.RIGHT);
					chance_to_hit = true;
					valid_dir = true;
					break;
				case 'i':
					//interruzione
					game_start=false;
					chance_to_hit = false;
					System.out.println("Interruzione della partita ...");
					valid_dir = true;
				default:
					System.out.println("Direzione non valida!");
					valid_dir = false;
					break;
			}//end switch
		}//end while		
	}//chooseDirection()

	//##### metodo accessori: mossa del pg #####
	
	/** metodo setPGmove(Direction): void
	 * questo metodo imposta la direzione in cui si
	 * vuole muovere il pg
	 * @param dir: Direction, direzione in cui effettuare la mossa
	 */
	public static void setPGmove(Direction dir) {
		//si imposta la direzione
		pg_move= dir;
	}//setPGmove(Direction)
	
	/** metodo getPGmove(): void
	 * questo metodo restituisce la direzione in cui si 
	 * vuole muovere il pg
	 * @return pg_move: Direction, direzione in cui effettuare
	 * 					la mossa del personaggio giocabile.
	 */
	public static Direction getPGmove() {
		//si restituisce il valore della direzione
		return pg_move;
	}//getPGmove()
	
	//##### metodo accessori: direzione del colpo #####
	
		/** metodo setShotDir(Direction): void
		 * questo metodo imposta la direzione in cui si
		 * vuole tentare il colpo
		 * @param dir: Direction, direzione in cui effettuare il colpo
		 */
		public static void setShotDir(Direction dir) {
			//si imposta la direzione
			shot_dir= dir;
		}//setShotDir(Direction)
		
		/** metodo getShotDir(): void
		 * questo metodo restituisce la direzione in cui si 
		 * vuole effettuare il colpo
		 * @return pg_move: Direction, direzione in cui effettuare
		 * 					il colpo per uccidere il nemico.
		 */
		public static Direction getShotDir() {
			//si restituisce il valore della direzione
			return shot_dir;
		}//getShotDir()
		
	//##### metodi accessori : flag di avvio della sessione di gioco #####
	
	/** metodo getGameStart(): boolean
	 * quesot metodo accessorio fornisce lo stato della variabile
	 * di interesse.
	 * @return game_start: boolean, flag che indica che la partita, 
	 * 						la sessione di gioco e' ancora in corso.
	 */
	public static boolean getGameStart() {
		//si restituisce la variabile di interesse
		return game_start;
	}//getGameStart()

	/** metodo setGameStart(boolean): void
	 * questo metodo accessorio assegna il valore ricevuto come parametro
	 * alla variabile game_start
	 * @param flag: boolean, valore che si vuole assegnare.
	 */
	public static void setGameStart(boolean flag) {
		//si imposta la variabile con il parametro ricevuto
		game_start=flag;
	}//setGameStart(boolean)

	//##### metodi accessori: flag della direzione scelta per il pg #####
	
	/** metodo setWalk(boolean): void
	 * questo metodo imposta la variabile di interesse
	 * che indica la scelta della direzione in cui muovere
	 * il pg, con il valore del parametro ricevuto
	 * @param flag: boolean, valore da assegnare alla variabile walk;
	 */
	public static void setWalk(boolean flag) {
		//si aggiorna il valore della variabiloe
		walk = flag;
	}//setWalk(boolean)

	/** metodo getWalk(): boolean
	 * questo emtodo restituisce il valore della variabile
	 * che indica la volonta' dell'utente di scegliere
	 * la direzione in cui muovere il pg.
	 * @return walk: boolean, valore della variabile.
	 */
	public static boolean getWalk() {
		//si restituisce il valore della variabile
		return walk;
	}//getWalk()

	//##### metodi accessori: tentativo del colpo #####
	
	/** metodo setTryToHit(boolean): void
	 * questo metodo aggiorna il valore della variabile che 
	 * indica la volonta', da parte dell'utente, di
	 * provare a colpire il nemico, con il valore
	 * della variabile ricevuta come parametro.
	 * @param flag: boolean, valore da assegnare.
	 */
	public static void setTryToHit(boolean flag) {
		//si aggiorna la variabile
		try_to_hit = flag;
	}//setTryToHit(boolean)

	/** metodo getTryToHit(): boolean
	 * questo metodo restituisce il valore della variabile
	 * che indica la voltonta' del giocatore, di provare
	 * a colpire il nemico oppure no.
	 * @return try_to_hit: boolean, se true allora il giocatore
	 * 								vuole tentare il colpo, se false
	 * 								allora no.
	 */
	public static boolean getTryToHit() {
		//si restituisce il valore della variabile
		return try_to_hit;
	}//getTryToHit()

	//##### metodi accessori: disponibilita' del colpo #####
	
	/** metodo setChanceToHit(boolean): void
	 * questo metodo aggiorna la variabile di interesse
	 * con il valore ricevuto come parametro, in modo da
	 * descrivere la presenza o meno della possibilita' di
	 * poter effettuare il colpo per uccidere il nemico.
	 * @param flag: boolean.
	 */
	public static void setChanceToHit(boolean flag) {
		//si aggiorna il campo di interesse
		chance_to_hit = flag;
	}//setChanceToHit(boolean)
	
	/** metodo getChanceToHit()
	 * questo metodo restituice il valore della variabile
	 * che indica la possibilita' o meno di avere munizioni
	 * per tentare il colpo al nemico.
	 * @return chance_to_hit: boolean, se true allora ci sono
	 * 							munizioni, quindi si tenta il
	 * 							colpo, altrimenti non si puo'.
	 */
	public static boolean getChanceToHit() {
		//si restituisce il valore della variabile
		return chance_to_hit;
	}//getChanceToHit()
	
	//##### metodi accessori per il nome ed il path del file dei punteggi #####
	
	/** metodo getPath(): String
	 * questo metodo restituisce il valore
	 * della stringa che rappresenta il path
	 * del file dei punteggi.
	 * @return path: String, stringa che contiene il path.
	 */
	public static String getPath() {
		//si restituisce l'attributo di classe path
		return path;
	}//getPath()
	
	/** metodo setPath(String): void
	 * questo metodo aggiorna il valore
	 * del path del file.
	 * @param p: String, valore da assegnare al path.
	 */
	public static void setPath(String p) {
		//controllo sul parametro
		if(p==null) throw new IllegalArgumentException("path del file ricevuto come"
				+ " parametro non valido");
		//si assegna il path
		path = new String(p);
	}//setPath(String)
	
	/** metodo getName(): String
	 * questo metodo restituisce il nome del file
	 * in cui salvare i punteggi.
	 * @return name: String, nome del file;
	 */
	public static String getName() {
		//si restituisce l'attributo di classe path
		return name;
	}//getPath()

	/** metodo setName(): void
	 * questo metodo permette di impostare il nome
	 * del file.
	 * @param n: String, nome del file;
	 */
	public static void setName(String n) {
		//controllo sul parametro
		if(n==null)throw new IllegalArgumentException("nome del file ricevuto come"
				+ " parametro non valido");
		//si specifica il nome del file
		name = new String(n);
	}//setPath(String)
}//end Starter
