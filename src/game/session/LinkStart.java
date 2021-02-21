package game.session;

import java.util.Scanner;

import game.controller.Controller;
import game.structure.cell.CellStatus;
import game.structure.map.GameConfiguration;
import game.structure.map.GameMap;

public class LinkStart {
	//acquisizione input
	private static Scanner input = new Scanner(System.in);
	//acquisizione comando
	private static char comando = ' ';
	//posizione attuale del pg
	private static int [] pg_pos = new int [2];
	
	public static void main(String [] args) {
		//inizializzazione dei dati di gioco
		Starter.initGameData();
		//si crea la mappa di gioco
		GameMap gm = new GameMap();
		//si crea la mappa di esplorazione
		GameMap ge = new GameMap();
		//avvio sessione di gioco
		while(comando != 'q') {
			System.out.println(GameModeTranslation.command_request);
			//acquisizione del comando
			comando = input.next().charAt(0);
			//comando info sul gioco
			if(comando == 'c') {
				System.out.println(GameModeTranslation.credits);
			}
			//comando punteggi
			else if(comando == 's') {
				System.out.println("TODO lista dei 5 punteggi piu' alti");
			}
			//comando per iniziare una partita
			else if(comando == 'g') {
				//flag avvio partita
				Starter.setGameStart(true);
				System.out.println("Preparazione del terreno di gioco....\n");
				//scelta della modalita'
				Starter.chooseGameMode();
				//elementi della mappa
				GameConfiguration.elementsVectorFilling(gm);
				//creazione della mappa
				GameConfiguration.placeMain(gm);
				//DEBUG
				System.out.println(gm.mapAndLegend());
				//si aggiorna il vettore dei sensori
				GameConfiguration.updateSensors(gm);
				//coordinate pg
				pg_pos= GameConfiguration.getPGstartPosition();
				System.out.println((Starter.trad_mex.get(CellStatus.PG)));
				//informazioni sull'ambiente all'inizio del gioco
				Starter.verifyPGpos(gm,pg_pos);
				//posizione del pg
				ge.getGameCell(pg_pos[0], pg_pos[1]).copyCellSpecs(gm.getGameCell(pg_pos[0],pg_pos[1]));
				System.out.println("\n"+ge.mapToString());
				//sessione di gioco: movimento del pg
				while(Starter.getGameStart()){
					//acquisizione della mossa
					Starter.chooseMove();
					//se non si e' interrotta la partita si valuta la mossa
					if(Starter.getGameStart()) {
						//verifica della mossa
						int status=Controller.movePG(Starter.getPGmove(), pg_pos, gm, ge); 
						//realizzazione della mossa
						switch(status) {
							case -1 : 
								System.out.println("Comando non valido!\nRipeti la mossa...");
								break;
							case 0 : 
								//si preleva la posizione del pg
								pg_pos = Controller.getPGpos();
								//debug
								System.out.println(ge.mapToString());
								//informazioni sulla posizione
								Starter.verifyPGpos(gm,pg_pos);
								break;
							case 1:
								//si prendono le informazioni della cella in cui si e' mosso il pg
								CellStatus cs = gm.getGameCell(pg_pos[0], pg_pos[1]).getCellStatus();
								//se nemico o pericolo, verra' stampato il messaggio appropriato
								System.out.println(Starter.trad_mex.get(cs)+"\n"+GameModeTranslation.looser);
								Starter.setGameStart(false);
								break;
							case 2:
								System.out.println("Wow!"+Starter.trad_mex.get(CellStatus.AWARD)
								+"\n"+GameModeTranslation.winner);
								//richiesta di iniziare una nuova partita
								Starter.setGameStart(false);
								break;
							default: break;
						}//switch
					}//fi game_start
				}//end while
				//fine partita
				System.out.println("THE E.N.D.");
				Starter.resetGameData(ge);
			}
			else if(comando == 'q') {
				System.out.println("Chiusura del gioco...");
			}
			//comando errato
			else {
				System.out.println("Comando errato!\n"+GameModeTranslation.command_legend);
			}//esle
					
		}//end while schermata di avvio
		System.out.println("Ciao, alla prossima!");
	
	}//end main
	
}//end class


