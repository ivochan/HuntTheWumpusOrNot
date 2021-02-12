package game.test;


import java.util.HashMap;

import game.session.GameModeTranslation;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;
import game.structure.map.Starter;

public class LinkStartBaseTest {
//si avvia il gioco
//TODO finche' non si esce, la partita puo' essere iniziata di nuovo
	public static void main(String [] args) {
		//############ inizializzazioni ###############
		//si crea la mappa di gioco
		GameMap gm = new GameMap();
		//si crea la mappa di esplorazione
		GameMap ge = new GameMap();
		//si sceglie la modalita' di gioco
		boolean hero_side = true;
		//flag per la legenda
		boolean info = false;
		//si inizializzano le classi delle traduzioni
		GameModeTranslation.initHeroTranslation();
		GameModeTranslation.initWumpusTranslation();
		//si prelevano le traduzioni necessarie: elementi di gioco
		HashMap<CellStatus, String> trad_el = (hero_side?(GameModeTranslation.hero_side_map)
				:(GameModeTranslation.wumpus_side_map));
		//si prelevano le traduzioni necessarie: messaggi
		HashMap<CellStatus, String> trad_mex = (hero_side?(GameModeTranslation.hero_side_mex)
				:(GameModeTranslation.wumpus_side_mex));
		//############## assegnamenti ################à
		Starter.elementsVectorFilling(gm);
		//si cerca di creare la mappa
		Starter.placeMain(gm);
		//si stampa il risultato
		System.out.println(gm);
		//si aggiorna il vettore dei sensori
		Starter.updateSensors(gm);
		//coordinate pg
		int [] pg_p= Starter.getPGPosition();
		System.out.println("Il PG e' stato posizionato nella cella ("+pg_p[0]+","+pg_p[1]+")\n");
		System.out.println((trad_mex.get(CellStatus.PG)));
		ge.getGameCell(pg_p[0], pg_p[1]).copyCellSpecs(gm.getGameCell(pg_p[0],pg_p[1]));
		System.out.println("\nEcco la mappa di esplorazione...");
		System.out.println(ge);
		
		
		
		
		
		
		
	}//main
	
	
	
	/** metodo setGameMode(boolean) : void
	 * metodo che permette di specificare la modalita' di gioco
	 * @param hero_side: boolean, se true indica la modalita' Eroe, 
	 * 							  se false, indica la modalita' Wumpus.
	 */
	/*
	public void setGameMode(boolean hero_side) {
		//si specifica la modalita' di gioco
		this.hero_side=hero_side;
	}//setGameMode
/*
	/** metodo getGameMode() : boolean
	 * metodo che restituisce l'attributo relativo alla modalita' di gioco secondo cui
	 * e' stata strutturata la mappa
	 * @return hero_side: boolean, se true indica la modalita' Eroe (hero_side),
	 * 							   se false indica la modalita' Wumpus (!hero_side).
	 */
	/*
	public boolean getGameMode() {
		//restituzione del parametro di interesse
		return this.hero_side;
	}//getGameMode
*/
}


