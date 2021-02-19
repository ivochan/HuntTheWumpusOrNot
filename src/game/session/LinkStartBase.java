package game.session;

import java.util.HashMap;
import java.util.Scanner;

import game.controller.Controller;
import game.controller.Direction;
import game.structure.cell.CellStatus;
import game.structure.map.GameMap;
import game.structure.map.Starter;

public class LinkStartBase {

	
	
	
	public static void main(String [] args) {
		//############ inizializzazioni ###############
		//vettore dei sensori
		boolean [] sense = new boolean[2];
		//inizializzazione acquisizione da riga di comando
		Scanner input = new Scanner(System.in);
		//carattere che rappresenta il comando
		char comando = ' ';
		//carattere che rappresenta la modalita' di gioco
		char game_mode = ' ';
		boolean game_mode_choosen=false;
		boolean hero_side=false;
		//flag sessione di gioco
		boolean game_start = false;
		//flag comando valido
		boolean valid_move = false;
		//mossa da acquisire da input
		char move = ' ';
		//mossa codificata in intero
		Direction pg_move = null;
		//inizializzazione delle traduzioni
		GameModeTranslation.initHeroTranslation();
		GameModeTranslation.initWumpusTranslation();
		//inizializzazione delle due mappe (da pulire al termine della sessione di gioco
		System.out.println("Link... Start-o!");
		//intro al gioco
		System.out.println(GameModeTranslation.legenda_comandi);
		System.out.println("Cosa vuoi fare?");
		//si crea la mappa di gioco
		GameMap gm = new GameMap();
		//si crea la mappa di esplorazione
		GameMap ge = new GameMap();
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
				//flag per avviare la sessione di gioco
				game_start= true;
				//avvio del gioco
				System.out.println("Ciao :3\nDimmi di te..."+GameModeTranslation.mode);
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
				System.out.println("Preparazione del terreno di gioco....\n");
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
				//si stampa il risultato per debug
				System.out.println(gm.mapAndLegend());
				//si aggiorna il vettore dei sensori
				Starter.updateSensors(gm);
				//coordinate pg
				int [] pg_pos= Starter.getPGstartPosition();
				System.out.println((trad_mex.get(CellStatus.PG)));
				//System.out.println("Inizia la tua avventura dalla cella ("+pg_pos[0]+","+pg_pos[1]+")\n");
				//informazioni sull'ambiente all'inizio del gioco
				sense = gm.getGameCell(pg_pos[0], pg_pos[1]).getSenseVector();
				//System.out.println(gm.getGameCell(pg_pos[0], pg_pos[1]).senseVectorToString(true));
				//informazioni sulla posizione del nemico
				if(sense[0])System.out.println(trad_mex.get(CellStatus.ENEMY_SENSE));
				//informazioni sulla posizione del pericolo
				if(sense[1])System.out.println(trad_mex.get(CellStatus.DANGER_SENSE));
				//se non ci sono informazioni
				if(!sense[0] && !sense[1])System.out.println("Tutto a posto all'orizzonte!");
				//posizione del pg
				ge.getGameCell(pg_pos[0], pg_pos[1]).copyCellSpecs(gm.getGameCell(pg_pos[0],pg_pos[1]));
				System.out.println("\n"+ge.mapToString());
				//scelta della modalita' da ripetere al prossimo avvio
				game_mode_choosen=false;
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
						else if(move=='a') {
							pg_move=Direction.LEFT;
							valid_move=true;
						}
						else if(move=='s') {
							 pg_move=Direction.DOWN;
							valid_move=true;
						}
						else if(move=='d') {
							pg_move= Direction.RIGHT;
							valid_move=true;
						}
						else if(move=='c') {
							valid_move=true;
							game_start=false;
							System.out.println("Interruzione della partita ...");
						}
						else {
							System.out.println("Mossa errata!");
							valid_move=false;
						}
					}//while mossa
					if(move!='c') {//se non e'stato ricevuto il comando di interruzione
						//si controlla il comando
						int status=Controller.movePG(pg_move, pg_pos, gm, ge); 
						switch(status) {
							case -1 : 
								System.out.println("Comando non valido!\nRipeti la mossa...");
								valid_move=false;
								break;
							case 0 : 
								//si acquisisce la posizione del pg
								//verifyPGpos(ge );
								pg_pos = Controller.getPGpos();
								//aggiornare la posizione del pg
								System.out.println(trad_el.get(CellStatus.PG)+" posizionato in ("+pg_pos[0]+','+pg_pos[1]+')');
								System.out.println(ge.mapToString());
								//stampa del vettore dei sensori
								//System.out.println("Ecco cosa vedi attorno a te:\n"+gm.getGameCell(pg_pos[0], pg_pos[1]).senseVectorToString(true));
								//comunicazione all'utente del valore dei sensori
								sense= gm.getGameCell(pg_pos[0], pg_pos[1]).getSenseVector();
								//vicinanza del nemico
								if(sense[0])System.out.println(trad_mex.get(CellStatus.ENEMY_SENSE));
								//vicinanza del pericolo
								if(sense[1])System.out.println(trad_mex.get(CellStatus.DANGER_SENSE));
								//richiesta della mossa successiva
								valid_move=false;
								game_start=true;
								break;
							case 1:
								//si prendono le informazioni della cella in cui si e' mosso il pg
								CellStatus cs = gm.getGameCell(pg_pos[0], pg_pos[1]).getCellStatus();
								//se nemico o pericolo, verra' stampato il messaggio appropriato
								System.out.println(trad_mex.get(cs)+"\n"+GameModeTranslation.looser);
								//richiesta di iniziare una nuova partita
								valid_move=true;
								game_start=false;
								break;
							case 2:
								System.out.println("Wow!"+trad_mex.get(CellStatus.AWARD)
												+"\n"+GameModeTranslation.winner);
								//richiesta di iniziare una nuova partita
								valid_move=true;
								game_start=false;
								break;
							default: break;
						}//switch
					}//fi 
				}//while sessione di gioco
				System.out.println("THE E.N.D.");
				System.out.println(GameModeTranslation.legenda_comandi);
				System.out.println("Che si fa? :> ");
				ge.clear();
				clearConsole();
				valid_move=false;
				
			}//fi 'g'
			else if(comando == 'q') {
				System.out.println("Ciao ciao!");
			}//fi 'q'
			else {
				System.out.println("Comando errato!\n"+GameModeTranslation.legenda_comandi);
			}//esle
		}//end while schermata di avvio
		System.out.println("Chiusura del gioco...");
	}//end main


	/** metodo clearConsole(): void
	 * utilizzato per pulire la console dopo ogni stampa 
	 * della matrice di gioco
	 */
	public static void clearConsole() {
		//linux
		System.out.print("\033[H\033[2J");
		System.out.flush();
	
	}//clearConsole()
	

	
	
}//end class


