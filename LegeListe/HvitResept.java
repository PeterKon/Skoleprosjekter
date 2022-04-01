public class HvitResept extends Resept {


    //Vi kaller her paa superklassens konstruktoer i korrekt rekkefoelge.
    public HvitResept(Legemiddel legemiddel, Lege utskrevendeLege, int pasientId, int reit) {
        super(legemiddel, utskrevendeLege, pasientId, reit);
    }


    //Strengrepresentasjon av medisinens farge.
    public String farge() {
        return "hvit";
    }


    //Her betaler pasienten full pris og vi henter derfor pris fra superklassens
    //hentPris-metode.
    public double prisAaBetale() {
        return this.hentLegemiddel().hentPris();
    }

}
