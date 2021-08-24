package game.structure.map;
//import
import java.io.Serializable;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
/** classe GameMap
 * 
 * Questa classe implementa il terreno di gioco, che sara' popolato da
 * un nemico (il Wumpus nella modalita' hero_side oppure il Cacciatore
 * nella modalita' wumpus_side), il pg, il premio (che sara' una via di
 * fuga per il wumpus oppure un tesoro, in base alla modalita' di gioco)
 * e da due celle contenenti un pericolo, ovvero 2 trappole (nella modalita'
 * wumpus_side o 2 pozzi nella modalita' hero_side.
 * La mappa di gioco e' costituita da una matrice di dimensione r x c, in cui
 * ogni cella rappresenta una stanza da esplorare, collegata a quelle ad essa
 * adiacenti. Si precisa che non tutte le celle sono "giocabili" e che ci sara
 * un minimo di 14 stanze accessibili ed un massimo di 16.
 * Ogni stanza o istanza della classe Cell sara' collegata con con quelle ad 
 * essa adiacenti.
 * Si deve fare in modo, nella costruzione della mappa di gioco, che gli elementi
 * di interesse (premio, nemico, pg) siano raggiungibili, ovvero che non siano
 * circondati dalle celle DENIED, cioe' non accessibili, perche' rappresentano dei sassi.
 * Ogni cella della mappa di gioco e' identificata da un vettore di interi, che contiente
 * l'indice di riga e di colonna.
 * Di default, la matrice ha  dimensione (4 x 4)
 * 
 * 								| |0,0| |0,1| |0,2| |0,3| |
 * 								| |1,0| |1,1| |1,2| |1,3| |
 * 								| |2,0| |2,1| |2,2| |2,3| |
 * 								| |3,0| |3,1| |3,2| |3,3| |
 * @author ivonne
 */
