package vidmot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import vinnsla.Leikur;
import vinnsla.Spilari;
import vinnsla.Tonlist;

import java.io.IOException;
import java.util.Random;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 * Represents the game board in the GoldRush game.
 * Extends Pane and is used to manage the game board layout and interactions.
 *
 *****************************************************************************/
public class Leikbord extends Pane {
    private Grafari grafari1;
    private Grafari grafari2;
    private Leikur leikur;
    private ObservableList<Gull> gull = FXCollections.observableArrayList(); // Listi sem heldur utan um gullin
    private ObservableList<Kol> kol = FXCollections.observableArrayList(); // Listi sem heldur utan um kolin
    private final Random random = new Random();   // Random generator
    private Tonlist tonlist = new Tonlist();

    /**
     * Constructor for Leikbord. Loads the FXML and catches exception
     */
    public Leikbord() {
        FXMLLoader fxmlLoader = new FXMLLoader(GoldApplication.class.getResource("leikbord-view.fxml"));
        fxmlLoader.setRoot(this);   // rótin á viðmótstrénu sett hér
        fxmlLoader.setController(this); // controllerinn settur hér en ekki í .fxml skránni
        try {
            fxmlLoader.load();          // viðmótstréð lesið inn (þ.e. .fxml skráin)
        } catch (IOException exception) {
            System.err.println("Fann ekki leikbord-view.fxml");
        }
    }

    /**
     * setjaBord prepares leikbord for a new game
     * Removes the gold and coal and relocates Grafari on leikbord
     */
    public void setjaBord() {
        // Remove previous golddiggers to prevent ghosts
        if (grafari1 != null) {
            this.getChildren().remove(grafari1);
        }

        if (grafari2 != null) {
            this.getChildren().remove(grafari2);
        }

        if (leikur.isTveirSpilarar()) {
            grafari1 = new Grafari();
            grafari1.relocate(20, 200);

            grafari2 = new Grafari();
            grafari2.relocate(120, 200);
            grafari2.setFill(Color.LIMEGREEN);

            this.getChildren().addAll(grafari1, grafari2);
        } else {
            grafari1 = new Grafari();
            grafari1.relocate(20, 200);
            this.getChildren().add(grafari1);

            // TODO: Maybe not necessary?
            grafari2 = null;
        }

        for (Gull molar : gull) {
            // Remove the gold
            this.getChildren().remove(molar);
        }
        gull.clear();

        for (Kol moli : kol) {
            // Remove the coal
            this.getChildren().remove(moli);
        }
        kol.clear();
    }

    public void setLeikur(Leikur leikur) {
        this.leikur = leikur;
    }

    /**
     * Update direction for Grafari 1
     * @param stefna
     */
    public void setStefna(Stefna stefna) {
        grafari1.setStefna(stefna);
    }

    /**
     * Update direction for Grafari 2
     * @param stefna
     */
    public void setStefna2(Stefna stefna) {
        grafari2.setStefna(stefna);
    }

    /**
     * Afram tells grafari in which direction and how far he should move
     */
    public void afram() {
        Spilari spilari1 = leikur.getSpilari1();
        Spilari spilari2 = leikur.getSpilari2();

        faeraGrafara(grafari1);

        if (leikur.isTveirSpilarar()) {
            faeraGrafara(grafari2);
        }

        if (erGrefurGull(grafari1)) {
            spilari1.setStig(spilari1.getStig() + 1);
        }

        if (leikur.isTveirSpilarar() && erGrefurGull(grafari2)) {
            spilari2.setStig(spilari2.getStig() + 1);
        }

        if (erGrefurKol(grafari1)) {
            spilari1.setStig(spilari1.getStig() - 1);
        }

        if (leikur.isTveirSpilarar() && erGrefurKol(grafari2)) {
            spilari2.setStig(spilari2.getStig() - 1);
        }
    }

    private void faeraGrafara(Grafari gr) {
        final double faersla = 10;

        Translate t = switch (gr.getStefna()) {
            case UPP -> new Translate(0, -faersla);
            case NIDUR -> new Translate(0, faersla);
            case VINSTRI -> new Translate(-faersla, 0);
            case HAEGRI -> new Translate(faersla, 0);
            case KYRR -> new Translate(0, 0);
        };
        double x = gr.getLayoutX();
        double y = gr.getLayoutY();

        double nyttX = innanBords(x + t.getX(), 0.0, getWidth() - gr.getWidth());
        double nyttY = innanBords(y + t.getY(), 0.0, getHeight() - gr.getHeight());

        gr.relocate(nyttX, nyttY);   // Location of golddigger updated
    }

    /**
     * Produces more gold g randomly on leikbord
     */
    private void framleidaGull() {
        Gull g = new Gull();
        g.relocate(random.nextInt((int) (getWidth() - g.getWidth())), random.nextInt((int) (getHeight() - g.getHeight())));

        // Add gold to children
        this.getChildren().add(g);

        // Add gold to ObservableList
        gull.add(g);
    }
    
    /**
     * meiraGull calls on framleidaGull
     */
    public void meiraGull() {
        framleidaGull();
    }

    /**
     * Checks if golddigger intersects with gold
     * Removes gold if intersects
     * @return true if intersected with gold, otherwise false
     */
    private boolean erGrefurGull(Grafari gr) {
        for (Gull molar : gull) {
            if (gr.getBoundsInParent().intersects(molar.getBoundsInParent())) {
                // Remove gold piece
                gull.remove(molar);
                tonlist.gullFoundSound();
                this.getChildren().remove(molar);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if golddigger intersects with coal
     * Removes coal if intersects
     * @return true if intersected with coal, otherwise false
     */
    private boolean erGrefurKol(Grafari gr) {
        for (Kol moli : kol) {
            if (gr.getBoundsInParent().intersects(moli.getBoundsInParent())) {
                // Remove coal piece
                kol.remove(moli);
                tonlist.kolFoundSound();
                this.getChildren().remove(moli);
                return true;
            }
        }
        return false;
    }

    /**
     * Produces more kol k randomly on leikbord
     */
    private void framleidaKol() {
        Kol k = new Kol();
        k.relocate(random.nextInt((int) (getWidth() - k.getWidth())), random.nextInt((int) (getHeight() - k.getHeight())));

        // Add kol to children
        this.getChildren().add(k);

        // Add kol to ObservableList
        kol.add(k);
    }

    /**
     * meiraKol calls on framleidaKol
     */
    public void meiraKol() {
        framleidaKol();
    }

    /**
     * innanBord used to make sure we are moving within our gameboard, leikbord
     * @param value value we are looking at
     * @param min min number
     * @param max number
     * @return value that is between min and max
     */
    private static double innanBords(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }
}

