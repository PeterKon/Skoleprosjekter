//Denne klassen er helt lik superklassen med unntak av objektnavnet. En kunne
//laget legemiddel ikke abstrakt og bare hatt C som superklassens, men dette
//er en daarlig maate aa gjoere det paa.
public class LegemiddelC extends Legemiddel{

    public LegemiddelC(String navn, double pris, double virkestoff) {
        super(navn, pris, virkestoff);
    }

}
