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

/* SAFE, colore verde, cella accessibile e LIBERA
   PIT, colore blu, FOSSA in cui puo' cadere l'avventuriero (hero_side)
   WUMPUS, colore rosso, MOSTRO
   HERO, colore arancione, AVVENTURIERO
   GOLD, colore giallo, ORO
   DENIED, colore nero, cella non accessibile,PIETRA
   TRAP colore viola, trappola in cui puo' cadere in wumpus (wumpus_side)
*/

