package game.structure.elements;

import game.structure.map.GameMap;

public class GameElements {
	/* vettore degli elementi di gioco
	 * contiene il numero massimo di elementi, suddivisi per tipologia,
	 * che dovra' essere posizionato nella mappa.
	 * [celle] [sassi] [premio] [eroe / wumpus] [pozzi / trappole]
	 */
	private static int [] game_elements;
	
	//##### costruttori #####
	
	/** GameElements()
	 * costruttore di default
	 * senza parametri
	 */
	public GameElements() {
		//si inzializza il vettore degli elementi di gioco
		game_elements= new int[5];
	}//GameElements()
	
	/** GameElements(GameMap)
	 * costruttore che si occupa di inizializzare ed assegnare un valore
	 * ad ogni cella del vettore degli elementi di gioco, specificando
	 * il massimo numero di elementi che potranno essere posizionati
	 * nella mappa di gioco, per ciascuna tipologia
	 * @param gm: GameMap, mappa di gioco.
	 */
	public GameElements(GameMap gm) {
		//controllo sul parametro
		if(gm==null) throw new IllegalArgumentException("mappa da utilizzare per il vettore"
				+ "dei sensori nulla");
		//inizializzazione del vettore dei sensori
		game_elements=new int[5];
		//numero massimo delle caselle della mappa
		game_elements[0]=gm.getMapDimension();
		//si sceglie casualmente il numero di celle non accessibili (da 0 a 2)
		int d=(int)(Math.random()*3);//(da 0 a 3 escluso)
		//massimo numero di celle  DENIED
		game_elements[1]=d;
		//premio
		game_elements[2]=1;
		//wumpus o avventuriero: nemico
		game_elements[3]=1;
		//massimo numero di pozzi o trappole
		game_elements[4]=2;
	}//GameElements(Map)

	//##### metodi accessori: contenuto di una singola cella del vettore #####
	
	/** metodo setGameElement(int, int): void
	 * questo metodo ha il ruolo di impostare il contenuto di una determinata
	 * cella del del vettore degli elementi di gioco.
	 * Gli elementi di cui potranno esserne modificate le quantita' sono:
	 * -il numero massimo di celle contenenti un pericolo, cioe' i pozzi o le
	 *  trappole, a seconda della modalita' di gioco;
	 * -il numero massimo di sassi, che corrisponde al numero di celle non giocabili;
	 * Questi valori, per scelta progettuale, non potranno ne' essere negativi
	 * ne' essere superiori a due.
	 * @param i: int, e' l'indice che indica la cella da riempire;
	 * @param elem: int, e' il valore del contenuto della cella in questione;
	 */
	public void setElement(int i, int elem) {
		//controllo sull'indice di cella
		if(i!=1 || i!=4) throw new IllegalArgumentException("l'indice di cella del vettore"
				+ " degli elementi di gioco non e' valido");
		//controllo sul valore che si vuole impostare come contenuto
		if(elem<0 || elem>2) throw new IllegalArgumentException("il contenuto del vettore"
				+ "degli elementi di gioco ricevuto come parametro non e' valido");
		//la cella esiste ed il contenuto da impostare e' valido
		game_elements[i]=elem;
	}//setElement(int, int)
	
	/** getElement(int, boolean): int
	 * questo metodo restituisce il numero di elementi di gioco della tipologia
	 * contenuta nella cella identifica dall'indice ricevuto come parametro
	 * @param i: int, indica l'indice della cella che contiene il numero degli
	 * 					  elementi di gioco di interesse, della tipologia richiesta;
	 * @return n_el: int, numero degli elementi di gioco della tipologia richiesta;
	 */
	public int getElement(int i) {
		//controllo sull'indice
		if(i<0 || i>game_elements.length)throw new IllegalArgumentException("Indice "
				+ "di cella del vettore degli elementi di gioco errato");
		//si preleva l'elemento
		int n_el = game_elements[i];
		//si restituisce
		return n_el;	
	}//getGameElement()
	
	//##### metodi accessori: vettore degli elementi di gioco #####
	
	/** metodo getGameElements(): int []
	 * questo metodo restituisce il vettore degli elementi di gioco
	 * 							game_elements
	 * [celle 0] [sassi 1 ] [premio 2] [eroe / wumpus 3] [pozzi / trappole 4]
	 * @return game_elements: int[], e' un vettore di interi, che contiene, per ogni cella, 
	 * 							il numero di elementi di quella stessa tipologia, che verranno
	 * 							inseriti nella mappa di gioco.
	 */
	public int[] getGameElements() {
		//si restituisce il vettore degli elementi di gioco
		return game_elements;
	}//getGameElements()
	
	/** metodo setGameElements(int[])
	 * questo metodo aggiorna il puntatore del vettore degli elementi di gioco
	 * assegnandogli il vettore ricevuto come parametro
	 * @param vector_elements
	 */
	public void setGameElements(int[] vector_elements) {
		//controllo sul parametro
		if(vector_elements==null) throw new IllegalArgumentException("Il vettore degli elementi"
				+" di gioco ricevuto come parametro e' nullo");
		if(vector_elements.length != game_elements.length) throw new IllegalArgumentException("La "+
				"dimensione del vettore degli elementi di gioco non e' corretta");
		//si aggiorna il vettore degli elementi
		game_elements=vector_elements;
	}//setGameElements(int[])
	
	//##### altri metodi #####
	
	/**metodo toString() :String
	 * metodo che restituisce una stringa che rappresenta il contenuto del vettore
	 * degli elementi di gioco
	 * @return game_els: String, stringa che contiene i valori del vettore degli
	 * 							 elementi di gioco.
	 */
	@Override
	public String toString() {
		//inizializzazione della stringa che conterra' gli elementi del vettore
		String game_els = new String("");
		//si riempie questa stringa con gli elementi di interesse
		game_els+=("| ");
		//si scorrono le celle del vettore
		for(int i=0;i<game_elements.length;i++) {
			//si stampa la cella i-esima
			game_els+=("["+game_elements[i]+"] ");
		}//for
		game_els+=("|\n");
		//si restituisce la stringa
		return ""+game_els;
	}//toString()

	/**metodo gameElementsToString() :String
	 * metodo che restituisce una stringa che rappresenta il contenuto del vettore
	 * degli elementi di gioco, comprensivo di legenda indicativa
	 *@return legend+game_els: String, stringa che comprensiva della legenda e del
	 *		  contenuto del vettore degli elementi di gioco.
	 */
	public String gameElementsToString() {
		//inizializzazione della stringa che conterra' gli elementi del vettore
		String game_els = new String("");
		//stringa che conterra' le informazioni riguardanti gli elementi di gioco
		String legend = new String("[cells] [stones] [award] [enemy/pg] [dangers]\n");
		//si riempie questa stringa con gli elementi di interesse
		game_els+=("| ");
		//si scorrono le celle del vettore
		for(int i=0;i<game_elements.length;i++) {
			//si stampa la cella i-esima
			game_els+=("["+game_elements[i]+"] ");
		}//for
		game_els+=("|\n");
		//stringa da restituire
		return ""+legend+game_els;
	}//gameElementsToString()
	
}//end GameElements