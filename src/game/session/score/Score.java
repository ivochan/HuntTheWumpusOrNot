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
	
	//nickname del giocatore
	private String nickname;
	//data e ora del punteggio 	nel formato :dd-MM-yyyy HH:mm:ss.SSS
	private Date date;
	//strumento di formattazione della data
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	
	//valore del punteggio
	private Integer score_value;
	//numero di mosse compiute
	private static int move_count;
	
	//##### costruttori #####
	
	/** costruttore di default
	 * inizializza tutti i campi a dei valori di default
	 */
	public Score() {
		//punteggio
		score_value=0;
		//nome del giocatore
		nickname=new String("Giocatore");
		//data della partita
		date = new Date();
		//numero di mosse
		move_count=0;
	}//Score()
	
	/** costruttore Score(String)
	 * questo costruttore permette di inserire un nome diverso
	 * da quello di default per il giocatore
	 * @param nickname: String, nome del giocatore
	 * questo costruttore viene utilizzato nel caso del giocatore automatico
	 */
	public Score(String nickname) {
		//punteggio
		score_value=0;
		//controllo sul parametro
		if(nickname==null || nickname.isEmpty()) {
			//nome del giocatore
			this.nickname=new String("Player");	
		}//fi
		else {
			this.nickname = new String(nickname);
		}//esle
		//data della partita
		date = new Date();
		//numero di mosse
		move_count=0;
	}//Score(String)
	/** costruttore Score(Integer, String, Date)
	 * costruttore che riceve tutti i parametri
	 * @param score_value
	 * @param nickname
	 * @param date
	 */
	public Score(Integer score_value, String nickname, Date date) {
		//controllo sui parametri
		if(score_value==null)throw new IllegalArgumentException("punteggio non valido");
		if(nickname==null) throw new IllegalArgumentException("nickname non valido");
		if(date==null)throw new IllegalArgumentException("data non valida");
		////creazione dell'oggetto
		this.score_value=score_value;
		this.nickname=nickname;
		this.date=date;
	}//Score(Integer,String,Date)
	
		
	//##### metodi accessori: value #####
	
	/** metodo getScore(): Integer
	 * @return score_value: Integer, punteggio del giocatore.
	 */
	public Integer getScore() {
		//si restituisce il valore del punteggio
		return score_value;
	}//getScore()
		
	/** metodo setScore(): void
	 * @param score: Integer, punteggio da impostare
	 */
	public void setScore(Integer score) {
		//controllo sul parametro
		if(score==null)throw new IllegalArgumentException("punteggio non valido");
		//si imposta il valore del punteggio
		score_value=score;
	}//setScore(Integer)
	
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
	public void setNickname(String nickname) {
		//controllo sul parametro
		if(nickname==null || nickname.isEmpty()) {
			this.nickname=new String("Player");
		}
		else {
			//si aggiorna il nickname
			this.nickname=nickname;
		}
	}//setNickname(String)
	
	//##### metodi accessori: data #####
	
	/** metodo getDate(): Date
	 * @return date: Date, restituisce la data in cui e' stato
	 * 						acquisito il punteggio.
	 */
	public Date getDate() {
		//si restituisce la data 
		return date;
	}//getDate()
		
	/** metodo setDate(Date): void
	 * questo metodo permette di modificare la data 
	 * del punteggio
	 * @param score_date: Date, la nuova data;
	 */
	public void setDate(Date score_date) {
		//controllo sul paramentro
		if(date==null)throw new IllegalArgumentException("data non valida");
		//si aggiorna il campo data
		date=score_date;
	}//setDate(String)
		
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
	
	//##### altri metodi #####
	
	/** metodo toString(): String
	 * @return score: String, stampa del punteggio.
	 */
	@Override
	public String toString(){
		//stringa con i dati del punteggio
		String s = new String(score_value+" "+nickname+" "+sdf.format(date));
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
		print_score=new String(""+score_value);
		//si restituisce
		return print_score;
	}//printScore()
	
	/** updateScore(CellStatus): void
	 * questo metoodo viene invocato, durante la sessione di gioco, ogni volta che
	 * verra' compiuta un mossa 
	 * @param cs: CellStatus, stato della cella.
	 */
	public  void updateScore(CellStatus cs){
		//numero di mosse compiute
		move_count++;
		//si aggiorna il valore del punteggio
		switch(cs){
		//calcolo del punteggio in base alla cella
		case ENEMY : 
			score_value= score_value + DEAD;
			break;
		case DANGER :
			score_value = score_value + TRAP;
			break;
		case AWARD:
			score_value = score_value + WIN;
			break;
		default: //SAFE e FORBIDDEN
			break;
		}//end switch
		//nel punteggio si deve considerare anche il numero di mosse
		score_value = score_value + STEP;
	}//updatescore_value(CellStatus)

	/** metodo hitScore(): void
	 * questo metodo si occupa di aggiornare il punteggio
	 * se e' stato colpito il nemico
	 */
	public void hitScore() {
		score_value = score_value + HIT;
	}//hitScore()
		
}//end Score
