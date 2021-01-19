package game_structure;

public class TestPopolamento {

	public static void main(String [] args) {
		//variabile booleana per la modalita'
		boolean hero_side=true;
		//creazione di una nuova mappa
		GameMap mappa = new GameMap(hero_side);
		//calcolo delle celle giocabili in questa partita
		//si sceglie casualmente il numero di celle giocabili (da 9 a 16)
		int max_n=((int)(Math.random()*8))+9; // numero massimo delle celle da riempire
		// si ricava il numero di celle non accessibili
		int d = 16 - max_n;
		//elementi di gioco, salvati in un vettore
		int [] elementi_di_gioco=  new int[7];
		// numero di celle da riempire rimaste
		int n=max_n;
		//nel vettore
		elementi_di_gioco[0]=n;
		//oggetti da sistemare nelle celle
		//celle non giocabili DENIED
		elementi_di_gioco[1]=d;
		//oro 
		int g=1;
		//nel vettore GOLD
		elementi_di_gioco[2]=g;
		//avventuriero 
		int h=1;
		elementi_di_gioco[3]=h;
		//mostro 
		int w=1;
		//nel vettore WUMPUS
		elementi_di_gioco[4]=w;
		//pozzi da mettere nella mappa (hero_side = true)
		int p = 2; 
		 //numero massimo di pozzi 
		int max_p = 2;
		//trappole da mettere al posto dei pozzi (hero_side = false)
		int t = p; 
		//numero massimo di trappole
		int max_t = max_p;
		//nel vettore PIT
		if(hero_side) {
			elementi_di_gioco[5]=p;
			elementi_di_gioco[6]=max_p;
		}
		//nel vettore TRAP
		else {
			elementi_di_gioco[5]=t;
			elementi_di_gioco[6]=max_t;
		}
		//vettore che contiene questi oggetti in ordine
		stampaVettore(elementi_di_gioco);
		//ESEMPIO del vettore di gioco
		//| [13]  [3]  [1]  [1]  [1]  [2]  [2] |
		int [] elem_gioco= new int [7];
		//rimepimento
		elem_gioco[0]=13; elem_gioco[1]=3; elem_gioco[2]=1; elem_gioco[3]=1;
		elem_gioco[4]=1; elem_gioco[5]=2; elem_gioco[6]=1;
		
		popola(mappa,elem_gioco);
		
		
		
	}//main

	private static void stampaVettore(int[] elem_gioco) {
		//si stampa una legenda
		System.out.println("[n_celle safe] [n_celle denied] [oro] [eroe]"
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

	private static void popola(GameMap mappa, int [] elementi_di_gioco) {
		//si itera la mappa per esaminare ogni cella e specificarne il contenuto
		for(int i=0;i<mappa.mappa_gioco.length;i++) {
			//scorrimento righe
			for(int j=0;j<mappa.mappa_gioco.length;j++){
				//scorrimento colonne
				CellStatus cs = scegliContenuto(elementi_di_gioco);
				//si imposta il contenuto della cella
				mappa.mappa_gioco[i][j].setCellStatus(cs);
			}//colonne
		}//righe
		
	}//popola

	private static CellStatus scegliContenuto(int [] elem_gioco) {
		//si utilizza una funzione di probabilita'
		//ipotesi: hero_side
		//celle rimaste da riempire
		//numero totale delle celle da riempire SAFE + DENIED
		int tot_celle = elem_gioco[0]+elem_gioco[1];
		//massimo numero di celle safe
		int max_n = elem_gioco[0];
		//celle safe rimaste da assegnare
		int n=max_n;
		//massimo numero di celle denied
		int max_d = elem_gioco[1];
		//celle denied da assegnare
		int d=max_d;
		//parametri del pozzo
		int x = elem_gioco[5];
		int max_x = elem_gioco[6];
		//calcolo della probabilita'
		
		
		return null;
	}
	
	
	//funzione di probabilita' utilizzata per stabilire il valore che deve assumere la cella di interesse
	private int lambda(int x,int max_x, int n, int max_n) {
		/* - x, oggetto cella da inserire;
		* - max_x, numero massimo di oggetti di tipo x che possono essere inseriti nella mappa;
		* - n, numero di celle della mappa che devono essere ancora riempite;
		* - max_n, numero massimo di celle della mappa che possono essere riempite (giocabili).
		*/
		int prob = ((x/max_x)) - (1-n/max_n);
		//probabilita'
		return prob;
	}

	
}
