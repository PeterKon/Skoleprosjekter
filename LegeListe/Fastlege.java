public class Fastlege extends Lege implements Kommuneavtale {

    private int avtalenummer;

    //Det eneste spennende med denne subklassen er at den har ett avtalenummer,
    //som returneres i en egen metode.
    public Fastlege(String navn, int avtalenummer) {
        super(navn);
        this.avtalenummer = avtalenummer;
    }


    public int hentAvtalenummer() {
        return avtalenummer;
    }
}
