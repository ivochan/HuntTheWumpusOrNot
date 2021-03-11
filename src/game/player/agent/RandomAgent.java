package game.player.agent;
//serie di import
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
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
	public Direction chooseMove(GameMap em) {
		//variabile ausiliaria
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
			//TODO 
			System.out.println("Si tenta il colpo");
		}
		else {
			//si cerca una cella libera
			
		}
		
		
		
		return null;
	}//chooseMove(GameMap)
	
	private static int[] findCell(Direction move, int[] pg_pos){
		//vettore della posizione da restituire
		int [] cell_pos= new int[2];
		//si prelevano gli indici della posizione del pg
		int ipg = pg_pos[0];
		int jpg = pg_pos[1];
		//System.out.println("posizione corrente "+ipg+" e "+jpg);
		//si inizializza la coppia di indici ausiliari
		int im=0;
		int jm=0;
		//si verifica se esiste una cella nella direzione indicata
		switch(move) {
			case UP: //move = 0
				//indici della cella in cui si vuole muovere il pg
				im = ipg-1;
				jm = jpg;
				break;
			case DOWN: //move = 1
				//indici della cella in cui si vuole muovere il pg
				im = ipg+1;
				jm = jpg;
				break;
			case LEFT://move = 2
				//indici della cella in cui si vuole muovere il pg
				im = ipg;
				jm = jpg-1;
				break;
			case RIGHT://move = 3
				//indici della cella in cui si vuole muovere il pg
				im = ipg;
				jm = jpg+1;
				break;
			default: 
				break;
		}//end switch
		//si memorizzano questi indici nel vettore della posizione successiva
		cell_pos[0]=im;
		cell_pos[1]=jm;
		//System.out.println("verifica della prossima posizione "+im+" e "+jm);
		return cell_pos;
	}//findCell(Direction , int[])
	
}//end RandomAgent
