package game.controller;

import game.structure.map.GameMap;
/** GameController
 * @author ivonne
 * questa classe definisce i comandi di gioco, cioe' il modo in cui si potra'
 * interagire con il programma, facendo muovere il personaggio giocabile, nelle direzioni
 * consentite.
 */
public class GameController {

	//posizione del PG
	private static int ic;
	private static int jc;
	
	private boolean moveTo(Direction d) {
		//controllo sul parametro
		if(d==null)return false;
		//si esegue uno switch case per stabilire la direzione ricevuta come parametro
		//e se sia valida in base alla posizione del pg sulla mappa 
		switch(d) {
			case UP:
				break;
			case DOWN: 
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			default:
				break;
		}//switch
		return false;
	}
	
	private boolean moveIn(int i, int j) {
		//indici di cella in cui fare lo spostamento
		return false;
	}
		
	//TODO
	public static boolean isAllowedMove(GameMap gm, int i, int j) {
		//si controlla se il movimento, indicato dalla coppia di indici che rappresenta
		//la cella di arrivo, e' lecito
		boolean r_ok=false;
		boolean c_ok=false;
		boolean dims_ok=false;
		//ovvero se non si sfori la dimensione della matrice
		int r=gm.getRows();
		int c=gm.getColumns();
		if(i>=0 && i< r)r_ok=true;
		if(j>=0 && i< c)c_ok=true;
		dims_ok = r_ok && c_ok;
		//controllare che la cella in cui effettuare la mossa sia adiacente alla posizione corrente
		//del pg
		//controllo sull'adiacenza
		if(i==ic && j==jc-1) {
			//cella a sinistra
			if(jc-1>=0 && jc-1<4) {
				//la cella adiacente esiste
				//mossa valida
				return dims_ok && true;
			}
			System.out.println("movimento non consentito in "+i+","+j);
			return false;
		}
		//cella a destra
		//cella in alto
		//cella in basso
		
		
		return false;
		
	}
}//GameController
