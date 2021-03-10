package game.session.score;
import java.util.Date;
import game.structure.cell.CellStatus;
/** class Score
 * questa classe descrive il punteggio raggiunto dal giocatore
 * durante la sessione di gioco, calcolandolo in base alle mosse che
 * ha effettuato durante la partita.
 * @author ivonne
 */
public class Score {
	//punteggi assegnati alle azioni da compiere nel gioco
	public static final int WIN = 100; //premio
	public static final int TRAP = -50; //pericolo
	public static final int DEAD = -100; //nemico
	public static final int STEP = -1; //mossa
	public static final int HIT = 50; //colpo andato a segno
	//valore del punteggio
	private static int score = 0;
	//nickname del giocatore
	private static String nickname = new String("Giocatore");
	//data e ora del punteggio
	private static String score_date = new Date().toString();
	//numero di mosse compiute
	private static int move_count;
	//##### metodi accessori: punteggio #####
		
	/** metodo getScore(): int
	 * @return score: int, punteggio del giocatore.
	 */
	public static int getScore() {
		//si restituisce il valore del punteggio
		return score;
	}//getScore()
	
	/** metodo setScore(): void
	 * @param s: int, punteggio da impostare
	 */
	public static void setScore(int s) {
		//si imposta il valore del punteggio
		score=s;
	}//setScore(int)
	
	//##### metodi accessori: numero di mosse #####
	
	/** metodo getMoveCount(): int
	 * questo metodo restituisce il numero di mosse 
	 * effettuate dal giocatore durante la partita
	 */
	public static int getMoveCount() {
		//si restituisce il numero di mosse effettuale
		return move_count;
	}//getMoveCount()

	/** metodo setMoveCount(int): void
	 * questo metodo consente di modificare il numero di mosse
	 * @param mc: int, nuovo valore del numero di mosse;
	 */
	public static void setMoveCount(int mc) {
		//controllo sul parametro
		if(mc<0) throw new IllegalArgumentException("numero di mosse negativo");
		//si aggiorna il numero di mosse
		move_count=mc;
	}//setMoveCount(int)
	
	//##### metodi accessori: nickname giocatore #####
	
	/** metodo getNickname(): String
	 * questo metodo restituisce il nome con cui il giocatore
	 * ha memorizzato il suo punteggio
	 * @return nickname: String, nome del giocatore.
	 */
	public static String getNickname() {
		//si restituisce il nome del giocatore
		return nickname;
	}//getNickname()
	
	/** metodo setNickname(String) : void
	 * questo metodo permette di specificare il nickname con cui il
	 * giocatore vuole memorizzare il suo punteggio.
	 * @param nickname: String, nome del giocatore.
	 */
	public static void setNickname(String nick) {
		//controllo sul parametro
		if(nick==null) throw new IllegalArgumentException("nickname non valido");
		//si aggiorna il nickname
		nickname=nick;
	}//setNickname(String)
	
	//##### altri metodi #####
	
	/** metodo ScoreToString(): String
	 * @return score: String, stampa del punteggio.
	 */
	public static String ScoreToString(){
		//stringa con i dati del punteggio
		String s = new String(score+"  "+nickname+"  "+score_date);
		//si restituisce
		return s;
	}//toString()
	
	/** updateScore(CellStatus): void
	 * questo metoodo viene invocato, durante la sessione di gioco, ogni volta che
	 * verra' compiuta un mossa 
	 * @param cs: CellStatus, stato della cella.
	 */
	public static void updateScore(CellStatus cs){
		//numero di mosse compiute
		move_count++;
		//si aggiorna il valore del punteggio
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
		default: //SAFE e FORBIDDEN
			break;
		}//end switch
	}//updateScore(CellStatus)

	/** metodo hitScore(): void
	 * questo metodo si occupa di aggiornare il punteggio
	 * se e' stato colpito il nemico
	 */
	public static void hitScore() {
		score = score + HIT;
	}//hitScore()
		
	/** metodo totalScore(): void
	 * questo metoto aggiorna il punteggio totale, tenenedo conto
	 * del numero di mosse compiute.
	 */
	public static void totalScore(){
		score = score + STEP*move_count;
	}//totalScore()
		
	/** metodo resetScoreData(): void
	 * resetta il punteggio attuale
	 */
	public static void resetScoreData() {
		//si assegnano alle variabili i valori di default
		score = 0;
		nickname = new String("Giocatore");
		score_date = new Date().toString();
		move_count=0;
	}//resetScoreData()
	
}//end Score
