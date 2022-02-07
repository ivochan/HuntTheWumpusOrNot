package game.structure.elements;
// serie di import
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;
/** class Sensors
 * questa classe si occupa di assegnare, ad ogni cella della mappa, 
 * il valore del vettore dei sensori, sulla base del contenuto delle 
 * celle adiacenti.
 * @author ivochan
 *
 */
public class Sensors {
		
	/** metodo updateSensors(GameMap, int[]): void
	 * questo metodo si occupa si specificare il valore dei sensori per le celle che 
	 * circoscrivono elementi di gioco di interesse, come il nemico o i pericoli.
	 * Quindi, avendo a disposizione gli indici di riga e colonna in cui questi risiedono,
	 * verra' poi specificato, nelle celle ad essi adiacenti, il valore che specifica
	 * che tipo di elemento si trova nelle vicinanze, rispettando la modalita' di gioco.
	 */
	public static void updateSensors(GameMap gm) {
		//vettore che conterra' gli indici di riga delle celle che contengono i pericoli
		int [] danger_i = new int[GameElements.getDanger()];
		int [] danger_j = new int[GameElements.getDanger()];
		//si crea un vettore che contiene gli indici di riga e colonna della cella avversario
		int [] enemy_indices = new int[2];
		//si cercano le celle di interesse e si assegnano i rispettivi indici
		setIndices(gm, enemy_indices, danger_i, danger_j);
		//si specificano i valori dei sensori per il nemico
		enemySensor(gm, enemy_indices);
		//si specificano i valori dei sensori per gli indizi
		dangerSensor(gm, danger_i,danger_j);
	}//updateSensors(Map, int[])
	
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
	 *@param gm: GameMap, la mappa di gioco per cui devono essere impostati i sensori;
	 */
	private static void enemySensor(GameMap gm, int[] enemy_indices) {
		//variabile ausiliaria
		Cell c;
		//si prelevano gli indici del nemico
		int ie = enemy_indices[0];
		int je = enemy_indices[1];
		//si specifica il vettore dei sensori per le celle attorno al nemico
		if(je>0) {
			//System.out.println("cella "+ie+","+(je-1));
			c = gm.getMapCell(ie, je-1);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(), true);
		}// fi cella a SINISTRA
		if(ie>0) {
			//System.out.println("cella "+(ie-1)+","+je);
			c = gm.getMapCell(ie-1, je);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(),true);
		}//fi cella in ALTO
		if(je<3) {
			//System.out.println("cella "+ie+","+(je+1));
			c = gm.getMapCell(ie, je+1);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(),true);
		}//fi cella a DESTRA
		if(ie<3) {
			//System.out.println("cella "+(ie+1)+","+je);
			c = gm.getMapCell(ie+1, je);
			c.setSenseVectorCell(CellStatus.ENEMY_SENSE.ordinal(),true);
		}//fi cella in BASSO
	}//enemySensor(Map, int[])

	/** metodo dangerSensor(int [], int []): void
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
	 * @param danger_i: int[], vettore che contiene gli indici riga delle celle che contengono
	 * 							 gli elementi di pericolo;
	 * @param dangerp_j: int [], vettore che contiene gli indici colonna delle celle che 
	 * 							  contengono gli elementi di pericolo;
	 * @param gm: GameMap, la mappa per cui devono essere definiti i valori dei sensori.
	 */
	private static void dangerSensor(GameMap gm, int[] danger_i, int[] danger_j) {
		//variabili ausiliarie per scorrere i vettori
		int id=0;
		int jd=0;
		//si iterano i vettori per prelevare gli indici riga e colonna
		for(int p=0; p<danger_i.length; p++) {
			//si preleva la coppia di indici
			id = danger_i[p];
			jd = danger_j[p];
			//si specifica il vettore dei sensori per le celle attorno al pericolo
			if(jd>0) {
				//System.out.println("cella "+id+","+(jd-1));
				gm.getMapCell(id, jd-1).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella a sinistra
			if(id>0) {
				//System.out.println("cella "+(id-1)+","+jd);
				gm.getMapCell(id-1, jd).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella in alto
			if(jd<3) {
				//System.out.println("cella "+id+","+(jd+1));
				gm.getMapCell(id, jd+1).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella a destra
			if(id<3) {
				//System.out.println("cella "+(id+1)+","+jd);
				gm.getMapCell(id+1, jd).setSenseVectorCell(CellStatus.DANGER_SENSE.ordinal(),true);
			}//fi cella in basso
		}//end for
	}///dangerSensor(Map, int[], int[])
	
	/** metodo setIndices(Map, int[], int[], int[]):void
	 * questo metodo, che riceve in ingresso i tre vettori che conterranno gli indici riga e 
	 * colonna delle celle che conterranno il nemico ed i pericoli che dovra' affrontare il 
	 * personaggio giocabile, si occupa di iterare tutta la mappa di gioco, andando a prelevare
	 * proprio questi indici di interesse, per memorizzarli nei vettori ricevuti come parametro.
	 * @param enemy_indices: int[], vettore che contiene la coppia di indici riga e colonna che
	 * 								indica la posizione della cella che contiene il nemico nella
	 * 								mappa di gioco;
	 * @param danger_i: int[], vettore che contiene gli indici riga di tutte le celle della mappa
	 *							 di gioco che contengono un pericolo, cioe' un pozzo oppure una trappola,
	 *							 in base alla modalita' in cui si sta giocando;
	 * @param danger_j: int[], vettore che contiene gli indici colonna di tutte le celle della mappa
	 *							 di gioco che contengono un pericolo, cioe' un pozzo oppure una trappola,
	 *							 in base alla modalita' in cui si sta giocando;
	 *@param gm: GameMap, la mappa di gioco sulla base della quale si devono aggiornare
	 *					  i valori dei due sensi del vettore dei sensori.
	 */
	private static void setIndices(GameMap gm, int[] enemy_indices, int[] danger_i, int[] danger_j) {
		//indici per iterare i valori dei vettori delle posizioni di pozzi o trappole
		int i_pt=0;
		int j_pt=0;
		//si itera la mappa di gioco dopo che e' stata totalmente popolata
		for(int i=0;i<gm.getRows();i++) { //for righe
			//for colonne
			for(int j=0;j<gm.getColumns();j++) {
				//si preleva lo stato della cella attuale sottoforma di stringa
				CellStatus cs = gm.getMapCell(i, j).getCellStatus();
				//si analizza lo stato della cella corrente
				if(cs.equals(CellStatus.ENEMY)) {
					//si prelevano gli indici ddella cella che contiene il nemico
					enemy_indices[0]=i;
					enemy_indices[1]=j;
				}//fi ENEMY
				if(cs.equals(CellStatus.DANGER)) {
					//e' stata trovata una cella contenente un pericolo
					danger_i[i_pt++] = i; //indice di riga
					danger_j[j_pt++] = j; //indice di colonna
				}//fi DANGER
				//System.out.println(cs);
			}//for colonne
		}//for righe
	}//setIndices(Map, int[], int[], int[])
	
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
				System.out.println("Cella ("+i+','+j+')'+gm.getMapCell(i, j));
				System.out.println(gm.getMapCell(i, j).senseVectorToString(info));
			}//end for colonne
		}//end for righe
	}//printSensors()Map, boolean
	
}//end Sensors
