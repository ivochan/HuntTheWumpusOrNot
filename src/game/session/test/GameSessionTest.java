package game.session.test;

import game.controller.GameController;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;

public class GameSessionTest {

	public static void main(String[] args) {
		//si sceglie la modalita' di gioco
		boolean hero_side = true;
		//si crea la mappa di gioco
		GameMap g = new GameMap(hero_side);
		//DEBUG
		System.out.println(g);
		//DEBUG
		System.out.println("Mappa di esplorazione\n" + g.toStringExplorationMap(false));
		System.out.println("Sei stato posizionato nelle cella ("+g.getPGiPosition()+
				","+g.getPGjPosition()+")");
		
		//MAPPA di TEST
		g.getCellMap(0,0).setCellStatus(CellStatus.SAFE);
		g.getCellMap(0,1).setCellStatus(CellStatus.PIT);
		g.getCellMap(0,2).setCellStatus(CellStatus.GOLD);
		g.getCellMap(0,3).setCellStatus(CellStatus.SAFE);
		g.getCellMap(1,0).setCellStatus(CellStatus.PIT);
		g.getCellMap(1,1).setCellStatus(CellStatus.WUMPUS);
		g.getCellMap(1,2).setCellStatus(CellStatus.DENIED);
		g.getCellMap(1,3).setCellStatus(CellStatus.SAFE);
		g.getCellMap(2,0).setCellStatus(CellStatus.SAFE);
		g.getCellMap(2,1).setCellStatus(CellStatus.SAFE);
		g.getCellMap(2,2).setCellStatus(CellStatus.SAFE);
		g.getCellMap(2,3).setCellStatus(CellStatus.SAFE);
		g.getCellMap(3,0).setCellStatus(CellStatus.DENIED);
		g.getCellMap(3,1).setCellStatus(CellStatus.SAFE);
		g.getCellMap(3,2).setCellStatus(CellStatus.SAFE);
		g.getCellMap(3,3).setCellStatus(CellStatus.SAFE);
		//posizione iniziale del pg
		
		//DEBUG
		System.out.println(g);
		//System.out.println("Mossa valida? "+GameController.isAllowedMove(5,1));
		//System.out.println("Mossa valida? "+GameController.isAllowedMove(-1,1));
		System.out.println("Mossa valida? "+GameController.isAllowedMove(3,1));
	}//main

}
