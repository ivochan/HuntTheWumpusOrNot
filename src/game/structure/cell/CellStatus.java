package game.structure.cell;
/** Enumerazione CellStatus
 * caratterizza la tipologia del contenuto di ogni cella
 * che compone la mappa di gioco
 * @author ivonne
 * Le informazioni da descrivere sono le seguenti:
 * 
 * SAFE, colore verde, cella accessibile e LIBERA, 0
   PIT, colore blu, FOSSA in cui puo' cadere l'avventuriero (hero_side), 1
   WUMPUS, colore rosso, MOSTRO, 2
   HERO, colore arancione, AVVENTURIERO, 3
   GOLD, colore giallo, ORO, 4
   DENIED, colore nero, cella non accessibile, PIETRA, 5
   TRAP colore viola, trappola in cui puo' cadere in wumpus, 6 (wumpus_side)
 */
public enum CellStatus {
    SAFE,
    PIT,
    WUMPUS,
    HERO,
    GOLD,
    DENIED, 
    TRAP, // equivale a PIT
    NONE;
}//CellStatus
