package game_structure;
/* classe che realizza la mappa di gioco
 * in cui ci saranno:
 * -1 mostro,
 * -1 eroe,
 * -1 lingotto d'oro,
 * -4 pozzi (hero_side) o 4 trappole (wumpus_side)
 * e' costituita da una matrice di Button di dimensione 4 x 4
 * ogni cella e' una stanza da esplorare, collegata a quelle ad essa adiacenti
 * non tutte le celle sono "giocabili"
 * ci saranno un minimo di 9 stanze ed un massimo di 16
 * ogni stanza e' collegata con quelle ad essa adiacenti
 */
public class GameMap {
	/* parametro che permette di scegliere la modalita' di gioco:
	 * -se hero_side = true, allora e' l'avventuriero a dover fuggire dal mostro (default);
	 * -se hero_side = false, allora e0 il mostro a dover scappare;
	 */
	private static boolean hero_side;
	
	//dimensioni della matrice
	private static int r = 4; //righe
	private static int c= 4; //colonne
  
	//inizializzazione della matrice di gioco 
	private Cell[][] mappa_gioco = new Cell[r][c];
	/*si deve popolare la matrice secondo queste specifiche:
	 *-generare casualmente un numero tra 9 e 16 che rappresenta il numero di stanze giocabili;
	 *-inserire la casella "fossa";
	 *-inserire la casella dove si trova il wumpus;
	 *-inserire la casella dove si trova l'oro;
	 *-inserire vicino il wumpus le caselle "puzza";
	 *-inserire caselle "libere->prato";
	 *-inserire le caselle "non accessibili";
	 */

	/* questa mappa viene popolata man mano che si gioca, esplorando la mappa di gioco
	 * Le celle non ancora visitare avranno il parametro isVisited = false (colore grigio)
	 */
	private Cell[][] mappa_esplorazione = new Cell[r][c];
  
	//costruttore di default
	public GameMap() {
		hero_side=true;
		mappa_esplorazione = null;
	}//GameMap()
  
	public GameMap(boolean hero_side) {
		//si specifica la modalita' di gioco
		this.hero_side=hero_side;
		//si inizializza la mappa di gioco
		popola_mappa();
		//si inizializza la mappa da costruire giocando
		this.mappa_esplorazione=null;	
	}//GameMap(boolean)

	/* per riempire la mappa di gioco il valore di ogni cella verra' deciso secondo 
	 * una funzione di probabilita'
	 */
	private void popola_mappa() {
		// TODO Auto-generated method stub
		
	}




}//GameMap
