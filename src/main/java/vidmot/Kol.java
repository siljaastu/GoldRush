package vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 * Represents a coal piece in the GoldRush game.
 * This class extends Rectangle and is used to display coal pieces on the game board.
 *
 *****************************************************************************/
public class Kol extends Rectangle {

    /**
     * Constructor for Kol. Loads the FXML and catches exception
     */
    public Kol() {
        FXMLLoader fxmlLoader = new FXMLLoader(GoldApplication.class.getResource("kol-view.fxml"));
        fxmlLoader.setRoot(this);       // rótin á viðmótstrénu sett hér
        fxmlLoader.setController(this); // controllerinn hér en ekki í .fxml skránni
        try {
            fxmlLoader.load();          // viðmótstréð lesið inn
        } catch (IOException exception) {
            System.err.println("Fann ekki kol-view.fxml");
        }
    }
}
