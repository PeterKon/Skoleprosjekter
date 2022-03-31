import java.io.File;

public class TestOblig5 {

    public static void main(String[] args) throws Exception {
        File test = new File("7.in");
        Labyrint testLab = Labyrint.lesFraFil(test);

        //Vi tester om labyrinten ser bra ut paa utskrift, og deretter
        //sjekker vi om aapningene ble korrekt plassert som 'O'.
        System.out.println("\nTester utskrift av labyrint");
        System.out.println(testLab);


        //Sjekker at naboer er satt korrekt med egen hjelpemetoden. Denne
        //printer ut H for hjoerner, og N, V, S og O avhengig av hvilken
        //side som har "null" som naboer. Slik ser vi om det er tilsynelatende
        //korrekt.
        System.out.println("Sjekker naboer i oppsatt labyrint");
        System.out.println(testLab.naboRuteSjekker());

        //Sjekker korrekt utskrift av koordinater.
        System.out.println("Sjekker koordinater i oppsatt labyrint");
        System.out.println(testLab.koordinatSjekker());

        System.out.println("Sjekker alle veier fra punkt 2,2 i 7.in");
        Liste<String> liste = testLab.finnUtveiFra(2, 2);
        for(String l: liste) {
            System.out.println(l);
        }
    }

}
