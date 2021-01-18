package game_structure;

public class TestGameStructure {

	public static void main(String [] args) {
		
		//test visualizzazione cella
		Cell c1 = new Cell();

		boolean hero_side=true;
		
		Cell c2 = new Cell(CellStatus.GOLD,hero_side,true,true);
		//System.out.println(c2);
		System.out.println(c2.SenseVectorToString(hero_side));
		//System.out.println("La cella e' stata visitata? "+ (hero_side?"si":"no"));
		
		GameMap g1 = new GameMap();
		//System.out.println(g1);
		
		GameMap g = new GameMap(hero_side);
		//riempimento mappa
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				g.mappa_gioco[i][j]=new Cell(CellStatus.SAFE,hero_side,true,true);
			}
		}
		
		g.mappa_gioco[1][2].setCellStatus(CellStatus.GOLD);
		g.mappa_gioco[3][3].setCellStatus(CellStatus.WUMPUS);
		g.mappa_gioco[0][0].setCellStatus(CellStatus.HERO);
		g.mappa_gioco[2][2].setCellStatus(CellStatus.PIT);
		
		g.mappa_gioco[0][3].setCellStatus(CellStatus.GOLD);
		g.mappa_gioco[0][3].setCell(5);
		
		
		
		
		System.out.println(g);
		
	}//main
}//Test
