package game.structure.map;

import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.GameElements;
import game.structure.elements.PlayableCharacter;
import game.structure.elements.Sensors;
/** class MapConfiguration
 * questa classe si occupa di effettuare tutte le configurazioni necessarie
 * a preparare il campo di gioco, inizializzando gli elementi di gioco, con cui il 
 * personaggio giocabile dovra' irterfacciarsi durante la sua esplorazione della mappa
 * @author ivonne
 */
public class MapConfiguration {
	
	/** metodo placeGameElements(GameMap, int[]): void
	 * questo metodo si occupa del popolamento della mappa di gioco, 
	 * inserendo tutti gli elementi che possono essere posizionati nella mappa, 
	 * ad eccezione del personaggio giocabile e del premio. 
	 * Dopo aver assegnato ai parametri i valori degli elementi da posizionare, 
	 * viene effettuato un ciclo che verra' ripetuto fino a quando non si determini 
	 * una configurazione che soddisfi tutte le condizioni.
	 * Per quanto riguarda la scelta della tipologia di cella, verra' calcolata
	 * la probalita' per cui questa possa essere etichettata nella tipologia che
	 * si sta valutando, se il valore ottenuto tramite la funzione 
	 * prob(int,int,int, int) risulta maggiore del valore utilizzato come soglia.
	 * @param gm: GameMap, e' la mappa di gioco che deve essere costruita.
	 * @param game_elements: int[], vettore che contiene i dati relativi agli 
	 * 								elementi di gioco.
	 */
	private static void placeGameElements(GameMap gm, int[] game_elements) {
		//numero di celle della mappa di gioco
		int n_cells=game_elements[0]; 
		int cells=n_cells;
		//sassi
		int n_stones=game_elements[1];
		int stones=n_stones;
		//premio game_elements[2]		
		//nemico
		int n_enemy=game_elements[3];
		int enemy=n_enemy;
		//pericolo
		int n_danger=game_elements[4];
		int danger=n_danger;
		//variabili che conterranno la probabilita'
		double pdanger=0;
		double penemy=0;
		double pstones=0;
		//ciclo di riempimento della mappa
		while( enemy>0 || danger>0 || stones>0 ) {
			/*si riassegnano alle variabili i valori di default
			 *in modo da resettare la situazione se la configurazione ottenuta
			 *per la mappa di gioco non e' idonea */
			danger=n_danger;
			enemy=n_enemy;
			cells=n_cells;
			stones=n_stones;
			//svuotare la matrice per ripristinarla alla situazione iniziale
			gm.clear();
			//for righe
			for(int i=0;i<gm.getRows();i++) {
				//for colonne
				for(int j=0;j<gm.getColumns();j++) {
					//si genera un numero casuale (da 0 a 1) da utilizzare come soglia 
					double random = Math.random();
					//calcolo delle probabilita' per ogni tipologia di cella
					pdanger = prob(danger, n_danger, cells, n_cells);
					penemy = prob(enemy, n_enemy, cells, n_cells);
					pstones = prob(stones, n_stones, cells, n_cells);
					//si preleva la cella attuale
					Cell c = gm.getMapCell(i, j);
					//confronto delle probabilita' con la soglia random
					if(random < pdanger) {
						//la cella e' un pozzo/trappola
						c.setCellStatus(CellStatus.DANGER);
						//si impostano gli indici che descrivono la posizione della cella
						c.setPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						danger=danger-1;
					}//fi pericolo
					else if(random < penemy) {
						//la cella conterra' l'avversario del pg
						 c.setCellStatus(CellStatus.ENEMY);
						//si impostano gli indici che descrivono la posizione della cella
						c.setPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						enemy=enemy-1;
					}//fi nemico
					else if(random < pstones) {
						//la cella non sara' giocabile, non ci si potra' posizionare il pg
						c.setCellStatus(CellStatus.FORBIDDEN);
						//si impostano gli indici che descrivono la posizione della cella
						c.setPosition(i, j);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						stones=stones-1;
					}//fi sasso
					else {
						//la cella e' etichettata come sicura, libera
						c.setCellStatus(CellStatus.SAFE);
						//si impostano gli indici che descrivono la posizione della cella
						c.setPosition(i,j);
					}//esle libera
					//si decrementa il numero di celle rimaste da riempire
					cells=cells-1;	
				}//for colonne
			}//for righe
		}//while
	}//placeGameElements(GameMap, int[])	

	/** metodo placeAward(GameMap): boolean
	 * questo metodo si occupa di posizionare il premio in un punto
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
		//numero di celle
		int n_cells=gm.getMapDimension();
		int cells = n_cells;
		//si iterano le celle della matrice
		while(award!=0) {
			//for righe
			for(int i=0;i<gm.getRows();i++) {
				//for colonne
				for(int j=0;j<gm.getColumns();j++) {
					//numero casuale (da 0 a 1) da utilizzare come soglia 
					double random = Math.random();
					//probabilita
					paward = prob(award, n_award, cells, n_cells);
					//si preleva la cella attuale
					Cell c = gm.getMapCell(i,j);
					//si aggiorna il numero di celle gia' esaminate
					cells = cells-1;
					//confronto delle probabilita' con la soglia random
					if(random < paward) {
						//si controllano le celle adiacenti
						found = GameMap.areAdjacentCellsSafe(gm, i, j);
						//se e'stata trovata una posizione
						if(found){
							//si posiziona il premio
							c.setCellStatus(CellStatus.AWARD);
							//si impostano gli indici che descrivono la posizione della cella
							c.setPosition(i, j);
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
	 * questo metodo si occupa di trovare una configurazione valida della
	 * mappa di gioco, generando una struttura di base, in cui sono stati
	 * posizionati tutti gli elementi di gioco, ad eccezione del personaggio
	 * giocabile, che verra' posizionato subito dopo.
	 * Se il posizionamento del pg non va a buon fine, la mappa deve essere
	 * generata nuovamente.
	 * @param gm: GameMap, e' l'istanza che rappresenta la mappa da costruire,
	 * 					   su cui verra' poi avviata la sessione di gioco.
	 */
	public static void init(GameMap gm) {
		//variabile asiliarie 
		boolean done = false;
		boolean award=false;
		//generazione degli elementi di gioco
		GameElements gels =new GameElements(gm);
		//System.out.println(gels.gameElementsToString());
		//si preleva il vettore degli elementi di gioco
		int[] game_elements = gels.getGameElements();
		//ciclo
		while(!done | !award) {
			//si riempie la mappa di gioco
			placeGameElements(gm, game_elements);
			//si cerca di psizionare il premio
			award = placeAward(gm);
			//si cerca di posizionare il pg
			done = PlayableCharacter.placePGonCorner(gm);
		}//end while
		//si aggiorna il vettore dei sensori per ogni cella
		Sensors.updateSensors(gm, game_elements);
	}//init()
	

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
	}//prob(int, int, int, int)

}//MapConfiguration

