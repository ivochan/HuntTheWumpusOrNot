package game.structure.map;

import game.structure.cell.Cell;
import game.structure.cell.CellStatus;

/** classe GameMap
 * 
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
 * @author ivonne
 */
public class GameMap {
	
	/** dimensioni della matrice
	 * -r e' il numero di righe
	 * -c e' il numero di colonne
	 * -n_cells rappresenta il numero complessivo delle celle della matrice, 
	 * 	dato dal prodotto delle due dimensioni, r e c.
	 */
	private int r; //righe
	private int c; //colonne
	
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
	private Cell[][] game_map;
	
	/**costruttore di default GameMap()
	 * non riceve nessun parametro percio' di defaul la matrice sara' (4 x 4)
	 * si occupa di inizializzare la matrice di gioco, istanziando tutti
	 * gli oggetti Cell che andranno a costituire le caselle di gioco,
	 * richamando il costruttore di default Cell().
	 */
	public GameMap() {
		//si impostano le dimensioni della mappa
		this.r=4; //righe
		this.c=4; //colonne
		//inizializzazione della matrice di gioco 
		this.game_map = new Cell[r][c];
		//si itera per righe
		for(int i=0;i<r;i++) {
			//si itera per colonne
			for(int j=0;j<c;j++) {
				//viene istanziata ogni cella della matrice di gioco
				this.game_map[i][j]= new Cell();
			}//for
		}//for
	}//GameMap()

	/** costruttore GameMap(int, int)
	 * riceve come parametro il numero di righe e colonne che dovra' avere
	 * la matrice di gioco.
	 * @param rows: int, numero di righe;
	 * @param columns: int, numero di colonne;
	 */
	public GameMap(int rows, int columns) {
		//si controllano i parametri ricevuti come dimensioni
		if(rows<4 || columns<4) {
			System.out.println("dimensioni errate! verranno scelti i valori di default");
			//righe
			this.r=4;
			//colonne
			this.c=4;
		}
		else {
			//i parametri sono idonei
			this.r=rows; //righe
			this.c=columns; //colonne
		}//esle
		//System.out.println("righe "+r+" colonne "+c);
		//inizializzazione della matrice di gioco 
		this.game_map = new Cell[r][c];
		//si itera per righe
		for(int i=0;i<r;i++) {
			//si itera per colonne
			for(int j=0;j<c;j++) {
				//viene istanziata ogni cella della matrice di gioco
				this.game_map[i][j]= new Cell();
			}//for
		}//for
	}//GameMap()
	
	/** metodo getRows(): int
	 * questo metodo restiuisce il numero di righe della matrice che 
	 * costituisce la mappa di gioco.
	 * @return r: int, numero di righe della matrice.
	 */
	public int getRows() {
		//numero di righe
		return this.r;
	}//getR()
	
	/** metodo getColumns(): int
	 * questo metodo restituisce il numero di colonne della matrice 
	 * che costiuisce la mappa di gioco.
	 * @return c:int, numero di colonne della matrice.
	 */
	public int getColumns() {
		//nuemro di colonne
		return this.c;
	}//getC()
	
	/** metodo getMapNCells() : int
	 * la mappa di gioco e' una matrice di dimensione (r x c)
	 * @return n_cells,il numero di celle che costituiscono la mappa, 
	 * 		   cioe' il prodotto del numero di righe per il numero di colonne.
	 */
	public int getNCells() {
		//numero complessivo di celle che costiuiscono la matrice
		return r*c;
	}//getNCells()
	
