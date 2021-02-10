package game.structure.test;

import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
/**
 * classe di test della classe Cell
 * @author ivonne
 *
 */
public class TestCell {

	public static void main(String[] args) {
		//############		Test oggetto Cell e SenseVector		##############
		boolean hero_side=true;
		//costruttore di default
		Cell c1 = new Cell();
		//stampa del contenuto della cella
		System.out.println("cella c1 "+c1);
		//secondo costruttore
		Cell c2 = new Cell(CellStatus.GOLD);
		//stampa del contenuto della cella
		System.out.println("cella c2 "+c2);
		//test attributo isVisited
		System.out.println("La cella e' stata visitata? "+ (c2.isVisited()?"si":"no"));
		//terzo costruttore
		Cell c3 = new Cell(CellStatus.HERO,false,true);
		//stampa del contenuto di c3
		System.out.println("cella c3 "+c3);
		//stampa del vettore dei sensi
		System.out.println(c3.senseVectorToString(hero_side));
		//stampa del vettore dei sensi
		System.out.println(c3.senseVectorToString(!hero_side));
		//test enum
		System.out.println("intero associato all'enum PIT "+CellStatus.PIT.ordinal());
		//test sui metodi accessori
		Cell c = new Cell();
		System.out.println("Cella c "+ c.getCell());
		Cell copy = c.getCell();
		System.out.println("Cella copia "+ copy);
		copy.setCellStatus(CellStatus.WUMPUS);
		System.out.println("Cella copia "+copy);
		System.out.println("Posizione "+c.getCellPosition());
		c.setCellPosition(0,-1);
		c.setCellStatus(CellStatus.HERO);
		System.out.println(c);
		c.setCellPosition(0,0);
		System.out.println("Posizione "+c.getCellPosition());
		//test metodo di copia
		copy.copyCellSpecs(c);
		System.out.println("Cella c "+c+" e' stata visitata? "+c.isVisited());
		System.out.println(c.senseVectorToString(hero_side));
		System.out.println("Cella copy "+copy+" e' stata visitata? "+copy.isVisited());
		System.out.println(copy.senseVectorToString(hero_side));
		

	}

}
