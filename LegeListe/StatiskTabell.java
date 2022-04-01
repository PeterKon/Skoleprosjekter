import java.util.Iterator;

@SuppressWarnings("unchecked")
public class StatiskTabell<T> implements Tabell<T> {

    //Deklarerer variabler - array av type object castes til type T senere
    protected Object[] liste;
    protected int antallObjekt = 0;
    protected int storrelse;


    //Her caster vi array av type object om til array av type T og initialiserer
    //med storrelsen "storrelse".
    public StatiskTabell(int storrelse) {
        liste = (T[]) new Object[storrelse];
        this.storrelse = storrelse;
    }


    //Returerer antall objekter i arrayet, ikke antall plasser.
    public int storrelse() {
        return antallObjekt;
    }


    //Returnerer true dersom ingen objekter finnes i arrayet.
    public boolean erTom() {
        return antallObjekt == 0;
    }


    //Setter inn element T i liste. Dersom storrelsen og antallObjekter er like
    //er lista full og det kastes ett FullTabellUnntak. Ellers skal vi sette
    //element T til temp og legge det til i liste og inkrementere antallObj.
    public void settInn(T element) {
        if(storrelse == antallObjekt) {
            throw new FullTabellUnntak(storrelse);
        } else {
            T temp = element;
            liste[antallObjekt] = temp;
            antallObjekt++;
        }
    }


    //Henter element T fra plass "plass". Dersom plass er mindre enn null eller
    //dersom plass er storre enn storrelsen paa arrayet, skal det kastes ett
    //UgyldigPlassUnntak. Ellers Setter vi tempObj til aa veare plassen i arrayet,
    //saa caster vi obj temp til type T og returnerer det.
    public T hentFraPlass(int plass) {
        if(plass < 0 || plass > storrelse) {
            throw new UgyldigPlassUnntak(plass, storrelse);
        } else {
            Object tempObj = liste[plass];
            T temp = (T)tempObj;
            return temp;
        }
    }

    //Indre klasse som vi kan lage interator-objekter av.
    private class ArrayIterator implements Iterator<T> {

		private int peker;

		public ArrayIterator() {
			peker = 0;
		}

        //Dersom pekeren vaar er storre eller lik storrelsen paa arrayet
        //finnes det ingen neste element og vi returnerer false. Ellers
        //evaluerer vi om den neste plassen er lik null - dersom ja return
        //false, dersom den inneholder ett objekt return true.
		public boolean hasNext() {
            if(peker >= storrelse) {
                return false;
            }
			return !(liste[peker] == null);
		}

        //Vi setter tempobj til aa veare objekter paa plass peker, caster det
        //om til type T, inkrementerer peker og returnerer vaart objekt "temp"
        //av type T.
		public T next() {
            Object tempObj = liste[peker];
            T temp = (T)tempObj;
            peker++;
            return temp;
		}
	}

    //Returnerer ett objekt av typen ArrayIterator av generisk type T.
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
}
