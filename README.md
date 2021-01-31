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

- **SAFE**, colore verde, cella accessibile e LIBERA, con il valore intero indicativo 0;
- **PIT**, colore blu, FOSSA in cui puo' cadere l'avventuriero, se si gioca nella modalità Eroe (hero_side = true), con il valore intero indicativo 1;
- **WUMPUS**, colore rosso, MOSTRO, con il valore intero indicativo 2;
- **HERO**, colore arancione, AVVENTURIERO, con il valore intero indicativo 3;
- **GOLD**, colore giallo, ORO, con il valore intero indicativo 4;
- **DENIED**, colore nero, cella non accessibile, SASSO, con il valore intero indicativo 5;
- **TRAP**, colore viola, trappola in cui può cadere il wumpus, se si gioca nella modalità Wumpus (hero_side = false), con il valore intero indicativo 6;

Per come è stato strutturato il gioco, gli elementi che, in totale, verranno posizionati sulla mappa di gioco sono:

- un mostro;
- un eroe;
- un lingotto d'oro;
- due pozzi (modalità hero_side) oppure due trappole (modalità !hero_side);
- un numero casuale da 0 a 4 di pietre, che rappresentano le celle non giocabili;

Per quanto riguarda il vettore dei sensori, i cui elementi sono stati rappresentati come valori di tipo boolean, descritti da un'enumerazione, potrà essere definito nel modo seguente:

- *SenseHStatus* , ovvero il vettore dei sensori nella modalità Eroe, sarà costituito, per ogni cella, da due elementi, quali:
   *	STINK, colore marrone, odore del Wumpus, con indice di cella 0;
   *	BREEZE, colore bianco, brezza del pozzo, icon indice di cella 1;
- *SenseWStauts*, ovvero il vettore dei sensori nella modalità Wumpus,  sarà costituito, per ogni cella, da due elementi, quali:
  - CREAK, colore marrone, scricchiolio dovuto al passo dell'eroe, con indice di cella 0;
  - SWISH, colore bianco, fruscio di foglie vicino la trappola, con indice di cella 1;



### Modalità di gioco

Per quanto riguarda la modalità di gioco, è stata prevista l'introduzione di due tipi differenti di giocatore, ovvero:

- il giocatore vero e proprio, *HumanPlayer*, pilotato dall'utente;
- il giocatore automatico,  *IAPlayer*, l'implementazione di un agente dotato di una semplice intelligenza artificiale;

Quindi, al momento dell'avvio del gioco, sarà l'utente finale a poter decidere in che modalità giocare, se nella versione classica, *Hero Side*, in cui l'avventuriero deve uccidere il Wumpus o se nella versione in cui dovrà impersonare proprio il mostro del gioco e riuscire a sopravvivere al cacciatore.

Inoltre, l'utente sarà nelle condizioni di poter decidere se vuole essere lui direttamente a controllare le mosse del suo personaggio oppure lasciare che la risoluzione del gioco e quindi l'esplorazione del labirinto venga affidata al giocatore automatico, provvisto di intelligenza artificiale.





## TODO 

Funzionalità da implementare:

- ~~creazione della classe Cell per implementare la generica casella della mappa di gioco;~~
- ~~creazione dell'enumerazioni per indicare i tipi di celle che possono costituire la mappa e i tipi di sensori;~~
- ~~creazione della struttura della mappa di gioco tramite la classe GameMap;~~
- inserire i super-pipistrelli come tipologia di cella della mappa (CellStatus.BAT);
- prevedere lo spostamento del personaggio giocabile in una cella casuale, se in presenza di un super-pipistrello; 
- definire il meccanismo del punteggio, in base alle tipologie di celle esaminate;
- memorizzare il valore del punteggio acquisito via via durante la partita di gioco;
- sostituire i messaggi di errore e le stampe di debug con il sollevamento delle eccezioni;
- ~~correggere funzione di probabilità per il popolamento della mappa;~~ grazie a PsykeDady :3
- prevedere il popolamento della mappa in entrambe le modalità di gioco;
- ~~integrare le funzionalità relative alla mappa nella classe GameMap;~~
- ~~testare il popolamento manuale della mappa di gioco;~~
- ~~inserire metodi ed attributi che permettano di identificare la posizione della cella nella mappa;~~
- ~~testare l'indicizzazione della cella una volta messa nella mappa di gioco con i metodi accessori;~~
- revisionare la struttura del vettore degli elementi di gioco in modo da avere a disposizione il numero di elementi, per ogni tipologia di cella, che devono essere posizionati sulla mappa;
- strutturare il popolamento automatico della mappa di gioco game_map in modo che sia coerente con i dati degli elementi che devono essere inseriti;
- posizionare dell'eroe nella mappa di gioco;
- specificare gli indici che descrivono la posizione della casella nella mappa, per ogni cella della matrice;
- specificare i valori del vettore dei sensori per ogni cella;
- testare la coerenza delle informazioni dei sensori in base al contenuto delle celle della mappa;
- testare il popolamento automatico della mappa di gioco;
- definire il controller di gioco che permette i movimenti del personaggio giocabile nella mappa;
- definire il giocatore "HumanPlayer",
- dotare la classe HumanPlayer dei metodi necessari allo spostamento del personaggio nella mappa;
- tenere traccia delle caselle visitate in una copia della mappa di gioco, denominata exploration_map;
- utilizzare il flag isVisited per identificare le celle che sono state esaminate e copiarle nella exploration_map, nella stessa posizione occupata nella game_map;
- definire i meccanismi di gioco a disposizione di HumanPlayer come:
  -  l'inizio della partita, 
  - il calcolo del punteggio, 
  - la scelta della mossa
  - l'esplorazione dell'ambiente di gioco tramite il vettore dei sensori
  - il termine della partita
  - movimento nella mappa manuale (tramite metodo accessorio)
  - movimento nella mappa da input (su decisione dell'utente)
  - prevedere e gestire i possibili casi d'errore nell'acquisizione della mossa da input
- definire il giocatore automatico "IAPlayer"
- dotare la classe "IAPlayer" dei metodi necessari per:
  - esaminare l'ambiente di gioco
  - acquisire le informazioni dal vettore dei sensori
  - stabilire la mossa da effettuare in base ai dati acquisiti
- testare la modalità di gioco lato utente
- testare la modalità di gioco automatico