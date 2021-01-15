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
		System.out.println(g);
		
	}//main
}//Test
