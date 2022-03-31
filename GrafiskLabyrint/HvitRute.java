public class HvitRute extends Rute {

    public HvitRute(int kolonne, int rad, Labyrint denneLabyrint) {
        super(kolonne, rad, denneLabyrint);
    }

    @Override
    public char tilTegn() {
        return '.';
    }

}
