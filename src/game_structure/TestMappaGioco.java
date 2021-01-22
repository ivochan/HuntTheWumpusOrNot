package game_structure;

public class TestMappaGioco {

	public static void main (String [] args) {
		
		/*
		//test visualizzazione cella
		Cell c1 = new Cell();
		boolean hero_side=true;
		Cell c2 = new Cell(CellStatus.GOLD,hero_side,true,true);
		//System.out.println(c2);
		System.out.println(c2.SenseVectorToString(hero_side));
		//System.out.println("La cella e' stata visitata? "+ (hero_side?"si":"no"));
		GameMap g = new GameMap(hero_side);
		//riempimento mappa
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				g.mappa_gioco[i][j]=new Cell(CellStatus.SAFE,hero_side,true,true);
			}
		}
		*/
		//creazione di una nuova mappa
		GameMap mappa = new GameMap();
		//vettore degli elementi di gioco
		int [] elem_gioco= new int [8];
		//riempimento del vettore
		//riempiVettore(mappa,elem_gioco);
		//stampa su console
		//stampaVettore(elem_gioco);
		
		//ESEMPIO del vettore di gioco
		//| [16] [16] [13]  [3]  [1]  [1]  [1]  [2]  [2] 
		elem_gioco[0]=16;//celle da riempire rimaste
		elem_gioco[1]=16;//n_max celle
		elem_gioco[2]=3;//sassi (celle non giocabili)
		elem_gioco[3]=1;//oro
		elem_gioco[4]=1;//eroe
		elem_gioco[5]=1;//wumpus
		elem_gioco[6]=2;//pozzi
		elem_gioco[7]=2;//n_max pozzi
		stampaVettore(elem_gioco);
		//stampa della mappa
		//System.out.println(mappa);
		//popolamento della mappa
		popolaMappa(mappa,elem_gioco);
		//stampa a video della mappa di gioco
		System.out.println(mappa);
		
	}//main
	
	private static void stampaVettore(int[] elem_gioco) {
		//si stampa una legenda
		System.out.println("[celle]   [n_max celle]   [sassi]  [oro]"
				+ "  [eroe] [wumpus] [pozzi] [n_max pozzi]\n");
		//si stampa il contenitore a sinistra
		System.out.print("|");
		//si scorrono le celle del vettore
		for(int i=0;i<elem_gioco.length;i++) {
			//si stampa la cella i-esima
			if(i<elem_gioco.length-1) {
				System.out.print(" ["+elem_gioco[i]+"]	   ");
			}
			else {
				System.out.print(" ["+elem_gioco[i]+"] ");
			}
		}//for
		System.out.println("|");
	}//stampaVettore

	private static void popolaMappa(GameMap mappa, int [] elem_gioco) {
		//si itera la mappa
		for(int i=0;i<mappa.mappa_gioco.length;i++) { //si scorrono le righe
			//si scorrono le colonne
			for(int j=0;j<mappa.mappa_gioco.length;j++) {
				//si considera la cella 
				CellStatus cs = scegliCella(mappa.mappa_gioco[i][j],elem_gioco);
				//si impsta il contenuto della cella
				mappa.mappa_gioco[i][j] = new Cell(cs);
				//si stampa il vettore degli elementi di gioco
				//stampaVettore(elem_gioco);
			}//for colonne
		}//for righe
	}//popolaMappa
	
	private static void riempiVettore(GameMap mappa,int[] elem_gioco) {
		//numero massimo delle celle della mappa
		int max_n=mappa.getMapDimension();
		// numero di celle da riempire rimaste
		int n=max_n;
		//numero delle celle da riempire nella mappa di gioco
		elem_gioco[0]=n;
		//numero massimo delle caselle della mappa
		elem_gioco[1]=max_n;
		//si sceglie casualmente il numero di celle non giocabili (da 0 a 4)
		//ovvero il numero di celle non accessibili PIETRA		
		int d=(int)(Math.random()*5);//(da 0 a 5 escluso)
		//celle non giocabili DENIED
		elem_gioco[2]=d;
		//oro 
		int g=1;
		elem_gioco[3]=g;
		//avventuriero 
		int h=1;
		elem_gioco[4]=h;
		//mostro 
		int w=1;
		elem_gioco[5]=w;
		//pozzi da mettere nella mappa (hero_side = true)
		int p = 2; 
		int max_p = 2;
		//trappole da mettere al posto dei pozzi (hero_side = false)
		int t = p; 
		//numero massimo di trappole
		int max_t = max_p;
		//nel vettore 
		if(mappa.getGameMode()) {
			//hero_side
			elem_gioco[6]=p;
			elem_gioco[7]=max_p;
		}
		else {
			//wumpus_side
			elem_gioco[6]=t;
			elem_gioco[7]=max_t;
		}
	}//riempiVettore

