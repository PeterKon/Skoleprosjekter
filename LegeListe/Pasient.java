public class Pasient {


    //Id'en er static og jeg forklarer her hvordan den funker. En static variabel
    //nesteId er felles for alle objekter av typen "Pasient". Denne variabelen
    //endres paa tvers av alle pasienter og forteller de individuelle objektene
    //hva den unike id'en til det neste pasient-objektet blir. Dette foregaar i
    //konstruktoeren nedenfor, hvor vi setter id og inkrementerer nesteId som da
    //inkrementeres for alle objekter.
    private static int nesteId = 0;
    private int id;

    private String navn;
    private long fodselsnummer;
    private String gateadresse;
    private int postnummer;

    private Stabel<Resept> reseptliste = new Stabel<Resept>();


    public Pasient(String navn, long fodselsnummer, String gateadresse, int postnummer) {
        this.navn = navn;
        this.fodselsnummer = fodselsnummer;
        this.gateadresse = gateadresse;
        this.postnummer = postnummer;

        this.id = nesteId;
        nesteId++;
    }


    public int hentId() {
        return id;
    }


    public String hentNavn() {
        return navn;
    }


    public long hentFodselsnummer() {
        return fodselsnummer;
    }


    public String hentGateadresse() {
        return gateadresse;
    }


    public int hentPostnummer() {
        return postnummer;
    }


    public void leggTilResept(Resept resept) {
        reseptliste.settInn(resept);
    }


    public Stabel<Resept> hentReseptliste() {
        return reseptliste;
    }


    //Returnerer en streng-representasjon av objektet med navn og fodselsnummer.
    @Override
    public String toString() {
        return navn + " (" + fodselsnummer + ")";
    }

}
