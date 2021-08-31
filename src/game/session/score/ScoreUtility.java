package game.session.score;
//serie di import
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//TODO rendere questa classe di servizio
/** class ScoreUtility
 * questa classe serve per interagire con il file di testo su
 * cui verranno memorizzati tutti i punteggi ottenuti dall'utente
 * @author ivonne
 */
public class ScoreUtility {

	//nome del file dei punteggi
	private static String name = new String("/Scores.txt");
	//path in cui creare il file
	private static String path = System.getProperty("user.dir") + name;

	//##### metodi per interagire con il file di testo #####
	
	/** metodo createScoreFile(): void
	 * questo metodo crea il file di memorizzazione dei punteggi
	 * utilizzando come path quello specificato dalla variabile
	 * di classe.
	 */
	public static void createScoreFile() {
		//controllo sul path
		if(path==null) throw new IllegalArgumentException("path come"
				+ " attributo di classe non valido");
		//si crea il file
		File f = new File(path);
		//cattura dell'eventuale eccezione
		try {
			//file esistente
			if(f.exists()) {
				//il file esiste gia'
				System.out.println("Il file dei punteggi si trova in "+path);
			}//fi
			else if(f.createNewFile()) {
				//il file e' stato creato
				System.out.println("Il file dei punteggi e' stato creato in "+path);
			}//fi
			else {
				//il file non e' stato creato
				System.out.println("Il file " + path + " non puo' essere creato");
			}//esle
		} 
		catch(IOException e){
			e.printStackTrace();
		}
	}//createScoreFile()
		
