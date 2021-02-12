package game.test;

import java.util.HashMap;

import game.session.GameModeTranslation;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;
import game.structure.map.Starter;

/**
 * classe di test 
 * e' utilizzata per verificare che la costruzione del terreno di gioco
 * avvenga in maniera corretta.
 * @author ivonne
 */
public class TestGameStructure {

	public static void main(String [] args) {
		//si istanzia la mappa di gioco
		GameMap g  =new GameMap();
		//si riempie il vettore degli elementi di gioco
		Starter.elementsVectorFilling(g);
		//si popola la mappa
		Starter.placeMain(g);
		//si aggiorna il vettore dei sensori
		Starter.updateSensors(g);
		//si stampa il risultato
		System.out.println(g);
		//coordinate pg
		int [] pg_p= Starter.getPGPosition();
		System.out.println("Il PG e' stato posizionato nella cella ("+pg_p[0]+","+pg_p[1]+")\n");
		//test delle traduzioni hero_side
		//si inizializzano le classi delle traduzioni
		GameModeTranslation.initHeroTranslation();
		GameModeTranslation.initWumpusTranslation();
		//cella che contiene il pg
		CellStatus pg = g.getGameCell(pg_p[0], pg_p[1]).getCellStatus();
		System.out.println("Chiave "+pg);
		//si stampa il contenuto
		System.out.println("Nella modalita' eroe il PG e' "+GameModeTranslation.hero_side_map.get(pg));
		System.out.println(GameModeTranslation.hero_side_mex.get(pg));
		//si stampa il contenuto
		System.out.println("Nella modalita' wumpus il PG e' "+GameModeTranslation.wumpus_side_map.get(pg));
						
	}//end main
}//end class
