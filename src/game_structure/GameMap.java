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
	
	/** metodo setGameElement(int, int): void
	 * questo metodo ha il ruolo di impostare il contenuto della cella del vettore
	 * degli elementi di gioco che verranno posizionati sulla mappa
	 * Gli elementi di cui potranno esserne modificate le quantita' saranno:
	 * -il numero massimo di pozzi, che corrisponde al numero di trappole;
	 * -il numero di pozzi rimasti da posizionare;
	 * -il numero massimo di sassi, che corrisponde al numero di celle non giocabili;
	 * -il numero di sassi rimasti da posizionare
	 * questi tre valori, per scelta progettuale, non potranno ne' essere negativi
	 * ne' essere superiori a quattro.
	 * @param i: int, e' l'indice che indica la cella da riempire;
	 * @param elem: int, e' il valore del contenuto della cella in questione;
	 */
	public void setGameElement(int i, int elem) {
		//controllo sull'indice di cella
		if(i==3 || i==8) {
			//se l'indice di cella e' corretto si puo' svolgere un controllo
			//sul parametro che ne descrive il contenuto da modificare
			if(elem>=0 && elem<=4) {
				//il contenuto scelto e' un numero valido
				//si puo' impostare il contenuto
				game_elements[i]=elem;
				//eventualmente aggiornare il numero dei sassi rimasti
				if(i==3) {
					//la cella precedente contiene il numero di sassi che 
					//e' ancora possibile posizionare
					game_elements[i-1]=elem;
				}//if sassi
				//eventualmenten aggiornare il numero dei pozzi rimasti
				if(i==8) {
					//la cella precedente contiene il numero di pozzi che 
					//e' ancora possibile posizionare
					game_elements[i-1]=elem;
				}//if pozzi
			}//fi
			else {
				//il contenuto ricevuto in ingresso non e' valido
				System.out.println("Il contenuto della cella "+elem+" non e' valido");
			}//esle
		}//fi
		else {
			//l'indice di cella non e' tra quelle modifcabili
			System.out.println("L'indice di cella "+i+" non e' corretto");
		}
	}//setGameElement

	/** metodo chooseCell(Cell): CellStatus
	 * questo metodo specifica la tipologia da assegnare all'oggetto Cell
	 * che e' rappresentato da game_map[i][j]
	 * nel metodo si utilizza il seme seed, un numero casuale che rappresenta un 
	 * valore di soglia con cui confrontare la probabilita' ottenuta per la cella
	 * @param cell: Cell, oggetto cella per cui deve essere assegnato il tipo, 
	 * 					  definito dalla enumerazione CellStatus
	 * @return status: CellStatus, ovvero l'enumerazione che e' stata assegnata alla cella in questione.
	 */
	private CellStatus chooseCell(Cell cell) {
		//variabili da utilizzare per il calcolo
		//si calcola la soglia con cui confrontare la probabilita' della cella
		double seed = (Math.random());//numero tra 0 e 1 escluso
		//modalita' eroe
		if(hero_side) {
			/* PIT
			 * -seed e' il valore di soglia con cui confrontare la probabilita' ottenuta
			 * -x = elem_gioco[7] e' il numero di pozzi rimasti da posizionare;
			 * -max_x = elem_gioco[8] e' il numero totale e massimo di pozzi;
			 * -n = elem_gioco[0] e' il numero di celle rimaste da riempire;
			 * -n_max = elem_gioco[1] e' il numero massimo di celle che costituiscono la mappa;
			 */
			if(game_elements[7]>0 && isPit(seed,game_elements[7],game_elements[8],game_elements[0],game_elements[1])) {
				//la cella e' un pozzo
				//quindi si devono aggiornare i parametri
				//n -> una celle e' stata occupata percio' il numero di celle libere e' diminuito
				game_elements[0]-=1; 
				//p -> un pozzo e' stato assegnato, percio' il numero di pozzi da collocare e' diminuito
				game_elements[7]-=1;
				//si stampa l'attuale vettore degli elementi di gioco
				//si assegna l'etichetta alla cella
				return CellStatus.PIT;
			}
			//MOSTRO
			else if(game_elements[6]>0 && isWumpus(seed,game_elements[6],game_elements[0],game_elements[1])) {
				//si aggiornano i parametri
				game_elements[6]-=1;
				//si riducono le celle libere
				game_elements[0]-=1;
				//nella cella e' stato messo il wumpus
				return CellStatus.WUMPUS;
			}
			//ORO
			else if(game_elements[4]>0 && isGold(seed,game_elements[4],game_elements[0],game_elements[1])) {
				//so aggiornano i parametri
				game_elements[4]-=1;
				//si riduce il numero di celle occupate
				game_elements[0]-=1;
				return CellStatus.GOLD;
			}
			//PIETRA
			else if(game_elements[2]>0 && isStone(seed,game_elements[2],game_elements[3],game_elements[0],game_elements[1])) {
				//si aggiornano i parametri
				game_elements[2]-=1;
				//si riduce il numero delle celle libere
				game_elements[0]-=1;
				//la cella e' una PIETRA
				return CellStatus.DENIED;
			}
			//EROE
			else if(game_elements[5]>0 && isHero()){
				//TODO controllare in quale posizione viene messo
				//sono ammessi solo i bordi
				//si aggiornano i parametri
				game_elements[5]-=1;
				//si riduce il numero delle celle libere
				game_elements[0]-=1;
				//la cella contiene l'eroe
				return CellStatus.HERO;
			}
			//SAFE
			else {
				//allora e' una cella sicura
				System.out.println("Cella SAFE\n");
				//si aggiornano i parametri
				game_elements[0]-=1;
				//numero celle da riempire
				return CellStatus.SAFE;
			}
		}//fi hero_side
		else {
			//modalita' wumpus
			//TODO posizionare il wumpus per ultimo perche' rappresenta il giocatore
			//e prima tutti gli altri oggetti
			return null;
		}
	}//chooseCell
	
	/** metodo isWumpus(double, int, int, int, int): boolean
	 * questo metodo si occupa di confrontare la probabilita' calcolata per la 
	 * cella in questione con il valore di soglia, per stabilire se in essa possa
	 * essere posizionato o meno il wumpus
	 * @param seed: double, valore di soglia, scelto in maniera casuale, con cui confrontare 
	 * 						la probabilita' calcolata per la cella attuale;
	 * @param x: int, rappresenta l'oggetto wumpus, nel vettore e' la cella game_elements[6];
	 * @param n: int, rappresenta il numero di celle che ancora possono essere riempite
	 * 				  nella mappa, nel vettore e' la cella game_elements[0];
	 * @param n_max: int, descrive il numero massimo di cella di cui e' composta la matrice,
	 * 					  nel vettore e' la cella game_elements[1];
	 * @return boolean, sara' true se il wumpus e' stato posiziato nella cella esaminata,
	 * 					sara' false altrimenti.
	 */
	private boolean isWumpus(double seed, int x, int n, int n_max) {
		//valore di soglia
		double prob=lambda(x, x, n, n_max);
		//stampe di debug
		System.out.println("Probabilita' wumpus "+prob);
		System.out.println("Soglia wumpus "+seed+"\n");
		//confronto della probabilita' con il valore di soglia
		if(prob>=seed) {
			//allora si puo' posizionare il wumpus
			System.out.println("Il Wumpus e' stato posizionato!\n");
			return true;
		}
		//la cella non conterra' il mostro
		return false;
	}//isWumpus

	/** metodo isGold(double, int, int, int): boolean
	 * questo metodo si occupa di confrontare la probabilita' calcolata per la 
	 * cella in questione con il valore di soglia, per stabilire se in essa possa
	 * essere posizionato o meno l'oro
	 * @param seed: double, valore di soglia, scelto in maniera casuale, con cui confrontare 
	 * 						la probabilita' calcolata per la cella attuale;
	 * @param x: int, rappresenta l'oggetto oro, nel vettore e' la cella game_elements[4];
	 * @param n: int, rappresenta il numero di celle che ancora possono essere riempite
	 * 				  nella mappa, nel vettore e' la cella game_elements[0];
	 * @param n_max: int, descrive il numero massimo di cella di cui e' composta la matrice,
	 * 					  nel vettore e' la cella game_elements[1];
	 * @return boolean, sara' true se  l'oro e' stato posiziato nella cella esaminata,
	 * 					sara' false altrimenti.
	 */
	private boolean isGold(double seed, int x, int n, int n_max) {
		//valore di soglia
		double prob=lambda(x, x, n, n_max);
		//stampe di debug
		System.out.println("Probabilita' oro "+prob);
		System.out.println("Soglia oro "+seed+"\n");
		//confronto della probabilita' con il valore di soglia
		if(prob<=seed) {
			//allora si puo' posizionare l'oro
			System.out.println("L'oro e' stato posizionato!\n");
			return true;
		}
		//la cella non conterra' l'oro
		return false;
	}//isGold

	/** metodo isStone(double, int, int, int): boolean
	 * questo metodo si occupa di confrontare la probabilita' calcolata per la 
	 * cella in questione con il valore di soglia, per stabilire se in essa possa
	 * essere posizionato o meno la cella PIETRA, cioe' la cella non giocabile, DENIED.
	 * @param seed: double, valore di soglia, scelto in maniera casuale, con cui confrontare 
	 * 						la probabilita' calcolata per la cella attuale;
	 * @param x: int, rappresenta l'oggetto pietra, nel vettore e' la cella game_elements[2];
	 * @param x_max: int, rappresenta il numero massimo di oggetti pietra che possono essere
	 * 					  posizionati, nel vettore e' la cella game_elements[3];
	 * @param n: int, rappresenta il numero di celle che ancora possono essere riempite
	 * 				  nella mappa, nel vettore e' la cella game_elements[0];
	 * @param n_max: int, descrive il numero massimo di cella di cui e' composta la matrice,
	 * 					  nel vettore e' la cella game_elements[1];
	 * @return boolean, sara' true se la pietra e' stato posiziata nella cella esaminata,
	 * 					sara' false altrimenti.
	 */
	private boolean isStone(double seed, int x, int x_max, int n, int n_max) {
		//valore di soglia
		double prob=lambda(x, x_max, n, n_max);
		//stampe di debug
		System.out.println("Probabilita' pietra "+prob);
		System.out.println("Soglia pietra "+seed+"\n");
		//confronto della probabilita' con il valore di soglia
		if(prob<=seed) {
			//allora si puo' posizionare l'oro
			System.out.println("Un sasso e' stato posizionato!\n");
			return true;
		}
		return false;
	}//isStone
	
	/**
	 * 
	 * @param seed
	 * @param x_pit
	 * @param x_max_pit
	 * @param n
	 * @param n_max
	 * @return
	 */
	private boolean isPit(double seed, int x_pit, int x_max_pit, int n, int n_max) {
		/* parametri:
		 *-seed, e' il valore di soglia con cui confrontare la probabilita'
		 * calcolata per la cella;
		 *-x_pit, e' il numero di pozzi che ancora devono essere messi nella mappa;
		 *-x_max_pit, e' il numero massimo di pozzi da mettere nella mappa;
		 *-n, e' il numeor di celle della mappa che ancora devono essere riempite;
		 *-n_max, il numero di caselle da cui e' composta la mappa di gioco.
		 */
		//calcolo della probabilita' per cui la cella possa essere
		//classificata come PIT in base ai parametri forniti
		double prob=lambda(x_pit, x_max_pit, n, n_max);
		System.out.println("Probabilita' pozzo "+prob);
		System.out.println("Soglia pozzo "+seed+"\n");
		//confronto con il valore di soglia
		//TODO controllare criterio
		if(Math.abs(prob)<=seed) {
			System.out.println("Pozzo posizionato!\n");
			//allora la casella sara' etichettata come PIT
			return true;
		}
		//se la soglia non e' stata rispettata allora questa
		//cella non sara' un pozzo
		return false;
	}//isPit
	
	/**
	 * 
	 * @return
	 */
	private boolean isHero() {
		return true;
	}
	
	/**
	 * 
	 * @param x
	 * @param max_x
	 * @param n
	 * @param max_n
	 * @return
	 */
	//funzione di probabilita' utilizzata per stabilire il valore che deve assumere la cella di interesse
	private static double lambda(int x,int max_x, int n, int max_n) {
		/* - x, numero del tipo di oggetti cella da inserire;
		 * - max_x, numero massimo di oggetti di tipo x che possono essere inseriti nella mappa;
		 * - n, numero di celle della mappa che devono essere ancora riempite;
		 * - max_n, numero massimo di celle della mappa che possono essere riempite (giocabili).
		 */
		double prob = ((x/max_x)) - (1-n/max_n);
		//System.out.println(prob);
		//probabilita'
		return prob;
	}//lambda

	
	
	
	
	
	
	
	
	

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
