package game.session.iaplayer;

import java.util.LinkedList;

import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.map.GameMap;

public abstract class BasicAgent implements Agent {

	//##### attribuiti di classe #####
	
	//lista contenente la coppia di indici di ogni cella visitata
	protected LinkedList<Cell> run = new LinkedList<Cell>();
		
	//modalita' di gioco
	//TODO da scegliere prima della risoluzione
		
	//##### metodi #####
		
	//metodo che legge la mappa di gioco allo stato attuale: mappa di esplorazione
	/** metodo seeEnvironment(GameMap): void
	 * si visualizza la mappa di gioco nella situazione attuale,
	 * cioe' la mappa di esplorazione, che mostra le caselle
	 * conosciute dal giocatore, perche' sono state gia' visitate
	 * @param em: GameMap, mappa di gioco.
	 */
	public void seeEnvironment(GameMap em) {
		//si esamina la mappa di esplorazione
		em.gameMaptoString();
		//
	}//seeEnvironment(GameMap)
	
	public //metodo che esamina il contenuto dei sensori
	void checkEnvironment(GameMap em) {
		
	}
		
		//metodo che valuta la mossa da fare
		public abstract Direction chooseMove(GameMap em);
		
		//metodo che effettua la moosa: utilizzare quello di Controller
		public abstract void makeMove(Direction dir);
		
		//metodo che tenta il colpo al nemico
		public abstract void hitEnemy(Direction dir, GameMap gm, GameMap em);
		
		//metodo che tiene inserisce la cella visitata nel percorso
		public abstract void updateRunPath(Cell c);
		
		//metodo che restituisce il percorso compiuto
		public abstract void seeRunPath();
		
		//metodo che tiene traccia del punteggio accumulato: utilizzare quello di Score
		public abstract void updateScore();
}
