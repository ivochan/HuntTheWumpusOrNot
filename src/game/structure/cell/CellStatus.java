package game.structure.cell;
/** Enum CellStatus
 * caratterizza la tipologia del contenuto di ogni cella
 * che compone la mappa di gioco, secondo i valori seguenti:
 * 
 *-PG, e' il personaggio giocabile. In base alla modalità di gioco puo' essere:
 * 		-l'Avventuriero, se ci si trova nella modalita' eroe;
 * 		-il Wumpus, se ci si trova nella modalita' mostro; 
 * 
 *-ENEMY, e' l'avversario del pg. In base alla modalità di gioco puo' essere:
 * 		  -il Wumpus, se ci si trova nella modalita' eroe;
 * 		  -l'Avventuriero, se ci si trova nella modalita' mostro; 
 * 
 *-AWARD, e' un premio che puo' essere trovato dal PG. 
 * 		  Nella modalita' eroe sara' un lingotto d'oro, in quella wumpus, invece,
 * 		  sara' una via di fuga dal dungeon, in modo da sfuggire al cacciatore.
 * 
 *-DANGER, e' il pericolo in cui puo' incorrere il PG, che in base alla modalita' 
 * 		   di gioco, puo' essere:
 * 		  -PIT, la fossa, il pozzo in cui puo' cadere l'avventuriero;
 *		  -TRAP, la trappola in cui puo' cadere il wumpus, preparata dal 
 *		   cacciatore.
 *
 *-SAFE, e' una cella sicura, accessibile e priva di pericoli.
 *
 *-FORBIDDEN, e' una cella non accessibile, rappresenta un sasso, un punto della
 * 		   mappa in cui il PG non puo' essere posizionato.
 * 
 *-ENEMY_SENSE, indica che il nemico si trova nelle vicinanze.
 * In base alla modalita' di gioco questo valore rappresenta:
 * 	-STINK, colore marrone, odore del Wumpus;
 * 	-CREAK, colore marrone, scricchiolio dovuto al passo dell'eroe;
 *
 *-DANGER_SENSE, indica che nelle vicinanze si rischia di incorrere in una
 * situazione di pericolo, che puo' essere dovuta ad:
 *	-una fossa di cui giunge la brezza, BREEZE;
 *	-il cacciatore in agguato, SWISH, di cui si sente il fruscio di foglie;
 */
public enum CellStatus {
		//i sensori
		ENEMY_SENSE, 
		DANGER_SENSE,
		//tipologia di cella
		PG,
		ENEMY,
		AWARD,
		DANGER,
		SAFE,
		FORBIDDEN, 
		BAT,
		//cella visitata
		OBSERVED,
		//cella non ancora scoperta: stato non specificato
		UNKNOWN;
}//end CellStatus
