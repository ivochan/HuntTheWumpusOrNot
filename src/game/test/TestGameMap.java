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
		//stampa della dimensione della mappa
		System.out.println("Righe "+g.getRows());
		System.out.println("Colonne "+g.getColumns());
		System.out.println("Numero di celle "+g.getNCells());
		//test sulla modifica del contenuto
		g.getGameCell(0,0).setCellStatus(CellStatus.PG);
		System.out.println(g);
		System.out.println("cella "+g.getGameCell(0, 0));
		System.out.println(g.mapAndLegend());
		//g.getGameCell(0,-1).setCellStatus(CellStatus.PG);
		g.clear();
		System.out.println("Mappa svuotata!\n"+g);
		//confronto fra celle
		boolean uguali = false;
		GameMap mappa = new GameMap();
		mappa.getGameCell(0,0).setCellStatus(CellStatus.SAFE);
		System.out.println(mappa);
		mappa.getGameCell(3, 3).setCellStatus(CellStatus.SAFE);
		System.out.println(mappa);
		uguali= mappa.getGameCell(0, 0).getCellStatus().
				equals(mappa.getGameCell(3, 3).getCellStatus());
		System.out.println("cella (0,0) uguale a cella (3,3)? "+uguali);
		mappa.getGameCell(1, 3).setCellStatus(CellStatus.PG);
		System.out.println(mappa);
		uguali= mappa.getGameCell(0, 0).getCellStatus().
				equals(mappa.getGameCell(1, 3).getCellStatus());
		System.out.println("cella (0,0) uguale a cella (1,3)? "+uguali);
		System.out.println(mappa);
		
		}//main
}
