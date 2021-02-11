package game.structure;

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
	}//end main
}//end class
