package game_structure;

import java.util.Random;

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
 * Ogni cella della mappa di gioco e' identificata da un vettore di interi, che contiente
 * l'indice di riga e di colonna.
 * 								| |0,0| |0,1| |0,2| |0,3| |
 * 								| |1,0| |1,1| |1,2| |1,3| |
 * 								| |2,0| |2,1| |2,2| |2,3| |
 * 								| |3,0| |3,1| |3,2| |3,3| |
 * 
 */
public class GameMap {
	//legenda
	private String legenda = new String("Questa e' la mappa di gioco! :)\n"
			+ "Il significato delle caselle e' il seguente:\n"
			+ "[G] Oro, [S] Sicura, [W] Wumpus,[H] Avventuriero, [D] Divieto, [T] Trappola, [P] Pozzo.\n");

	/** parametro che definisce la modalita' di gioco
	 * -se hero_side = true, allora e' l'avventuriero a dover fuggire dal mostro (default);
	 * -se hero_side = false, allora e' il mostro a dover scappare;
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
	//int [] game_elements= new int [9];
	// [celle 0] [sassi 1] [oro 2] [eroe 3] [wumpus 4] [pozzi o trappole 5]
	int [] game_elements= new int [6];
	
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
		System.out.println(elementsVectorToString(false));
		//si popola la matrice di gioco
		fillingGameMap();
	
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
	
	/** metodo clear(): void
	 * questo metodo consente di cancellare le informazioni con cui sono
	 * state caratterizzate tutte le celle della mappa di gioco, in modo
	 * da ripristinarla allo stato iniziale.
	 */
	private void clear() {
		//si itera per righe
		for(int i=0;i<r;i++) {
			//si itera per colonne
			for(int j=0;j<c;j++) {
				//viene istanziata ogni cella della matrice di gioco
				this.game_map[i][j]= new Cell();
				this.game_map[i][j].setCellPosition(-1,-1);
			}//for colonne
		}//for righe 
	}//clear()
	
