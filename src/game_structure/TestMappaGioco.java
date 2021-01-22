package game_structure;

public class TestMappaGioco {

	public static void main (String [] args) {
		
		//variabile booleana per la modalita'
		boolean hero_side=true;
		//creazione di una nuova mappa
		GameMap mappa = new GameMap(hero_side);
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
		elem_gioco[7]=1;//n_max pozzi
		stampaVettore(elem_gioco);
		//popolamento della mappa
		//mappa.popolaMappa();
		//stampa a video della mappa di gioco
		//System.out.println(mappa);
		
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

	private void popolaMappa(GameMap mappa, int [] elem_gioco) {
		//si itera la mappa
		for(int i=0;i<mappa.mappa_gioco.length;i++) { //si scorrono le righe
			//si scorrono le colonne
			for(int j=0;j<mappa.mappa_gioco.length;j++) {
				//si considera la cella 
				CellStatus cs = scegliCella(mappa.mappa_gioco[i][j],elem_gioco);
				//si impsta il contenuto della cella
				mappa.mappa_gioco[i][j].setCellStatus(cs);
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

	private CellStatus scegliCella(Cell cell, int[] elem_gioco) {
		//metodo che specifica la tipologia di cella che sara' mappa[i][j]
		//variabili da utilizzare per il calcolo
		
		//si calcola un seme (un numero casuale che sara' la soglia con cui
		//confrontare la probabilita'
		double seed = (Math.random());//numero tra 0 e 1 escluso
		//HERO_SIDE
		if(isPit(seed,elem_gioco[0],elem_gioco[5],elem_gioco[6])) {
			//la cella e' un pozzo
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
		else 
			//allora e' una cella sicura
			return CellStatus.SAFE;
		//TODO manca il posizionamento dell'eroe
	}//scegliCella

	private boolean isStone(double seed, int i, int j) {
		//i parametri sono il valore di soglia con cui 
		//confrontare la probabilita' da calcolare
		//il numero di pietre che ancora possono essere messe
		//il numero massimo di pietre in generale
		//si calcola la probabilita'della cella
		double prob = lambda(i, i, j, j);
		return false;
	}

	private boolean isGold(double seed, int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isWumpus(double seed, int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isPit(double seed, int x_pit, int x_max_pit, int n, int n_max) {
		/* parametri:
		 *-seed, e' il valore di soglia con cui confrontare la probabilita'
		 * calcolata per la cella;
		 *-x_pit, e' il numero di pozzi che ancora devono essere messi nella mappa;
		 *-x_max_pit, e' il numero massimo di pozzi da mettere nella mappa;
		 *-n, e' il numeor di celle della mappa che ancora devono essere riempite;
		 *-n_max, il numero di caselle da cui e' composta la mappa di gioco.
		 */
		n_max=mappa
		//verifica se la cella considerata possa essere un pozzo
		//
		
		
		return false;
	}
	
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
	}

	
	

}//TestMappaGioco
