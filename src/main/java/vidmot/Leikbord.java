package vidmot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import vinnsla.Leikur;

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
    @FXML
    private Grafari fxGrafari;
    private Leikur leikur;
    private ObservableList<Gull> gull = FXCollections.observableArrayList(); // Listi sem heldur utan um gullin
    private ObservableList<Kol> kol = FXCollections.observableArrayList(); // Listi sem heldur utan um kolin
    private final Random random = new Random();   // Random generator

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
        fxGrafari.relocate(20, 200);

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

    public void setStefna(Stefna stefna) {
        fxGrafari.setStefna(stefna);
    }

    /**
     * Afram tells grafari in which direction and how far he should move
     */
    public void afram() {
        final double faersla = 10;
        Translate t = switch (fxGrafari.getStefna()) {
            case UPP -> new Translate(0, -faersla);
            case NIDUR -> new Translate(0, faersla);
            case VINSTRI -> new Translate(-faersla, 0);
            case HAEGRI -> new Translate(faersla, 0);
        };
        double x = fxGrafari.getLayoutX();
        double y = fxGrafari.getLayoutY();

        double nyttX = innanBords(x + t.getX(), 0.0, getWidth() - fxGrafari.getWidth());
        double nyttY = innanBords(y + t.getY(), 0.0, getHeight() - fxGrafari.getHeight());

        fxGrafari.relocate(nyttX, nyttY);   // Location of golddigger updated
        if (erGrefurGull()) {
            leikur.setStig(leikur.getStig() + 1);
        }

        if (erGrefurKol()) {
            leikur.setStig(leikur.getStig() - 1);
        }
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
    private boolean erGrefurGull() {

        for (Gull molar : gull) {
            if (fxGrafari.getBoundsInParent().intersects(molar.getBoundsInParent())) {
                // Remove gold piece
                gull.remove(molar);
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
    private boolean erGrefurKol() {
        for (Kol moli : kol) {
            if (fxGrafari.getBoundsInParent().intersects(moli.getBoundsInParent())) {
                // Remove coal piece
                kol.remove(moli);
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

