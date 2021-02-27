package game.highscore;

import java.util.Date;

import game.structure.cell.CellStatus;


/** class Score
 * questa classe rappresenta l'oggetto Highscore, costituito da due attributi
 * di classe significativi:
 * -il nickname del giocatore;
 * -il punteggio raggiunto;
 * @author ivonne
 *
 */
public class Highscore {
	//punteggi assegnati alle azioni da compiere nel gioco
	public static final int WIN = 100; //premio
	public static final int TRAP = -50; //pericolo
	public static final int DEAD = -100; //nemico
	public static final int STEP = -1; //mossa
	public static final int HIT = 50; //colpo andato a segno
	//valore del punteggio
	private int score=0;
	//nickname del giocatore
	private String nickname="Giocatore";
	//data e ora del punteggio
	private String score_date="";
	//numero di mosse compiute
	private int move_count=0;
	
	/** Score()
	 * costruttore di default
	 * senza paramentri
	 */
	public Highscore(){
		//gli attributi di classe avranno i valori di default
		this.score=0;
		this.score_date=new Date().toString();
		//nickname resta con il valore di default
	}//score()
	
	/** Score(int)
	 * costruttore con parametro
	 * @param score: int, punteggio raggiunto dal giocatore
	 */
	public Highscore(int score){
		this.score=score;
		//nickname resta con il valore di default
		this.score_date=new Date().toString();
	}//Score(int)
	
	/** Score(int, String)
	 * costruttore con parametri
	 * @param score: int, punteggio ottenuto dal giocatore;
	 * @param nickname: String, nome che il giocatore ha inserito al termine della
	 * 							partita;
	 */
	public Highscore(int score, String nickname){
		this.score=score;
		this.score_date=new Date().toString();
		this.nickname=nickname;
	}//Score(int,String)
	

	/** metodo toString(): String
	 * @return highscore: String, stampa del punteggio.
	 */
	@Override
	public String toString(){
		//punteggio
		String highscore = new String(""+score);
		if(this!=null){
			highscore = new String(score+"  "+nickname+"  "+score_date);
		}
		return highscore;
	}//toString()
	

	/** updateScore(CellStatus): void
	 * questo metoodo viene invocato, durante la sessione di gioco, ogni volta che
	 * verra' compiuta un mossa 
	 * @param c
	 */
	public void updateScore(CellStatus cs){
		//numero di mosse compiute
		move_count++;
		//in base alla cella in cui ci si trova verra' aggiornato il valore del punteggio
		switch(cs){
		case ENEMY : 
			score= score + DEAD;
			break;
		case DANGER :
			score = score + TRAP;
			break;
		case AWARD:
			score = score + WIN;
			break;
		default: //include anche SAFE
			break;
		}//end switch
		//System.out.println("numero di mosse "+move_count);
	}//updateScore()
	
	/** metodo hitScore(): void
	 * questo metodo si occupa di aggiornare il punteggio
	 * se e' stato colpito il nemico
	 */
	public void hitScore() {
		score = score + HIT;
	}//hitScore()
	
	/** metodo totalScore(): void
	 * questo metoto aggiorna il punteggio totale, tenenedo conto
	 * del numero di mosse compiute.
	 */
	public void totalScore(){
		score = score + STEP*move_count;
	}//totalScore()
		
	
}//end class