	private static CellStatus scegliCella(Cell cell, int[] elem_gioco) {
		//metodo che specifica la tipologia di cella che sara' mappa[i][j]
		//variabili da utilizzare per il calcolo
		//System.out.println("Metodo scegliCella");
		//si calcola un seme (un numero casuale che sara' la soglia con cui
		//confrontare la probabilita'
		double seed = (Math.random());//numero tra 0 e 1 escluso
		//HERO_SIDE
		if(elem_gioco[6]!=0&&isPit(seed,elem_gioco[6],elem_gioco[7],elem_gioco[0], elem_gioco[1])) {
			/* -seed e' il valore di soglia con cui confrontare la probabilita' ottenuta
			 * -x = elem_gioco[6] e' il numero di pozzi rimasti da posizionare;
			 * -max_x = elem_gioco[7] e' il numero totale di pozzi;
			 * -n = elem_gioco[0] e' il numero di celle rimaste da rimepire;
			 * -n_max = elem_gioco[1] e' il numero massimo di celle che costituiscono la mappa;
			 */
			//la cella e' un pozzo
			//quindi si devono aggiornare i parametri
			//n -> una celle e' stata occupata percio' il numero di celle libere e' diminuito
			elem_gioco[0]-=1; 
			//p -> un pozzo e' stato assegnato, percio' il numero di pozzi da collocare e' diminuito
			elem_gioco[6]-=1;
			//si stampa l'attuale vettore degli elementi di gioco
			//si assegna l'etichetta alla cella
			return CellStatus.PIT;
		}
		else if(isWumpus(seed,elem_gioco[0],elem_gioco[4])) {
			//nella cella e' stato messo il wumpus
			//TODO aggiornare il vettore dei sensori
			return CellStatus.WUMPUS;
		}
		else if(isGold(seed,elem_gioco[0],elem_gioco[2])) {
			//e' stato messo l'oro
			return CellStatus.GOLD;
		}
		else if(isStone(seed,elem_gioco[0],elem_gioco[1])) {
			//la cella e' DENIED
			return CellStatus.DENIED;
		}
		else {
			System.out.println("Cella SAFE\n");
			//allora e' una cella sicura
			//si aggiornano i parametri
			elem_gioco[0]-=1;//numero celle da riempire
			return CellStatus.SAFE;
		}
			//TODO manca il posizionamento dell'eroe
	}//scegliCella

	private static boolean isStone(double seed, int i, int j) {
		//i parametri sono il valore di soglia con cui 
		//confrontare la probabilita' da calcolare
		//il numero di pietre che ancora possono essere messe
		//il numero massimo di pietre in generale
		//si calcola la probabilita'della cella
	
		return false;
	}

	private static boolean isGold(double seed, int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isWumpus(double seed, int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private static boolean isPit(double seed, int x_pit, int x_max_pit, int n, int n_max) {
		/* parametri:
		 *-seed, e' il valore di soglia con cui confrontare la probabilita'
		 * calcolata per la cella;
		 *-x_pit, e' il numero di pozzi che ancora devono essere messi nella mappa;
		 *-x_max_pit, e' il numero massimo di pozzi da mettere nella mappa;
		 *-n, e' il numeor di celle della mappa che ancora devono essere riempite;
		 *-n_max, il numero di caselle da cui e' composta la mappa di gioco.
		 */
		//calcolo della probabilita' per cui la cella possa essere
		//classificata come PIT in base ai parametri forniti
		double prob=lambda(x_pit, x_max_pit, n, n_max);
		System.out.println("Probabilita' pozzo "+prob);
		System.out.println("Soglia pozzo "+seed+"\n");
		//confronto con il valore di soglia
		//TODO controllare criterio
		if(Math.abs(prob)<=seed) {
			System.out.println("Pozzo posizionato!\n");
			//allora la casella sara' etichettata come PIT
			return true;
		}
		//se la soglia non e' stata rispettata allora questa
		//cella non sara' un pozzo
		return false;
	}//isPit
	
	//funzione di probabilita' utilizzata per stabilire il valore che deve assumere la cella di interesse
	private static double lambda(int x,int max_x, int n, int max_n) {
		/* - x, numero del tipo di oggetti cella da inserire;
		 * - max_x, numero massimo di oggetti di tipo x che possono essere inseriti nella mappa;
		 * - n, numero di celle della mappa che devono essere ancora riempite;
		 * - max_n, numero massimo di celle della mappa che possono essere riempite (giocabili).
		 */
		double prob = ((x/max_x)) - (1-n/max_n);
		//System.out.println(prob);
		//probabilita'
		return prob;
	}//lambda

	
	

}//TestMappaGioco
