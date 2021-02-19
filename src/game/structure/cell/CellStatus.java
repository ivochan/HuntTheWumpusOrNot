package game.structure.cell;
/** Enumerazione CellStatus
 * caratterizza la tipologia del contenuto di ogni cella
 * che compone la mappa di gioco
 * @author ivonne
 * Le informazioni da descrivere sono le seguenti:
 * 
 * PG, e' il personaggio giocabile. In base alla modalità di gioco puo' essere:
 * 		-l'Avventuriero, se ci si trova nella modalita' eroe;
 * 		-il Wumpus, se ci si trova nella modalita' mostro; 
 * 		(colore ARANCIONE)
 * 
 * ENEMY, e' l'avversario del pg. In base alla modalità di gioco puo' essere:
 * 		  -il Wumpus, se ci si trova nella modalita' eroe;
 * 		  -l'Avventuriero, se ci si trova nella modalita' mostro; 
 * 		  (colore ROSSO)
 * 
 * AWARD, e' un premio che puo' essere trovato dal PG. 
 * 		  Nella modalita' eroe sara' un lingotto d'oro, in quella wumpus, invece,
 * 		  sara' una via di fuga dal dungeon, in modo da sfuggire al cacciatore.
 * 		  (colore GIALLO)
 * 
 * DANGER, e' il pericolo in cui puo' incorrere il PG, che in base alla modalita' 
 * 		   di gioco, puo' essere:
 * 		  -PIT, la fossa, il pozzo in cui puo' cadere l'avventuriero;
 * 		   (colore CIANO)
 *		  -TRAP, la trappola in cui puo' cadere il wumpus, preparata dal 
 *		   cacciatore.
 *		   (colore VIOLA)
 *
 * SAFE, e' una cella sicura, accessibile e priva di pericoli.
 * 		 (colore VERDE)
 *
 * FORBIDDEN, e' una cella non accessibile, rappresenta un sasso, un punto della
 * 		   mappa in cui il PG non puo' essere posizionato.
 * 		   (colore NERO)
 * BAT 
 * 
 * ENEMY_SENSE, indica che il nemico si trova nelle vicinanze.
 * In base alla modalita' di gioco questo valore rappresenta:
 * 	-STINK, colore marrone, odore del Wumpus;
 * 	-CREAK, colore marrone, scricchiolio dovuto al passo dell'eroe;
 * 	 (colore marrone)
 * 
 * DANGER_SENSE, indica che nelle vicinanze si rischia di incorrere in una
 * situazione di pericolo, che puo' essere dovuta ad:
 *	-una fossa di cui giunge la brezza, BREEZE;
 *	-il cacciatore in agguato, SWISH, di cui si sente il fruscio di foglie;
 *	 (colore bianco)
 */
public enum CellStatus {
	//per quanto riguarda i sensori
	ENEMY_SENSE,
	DANGER_SENSE,
	//per quanto riguarda la tipologia delle celle
	PG,
	ENEMY,
	AWARD,
	DANGER,
	SAFE,
	FORBIDDEN,
	BAT,
	//indica se la cella e' stata esaminata
	OBSERVED;
}//CellStatus
