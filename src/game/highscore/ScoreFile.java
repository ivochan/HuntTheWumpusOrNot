package game.highscore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreFile {
	//attributo di classe
	private static String path = "/home/ivonne/eclipse-workspace/WumpusGame/src/game/highscore/Record.txt";
	
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static void createScoreFile(String path) {
		File f = new File(path);
		try {
			
			if(f.exists()) {
				System.out.println("Il file dei punteggi si trova in "+path);
			}
			else if(f.createNewFile()) {
				System.out.println("Il file dei punteggi e' stato creato in "+path);
			
			}
			else {
				System.out.println("Il file " + path + " non puo' essere creato");
			}

		} 
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param f
	 * @param h
	 */
	public static void writeScoreFile(Highscore h) {
		//si preleva il file
		File f = new File(path);
		//flag che indica di non sovrascrivere le righe
		boolean append = true;
		//lettura
		try {
			FileWriter fw = new FileWriter(f, append);
			fw.write(h.toString()+'\n');
			fw.flush();
			fw.close();
			}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param f
	 */
	public static void readScoreFile() {
		File f = new File(path);
		try {
			FileReader fr = new FileReader(f);
			//buffer
			BufferedReader b = new BufferedReader(fr);
			String s=new String();
			//si cicla fino a quando non si trova una linea vuota
			do{
				s=b.readLine();
				System.out.println(s==null?"":s);
			}while(s!=null);
			fr.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	
	public static void saveHighscore(Highscore h) {
		//creazione del file
		createScoreFile(path);
		//scrittura del punteggio
		writeScoreFile(h);
	}
	
	//TODO documentazione
	//TODO metodi accessori per il path
	//TODO ordinamento dei punteggi nel file
	
	public static void deleteScoreFile() {
		File f = new File(path);
		if(f.exists()){
			//il file esiste
			if(f.delete()) {
				//si tenta di eliminarlo
				System.out.println("File eliminato!");
			}
		}
		else
			System.out.println("Il file non esiste!");
		}
	
}//end class

