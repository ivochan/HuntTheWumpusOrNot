package game.test;

import game.structure.cell.CellStatus;
import game.structure.map.GameMap;

/** classe di test
 * questa classe e' stata utilizzata per testare la validita' 
 * delle funzionalita' della classe GameMap
 * @author ivonne
 */
public class TestGameMap {

	public static void main(String [] args) {
		//viene istanziato un oggetto GameMap
		GameMap g = new GameMap();
		System.out.println(g);
		//costruttore con parametri
		GameMap gm  =new GameMap(-1,4);
		GameMap gm1  =new GameMap(5,0);
		GameMap gm2 =new GameMap(5,5);
		System.out.println(gm2.mapToString());
		//stampa della dimensione della mappa
		System.out.println("Righe "+g.getRows());
		System.out.println("Colonne "+g.getColumns());
		System.out.println("Numero di celle "+g.getNCells());
		//test sulla modifica del contenuto
		g.getGameCell(0,0).setCellStatus(CellStatus.PG);
		System.out.println(g);
		System.out.println(g.mapAndLegend());
		//g.getGameCell(0,-1).setCellStatus(CellStatus.PG);
		g.clear();
		System.out.println("Mappa svuotata!\n"+g);
	
		}//main
}
