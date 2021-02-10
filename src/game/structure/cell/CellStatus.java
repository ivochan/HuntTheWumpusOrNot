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
 * GOLD, e' un premio che puo' essere trovato dal PG. 
 * 		  In questo caso sara' un lingotto d'oro.
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
 * 
 */
public enum CellStatus {
	PG,
	ENEMY,
	GOLD,
	DANGER,
	SAFE,
	FORBIDDEN;
}//CellStatus