	/** metodo setMapDimensions(int, int) : void
	 * questo metodo permette di definire le dimensioni della matrice di gioco
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
		
		return print_map;
	}//toString()
	
	/** metodo MaptoString() : String
	 * permette di stampare la disposizione delle celle sulla mappa
	 * stampando il loro contenuto secondo il metodo toString() definito per l'oggetto Cell
	 * Viene stampata la legenda del contenuto delle celle
	 * @return print_map: String, stringa che rappresenta l'oggetto mappa da stampare a video.
	 */
	public String mapToString() {
		String inizio = new String("       MAPPA				LEGENDA	\n"+
				 "			                                         \n");
		String fine = new String(" 												 \n"+
				 " Comandi:										 \n"+
				 " 												 \n"+
				 " w = sopra									 \n"+
				 " a = sinistra									 \n"+
				 " s = sotto									 \n"+
				 " d = destra									 \n"+
				 " i = interrompi partita						 \n"+
				 " l = prova a colpire il nemico                 \n"+
				 "												 \n");
		//vettore colonna per la legenda
		String [] v_leg = new String [4];
		v_leg[0] = "       --------------------------------------------\n";
		v_leg[1] = "	| X = LUOGO DA VISITARE | O = LUOGO VISITATO |\n";
		v_leg[2] = "	| P = GIOCATORE         | F = LUOGO VIETATO  |\n";
		v_leg[3] = "       --------------------------------------------\n";
		//stringa da stampare
		String tot = new String();
		tot+=inizio;
		//si crea la stringa che rappresenta la mappa da stampare
		String print_map = new String();
		//iterazione sulla matrice di gioco
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
			print_map+="|"+v_leg[i]; //si stampa la fine della riga e si va a capo
		}//for righe
		tot += print_map + fine; 
		return tot;
	}//toString()
	
	
	/** metodo getGameCell(int, int): Cell
	 * metodo accessorio che permette di accedere all'oggetto Cell, corrispondente
	 * alla posizione indicata dagli indici i e j, nella mappa di gioco.
	 * @param i: int, indice di riga dell'oggetto Cell;
	 * @param j: int, indice di colonna dell'oggetto Cell;
	 * @return game_map[i][j]: Cell, la cella contenuta nella mappa nella posizione (i,j)
	 */
	public Cell getGameCell(int i,int j) {
		//controllo sugli indici
		if(i<0 || i>r-1 || j<0 || j>c-1) {
			//System.err.println("La cella cosi' indicata non esiste nella mappa.");
			//TODO
			return null;
		}
		else {
			return game_map[i][j];
		}
	}//getCellMap()
	
	public void setGameCell(int i, int j, CellStatus status){
		//controllo sugli indici
		if(i<0 || i>r-1 || j<0 || j>c-1) {
			System.err.println("La cella cosi' indicata non esiste nella mappa.");
		}
		else {
			if(status!=null){
				game_map[i][j].setCellStatus(status);
			}
			else {
				System.err.println("parametro di stato della cella non valido.");
			}
		}
	}//setGameCell()
	
	/** metodo clear(): void
	 * questo metodo consente di cancellare le informazioni con cui sono
	 * state caratterizzate tutte le celle della mappa di gioco, in modo
	 * da ripristinarla allo stato iniziale.
	 */
	public void clear() {
		//si itera per righe
		for(int i=0;i<r;i++) {
			//si itera per colonne
			for(int j=0;j<c;j++) {
				//viene istanziata ogni cella della matrice di gioco
				game_map[i][j]=new Cell();
			}//for colonne
		}//for righe 
	}//clear()

	/** metodo getGameMap(): Cell[][]
	 * questo metodo restituisce il puntatore alla matrice
	 * di gioco, costiuita dagli oggetti di tipo Cell
	 * @return game_map: GameMap, restituisce l'attributo di classe che
	 * 					 rappresenta la matrice di oggetti Cell, cioe' la 
	 * 					 mappa di gioco.
	 */
	public Cell[][] getGameMap(){
		//mappa di gioco
		return game_map;
	}//getGameMap()

	/** metodo mapAndLegend(): String
	 * questo metodo stampa la mappa di gioco visibile all'utente,
	 * quella di esplorazione, accompagnata da una legenda che esplica
	 * il contenuto delle celle ed i comandi da premere per muovere il pg.
	 * @return tot:String, stringa che contiene la mappa e la legenda.
	 */
	 public String mapAndLegend() {
		String inizio = new String("       MAPPA				LEGENDA	\n"+
				 "			-----------------------------------------\n");
		//vettore colonna per la legenda
		String [] v_leg = new String [4];
		v_leg[0] = "	| S = SPAZIO VUOTO | D = PERICOLO	|\n";
		v_leg[1] = "	| E = NEMICO       | F = SPAZIO VIETATO |\n";
		v_leg[2] = "	| P = GIOCATORE	   | A = PREMIO		|\n";
		v_leg[3] = "	-----------------------------------------\n";
		//stringa da stampare
		String tot = new String();
		tot+=inizio;
		//si crea la stringa che rappresenta la mappa da stampare
		String print_map = new String();
		//iterazione sulla matrice di gioco
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
			print_map+="|"+v_leg[i]; //si stampa la fine della riga e si va a capo
		}//for righe 
		return inizio+print_map;
	 }//mapAndLegend()
				
	

}//GameMap
