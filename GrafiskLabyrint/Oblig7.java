import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javax.swing.SwingConstants;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javax.swing.JOptionPane;



public class Oblig7 extends Application implements EventHandler<ActionEvent> {

    private Button button;
    private Stage window;

    //I denne klassen haandteres hele det grafiske grensesnittet. Vi skal her
    //operere med tre forskjellige scener. Scene1 er startskjermen hvor
    //brukeren presenteres med en skjerm hvor han kan velge hvilken fil som
    //skal scannes inn. Scene2 skal representere labyrinten, hvor alle hvite
    //bokser er klikkbare og skal deretter vise scene3, som er loesning nr 1
    //i listen over loesninger gitt fra den rekursive algoritmen.
    private Scene scene1, scene2, scene3;

    private int antallLosninger;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.window = primaryStage;
        window.setTitle("Labyrinth solver - Peter Kongsvik (2017)");

        //Denne knappen skal brukeren trykke paa for aa velge fil. Her settes
        //bare tekstinnholdet paa knappen.
        button = new Button();
        button.setText("Choose file");

        //Denne klassen skal haandtere det som skjer naar knappen trykkes, og
        //den vil derfor bruke denne klassens handle, som leder til resten av
        //programmets funksjonalitet.
        button.setOnAction(this);

        //Vi lager en sentrert knapp med en StackPane, og legger til knappen som
        //da settes i midten. Scenestorrelsen settes og vinduet settes til scene1
        //og vises.
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        scene1 = new Scene(layout, 500, 400);
        window.setScene(scene1);

