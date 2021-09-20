package game.structure.elements;

import game.structure.map.GameMap;

public class GameElements {
	//##### attributi di classe #####
	
	private static int NUMBER_OF_CELLS;
	private static int STONES;
	private static int AWARD;
	private static int ENEMY;
	private static int DANGER;
	
	/** metodo generatingGameElements(GameMap)
	 * metodo che si occupa di inizializzare ed assegnare un valore
	 * ad ogni cella del vettore degli elementi di gioco, specificando
	 * il massimo numero di elementi che potranno essere posizionati
	 * nella mappa di gioco, per ciascuna tipologia
	 * @param gm: GameMap, mappa di gioco.
	 */
	public static void generatingGameElements(GameMap gm) {
		//controllo sul parametro
		if(gm==null) throw new IllegalArgumentException("mappa da utilizzare per il vettore"
				+ "dei sensori nulla");
		//numero di celle della mappa
		NUMBER_OF_CELLS = gm.getMapDimension();
		//numero di celle non accessibili: da 0 a 1
		STONES = (int)(Math.random()*2);
		//STONES = 0;
		//premio
		AWARD = 1;
		//nemico
		ENEMY = 1;
		//pericoli
		DANGER = 2;
	}//generatingGameElements(GameMap)
	
	//##### metodi accessori #####
	
	/** metodo getNumberOfCells(): int
	 * questo metodo restituisce il numero di celle
	 * della matrice che costituisce la mappa di gioco
	 * @return NUMBER_OF_CELLS: int, numero di celle.
	 */
	public static int getNumberOfCells() {
		return NUMBER_OF_CELLS;
	}//getNumberOfCells()
	
	/** metodo getDenied(): int
	 * questo metodo restiuisce il numero di
	 * celle non accessibili, i sassi.
	 * @return STONES: int, numero di celle non accessibili.
	 */
	public static int getStones() {
		return STONES;
	}//getDenied()

	/** metodo getAward(): int
	 * questo metodo restituisce il numero di celle contenenti
	 * il premio, presenti sulla mappa di gioco.
	 * @return AWARD: int, numero di premi.
	 */
	public static int getAward() {
		return AWARD;
	}//getAward()

	/** metodo getEnemy(): int
	 * questo metodo restituisce il numero di nemici
	 * che verranno collocati sulla mappa di gioco
	 * @return ENEMY: int, numero di celle contenenti il nemico.
	 */
	public static int getEnemy() {
		return ENEMY;
	}//getEnemy()

	/** metodo getDanger(): int
	 * questo metodo restituisce il numero di celle
	 * contenenti un pericolo (pozzo o trappola)
	 * nella mappa di gioco.
	 * @return DANGER: int, numero di pericoli.
	 */
	public static int getDanger() {
		return DANGER;
	}//getDanger()	

	/**metodo printGameElements(): String
	 * metodo che restituisce una stringa che rappresenta il contenuto del vettore
	 * degli elementi di gioco, comprensivo di legenda indicativa
	 *@return game_els: String, stringa che comprensiva della legenda e del
	 *		  contenuto del vettore degli elementi di gioco.
	 */
	public static String printGameElements() {
		//inizializzazione della stringa
		String game_els = new String();
		//informazioni riguardanti gli elementi di gioco
		game_els = new String("[number of cells : "+NUMBER_OF_CELLS+" ]\n" +
							"[stones : "+ STONES+" ]	[award : "+AWARD+" ]\n" +
							"[enemy : "+ENEMY+" ]	[dangers : "+DANGER+" ]\n");
		//si restituisce la stringa
		return game_els;
	}//gameElements()
	
}//end GameElements