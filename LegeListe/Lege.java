public class Lege implements Comparable<Lege> {


    //En lege har ett navn og ingen ID. Vi bruker en lenkeliste type koe (FIFO)
    //for aa lagre objekter av type Resept.
    private String navn;
    private Koe<Resept> reseptliste = new Koe<Resept>();


    public Lege(String navn) {
        this.navn = navn;
    }


    public String hentNavn() {
        return navn;
    }


    //Siden String sin implementasjon av compareTo passer med oppgaveteksten vaar
    //sin beskrivelse av hvordan strenger skal sammenlignes, kan vi ganske enkelt
    //bruke den, og vi sammenligner derfor dette objektets navnestreng med det
    //innkommende objektets navnestreng. CompareTo returnerer ett positivt eller
    //negativt heltall. (0 er ett heltall)
    public int compareTo(Lege annenLege) {
        return this.navn.compareTo(annenLege.hentNavn());
    }


    //Setter en resept inn i FIFO lenkelisten.
    public void leggTilResept(Resept resept) {
        Resept tempResept = resept;
        reseptliste.settInn(tempResept);
    }


    //Returnerer hele lenkelisten av resepter.
    public Koe<Resept> hentReseptliste() {
        return reseptliste;
    }
}
