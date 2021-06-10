# Gioco del Wumpus

## Introduzione

Questo progetto rappresenta l'implementazione, in codice **Java**, del videogioco conosciuto con il nome di "**Hunt the Wumpus**", realizzato nel 1972 da Gregory Yob.

Si tratta di un gioco di avventura che si svolge in un labirinto, le cui caratteristiche verranno generate in maniera casuale, strutturato come una composizione di stanze, comunicanti se adiacenti.

Il giocatore interpreta il ruolo di un *cacciatore*, che durante l'esplorazione del labirinto, dovrà sopravvivere alle insidie presenti, come il pozzo e individuare la tana del mostro, il *Wumpus*. Questo sarà possibile sfruttando le tracce disseminate per il labirinto, ovvero il cattivo odore emanato dal mostro, che giunge sino alle celle attigue alla sua tana (nel gioco originale si trattava di macchie di sangue).

La sola possibilità che si ha di sfuggire al mostro è quella di ucciderlo scoccando una freccia da una qualsiasi stanza adiacente a quella in cui è nascosto.

Inoltre, bisogna evitare le stanze con i pozzi, in cui si corre il rischio di caderci dentro, attigue a quelle in cui giunge la brezza (nel gioco originale era presente il muschio). Un ulteriore rischio, nel gioco di Yob, è costituito dai super-pipistrelli, che possono catturare il giocatore e rilasciarlo in una stanza differente, scelta in maniera casuale.

## Struttura del progetto

Il codice utilizzato per implementare questa versione del gioco andrà costituire il back-end di un altro progetto, ovvero la trasposizione di questo gioco in un'applicazione Android.

Quindi l'integrazione con la parte grafica, ovvero il front-end, realizzato prima in Android e poi in Kotlin, avverrà in seguito.

In questa versione del gioco è stata prevista, per il giocatore, la possibilità di interpretare, indifferentemente, il ruolo del **Wumpus** o del cacciatore, a cui ci si riferisce come **Avventuriero**.



### Campo di gioco

La mappa di gioco sarà costituita come una matrice di dimensioni ( 4 x 4 ).

Le celle potranno essere di questo tipo:

- **SAFE**, colore verde, cella accessibile e LIBERA;
- **DANGER**, colore blu, PIT, fossa in cui puo' cadere l'avventuriero, se si gioca nella modalità Eroe oppure TRAP, trappola, in cui può cadere il wumpus, se si gioca nella modalità Wumpus;
- **ENEMY**, colore rosso, AVVERSARIO, che può essere il Wumpus se si gioca nella modalità Eroe, altrimenti è il Cacciatore;
- **PG**,  colore arancione,  è il Giocatore, che sarà l'Avventuriero se si gioca nella modalità Eroe, altrimenti sarà il Wumpus;
- **AWARD** , colore giallo,  Premio;
- **FORBIDDEN**, colore nero, cella non accessibile, SASSO;

Per come è stato strutturato il gioco, gli elementi che, in totale, verranno posizionati sulla mappa di gioco sono:

- un mostro;
- un eroe;
- un premio;
- due pozzi (modalità hero_side) oppure due trappole (modalità !hero_side);
- da 0 a 2 di pietre, che rappresentano le celle non giocabili;

Per quanto riguarda il vettore dei sensori, valido per entrambe le modalità di gioco, sarà costituito, per ogni cella, da due elementi, quali:

*	**ENEMY_SENSE**;
*	**DANGER_SENSE**;



### Modalità di gioco

Per quanto riguarda la modalità di gioco, è stata prevista l'introduzione di due tipi differenti di giocatore, ovvero:

- il giocatore vero e proprio, *HumanPlayer*, pilotato dall'utente;
- il giocatore automatico,  *IAPlayer*, l'implementazione di un agente dotato di una semplice intelligenza artificiale;

Quindi, al momento dell'avvio del gioco, sarà l'utente finale a poter decidere in che modalità giocare, se nella versione classica, *Hero Side*, in cui l'avventuriero deve uccidere il Wumpus o se nella versione *Wumpus Side*, in cui dovrà impersonare proprio il mostro del gioco e riuscire a sopravvivere al cacciatore.

Inoltre, l'utente sarà nelle condizioni di poter decidere se vuole essere lui direttamente a controllare le mosse del suo personaggio oppure lasciare che la risoluzione del gioco e quindi l'esplorazione del labirinto siano affidate al giocatore automatico, ovvero un agente provvisto di intelligenza artificiale.



## Struttura delle classi

Di seguito verrà riporta una descrizione delle funzionalità realizzate per ciascuna delle classi che costituiscono il gioco.

### Cell

Questa classe implementa l'oggetto Cell, quello che costituisce ogni singola casella della matrice che realizza la mappa di gioco.

### Cell Status

