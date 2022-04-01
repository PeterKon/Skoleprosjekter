public class BlaaResept extends Resept {


    //Vi kaller her paa superklassens konstruktoer i korrekt rekkefoelge.
    public BlaaResept(Legemiddel legemiddel, Lege utskrevendeLege, int pasientId, int reit) {
        super(legemiddel, utskrevendeLege, pasientId, reit);
    }


    //Returnerer en enkel strengrepresentasjon av fargen paa medisinen.
    public String farge() {
        return "blaa";
    }


    //Det er 75% av paa dette legemiddelet, saa pasienten betaler prisen * 0,25.
    public double prisAaBetale() {
        return this.hentLegemiddel().hentPris() * 0.25;
    }

}
