package game.structure.elements;

import game.structure.cell.CellStatus;
import game.structure.map.GameMap;

/** class PlayableCharacter
 * Questa classe si deve occupare di tenere traccia della posizione del PG
 * all'interno della mappa di gioco, occupandosi proprio del suo insierimento
 * nella matrice, dopo che e' stata popolata con tutit gli altri elementi.
 * E' necessatio che il suo percorso non venga bloccato da celle non accessibili.
 * Questo personaggio giocabile puo' essere l'eroe oppure il wumpus, a seconda
 * della modalita' di gioco considerata.
 * 
 * Questa classe e' stata implementata tenendo conto che il terreno di gioco e'
 * costituito da una matrice di dimensione (4 x 4).
 * Ogni cella della cornice e' indicata da un numero identificativo:
 * Cella i-esima della cornice
 * {0,		1,	   2,	  3,	 4,		5,	   6,	  7,	 8, 	9,	  10,	 11}
 * Inoltre si utilizzaranno due vettori, quali:
 * - un vettore di 12 celle, che conterra' gli indici riga, in ordine, di tutte
 * 	 le celle sulla cronice, tenendo conto che gli indici di cella di questo vettore
 *	 saranno indicativi della cella considerata.
 * - un vettore di 12 celle, analogo al precedente, che conterra' pero' gli indici 
 * 	 colonna.
 * Segue la numerazione utilizzata:		
 *
 * | |0,0| |0,1| |0,2| |0,3| |			| | 0| |1| |2| |3| |
 * | |1,0| |1,1| |1,2| |1,3| |			| |11| |X| |X| |4| |
 * | |2,0| |2,1| |2,2| |2,3| |			| |10| |X| |X| |5| |
 * | |3,0| |3,1| |3,2| |3,3| |			| | 9| |8| |7| |6| |
 * 
 * Cella i-esima centrale 
 * {12,		13,	   14,	  15}
 * si dovra' generale un numero casuale tra 12 e 16 (escluso)
 * il posizionamento centrale si potra' utilizzare se il pg e' il wumpus
 *
 * @author ivonne
 *
 */
public class PlayableCharacter {
	//##### attributi di classe #####
	
	//posizione (i,j) del pg nella mappa di gioco
	private static int [] pg_position = new int[2];
	
	//per una matrice di gioco (4 x 4)
	//vettore degli indici riga, per le celle della cornice
	private static int [] r_index = {0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 2, 1};
	//vettore degli indici colonna, per le celle della cornice
	private static int [] c_index = {0, 1, 2, 3, 3, 3, 3, 2, 1, 0, 0, 0};
	//vettore degli indici riga, per le celle centrali
	//private static int [] r_center_index = {1, 1, 2, 2 };
	//vettore degli indici colonna, per le celle centrali
	//private static int [] c_center_index = {0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 2, 1};
	
	
	/** metodo placePGonCorner(): boolean
	 * questo metodo si occupa di posizionare il personaggio giocabile nella mappa
	 * dopo che e' stata popolata con gli altri elementi di gioco.
	 * Se questo metodo dovesse fallire il posizionamento del PG, allora,
	 * il metodo di riempimento della mappa andra' rieseguito.
	 * Allora verra' generato un numero casuale tra 0 e 12 (escluso) che indichera' la cella
	 * predestinata a contenere il personaggio giocabile, verranno estratti, rispettivamente,
	 * dal vettore degli indici di riga, l'indice i-esimo e dal vettore degli indici colonna
	 * l'indice j-esimo, per estrarre la posizione in cui e' collocata la cella di interesse,
	 * nella matrice di gioco.
	 * Se SAFE, la cella sara' idonea al posizionamento del pg.
	 * @param gm: GameMap, mappa di gioco su cui deve essere posizionato il pg;
	 * @return found: boolean, flag che indica se il posizionamento del pg sia avvenuto
	 * 						  o meno con successo.
	 */
	public static boolean placePGonCorner(GameMap gm) {
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
			 * sia nel vettore deglie indici riga che nel vettore degli indici colonna
			 */
			int rand = (int)(Math.random()*12);
			//si preleva l'indice di riga
			int i = r_index[rand];
			//si preleva l'indice di colonna
			int j = c_index[rand];
			//si aggiorna il vettore dei tentativi
			v_trials[rand] = true;
			//si controlla se la cella della cornice sia libera
			CellStatus cs = gm.getMapCell(i, j).getCellStatus();
			//si controlla che sia libera
			if(cs.equals(CellStatus.SAFE)) {
				//si controlla se tra le celle adiacenti ce n'e' una libera
				found = gm.areAdjacentCellsSafe(i, j);
				//si verifica se la condizione e' soddisfatta
				if(found) {
					//si puo' posizionare il pg
					gm.getMapCell(i, j).setCellStatus(CellStatus.PG);
					//si aggiorna il vettore della posizione del pg sulla mappa
					pg_position[0]=i; // indice di riga
					pg_position[1]=j; //indice di colonna
					//si restituisce il flag
					return true;
				}//fi
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
		/*se il contatore e' pari alla lunghezza del vettore vuol dire che tutti 
		 *gli elementi sono pari a true*/
		return c==v_trials.length;
	}//allTrials(boolean[])
	
	//##### metodi accessori #####
	
	/** metodo getPGposition(): int[]
	 * metodo accessorio che restiuisce il vettore che contiene l'indice di riga
	 * e l'indice di colonna della cella in cui e' contenuto il personaggio giocabile.
	 * @return pg_position: int[], il vettore della posizione del personaggio giocabile.
	 */
	public static int [] getPGposition() {
		//questo metodo restituisce l'indice di riga e colonna in cui si trova il pg
		return pg_position;
	}//getPGposition()
	
	/** metodo setPGpositon(int [], Map): void
	 * questo metodo imposta l'indice di riga e di colonna in cui si trova il pg
	 * all'interno della matrice di gioco.
	 * @param position: int [], vettore che contiene gli indici di posizione;
	 */
	public static void setPGposition(int [] position) {
		//controllo sul vettore della posizione
		if(position==null) throw new IllegalArgumentException("vettore della posizione nullo");
		//controllo sulla dimensione del vettore
		if(position.length!=2) throw new IllegalArgumentException("il vettore della posizione"
				+ " non ha una dimensione valida");
		//si preleva l'indice di riga
		int i = position[0];
		//si preleva l'indice di colonna
		int j = position[1];
		//si assegna l'indice di riga
		pg_position[0]=i;
		//si assegna l'indice della colonna
		pg_position[1]=j;	
	}//setPGposition(int,int, Map)
	
	//##### altri metodi #####
	
	/** metodo getPGpositionToString(): String
	 * metodo che restituisce la posizione del pg all'interno della mappa
	 * di gioco sotto forma di stringa
	 * @return p
	 */
	public static String getPGpositionToString() {
		//si restituisce la stringa che rappresenta il contenuto del vettore posizione
		return "("+pg_position[0]+','+pg_position[1]+')';
	}//getPGPositionToString()
	
}//PG
