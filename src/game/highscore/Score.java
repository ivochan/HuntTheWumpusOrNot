package game.highscore;

import java.util.Date;

import game.structure.cell.CellStatus;


/** class Score
 * questa classe rappresenta l'oggetto Score, costituito da due attributi:
 * -il nickname del giocatore;
 * -il punteggio raggiunto;
 * @author ivonne
 *
 */
public class Score {
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
	public Score(){
		//gli attributi di classe avranno i valori di default
		this.score=0;
		this.score_date=new Date().toString();
		//nickname resta con il valore di default
	}//score()
	
	/** Score(int)
	 * costruttore con parametro
	 * @param score: int, punteggio raggiunto dal giocatore
	 */
	public Score(int score){
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
	public Score(int score, String nickname){
		this.score=score;
		this.score_date=new Date().toString();
		this.nickname=nickname;
	}//Score(int,String)
	

	
	@Override
	public String toString(){
		//punteggio
		String highscore = new String(""+score);
		if(this!=null){
			highscore = new String(nickname+"  "+score+"  "+score_date);
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
			score= score + HighScore.DEAD;
			break;
		case DANGER :
			score = score + HighScore.TRAP;
			break;
		case AWARD:
			score = score + HighScore.WIN;
			break;
		default: //include anche SAFE
			break;
		}//end switch
		//System.out.println("numero di mosse "+move_count);
	}
	
	public void totalScore(){
		score = score + HighScore.STEP*move_count;
	}
		
}//end class
