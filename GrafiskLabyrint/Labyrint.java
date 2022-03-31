import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Labyrint {

    private int kolonner;
    private int rader;
    private Rute[][] ruteArray;
    private Koe<String> utveiListe = new Koe<String>();

    private Labyrint(int kolonner, int rader) {
        this.kolonner = kolonner;
        this.rader = rader;
        ruteArray = new Rute[rader][kolonner];
    }


    //Vi bruker her en fabrikkmetode for aa lage objekter av type Labyrint.
    //Konstruktoeren er privat, og for aa faa laget labyrinter, maa du da
    //kalle paa denne metoden med en fil.
    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {

        File temp = fil;
        Scanner sc = new Scanner(temp);

        String[] info = sc.nextLine().split(" ");
        int tempRader = Integer.parseInt(info[0]);
        int tempKolonner = Integer.parseInt(info[1]);
        Labyrint tempLab = new Labyrint(tempKolonner, tempRader);


        while(sc.hasNextLine()) {
            //Hver linje lages til ett array av chars. Vi leser slik gjennom ett
            //nytt array for hver rad per kolonne med to noestede for-loekker,
            //og kan sette inn Ruter basert paa hvilken char som blir gitt.
            for(int i = 0; i < tempRader; i++) {
                char[] tempCharArray = sc.nextLine().toCharArray();
                for(int j = 0; j < tempKolonner; j++) {
                    //Sorte ruter representeres ved "#", og skal bare settes rett inn
                    //i det to-dimensjonelle arrayet.
                    if(tempCharArray[j] == '#') {
                        tempLab.ruteArray[i][j] = new SortRute(j + 1, i + 1, tempLab);
                    //Hvis det ikke er en sort rute, er den hvit. Siden hvite ruter ogsaa
                    //kan vaere Aapninger, maa vi sjekke dette.
                    } else if(tempCharArray[j] == '.') {
                        //De fire testene sjekker her om vi er paa kanten av brettet.
                        //En hvit rute paa kanten skal settes inn som en Aapning,
                        //og om en av disse slaar inn settes en Aapning inn her.
                        if(j == 0 || j == tempKolonner - 1 || i == 0 || i == tempRader - 1) {
                            tempLab.ruteArray[i][j] = new Aapning(j + 1, i + 1, tempLab);
                        //Hvis ikke, skal det lages en vanlig hvit rute paa dette feltet.
                        } else {
                            tempLab.ruteArray[i][j] = new HvitRute(j + 1, i + 1, tempLab);
                        }
                    }
                }
            }
        }
        //I denne doble for-loekken gaar vi gjennom det to-dimensjonelle arrayet
        //og setter alle referansene til naborutene. Alle if-testene sjekker om
        //vi befinner oss paa en av kantene i brettet. Hoyre, venstre, topp eller
        //bunn. Dersom dette er tilfellet, kan vi ikke sette naboer.
        for(int i = 0; i < tempLab.rader; i++) {
            for(int j = 0; j < tempLab.kolonner; j++) {
                if(i != 0) { //Sett Nord dersom ikke paa toppen
                    tempLab.settNord(tempLab.ruteArray[i][j], tempLab.ruteArray[i-1][j]);
                }
                if(i != tempLab.rader - 1) { //Sett Syd dersom ikke paa bunnen
                    tempLab.settSyd(tempLab.ruteArray[i][j], tempLab.ruteArray[i+1][j]);
                }
                if(j != 0) { //Sett Vest dersom ikke paa venstre side
                    tempLab.settVest(tempLab.ruteArray[i][j], tempLab.ruteArray[i][j-1]);
                }
                if(j != tempLab.kolonner - 1) { //Sett Ost dersom ikke paa hoyre side
                    tempLab.settOst(tempLab.ruteArray[i][j], tempLab.ruteArray[i][j+1]);
                }
            }
        }
        return tempLab;
    }


    //Returnerer en streng-representasjon av selve labyrinten, dvs at den
    //returnerer en char for hvert element i 2d-arrayet. For hver nye linje
    //legges en ny linje-karakter \n til. Naar vi treffer ett null-element,
    // legges n til, dette er for rent testformaal og for aa unngaa
    //nullPointerException ved feil.
    public String toString() {

        String returnDenne = "";
        for(int i = 0; i < rader; i++) {
            for(int j = 0; j < kolonner; j++) {
                if(ruteArray[i][j] == null) {
                    returnDenne += 'n'; //n representerer null, for testing
                } else {
                    returnDenne += ruteArray[i][j].tilTegn(); //Dersom ikke, vanlig tegn
                }
            }
            returnDenne += "\n"; //Linjeskift etter hver rad
        }
        return returnDenne;
    }


    //Denne metoden er implementert identisk med toString, forskjellen er at denne
    //metoden returnerer char for hver rute i 2D-arrayet som sier hvilken type nabo
    //denne ruten mangler. x returneres for ingen ruter null, N for nord etc.
    //Slik kan vi teste om alle rutene har faatt tilegnet korrekt naboer. Denne
    //metoden er bare for testing.
    public String naboRuteSjekker() {

        String returnDenne = "";
        for(int i = 0; i < rader; i++) {
            for(int j = 0; j < kolonner; j++) {
                if(ruteArray[i][j] == null) {
                    returnDenne += 'n';
                } else {
                    returnDenne += ruteArray[i][j].naboSjekker();
                }
            }
            returnDenne += "\n";
        }
        return returnDenne;
    }


    //Implementeres ogsaa identisk med toString og naboRuteSjekker, denne
    //metoden sjekker korrekt satt koordinatsystem. For testing.
    public String koordinatSjekker() {

        String returnDenne = "";
        for(int i = 0; i < rader; i++) {
            for(int j = 0; j < kolonner; j++) {
                if(ruteArray[i][j] == null) {
                    returnDenne += 'n';
                } else {
                    returnDenne += ruteArray[i][j].koordinat();
                }
            }
            returnDenne += "\n";
        }
        return returnDenne;
    }


    //Maaten jeg loeste dette paa var litt spagetti, men hver gang vi finner ett
    //nytt sett utveier fra ett punkt, oprettes ett temp-objekt av listen som
    //etter dette initialiseres paa nytt som en tom liste. Temp returneres. Slik
    //er listen tom etter bruk.
    public Liste<String> finnUtveiFra(int i, int j) {
        ruteArray[j - 1][i - 1].finnUtvei();
        Koe<String> tempUtveiListe = utveiListe;
        utveiListe = new Koe<String>();
        return tempUtveiListe;
    }


    //Denne metoden brukes av Rute for aa sette inn en utvei i listen.
    public void settInnUtvei(String string) {
        utveiListe.settInn(string);
    }


    //Fire metoder for aa gjoere setting av rute-referanser i Rute litt
    //enklere med tanke paa syntaks slik at vi kan kalle pa sett(fra, til).
    public void settNord(Rute fra, Rute til) {
        fra.settReferanseNord(til);
    }

    public void settSyd(Rute fra, Rute til) {
        fra.settReferanseSyd(til);
    }

    public void settVest(Rute fra, Rute til) {
        fra.settReferanseVest(til);
    }

    public void settOst(Rute fra, Rute til) {
        fra.settReferanseOst(til);
    }


    //Denne var litt uklar hvorfor den skulle med, stod ikke noe om det i teksten
    //annet enn at "den blir brukt". Tok den med for aa unngaa feilmeldinger.
    public void settMinimalUtskrift() { }

}
