//I denne lenkelisten setter vi inn elementer paa slutten av listen og
//henter de ut fra toppen.
public class Koe<T> extends LenkeListe<T> {

    public void settInn(T element) {

        //Dersom hode == null eksisterer ingen elementer og vi setter hode-
        //pekeren til aa peke paa en ny node av vaar generiske typeparameter
        //T. Inkrementerer storrelse.
        if(this.hode == null) {
            this.hode = new Node(element);
            this.storrelse++;
        } else {
            //Dersom det finnes elementer, skal vi lage en midlertidig node.
            //Vi setter den foerst til aa peke paa hode, deretter looper vi
            //gjennom lenken til vi treffer ett element som ikke har en neste-
            //peker. Vi setter dette elementets nestepeker til aa bli en
            //ny node av T. Slik settes elementer inn paa slutten.
            Node temp = this.hode;
            while(temp.hentNeste() != null) {
                temp = temp.hentNeste();
            }
            temp.settNeste(new Node(element));
            this.storrelse++;
        }
    }
}