	/** metodo elementsVectorFilling(): void
	 * questo metodo e' utilizzato per riempire automaticamente il vettore degli elementi di 
	 * gioco che dovranno poi essere utilizzati per popolare la mappa
	 * @param elem_gioco
	 */
	private void elementsVectorFilling() {
		//TODO test
		//[celle 0] [sassi 1] [oro 2] [eroe 3] [wumpus 4] [pozzi o trappole 5]
		//numero massimo delle celle della mappa
		//TODO int max_n=n_cells;
		// numero di celle da riempire rimaste
		//TODO int n=max_n;
		//numero delle celle da riempire nella mappa di gioco
		//TODO game_elements[0]=n;
		//TODO test
		game_elements[0]=n_cells;
		//numero massimo delle caselle della mappa
		//TODO game_elements[1]=max_n;
		//si sceglie casualmente il numero di celle non giocabili (da 0 a 2)
		//ovvero il numero di celle non accessibili PIETRA		
		int d=(int)(Math.random()*3);//(da 0 a 3 escluso)
		//celle non giocabili DENIED
		//TODO game_elements[2]=d;
		//massimo numero di celle non giocabili DENIED
		//TODO game_elements[3]=d;
		//TODO test
		game_elements[1]=d;
		//oro 
		int g=1;
		//TODO game_elements[4]=g;
		//TODO test
		game_elements[2]=g;
		//avventuriero 
		int h=1;
		//TODO game_elements[5]=h;
		//TODO test
		game_elements[3]=h;
		//mostro 
		int w=1;
		//TODO game_elements[6]=w;
		//TODO test
		game_elements[4]=w;
		//pozzi da mettere nella mappa (hero_side = true)
		int p = 2; 
		//TODO int max_p = 2;
		//trappole da mettere al posto dei pozzi (hero_side = false)
		//TODOint t = p; 
		//numero massimo di trappole
		//TODO int max_t = max_p;
		//nel vettore 
		if(hero_side) {
			//hero_side
			//TODO game_elements[7]=p;
			//TODO game_elements[8]=max_p;
			//TODO test
			game_elements[5]=p;
		}//fi
		else {
			//wumpus_side
			//TODO game_elements[7]=t;
			//TODO game_elements[8]=max_t;
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
		
	
	/** metodo porb(int, int, int, int): void
	 * questo metodo calcola, sfruttando la funzione cosi' definita:
	 * ((x/max_x) - (n/max_n) + random*0.3) /3
	 * dove random, calcolato usando la funzione Math.random(), e' un numero casuale
	 * utilizzato per dare un po' di varianza alla funzione stessa, moltiplicato per 0.3
	 * dividendo tutto quanto per 3.
	 * Dopo aver effettuato il calcolo, questa probilita' viene restituita per essere confrontata
	 * con il valore di soglia e stabilire cosi' se la cella in esame possa essere etichetta
	 * con la tipologia che si sta considerando.
	 * @param x: int, e' il numero di oggetti di tipo X che sono rimasti da posizionare nella mappa;
	 * @param max_x: int, e' il numero massimo di oggetti di tipo X che si possono posizionare
	 * 					  complessivamente nella mappa;
	 * @param n: int, e' il numero di celle della mappa che devono essere ancora riempite;
	 * @param max_n: int, e' il numero massimo di celle che compongono la mappa di gioco.
	 * @return prob: double, il valore di probabilita' che, in base ai parametri ricevuti, e' stato 
	 * 						 calcolato per la cella in esame.
	 */
	private double prob(int x,int max_x, int n, int max_n) {
		//controllo sui parametri
		if(max_x==0 || max_n==0)return 0;
		//numero casuale
		double random = Math.random();
		//funzione di probabilita'
		double prob = ((x/max_x) - (n/max_n) + random*0.3) /3;
		return prob;
	}//prob
	
	//TODO TEST METODO RIEMPIMENTO MAPPA
	private void fillingGameMap() {
		//[celle 0] [sassi 1] [oro 2] [eroe 3] [wumpus 4] [pozzi 5]
		//parametri da verificare
		int n_pozzi=game_elements[5];
		int pozzi=n_pozzi;
		
		int n_sassi=game_elements[1];
		int sassi=n_sassi;
		
		int n_wumpus=game_elements[4];
		int wumpus=n_wumpus;
		
		int n_oro=game_elements[2];
		int oro=n_oro;
		
		int n_celle=game_elements[0]; 
		int celle=n_celle;
		//variabili che conterranno la probabilita'
		double ppozzi=0;
		double pwumpus=0;
		double poro=0;
		double psassi=0;
		
		//ciclo di riempimento della mappa
		while( pozzi!=0 || wumpus!=0 || oro!=0 || sassi!=0) {
			//riassegnare alle variabili i valori di default
			oro=n_oro;
			pozzi=n_pozzi;
			wumpus=n_wumpus;
			celle=n_celle;
			sassi=n_sassi;
			//svuotare la matrice
			clear();
			
			for(int i=0;i<r;i++) {
				for(int j=0;j<c;j++) {
					
					//si genera un numero casuale
					double random = Math.random();
				//calcolo delle probabilita' per ogni tipologia di cella
					ppozzi = prob(pozzi, n_pozzi, celle, n_celle);
					pwumpus = prob(wumpus, n_wumpus, celle, n_celle);
					poro = prob(oro, n_oro, celle, n_celle);
					psassi = prob(sassi, n_sassi, celle, n_celle);
					//confronto delle probabilita' con la soglia random
					if(random < ppozzi) {
						game_map[i][j]=new Cell(CellStatus.PIT);
						game_map[i][j].setCellPosition(i, j);
						pozzi=pozzi-1;
					}
					else if(random < pwumpus) {
						game_map[i][j]= new Cell(CellStatus.WUMPUS);
						game_map[i][j].setCellPosition(i, j);
						wumpus=wumpus-1;
					}
					else if(random < poro) {
						game_map[i][j]= new Cell(CellStatus.GOLD);
						game_map[i][j].setCellPosition(i, j);
						oro=oro-1;
					}
					else if(random < psassi) {
						game_map[i][j]= new Cell(CellStatus.DENIED);
						game_map[i][j].setCellPosition(i, j);
						sassi=sassi-1;
					}
					else {
						game_map[i][j]= new Cell(CellStatus.SAFE);
						game_map[i][j].setCellPosition(i,j);
					}
					//si decrementa il numero di celle rimaste da 
					//riempire
					celle=celle-1;	
					//System.out.println("Cella "+game_map[i][j]);
					//System.out.println(elementsVectorToString(false));
				}//for colonne
			}//for righe
			//System.out.println("pozzir "+pozzi+" wumpusr "+wumpus+" oror "+oro+" sassi "+sassi);	
		}//while
	}				
	
	//TODO posizionare l'eroe metodo metti eroe nella matrice popolata
	//scegliere un numero casuale da 0 a 12
	//vedere a quale cella corrisponde 
	//indici da prendere i*2 e i*2+1
	//vettore grande il doppio
	
	//TODO metodo complessivo che crea la mappa di gioco
	//tutto in un while 
	//se non trovo una cella vuota lungo la cornice, dove mettere l'eroe
	//devo ripopolare la mappa
	
	
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
