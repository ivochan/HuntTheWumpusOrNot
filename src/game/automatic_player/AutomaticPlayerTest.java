package game.automatic_player;

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

		//creazione della mappa di gioco
		GameMap gm = new GameMap();
		//creazione della mappa di esplorazione
		GameMap em = new GameMap(); 
		//stampa
		System.out.println("Ecco il terreno di gioco....\n");
		//creazione della mappa
		MapConfiguration.init(gm,em);
		//stampa della mappa
		System.out.println(gm.gameMapToString());
		//System.out.println(em);		
		//risoluzione
		AutomaticPlayer.solveGame(gm,em);
		//stampa della mappa di esplorazione
		System.out.println("Fine");
		System.out.println(em);
		System.out.println(AutomaticPlayer.runPathToString());
	}//end main			

	
}//end AutomaticAgentTest