	/** metodo writeScoreFile(Score s): void
	 * questo metodo scrive il punteggio nel file di testo
	 * creato per il salvataggio 
	 * @param score: String, punteggio da memorizzare;
	 */
	public static void writeScoreFile(String score) {
		//si preleva il file
		File f = new File(path);
		//flag che indica di non sovrascrivere le righe
		boolean append = true;
		//lettura
		try {
			//si istanzia la classe per la scrittura
			FileWriter fw = new FileWriter(f, append);
			//si la nuova riga nel file
			fw.write(score+'\n');
			//si svuota e si chiude il buffer di scrittura
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}//writeScoreFile(Score)
		
	/** metodo readScoreFile(): void
	 * questo metodo accede al contenuto del file di testo in cui
	 * sono stati memorizzati i punteggi di gioco e lo stampa a video
	 */
	public static void readScoreFile() {
		//si preleva il file dei punteggi
		File f = new File(path);
		if(!f.exists()) {
			System.err.println("File inesistente!");
			return;
		}
		try {
			//si istanzia la classe per la lettura
			FileReader fr = new FileReader(f);
			//buffer
			BufferedReader b = new BufferedReader(fr);
			//variabile ausiliaria
			String s=new String();
			//si cicla fino a quando non si trova una linea vuota
			do{
				//si legge la riga
				s=b.readLine();
				//se non e' nulla si stampa
				System.out.println(s==null?"":s);
			}while(s!=null);
			//si chiude il processo di lettura
			fr.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}//readScoreFile()
	
	/** metodo deleteScoreFile(): void
	 * questo metodo viene utilizzato per cancellare
	 * il file contenente i punteggi
	 */
	public static void deleteScoreFile() {
		//si preleva il file
		File f = new File(path);
		//si controlla se esiste
		if(f.exists()){
			//il file esiste
			if(f.delete()) {
				//si tenta di eliminarlo
				System.out.println("File dei punteggi eliminato!");
			}//fi
		}//fi
		else {
			//file non trovato
			System.out.println("Il file dei punteggi non esiste!");
		}//esle
	}//deleteScoreFile()
	
	/** metodo saveScore(Score): void
	 * questo metodo si occupa della creazione
	 * e della scrittura del file dei punteggi, 
	 * quando e' richiesto di memorizzare quello
	 * ricevuto come parametro.
	 * @param score: String, punteggio da memorizzare
	 */
	public static void saveScore(String score) {
		//creazione del file
		createScoreFile();
		//scrittura del punteggio
		writeScoreFile(score);
	}//saveScore(Score)
		
	//##### metodi accessori #####
	
	/** metodo getPath(): String
	 * questo metodo restituisce il valore
	 * della stringa che rappresenta il path
	 * del file dei punteggi.
	 * @return path: String, stringa che contiene il path.
	 */
	public String getPath() {
		//si restituisce l'attributo di classe path
		return path;
	}//getPath()

	/** metodo setPath(String): void
	 * questo metodo aggiorna il valore
	 * del path del file.
	 * @param p: String, valore da assegnare al path.
	 */
	public void setPath(String p) {
		//controllo sul parametro
		if(p==null) throw new IllegalArgumentException("path del file ricevuto come"
				+ " parametro non valido");
		//si assegna il path
		path = new String(p);
	}//setPath(String)
	
	/** metodo getName(): String
	 * questo metodo restituisce il nome del file
	 * in cui salvare i punteggi.
	 * @return name: String, nome del file;
	 */
	public String getName() {
		//si restituisce l'attributo di classe path
		return name;
	}//getPath()

	/** metodo setName(): void
	 * questo metodo permette di impostare il nome
	 * del file.
	 * @param n: String, nome del file;
	 */
	public void setName(String n) {
		//controllo sul parametro
		if(n==null)throw new IllegalArgumentException("nome del file ricevuto come"
				+ " parametro non valido");
		//si specifica il nome del file
		name = new String(n);
	}//setPath(String)
	
	/** metodo scoreFileAnalysis()
	 * metodo per analizzare il contenuto di ogni riga del file dei punteggi
	 * estrapolare il punteggio, il nome del giocatore e la data in cui e' stato registrato
	 */
	public static void scoreFileAnalysis() {
		//vettore che conterra' gli elementi ottenuti dal parsing di ogni riga del file
		String [] score_line = new String[3];		
		//si istanzia il file dei punteggi
		File f = new File(path);		
		//analisi del file
		if(!f.exists()) {
			System.err.println("File inesistente!");
			return;
		}
		else {
			try {
				//si istanzia la classe per la lettura
				FileReader fr = new FileReader(f);
				//buffer
				BufferedReader b = new BufferedReader(fr);
				//variabile ausiliaria
				String s=new String("");
				//si cicla fino a quando non si trova una linea vuota
				do {
					//si legge la riga
					s=b.readLine();
					//stringa temporanea
					String temp = new String("");
					//indice per iterare il vettore
					int index=0;
					//contatore del carattere spazio ' '
					int space_count=0;
					//controllo sulla stringa
					if(s!=null) {
						//System.out.println(s);
						//iterazione della stringa che rappresenta la riga
						for( char c: s.toCharArray()) {
							//concatenzaione dei caratteri che vengono estratti
							temp+=c;
							//si incontra uno spazio
							if(c==' ') {
								//punteggio o nome
								if(space_count<3) {
									//si aumenta il contatore
									space_count++;
									//si memorizza del vettore
									score_line[index] = temp;
									//si resetta la variabile temporanea
									temp = "";
								}//fi
								else {
									//oggetto data, se sono gia' stati incontrati due caratteri spazio ' '
									//si memorizza nel vettore
									score_line[index] = temp;
								}//esle
								//indice del vettore aumentato
								if(index<2)index++;
							}//fi
						}//for
						//System.out.println("punti "+score_line[0]);
						//System.out.println("nome "+score_line[1]);
						//System.out.println("data "+score_line[2]);
						//TODO inserimento dei dati della riga analizzata nella struttura dati
						
						
						
					}//fi controllo
				}while(s!=null);//while
				//si chiude il processo di lettura
				fr.close();
			}//try
			catch(IOException e) {
				e.printStackTrace();
			}//catch
		}//esle

	}//scoreFileAnalysis
	
}//end ScoreUtility
