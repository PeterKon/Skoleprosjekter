//Dette er en ordnet, dvs sortert lenkeliste der elementene er sortert
//etter "storrelse". De minste elementene er plassert foerst, og
//de stoerste elementene sist.
public class OrdnetLenkeliste<T extends Comparable<T>> extends LenkeListe<T> {


    //Dette er den eneste metoden vi trenger implementere. Den setter inn ett
    //element og sorterer det paa riktig plass. Metoden er noe komplisert
    //og jeg vil kommentere den stykkvis.
    public void settInn(T element) {

        //Her initialiserer jeg et generisk element T og en Node for aa bruke
        //de til sammenligning.
        T temp = element;
        Node tempNode = new Node(temp);

        //Dersom hode == null er det ingen elementer i listen. Da oensker vi
        //aa sette inn elementet vi har satt inn imetoden som hode.
        if(this.hode == null) {
            this.hode = new Node(element);
            this.storrelse++;
            return;
        }

        //Dersom temp generisk T er mindre enn eller lik hodes generiske T
        //kan vi sette elementet vaart fremst i listen som ett hode. Vi setter
        //da foerst tempNodes neste til aa bli hode, deretter setter vi hode-
        //pekeren til aa bli tempNode.
        if(temp.compareTo(this.hode.hentInnhold()) <= 0) {
            tempNode.settNeste(this.hode);
            this.hode = tempNode;
            this.storrelse++;
            return;
        }

        //Jeg er ikke saa veldig stolt av dette, men denne vederstyggeligheten
        //fungerer. Den sjekker om vandrer har ett neste element, og dersom det
        //har dette sjekker den deretter om innholdet i vandrer sin neste er
        //storre enn tempNodes innhold. Dersom dette stemmer, gaar vandrer
        //videre i lenkelisten. Dersom dette blir usant, terminerer loekken.
        //Vi unngaar NullPointerException fordi java sjekker venstre del av
        //statementen foerst. Tok inspirasjon fra kode i en gruppetime paa
        //akkurat denne linjen.
        Node vandrer = this.hode;
		while (vandrer.hentNeste() != null && vandrer.hentNeste().hentInnhold().compareTo(tempNode.hentInnhold()) < 0) {
			vandrer = vandrer.hentNeste();
		}
        //Naar loekken har terminert vet vi at vi er paa riktig plass i
        //lenkelisten og vi kan tempNodes neste som vandrers neste, og sette
        //vandrers neste som tempNode.
        tempNode.neste = vandrer.neste;
        vandrer.neste = tempNode;
        this.storrelse++;
    }
}
