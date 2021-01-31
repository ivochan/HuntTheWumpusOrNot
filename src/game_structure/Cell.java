package game_structure;
/** classe Cell
 * racchiude le due enumerazioni utilizzate per descrivere il vettore dei sensori
 * associato ad ogni oggetto Cell 
 * e la stessa classe Cell, utilizzata per descrivere la tipologia di cella che 
 * costituisce la mappa di gioco.
 * @author ivonne
 * 
 */
/** Enumerazione utilizzata per il vettore dei sensori: modalita' eroe
 *	STINK, colore marrone, odore del Wumpus
 *	BREEZE, colore bianco, brezza del pozzo
 */
enum SenseStatusH {
	STINK,
	BREEZE
}//SenseStatusH

/** Enumerazione utilizzata per il vettore dei sensori: modalita' mostro
 *	CREAK, colore marrone, scricchiolio dovuto al passo dell'eroe
 *	SWISH, colore bianco, fruscio di foglie vicino la trappola
 */
enum SenseStatusW {
	CREAK,
	SWISH
}//SenseStatusW

/** Questa classe Cell implementa la cella di una matrice
 *	il cui contenuto cambia in base alla enum CellStatus
 *	e secondo altre proprieta' stabilite in questa classe
 */
public class Cell {
	
	//vettore dei sensori H: STINK, BREEZE
	//vettore dei sensori W: CREAK, SWISH
	private boolean[] sense_vector = new boolean[2];

	//contenuto della cella sulla base della enum CellStatus
	private CellStatus status;
	//numero intero associato alla enumerazione CellStatus
	private int content;
	//indica se la cella e' stata visitata o meno
	private boolean isVisited;
	//vettore che indice la posizione [i][j] della cella nella matrice di gioco
	private int[] position= {-1, -1};


	/** costruttore di default
	 */
	public Cell() {
		//le caratteristiche della cella non sono state specificate
		this.status=null;
		this.content=-1;
		this.isVisited=false;
		//inizializzazione vettore dei sensi
		this.sense_vector[0]=false;
		this.sense_vector[1]=false;
	}//Cell()

	/** costruttore con parametri
	 * @param status
	 * @param hero_side
	 * @param sense1
	 * @param sense2
	 */
	public Cell(CellStatus status, boolean hero_side, boolean sense1, boolean sense2) {
		//stato della cella 
		this.status=status;
		//si verifica in quale modalita' si stia giocando
		if (hero_side) {
			//e' la modalita' classica, in cui l'avventuriero da' la caccia al wumpus
			//si assegna lo stato alla cella
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
			
			/* vettore dei sensori 
			 * se nella cella c'e' l'odore del wumpus si assegna true a sense_vector[0]
			 *altrimenti false, in base al parametro ricevuto dal costruttore
			 */
			sense_vector[SenseStatusH.STINK.ordinal()]= sense1;
			/*se nella cella c'e' la brezza che arriva dal pozzo, dalla fossa,
			 *si assegna true a sense_vector[1], altrimenti false, in base al parametro ricevuto dal
			 *costruttore
			 */
			sense_vector[SenseStatusH.BREEZE.ordinal()]= sense2;
		}//hero_side
		else {
			//modalita' wumpus, in cui il wumpus deve fuggire dall'avventuriero
			//si assegna lo stato alla cella
			switch (status) {
				case SAFE:
					content = CellStatus.SAFE.ordinal();
					break;
				case GOLD:
					content = CellStatus.GOLD.ordinal();
					break;
				case HERO:
					content = CellStatus.HERO.ordinal();
					break;
				case TRAP: //il wumpus deve evitare la trappola
					content = CellStatus.TRAP.ordinal();
					break;
				case DENIED:
					content = CellStatus.DENIED.ordinal();
					break;
				default: break;
			}//switchcase
			
			/* vettore dei sensori W
			 * se nella cella c'e' lo scricchiolio del passo dell'eroe
			 * si assegna true a sense_vector[0]
			 * altrimenti false, in base al parametro ricevuto dal costruttore
			 */
			sense_vector[SenseStatusW.CREAK.ordinal()]= sense1;
			/*se nella cella c'e' il fruscio delle foglie vicine alla trappola
			 *si assegna true a sense_vector[1], altrimenti false,
			 *in base al parametro ricevuto dal costruttore
			 */
			sense_vector[SenseStatusW.SWISH.ordinal()]= sense2;
		}//wumpus_side
		//inizialmente la cella non e' mai stata visitata
		this.isVisited=false;
	}//costruttore
	

