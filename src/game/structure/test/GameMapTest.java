package game.structure.test;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;

public class GameMapTest {

	public static void main(String[] args) {
		//si crea la mappa di gioco
		GameMap gm = new GameMap();
		//si crea la mappa di esplorazione
		GameMap em = new GameMap();
		System.out.println(gm);
		//posizionamento degli elementi nella mappa
		MapConfiguration.init(gm,em);
		System.out.println(gm);
		//info path progetto
		final String dir = System.getProperty("user.dir");
		System.out.println(dir);

	}//main

}
