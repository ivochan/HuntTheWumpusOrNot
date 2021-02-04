package game_structure;
/** classe di test
 * questa classe e' stata utilizzata per testare la validita' della struttura di 
 * ogni classe utilizzata per creare il campo di gioco
 * 
 * @author ivonne
 *
 */
public class TestGameStructure {

	public static void main (String [] args) {
		//attributo della modalita' di gioco
		boolean hero_side=true;
		
		/*//############		Test oggetto Cell e SenseVector		##############
		//test visualizzazione cella
		Cell c1 = new Cell();
		//stampa del contenuto della cella
		System.out.println("cella c1 "+c1);
		Cell c2 = new Cell(CellStatus.GOLD,hero_side,true,true);
		//stampa del contenuto della cella
		System.out.println("cella c2 "+c2);
		//stampa del vettore dei sensi
		System.out.println(c2.senseVectorToString(hero_side));
		//test attributo isVisited
		System.out.println("La cella e' stata visitata? "+ (hero_side?"si":"no"));
		//test enum
		System.out.println("intero associato all'enum "+CellStatus.PIT.ordinal());
		
		Cell c = new Cell();
		System.out.println("Cella "+c);
		System.out.println("Posizione "+c.getCellPosition());
		c.setCellPosition(0, -1);
		c.setCellStatus(CellStatus.HERO);
		System.out.println(c);
		c.setCellPosition(0, 0);
		System.out.println("Posizione "+c.getCellPosition());
		*/
		
		//###############		test riempimento MAPPA: MANUALE		####################
		//GameMap gm = new GameMap();
		//System.out.println(gm);
		GameMap g = new GameMap(hero_side);
		System.out.println(g);
		//GameMap ga = new GameMap(!hero_side);
		//System.out.println(ga);
		//stampa della dimensione della mappa
		//System.out.println("Length della mappa "+g.game_map.length);
		//riempimento mappa 
		/*
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				//inizializzazione delle celle di default
				g.game_map[i][j]=new Cell();
				//inizializzazione delle celle tutte di tipo SAFE
				//g.mappa_gioco[i][j]=new Cell(CellStatus.SAFE);
			}//for colonne
		}//for righe
		//stampa della mappa
		System.out.println("Mappa inizializzata di default:\n"+g);
		*/
		/*
		//si specificano altri valori per le celle
		g.game_map[0][0].setCellStatus(CellStatus.HERO);
		g.game_map[2][2].setCellStatus(CellStatus.DENIED);
		g.game_map[3][2].setCellStatus(CellStatus.DENIED);
		g.game_map[2][3].setCellStatus(CellStatus.DENIED);
		g.game_map[1][3].setCellStatus(CellStatus.GOLD);
		g.game_map[1][2].setCellStatus(CellStatus.PIT);
		g.game_map[2][0].setCellStatus(CellStatus.SAFE);
		g.game_map[3][0].setCellStatus(CellStatus.WUMPUS);
		
		//si cercano le celle non inizializzate per riempirle come SAFE
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				//inizializzazione delle celle di default
				CellStatus cs = g.game_map[i][j].getCellStatusEnum();
				if(cs==null) {
					//si imposta come cella SAFE = 
					g.game_map[i][j].setCell(0);
				}//fi
				//altrimenti si lascia il contenuto della cella così com'è
			}//for colonne
		}//for righe
		//N.B. non mettere l'oro, il wumpus o l'eroe circondato dalle celle non accessibili
		//mappa creata
		//||H| |S| |S| |S||
 		//||S| |S| |P| |S||
 		//||P| |S| |D| |D||
 		//||W| |S| |D| |G||
		System.out.println(g);
		//Cell c1 = g.game_map[0][1];
		//System.out.println(c1.getCellStatus().equals("SAFE"));
		*/
		/*
		//test sul posizionamento dell'eroe
		//System.out.println("Posizionamento dell'eroe ...");
		//g.putPG(); //questo metodo deve essere pubblico
		//System.out.println(g);
		*/
		/*		
		g.game_map[0][0].setCellStatus(CellStatus.DENIED);
		g.game_map[0][1].setCellStatus(CellStatus.DENIED);
		g.game_map[0][2].setCellStatus(CellStatus.DENIED);
		g.game_map[0][3].setCellStatus(CellStatus.DENIED);
		g.game_map[1][0].setCellStatus(CellStatus.DENIED);
		g.game_map[1][3].setCellStatus(CellStatus.DENIED);
		g.game_map[2][0].setCellStatus(CellStatus.DENIED);
		g.game_map[2][3].setCellStatus(CellStatus.DENIED);
		g.game_map[3][0].setCellStatus(CellStatus.DENIED);
		g.game_map[3][1].setCellStatus(CellStatus.DENIED);
		g.game_map[3][2].setCellStatus(CellStatus.DENIED);
		g.game_map[3][3].setCellStatus(CellStatus.DENIED);
		//posizionamento del pg
		System.out.println("Il PG e' stato messo? "+g.putPG());
		//test con nessuna casella disponibile
		System.out.println(g);
		boolean done = false;
		//ciclo
		while(!done) {
			//si cerca di posizionare il pg
			done = g.putPG();
			//finche' non si esce dal ciclo viene ripopolata la mappa alla ricerca
			//di una configurazione idonea al posizionamento del pg
			System.out.println("Posizionamento PG riuscito? "+done);
			//
			g.fillingGameMap();
			System.out.println(g);
		}//while
		System.out.println("posizionamento corretto!");
		System.out.println(g);
		*/
		
		/*
		//#####################		Test riempimento VETTORE elementi di GIOCO		#################
		//si costruisce un nuovo oggetto mappa
		GameMap g1 =new GameMap(hero_side);
		System.out.println(g1);
		//test riempimento vettore degli elementi di gioco : Automatico
		//questo riempimento avviene nel costruttore della classe GameMap
		//"true" per stampare la legenda
		System.out.println(g1.elementsVectorToString(true));		
		//test riempimento vettore degli elementi di gioco : Manuale
		g1.setGameElement(0,4);
		System.out.println(g1.elementsVectorToString(false));
		g1.setGameElement(8,1);
		System.out.println(g1.elementsVectorToString(false));	
		g1.setGameElement(4,7);
		System.out.println(g1.elementsVectorToString(false));
		g1.setGameElement(8,4);
		System.out.println(g1.elementsVectorToString(false));
		g1.setGameElement(3,2);
		System.out.println(g1.elementsVectorToString(false));
		*/
		
		
		/*//#################		###################à
		//ESEMPIO del vettore di gioco
		//| [16] [16] [13] [3] [3] [1] [1] [1] [2] [2] | 
		*/
		/*
		System.out.println("Modalita' Wumpus.");
		GameMap gm1 = new GameMap(!hero_side);
		System.out.println(gm1);
		*/
		/*
		System.out.println("Modalita' Eroe.");
		GameMap gm = new GameMap(hero_side);
		System.out.println(gm);
		*/
		/*
		 for(int i=0;i<4;i++) {
		 	for(int j=0;j<4;j++) {
				System.out.println(gm.game_map[i][j]+" "+gm.game_map[i][j].getCellPosition());
			}
		}
		*/
	
	}//main
	
}//TestGameStructure
