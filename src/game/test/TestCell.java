package game.test;

import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
/**
 * classe di test 
 * e' utilizzata per validare le funzionalita' della classe Cell
 * @author ivonne
 *
 */
public class TestCell {

	public static void main(String[] args) {
		//############		Test oggetto Cell e SenseVector		##############
		boolean info=true;
		//costruttore di default
		Cell c1 = new Cell();
		//stampa del contenuto della cella
		System.out.println("cella c1 "+c1);
		//secondo costruttore
		Cell c2 = new Cell(CellStatus.AWARD);
		//stampa del contenuto della cella
		System.out.println("cella c2 "+c2);
		//terzo costruttore
		Cell c3 = new Cell(CellStatus.PG,false,true);
		//stampa del contenuto di c3
		System.out.println("cella c3 "+c3);
		//stampa del vettore dei sensi
		System.out.println(c3.senseVectorToString(info));
		//test enum
		System.out.println("intero associato all'enum PIT "+CellStatus.DANGER.ordinal());
		//test sui metodi accessori
		Cell c = new Cell();
		System.out.println("Cella c "+ c.getCell());
		Cell copy = c.getCell();
		System.out.println("Cella copia "+ copy);
		copy.setCellStatus(CellStatus.ENEMY);
		System.out.println("Cella copia "+copy);
		System.out.println("Posizione "+c.getCellPosition());
		c.setCellPosition(0,-1);
		c.setCellStatus(CellStatus.PG);
		System.out.println(c);
		c.setCellPosition(0,0);
		System.out.println("Posizione "+c.getCellPosition());
		//test metodo di copia
		copy.copyCellSpecs(c);
		System.out.println(c.senseVectorToString(info));
		System.out.println(copy.senseVectorToString(!info));
		

	}

}
