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
	//legenda
	private String legenda = new String("Questa e' la mappa di gioco! :)\n"
			+ "Il significato delle caselle e' il seguente:\n"
			+ "[G] Oro, [S] Sicura, [W] Wumpus,[H] Avventuriero, [D] Divieto, [T] Trappola, [P] Pozzo.\n");

	/* parametro che permette di scegliere la modalita' di gioco:
	 * -se hero_side = true, allora e' l'avventuriero a dover fuggire dal mostro (default);
	 * -se hero_side = false, allora e0 il mostro a dover scappare;
	 */
	private static boolean hero_side;
	
	//dimensioni della matrice
	private static int r = 4; //righe
	private static int c= 4; //colonne
  
	//inizializzazione della matrice di gioco 
	Cell[][] mappa_gioco = new Cell[r][c];
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
		popolaMappa();
		//si inizializza la mappa da costruire giocando
		this.mappa_esplorazione=null;	
	}//GameMap(boolean)

	/* per riempire la mappa di gioco il valore di ogni cella verra' deciso secondo 
	 * una funzione di probabilita'
	 */
	//TODO
	private void popolaMappa() {
		//si sceglie casualmente il numero di celle giocabili (da 9 a 16)
		int max_n=((int)(Math.random()*8))+9; // numero massimo delle celle da riempire
		// numero di celle da riempire rimaste
		int n=max_n;
		// si ricava il numero di celle non accessibili
		int d = 16-max_n;
		//oggetti da sistemare nelle celle
		int p = 4; //pozzi da mettere nella mappa (hero_side = true)
		int max_p = 4; //numero massimo di pozzi 
		int t = p; //trappole da mettere al posto dei pozzi (hero_side = false)
		int max_t = max_p; //numero massimo di trappole
		int g = 1;//oro
		int h=1;//avventuriero
		int w=1;//mostro
		//calcolo della probabilita'
		
	}//popolaMappa
	
	//funzione di probabilita' utilizzata per stabilire il valore che deve assumere la cella di interesse
	private int lambda(int x,int max_x, int n, int max_n) {
		/* - x, oggetto cella da inserire;
		 * - max_x, numero massimo di oggetti di tipo x che possono essere inseriti nella mappa;
		 * - n, numero di celle della mappa che devono essere ancora riempite;
		 * - max_n, numero massimo di celle della mappa che possono essere riempite (giocabili).
		 */
		int prob = ((x/max_x)) - (1-n/max_n);
		//probabilita'
		return prob;
	}

	//metodi accessori
	public boolean getGameMode() {
		/* restituisce il parametro che indica la modalita' di gioco
		 * -se true, allora e' hero_side,
		 * -se false, allora e' wumpus_side.
		 */
		return this.hero_side;
	}//getGameMode
	public void setGameMode(boolean hero_side) {
		this.hero_side=hero_side;
	}//setGameMode

	@Override
	public String toString() {
		//si crea la stringa che rappresenta la mappa da stampare
		String stampa_mappa = new String();
		for(int i=0; i<r; i++) {
			//si scorrono le righe della matrice
			stampa_mappa+=" |"; //si stampa l'inizio della riga
			for(int j=0; j<c; j++) {
				//si scorrono le colonne della matrice
				//si stampa il contenuto della cella 
				if(j<c-1) {
					stampa_mappa+=mappa_gioco[i][j]+ " ";
				}
				else {
					stampa_mappa+=mappa_gioco[i][j];
				}
			}//for colonne
			stampa_mappa+="|\n"; //si stampa la fine della riga e si va a capo
		}//for righe
		
		return legenda+"\n"+stampa_mappa;
	}//toString()
	
	public void setGameCell(int i, int j, CellStatus status) {
		//metodo che permette specificare il contenuto di una cella di gioco
	}

	

}//GameMap
