public class Aapning extends HvitRute {

    public Aapning(int kolonne, int rad, Labyrint denneLabyrint) {
        super(kolonne, rad, denneLabyrint);
    }

    //For aa se om Aapninger er korrekt satt returnerer vi 'O'
    @Override
    public char tilTegn() {
        return 'O';
    }

}
