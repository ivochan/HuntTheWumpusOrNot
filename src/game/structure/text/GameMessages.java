package game.structure.text;
/** classe GameMessages
 * contiene i messaggi utilizzati all'interno del gioco
 * per fornire delle informazioni all'utente
 * 
 * @author ivochan
 */
public class GameMessages {

	//descrizione comandi
	public final static String command_legend = "Ecco la lista dei comandi:\n"+
								"[q - quit] [g - game start] [s - score] [c - credits] [n - set player name]";
	//richiesta di azione da parte dell'utente
	public final static String command_request = "Cosa vuoi fare?"; 
	//richiesta di scelta della modalita' di gioco
	public final static String mode = "Sarai il cacciatore oppure il Wumpus?\n"
							+"[h - cacciatore] [w - wumpus]";
	//richiesta del comando di mossa
	public final static String move_command = new String(" 												 \n"+
			 						 " Comandi:										 \n"+
			 						 " 												 \n"+
			 						 " w = sopra									 \n"+
			 						 " a = sinistra									 \n"+
			 						 " s = sotto									 \n"+
			 						 " d = destra									 \n"+
			 						 " i = interrompi partita						 \n"+
			 						 " l = prova a colpire il nemico                 \n"+
									 "												 \n");
	//messaggi sullo stato della partita
	public final static String looser = new String("Hai perso :(");
	public final static String winner = new String("Hai vinto !!! =) ");
	//messaggi di info
	public final static String credits = "E' Ivonne che ha tentato di sviluppare questo gioco...";
	//messaggi relativi al tentativo di colpire il nemico 
	public final static String wasted_shot = "Ooops...La tua arma ormai e' andata, come la possibilita'"
											+ " di battere il nemico!";
	public final static String failed_shot = "Che mira pessima...Non hai colpito niente!";
	public final static String no_hit = "Eh eh, non hai piu' nulla con cui colpire il nemico...";
	public final static String hit = "Yu-hu!Hai colpito il nemico!";
	//messaggi sullo stato della mossa
	public final static String denied_move = new String("Posizione non consentita!");
	public final static String safe_place = new String("Tutto a posto all'orizzonte!");
	//tipo di giocatore
	public final static String player_type = new String("Vuoi metterti alla prova? [y - yes] [n - no]");
	public final static String human_player = new String("Uh uh, coraggio allora!");
	public final static String automatic_player = new String("Bene, lascia tutto a me, ci penso io!");
}//end GameMessages
