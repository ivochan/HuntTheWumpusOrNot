package game.player.agent;
import game.session.configuration.Starter;
import game.session.controller.Controller;
//serie di import
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
import game.structure.text.GameMessages;
import game.structure.text.GameTranslations;
/** class RandomAgent
 * giocatore automatico
 * @author ivonne
 */
public class RandomAgent extends BasicAgent {
	//###### attributi di classe #####
	//posizione del pg
	private int[] pg_pos = new int[2];
	
	
	//##### gli altri metodi sono ereditati dalla classe madre #####
	
	/** metodo chooseMove(GameMap): Direction
	 * 
	 */
	@Override
	public void chooseMove(GameMap em, GameMap gm) {
		//variabile ausiliaria per la direzione
		Direction dir;
		//variabile ausiliaria per i sensori
		boolean[] sensors = new boolean[2];
		//si preleva la posizione del pg
		pg_pos = PlayableCharacter.getPGposition();
		//si preleva la cella in cui si trova
		Cell cur = em.getMapCell(pg_pos[0], pg_pos[1]);
		//si preleva il vettore dei sensori
		sensors = cur.getSenseVector();
		//verifica del contenuto
		if(sensors[CellStatus.ENEMY_SENSE.ordinal()]) {
			//il nemico e' nelle vicinanze
			System.out.println("Il nemico e' in agguato!");
			if(Starter.getChanceToHit()) {
				//si verifica la disponibilita' del colpo
				System.out.println("Si tenta il colpo");
				//si sceglie la direzione in cui colpire
				//almeno una delle celle non e' stata visitata se il sensore e' acceso
				Direction shot_dir = chooseDirection(pg_pos[0], pg_pos[1], gm);
				System.out.println("sparo verso "+shot_dir);
				//si tenta il colpo
				Controller.hitEnemy(shot_dir, gm);
				//si resetta il flag
				Starter.setChanceToHit(false);
			}
			else {
				//non si hanno munizioni
				System.out.println(GameMessages.no_hit);
				//si sceglie la direzione in cui fare muovere il pg
				dir =chooseDirection(pg_pos[0], pg_pos[1], gm);
				//si muove il pg
				Controller.movePG(dir, gm, em);
			}
			
		}
		else if(sensors[CellStatus.DANGER_SENSE.ordinal()]) {
			//il pericolo e' vicino
			System.out.println("Il pericolo e' vicino...");
			//si preferisce come direzione una cella non visitata
			dir = chooseDirection(pg_pos[0], pg_pos[1], gm);
			//si muove il pg
			Controller.movePG(dir, gm, em);
		}
		else {
			//entrambi i sensori sono spenti
			System.out.println(GameMessages.safe_place);
			//si sceglie una direzione a caso, tra quelle non esplorate
			dir = chooseDirection(pg_pos[0], pg_pos[1], gm);
			//si muove il pg
			Controller.movePG(dir, gm, em);
		}
	}//chooseMove(GameMap)

	@Override
	public Direction chooseDirection(int i, int j, GameMap em) {
		//variabile ausiliaria
		boolean found = false;
		//vettore ausiliario
		boolean [] ok_cells = new boolean[4];
		//indice di cella casuale
		int random = 0;
		//si verificano le celle dispoonibili
		if(em.cellExists(i-1, j)) { //UP
			//si verifica la cella e si aggiorna il vettore
			ok_cells[Direction.UP.ordinal()] = verifyCell(i-1,j, em);
		}
		if(em.cellExists(i+1, j)) { //DOWN
			//si verifica la cella e si aggiorna il vettore
			ok_cells[Direction.DOWN.ordinal()] = verifyCell(i+1,j, em);
		}
		if(em.cellExists(i, j-1)) { // LEFT
			//si verifica la cella e si aggiorna il vettore
			ok_cells[Direction.LEFT.ordinal()] = verifyCell(i,j-1, em);
		}
		if(em.cellExists(i, j+1)) { //RIGHT
			//si verifica la cella e si aggiorna il vettore
			ok_cells[Direction.RIGHT.ordinal()] = verifyCell(i,j+1, em);
		}
		//si sceglie casualmente una cella
		while(!found) {
			//si genera un numero casuale
			random = (int)(Math.random()*4);
			System.out.println("random "+random);
			//si accede alla cella corrispondente
			found = ok_cells[random];
			//se true si esce dal ciclo
		}
		//si prelevano le direzioni possibili
		Direction direction = Direction.values()[random];
		//la cella idonea e' quella indicata da random
		return direction;
	}

	private boolean verifyCell(int i, int j, GameMap em) {
		//si preleva la cella
		CellStatus cs = em.getMapCell(i-1, j).getCellStatus();
		//si verifica che non e' un sasso
		if(!cs.equals(CellStatus.FORBIDDEN)) {
			//si verifica che la cella non e' stata visitata
			if(!cs.equals(CellStatus.OBSERVED)) {
				//la cella e' idonea
				return true;
			}
			return false;
		}
		//la cella non e' idonea
		return false;	
	}
	
}//end RandomAgent
