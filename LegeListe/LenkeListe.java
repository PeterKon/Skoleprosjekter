import java.util.Iterator;

//Dette er en abstrakt klasse som de andre lenkelistene skal arve metoder
//og parametre fra. Den implementerer Liste<T> som extends Iterable<T>,
//dvs at de andre lenkelistene ogsaa arver dette.
@SuppressWarnings("unchecked")
public abstract class LenkeListe<T> implements Liste<T> {


    //Vi har ett hode og en hale. Vi bruker storrelse for aa holde orden paa
    //antall Noder i lenken.
    protected Node hode;
    protected Node hale;
    protected int storrelse = 0;


    //Returnerer antall noder i lenken.
    public int storrelse() {
        return storrelse;
    }


    //Setter hode-pekeren til noden som sendes inn.
    public void settHode(Node temp) {
        hode = temp;
    }


    //Returnerer true dersom det er ingen noder i lenken.
    public boolean erTom() {
        return hode == null;
    }


    //En abstrakt metode som vi vil be de andre listene implementere.
    public abstract void settInn(T element);


    //Hvis hode == null eksisterer ingen T og vi returnerer null. Om vi har
    //innhold i listen, saa henter vi T fra hodet, setter hodepekeren til
    //aa peke paa neste element og dekrementerer storrelsen. Denne metoden
    //gjelder for stabel og koe og OL.
    public T fjern() {
        T returnInnhold;
        if(this.hode == null) {
            return null;
        } else {
            returnInnhold = this.hode.hentInnhold();
            settHode(hode.hentNeste());
            this.storrelse--;
            return returnInnhold;
        }
    }


    //Iterator over lenkelisten.
    public class ListeIterator implements Iterator<T> {

        //Iteratoren har en peker. Den starter paa hodet. hasNext sjekker om
        //neste Node er null, og next henter det generiske innholdet T fra
        //neste Node og returnerer det.
        private Node peker;

        public ListeIterator() {
            peker = hode;
        }

        public boolean hasNext() {
            return peker != null;
        }

        //Den setter ikke peker som neste for her, dvs at dersom den blir null
        //kan vi sjekke det diretke i hasNext.
        public T next() {
            T temp = peker.hentInnhold();
            peker = peker.hentNeste();
            return temp;
        }
    }


    //Indre klasse Node som er lenkeelementene i listen.
    public class Node {

        //Linken er enkeltlenket og har generisk typeparameter T. Resten av
        //node-koden er noe selvforklarende.
        protected Node neste;
        protected T innhold;

        public Node(T innhold) {
    		this.innhold = innhold;
    	}

        public void settNeste(Node neste) {
    		this.neste = neste;
    	}

        public Node hentNeste() {
    		return neste;
    	}

        public T hentInnhold() {
    		return innhold;
    	}

        //Setter inn en node mellom to noder, hoyre og venstre node blir
        //deres relative plasser i lenken.
        public void settInnMellom(Node venstre, Node hoyre) {
            this.settNeste(hoyre);
            venstre.settNeste(this);
        }
    }


    //Returnerer en iterator over lenkelisten.
    public Iterator<T> iterator() {
        return new ListeIterator();
    }

}
