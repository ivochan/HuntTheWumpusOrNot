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
- correggere funzioni di probabilità per il popolamento della mappa;
- posizionamento dell'eroe nella mappa di gioco;
- specificare i valori del vettore dei sensori per ogni cella;
- ~~integrare le funzionalità relative alla mappa nella classe GameMap;~~
- prevedere il popolamento della mappa in entrambe le modalità di gioco;
- ~~testare il popolamento manuale della mappa di gioco;~~
- testare il popolamento automatico della mappa di gioco;

