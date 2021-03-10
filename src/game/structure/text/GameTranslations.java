package game.structure.text;
import java.util.HashMap;
import game.structure.cell.CellStatus;
/** class GameTranslations
 * questa e' una classe di utilita', contenente soltanto metodi statici, 
 * che permettera' di avere a disposione le traduzioni degli elementi di
 * gioco, in base alla modalita' che verra' selezionata nella sessione di
 * gioco vera e propria.
 * @author ivonne
 */
public class GameTranslations {
	//##### attribuiti di classe #####
	
	/** HERO_SIDE
	 * Questa HashMap conterra' tutti i messaggi che verranno utlizzati per
	 * dare informazioni sullo stato del gioco all'utente, nella modalita' eroe.
	 */
	public static final HashMap<CellStatus, String> hero_side=
			new HashMap<CellStatus, String>();	
	
	/** WUMPUS_SIDE
	 * Questa HashMap conterra' tutti i messaggi che verranno utlizzati per
	 * dare informazioni sullo stato del gioco all'utente, nella modalita' wumpus.
	 */
	public static final HashMap<CellStatus, String> wumpus_side=
			new HashMap<CellStatus, String>();

	//##### inizializzazione dei messaggi di gioco #####
	
	/** metodo initHeroTranslation(): void
	 * inizializza la mappa dei messaggi che verranno utilizzati nel gioco
	 * per fornire delle informazioni all'utente, nella modalita' eroe.
	 */
	public static void initHeroTranslation() {
		hero_side.put(CellStatus.PG, "Benvenuto avventuriero!\nAndiamo a caccia del Wumpus e dei suoi tesori.");
		hero_side.put(CellStatus.ENEMY, "Ahi ahi, sei stato ferito dal Wumpus!");
		hero_side.put(CellStatus.AWARD, "Hai trovato il tesoro del Wumpus!");
		hero_side.put(CellStatus.DANGER, "Sei caduto nella fossa piena d'acqua...");
		hero_side.put(CellStatus.SAFE, "");
		hero_side.put(CellStatus.FORBIDDEN, "Il passaggio e' bloccato da un sasso.");
		hero_side.put(CellStatus.ENEMY_SENSE, "Pufff! Che puzza...Il mostro deve essere vicino!");
		hero_side.put(CellStatus.DANGER_SENSE, "Brrr, che aria fredda che arriva...");
	}//initHeroTranslation()
	
	/** metodo initWumpusTranslation(): void
	 * inizializza la mappa dei messaggi che verranno utilizzati nel gioco
	 * per fornire delle informazioni all'utente, nella modalita' wumpus.
	 */
	public static void initWumpusTranslation() {
		wumpus_side.put(CellStatus.PG, "Benvenuto Wumpus!\nAttenzione al cacciatore, scappa.");
		wumpus_side.put(CellStatus.ENEMY, "Oh no, il cacciatore ti ha colpito!");
		wumpus_side.put(CellStatus.AWARD, "Hai trovato una via di fuga, sbrigati!");
		wumpus_side.put(CellStatus.DANGER, "Sei caduto in trappola...");
		wumpus_side.put(CellStatus.SAFE, "");
		wumpus_side.put(CellStatus.FORBIDDEN, "Il passaggio e' bloccato da un sasso.");
		wumpus_side.put(CellStatus.ENEMY_SENSE, "Swiiiish...si muovono le foglie...c'e' qualcuno che ti osserva...");
		wumpus_side.put(CellStatus.DANGER_SENSE, "Crick, crock...Il terreno e' instabile");
	}//initWumpusTranslation()

}//end GameTranslation
