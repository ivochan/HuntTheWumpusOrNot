# Gioco del Wumpus

------

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

La mappa di gioco sarà costituita come una matrice di dimensioni ( 4 x 4 ).





## TODO 

Funzionalità da implementare:

- ~~creazione della classe Cell per implementare la generica casella della mappa di gioco;~~
- ~~creazione dell'enumerazioni per indicare i tipi di celle che possono costituire la mappa e i tipi di sensori;~~
- ~~creazione della struttura della mappa di gioco tramite la classe GameMap;~~ 
- correggere funzioni di probabilità per il popolamento della mappa;
- posizionamento dell'eroe nella mappa di gioco;
- specificare i valori del vettore dei sensori per ogni cella;
- integrare le funzionalità relative alla mappa nella classe GameMap;
- prevedere il popolamento della mappa in entrambe le modalità di gioco;
- testare il popolamento manuale della mappa di gioco;
- testare il popolamento automatico della mappa di gioco;

