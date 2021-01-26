package game_structure;
/** classe GameMap
 * @author ivonne
 * Questa classe realizza la mappa di gioco, i cui elementi saranno:
 * -1 mostro,
 * -1 eroe,
 * -1 lingotto d'oro,
 * -2 pozzi (hero_side) o 2 trappole (wumpus_side)
 * Questa mappa e' costituita da una matrice di Button di dimensione 4 x 4.
 * Ogni cella e' una stanza da esplorare, collegata a quelle ad essa adiacenti ed inoltre
 * non tutte le celle sono "giocabili".
 * Ci saranno un minimo di 9 stanze ed un massimo di 16 ed
 * ogni stanza e' collegata con quelle ad essa adiacenti.
 * N.B. si deve dare in modo che gli elementi di interesse (oro, wumpus, eroe) non siano
 * circondati dalle celle DENIED, cioe' non accessibili e quindi non raggiungibili
 */
public class GameMap {
	//legenda
	private String legenda = new String("Questa e' la mappa di gioco! :)\n"
			+ "Il significato delle caselle e' il seguente:\n"
			+ "[G] Oro, [S] Sicura, [W] Wumpus,[H] Avventuriero, [D] Divieto, [T] Trappola, [P] Pozzo.\n");

	/** parametro che definisce la modalita' di gioco
	 * -se hero_side = true, allora e' l'avventuriero a dover fuggire dal mostro (default);
	 * -se hero_side = false, allora e0 il mostro a dover scappare;
	 */
	private boolean hero_side;

	/** dimensioni della matrice
	 * -r e' il numero di righe
	 * -c e' il numero di colonne
	 * -n_cells rappresenta il numero complessivo delle celle della matrice, 
	 * 	dato dal prodotto delle due dimensioni, r e c.
	 */
	private int r = 4; //righe
	private int c= 4; //colonne
	//numero delle celle
	private int n_cells = r*c;

	/** matrice di gioco game_map
	 * si deve popolare la matrice secondo queste specifiche:
	 *-generare casualmente un numero tra 0 e 4 che rappresenta il numero di stanze giocabili;
	 *-inserire questo numero di caselle come "non accessibili-> denied";
	 *-inserire le caselle "fossa";
	 *-inserire la casella dove si trova il wumpus;
	 *-inserire la casella dove si trova l'oro;
	 *-inserire vicino il wumpus le caselle con la cella del vettore dei sensori "puzza";
	 *-inserire vicino le caselle fossa quelle che hanno la cella del vettore dei sensori "brezza";
	 *-inserire caselle safe, "libere-> prato";
	 */
	//inizializzazione della matrice di gioco 
	Cell[][] game_map= new Cell[r][c];
	
	/** matrice di esplorazione exploration_map 
	 * questa mappa viene popolata man mano che si gioca, esplorando la mappa di gioco
	 * (le celle non ancora visitate avranno il parametro isVisited = false (colore grigio)
	 */
	private Cell[][] exploration_map = new Cell[r][c];
	
	/** vettore degli elementi di gioco game_elements
	 * questo vettore di nove celle conterra' le informazioni riguarndanti gli elementi
	 * con cui dovra' essere popolata la mappa
	 * [celle] [n_max celle] [sassi] [n_max sassi] [oro] [eroe] [wumpus] [pozzi] [n_max pozzi]
	 */
	int [] game_elements= new int [9];
	
	
  
	/**costruttore di default GameMap()
	 * non riceve nessun parametro
	 * percio' di default la modalita' di gioco e' hero_side
	 */
	public GameMap() {
		//di default hero_side mode
		hero_side=true;
		//si inizializza la mappa di gioco e la mappa di esplorazione
		inizializeMaps();
	}//GameMap()
	
	/** costruttore GameMap(boolean)
	 * @param hero_side, valore booleano che se true indica la modalita' hero_side,
	 * 					 se false, indice la modalita' wumpus_side
	 * quindi la mappa di gioco verra' popolata in base alla modalita' di gioco scelta
	 */
	public GameMap(boolean hero_side) {
		//si specifica la modalita' di gioco
		this.hero_side=hero_side;
		//si inizializzano le due mappe di gioco
		inizializeMaps();
		//si definisce il vettore degli elementi di gioco 
		elementsVectorFilling();
		//si popola la matrice di gioco
		//popolaMappa();
	}//GameMap(boolean)

