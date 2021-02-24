package game.structure.map;

import game.structure.cell.Cell;
import game.structure.cell.CellStatus;

/** class GameConfiguration
 * questa classe si occupa di effettuare tutte le configurazioni necessarie
 * a preparare il campo di gioco, inizializzando gli elementi di gioco, con cui il 
 * personaggio giocabile dovra' irterfacciarsi durante la sua esplorazione della mappa
 * @author ivonne
 * questa classe contiene dei metodi di servizio, static, che quindi verranno invocati
 * sulla classe stasse e non su una sua istanza.
 */
public class GameConfiguration {
	
	/** pg_start_position
	 * vettore che contiene l'indice di riga e colonna che contiene
	 * la posizione del pg nella mappa
	 */
	static int [] pg_start_position= new int[2];
	
	/** vettore degli elementi di gioco game_elements
	 * questo vettore di nove celle conterra' le informazioni riguardanti 
	 * il numero massimo per ciascun tipologia di elementi con cui dovra' 
	 * essere popolata la mappa.
	 * N.B. nella modalita' hero_side verranno visualizzati il wumpus ed i pozzi,
	 * 		nella modalita' wumpus_side, invece, l'eroe e le trappole.
	 * [celle] [sassi] [premio] [eroe / wumpus] [pozzi / trappole]
	 * 	 [0] 	 [1] 	[2] 		[3] 			[4]
	 */
	private static int [] game_elements= new int [5];
	
	//################ vettore degli elementi di gioco ########################

	/** metodo elementsVectorFilling(): void
	 * questo metodo e' utilizzato per riempire automaticamente il vettore degli elementi di 
	 * gioco che dovranno poi essere utilizzati per popolare la mappa
	 * @param gm: GameMap, e' la mappa di gioco, una matrice di oggetti di tipo Cell.
	 */
	public static void elementsVectorFilling(GameMap gm) {
		//[celle 0] [sassi 1 ] [premio 2] [pg / enemy 3] [danger 4]
		//numero massimo delle caselle della mappa
		game_elements[0]=gm.getNCells();
		//si sceglie casualmente il numero di celle non giocabili (da 0 a 2)
		//ovvero il numero di celle non accessibili DENIED		
		int d=(int)(Math.random()*3);//(da 0 a 3 escluso)
		//massimo numero di celle non giocabili SASSI
		game_elements[1]=d;
		//premio 
		int a=1;
		//massimo numeor di lingotti
		game_elements[2]=a;
		//wumpus o avventuriero: personaggio giocabile
		int pg=1;
		//massimo numero di personaggi giocabili
		game_elements[3]=pg;
		//numero di pozzi o trappole da mettere nella mappa
		int pt = 2; 
		//massimo numero di pozzi o trappole
		game_elements[4]=pt;
	}//elementsVectorFilling
	
	/** elementsVectortoString() :String
	 * metodo che restituisce una stringa che rappresenta il contenuto del vettore
	 * degli elementi di gioco
	 * in questo modo potra' essere visualizzato a schermo tramite una stampa
	 * @param info: boolean, se true verra' stampata anche una legenda indicativa sul 
	 * 						 contenuto del vettore, se false soltanto il vettore.
	 */
	public String elementsVectorToString(boolean info) {
		//inizializzazione della stringa che conterra' gli elementi del vettore
		String game_els = new String("");
		//stringa che conterra' le informazioni riguardanti gli elementi di gioco
		String legend = new String("[celle] [sassi] [oro] [wumpus/eroe] [pozzi/trappole]\n");
		//si riempie questa stringa con gli elementi di interesse
		game_els+=("| ");
		//si scorrono le celle del vettore
		for(int i=0;i<game_elements.length;i++) {
			//si stampa la cella i-esima
			game_els+=("["+game_elements[i]+"] ");
		}//for
		game_els+=("|\n");
		//controllo sul parametro
		if(info) {
			return ""+legend+game_els;
		}
		else {
			//altrimenti si stampa senza legenda
			return ""+game_els;
		}
	}//elementsVectorToString

