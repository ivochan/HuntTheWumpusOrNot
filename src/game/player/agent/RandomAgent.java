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
			if(Starter.getChanceToHit()) {
				//si verifica la disponibilita' del colpo
				System.out.println("Si tenta il colpo");
				//si sceglie la direzione in cui colpire
				//almeno una delle celle non e' stata visitata se il sensore e' acceso
				Direction shot_dir = chooseShotDir(pg_pos[0], pg_pos[1], gm);
				//si tenta il colpo
				Controller.hitEnemy(shot_dir, gm);
				//si resetta il flag
				Starter.setChanceToHit(false);
			}
			else {
				//non si hanno munizioni
				System.out.println(GameMessages.no_hit);
			}
			
		}
		else if(sensors[CellStatus.DANGER_SENSE.ordinal()]) {
			//il pericolo e' vicino
			//TODO
		}
		else {
			//entrambi i sensori sono spenti
			
		}
		

	}//chooseMove(GameMap)


	private Direction chooseShotDir(int i, int j, GameMap em) {
		//TODO memorizzare celle idone in un vettore temporaneo
		//di boolean e poi la scelgo con un numero casuale moltiplicato per
		//il massimo di celle trovate
		//si cerca la direzione in cui sparare
		if(em.cellExists(i-1, j)) { //UP
			//si preleva la cella
			CellStatus cs = em.getMapCell(i-1, j).getCellStatus();
			//si verifica che non e' un sasso
			if(!cs.equals(CellStatus.FORBIDDEN)) {
				//si verifica che la cella non e' stata visitata

			}
				
			
		}
		if(em.cellExists(i+1, j)) { //DOWN
			
			
		}
		if(em.cellExists(i, j-1)) { // LEFT
			
			
		}
		if(em.cellExists(i, j+1)) { //RIGHT
			
			
		}
		return null;
		
		
	}
	

	
}//end RandomAgent
