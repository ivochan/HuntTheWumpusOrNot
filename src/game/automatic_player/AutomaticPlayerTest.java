package game.automatic_player;
import java.util.LinkedList;

import game.structure.cell.Cell;
//import
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/**
 * 
 * @author ivochan
 *
 */
public class AutomaticPlayerTest {
	
	/** main
	 * @param args
	 */
	public static void main(String[] args) {

		//percorso compiuto
		LinkedList<Cell> run_path = new LinkedList<>();
		//creazione della mappa di gioco
		GameMap gm = new GameMap();
		//creazione della mappa di esplorazione
		GameMap em = new GameMap(); 
		//stampa
		System.out.println("Ecco il terreno di gioco....\n");
		//creazione della mappa
		MapConfiguration.init(gm,em);
		//stampa della mappa
		//System.out.println(gm.gameMapToString());
		System.out.println(gm);
		//risoluzione
		int status = AutomaticPlayer.solveGame(gm,em, run_path);
		//stampa della mappa di esplorazione

		System.out.println(em);
		//messaggio di fine partita
		System.out.println(AutomaticPlayer.printStatusMessage(status));
		//path
		System.out.println(AutomaticPlayer.runPathToString(run_path));
	}//end main			

	
}//end AutomaticAgentTest