	/** metodo inizializeMaps(): void
	 * questo metodo si occupa di inizializzare la matrice di gioco
	 * ed anche la matrice associata alla mappa di esplorazione
	 * il valore di ogni cella allora sarà null, con il corrispettivo intero pari a -1
	 * e la variabile isVisited impostata a false. 
	 * Inoltre, il vettore dei sensori e' inizializzato a false, per ogni cella della mappa
	 * dal costruttore di default della stessa cella
	 */
	private void inizializeMaps() {
		//si itera per righe
		for(int i=0;i<r;i++) {
			//si itera per colonne
			for(int j=0;j<c;j++) {
				//viene istanziata ogni cella della matrice di gioco
				this.game_map[i][j]= new Cell();
				//viene istanziata ogni cella della mappa di esplorazione (popolata via via che si gioca)
				this.exploration_map[i][j]= new Cell();
			}//for colonne
		}//for righe 
	}//inizializeMaps()
	
	/** metodo ElvectorFilling(): void
	 * questo metodo e' utilizzato per riempire automaticamente il vettore degli elementi di 
	 * gioco che dovranno poi essere utilizzati per popolare la mappa
	 * @param elem_gioco
	 */
	private void elementsVectorFilling() {
		//numero massimo delle celle della mappa
		int max_n=n_cells;
		// numero di celle da riempire rimaste
		int n=max_n;
		//numero delle celle da riempire nella mappa di gioco
		game_elements[0]=n;
		//numero massimo delle caselle della mappa
		game_elements[1]=max_n;
		//si sceglie casualmente il numero di celle non giocabili (da 0 a 4)
		//ovvero il numero di celle non accessibili PIETRA		
		int d=(int)(Math.random()*5);//(da 0 a 5 escluso)
		//celle non giocabili DENIED
		game_elements[2]=d;
		//massimo numero di celle non giocabili DENIED
		game_elements[3]=d;
		//oro 
		int g=1;
		game_elements[4]=g;
		//avventuriero 
		int h=1;
		game_elements[5]=h;
		//mostro 
		int w=1;
		game_elements[6]=w;
		//pozzi da mettere nella mappa (hero_side = true)
		int p = 2; 
		int max_p = 2;
		//trappole da mettere al posto dei pozzi (hero_side = false)
		int t = p; 
		//numero massimo di trappole
		int max_t = max_p;
		//nel vettore 
		if(hero_side) {
			//hero_side
			game_elements[7]=p;
			game_elements[8]=max_p;
		}//fi
		else {
			//wumpus_side
			game_elements[7]=t;
			game_elements[8]=max_t;
		}//esle
	}//elementsVectorFilling
	
	//TODO prevedere il riempimento manuale specificando il valore di interesse per la cella
	//da modificare nel vettore degli elementi di gioco della mappa
	

	
	
	
	
	
	
	
	
	
	
	
	

	/** metodo toString() : String
	 * permette di stampare la disposizione delle celle sulla mappa
	 * stampando il loro contenuto secondo il metodo toString() definito per l'oggetto Cell
	 * @return print_map: String, stringa che rappresenta l'oggetto mappa da stampare a video.
	 */
	@Override
	public String toString() {
		//si crea la stringa che rappresenta la mappa da stampare
		String print_map = new String();
		for(int i=0; i<r; i++) {
			//si scorrono le righe della matrice
			print_map+=" |"; //si stampa l'inizio della riga
			for(int j=0; j<c; j++) {
				//si scorrono le colonne della matrice
				//si stampa il contenuto della cella 
				if(j<c-1) {
					print_map+=game_map[i][j]+ " ";
				}
				else {
					print_map+=game_map[i][j];
				}
			}//for colonne
			print_map+="|\n"; //si stampa la fine della riga e si va a capo
		}//for righe
		
		return legenda+"\n"+print_map;
	}//toString()
	
	/** metodo toStringExplorationMap() : String
	 * permette di stampare la disposizione delle celle sulla mappa di esplorazione
	 * per poter cos' vedere i progressi nel gioco
	 * ed eventualmente confrontarla con la mappa di gioco
	 * le celle della mappa sono stampate secondo il metodo toString() definito per l'oggetto Cell
	 * @return print_map: String, stringa che contiene la mappa, poi stampata a video.
	 */
	public String toStringExplorationMap() {
		//si crea la stringa che rappresenta la mappa da stampare
		String print_map = new String();
		for(int i=0; i<r; i++) {
			//si scorrono le righe della matrice
			print_map+=" |"; //si stampa l'inizio della riga
			for(int j=0; j<c; j++) {
				//si scorrono le colonne della matrice
				//si stampa il contenuto della cella 
				if(j<c-1) {
					print_map+=exploration_map[i][j]+ " ";
				}
				else {
					print_map+=exploration_map[i][j];
				}
			}//for colonne
			print_map+="|\n"; //si stampa la fine della riga e si va a capo
		}//for righe
		
		return legenda+"\n"+print_map;
	}//toString()
	
