package game.test;

import game.highscore.Highscore;
import game.highscore.ScoreFile;

public class TestScoreFile {

	public static void main(String [] args) {
		//path e nome del file
		String path  ="/home/ivonne/eclipse-workspace/WumpusGame/src/game/highscore/Record.txt";
		//creazione del file vuoto
		ScoreFile.createScoreFile(path);
		//punteggi da scrivere
		Highscore h = new Highscore(100,"Ivonne");
		Highscore h1 = new Highscore(90,"Ivonne");
		//scrittura dei punteggi
		ScoreFile.writeScoreFile(h);
		ScoreFile.writeScoreFile(h1);
		//lettura del file
		ScoreFile.readScoreFile();
		ScoreFile.deleteScoreFile();
	}//end main
}
