package game.session.score;
//serie di import
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeMap;
/** class ScoreUtility
 * questa classe serve per interagire con il file di testo su
 * cui verranno memorizzati tutti i punteggi ottenuti dall'utente
 * @author ivonne
 */
public class ScoreUtility {
	
	//formato della data
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

	//##### metodi per interagire con il file di testo #####
	
	/** metodo createScoreFile(String): void
	 * questo metodo crea il file di memorizzazione dei punteggi
	 * utilizzando come path quello specificato dalla variabile
	 * di classe.
	 * @param path: String, nome del file da creare
	 */
	public static void createScoreFile(String path) {
		//controllo sul path
		if(path==null) throw new IllegalArgumentException("path o nome del file non valido");
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
		
	/** metodo writeScoreFile(String, Score): void
	 * questo metodo scrive il punteggio nel file di testo
	 * creato per il salvataggio 
	 * @param score: String, punteggio da memorizzare;
	 * @param path: String, nome del file da creare
	 */
	public static void writeScoreFile(String path, String score) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
		//controllo sull'oggetto punteggio
		if(score==null)throw new IllegalArgumentException("punteggio non valido");
		//si preleva il file
		File f = new File(path);
		//flag che indica di non sovrascrivere le righe
		boolean append = true;
		//lettura
		try {
			//si istanzia la classe per la scrittura
			FileWriter fw = new FileWriter(f, append);
			//si scrive la nuova riga nel file
			fw.write(score+"\n");
			//si svuota e si chiude il buffer di scrittura
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}//writeScoreFile(Score)
		
	/** metodo readScoreFile(String): void
	 * questo metodo accede al contenuto del file di testo in cui
	 * sono stati memorizzati i punteggi di gioco e lo stampa a video
	 * @param path: String, nome del file da creare
	 */
	public static void readScoreFile(String path) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
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
	
	/** metodo deleteScoreFile(String): void
	 * questo metodo viene utilizzato per cancellare
	 * il file contenente i punteggi
	  * @param path: String, nome del file da creare
	 */
	public static void deleteScoreFile(String path) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
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
	
	/** metodo saveScore(String,String): void
	 * questo metodo si occupa della creazione
	 * e della scrittura del file dei punteggi, 
	 * quando e' richiesto di memorizzare quello
	 * ricevuto come parametro.
	 * @param path: String, nome del file da creare
	 * @param score: String, punteggio da memorizzare
	 */
	public static void saveScore(String path, String score) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
		//controllo sull'oggetto punteggio da salvare
		if(score==null)throw new IllegalArgumentException("punteggio non valido");
		//creazione del file
		createScoreFile(path);
		//scrittura del punteggio
		writeScoreFile(path, score);
	}//saveScore(String,String)
	
	//##### metodi gestione del file dei punteggi #####
	