Questa classe implementa una enumerazione che si occupa di definire le tipologie che può assumere l'oggetto Cell, nella mappa di gioco.

### Game Map

Questa classe si occupa di definire la struttura basilare del gioco, ovvero la mappa su cui si potrà muovere il personaggio giocabile.

### Game Configuration

Questa classe si occupa della generazione della mappa di gioco, popolandola di tutti i suoi elementi.

La struttura sarà di questo tipo:

```visualizzazione del terreno di gioco
      MAPPA				LEGENDA	
					-----------------------------------------
 ||S| |A| |S| |S||	| S = SPAZIO VUOTO | D = PERICOLO		|
 ||D| |S| |S| |S||	| E = NEMICO       | F = SPAZIO VIETATO |
 ||S| |S| |S| |E||	| P = GIOCATORE	   | A = PREMIO			|
 ||P| |S| |S| |D||	-----------------------------------------
```


### Starter

Questa classe si occupa di gestire le configurazioni necessarie all'esecuzione della sessione di gioco, come:

- la schermata introduttiva,

  ```	intro
  Link... Start-o!
  Cosa vuoi fare?
  Ecco la lista dei comandi:
  [q - quit] [g - game start] [s - score] [c - credits]```
  ```

- l'avvio della partita,

  ```schermata di gioco
     MAPPA									LEGENDA	
   ||X| |X| |X| |X||       --------------------------------------------
   ||X| |X| |X| |P||		| X = LUOGO DA VISITARE | O = LUOGO VISITATO |
   ||X| |X| |X| |X||		| P = GIOCATORE         | F = LUOGO VIETATO  |
   ||X| |X| |X| |X||       --------------------------------------------
   												 
   Comandi:										 
   												 
   w = sopra									 
   a = sinistra									 
   s = sotto									 
   d = destra									 
   i = interrompi partita		
   
   Inserisci comando :> 
  												
  ```

   

- la scelta della modalità di gioco ,

  ```scelta della modalità di gioco
  Preparazione del terreno di gioco....
  
  Ciao :3
  Dimmi di te...Sarai il cacciatore oppure il Wumpus?
  [h - cacciatore] [w - wumpus]
  
  ```

- l'inizializzazione della mappa,

- la risoluzione sfruttando il giocatore automatico.



### Direction

Questa classe definisce le possibili direzioni in cui il personaggio giocabile può muoversi nella mappa di gioco, tramite un'enenumerazione.

I movimenti consenti sono, quindi, quelli nelle quattro direzioni: 

- **UP**,
- **DOWN**,
- **LEFT**,
- **RIGHT**.

Il comando corrispondente verrà fornito da input, secondo la disposizione WASD, come carattere.

### Controller

Questa classe definisce i comandi di gioco, cioe' il modo in cui si potrà interagire con il programma, facendo muovere il personaggio giocabile, nelle direzioni consentite.

Bisogna:

 * verificare se la direzione in cui si vuole fare la mossa sia valida;
 * effettuare la mossa;
 * aggiornare la posizione del pg;
 * mostrare le informazioni dei sensori;
 * aggiornare la mappa di esplorazione;

### Game Translations

Questa classe contiene delle strutture dati che immagazzinano le informazioni da fornire all'utente, durante la sessione di gioco, sullo stato della partita, per ciascuna delle due modalità.

### Game Messages

Questa classe contiene delle stringhe, dei messaggi di testo, che forniscono informazioni all'utente, durante la sua interazione con lìapplicazione.

### Link Start

Questa classe implementa l'esecuzione dell'applicazione, permettendo all'utente di iniziare una sessione di gioco, chiudere il programma oppure ricominciare una nuova partita, dopo aver terminato la precedente.

### Score

Questa classe rappresenta l'oggetto Score, costituito da due attributi di classe significativi, ovvero:
 * il *nickname* del giocatore;
 * lo *score*, ovveroil punteggio raggiunto;

Il punteggio verrà calcolato tenendo conto delle seguenti penalità/ ricompense, in base alle azioni che saranno compiute dal personaggio giocabile durante la partita:

-  **+100**, è il valore che viene aggiunto al punteggio, se si trova la posizione del premio **AWARD**, *WIN = 100*;
- **-50**, è la penalità che si subisce se si il personaggio giocabile si posiziona in corrispondenza di un pericolo **DANGER**, sia questo il pozzo oppure la trappola, a seconda della modalità in cui si sta giocando, *TRAP = -50*;
- **-100**, è il valore che viene sottratto al punteggio complessivo se il giocatore incontra il nemico **ENEMY**, ovvero se viene ferito dal Wumpus, se sta impersonando l'Avventuriero, oppure se viene ucciso dal cacciatore, se si sta giocando nel ruolo del Wumpus, DEAD = -100;
- **-1**, è la penalità che viene assegnata per ogni mossa compiuta, cioè per ogni cella in  cui verrà scelto di posizionare il personaggio giocabile, *STEP = -1*;

### Score Memo

Questa classe si occupa di memorizzare il punteggio del giocatore al termine di ogni partita, utilizzando un file di testo, denominato *Punteggi.txt*. Alla chiusura dell'applicazione questo file verrà eliminato.

### Human Player

Questa classe implementa il giocatore controllato dall'utente.

### IA Player 

Questa classe implementa il giocatore automatico.



## TODO 

Funzionalità da implementare:

- ~~creazione della classe Cell per implementare la generica casella della mappa di gioco;~~
- ~~creazione dell'enumerazioni per indicare i tipi di celle che possono costituire la mappa e i tipi di sensori;~~
- ~~creazione della struttura della mappa di gioco tramite la classe GameMap;~~
- ~~inserire i super-pipistrelli come tipologia di cella della mappa (CellStatus.BAT);~~
- implementare il posizionamento casuale dei super pipistrelli nella mappa di gioco;
- prevedere lo spostamento del personaggio giocabile in una cella casuale, se in presenza di un super-pipistrello; 
- definire il meccanismo del punteggio, in base alle tipologie di celle esaminate;
- memorizzare il valore del punteggio acquisito via via durante la partita di gioco;
- sostituire i messaggi di errore e le stampe di debug con il sollevamento delle eccezioni;
- ~~correggere funzione di probabilità per il popolamento della mappa;~~        grazie a PsykeDady <3
- prevedere il popolamento della mappa in entrambe le modalità di gioco;
- ~~integrare le funzionalità relative alla mappa nella classe GameMap;~~
- ~~testare il popolamento manuale della mappa di gioco;~~
- ~~inserire metodi ed attributi che permettano di identificare la posizione della cella nella mappa;~~
- ~~testare l'indicizzazione della cella una volta messa nella mappa di gioco con i metodi accessori;~~
- ~~revisionare la struttura del vettore degli elementi di gioco in modo da avere a disposizione il numero di elementi, per ogni tipologia di cella, che devono essere posizionati sulla mappa;~~
- ~~strutturare il popolamento automatico della mappa di gioco game_map in modo che sia coerente con i dati degli elementi che devono essere inseriti;~~
- posizionare dell'eroe (o wumpus, nella modalita' !hero_side) nella mappa di gioco, occupandosi di:
  - ~~indicizzare le celle della cornice della matrice, ciascuna con un numero da 0 a 12;~~
  - ~~creare due vettori del doppio degli elementi che contengano, rispettivamente, gli indici identificativi della posizione di ogni cella nella matrice di gioco;~~
  - ~~generare un numero casuale che definisca in quale cella posizionare l'eroe, se etichettata come SAFE;~~
- ~~testare il metodo di posizionamento dell'eroe;~~
- ~~creare un metodo che inglobi il popolamento della mappa con il posizionamento dell'eroe, prevedendo il caso di ripopolare la mappa se non è stata trovata una collocazione idonea al personaggio giocabile;~~
- ~~specificare gli indici che descrivono la posizione della casella nella mappa, per ogni cella della matrice;~~
- ~~specificare i valori del vettore dei sensori per ogni cella vicina a quelle significative;~~
- ~~testare la coerenza delle informazioni dei sensori in base al contenuto delle celle della mappa;~~
- ~~testare il popolamento automatico della mappa di gioco;~~
- ~~creare subpackage secondo la notazione comunemente adottata;~~
- ~~eliminare il parametro int content dalla classe Cell;~~
- ~~revisionare la classe Cell eliminando la scelta della modalità di gioco;~~
- ~~testare tutti i metodi della classe Cell dopo averli revisionati;~~
- definire una classe Rules che determini le regole di gioco, quali:
  - creare una mappa di gioco che sia quadrata o rettangolare e specificare le dimensioni massime;
- ~~spostare metodo di inizializzazione mappa fuori dal costruttore, riempimento all'esterno della classe GameMap;~~
- ~~testare la classe revisionata GameMap, dopo aver spostato tutti i metodi che non si occupano della sua definizione, ma del suo riempimento;~~
- ~~modificare il costruttore di GameMap in modo che crei un'istanza le cui caratteristiche potranno essere specificate in seguito;~~
- ~~creare classe che si occupi delle inizializzazioni della struttura di gioco Starter;~~
- ~~generalizzare la enum CellStatus, in modo da specificare in seguito chi sia il pg, chi il nemico e quali trappole si possono incontrare, in base alla modalità di gioco;~~
- ~~implementazione di un unica enum SenseStatus, eliminando la distinzione tra le due modalità di gioco;~~
- creare delle traduzioni degli elementi di gioco, in base alla modalità scelta in una classe apposita:
  - ~~mappa delle traduzioni se il pg è il cacciatore;~~
  - ~~mappa delle traduzioni se il pg è il wumpus;~~
- ~~definire la classe Starter che si occupa delle inizializzazioni della struttura di gioco;~~
- dotare la classe Starter di tutti i metodi che consentano:
  - ~~il posizionamento degli elementi di gioco sulla mappa;~~
  - ~~il posizionamento del pg sulla mappa;~~
  - ~~l'aggiornamento del vettore dei sensori;~~
- ~~testare la classe Starter e verificare il riempimento automatico della mappa di gioco;~~
- ~~verificare l'assegnamento dei sensori dopo aver popolato la mappa;~~
- creare classe che si occupi dell'avvio del gioco LinkStart:
  - ~~inizializzare la mappa di esplorazione;~~
  - ~~implementare ciclo di avvio della sessione di gioco;~~
  - ~~gestisca la ricezione di un comando;~~
- implementare la sessione di gioco, in cui prevedere:
  - ~~la scelta dellla modalità di gioco;~~
  - ~~l'inizio della partita;~~
  - ~~la possibilità di terminare il gioco;~~
  - ~~la possibilità di effettuare delle mosse;~~~~
  - ~~aggiornare la mappa di esplorazione, tenendo traccia delle celle già visitate;~~
  - ~~conoscere la posizione corrente del pg;~~
  - ~~il calcolo del punteggio;~~
  - movimento nella mappa manuale (tramite metodo accessorio)
  - ~~movimento nella mappa da input (su decisione dell'utente)~~
    - prevedere e gestire i possibili casi d'errore nell'acquisizione della mossa da input;
  - ~~fornire dei messaggi esplicativi della situazione attuale nel gioco;~~
- ~~effettuare il controllo della mossa di gioco lavorando su un'istanza di GameMap le cui info si prendono dai metodi accessori;~~
- definire diversi metodi di riempimento della mappa in modo che si possa scegliere se:
  - ~~posizionare il pg sulla cornice;~~
  - posizionare il pg al centro della mappa;
  - posizionare il pg in un qualsiasi punto della mappa;
  - poter scegliere tra diverse funizioni di probabilità per il riempimento della mappa;
- definire il controller di gioco che permette i movimenti del personaggio giocabile nella mappa, ad esempio:
  - ~~definire la enum che indica le direzioni in cui è possibile effettuare una mossa;~~
  - prevedere i casi di fine partita:
    - ~~il pg perde se incontra il nemico;~~
    - il pg vince se:
      - ~~trova l'uscita segreta, se Wumpus;~~
      - ~~trova l'oro, se è il Cacciatore;~~
  - controllare che la mossa sia valida:
    - ~~in termini di indici che identificano la posizione della cella di arrivo nella mappa di gioco;~~
    - ~~in termini di adiacenza alla cella che rappresenta la posizione corrente del pg;~~
    - restituire una variabile che descriva lo stato della mossa, ovvero:
      - ~~se valida;~~
      - ~~se errata;~~
      - ~~se il pg è morto;~~
  - effettuare la mossa scelta, nella direzione specificata:
    - ~~in base alla direzione dedurre gli indici della nuova cella;~~
    - ~~effettuare la mossa inserendo la cella visitata nella mappa di esplorazione nota al giocatore;~~
    - ~~effettuare la mossa restituendo le variabili che descrivono il contenuto dei sensori della cella di arrivo;~~
    - ~~richiedere la mossa successiva;~~
    - ~~personalizzare i messaggi del gioco in base alla modalità;~~
  - ~~definire l'azione di colpire il nemico, scegliendo la direzione~~;
- ~~correggere il posizionamento del pg, controllando che il passaggio dalla sua cella non sia bloccato~~;
- definire la classe che definisce alcune regole di gioco Rules;
- ~~aggiornare path di salvataggio del file del punteggio con la variabile d'ambiente~~;
- ~~testare la modalità di gioco lato utente~~;
- ordinare i punteggi in maniera decrescente;
- controllare che il posizionamento del nemico sia realizzato;
- realizzare metodo di posizionamento del pg al centro della mappa;
- fare in modo che il wumpus sia posizionato al centro, se scelto come pg;
- ~~definire l'interfaccia Agent, che delinea la struttura del giocatore automatico;~~
- ~~definire la classe astratta "BasicAgent";~~
- definire il giocatore automatico "RandomAgent":
  - si esamina la griglia di gioco, la "situazione iniziale" dell'ambiente circostante;
  - si sceglie la mossa da effettuare in base allo stato dei sensori (**TODO** da testare);
  - ad ogni passo, si salvano gli indici della cella che è stata visitata;
  - al termine della partita mostra il percorso effettuato;
- definire il giocatore automatico "ScoreAgent";
- testare la modalità di gioco automatico;

