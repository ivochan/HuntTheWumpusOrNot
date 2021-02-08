package game_session;

import game_structure.GameMap;

public class GameSessionTest {

	public static void main(String[] args) {
		//si sceglie la modalita' di gioco
		boolean hero_side = true;
		//si crea la mappa di gioco
		GameMap gm = new GameMap(hero_side);
		//DEBUG
		System.out.println(gm);
		
	}

}
