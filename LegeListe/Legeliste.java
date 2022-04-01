import java.util.Iterator;


//Vi erstatter vaar generiske typeparameter med objekt av typen Lege, og siden
//Lege implementerer Comparable<T> kan vi lage denne lenkelisten av typen
//OrdnetLenkeliste, som ordner objektene i listen i sortert rekkefoelge.
public class Legeliste extends OrdnetLenkeliste<Lege> {


    //Denne metoden tar inn en streng og returnerer ett objekt av typen Lege.
    public Lege finnLege(String navn) {

        Lege temp = null;
        String tempNavn = navn;
        Iterator<Lege> kjor = iterator();

        //Naar iteratoren vaar har en neste linje, kan vi sette Legeobjektet
        //som kjor.next fordi denne metoden returnerer innholdet av generisk
        //type T som i dette tilfellet er objekter av typen Lege. Deretter
        //kaller vi paa leges hentnavn, og sjekker disse to strengene mot hverandre
        //med .equals-metoden. Dersom denne er true, er dette likt "navn"-variabelen
        //til legeobjektet og vi kan trygt returnere det.
        while(kjor.hasNext()) {
            temp = kjor.next();
            if(temp.hentNavn().equals(tempNavn)) {
                return temp;
            }
        }
        return temp;
    }


    //Denne metoden returerer ett array med strings, som er navnene paa alle legene
    //i lenkelisten. Den gjoer dette med en iterator.
    public String[] stringArrayMedNavn() {

        String[] tempArray = new String[storrelse];
        Iterator<Lege> kjor = iterator();

        //Arrayet blir initialisert paa "storrelse" storrelse, som er antall
        //objekt i listen. Vi looper gjennom listen, og setter hver plass i
        //i arrayet til aa veare stringen navn til legen. Slik fyller vi arrayet
        //og returerer det i slutten av metoden.
        for(int i  = 0; kjor.hasNext(); i++) {
            tempArray[i] = kjor.next().hentNavn();
        }
        return tempArray;
    }
}
