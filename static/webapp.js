/*
* Crea un namespace per i test, vedi note di "grafici" nel file HTML
* Nota: le doppie graffe indicano la creazione di un dizionario (mappa chiave:valore)
*       che in JS viene usato anche per definire un oggetto.
*/
var test = test || {};

/*
 * Logga un messaggio, ove possibile
 * Si può chiamare sia con logX(msg) che con test.log(msg)
 */
var logX = test.log = function(msg) {
    if(typeof Android !== 'undefined') {
        // se disponibile Android
        Android.showToast(msg);
    } else if(typeof console !== 'undefined') {
        // se disponibile la console
        console.log(msg);
    } else{
        // alert generico
        alert(msg);
    }
};

/*
 * Data una generica funzione "fun" la esegue misurando il tempo impiegato
 */
test.profila = function(fun) {
    var startTime = test.getTime();
    test.log(fun());
    var endTime = test.getTime();
    
    return endTime - startTime;
};

/*
 * Ricava il timestamp attuale
 */
test.getTime = function() {
    return new Date().getTime();
};

/*
 * Funzione di test, calcola il valore di pi greco a precisione variabile
 */
test.pi = function() {
    var pos = 0;
    var size = 100000;

    for (var i = 0; i < size; i++) {
        var x = Math.random() * 2 - 1;
        var y = Math.random() * 2 - 1;
        if ((x * x + y * y) < 1) {
            pos++
        }
    }

    return 4.0 * pos / size;
};
