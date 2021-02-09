 package game.structure.cell;

/** class Cell
 * Questa classe Cell implementa la cella di una matrice
 *	il cui contenuto cambia in base alla enum CellStatus
 *	e secondo altre proprieta' stabilite in questa classe
 *	@author ivonne
 */
public class Cell {
	
	//vettore dei sensori H: STINK, BREEZE
	//vettore dei sensori W: CREAK, SWISH
	private boolean[] sense_vector = new boolean[2];

	//contenuto della cella sulla base della enum CellStatus
	private CellStatus status;
	//indica se la cella e' stata visitata o meno
	private boolean isVisited;
	//vettore che indice la posizione [i][j] della cella nella matrice di gioco
	private int[] position= {-1, -1};


	/** Cell()
	 * costruttore di default
	 * istanzia un oggetto cella vuoto, con soltanto 
	 * caratteristiche di default
	 */
	public Cell() {
		//le caratteristiche della cella non sono state specificate
		//tipologia di cella
		this.status=null;
		//cella non visitata dal pg
		this.isVisited=false;
		//inizializzazione vettore dei sensi
		this.sense_vector[0]=false;
		this.sense_vector[1]=false;
	}//Cell()

	/** costruttore Cell(CellStatus)
	 * questo costruttore crea un oggetto Cell, specificando il suo contenuto attraverso
	 * il parametro che riceve e mettendo tutti gli altri attribuiti di classe ai loro
	 * valori di default
	 * @param status : CellStatus, parametro che attraverso l'enumerazione, 
	 * 							   descrive la tipologia della cella.
	 */
	public Cell(CellStatus status) {
		//valori di default agli altri attributi di classe
		this.isVisited=false;
		//inizializzazione vettore dei sensi
		this.sense_vector[0]=false;
		this.sense_vector[1]=false;
		//controllo sul parametro ricevuto
		if(status==null) {
			System.out.println("stato nullo");
		}//fi
		//si assegna lo stato alla cella
		this.status=status;
	}//Cell(CellStatus)

	//metodi accessori
	
	/** metodo getCell() : int
	 * @return content, l'informazione sul contenuto della cella,
	 * ovvero l'intero associato alla enumerazine CellStatus
	 */
	public int getCell() {
		//contenuto della cella
		return 0;
	}//getCell
	
	/** metodo setCell(int): void
	 * imposta il valore del contenuto della cella
	 * @param content: int, rappresenta il valore della cella, associato alla enum CellStatus
	 */
	public void setCell(int content) {
		//il valore puo' essere uno tra quelli della enum Cell status
		if(content >=0 && content <=6) {
			this.content=content;
			//al momento della creazione la cella non e' mai stata visitata
			this.isVisited=false;
			//aggiornamento del parametro status
			this.status=getEnumFromInt(content);
		}
		else {
			//il valore ricevuto non e' valido
			System.exit(-1);
		}
	}//setCell
	
	/** metodo setCellStauts(CellStatus status): void
	 * @param status, stato che si vuole assegnare alla cella per definirne la tipologia;
	 * @return void
	 */
	public void setCellStatus(CellStatus status) {
		//controllo sul parametro ricevuto
		if(status==null) {
			//non esiste un oggetto enum corrispondente al parametro
			this.status=null;
			System.out.println("stato nullo");
		}//fi
		else{
			//si assegna lo stato alla cella
			this.status=status;
			//si assegna il contenuto
			switch (status) {
				case SAFE:
					content = CellStatus.SAFE.ordinal();
					break;
				case PIT: //l'avventuriero deve evitare di cadere nel pozzo
					content = CellStatus.PIT.ordinal();
					break;
				case WUMPUS:
					content = CellStatus.WUMPUS.ordinal();
					break;
				case GOLD:
					content = CellStatus.GOLD.ordinal();
					break;
				case DENIED:
					content = CellStatus.DENIED.ordinal();
					break;
				default:
					break;
			}//switchcase
		}//else
	}//setCellStatus()
	
	/** metodo getCellStatusName() : String
	 * metodo che restituisce la stringa che rappresenta il contenuto della cella
	 * @return status.name() : String, la stringa associata alla enum CellStatus
	 */
	public String getCellStatusName() {
		//ritorna il nome della enum che descrive la cella
		return this.status.name();
	}//getCellStatus()
	
	/** metodo getCellStatus() : CellStatus
	 * metodo che restituisce l'oggetto CellStatus che rappresenta il contenuto della cella
	 * @return cs : CellStatus, il valore associato alla enum CellStatus
	 */
	public CellStatus getCellStatus() {
		//ritorna il valore della enum che descrive la cella
		return this.status;
	}//getCellStatusEnum()
	
	/** metodo getCellPosition()
	 * questo metodo restituisce il vettore che contiente gli indici i e j
	 * che indicano la posizione della cella nella mappa di gioco,
	 * visualizzandla come una stringa 
	 * @return p: String, stringa del vettore degli indici di posizione
	 */
	public String getCellPosition() {
		//si costruisce il vettore delle posizioni 
		String p = new String("Cella ["+position[0]+","+position[1]+"]");
		//si restituisce la posizione
		return p;
	}//getCellPosition
	
