package game.test;

import game.structure.map.GameConfiguration;
import game.structure.map.GameMap;


/**
 * classe di test 
 * e' utilizzata per validare il meccanismo di riempimento della mappa
 * di gioco, ai fini di prepararla per la sessione di gioco vera e propria
 * implementato nella classe Starter
 * @author ivonne
 */
public class TestGameConfiguration {

	public static void main (String [] args) {
		//flag per la legenda
		boolean info=true;
		//si istanzia la mappa di gioco
		GameMap g  =new GameMap();
		//si visualizza a schermo
		System.out.println(g.mapToString());
		//si riempie il vettore degli elementi di gioco
		GameConfiguration.elementsVectorFilling(g);
		System.out.println("Vettore degli elementi di gioco\n"
							+GameConfiguration.getGameElementsToString(info));
		//si cerca di creare la mappa
		GameConfiguration.placeMain(g);
		//si stampa il risultato
		System.out.println(g);
		//coordinate pg
		int [] pg_p= GameConfiguration.getPGstartPosition();
		System.out.println("Il PG e' stato posizionato nella cella ("+pg_p[0]+","+pg_p[1]+")\n");
		System.out.println("Svuotamento della mappa...");
		g.clear();
		System.out.println(g);
		//si cerca di creare la mappa
		GameConfiguration.placeMain(g);
		//si stampa il risultato
		System.out.println(g);
		//si aggiorna il vettore dei sensori
		GameConfiguration.updateSensors(g);
		//per il debug
		GameConfiguration.printSensors(g, info);
	}//end main
}//end class
