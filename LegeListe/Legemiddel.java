public abstract class Legemiddel {


    //Id'en er static og jeg forklarer her hvordan den funker. En static variabel
    //nesteId er felles for alle objekter av typen "Legemiddel". Denne variabelen
    //endres paa tvers av alle legemidler og forteller de individuelle objektene
    //hva den unike id'en til det neste legemiddel-objektet blir. Dette foregaar i
    //konstruktoeren nedenfor, hvor vi setter id og inkrementerer nesteId som da
    //inkrementeres for alle objekter.
    private static int nesteId = 0;
    private int id;

    private String navn;
    private double pris;
    private double virkestoff;


    public Legemiddel(String navn, double pris, double virkestoff) {
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;

        this.id = nesteId;
        nesteId++;
    }


    public int hentId() {
        return id;
    }


    public String hentNavn() {
        return navn;
    }


    public double hentPris() {
        return pris;
    }


    public double hentVirkestoff() {
        return virkestoff;
    }


    //Sjekker instanceof objektene og printer ut korrekt utskrift avhengig av
    //hvilken type de er.
    @Override
    public String toString() {
        if(this instanceof LegemiddelA) {
            return "Type: A " + "Navn: " + navn + " Pris: " + pris;
        } else if(this instanceof LegemiddelB) {
            return "Type: B " + "Navn: " + navn + " Pris: " + pris;
        } else {
            return "Type: C " + "Navn: " + navn + " Pris: " + pris;
        }
    }


}
