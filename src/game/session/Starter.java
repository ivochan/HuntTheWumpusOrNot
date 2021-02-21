package game.session;

import java.util.HashMap;
import java.util.Scanner;

import game.controller.Controller;
import game.controller.Direction;
import game.structure.cell.CellStatus;
import game.structure.map.GameConfiguration;
import game.structure.map.GameMap;

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
	

	static void chooseMove() {
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
			}
		}
	
	
	
	
	public static boolean getGameStart() {
		return game_start;
	}

	public static void setGameStart(boolean flag) {
		game_start=flag;
	}
	
	static void initGameData() {
		//traduzioni
		GameModeTranslation.initHeroTranslation();
		GameModeTranslation.initWumpusTranslation();
		System.out.println("Link... Start-o!");
		//intro al gioco
		System.out.println(GameModeTranslation.command_legend);
	}//initGameData
	
	static void resetGameData(GameMap ge) {
		//scelta della modalita' di gioco
		game_mode_choosen=false;
		game_start=false;
		ge.clear();
		clearConsole();
	}


	static void chooseGameMode() {
		System.out.println("Ciao :3\nDimmi di te..."+GameModeTranslation.mode);
		//scelta della modalita' di gioco
		while(!game_mode_choosen) {
			//acquisizione da input
			game_mode = input.next().charAt(0);
			//hero side
			if(game_mode=='h') {
				hero_side=true;
				game_mode_choosen=true;
			}
			//wumpus side
			else if(game_mode == 'w') {
				hero_side=false;
				game_mode_choosen=true;
			}
			//scelta non valida
			else {
				System.out.println("Dai, scegli!");
			}
		}//end while scelta modalita'
		//traduzioni messaggi messaggi di gioco
		trad_mex = (hero_side?(GameModeTranslation.hero_side_mex)
				:(GameModeTranslation.wumpus_side_mex));
	}//chooseGameMode
	
	static void verifyPGpos(GameMap gm, int [] pg_pos) {
		//vettore dei sensori
		boolean [] sensors = new boolean[2];
		//stampa della posizione corrente del pg
		System.out.println("Ti trovi nella cella ("+pg_pos[0]+','+pg_pos[1]+')');
		//si acquisiscono le informazioni del vettore dei sensori
		//comunicazione all'utente del valore dei sensori
		sensors= gm.getGameCell(pg_pos[0], pg_pos[1]).getSenseVector();
		//vicinanza del nemico
		if(sensors[0])System.out.println(trad_mex.get(CellStatus.ENEMY_SENSE));
		//vicinanza del pericolo
		if(sensors[1])System.out.println(trad_mex.get(CellStatus.DANGER_SENSE));
		//nessun tipo di pericolo
		if(!sensors[0] && !sensors[1])System.out.println("Tutto a posto all'orizzonte!");
		
	}
			
	/** metodo clearConsole(): void
	 * utilizzato per pulire la console dopo ogni stampa 
	 * della matrice di gioco
	 */
	public static void clearConsole() {
		//linux
		System.out.print("\033[H\033[2J");
		System.out.flush();
	
	}//clearConsole()




	public static Direction getPGmove() {
		return pg_move;
	}




	public static void setPGmove(Direction pg_move) {
		Starter.pg_move = pg_move;
	}
	
	
}//class