	/** elementsVectortoString() :String
	 * metodo che restituisce una stringa che rappresenta il contenuto del vettore
	 * degli elementi di gioco
	 * in questo modo potra' essere visualizzato a schermo tramite una stampa
	 * @param info: boolean, se true verra' stampata anche una legenda indicativa sul 
	 * 						 contenuto del vettore, se false soltanto il vettore.
	 */
	public String elementsVectorToString(boolean info) {
		//inizializzazione della stringa che conterra' gli elementi del vettore
		String game_el_vector = new String("");
		//si riempie questa stringa con fli elementi di interesse
		game_el_vector+=("| ");
		//si scorrono le celle del vettore
		for(int i=0;i<game_elements.length;i++) {
			//si stampa la cella i-esima
			game_el_vector+=("["+game_elements[i]+"] ");
		}//for
		game_el_vector+=("|\n");
		//controllo sul parametro
		if(info) {
			//si crea una stringa che contiene una legenda che chiarisce il contenuto
			//del vettore
			String legend=new String("[celle] [n_max celle] [sassi] [n_max sassi]"
					+ " [oro] [eroe] [wumpus] [pozzi] [n_max pozzi]\n");
			//allora si fa in modo che la stringa da stampare sia preceduta dalla legenda
			return new String(legend+game_el_vector);
		}//fi
		//altrimenti si stampa senza legenda
		return game_el_vector;
	}//elementsVectorToString

	/** metodo getMapNCells() : int
	 *la mappa di gioco e' una matrice di dimensione (r x c)
	 * @return n_cells,il numero di celle che costituiscono la mappa, 
	 * 		   cioe' il pordotto del numero di righe per il numero di colonne.
	 */
	public int getMapNCells() {
		return n_cells;
	}//getMapDimension
	
	/** metodo setMapDimension(int) : void
	 * si vuole definire una mappa di gioco che sia quadrata
	 * percio' la dimensione scelta sara' univoca, sia per il 
	 * numero di righe della matrice che per il numero di colonne
	 * @param d: int, specifica il numero di righe e colonne della matrice
	 */
	public void setMapDimension(int d) {
		//la mappa deve essere quadrata
		//e di dimensione che sia minimo 4 x 4
		if(d>=4) {
			//e' un valore valido
			//si possono aggiornare le dimensioni della mappa
			this.r=d;//righe
			this.c=d;//colonne
		}
		//altrimenti restano invariate
	}//setMapDimension
	
	/** metodo setMapDimensions(int, int) : void
	 * questo metodo permette di definire le dimensioni della matrice di gioco	 * 
	 * @param r: int, e' il numero di righe della matrice;
	 * @param c: int, e' il numero di colonne della matrice;
	 */
	public void setMapDimensions(int r, int c) {
		//la mappa deve essere quadrata
		//e di dimensione che sia minimo 4 x 4
		if(r>=4 && c>=4) {
			//i parametri sono validi percio' si aggiornano le dimensioni della mappa
			this.r=r;//righe
			this.c=c;//colonne	
		}//fi
		//altrimenti si lasciano invariate
	}//setMapDimension
	
	/** metodo setGameMode(boolean) : void
	 * metodo che permette di specificare la modalita' di gioco
	 * @param hero_side: boolean, se true indica la modalita' Eroe, 
	 * 							  se false, indica la modalita' Wumpus.
	 */
	public void setGameMode(boolean hero_side) {
		//si specifica la modalita' di gioco
		this.hero_side=hero_side;
	}//setGameMode
	
	/** metodo getGameMode() : boolean
	 * metodo che restituisce l'attributo relativo alla modalita' di gioco secondo cui
	 * e' stata strutturata la mappa
	 * @return hero_side: boolean, se true indica la modalita' Eroe (hero_side),
	 * 							   se false indica la modalita' Wumpus (!hero_side).
	 */
	public boolean getGameMode() {
		//restituzione del parametro di interesse
		return this.hero_side;
	}//getGameMode
}//GameMap
