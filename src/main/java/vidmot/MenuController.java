package vidmot;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 * Controller class for the game menu.
 * This class handles the game menu interactions and logic.
 *
 */
public class MenuController {
    @FXML
    private GoldController goldController; // GoldController item

    /**
     * Sets gold controller.
     *
     * @param aThis GoldController
     */
    public void setGoldController(GoldController aThis) {
        goldController = aThis;
    }

    /**
     * Handler for about the game
     * @param event
     */
    @FXML
    private void onUmForritid(ActionEvent event) {
        Alert forritid = new Alert(Alert.AlertType.INFORMATION);
        forritid.setTitle("Um GoldRush");
        forritid.setHeaderText("GoldRush leikurinn");
        forritid.setContentText("Þessi leikur er gerður af Silju Ástudóttur í áfanganum Viðmótsforritun í Háskóla Íslands 2024.");
        forritid.showAndWait();
    }

    /**
     * Handler for on quit
     * @param event
     */
    @FXML
    private void onHaetta(ActionEvent event) {
        Alert haetta = new Alert(Alert.AlertType.CONFIRMATION);
        haetta.setTitle("Loka leik");
        haetta.setHeaderText("Hætta í leiknum");
        haetta.setContentText("Ertu viss um að þú viljir loka forritinu?");
        Optional<ButtonType> opt = haetta.showAndWait();
        opt.ifPresent(takki -> {
            if (takki == ButtonType.OK) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    /**
     * Handler for on difficulty level
     * @param event
     */
    @FXML
    private void onErfidleikastig(ActionEvent event) {
        RadioMenuItem erfidleiki = (RadioMenuItem) event.getSource();
        int thyngd = switch (erfidleiki.getId()) {
            case "lett" -> 0;
            case "midlungs" -> 1;
            case "erfitt" -> 2;
            default -> 0;
        };
        goldController.setErfidleikastig(thyngd);
        onNyrLeikur(); // Starts a new game with the selected level
    }

    /**
     * On nyr leikur starts a new game
     * Calls on start game and start clock
     */
    @FXML
    public void onNyrLeikur() {
        goldController.hefjaLeik();
        goldController.raesaKlukku();
    }
}
