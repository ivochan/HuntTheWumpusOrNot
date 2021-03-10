package game.player.agent;

import java.util.LinkedList;

import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.map.GameMap;

/** interface Agent
 * 
 * @author ivonne
 *
 */
public interface Agent {
	//##### attribuiti #####
	
	//lista contenente la coppia di indici di ogni cella visitata
	LinkedList<Cell> run = new LinkedList<Cell>();

	//##### metodi #####
	
	//metodo che legge la mappa di gioco allo stato attuale: mappa di esplorazione
	//void seeEnvironment(GameMap em);
	
	//metodo che esamina il contenuto dei sensori
	//void checkEnvironment(GameMap em);
	
	//metodo che valuta la mossa da fare
	Direction chooseMove(GameMap em);
	
	//metodo che effettua la moosa: utilizzare quello di Controller
	//void makeMove(Direction dir);
	
	//metodo che tenta il colpo al nemico
	//void hitEnemy(Direction dir, GameMap gm, GameMap em);

	//metodo che tiene traccia del punteggio accumulato: utilizzare quello di Score
	//void updateScore();
	
	//##### metodi per il percorso #####
	
	public LinkedList<Cell> getRunPath();

	public String runPathToString();
	
	public void updateRunPath(Cell c);
	
	public void showRunPath();
		
}//end Agent
