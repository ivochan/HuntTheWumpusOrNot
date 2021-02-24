package game.highscore;

import java.util.LinkedList;

import game.structure.cell.CellStatus;

/** class HighScore
 * questa classe si occupera' di gestire i punteggi del giocatore, memorizzando i cinque
 * piu' alti, permettendo alla conclusione della partita di inserire un nickname.
 * Inoltre, ad ogni azione nel gioco corrispondera' un certo valore di punteggio, da cumulare.
 * Ogni mossa compiuta, costera' una penalita' di un punto, percio', piu' sara' breve
 * la partita, piu' il punteggio sara' elevato.
 * @author ivonne
 *
 */
public class HighScore {
	//punteggi assegnati alle azioni da compiere nel gioco
	public static final int WIN = 100; //premio
	public static final int TRAP = -50; //pericolo
	public static final int DEAD = -100; //nemico
	public static final int STEP = -1; //mossa
	//lista dei dieci punteggi piu' alti
	private static Score [] top_ten = new Score[10];
	

	public void storeScore(Score s){
		//si deve conservare il punteggio corrente
		
		
	}
	

	public Score [] getTopTen(){
		return top_ten;
	}
	
	public void showHighScore(){
		//stampa del vettore dei punteggi piu' alti
	}
	
}//end class