	/** metodo setGameElement(int, int): void
	 * questo metodo ha il ruolo di impostare il contenuto della cella del vettore
	 * degli elementi di gioco che verranno posizionati sulla mappa
	 * Gli elementi di cui potranno esserne modificate le quantita' saranno:
	 * -il numero massimo di pozzi, che corrisponde al numero di trappole;
	 * -il numero massimo di sassi, che corrisponde al numero di celle non giocabili;
	 * questi valori, per scelta progettuale, non potranno ne' essere negativi
	 * ne' essere superiori a due.
	 * [celle 0] [sassi 1 ] [premio 2] [eroe / wumpus 3] [pozzi / trappole 4]
	 * @param i: int, e' l'indice che indica la cella da riempire;
	 * @param elem: int, e' il valore del contenuto della cella in questione;
	 */
	public void setGameElement(int i, int elem) {
		//controllo sull'indice di cella
		if(i==1 || i==4) {
			//se l'indice di cella e' corretto si puo' svolgere un controllo
			//sul parametro che ne descrive il contenuto da modificare
			if(elem>=0 && elem<=2) {
				//il contenuto scelto e' un numero valido
				//si puo' impostare il contenuto
				game_elements[i]=elem;
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
	
	/** getGameElement(int, boolean): int
	 * questo metodo restituisce il numero di elementi di gioco della tipologia
	 * contenuta nella cella identifica da index
	 * @param index: int, indica l'indice della cella che contiene il numero degli
	 * 					  elementi di gioco di interesse, della tipologia richiesta;
	 * @param info: boolean, questo flag, se true, stampa il nome della tipologia
	 * 						 degli elementi di gioco di cui si vuole conoscere il
	 * 						 numero, se false invece no;
	 * @return el: int, numero degli elementi di gioco della tipologia richiesta;
	 */
	public int getGameElement(int index, boolean info) {
		//controllo sull'indice
		if(index<0 || index>game_elements.length) {
			System.err.println("Indice di cella del vettore degli elementi di gioco errato");
			System.exit(-1);
		}//fi
		//si preleva l'elemento
		int el = game_elements[index];
		if(info) {
			//stringa da stampare
			String el_type=new String("");
			switch(index) {
			case 0:
				el_type="numero di celle";
				break;
			case 1:
				el_type="sassi";
				break;
			case 2:
				el_type="premio";
				break;
			case 3:
				el_type="nemico";
				break;
			case 4:
				el_type="pericolo";
				break;
			default:break;
			}//end switch
			System.out.println(el_type);
		}//fi info
		return el;	
	}//getGameElement()
	
	/** metodo getGameElements(): int []
	 * questo metodo restituisce il vettore degli elementi di gioco
	 * 							game_elements
	 * [celle 0] [sassi 1 ] [premio 2] [eroe / wumpus 3] [pozzi / trappole 4]
	 * @return game_elements: int[], e' un vettore di interi, che contiene, 
	 * 								 per ogni cella, il numero di elementi
	 * 								 di quella stessa tipologia, che verranno
	 * 								 inseriti nella mappa di gioco.
	 */
	public static int[] getGameElements() {
		//si restituisce il vettore degli elementi di gioco
		return game_elements;
	}//getGameElements
	
	/** metodo getGameElementsToString(boolean): String
	 * questo metodo restituisce una stringa che rappresenta il contenuto
	 * del vettore degli elementi di gioco.
	 * @param info: boolean, flag che indica se si vuole la legenda del contenuto;
	 * @return game_els: String, stringa che mostra il contenuto del vettore
	 * 					 game_elements.
	 */
	public static String getGameElementsToString(boolean info) {
		//stringa di informazioni
		String legend=new String("||celle|  |sassi|  |premio|  |pg-nemico|  |pericolo||\n");
		//contenuto del vettore sottoforma di stringa
		String game_els= new String("");
		//si itera il vettore game_elements
		game_els="|";
		for(int i=0;i<game_elements.length;i++) {
			//si mette il contenuto della cella
			if(i != game_elements.length-1) {
				game_els+=" |"+game_elements[i]+"|";
			}
			else {
				//e' l'ultimo elemento
				game_els+=" |"+game_elements[i]+"| |\n";
			}
		}//end for
		//se info richiesta
		if(info) {
			return ""+legend+game_els;
		}
		else {
			//si restituisce il vettore degli elementi di gioco
			return game_els;
		}
	}//getGameElements
	
	//###################### mappa di gioco ###############################
	
	/** metodo placeMain(GameMap): void
	 * questo metodo si occupa di trovare una configurazione valida della
	 * mappa di gioco, generando una struttura di base, in cui sono stati
	 * posizionati tutti gli elementi di gioco, ad eccezione del personaggio
	 * giocabile, che verra' posizionato subito dopo.
	 * Se il posizionamento del pg non va a buon fine, la mappa deve essere
	 * generata nuovamente.
	 * @param gm: GameMap, e' l'istanza che rappresenta la mappa da costruire,
	 * 					   su cui verra' poi avviata la sessione di gioco.
	 */
	public static void placeMain(GameMap gm) {
		//variabile asiliaria che indica l'avvenuto posizionamento del pg
		boolean done = false;
		//ciclo
		while(!done) {
			//si riempie la mappa di gioco
			init(gm);
			//si cerca di posizionare il pg
			done = placePGonCorner(gm);
			//finche' non si esce dal ciclo viene ripopolata la mappa alla ricerca
			//di una configurazione idonea al posizionamento del pg
			//System.out.println("Posizionamento PG riuscito? "+done);
		}//end while
	}//placeMain()
	
	/** metodo placeMainAward(GameMap): void
	 * questo metodo si occupa di trovare una configurazione valida della
	 * mappa di gioco, generando una struttura di base, in cui sono stati
	 * posizionati tutti gli elementi di gioco, ad eccezione del premio e
	 * del personaggio giocabile, che verranno posizionati subito dopo.
	 * Se il posizionamento del pg o del premio non vanno a buon fine,
	 * la mappa deve essere generata nuovamente.
	 * @param gm: GameMap, e' l'istanza che rappresenta la mappa da costruire,
	 * 					   su cui verra' poi avviata la sessione di gioco.
	 */
	public static void placeMainAward(GameMap gm) {
		//variabile asiliarie 
		boolean done = false;
		boolean award=false;
		//ciclo
		while(!done || !award) {
			//si riempie la mappa di gioco
			initWithoutAward(gm);
			//si cerca di psizionare il premio
			award = placeAward(gm);
			//si cerca di posizionare il pg
			done = placePGonCorner(gm);
		}//end while
	}//placeMain()
	
	/** metodo placeAward(GameMap): boolean
	 * questo metodo si occupa di poszionare il premio in un punto
	 * qualsiasi della mappa di gioco, purche' sia raggiungibile dal
	 * pg. Questo vuol dire che da almeno un lato, su quattro, dovra'
	 * avere una cella adiacente che sia etichettata come Safe.
	 * @param gm: GameMap, mappa di gioco.
	 * @return found: boolean, flag che indica se il premio e' stato posizionato.
	 */
	private static boolean placeAward(GameMap gm) {
		//variabile booleana che indica se e' stata trovata la posizione idonea 
		boolean found = false;
		//probabilita
		double paward;
		//numero di premi
		int n_award=1;
		int award=1;
		int n_cells=gm.getNCells();
		int cells = n_cells;
		while(award!=0) {
			//si iterano le celle della matrice
			for(int i=0;i<gm.getRows();i++) { //for righe
				for(int j=0;j<gm.getColumns();j++) { //for colonne
					//si genera un numero casuale (da 0 a 1) da utilizzare come soglia 
					double random = Math.random();
					//probabilita
					paward = prob(award, n_award, cells, n_cells);
					//si preleva la cella attuale
					Cell c = gm.getGameCell(i,j);
					//si aggiorna il numero di celle gia' esaminate
					cells = cells-1;
					//confronto delle probabilita' con la soglia random
					if(random < paward) {
						//si controllano le celle adiacenti
						//cella sopra
						if(gm.getGameCell(i-1, j)!=null && 
								gm.getGameCell(i-1, j).getCellStatus().equals(CellStatus.SAFE)){
							found=true;
							System.out.println("cella sopra "+found);
						}
						//cella sotto
						else if(gm.getGameCell(i+1, j)!=null &&
								gm.getGameCell(i+1, j).getCellStatus().equals(CellStatus.SAFE)){
							found=true;
							System.out.println("cella sotto "+found);
						}
						//cella a sinistra
						else if(gm.getGameCell(i, j-1)!=null &&
								gm.getGameCell(i, j-1).getCellStatus().equals(CellStatus.SAFE)){
							found=true;	
							System.out.println("cella sinistra "+found);
						}
						//cella a destra
						else if(gm.getGameCell(i, j+1)!=null && 
								gm.getGameCell(i, j+1).getCellStatus().equals(CellStatus.SAFE)){
							found=true;
							System.out.println("cella destra "+found);
						}
						else {
							//nessuna delle celle adiacenti esistenti e' libera
							found=false;
						}
						//se e'stata trovata una posizione
						if(found){
							//si posiziona il premio
							c.setCellStatus(CellStatus.AWARD);
							//si impostano gli indici che descrivono la posizione della cella
							c.setCellPosition(i, j);
							//si decrementa la variabile, perche' un elemento e' stato posizionato
							award = award -1;
							return found;
						}//fi posizionamento
					}//fi probabilita'
				}//for colonne
			}//for righe
		}//end while
		return found;
	}//placeAward()
	
	
	
	/** metodo init(GameMap): void
	 * questo metodo si occupa del popolamento della mappa di gioco, 
	 * inserendo tutti gli elementi che possono essere posizionati nella mappa, 
	 * ad eccezione del personaggio giocabile, che a seconda della modalita- di gioco
	 * sara' l'eroe oppure il wumpus. 
	 * Di default, il nemico sara' indicato con il wumpus.
	 * Il metodo funziona in questo modo:
	 * dopo aver assegnato ai parametri i valori degli elementi da posizionare, viene
	 * effettuato un ciclo che terminera' soltanto quando tutti gli elementi siano stati 
	 * posizionati, quindi il ciclo di for innestato, che si occupera' di iterare le celle 
	 * della matrice, verra' ripetuto fino a quando non si determini una configurazione tale
	 * da soddisftare tutte le condizioni simultaneamente.
	 * Per quanto riguarda proprio la scelta della tipologia di cella, verra' calcolata
	 * la probalita' per cui questa possa essere etichettata nella tipologia che si sta
	 * valutando, se il valore ottenuto tramite l'apposita funzione prob(int,int,int, int)
	 * risulta maggiore del valore utilizzato come soglia di confronto, cioe' il numero casuale
	 * calcolato prima ancora che vengano assegnate le porbabilita' ad ogni tipologia di
	 * elemento da posizionare nella matrice di gioco.
	 * @param gm: GameMap, e' la mappa di gioco che deve essere costruita.
	 */
	public static void init(GameMap gm) {
		//si prelevano gli elementi di gioco
		//[celle 0] [sassi 1] [premio 2] [eroe / wumpus 3] [pozzi/ trappole 4]
		//pozzi o trappole
		int n_pit_trap=game_elements[4];
		int pit_trap=n_pit_trap;
		//sassi
		int n_stones=game_elements[1];
		int stones=n_stones;
		//nemico
		int n_enemy=game_elements[3];
		int enemy=n_enemy;
		//oro
		int n_award=game_elements[2];
		int award=n_award;
		//numero di celle della mappa di gioco
		int n_cells=game_elements[0]; 
		int cells=n_cells;
		//variabili che conterranno la probabilita'
		double ppitrap=0;
		double penemy=0; //di default e' il wumpus
		double paward=0;
		double pstones=0;
		//ciclo di riempimento della mappa
		while( pit_trap!=0 || enemy!=0 || award!=0 || stones!=0) {
			//si riassegnano alle variabili i valori di default
			//in modo da resettare la situazione se la configurazione ottenuta
			//per la mappa di gioco non e' idonea poiche' non soddisfa tutte le specifiche
			award=n_award;
			pit_trap=n_pit_trap;
			enemy=n_enemy;
			cells=n_cells;
			stones=n_stones;
			//svuotare la matrice per ripristinarla alla situazione iniziale
			gm.clear();
			//riempimento delle celle della matrice
			for(int i=0;i<gm.getRows();i++) { //for righe
				for(int j=0;j<gm.getColumns();j++) { //for colonne
					//si genera un numero casuale (da 0 a 1) da utilizzare come soglia 
					double random = Math.random();
					//calcolo delle probabilita' per ogni tipologia di cella
					ppitrap = prob(pit_trap, n_pit_trap, cells, n_cells);
					penemy = prob(enemy, n_enemy, cells, n_cells);
					paward = prob(award, n_award, cells, n_cells);
					pstones = prob(stones, n_stones, cells, n_cells);
					//si preleva la cella attuale
					Cell c = gm.getGameCell(i, j);
					//confronto delle probabilita' con la soglia random
					if(random < ppitrap) {
						//la cella e' un pozzo/trappola
						c.setCellStatus(CellStatus.DANGER);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						pit_trap-=1;
					}
					else if(random < penemy) {
						/* la cella conterra' l'avversario del pg, che sara':
						 * -il wumpus, nella modalita' eroe;
						 * -l'eroe, nella modalita' wumpus;
						 */
						 c.setCellStatus(CellStatus.ENEMY);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						enemy-=1;
					}
					else if(random < paward) {
						/* la cella conterra' un premio, che sara':
						 * -per l'avventuriero, un lingotto d'oro;
						 * -per il wumpus, una via di fuga;
						 * quindi dipendera' dalla modalita' di gioco
						 */
						c.setCellStatus(CellStatus.AWARD);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						award-=1;
					}
					else if(random < pstones) {
						/* la cella non sara' giocabile, non ci si potra' posizionare il
						 * personaggio giocabile perche' contiene un sasso
						 */
						c.setCellStatus(CellStatus.FORBIDDEN);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						stones-=1;
					}
					else {
						//la cella e' etichettata come sicura, libera
						c.setCellStatus(CellStatus.SAFE);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i,j);
					}
					//si decrementa il numero di celle rimaste da riempire
					cells=cells-1;	
					//DEBUG
					//System.out.println("Cella "+game_map[i][j]);
					//System.out.println(elementsVectorToString(false));
				}//for colonne
			}//for righe
			//System.out.println("pozzir "+pozzi+" wumpusr "+wumpus+" oror "+oro+" sassi "+sassi);	
		}//while
	}//init()			
	
	/**
	 * 
	 * @param gm
	 */
	public static void initWithoutAward(GameMap gm) {
		//si prelevano gli elementi di gioco
		//pozzi o trappole
		int n_pit_trap=game_elements[4];
		int pit_trap=n_pit_trap;
		//sassi
		int n_stones=game_elements[1];
		int stones=n_stones;
		//nemico
		int n_enemy=game_elements[3];
		int enemy=n_enemy;
		//numero di celle della mappa di gioco
		int n_cells=game_elements[0]; 
		int cells=n_cells;
		//variabili che conterranno la probabilita'
		double ppitrap=0;
		double penemy=0; //di default e' il wumpus
		double pstones=0;
		//ciclo di riempimento della mappa
		while( pit_trap!=0 || enemy!=0 || stones!=0) {
			//si riassegnano alle variabili i valori di default
			pit_trap=n_pit_trap;
			enemy=n_enemy;
			cells=n_cells;
			stones=n_stones;
			//svuotare la matrice per ripristinarla alla situazione iniziale
			gm.clear();
			//riempimento delle celle della matrice
			for(int i=0;i<gm.getRows();i++) { //for righe
				for(int j=0;j<gm.getColumns();j++) { //for colonne
					//si genera un numero casuale (da 0 a 1) da utilizzare come soglia 
					double random = Math.random();
					//calcolo delle probabilita' per ogni tipologia di cella
					ppitrap = prob(pit_trap, n_pit_trap, cells, n_cells);
					penemy = prob(enemy, n_enemy, cells, n_cells);
					pstones = prob(stones, n_stones, cells, n_cells);
					//si preleva la cella attuale
					Cell c = gm.getGameCell(i, j);
					//confronto delle probabilita' con la soglia random
					if(random < ppitrap) {
						//la cella e' un pozzo/trappola
						c.setCellStatus(CellStatus.DANGER);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						pit_trap-=1;
					}
					else if(random < penemy) {
						/* la cella conterra' l'avversario del pg, che sara':
						 * -il wumpus, nella modalita' eroe;
						 * -l'eroe, nella modalita' wumpus;
						 */
						 c.setCellStatus(CellStatus.ENEMY);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						enemy-=1;
					}
					else if(random < pstones) {
						/* la cella non sara' giocabile, non ci si potra' posizionare il
						 * personaggio giocabile perche' contiene un sasso
						 */
						c.setCellStatus(CellStatus.FORBIDDEN);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						stones-=1;
					}
					else {
						//la cella e' etichettata come sicura, libera
						c.setCellStatus(CellStatus.SAFE);
						//si impostano gli indici che descrivono la posizione della cella
						c.setCellPosition(i,j);
					}
					//si decrementa il numero di celle rimaste da riempire
					cells=cells-1;	
				}//for colonne
			}//for righe
		}//while
	}//initWithoutAward()			
		
	/** metodo prob(int, int, int, int): void
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
	private static double prob(int x,int max_x, int n, int max_n) {
		//controllo sui parametri
		if(max_x==0 || max_n==0)return 0;
		//numero casuale
		double random = Math.random();
		//funzione di probabilita'
		double prob = ((x/max_x) - (n/max_n) + random*0.3) /3;
		return prob;
	}//prob

	/** metodo placePGonCorner(): boolean
	 * questo metodo si occupa di posizionare il personaggio giocabile nella mappa
	 * dopo che e' stata popolata con gli altri elementi di gico.
	 * Questo personaggio giocabile puo' essere l'eroe oppure il wumpus, a seconda
	 * della modalita' di gioco considerata.
	 * Questo metodo viene eseguito dopo che il metodo init() ha trovato una
	 * configurazione valida per tutti gli elementi da posizionare.
	 * Se questo metodo dovesse fallire il posizionamento del PG, allora,
	 * il metodo di riempimento della mappa andra' rieseguito.
	 * Per la realizzazione di questo metodo bisogna tenere presente che ogni cella
	 * della cornice sara' indicata con un numero identificativo, 
	 * ad esempio la cella (1,0) e' la numero 11, per un totale di 12 id.
	 * Inoltre si utilizzaranno due vettori, quali:
	 * - un vettore di 12 celle, che conterra' gli indici riga, in ordine, di tutte
	 * 	 le celle sulla cronice, tenendo conto che gli indici di cella di questo vettore
	 *	 saranno indicativi della cella considerata.
	 * - un vettore di 12 celle, analogo al precedente, che conterra' pero' gli indici 
	 * 	 colonna.
	 * Segue la numerazione utilizzata:		
	 * | |0,0| |0,1| |0,2| |0,3| |			|  |0| |1| |2| |3| |
	 * | |1,0| |1,1| |1,2| |1,3| |			| |11| |X| |X| |4| |
	 * | |2,0| |2,1| |2,2| |2,3| |			| |10| |X| |X| |5| |
	 * | |3,0| |3,1| |3,2| |3,3| |			|  |9| |8| |7| |6| |
	 *	Cella i-esima
	 *	{0,		1,	   2,	  3,	 4,		5,	   6,	  7,	 8, 	9,	  10,	 11}
	 *	Vettore degli indi riga [i]
	 *	[0] [0] [0] [0] [1] [2] [3] [3] [3] [3] [2] [1]	
	 *	Vettore degli indici colonna [j]
	 *	[0] [1] [2] [3] [3] [3] [3] [2] [1] [0] [0] [0]	
	 * Quindi, verra' generato un numero casuale tra 0 e 12 (escluso) che indichera' la cella
	 * predestinata a contenere il personaggio giocabile, verranno estratti, rispettivamente,
	 * dal vettore degli indici di riga, l'indice i-esimo e dal vettore degli indici colonna
	 * l'indice j-esimo, per estrarre la posizione in cui e' collocata la cella di interesse,
	 * nella matrice di gioco.
	 * Se SAFE, la cella sara' idonea al posizionamento del pg.
	 * @param gm: GameMap, mappa di gioco su cui deve essere posizionato il pg;
	 * @return found: boolean, flag che indica se il posizionamento del pg sia avvenuto
	 * 						  o meno con successo.
	 */
	private static boolean placePGonCorner(GameMap gm) {
		//si scrive il vettore degli indici riga della matrice, per le celle della cornice
		int [] r_index = {0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 2, 1};
		//si scrive il vettore degli indici colonna della matrice, per le celle della cornice
		int [] c_index = {0, 1, 2, 3, 3, 3, 3, 2, 1, 0, 0, 0};
		//vettore che tiene traccia dei tentativi che sono stati effettuati
		boolean [] v_trials = new boolean[12];
		//variabile booleana che indica se e' stata trovata la posizione idonea 
		boolean found = false;
		/* variabile boolean che indica se sono stati fatti tutti i tentativi possibili,
		 * ovvero se sono state esaminate tutte le celle della cornice
		 */
		boolean all_trials =false;
		//ciclo di posizionamento del pg
		while(!found && !all_trials) {
			/* si genera il numero casuale da 0 a 12 (escluso)
			 * questo indichera' la posizione in cui trovare l'indice di interesse,
			 *sia nel vettore deglie indici riga che nel vettore degli indici colonna
			 */
			int rand = (int)(Math.random()*12);
			//si preleva l'indice di riga
			int i = r_index[rand];
			//si preleva l'indice di colonna
			int j = c_index[rand];
			//si aggiorna il vettore dei tentativi
			v_trials[rand] = true;
			//si controlla se la cella della cornice sia libera
			String cs = gm.getGameCell(i, j).getCellStatusID();
			//si controlla che sia libera
			if(cs.equals(CellStatus.SAFE.name())) {
				//se la cella e' libera si puo' posizionare il pg
				gm.getGameCell(i, j).setCellStatus(CellStatus.PG);
				//si imposta la variabile boolean
				found = true;
				//System.out.println("Il PG e' stato posizionato nella cella "+"["+i+",]["+j+"]");
				//si aggiorna il vettore che indica la posizione del og sulla mappa
				pg_start_position[0]=i; // indice di riga
				pg_start_position[1]=j; //indice di colonna
			}//fi
			else {
				//se la cella scelta non era libera si deve ciclare di nuovo 
				found =false;
			}//esle
			/* se sono state controllate tutte le celle dei vettori degli indici
			 * non c'e' una combinazione valida quindi il metodo termina senza aver 
			 * posizionato il pg
			 */
			all_trials = allTrials(v_trials);
			//System.out.println("found "+found);
			//System.out.println("areAllTrials? "+all_trials);
		}//end while
		//si indica se e' stata trovata una posizione al pg nella mappa di gioco
		return found;
	}//putPGonCorner()
	
	/** metodo allTrials(boolean []): boolean
	 * questo metodo itera il vettore dei tentativi v_trials, cioe' quello che contiene
	 * una variabile boolean per ogni cella, in modo da indicare se la casella della 
	 * matrice di gioco che corrisponde all'indice della cella del vettore e' stata gia'
	 * controllata o meno, come possibile posizione per il pg.
	 * Se tutte le celle sono state verificare, percio' il vettore dei tentativi contiene
	 * true in ogni suo elemento, allora il metodo che si occupa di posizionare il pg
	 * termina senza successo, rendendo necessario rielaborare una nuova configurazione
	 * per tutti gli elementi di gioco che devono essere posizionati sulla mappa.
	 * @param v_trials: boolean [], e' il vettore di variabili booleane;
	 * @return true, se ogni elemento del vettore e' pari a true,
	 * 		   false, altrimenti. Questo verra' verificato tenendo traccia di una variabile
	 * 		   contatore che verra' incrementata ad ogni valore pari a true trovato nel vettore.
	 * Se questo valore sara' pari alla lunghezza del vettore allora tutti gli elementi saranno
	 * true, altrimenti vorra' dire che ci sono ancora celle che non sono state prese in considerazione.
	 */
	private static boolean allTrials(boolean[] v_trials) {
		//contatore che tiene traccia di tutti i valori a true
		int c = 0;
		//si itera il vettore
		for(int i=0;i<v_trials.length;i++) {
			//si controlla il contenuto della cella
			if(v_trials[i]==true) {
				//si incrementa il contatore
				c++;
			}//fi
		}//for del vettore
		//se il contatore e' pari alla lunghezza del vettore vuol dire che tutti 
		//gli elementi sono pari a true
		return c==v_trials.length;
	}//allTrials()

	//#################### personaggio giocabile #########################
	
	/** metodo getPGstartPosition(): int[]
	 * metodo accessorio che restiuisce il vettore che contiene l'indice di riga
	 * e l'indice di colonna della cella in cui e' contenuto il personaggio giocabile.
	 * @return pg_position: int[], il vettore della posizione del personaggio giocabile.
	 */
	public static int [] getPGstartPosition() {
		//questo metodo restituisce l'indice di riga in cui si trova il pg
		return pg_start_position;
	}//getPGposition()

	//######################## vettore dei sensori #######################
	
	/** metodo updateSensors(): void
	 * questo metodo si occupa si specificare il valore dei sensori per le celle che 
	 * circoscrivono elementi di gioco di interesse, come il nemico o i pericoli.
	 * Quindi, avendo a disposizione gli indici di riga e colonna in cui questi risiedono,
	 * verra' poi specificato, nelle celle ad essi adiacenti, il valore che specifica
	 * che tipo di elemento si trova nelle vicinanze, rispettando la modalita' di gioco.
	 */
	public static void updateSensors(GameMap gm) {
		//si crea un vettore che conterra' gli indici di riga delle celle
		//che contengono i pozzi o trappole
		int [] pit_trap_i = new int[game_elements[4]];
		int [] pit_trap_j = new int[game_elements[4]];
		//si crea un vettore che contiene gli indici di riga e colonna della cella avversario
		int [] enemy_indices = new int[2];
		//si cercano le celle di interesse e si assegnano i rispettivi indici
		setIndices(gm, enemy_indices, pit_trap_i, pit_trap_j);
		//si specificano i valori dei sensori per il nemico
		enemySensor(gm, enemy_indices);
		//si specificano i valori dei sensori per gli indizi
		dangerSensor(gm, pit_trap_i, pit_trap_j);
	}//sensorAssignement()
	
	/** metodo enemySensor(int []): void
	 * questo metodo, dopo aver ricevuto in ingresso il vettore che contiene gli indici della
	 * cella in cui e' stato posizionato il nemico, wumpus o avventuriero che sia, a seconda della
	 * modalita' di gioco specificata al momento delle creazione della mappa, si occupa di impostare
	 * il primo elemento del vettore dei sensori come "true", per ogni cella adiacente a questa.
	 * @param enemy_indices: int[], e' il vettore che contiene l'indice di riga e l'indice di colonna
	 * 								della cella in cui e' stato posizionato il nemico.
	 * Nelle due modalita', si avra', rispettivamente:
	 * -STINK = true, che indica l'odore del wumpus, se il mostro si trova nelle vicinanze (hero_side);
	 * -SWISH = true, rappresenta il fruscio di foglie, causato dal nascondersi dell'avventuriero,
	 * 				  per fare un agguato al wumpus (wumpus_side= !hero_side);
	 *@param gm: GameMAp, la mappa di gioco per cui devono essere impostati i sensori;
	 */
	private static void enemySensor(GameMap gm, int[] enemy_indices) {
		//si prelevano gli indici del nemico
		int ie = enemy_indices[0];
		int je = enemy_indices[1];
		//si controlla di non sforare la dimensione del vettore
		//System.out.println("Il nemico e' nella cella "+ie+","+je);
		//si specifica il vettore dei sensori per le celle attorno al nemico
		//STINK o CREAK
		if(je>0) {
			//System.out.println("cella "+ie+","+(je-1));
			gm.getGameCell(ie, je-1).setSenseVectorCell(0,true);
		}// fi cella a SINISTRA
		if(ie>0) {
			//System.out.println("cella "+(ie-1)+","+je);
			gm.getGameCell(ie-1, je).setSenseVectorCell(0,true);
		}//fi cella in ALTO
		if(je<3) {
			//System.out.println("cella "+ie+","+(je+1));
			gm.getGameCell(ie, je+1).setSenseVectorCell(0,true);
		}//fi cella a DESTRA
		if(ie<3) {
			//System.out.println("cella "+(ie+1)+","+je);
			gm.getGameCell(ie+1, je).setSenseVectorCell(0,true);
		}//fi cella in BASSO
	}//enemySensor()

	/** metodo dangerSensors(int [], int []): void
	 * questo metodo, dopo aver ricevuto i vettori che contengono le posizioni in cui si
	 * trovano i pericoli nella mappa di gioco, pozzi o trappole che siano, in base alla 
	 * modalita' di gioco scelta, si occupa di aggiornare il valore del secondo elemento 
	 * del vettore dei sensori, impostandolo pari a "true" (BREEZE o CREAK), per tutte le celle
	 * che circoscrivono quella in cui si trova l'elemento di interesse, cioe' il pozzo o 
	 * la trappola.
	 * Rispettivamente, nelle due modalita', si avra':
	 * 
	 * -CREAK = true, rappresenta lo scricchiolio dei rami usati per camuffare la presenza di 
	 * 				  una trappola, creata dall'avventuriero (wumpus_side = !hero_side);
	 * -BREEZE = true, indica la brezza che si propaga nell'aria se nelle vicinanze si trova
	 * 				   un pozzo in cui l'avventuriero rischia di cadere (hero_side);
	 * 
	 * @param pit_trap_i: int[], vettore che contiene gli indici riga delle celle che contengono
	 * 							 gli elementi di pericolo;
	 * @param pit_trap_j: int [], vettore che contiene gli indici colonna delle celle che 
	 * 							  contengono gli elementi di pericolo;
	 * @param gm: GameMap, la mappa per cui devono essere definiti i valori dei sensori.
	 */
	private static void dangerSensor(GameMap gm, int[] pit_trap_i, int[] pit_trap_j) {
		//variabili ausiliarie per scorrere i vettori
		int id=0;
		int jd=0;
		//si iterano i vettori per prelevare gli indici riga e colonna
		for(int p=0; p<pit_trap_i.length; p++) {
			//si preleva la coppia di indici
			id = pit_trap_i[p];
			jd = pit_trap_j[p];
			//si specifica il vettore dei sensori per le celle attorno al pericolo
			//BREEZE o SWISH
			//System.out.println("Il pericolo e' nella cella "+id+","+jd);
			if(jd>0) {
				//System.out.println("cella "+id+","+(jd-1));
				gm.getGameCell(id, jd-1).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella a sinistra
			if(id>0) {
				//System.out.println("cella "+(id-1)+","+jd);
				gm.getGameCell(id-1, jd).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella in alto
			if(jd<3) {
				//System.out.println("cella "+id+","+(jd+1));
				gm.getGameCell(id, jd+1).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella a destra
			if(id<3) {
				//System.out.println("cella "+(id+1)+","+jd);
				gm.getGameCell(id+1, jd).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella in basso
		}//for
	}///dangerSensor
	
	/** metodo settingIndices(int[], int[], int[]):void
	 * questo metodo, che riceve in ingresso i tre vettore che conterranno gli indici riga e 
	 * colonna delle celle che conterranno il nemico ed i pericoli che dovra' affrontare il 
	 * personaggio giocabile, si occupa di iterare tutta la mappa di gioco, andando a prelevare
	 * proprio questi indici di interesse, per memorizzarli nei vettori ricevuti come parametro.
	 * @param enemy_indices: int[], vettore che contiene la coppia di indici riga e colonna che
	 * 								indica la posizione della cella che contiene il nemico nella
	 * 								mappa di gioco;
	 * @param pit_trap_i: int[], vettore che contiene gli indici riga di tutte le celle della mappa
	 *							 di gioco che contengono un pericolo, cioe' un pozzo oppure una trappola,
	 *							 in base alla modalita' in cui si sta giocando;
	 * @param pit_trap_j: int[], vettore che contiene gli indici colonna di tutte le celle della mappa
	 *							 di gioco che contengono un pericolo, cioe' un pozzo oppure una trappola,
	 *							 in base alla modalita' in cui si sta giocando;
	 *@param gm: GameMap, la mappa di gioco sulla base della quale si devono aggiornare
	 *					  i valori dei due sensi del vettore dei sensori.
	 */
	private static void setIndices(GameMap gm, int[] enemy_indices, int[] pit_trap_i, int[] pit_trap_j) {
		//variabile ausiliaria
		String cstatus = new String("");
		//indici per iterare i valori dei vettori delle posizioni di pozzi o trappole
		int i_pt=0;
		int j_pt=0;
		//si itera la mappa di gioco dopo che e' stata totalmente popolata
		for(int i=0;i<gm.getRows();i++) { //for righe
			//for colonne
			for(int j=0;j<gm.getColumns();j++) {
				//si preleva lo stato della cella attuale sottoforma di stringa
				cstatus = new String(gm.getGameCell(i, j).getCellStatusID());
				//si analizza lo stato della cella corrente
				if(cstatus.equals("ENEMY")) {
					//e'stato trovato l'avversario del pg
					//si prelevano gli indici di cella
					enemy_indices[0]=i; //indice di riga
					enemy_indices[1]=j; //indice di colonna 
					//System.out.println(cstatus);
				}//fi ENEMY
				if(cstatus.equals("DANGER")) {
					//e' stata trovata una cella contenente un pericolo
					pit_trap_i[i_pt] = i; //indice di riga
					pit_trap_j[j_pt] = j; //indice di colonna
					//controllo sugli indici e conseguente incremento
					if(i_pt < pit_trap_i.length && j_pt < pit_trap_j.length) {
						//si incrementano gli indici per accedere alla cella successiva
						i_pt++;
						j_pt++;
					}//fi indici
					//System.out.println(cstatus);
				}//fi DANGER
			}//for colonne
		}//for righe
	}//setIndices()
	
	/** metodo printSensors(GameMap, boolean): void
	 * questo metodo stampa il vettore dei sensori per ogni cella della mappa
	 * di gioco, dopo che questa e' stata popolata.
	 * @param gm: GameMap, mappa di gioco;
	 * @param info: boolean, se true viene stampata la legenda del vettore dei
	 * 						 sensori, altrimenti soltanto il contenuto.
	 */
	public static void printSensors(GameMap gm, boolean info) {
		//si itera la mappa
		for(int i=0;i<gm.getRows();i++) {
			for(int j=0;j<gm.getColumns();j++) {
				System.out.println("Cella ("+i+','+j+')'+gm.getGameCell(i, j));
				System.out.println(gm.getGameCell(i, j).senseVectorToString(info));
			}//end for colonne
		}//end for righe
	}//printSensors()

}//Starter
