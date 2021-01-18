package game_structure;

public class TestGameStructure {

	public static void main(String [] args) {
		
		//test visualizzazione cella
		Cell c1 = new Cell();

		boolean hero_side=true;
		
		Cell c2 = new Cell(CellStatus.GOLD,hero_side,true,true);
		//System.out.println(c2);
		System.out.println(c2.SenseVectorToString(hero_side));
		//System.out.println("La cella e' stata visitata? "+ (hero_side?"si":"no"));
		
		GameMap g1 = new GameMap();
		//System.out.println(g1);
		
		GameMap g = new GameMap(hero_side);
		//riempimento mappa
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				g.mappa_gioco[i][j]=new Cell(CellStatus.SAFE,hero_side,true,true);
			}
		}
		
		g.mappa_gioco[1][2].setCellStatus(CellStatus.GOLD);
		g.mappa_gioco[3][3].setCellStatus(CellStatus.WUMPUS);
		g.mappa_gioco[0][0].setCellStatus(CellStatus.HERO);
		g.mappa_gioco[2][2].setCellStatus(CellStatus.PIT);
		
		g.mappa_gioco[0][3].setCellStatus(CellStatus.GOLD);
		g.mappa_gioco[0][3].setCell(5);
		
		System.out.println(g);
		//test numero casuale delle caselle giocabili
		//double n=(int)(Math.random()*8);//numero da 0 a 8 escluso
		//System.out.println(n);
		//System.out.println(n+9);
		//System.out.println(((int)(Math.random()*8))+9);
		
	
			//si sceglie casualmente il numero di celle giocabili (da 9 a 16)
			int max_n=((int)(Math.random()*8))+9; // numero massimo delle celle da riempire
			System.out.println("MAX_N "+max_n);
			// numero di celle da riempire rimaste
			int n=max_n;
			// si ricava il numero di celle non accessibili
			int d = 16-max_n;
			//oggetti da sistemare nelle celle
			int p = 4; //pozzi da mettere nella mappa (hero_side = true)
			int max_p = 4; //numero massimo di pozzi 
			int t = p; //trappole da mettere al posto dei pozzi (hero_side = false)
			int max_t = max_p; //numero massimo di trappole
			int go = 1;//oro
			int h=1;//avventuriero
			int w=1;//mostro
			//calcolo della probabilita'
	
		
		//funzione di probabilita' utilizzata per stabilire il valore che deve assumere la cella di interesse
		
			/* - x, oggetto cella da inserire;
			 * - max_x, numero massimo di oggetti di tipo x che possono essere inseriti nella mappa;
			 * - n, numero di celle della mappa che devono essere ancora riempite;
			 * - max_n, numero massimo di celle della mappa che possono essere riempite (giocabili).
			 */
			int prob = ((p/max_p)) - (1-n/max_n);
			//probabilita'
			System.out.println(prob);
			p--;
			n--;
			prob = ((p/max_p)) - (1-n/max_n);
			System.out.println(prob);
			n--;
			prob = ((p/max_p)) - (1-n/max_n);
			System.out.println(prob);
	}//main
}//Test