        window.show();
    }


    //Handle kalles naar knappen trykkes. Her foregaar logikken i programmet.
    @Override
    public void handle(ActionEvent event) {

        //Vi bruker en FileChooser, som gjoer at brukeren kan browse mappene
        //sine og velge en fil. File fil settes til resultatet av dette valget.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Labyrinth solver - Please choose a file");
        File fil = fileChooser.showOpenDialog(window);

        //Her lages labyrintobjektet vi skal manipulere, og scene2 settes som
        //resultat av funksjonen lagGrid. Dette vil vise labyrinten i ett sort-
        //hvitt representasjon hvor alle hvite ruter er klikkbare og vil
        //returnere en loesning.
        try {
            Labyrint tempLab = Labyrint.lesFraFil(fil);
            scene2 = new Scene(lagGrid(tempLab));
            window.setScene(scene2);

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fil == null) {
            return;
        }

    }


    //Denne metoden er ansvarlig for aa grafisk representere labyrinten med
    //klikkbare hvite ruter. Den viser Scene2 naar den er ferdig.
    public GridPane lagGrid(Labyrint tempLab) {

        //Vi tar inn labyrintobjektet vaart og lager en gridPane som skal sendes
        //opp til handle()-funksjonen og vises naar den har blitt laget ferdig.
        Labyrint innLab = tempLab;
        GridPane grid = new GridPane();

        //Dette gaar gjennom labyrintens rader og kolonner, og scanner inn tegn
        //som da settes som Grid-Pane representasjoner grafisk. Sorte ruter for
        //char '#' og hvite ruter ellers.
        for(int i = 0; i < innLab.getRader(); i++) {
            for(int j = 0; j < innLab.getKolonner(); j++) {

                //Dersom ruten i labyrinten er '#' skal vi vise en sort rute
                if(innLab.getTegn(i, j) == '#') {
                    //Vi representerer de som rektangler, og setter hoyde og
                    //bredde og fyller de med sort. Deretter setter vi deres
                    //indeks og legger de til i GridPane'en.
                    Rectangle rec = new Rectangle();
                    rec.setWidth(16);
                    rec.setHeight(16);
                    rec.setFill(Color.BLACK);
                    GridPane.setRowIndex(rec, i);
                    GridPane.setColumnIndex(rec, j);
                    grid.getChildren().addAll(rec);
                } else {
                    //Derson ikke sort, er denne ruten hvit, og den settes med
                    //samme dimensjoner som sort og fylles med hvitt.
                    Rectangle rec = new Rectangle();
                    rec.setWidth(16);
                    rec.setHeight(16);
                    rec.setFill(Color.WHITE);
                    GridPane.setRowIndex(rec, i);
                    GridPane.setColumnIndex(rec, j);
                    grid.getChildren().addAll(rec);

                    //Vi oensker at hvite ruter skal vaere klikkbare. Dette haandterer vi
                    //her, hvor hver individuelle hvite rute gis en funksjon for aa
                    //loese labyrinten og vise resultatet.
                    rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {

                            //Vi maa vite rektangelets rad og kolonne for aa
                            //kalle paa funksjonen som loeser labyrinten.
                            int i = GridPane.getRowIndex(rec);
                            int j = GridPane.getColumnIndex(rec);

                            //Vi henter ut listen av loesninger ved aa kalle paa den
                            //rekursive funksjonen gaa via finnUtveiFra. Vi tar ut
                            //den foerste loesningen fra denne listen og viser den.
                            Liste<String> liste = innLab.finnUtveiFra(j + 1, i + 1);
                            int storrelse = liste.storrelse();
                            String losning = liste.fjern();

                            //genererer ett boolean-array som gir losning via stringen
                            //vi fikk ut fra foerste plass paa listen over loesninger.
                            boolean[][] losningArray = losningStringTilTabell(losning, innLab.getKolonner(), innLab.getRader());

                            //Scene3 er loesningen som skal vises grafisk. For aa vise denne
                            //maa vi sende med labyrinten og boolean-arrayet for aa kunne
                            //tegne opp stien. Vi setter saa scenen til scene3.
                            scene3 = new Scene(lagLosningGrid(losningArray, innLab, storrelse));
                            window.setScene(scene3);

                            JOptionPane.showMessageDialog(null, "Antall loesninger: " + antallLosninger);

                        }
                    });
                }
            }
        }

        return grid;
    }


    //Denne funksjonen er ansvarlig for aa klargjoere scene3 via aa ta inn boolean-
    //arrayet av loesninger og labyrinten. Den returnerer en ferdig GridPane som
    //vises i scene3.
    public GridPane lagLosningGrid(boolean[][] losningArray, Labyrint innLab, int storrelse) {

        GridPane grid = new GridPane();
        Labyrint tempLab = innLab;
        boolean[][] array = losningArray;
        this.antallLosninger = storrelse;

        //Denne noestede for-loekken har samme prinsipp som den i metoden over. Den
        //gaar gjennom hver kolonne og rad og basert paa resultat lager ett rektangel
        //i GridPanen vaar.
        for(int i = 0; i < innLab.getRader(); i++) {
            for(int j = 0; j < innLab.getKolonner(); j++) {

                //Dersom tegnet er char '#' skal det alltid vaere en sort
                //rute her.
                if(tempLab.getTegn(i, j) == '#') {
                    Rectangle rec = new Rectangle();
                    rec.setWidth(16);
                    rec.setHeight(16);
                    rec.setFill(Color.BLACK);
                    GridPane.setRowIndex(rec, i);
                    GridPane.setColumnIndex(rec, j);
                    grid.getChildren().addAll(rec);
                //Stien av loesninger vil ha prioritet over vanlige hvite ruter.
                //Slik unngaar vi aa printe over med hvite ruter hvor stien er.
                //Dersom boolean-arrayet er true paa denne indeks, skal stien
                //legges inn som gronn rute paa denne plassen i gridpane.
                } else if(array[i][j] == true) {
                    Rectangle rec = new Rectangle();
                    rec.setWidth(16);
                    rec.setHeight(16);
                    rec.setFill(Color.GREEN);
                    GridPane.setRowIndex(rec, i);
                    GridPane.setColumnIndex(rec, j);
                    grid.getChildren().addAll(rec);
                } else {
                    //Hvis ingen av de andre to slo inn, skal det vaere en vanlig
                    //hvit rute her.
                    Rectangle rec = new Rectangle();
                    rec.setWidth(16);
                    rec.setHeight(16);
                    rec.setFill(Color.WHITE);
                    GridPane.setRowIndex(rec, i);
                    GridPane.setColumnIndex(rec, j);
                    grid.getChildren().addAll(rec);
                }
            }
        }

        return grid;
    }


    //Medfoelgende metode tatt fra Oblig7 som mottar en String og gir tilbake
    //ett 2D-array av boolean-verdier som representerer loesnings-stien.
    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {

        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));

        while(m.find()) {
            int x = Integer.parseInt(m.group(1))-1;
            int y = Integer.parseInt(m.group(2))-1;
            losning[y][x] = true;
        }
        return losning;
    }

}
