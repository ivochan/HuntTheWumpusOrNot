package game_structure;
/*enumerazione utilizzata per il vettore dei sensori: modalita' eroe
 *STINK, colore marrone, odore del Wumpus
 *BREEZE, colore bianco, brezza del pozzo
 */
enum SenseStatusH {
	STINK,
	BREEZE
}//SenseStatusH

/*enumerazione utilizzata per il vettore dei sensori: modalita' mostro
 *CREAK, colore marrone, scricchiolio dovuto al passo dell'eroe Wumpus
 *SWISH, colore bianco, fruscio di foglie vicino la trappola
 */
enum SenseStatusW {
	CREAK,
	SWISH
}//SenseStatusW

/*questa classe implementa la cella di una matrice
 *il cui contenuto cambia in base alla enum CellStatus
 *e secondo altre proprieta' stabilite in questa classe
 */
public class Cell {
	/* oltre al suo status,ogni cella puo' essere caratterizzata
	 * dall'essere stata visitata o meno
	 */

	//indica se la cella e' stata visitata o meno
	private boolean isVisited;
	
	//vettore dei sensori H: STINK, BREEZE
	//vettore dei sensori W: CREAK, SWISH
	private boolean[] sense_vector = new boolean[2];

	//contenuto della cella sulla base della enum CellStatus
	private CellStatus status;
	//numero intero associato alla enumerazione CellStatus
	private int content;

	//
	
	//costruttore di default
	public Cell() {
		//le caratteristiche della cella non sono state specificate
		this.status=null;
		this.content=-1;
		this.isVisited=false;
	}//Cell()

	//costruttore che riceve lo stato che caratterizza la cella
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
	
	//metodi accessori
	public int getCell() {
		//contenuto della cella
		return content;
	}//getCell
	
	public void setCell(int content) {
		//il valore puo' essere uno tra quelli della enum Cell status
		if(content >=0 && content <=6) {
			this.content=content;
			//al momento della creazione la cella non e' mai stata visitata
			this.isVisited=false;
		}
		else {
			//il valore ricevuto non e' valido
			System.exit(-1);
		}
	}//setCell
	
	public String getCellStatus() {
		//ritorna il nome della enum che descrive la cella
		return this.status.name();
	}//getCellStatus()
	
	public void setCellStatus(CellStatus status) {
		if(status==null) {
			System.exit(-1);
		}
		else {
			this.status=status;
		}
	}//setCellStatus(CellStatus)
	
	
	public boolean[] getSenseVector() {
		return sense_vector;
	}//getSenseVector
	
	public void setSenseVector(boolean sense1, boolean sense2) {
		//prima cella del vettore dei sensi
		this.sense_vector[0]=sense1;
		//seconda cella del vettore dei sensi
		this.sense_vector[1]=sense2;
	}//setSenseVector
	
	public String SenseVectorToString(boolean hero_side) {
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
	
	public boolean isVisited() {
		//restituisce true se la cella e' stata gia' visitata, altrimenti false
		return isVisited;
	}//isVisited
	
	@Override
	public String toString() {
		return "|"+new String(this.status.name()).charAt(0)+"|";
	}//toString()

}//Cell
