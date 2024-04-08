package vidmot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import vinnsla.Leikur;

import java.io.IOException;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 * Represents a dialog that is shown when a game ends.
 * This class extends Dialog and is used to display a game over message and score.
 *
 *****************************************************************************/

public class LeiklokDialog extends Dialog<Void> {
    private Leikur leikur; // new instance of Leikur
    @FXML
    private Label fxLokastig; // Label for final score

    public LeiklokDialog(Leikur leikur) {
        this.leikur = leikur;

        setTitle("Leik lokið!");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog-view.fxml"));
        try {
            fxmlLoader.setController(this);
            DialogPane dialogPane = fxmlLoader.load();
            setDialogPane(dialogPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getDialogPane().getButtonTypes().addAll(new ButtonType("Já"), (new ButtonType("Valmynd", ButtonBar.ButtonData.CANCEL_CLOSE)));
    }

    public void initialize() {
        fxLokastig.setText("Þú fékkst " + leikur.getStig() + " stig");
    }
}