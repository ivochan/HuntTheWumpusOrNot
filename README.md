# Gioco del Wumpus

## Introduzione

Questo progetto rappresenta l'implementazione, in codice **Java**, del videogioco conosciuto con il nome di "**Hunt the Wumpus**", realizzato nel 1972 da Gregory Yob.

Si tratta di un gioco di avventura che si svolge in un labirinto, le cui caratteristiche verranno generate in maniera casuale, strutturato come una composizione di stanze, comunicanti se adiacenti.

Il giocatore interpreta il ruolo di un *cacciatore*, che durante l'esplorazione del labirinto, dovrà sopravvivere alle insidie presenti, come il pozzo e individuare la tana del mostro, il *Wumpus*. Questo sarà possibile sfruttando le tracce disseminate per il labirinto, ovvero il cattivo odore emanato dal mostro, che giunge sino alle celle attigue alla sua tana (nel gioco originale si trattava di macchie di sangue).

La sola possibilità che si ha di sfuggire al mostro è quella di ucciderlo scoccando una freccia da una qualsiasi stanza adiacente a quella in cui è nascosto.

Inoltre, bisogna evitare le stanze con i pozzi, in cui si corre il rischio di caderci dentro, attigue a quelle in cui giunge la brezza (nel gioco originale era presente il muschio). Un ulteriore rischio, nel gioco di Yob, è costituito da dei super-pipistrelli, che possono catturare il giocatore e rilasciarlo in una stanza differente, scelta in maniera casuale.

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
- un lingotto d'oro;
- due pozzi (modalità hero_side) oppure due trappole (modalità !hero_side);
- un numero casuale da 0 a 4 di pietre, che rappresentano le celle non giocabili;

Per quanto riguarda il vettore dei sensori, i cui elementi sono stati rappresentati come valori di tipo boolean, descritti da un'enumerazione, potrà essere definito nel modo seguente:

*SenseStatus* , ovvero il vettore dei sensori nella modalità Eroe, sarà costituito, per ogni cella, da due elementi, quali:

*	ENEMY_SENSE;
*	DANGER_SENSE;



### Modalità di gioco

Per quanto riguarda la modalità di gioco, è stata prevista l'introduzione di due tipi differenti di giocatore, ovvero:

- il giocatore vero e proprio, *HumanPlayer*, pilotato dall'utente;
- il giocatore automatico,  *IAPlayer*, l'implementazione di un agente dotato di una semplice intelligenza artificiale;

Quindi, al momento dell'avvio del gioco, sarà l'utente finale a poter decidere in che modalità giocare, se nella versione classica, *Hero Side*, in cui l'avventuriero deve uccidere il Wumpus o se nella versione in cui dovrà impersonare proprio il mostro del gioco e riuscire a sopravvivere al cacciatore.

Inoltre, l'utente sarà nelle condizioni di poter decidere se vuole essere lui direttamente a controllare le mosse del suo personaggio oppure lasciare che la risoluzione del gioco e quindi l'esplorazione del labirinto venga affidata al giocatore automatico, provvisto di intelligenza artificiale.



## Struttura delle classi

Di seguito verrà riporta una descrizione delle funzionalità realizzate per ciascuna delle classi che costituiscono il gioco.



### Cell

Questa classe implementa l'oggetto Cella, quello che costituisce ogni singola casella della matrice che realizza la mappa di gioco.



### Game Map

Questa classe si occupa di definire la struttura basilare del gioco, ovvero la mappa su cui si potrà muovere il personaggio giocabile.



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
  - gestisca la ricezione di un comando;
- implementare la sessione di gioco, in cui prevedere:
  - la scelta dellla modalità di gioco;
  - l'inizio della partita;
  - la possibilità di terminare il gioco;
  - la possibilità di effettuare delle mosse;
  - aggiornare la mappa di esplorazione, tenendo traccia delle celle già visitate;
  - conoscere la posizione corrente del pg;
  - il calcolo del punteggio;
  - movimento nella mappa manuale (tramite metodo accessorio)
  - movimento nella mappa da input (su decisione dell'utente)
    - prevedere e gestire i possibili casi d'errore nell'acquisizione della mossa da input;
  - fornire dei messaggi esplicativi della situazione attuale nel gioco;
- effettuare il controllo della mossa di gioco lavorando su un'istanza di GameMap le cui info si prendono dai metodi accessori;
- definire diversi metodi di riempimento della mappa in modo che si possa scegliere se:
  - ~~posizionare il pg sulla cornice;~~
  - posizionare il pg al centro della mappa;
  - posizionare il pg in un qualsiasi punto della mappa;
  - poter scegliere tra diverse funizioni di probabilità per il riempimento della mappa;
- definire il controller di gioco che permette i movimenti del personaggio giocabile nella mappa, ad esempio:
  - ~~definire la enum che indica le direzioni in cui è possibile effettuare una mossa;~~
  - realizzare i metodi accessori che consentono di avere informazioni sulla enum;
  - controllare che la mossa sia valida:
    - in termini di indici che identificano la posizione della cella di arrivo nella mappa di gioco;
    - in termini di adiacenza alla cella che rappresenta la posizione corrente del pg;
    - restituire una variabile che descriva lo stato della mossa, ovvero:
      - se valida;
      - se errata;
      - se il pg è morto;
  - effettuare la mossa scelta, nella direzione specificata:
    - in base alla direzione dedurre gli indici della nuova cella;
    - effettuare la mossa inserendo la cella visitata nella mappa di esplorazione nota al giocatore;
- definire la classe che definisce alcune regole di gioco Rules;
- definire il giocatore "HumanPlayer";
- definire il giocatore automatico "IAPlayer"
- dotare la classe "IAPlayer" dei metodi necessari per:
  - esaminare l'ambiente di gioco
  - acquisire le informazioni dal vettore dei sensori
  - stabilire la mossa da effettuare in base ai dati acquisiti
- testare la modalità di gioco lato utente;
- testare la modalità di gioco automatico;