public abstract class Resept {


    //Id'en er static og jeg forklarer her hvordan den funker. En static variabel
    //nesteId er felles for alle objekter av typen "Resept". Denne variabelen
    //endres paa tvers av alle resepter og forteller de individuelle objektene
    //hva den unike id'en til det neste resept-objektet blir. Dette foregaar i
    //konstruktoeren nedenfor, hvor vi setter id og inkrementerer nesteId som da
    //inkrementeres for alle objekter.
    private static int nesteId = 0;
    private int id;

    private Legemiddel legemiddel;
    private Lege utskrevendeLege;
    private int pasientId;
    private int reit;


    public Resept(Legemiddel legemiddel, Lege utskrevendeLege, int pasientId, int reit) {
        this.legemiddel = legemiddel;
        this.utskrevendeLege = utskrevendeLege;
        this.pasientId = pasientId;
        this.reit = reit;

        id = nesteId;
        nesteId++;
    }


    public int hentId() {
        return id;
    }


    public Legemiddel hentLegemiddel() {
        return legemiddel;
    }


    public Lege hentLege() {
        return utskrevendeLege;
    }

    public int hentPasientId() {
        return pasientId;
    }


    public int hentReit() {
        return reit;
    }


    //Naar du bruker en resept, er det eneste du gjoer aa dekrementere
    //reit. Hvis reit er 0 kan du ikke bruke resepten - da return false.
    //Ellers dekrementerer vi reit og returnerer true.
    public boolean bruk() {
        if(reit == 0) {
            return false;
        } else {
            reit--;
            return true;
        }
    }


    //Returnerer en streng-representasjon av objektet.
    @Override
    public String toString() {
        return "Id: " + id + " Navn: " + legemiddel.hentNavn() +
        " Reit: " + reit + " Farge: " + this.farge();
    }


    //To abstrakte metoder som subklassene av resept maa implementere.
    public abstract String farge();
    public abstract double prisAaBetale();

}
