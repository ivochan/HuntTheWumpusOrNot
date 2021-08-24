package game.structure.map;
//serie di import
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
	 */
	private static void placeGameElements(GameMap gm) {
		//numero di celle della mappa di gioco
		int n_cells=GameElements.getNumberOfCells(); 
		int cells=n_cells;
		//sassi
		int n_stones=GameElements.getStones();
		int stones=n_stones;
		//nemico
		int n_enemy=GameElements.getEnemy();
		int enemy=n_enemy;
		//pericolo
		int n_danger=GameElements.getDanger();
		int danger=n_danger;
		//premio
		int n_award=GameElements.getAward();
		int award=n_award;
		//variabili che conterranno la probabilita'
		double pdanger=0;
		double penemy=0;
		double pstones=0;
		double paward=0;
		//variabile ausiliaria per il random
		double random=0;
		//ciclo di riempimento della mappa
		do {
			//si riassegnano alle variabili i valori di default
			//in modo da resettare la situazione se la configurazione ottenuta
			//per la mappa di gioco non e' idonea */
			danger=n_danger;
			enemy=n_enemy;
			cells=n_cells;
			stones=n_stones;
			award=n_award;
			//svuotare la matrice per ripristinarla alla situazione iniziale
			gm.clear();
			//for righe
			for(int i=0;i<gm.getRows();i++) {
				//for colonne
				for(int j=0;j<gm.getColumns();j++) {
					//si genera un numero casuale (da 0 a 1) da utilizzare come soglia 
					//random = Math.random()*0.1;
					random = Math.random();
					//calcolo delle probabilita' per ogni tipologia di cella
					pdanger = prob(danger, n_danger, cells, n_cells);
					penemy = prob(enemy, n_enemy, cells, n_cells);
					pstones = prob(stones, n_stones, cells, n_cells);
					paward= prob(award, n_award, cells, n_cells);
					//si preleva la cella attuale
					Cell c = gm.getMapCell(i, j);
					//confronto delle probabilita' con la soglia random
					if(random < pdanger) {
						//la cella e' un pozzo/trappola
						c.setCellStatus(CellStatus.DANGER);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						danger=danger-1;
					}//fi pericolo
					else if(random < penemy) {
						//la cella conterra' l'avversario del pg
						 c.setCellStatus(CellStatus.ENEMY);
						 //si decrementa la variabile, perche' un elemento e' stato posizionato
						enemy=enemy-1;
					}//fi nemico
					else if(random < pstones) {
						//la cella non sara' giocabile, non ci si potra' posizionare il pg
						c.setCellStatus(CellStatus.FORBIDDEN);
						//si decrementa la variabile, perche' un elemento e' stato posizionato
						stones=stones-1;
					}//fi sasso
					else if(random < paward) {
						//la cella conterra' il premio
						c.setCellStatus(CellStatus.AWARD);
						//si decrementa la variabile perche' l'elemento e' stato posizionato
						award=award-1;
					}//fi premio
					else {
						//la cella e' etichettata come sicura, libera
						c.setCellStatus(CellStatus.SAFE);	
					}//esle libera
					//si impostano gli indici che descrivono la posizione della cella
					c.setPosition(i,j);
					//si decrementa il numero di celle rimaste da riempire
					cells=cells-1;	
				}//for colonne
			}//for righe
		//}//while
		}while( enemy>0 || danger>0 || stones>0 || award>0 );
	}//placeGameElements(GameMap, int[])
	
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
	public static void init(GameMap gm, GameMap em) {
		//variabile asiliaria
		boolean done = false;
		//generazione degli elementi di gioco
		GameElements.generatingGameElements(gm);
		//DEBUG
		//System.out.println(GameElements.printGameElements());
		//ciclo di posizionamento degli elementi di gioco e del pg
		do {
			//posizionamento degli elementi di gioco nella mappa
			placeGameElements(gm);
			//si cerca di posizionare il personaggio giocabile
			done = PlayableCharacter.placePGonCorner(gm);
			//si esce dal ciclo se il pg e' stato posizionato
		}while(!done);
		//si aggiorna il vettore dei sensori per ogni cella
		Sensors.updateSensors(gm);
		//si preleva la posizione del pg
		int [] pg_pos = PlayableCharacter.getPGposition();
		//si aggiorna la mappa di esplorazione
		em.getMapCell(pg_pos[0], pg_pos[1]).setCellStatus(CellStatus.PG);	
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
		if(x==0)return 0;
		//numero casuale
		double random = Math.random();
		//funzione di probabilita'
		double prob = ((x/max_x) - (n/max_n) + random*0.3) /3;		
		return prob;
	}//prob(int, int, int, int)

}//MapConfiguration

