package game.session.score;
import java.text.SimpleDateFormat;
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
	private static int score;
	//nickname del giocatore
	private String nickname;
	//data e ora del punteggio
	private String date;
	//formato della data
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	//numero di mosse compiute
	private static int move_count;
	
	//##### costruttori #####
	
	/** costruttore di default
	 * inizializza tutti i campi a dei valori di default
	 */
	public Score() {
		//punteggio
		score=0;
		//nome del giocatore
		nickname=new String("Giocatore");
		//data della partita
		date = new String(sdf.format(new Date()));
		//numero di mosse
		move_count=0;
	}//Score()
	
	/** costruttore Score(String)
	 * questo costruttore permette di inserire un nome diverso
	 * da quello di default per il giocatore
	 * @param nickname: String, nome del giocatore
	 */
	public Score(String nickname) {
		//punteggio
		score=0;
		//controllo sul parametro
		if(nickname==null || nickname.isEmpty()) {
			//nome del giocatore
			this.nickname=new String("Giocatore");	
		}//fi
		else {
			this.nickname = new String(nickname);
		}//esle
		//data della partita
		date = new String(sdf.format(new Date()));
		//numero di mosse
		move_count=0;
	}//Score(String)
	
	
	
	//##### metodi accessori: punteggio #####
		
	/** metodo getScore(): int
	 * @return score: int, punteggio del giocatore.
	 */
	public int getScore() {
		//si restituisce il valore del punteggio
		return score;
	}//getScore()
	
	/** metodo setScore(): void
	 * @param score_value: int, punteggio da impostare
	 */
	public void setScore(int score_value) {
		//si imposta il valore del punteggio
		score=score_value;
	}//setScore(int)
	
	//##### metodi accessori: numero di mosse #####
	
	/** metodo getMoveCount(): int
	 * questo metodo restituisce il numero di mosse 
	 * effettuate dal giocatore durante la partita
	 */
	public  int getMoveCount() {
		//si restituisce il numero di mosse effettuale
		return move_count;
	}//getMoveCount()

	/** metodo setMoveCount(int): void
	 * questo metodo consente di modificare il numero di mosse
	 * @param mc: int, nuovo valore del numero di mosse;
	 */
	public void setMoveCount(int mc) {
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
	public String getNickname() {
		//si restituisce il nome del giocatore
		return nickname;
	}//getNickname()
	
	/** metodo setNickname(String) : void
	 * questo metodo permette di specificare il nickname con cui il
	 * giocatore vuole memorizzare il suo punteggio.
	 * @param nickname: String, nome del giocatore.
	 */
	public void setNickname(String nick) {
		//controllo sul parametro
		if(nick==null) throw new IllegalArgumentException("nickname non valido");
		//si aggiorna il nickname
		nickname=nick;
	}//setNickname(String)
	
	//##### altri metodi #####
	
	/** metodo toString(): String
	 * @return score: String, stampa del punteggio.
	 */
	@Override
	public String toString(){
		//stringa con i dati del punteggio
		String s = new String(score+" "+nickname+" "+date);
		//si restituisce
		return s;
	}//toString()
	
	/** metodo printScore(): String
	 * questo metodo restituisce una stringa
	 * che contiene solamente il valore del punteggio
	 * @return print_score: String, valore del punteggio corrente
	 */
	public String printScore() {
		//stringa ausiliaria
		String print_score;
		//si assegna il valore
		print_score=new String(""+score);
		//si restituisce
		return print_score;
	}//printScore()
	
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
		//calcolo del punteggio in base alla cella
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
		//nel punteggio si deve considerare anche il numero di mosse
		score = score + STEP;
	}//updateScore(CellStatus)

	/** metodo hitScore(): void
	 * questo metodo si occupa di aggiornare il punteggio
	 * se e' stato colpito il nemico
	 */
	public static void hitScore() {
		score = score + HIT;
	}//hitScore()
		
	/** metodo resetScoreData(): void
	 * resetta il punteggio attuale
	 */
	public  void resetScoreData() {
		//si assegnano alle variabili i valori di default
		score = 0;
		nickname = new String("Giocatore");
		date = new String(sdf.format(new Date()));
		move_count=0;
	}//resetScoreData()
	
}//end Score
