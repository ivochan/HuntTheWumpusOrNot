package game.session;

import java.util.HashMap;
import java.util.Scanner;

import game.controller.Controller;
import game.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;
import game.structure.map.Starter;

public class LinkStart {

	public static void main(String [] args) {
		//############ inizializzazioni ###############
		
		//stringa descrizione comandi
		String legenda_comandi = "Ecco la lista dei comandi:\n"+
						"[q -quit] [g - game start] [s - score] [c - credits]";
		//stringa modalita' di gioco
		String mode = "Sarai il cacciatore oppure il Wumpus?\n"
						+"[h - cacciatore] [w -wumpus]";
		//inizializzazione acquisizione da riga di comando
		Scanner input = new Scanner(System.in);
		//carattere che rappresenta il comando
		char comando = ' ';
		//carattere che rappresenta la modalita' di gioco
		char game_mode = ' ';
		boolean game_mode_choosen=false;
		boolean hero_side=false;
		//inizializzazione delle traduzioni
		GameModeTranslation.initHeroTranslation();
		GameModeTranslation.initWumpusTranslation();
		//inizializzazione delle due mappe (da pulire al termine della sessione di gioco
		System.out.println("Link... Start-o!");
		//si crea la mappa di gioco
		GameMap gm = new GameMap();
		//si crea la mappa di esplorazione
		GameMap ge = new GameMap();
		//intro al gioco
		System.out.println(legenda_comandi);
		System.out.println("Cosa vuoi fare?");
		//ciclo di avvio della sessione di gioco
		while(comando != 'q') {
			//si controlla se e' stato ricevuto il comando di chiusura
			comando = input.next().charAt(0);
			//comando info sul gioco
			if(comando == 'c') {
				System.out.println(GameModeTranslation.credits);
				System.out.println("Cosa vuoi fare?");
			}
			//comando punteggi
			else if(comando == 's') {
				System.out.println("Da sviluppare: lista dei 5 punteggi piu' alti");
				System.out.println("Cosa vuoi fare?");
			}
			//comando per iniziare una partita
			else if(comando == 'g') {
				System.out.println("Ciao :3\nDimmi di te..."+mode);
				while(!game_mode_choosen) {
					game_mode = input.next().charAt(0);
					if(game_mode=='h') {
						hero_side=true;
						game_mode_choosen=true;
					}
					else if(game_mode == 'w') {
						hero_side=false;
						game_mode_choosen=true;
					}
					else {
						System.out.println("Dai, scegli!");
					}
				}//end while scelta modalita'
				System.out.println("Preparazione del terreno di gioco....");
				//si pulisce la mappa di esplorazione
				ge.clear();
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
				int [] pg_pos= Starter.getPGstartPosition();
				System.out.println("Il PG e' stato posizionato nella cella ("+pg_pos[0]+","+pg_pos[1]+")\n");
				System.out.println((trad_mex.get(CellStatus.PG)));
				ge.getGameCell(pg_pos[0], pg_pos[1]).copyCellSpecs(gm.getGameCell(pg_pos[0],pg_pos[1]));
				System.out.println("\nEccoti la mappa di esplorazione...");
				System.out.println("\n"+ge.mapAndLegend());
				//flag sessione di gioco
				boolean game_start = true;
				//flag comando valido
				boolean valid_move = false;
				//mossa da acquisire da input
				char move = ' ';
				//mossa codificata in intero
				Direction pg_move = null;
				//sessione di gioco: movimento del pg
				while(game_start){
					//si acquisisce il comando
					while(!valid_move) {
						System.out.println("Inserisci comando :> ");
						move = input.next().charAt(0);
						//controllo sul comando
						if(move=='w') {
							pg_move=Direction.UP;
							valid_move=true;
						}
						if(move=='a') {
							pg_move=Direction.LEFT;
							valid_move=true;
						}
						 if(move=='s') {
							 pg_move=Direction.DOWN;
							valid_move=true;
						}
						if(move=='d') {
							pg_move= Direction.RIGHT;
							valid_move=true;
						}
						else {
							System.out.println("Mossa errata!");
						}
					}//while mossa
					System.out.println("Comado inserito: "+move);
					//si controlla il comando
					int status=Controller.movePG(pg_move, pg_pos, gm); 
					switch(status) {
						case -1 : 
							System.out.println("Comando non valido!\nRipeti la mossa...");
							valid_move=false;
							break;
						case 0 : 
							//TODO
							System.out.println("Il pg si sta muovendo...");
							pg_pos = Controller.getPGpos();
							System.out.println("Il pg si trova in ("+pg_pos[0]+','+pg_pos[1]+')');
							//si preleva la cella in cui si sposta il pg
							Cell c = gm.getGameCell(pg_pos[0], pg_pos[1]);
							//si segna la cella in gm come visitata
							c.setVisited();
							//si copia questa cella nella matrice di esplorazione
							ge.getGameCell(pg_pos[0], pg_pos[1]).copyCellSpecs(c);
							//TODO
							//aggiornare la posizione del pg
							System.out.println("Il pg si trova in ("+pg_pos[0]+','+pg_pos[1]+')');
							System.out.println(ge.mapAndLegend());
							System.out.println("Ecco cosa vede-");
							//stampa del vettore dei sensori
							//aggiornamento della posizione del pg
							//richiesta della mossa successiva
							valid_move=false;
							game_start=true;
							break;
						case 1:
							System.out.println("Sei morto");
							//richiesta di iniziare una nuova partita
							valid_move=true;
							game_start=false;
							break;
						default: break;
					}//switch
					
				}//while sessione di gioco
				System.out.println("THE END!");
				System.out.println(legenda_comandi);
				System.out.println("Che si fa? :> ");				
			}//fi 'g'
			else if(comando == 'q') {
				System.out.println("Ciao ciao!");
			}//fi 'q'
			else {
				System.out.println("Comando errato!\n"+legenda_comandi);
			}//esle
		}//end while
		System.out.println("Chiusura del gioco...");
	}//end main

}//end class


