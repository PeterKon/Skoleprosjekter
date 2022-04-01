//Denne lenkelisten er en stabel, hvor en setter inn OG tar ut
//elementer fra toppen av listen.
public class Stabel<T> extends LenkeListe<T> {


    public void settInn(T element) {
        //Hvis hode == null er listen tom og vi setter hode som en ny node
        //med vaar generiske typeparameter T.
        if(this.hode == null) {
            this.hode = new Node(element);
            this.storrelse++;
        } else {
            //Dersom det er elementer i listen maa vi foerst lage en nyNode
            //av vaar generiske typeparameter, deretter setter vi dens neste-
            //peker til aa peke paa hodet i listen. Deretter setter vi
            //hodepekeren til aa peke paa nyNode.
            Node nyNode = new Node(element);
			nyNode.settNeste(this.hode);
            this.hode = nyNode;
            this.storrelse++;
        }
    }
}