	/** costruttore Cell(CellStatus)
	 * questo costruttore crea un oggetto Cell, specificando il suo contenuto attraverso
	 * il parametro che riceve e mettendo tutti gli altri attribuiti di classe ai loro
	 * valori di default
	 * @param status : CellStatus, parametro che attraverso l'enumerazione, descrive la tipologia della cella
	 */
	public Cell(CellStatus status) {
		//TODO si ipotizza la modalita' hero_side
		//si assegnano i valori di default agli attribuit di classe
		//non ricevuti come parametro
		this.isVisited=false;
		//inizializzazione vettore dei sensi
		this.sense_vector[0]=false;
		this.sense_vector[1]=false;
		//controllo sul parametro ricevuto
		if(status==null) {
			this.status=null;
			System.out.println("nullo");
			//allora il contenuto associato sara' -1
			//perche' non esiste un oggetto enum corrispondente al parametro
			this.content=-1;
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
		}
		//TODO inserire qui l'inizializzazione del vettore dei sensori
	}//Cell(CellStatus)

	//metodi accessori
	
	/** metodo getCell() : int
	 * @return content, l'informazione sul contenuto della cella,
	 * ovvero l'intero associato alla enumerazine CellStatus
	 */
	public int getCell() {
		//contenuto della cella
		return content;
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
			this.status=null;
			System.out.println("nullo");
			//allora il contenuto associato sara' -1
			//perche' non esiste un oggetto enum corrispondente al parametro
			this.content=-1;
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
	
	/** metodo getEnumFromInt(int) : CellStatus
	 * questo metodo restituisce il valore della enum CellStatus associato
	 * al contenuto della cella, indicato da un valore intero.
	 * @param content: int, valore intero che rappresenta il contenuto della cella;
	 * @return c : CellStatus, se alla cella e' stato assegnato un valore,
	 * 		   null, altrumenti.
	 */
	private CellStatus getEnumFromInt(int content) {	
		//si scorre il vettore delle enumerazioni CellStatus
		for(CellStatus c : CellStatus.values()) {
			if(c.ordinal()==content) {
				return c; 
			}
		}
		return null;
	}//getEnumFromInt
	
	/** metodo getCellStatus() : String
	 * metodo che restituisce la stringa che rappresenta il contenuto della cella
	 * @return status.name() : String, la stringa associata alla enum CellStatus
	 */
	public String getCellStatus() {
		//ritorna il nome della enum che descrive la cella
		return this.status.name();
	}//getCellStatus()
	
	/** metodo getCellStatus() : CellStatus
	 * metodo che restituisce l'oggetto CellStatus che rappresenta il contenuto della cella
	 * @return cs : CellStatus, il valore associato alla enum CellStatus
	 */
	public CellStatus getCellStatusEnum() {
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
		if(i<0 || j<0) {
			System.out.println("Non e' ammesso un indice negativo");
		}
		else {//gli indici sono validi
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
		return sense_info+"||"+sense_vector[0]+"| |"+sense_vector[1]+"||\n";
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
	 * della enum CellStatus corrispontente al contenuto
	 */
	@Override
	public String toString() {
		if(status!=null) {
			return "|"+new String(this.status.name()).charAt(0)+"|";
		}
		else
			return "|"+new String(this.content+"|");
	}//toString()

}//Cell
