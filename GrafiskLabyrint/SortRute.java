public class SortRute extends Rute {

    public SortRute(int kolonne, int rad, Labyrint denneLabyrint) {
        super(kolonne, rad, denneLabyrint);
    }

    @Override
    public char tilTegn() {
        return '#';
    }
    
}
