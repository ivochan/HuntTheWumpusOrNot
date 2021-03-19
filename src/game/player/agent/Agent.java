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

	//metodo che valuta la mossa da fare
	abstract void chooseMove(GameMap em, GameMap gm);
	
	//metodo che sceglie la direzione in cui effettuare la mossa
	abstract Direction chooseDirection(int i, int j, GameMap em);
	
	//metodo che tiene traccia del punteggio accumulato: utilizzare quello di Score
	//void updateScore();
	
	//##### metodi per il percorso #####
	
	public LinkedList<Cell> getRunPath();

	public String runPathToString();
	
	public void updateRunPath(Cell c);
	
	public void showRunPath();
		
}//end Agent
