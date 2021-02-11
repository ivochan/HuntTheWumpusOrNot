package game.structure.cell;
/** Enumerazione utilizzata per il vettore dei sensori: 
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

public enum SenseStatus {
	ENEMY_SENSE,
	DANGER_SENSE;
}//SenseStatusH