	/** metodo scoreFileAnalysis()
	 * metodo per analizzare il contenuto di ogni riga del file dei punteggi
	 * estrapolare il punteggio, il nome del giocatore e la data in cui e' stato registrato
	 * @param path: String, nome del file da creare
	 * @param map: TreeMap<Score, String> mappa che contiene tutti i punteggi estratti dal file
	 */
	public static void scoreFileAnalysis(String path, TreeMap<Score, String> map) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
		//controllo sulla mappa
		if(map==null)throw new IllegalArgumentException("struttura dati inesistente");
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
							temp+=Character.toString(c);
							//si incontra uno spazio
							if(c== ' ') {
								//punteggio o nome
								if(space_count<2) {
									//si aumenta il contatore
									space_count++;
									//si memorizza del vettore
									score_line[index] = new String(temp.replaceAll(" ", ""));
									//si resetta la variabile temporanea
									temp = new String();
								}//fi
								else {
									//oggetto data, se sono gia' stati incontrati due caratteri spazio ' '
									//si memorizza nel vettore
									score_line[index] = new String(temp.replaceAll(" ", ""));
								}//esle
								//indice del vettore aumentato
								if(index<2)index++;
							}//fi
							else {
								//se fine riga
								if(index==2) {
									//si accoda l'ultima sequenza della data, cioe' l'orario
									score_line[index]=new String(temp);
								}//fi
							}//esle
						}//for
						//System.out.println("punti "+score_line[0]);
						//System.out.println("nome "+score_line[1]);
						//System.out.println("data "+score_line[2]);
						//punteggio
						Integer score_value = Integer.parseInt(score_line[0]);
						//nome del giocatore
						String name = new String(score_line[1]);
						//data: String -> Date
						Date d = sdf.parse(score_line[2]);
						//oggetto nella mappa: chiave		
						Score sk =new Score(score_value,name,d);
						//oggetto nella mappa: valore
						String value = new String(score_line[1]+" "+score_line[2]);
						//inserimento nella mappa
						map.put(sk, value);						
					}//fi controllo
				}while(s!=null);//while
				//si chiude il processo di lettura
				fr.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//esle
	}//scoreFileAnalysis
	
	/** metodo updateScoreFile(): void
	 * questo emtodo e' utilizzato per aggiornare il file dei punteggi al termine di ogni
	 * partita, in modo da ordinarli in maniera decrescente.
	 * @param path: String, nome del file dei punteggi
	 */
	public static void updateScoreFile(String path) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
		//si crea il comparator da sfruttare nell'ordinamento dei punteggi
		Comparator<Score> comparator = new ScoreComparator();
		//si crea la struttura dati che memorizzera' i dati del file
		TreeMap<Score, String> scores_map = new TreeMap<Score, String>(comparator);
		//si analizza il file dei punteggi
		scoreFileAnalysis(path,scores_map);
		//si cancella il file dei punteggi
		deleteScoreFile(path);
		//si ricrea il file dei punteggi
		createScoreFile(path);
		//ordine decrescente
		for(Score key: scores_map.descendingKeySet()) {
			//si stampa la coppia chiave valore
			String line = new String(key.getScore()+ " "+scores_map.get(key));
			//scrittura su file
			writeScoreFile(path,line);
		}
		//si legge il file dei punteggi
		readScoreFile(path);
	}//updateScoreFile()
	
	/** metodo extractScoreData(String, LinkedList<String<): void
	 * questo metodo si occupa di leggere tutto il file dei punteggi ed inserire
	 * ogni riga come nodo della lista dei punteggi
	 * @param path: String, path del file
	 * @param score_list: LinkedList<String>, lista che contiene i punteggi
	 */
	public static void extractScoreData(String path, LinkedList<String> score_list) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
		//controllo sul parametro in cui memorizzare i punteggi
		if(score_list==null)throw new IllegalArgumentException("lista dei punteggi nulla");
		//si preleva il file dei punteggi
		File f = new File(path);
		//si controlla se esiste
		if(!f.exists()) {
			System.err.println("File inesistente!");
			return;
		}//fi
		//inizio della lettura del file
		try {
			//si istanzia la classe per la lettura
			FileReader fr = new FileReader(f);
			//buffer
			BufferedReader br = new BufferedReader(fr);
			//variabile ausiliaria
			String line=new String();
			//si cicla fino a quando non si trova una linea vuota
			while ((line = br.readLine()) != null) {
				//DEBUGG
				//System.out.println(line);
				//si inserisce la linea nella lista
				score_list.add(line);
			}//while
			//si chiude il processo di lettura
			fr.close();
		}//end try
		catch(IOException e) {
			e.printStackTrace();
		}//end catch	
	}//extractScoreData
	
	/** metodo scoreLineAnalysis()
	 * metodo per analizzare il contenuto di una generica riga del file dei punteggi
	 * per estrapolare il punteggio ed il nome del giocatore
	 * @param score_line: String, riga contenente i dati del punteggio di interesse.
	 * @return score_vector: String[], vettore che contiene il punteggio analizzato
	 */
	public static String[] scoreLineAnalysis(String score_line) {
		//controllo sulla riga
		if(score_line==null)throw new IllegalArgumentException("stringa punteggio non valida");
		//vettore che conterra' gli elementi ottenuti dal parsing di ogni riga del file
		String [] score_vector = new String[3];	
		//stringa temporanea
		String temp = new String("");
		//indice per iterare il vettore
		int index=0;
		//contatore del carattere spazio ' '
		int space_count=0;
		//DEBUGG
		System.out.println(score_line);
		//iterazione della stringa che rappresenta la riga
		for( char c: score_line.toCharArray()) {
			//concatenzaione dei caratteri che vengono estratti
			temp+=Character.toString(c);
			//si incontra uno spazio
			if(c== ' ') {
				//punteggio o nome
				if(space_count<2) {
					//si aumenta il contatore
					space_count++;
					//si memorizza del vettore
					score_vector[index] = new String(temp.replaceAll(" ", ""));
					//si resetta la variabile temporanea
					temp = new String();
				}//fi
				else {
					//oggetto data, se sono gia' stati incontrati due caratteri spazio ' '
					//si memorizza nel vettore
					score_vector[index] = new String(temp.replaceAll(" ", ""));
				}//esle
				//indice del vettore aumentato
				if(index<2)index++;
			}//fi
			else {
				//se fine riga
				if(index==2) {
					//si accoda l'ultima sequenza della data, cioe' l'orario
					score_vector[index]=new String(temp);
				}//fi
			}//esle
		}//for
		//System.out.println("punti "+score_vector[0]);
		//System.out.println("nome "+score_vector[1]);
		//System.out.println("data "+score_vector[2]);
		//si restituisce il vettore
		return score_vector;	
	}//scoreFileAnalysis
	
	public static boolean scoreLineCheck(String score_line) {
		//controllo sulla riga
		if(score_line==null)throw new IllegalArgumentException("stringa punteggio non valida");
		//vettore che conterra' gli elementi ottenuti dal parsing di ogni riga del file
		String [] score_vector = new String[3];	
		//stringa temporanea
		String temp = new String("");
		//indice per iterare il vettore
		int index=0;
		//contatore del carattere spazio ' '
		int space_count=0;
		//DEBUGG
		System.out.println(score_line);
		//iterazione della stringa che rappresenta la riga
		for( char c: score_line.toCharArray()) {
			//concatenzaione dei caratteri che vengono estratti
			temp+=Character.toString(c);
			//si incontra uno spazio
			if(c== ' ') {
				//punteggio o nome
				if(space_count<2) {
					//si aumenta il contatore
					space_count++;
					//si memorizza del vettore
					score_vector[index] = new String(temp.replaceAll(" ", ""));
					//si resetta la variabile temporanea
					temp = new String();
				}//fi
				else {
					//oggetto data, se sono gia' stati incontrati due caratteri spazio ' '
					//si memorizza nel vettore
					score_vector[index] = new String(temp.replaceAll(" ", ""));
				}//esle
				//indice del vettore aumentato
				if(index<2)index++;
			}//fi
			else {
				//se fine riga
				if(index==2) {
					//si accoda l'ultima sequenza della data, cioe' l'orario
					score_vector[index]=new String(temp);
				}//fi
			}//esle
		}//for
		//ystem.out.println("punti "+score_vector[0]);
		if(!score_vector[0].matches("\\-?([0-9]|[1-9][0-9]{1,2})")) {
			System.err.println("punti errati");
			return false;
		}
		//System.out.println("nome "+score_vector[1]);
		if(!score_vector[1].matches("[\\w\\W]*")) {
			System.err.println("nome errato");
			return false;
		}
		//System.out.println("data "+score_vector[2]);
		if(!score_vector[2].matches("([0123][0-9])\\-(0[1-9]|1[0-2])\\-([1-9][0-9]{3})\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]\\.[0-9]{3})")) {
			System.err.println("data errata");
			return false;
		}
		//si restituisce il vettore
		return true;	
	}//scoreFileAnalysis
	
	/** metodo scoreFileCheck()
	 * metodo per analizzare il contenuto di tutte le righe del file dei punteggi
	 * in modo da controllare che la sua formattazione sia corretta, ovvero nel 
	 * formato: punteggio-spazio-nome-spazio-data sotto forma di striga-fine riga
	 * per estrapolare il punteggio ed il nome del giocatore
	 * @param path: String, path del file dei punteggi.
	 * @return boolean: true, se il controllo e' andato a buon fine, false altrimenti.
	 */
	public static boolean scoreFileCheck(String path) {
		//si crea una lista in cui ogni nodo sara' una riga del file
		LinkedList<String> list = new LinkedList<>();
		//variabile ausiliaria
		boolean check = true;
		//si riempie questa lista estraendo i dati del punteggio
		extractScoreData(path,list);
		//si analizza ogni nodo della lista
		for(String s: list) {
			//analisi della riga selezionata con test tramite regex
			check = scoreLineCheck(s);
		}//for
		return check;
	}//scoreFileCheck(String)
	
	//##### metodi per la gestione del file del punteggio attuale #####
	
	/** metodo extractCurrentScoreData(String, LinkedList<String<): void
	 * questo metodo si occupa di leggere il file dei punteggio attuale
	 * e restituire l'unica riga come stringa
	 * @param path: String, path del file
	 * @param current_score: String, stringa che contiene il punteggio attuale
	 */
	public static String extractCurrentScoreData(String path) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
		//variabile da restituire
		String current_score = new String();
		//si preleva il file dei punteggi
		File f = new File(path);
		//si controlla se esiste
		if(!f.exists()) {
			System.err.println("File inesistente!");
			return "";
		}//fi
		//inizio della lettura del file
		try {
			//si istanzia la classe per la lettura
			FileReader fr = new FileReader(f);
			//buffer
			BufferedReader br = new BufferedReader(fr);
			//si estrae la prima linea del file
			current_score = br.readLine();
			//si chiude il buffer
			br.close();
			//si chiude il processo di lettura
			fr.close();
		}//end try
		catch(IOException e) {
			e.printStackTrace();
		}//end catch	
		return current_score;
	}//extractCurrentScoreData
	
	/** metodo saveCurrentScore(Score): void
	 * questo metodo si occupa della creazione
	 * e della scrittura del file del punteggio attuale, 
	 * quando e' richiesto di memorizzare l'ultimo punteggio
	 * ottenuto, ricevuto come parametro.
	 * @param path: String, nome del file da creare
	 * @param score: String, punteggio da memorizzare
	 */
	public static void saveCurrentScore(String path, String current_score) {
		//controllo sul parametro che indica il nome del file
		if(path==null)throw new IllegalArgumentException("path o nome del file non valido");
		//controllo sull'oggetto punteggio da salvare
		if(current_score==null)throw new IllegalArgumentException("punteggio non valido");
		//creazione del file
		createScoreFile(path);
		//scrittura del punteggio
		//si preleva il file
		File f = new File(path);
		//blocco try-catch
		try {
			//si istanzia la classe per la scrittura
			FileWriter fw = new FileWriter(f);
			//si scrive il punteggio nel file
			fw.write(current_score+'\n');
			//si svuota e si chiude il buffer di scrittura
			fw.flush();
			fw.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}//saveCurrentScore(String,String)
	
	
}//end ScoreUtility
