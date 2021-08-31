package game.session.score;
//import
import java.util.Comparator;
/** classe ScoreComparator 
* questa classe implementa l'interfaccia Comparator<T> 
* in modo da definire uno strumento che verra' utilizzato
* nel confronto di due oggetti di tipo <T>
* @author ivochan
*
*/
public class ScoreComparator implements Comparator<Score>{

	/** metodo compare( , ): int
	 * questo metodo fornisce un criterio di confronto per i due
	 * oggetti che riceve come parametro
	 */
	@Override
	public int compare(Score o1, Score o2) {
		//controllo sui parametri
		if(o1==null && o2==null)return 1;
		if(o1==null)return -1;
		if(o2==null)return 1;
		//confronto per integer
		if(o1.getScore()>o2.getScore())return 1;
		if(o1.getScore()<o2.getScore())return -1;
		//se i punteggi sono uguali si confronta per data
		return o1.getDate().compareTo(o2.getDate());
	}//compare()

}//end ScoreComparator