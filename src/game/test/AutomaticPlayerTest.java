package game.test;
//serie di import
import java.util.LinkedList;
import game.session.player.AutomaticPlayer;
import game.structure.cell.Cell;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/** class AutomaticPlayerTest
 * questa classe esegue un test per verificare il modo
 * in cui il giocatore automatico si occupa della 
 * risoluzione della mappa di gioco.
 * 
 * @author ivochan
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
		int status = AutomaticPlayer.solveGame(gm, em, run_path);
		//stampa della mappa di esplorazione
		System.out.println("Ecco cosa ho visto:\n"+em);
		//messaggio di fine partita
		System.out.println(AutomaticPlayer.printStatusMessage(status));
		//path
		System.out.println(AutomaticPlayer.runPathToString(run_path));
		//DEBUGG
		//System.out.println("confronto\n"+gm)
		//int[] pg = PlayableCharacter.getPGposition();
		//System.out.println("PG : "+pg[0]+','+pg[1]);
		AutomaticPlayer.updateExplorationMap(em);
		System.out.println(em);

	}//end main
	
}//end AutomaticAgentTest