	/** metodo setCellPosition(int, int){
	 * questo metodo e' stato previsto per essere utilizzato nella classe GameMap,
	 * nel momento in cui deve essere inizializzata la mappa, per identificare la
	 * posizione di ogni cella nella mappa di gioco tramite questa coppia di indici
	 * @param i: int, indice di riga;
	 * @param j: int, indice di colonna;
	 * questo metodo si e' reso necessario perche' la conoscenza della posizione che 
	 * la cella occupa nella mappa di gioco serve a garantire che il personaggio giocabile, ovvero 
	 * l'eroe nella modalita' hero_side, il wumpus altrimenti, sia posizionato nelle celle
	 * che costituiscono la "cornice" della mappa di gioco.
	 */
	public void setCellPosition(int i, int j) {
		//controllo sui parametri
		if(i<-1 || i>3 || j<-1 || j>3) {
			System.out.println("Indice di cella non valido!");
		}
		else {
			//indice di riga
			position[0]=i;
			//indice di colonna
			position[1]=j;
		}
	}//setCellPosition()

	/** metodo getSenseVector() : boolean []
	 * metodo che restituisce il vettore dei sensori che caratterizza ogni cella
	 * serve per memorizzare le informazioni relative alle celle circostanti
	 * @return sense_vector: int [], il vettore costituito da due celle, 
	 * che racchiude le informazioni sense1 e sense2 sulle celle circostanti.
	 */
	public boolean[] getSenseVector() {
		//si restituisce il vettore dei sensi
		return sense_vector;
	}//getSenseVector
	
	/** metodo setSenseVector(boolean, boolean) : void
	 * metodo che imposta il valore delle celle del vettore dei sensori
	 * @param sense1 : SenseStatusH (hero_side) STINK, oppure SenseStatusW (!hero_side) CREAK;
	 * @param sense2 : SenseStatusH (hero_side) BREEZE, oppure SenseStatusW (!hero_side) SWISH;
	 */
	public void setSenseVector(boolean sense1, boolean sense2) {
		//prima cella del vettore dei sensi
		this.sense_vector[0]=sense1;
		//seconda cella del vettore dei sensi
		this.sense_vector[1]=sense2;
	}//setSenseVector
	
	/** metodo setSenseVectorCell(int, boolean) : void
	 * metodo che imposta il contenuto di una delle due celle del vettore dei
	 * sensori, in base all'indice ricevuto
	 * @param i : int, indice della cella del vettore da modificare;
	 * @param sense: boolean, flag da mettere nella cella del vettore dei sensori; 
	 */
	public void setSenseVectorCell(int i, boolean sense) {
		//controllo sull'indice di cella del vettore
		if(i==0 || i==1) {
			//si specifica il senso corrispondente
			this.sense_vector[i]=sense;
		}
		else {
			System.err.println("Indice del vettore dei sensi errato");
		}
	}//setSenseVectorCell
	
	/** metodo senseVectorToString(boolean) : String
	 * metodo che stmapa il contenuto del vettore dei sensori della cella su cui e'
	 * invocato secondo la formattazione specificata
	 * @param hero_side, specifica la modalita' di gioco, in modo da sapere quale enum
	 * sono state utilizzati come identiificativi nel vettore dei sensori;
	 * @return sense_info: String, stringa che rappresenta il contenuto del vettore;
	 */
	public String senseVectorToString(boolean hero_side) {
		//Stringa da stampare come informazioni sui sensori
		String sense_info;
		if(hero_side) {
			sense_info=new String("Il vettore dei sensi dell'eroe:\n||Stink| |Breeze||\n");
		}
		else {
			sense_info=new String("Il vettore dei sensi del mostro:\n||Creak| |Swish||\n");
		}
		return sense_info+"||"+sense_vector[0]+" | | "+sense_vector[1]+"||\n";
	}//SenseVectorToString()
	
	/** metodo isVisited(): boolean
	 * metodo che verifica se la cella in questione e' stata visitata dal giocatore
	 * @return isVisited: boolean, e' il flag che indica se e' stata visitata (true) o meno (false),
	 * e' un parametro dell'oggetto Cell
	 */
	public boolean isVisited() {
		//restituisce true se la cella e' stata gia' visitata, altrimenti false
		return isVisited;
	}//isVisited
	
	/** metodo toString() : String
	 * override del metodo toString()
	 * stampa il contenuto della cella utilizzando come indicativo la prima lettera
	 * della enum CellStatus corrispontente allo stato
	 */
	@Override
	public String toString() {
		//se il parametro status non e' nullo
		if(status!=null) {
			return "|"+new String(this.status.name()).charAt(0)+"|";
		}
		//se il parametro status e' nullo
		return "|"+new String("X"+"|");
	}//toString()
	
	/**
	 * 
	 * @param c
	 */
	public void copyCellSpecs(Cell c) {
		//questo metodo si usa per copiare le specifiche della 
		//cella che e' stata visitata nella matrice di eplorazione
		this.isVisited=true;
		//si copiano le caratteristiche della cella c
		//in quella su cui il metodo e' invocato
		this.status=c.status; //stato
		//si prelevano le posizioni
		this.position[0]=c.position[0];//indice di riga
		this.position[1]=c.position[1];//indice di colonna
		//vettore dei sensori
		this.sense_vector[0]=c.sense_vector[0];
		this.sense_vector[1]=c.sense_vector[1];
		//questa cella si copia nella matrice di esplorazione
		//TODO
	}//cellSpecs
	
}//Cell
