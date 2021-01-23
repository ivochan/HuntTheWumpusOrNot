package game_structure;
// si caratterizza la tipologia della cella
public enum CellStatus {
    SAFE,
    PIT,
    WUMPUS,
    HERO,
    GOLD,
    DENIED, 
    TRAP;
}//CellStatus

/* SAFE, colore verde, cella accessibile e LIBERA, 0
   PIT, colore blu, FOSSA in cui puo' cadere l'avventuriero (hero_side), 1
   WUMPUS, colore rosso, MOSTRO, 2
   HERO, colore arancione, AVVENTURIERO, 3
   GOLD, colore giallo, ORO, 4
   DENIED, colore nero, cella non accessibile, PIETRA, 5
   TRAP colore viola, trappola in cui puo' cadere in wumpus, 6 (wumpus_side)
*/