public class GameMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5948888928665296259L;
	
	//##### attributi di classe #####
	
	//nuemro di righe della matrice
	private int r;
	//numero di colonne della matrice
	private int c;
	//matrice di gioco
	private Cell[][] game_map;
	
	//##### costruttori #####
	
	/**costruttore di default GameMap()
	 * non riceve nessun parametro
	 * inizializza la matrice di gioco, di dimensione 4 x 4,
	 * istanziando tutti le caselle di gioco, ovvero degli
	 * oggetti Cell.
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
	}//Map()

	/** costruttore GameMap(int, int)
	 * riceve come parametro il numero di righe e colonne che dovra' avere
	 * la matrice di gioco.
	 * @param rows: int, numero di righe;
	 * @param columns: int, numero di colonne;
	 */
	public GameMap(int rows, int columns) {
		//si controllano i parametri ricevuti come dimensioni
		if(rows<4 || columns<4)throw new IllegalArgumentException("dimensioni errate!");
		//righe
		this.r=rows;
		 //colonne
		this.c=columns;
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
	}//GameMap(int, nt)
	
	//##### metodi accessori: contenuto della mappa di gioco #####
	
	/** metodo getMapCell(int, int): Cell
	 * metodo accessorio che permette di accedere all'oggetto Cell, corrispondente
	 * alla posizione indicata dagli indici i e j, nella mappa di gioco.
	 * @param i: int, indice di riga dell'oggetto Cell;
	 * @param j: int, indice di colonna dell'oggetto Cell;
	 * @return game_map[i][j]: Cell, la cella contenuta nella mappa nella posizione (i,j)
	 */
	public Cell getMapCell(int i,int j) {
		//si restituisce la cella
		return game_map[i][j];
	}//getMapCell(int, int)
	
	/** metodo setMapCell(int, int, CellStatus)
	 * questo metodo modifica il contenuto della cella (i, j) con il
	 * parametro ricevuto come stato
	 * @param i: int, indice di riga della cella;
	 * @param j: int, indice di colonna della cella;
	 * @param status: CellStatus, nuova tipologia da assegnare alla cella;
	 */
	public void setMapCell(int i, int j, CellStatus status){
		//controllo sugli indici
		if(i<0 || i>r-1 || j<0 || j>c-1) throw new IllegalArgumentException("La cella cosi'"
				+ " indicata non esiste nella mappa.");
		if(status==null) throw new IllegalArgumentException("valore dello stato nullo");
		//si assegna il valore status alla cella
		game_map[i][j].setCellStatus(status);
	}//setGameCell(int, int, CellStatus)
	
	/** metodo cellExists(int, int): boolean
	 * questo metodo verifica se la cella indicata dagli indici (i,j) esiste
	 * nella mappa, ovvero se la coppia di indici rispetta la dimensione della
	 * matrice che descrive il terreno di gioco.
	 * @param i: int, indice riga;
	 * @param j: int, indice colonna;
	 * @return true, se la cella indicata dagli indici fa parte della matrice,
	 * 			false altrimenti.
	 */
	public boolean cellExists(int i, int j) {
		//controllo sull'indice della riga
		if(i<0 || i>= this.getRows())return false;
		//controllo sull'indice della colonna
		if(j<0 || j>= this.getColumns())return false;
		//indici di cella corretti
		return true;
	}//cellExists(int, int)
	
	//##### metodi accessori: struttura della mappa di gioco #####
	
	/** metodo getRows(): int
	 * questo metodo restiuisce il numero di righe della matrice che 
	 * costituisce la mappa di gioco.
	 * @return r: int, numero di righe della matrice.
	 */
	public int getRows() {
		//numero di righe
		return this.r;
	}//getRows()
	
	/** metodo getColumns(): int
	 * questo metodo restituisce il numero di colonne della matrice 
	 * che costiuisce la mappa di gioco.
	 * @return c:int, numero di colonne della matrice.
	 */
	public int getColumns() {
		//numero di colonne
		return this.c;
	}//getColumns()
	
	/** metodo getMapDimension() : int
	 * questo metodo restituisce la dimensione della mappa di gioco 
	 * @return r*c: int, la dimensione della matrice, ovvero il prodotto
	 * 				del numero di righe e colonne, cioe' il numero di celle
	 * 				che la compongono.
	 */
	public int getMapDimension() {
		//numero complessivo di celle della matrice
		return r*c;
	}//getMapDimension()
	
	/** metodo setMapDimension(int, int) : void
	 * questo metodo permette di definire le dimensioni della matrice di gioco
	 * @param r: int, e' il numero di righe della matrice;
	 * @param c: int, e' il numero di colonne della matrice;
	 */
	public void setMapDimensions(int r, int c) {
		//la mappa deve essere quadrata e di dimensione che sia minimo (4 x 4)
		if(r<4 || c<4)throw new IllegalArgumentException("Le dimensioni scelte per"
				+ " la matrice di gioco non sono valide.");
		//si aggiornano le dimensioni della mappa
		this.r=r;//righe
		this.c=c;//colonne	
	}//setMapDimension(int, int)
	
	/** metodo getGameMap(): Cell[][]
	 * questo metodo restituisce il puntatore alla matrice
	 * di gioco, costituita dagli oggetti di tipo Cell
	 * @return game_map: GameMap, restituisce l'attributo di classe che
	 * 					 rappresenta la matrice di oggetti Cell, cioe' la 
	 * 					 mappa di gioco.
	 */
	public Cell[][] getGameMap(){
		//mappa di gioco
		return game_map;
	}//getGameMap()
	
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
	
	//##### metodi di stampa a video #####
	
	/** metodo toString() : String
	 * permette di stampare la disposizione delle celle nella matrice di gioco.
	 * Il loro contenuto verra' stampato secondo il metodo toString() definito 
	 * per l'oggetto Cell.
	 * @return print_map: String, stringa che rappresenta il contenuto della mappa.
	 */
	@Override
	public String toString() {
		//si crea la stringa che rappresenta la mappa da stampare
		String print_map = new String();
		//si scorrono le righe della matrice
		for(int i=0; i<r; i++) {
			//si stampa l'inizio della riga
			print_map+=" |"; 
			//si scorrono le colonne della matrice
			for(int j=0; j<c; j++) {
				//si stampa il contenuto della cella 
				if(j<c-1) {
					//tutte le celle della riga tranne l'ultima
					print_map+=game_map[i][j]+ " ";
				}//fi
				else {
					//ultima cella della riga
					print_map+=game_map[i][j];
				}//else
			}//for colonne
			//si stampa la fine della riga e si va a capo
			print_map+="|\n"; 
		}//for righe
		//si restituisce la stringa
		return print_map;
	}//toString()
	
	/** metodo gameMapToString(): String
	 * questo metodo stampa la mappa di gioco visibile all'utente,
	 * quella di esplorazione, accompagnata da una legenda che esplica
	 * il contenuto delle celle ed i comandi da premere per muovere il pg.
	 * Serve per la mappa che rappresenta il terreno di gioco dopo
	 * che e' stato popolato di tutti gli elementi.
	 * @return tot:String, stringa che contiene la mappa e la legenda.
	 */
	 public String gameMaptoString() {
		 //intestazione
		String inizio = new String("       MAPPA				LEGENDA	\n"+
				 "			-----------------------------------------\n");
		//vettore colonna per la legenda del contenuto delle celle
		String [] v_leg = new String [4];
		v_leg[0] = "	| S = SPAZIO VUOTO | D = PERICOLO	|\n";
		v_leg[1] = "	| E = NEMICO       | F = SPAZIO VIETATO |\n";
		v_leg[2] = "	| P = GIOCATORE	   | A = PREMIO		|\n";
		v_leg[3] = "	-----------------------------------------\n";
		//si crea la stringa che rappresenta la mappa da stampare
		String print_map = new String();
		//si scorrono le righe della matrice
		for(int i=0; i<r; i++) {
			//si stampa l'inizio della riga
			print_map+=" |";
			//si scorrono le colonne della matrice
			for(int j=0; j<c; j++) {
				//si stampa il contenuto della cella 
				if(j<c-1) {
					//tutti glie elementi della riga tranne l'ultimo
					print_map+=game_map[i][j]+ " ";
				}//fi
				else {
					//ultimo elemento della riga
					print_map+=game_map[i][j];
				}//else
			}//for colonne
			//si stampa la fine della riga e si va a capo
			print_map+="|"+v_leg[i];
		}//for righe 
		//stringa complessiva
		return inizio+print_map;
	 }//gameMapToString()
	 
	/** metodo maptoStringAndLegend() : String
	 * permette di stampare la disposizione delle celle sulla mappa
	 * e la legenda esemplificativa del contenuto delle stesse.
	 * Serve per la mappa di esplorazione, visibile al giocatore.
	 * @return print_map: String, stringa che rappresenta l'oggetto mappa,
	 * 							  comprensiva di legenda dei comandi.
	 */
	public String mapToStringAndLegend() {
		//intestazione
		String inizio = new String("       MAPPA				LEGENDA	\n"+
				 "			                                         \n");
		//legenda del comandi
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
		//vettore colonna per la legenda del contenuto delle celle
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
		//si scorrono le righe della matrice
		for(int i=0; i<r; i++) {
			//si stampa l'inizio della riga
			print_map+=" |";
			//si scorrono le colonne della matrice
			for(int j=0; j<c; j++) {
				//si stampa il contenuto della cella 
				if(j<c-1) {
					//tutti gli elementi della riga tranne l'ultimo
					print_map+=game_map[i][j]+ " ";
				}//fi
				else {
					//ultimo elemento della riga
					print_map+=game_map[i][j];
				}//esle
			}//for colonne
			//si stampa la fine della riga e si va a capo
			print_map+="|"+v_leg[i]; 
		}//for righe
		//stringa complessiva
		tot += print_map + fine; 
		return tot;
	}//MaptoString()

	/** metodo maptoString() : String
	 * permette di stampare la disposizione delle celle sulla mappa
	 * e la legenda esemplificativa del contenuto delle stesse.
	 * Serve per la mappa di esplorazione, visibile al giocatore.
	 * @return print_map: String, stringa che rappresenta l'oggetto mappa.
	 */
	public String mapToString() {
		//intestazione
		String inizio = new String("       MAPPA				LEGENDA	\n"+
				 "			                                         \n");
		//vettore colonna per la legenda del contenuto delle celle
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
		//si scorrono le righe della matrice
		for(int i=0; i<r; i++) {
			//si stampa l'inizio della riga
			print_map+=" |";
			//si scorrono le colonne della matrice
			for(int j=0; j<c; j++) {
				//si stampa il contenuto della cella 
				if(j<c-1) {
					//tutti gli elementi della riga tranne l'ultimo
					print_map+=game_map[i][j]+ " ";
				}//fi
				else {
					//ultimo elemento della riga
					print_map+=game_map[i][j];
				}//esle
			}//for colonne
			//si stampa la fine della riga e si va a capo
			print_map+="|"+v_leg[i]; 
		}//for righe
		//stringa complessiva
		tot += print_map; 
		return tot;
	}//maptoString()

	/** metodo areAdjacentCellsSafe(int, int)
	 * questo metodo verifica se le celle adiacenti a quella indicata
	 * dalla coppia di indici ricevuta come parametro siano libere
	 * oppure no, in modo da garantire che la cella di interesse
	 * sia circondata almeno da una SAFE.
	 * @param i: int, indice di riga della cella di interesse;
	 * @param j: int, indice della colonna della cella di interesse;
	 * @return true, se la cella di interesse e' circondada da almeno
	 * 				 una cella libera, false altrimenti.
	 */
	public boolean areAdjacentCellsSafe(int i, int j) {
		//variabile ausiliaria
		CellStatus cs;
		//controllo
		if(this.cellExists(i-1, j)) {
			cs = this.getMapCell(i-1, j).getCellStatus();
			return cs.equals(CellStatus.SAFE);
		}//fi cella sopra
		if(this.cellExists(i+1, j)) {
			cs = this.getMapCell(i+1, j).getCellStatus();
			return cs.equals(CellStatus.SAFE);
		}//fi cella sotto
		if(this.cellExists(i, j-1)) {
			cs = this.getMapCell(i, j-1).getCellStatus();
			return cs.equals(CellStatus.SAFE);
		}//fi cella a sinistra
		if(this.cellExists(i, j+1)) {
			cs = this.getMapCell(i, j+1).getCellStatus();
			return cs.equals(CellStatus.SAFE);
		}//fi cella a destra
		//non ci sono celle adiacenti a quella data con status uguale a SAFE
		return false;
	}//areAdjacentCellsSafe(int, int)

	
}//end GameMap
