public abstract class Rute {

    //Labyrinten har info om kolonne og rad, referanse til egen Labyrint,
    //samt to booleans som brukes i den rekursive gaa()-metoden. Den har
    //ogsaa referanser til alle naboruter nord, sor, ost og vest.
    protected int kolonne;
    protected int rad;
    protected Labyrint denneLabyrint;
    protected boolean besokt = false;
    protected boolean res = false;
    protected String totalUtvei = "";

    protected Rute nord, syd, vest, ost;

    public Rute(int kolonne, int rad, Labyrint denneLabyrint) {
        this.kolonne = kolonne;
        this.rad = rad;
        this.denneLabyrint = denneLabyrint;
    }


    //Kaller paa gaa() med en tom streng, slik at vi starter aa loese labyrinten.
    public void finnUtvei() {
        this.gaa("");
    }


    //Rekursiv metode som loeser labyrinten fra Ruten den blir kalt fra. Gaar
    //gjennom stegvis for aa forklare funksjonen av div kall.
    public boolean gaa(String utvei) {


        //Basistilfellet for at det rekursive kallet skal avsluttes er at vi
        //har ankommet en Rute av typen Aapning. Dersom dette er tilfellet,
        //settes den veien vi gikk hit inn i listen og vi returnerer true.
        if(this instanceof Aapning) {
            totalUtvei = utvei + " --> " + this.koordinat();
            denneLabyrint.settInnUtvei(totalUtvei);
            return true;
        }
        //Hvis dette er en sort Rute, eller vi har besokt den fra foer, skal
        //det returneres false. Slik gaar vi ikke paa disse rutene.
        if(this instanceof SortRute || besokt) {
            return false;
        }

        //Vi oensker aa lagre alle utveier som en string for deretter aa sette
        //de inn i en Koe<String>. Dette gjoeres ved at dersom stringen er tom
        //skal det ikke legges paa en pil foran, ellers saa tar vi imot en String
        //string som er summen av alle foregaaende kall. Det legges paa en pil
        //her og saa koordinaten for naavaerende Rute.
        if(utvei.equals("")) {
            totalUtvei = utvei + this.koordinat();
        } else {
            totalUtvei = utvei + " --> " + this.koordinat();
        }

        //Dersom vi kommer ned hit er vi naa paa Ruten og kan markere den som
        //besokt. Herfra proever vi aa gaa alle veiene nord, soer, ost og vest.
        //Vi sender med summen av strings (utveien) til neste Rute.
        besokt = true;
        if(nord.gaa(totalUtvei)) {
            res = true;
        }
        if(ost.gaa(totalUtvei)) {
            res = true;
        }
        if(syd.gaa(totalUtvei)) {
            res = true;
        }
        if(vest.gaa(totalUtvei)) {
            res = true;
        }
        //Vi returneres res til slutt. Dersom ingen veier var mulig aa gaa,
        //returneres false her fordi res initialiseres som false.
        return res;
    }


    //Disse fire metodene setter referanser for naboruter. Naboruter som ikke
    //finnes (utenfor arrayet i Labyrint) er satt som "null".
    public void settReferanseNord(Rute nord) {
        this.nord = nord;
    }

    public void settReferanseSyd(Rute syd) {
        this.syd = syd;
    }

    public void settReferanseVest(Rute vest) {
        this.vest = vest;
    }

    public void settReferanseOst(Rute ost) {
        this.ost = ost;
    }


    //Metode for aa teste at naboene til ruten er korrekt satt. Eksempelvis
    //dersom ruten ligger i hoyre hjoerne, er nord og ost null og da gir ruten
    //'H' tilbake. Ellers indikerer den hvilken retning null-elementet ligger
    //i. 'x' dersom ingen null.
    public char naboSjekker() {

        if(nord == null && vest == null || nord == null && ost == null
        || syd == null && vest == null || syd == null && ost == null) {
            return 'H'; //Hjoernet, dvs kantene
        } else if(nord == null) {
            return 'N'; //Side Nord
        } else if (syd == null) {
            return 'S'; //Side soer
        } else if (vest == null) {
            return 'V'; //Side Vest
        } else if (ost == null) {
            return 'O'; //Side Ost
        }
        return 'x'; //Inni labyrinten, ingen sider null
    }


    //Returnerer koordinatene til denne ruten, korrekt formatert
    public String koordinat() {
        return "(" + kolonne + ", " + rad + ")";
    }


    //Abstrakt metode som returnerer rutens char-representasjon.
    abstract char tilTegn();

}
