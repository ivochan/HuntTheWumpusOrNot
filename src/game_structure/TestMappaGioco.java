package game_structure;

public class TestMappaGioco {

	public static void main (String [] args) {
		
		//variabile booleana per la modalita'
		boolean hero_side=true;
		//creazione di una nuova mappa
		GameMap mappa = new GameMap(hero_side);
		//vettore degli elementi di gioco
		int [] elem_gioco= new int [7];
		//riempimento del vettore
		//riempiVettore(mappa,elem_gioco);
		//stampa su console
		//stampaVettore(elem_gioco);
		
		//ESEMPIO del vettore di gioco
		//| [13]  [3]  [1]  [1]  [1]  [2]  [2] 
		elem_gioco[0]=13; elem_gioco[1]=3; elem_gioco[2]=1; elem_gioco[3]=1;
		elem_gioco[4]=1; elem_gioco[5]=2; elem_gioco[6]=1;
		stampaVettore(elem_gioco);
		//popolamento della mappa
		//mappa.popolaMappa();
		//stampa a video della mappa di gioco
		//System.out.println(mappa);
		
	}//main
	
	private static void stampaVettore(int[] elem_gioco) {
		//si stampa una legenda
		System.out.println("[n_celle] [sassi] [oro] [eroe]"
				+ " [wumpus] [pozzi] [n_max pozzi]");
		//si stampa il contenitore a sinistra
		System.out.print("|");
		//si scorrono le celle del vettore
		for(int i=0;i<elem_gioco.length;i++) {
			//si stampa la cella i-esima
			System.out.print(" ["+elem_gioco[i]+"] ");
		}
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
		//calcolo delle celle giocabili in questa partita
		///si sceglie casualmente il numero di celle giocabili (da 12 a 16) da riempire
		int max_n=((int)(Math.random()*5))+12;
		// numero di celle da riempire rimaste
		int n=max_n;
		elem_gioco[0]=n;
		// si ricava il numero di celle non accessibili PIETRA
		int d = 16 - max_n;
		//celle non giocabili DENIED
		elem_gioco[1]=d;
		//oro 
		int g=1;
		elem_gioco[2]=g;
		//avventuriero 
		int h=1;
		elem_gioco[3]=h;
		//mostro 
		int w=1;
		elem_gioco[4]=w;
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
			elem_gioco[5]=p;
			elem_gioco[6]=max_p;
		}
		else {
			//wumpus_side
			elem_gioco[5]=t;
			elem_gioco[6]=max_t;
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
		// TODO Auto-generated method stub
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

	private boolean isPit(double seed, int i, int j, int k) {
		//verifica se la cella considerata possa essere un pozzo
		
		return false;
	}
	
	//funzione di probabilita' utilizzata per stabilire il valore che deve assumere la cella di interesse
	private static double lambda(int x,int max_x, int n, int max_n) {
		/* - x, oggetto cella da inserire;
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
