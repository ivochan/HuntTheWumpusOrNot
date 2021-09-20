package game.structure.cell;

import java.io.Serializable;

/** Cell
 * Questa classe implementa la cella della matrice di gioco
 * il cui contenuto e' indicato dalla enum CellStatus
 * 
 * @author ivonne
 */
public class Cell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063662208178208918L;

	//##### attributi di classe #####
	
	//vettore dei sensori : ENEMY_SENSE, DANGER_SENSE
	private boolean[] sense_vector = new boolean[2];
	//tipologia del contenuto della cella
	private CellStatus status;
	//vettore che indice la posizione [i][j] della cella nella matrice di gioco
	private int[] position= {-1, -1};
	
	//##### costruttori #####
	
	/** Cell()
	 * costruttore di default
	 * istanzia un oggetto Cell vuoto,
	 * con caratteristiche di default
	 */
	public Cell() {
		//tipologia di cella non specificata
		this.status=CellStatus.UNKNOWN;
		//inizializzazione vettore dei sensi
		this.sense_vector[0]=false;
		this.sense_vector[1]=false;
	}//Cell()

	/** costruttore Cell(CellStatus)
	 * questo costruttore crea un oggetto Cell, specificando il suo contenuto
	 * attraverso il parametro che riceve e mettendo gli altri attribuiti 
	 * di classe ai loro valori di default
	 * @param status : CellStatus, parametro che attraverso l'enumerazione, 
	 * 							   descrive la tipologia della cella.
	 */
	public Cell(CellStatus status) {
		//inizializzazione vettore dei sensi
		this.sense_vector[0]=false;
		this.sense_vector[1]=false;
		//controllo sul parametro ricevuto
		if(status==null)throw new IllegalArgumentException("Stato della cella nullo");
		//si assegna lo stato alla cella
		this.status=status;
	}//Cell(CellStatus)
	
	/** costruttore Cell(CellStatus, boolean, boolean )
	 * questo costruttore crea un oggetto Cell, specificando il suo contenuto attraverso
	 * il parametro che riceve come indicativo dello stato e aggiorna il contenuto delle
	 * due celle del vettore dei sensori mettendo i due booleani che sono stati passati
	 * come parametri.
	 * @param status : CellStatus, parametro che attraverso l'enumerazione, 
	 * 							   descrive la tipologia della cella.
	 * @param sense1: boolean, parametro che specifica il primo valore del vettore dei sensi;
	 * @param sense2: boolean, parametro che specifica il secondo valore del vettore dei sensi;
	 */
	public Cell(CellStatus status, boolean sense1, boolean sense2) {
		//inizializzazione vettore dei sensi
		this.sense_vector[0]=sense1;
		this.sense_vector[1]=sense2;
		//controllo sul parametro ricevuto
		if(status==null)throw new IllegalArgumentException("Stato della cella nullo");
		//si assegna lo stato alla cella
		this.status=status;
	}//Cell(CellStatus, boolean, boolean)
	
	//##### metodi accessori: contenuto e sensori #####
	
	/** metodo setCell(CellStatus): void
	 * @param status, stato che si vuole assegnare alla cella per definirne la tipologia;
	 * @return void
	 */
	public void setCellStatus(CellStatus status) {
		//controllo sul parametro ricevuto
		if(status==null)throw new IllegalArgumentException("La variabile indicata come status"
				+ " della cella non e' valida");
		//si assegna lo stato alla cella
		this.status=status;
	}//setCellStatus()
	
	/** metodo getCellStatus() : CellStatus
	 * metodo che restituisce l'oggetto CellStatus che rappresenta il contenuto della cella
	 * @return cs : CellStatus, il valore associato alla enum CellStatus
	 */
	public CellStatus getCellStatus() {
		//ritorna il valore della enum che descrive la cella
		return this.status;
	}//getCellStatus()
	
	//##### metodi accessori: posizione della cella nella matrice #####
	
	/** metodo getCellPosition()
	 * questo metodo restituisce il vettore che contiente gli indici i e j
	 * che indicano la posizione della cella nella mappa di gioco,
	 * visualizzandola come una stringa 
	 * @return p: String, stringa del vettore degli indici di posizione
	 */
	public String getCellPosition() {
		//si costruisce il vettore delle posizioni 
		String p = new String("Cella ["+position[0]+","+position[1]+"]");
		//si restituisce la posizione
		return p;
	}//getCellPosition()
	
	/** metodo getPosition()
	 * questo metodo restituisce il vettore che contiente gli indici i e j
	 * che indicano la posizione della cella nella mappa di gioco
	 * @return p: int[], vettore degli indici di posizione
	 */
	public int[] getPosition() {
		//si restituisce la posizione
		return position;
	}//getPosition()
	
	/** metodo setPosition(int, int){
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
	public void setPosition(int i, int j) {
		//controllo sui parametri
		if(i<-1 || i>3 || j<-1 || j>3) throw new IllegalArgumentException("Indice di cella non valido!");
		//indice di riga
		position[0]=i;
		//indice di colonna
		position[1]=j;
	}//setCellPosition(int, int)
	
	//##### metodi accessori: vettore dei sensori #####
	
	/** metodo getSenseVector() : boolean []
	 * metodo che restituisce il vettore dei sensori che caratterizza ogni cella
	 * serve per memorizzare le informazioni relative alle celle circostanti
	 * @return sense_vector: int [], il vettore costituito da due celle, 
	 * che racchiude le informazioni sense1 e sense2 sulle celle circostanti.
	 */
	public boolean[] getSenseVector() {
		//si restituisce il vettore dei sensi
		return sense_vector;
	}//getSenseVector()
	
	/** metodo setSenseVector(boolean, boolean) : void
	 * metodo che imposta il valore delle celle del vettore dei sensori
	 * @param sense1 : ENEMY_SENSE (hero_side= STINK, wumpus_side = CREAK);
	 * @param sense2 : DANGER_SENSE (hero_side = BREEZE, wumpus_side = SWISH);
	 */
	public void setSenseVector(boolean sense1, boolean sense2) {
		//prima cella del vettore dei sensi
		this.sense_vector[0]=sense1;
		//seconda cella del vettore dei sensi
		this.sense_vector[1]=sense2;
	}//setSenseVector(boolean, boolean)
	
	/** metodo setSenseVectorCell(int, boolean) : void
	 * metodo che imposta il contenuto di una delle due celle del vettore dei
	 * sensori, in base all'indice ricevuto
	 * @param i : int, indice della cella del vettore da modificare;
	 * @param sense: boolean, flag da mettere nella cella del vettore dei sensori; 
	 */
	public void setSenseVectorCell(int i, boolean sense) {
		//controllo sull'indice di cella del vettore
		if(i<0 || i>1)throw new IllegalArgumentException("Indice di cella del vettore "
				+ "dei sensori errato.");
		//si specifica il senso corrispondente
		this.sense_vector[i]=sense;
	}//setSenseVectorCell(int, boolean)
	
	/** metodo senseVectorToString(boolean) : String
	 * metodo che stampa il contenuto del vettore dei sensori della cella su cui e'
	 * invocato secondo questo criterio:
	 * -la prima cella contiene il sensore che indica se nelle vicinanze sia presente
	 *  l'avversario del pg, ovvero il mostro (STINK), oppure il cacciatore (SWISH),
	 *  in base alla modalita' scelta per la sessione di gioco;
	 * -la seconda cella contiene il sensore che indica se nelle vicinanze sia presente
	 *  un pericolo per il pg, ovvero il pozzo (BREEZE), oppure la trappola (CREAK),
	 *  in base alla modalita' scelta, per la sessione di gioco, che sara', rispettivamente,
	 *  la modalita' eroe oppure la modalita' wumpus.
	 *  @param legend: boolean, se true verra' stampato a cosa si riferisce ogni valore
	 *  					  del vettore dei sensori.
	 * @return sense_info: String, stringa che rappresenta il contenuto del vettore;
	 */
	public String senseVectorToString(boolean info) {
		//stringa di informazioni
		String legend=new String("||nemico| |pericolo||\n");
		//se info richiesta
		if(info) {
			return ""+legend+"||"+sense_vector[0]+" | | "+sense_vector[1]+"||\n";
		}//fi
		//contenuto del vettore dei sensori
		return "||"+sense_vector[0]+" | | "+sense_vector[1]+"||\n";
	}//SenseVectorToString(boolean)
	
	//##### altri metodi #####
	
	/** metodo toString() : String
	 * override del metodo toString()
	 * stampa il contenuto della cella utilizzando come indicativo la prima lettera
	 * della enum CellStatus corrispontente allo stato
	 */
	@Override
	public String toString() {
		//se il parametro status non e' sconosciuto
		if(!status.equals(CellStatus.UNKNOWN)) {
			return "|"+new String(this.status.name()).charAt(0)+"|";
		}//fi
		else {
			//se il parametro status non e' stato specificato
			return "|"+new String("X"+"|");
		}//else
	}//toString()
	
	/** metodo positionToString(): String
	 * questo metodo serve per stampare gli indici della cella considerata
	 * sottoforma di stringa
	 * @return String: posizione della cella.
	 */
	public String positionToString() {
		//si restituisce la tringa
		return ""+'('+position[0]+','+position[1]+')';
	}//positionToString()
	
	/** metodo copyCellSpecs(Cell): void
	 * questo metodo effettua una copia della cella ricevuta come parametro
	 * sull'oggetto Cell su cui e' stato invocato.
	 * Verra' utilizzato quando si vorranno copiare le informazioni di una cella
	 * della matrice game_map, che rappresenta la struttura della mappa di gioco, 
	 * nella mappa che tiene traccia delle celle gia' visitate dal personaggio 
	 * giocabile, cioe' la mappa di esplorazione che verra' istanziata direttamente
	 * nella sessione di gioco.
	 * 
	 * @param c: Cell, cella di cui copiare le specifiche.
	 */
	public void copyCellSpecs(Cell c) {
		//si copiano le caratteristiche della cella c su this
		this.status=c.status; //stato
		//si prelevano le posizioni
		this.position[0]=c.position[0];//indice di riga
		this.position[1]=c.position[1];//indice di colonna
		//vettore dei sensori
		this.sense_vector[0]=c.sense_vector[0];
		this.sense_vector[1]=c.sense_vector[1];
	}//cellSpecs(Cell)
	
}//end Cell
