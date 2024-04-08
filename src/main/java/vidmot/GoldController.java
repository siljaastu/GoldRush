package vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vinnsla.Leikur;

import java.util.HashMap;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 *  Controller class for the GoldRush game.
 *  This class handles the game logic and user interactions.
 *
 */
public class GoldController {
    @FXML
    private Leikbord fxLeikbord; // Leikbord item
    @FXML
    private Label fxKlukka;     // Label for the clock
    @FXML
    private Label fxStig;       // Label for the score
    @FXML
    private MenuController menuStyringController; // MenuController item
    private Leikur leikur = new Leikur(); // Leikur instance
    private Timeline gulltimalina;  // Timeline for the gold
    private Timeline leiktimalina;  // Timeline for the gameloop
    private Timeline klukkutimalina; // Timeline for the clock
    // TODO: Class fyrir spilara?
    private final HashMap<KeyCode, Stefna> spilari1KeyMap = new HashMap<>(); // Makes a map for keycodes and directions for player 1
    private final HashMap<KeyCode, Stefna> spilari2KeyMap = new HashMap<>(); // Makes a map for keycodes and directions for player 2
    private final HashMap<KeyCode, Boolean> spilari1VirkirTakkar = new HashMap<>(); // Makes a map for registering currently held keys for player 1
    private final HashMap<KeyCode, Boolean> spilari2VirkirTakkar = new HashMap<>(); // Makes a map for registering currently held keys for player 2

    /**
     * Initializes the controller.
     * This method is called to initialize a controller after its root element has been
     * completely processed.
     */
    public void initialize() {
        leikur.setTveirSpilarar(true); // TODO: Remove

        menuStyringController.setGoldController(this);
        orvatakkar();
        fxKlukka.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(leikur.getKlukka().getTimi());
        }, leikur.getKlukka().getTimiProperty()));
        fxLeikbord.setLeikur(leikur);
        // binds the score in Leikur
        fxStig.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(leikur.getSpilari1Stig());
        }, leikur.spilari1StigProperty()));
        leikur.getKlukka().getTimiProperty().addListener((observable, old, newValue) -> {
            if (newValue.intValue() < 6) {
                fxKlukka.setStyle("-fx-text-fill: RED;");
            } else if (newValue.intValue() > 5) {
                fxKlukka.setStyle("-fx-text-fill: BLACK;");
            }
        });
        hefjaLeik();
        raesaKlukku();
    }

    /**
     * orvatakkar puts together enum directions and keycodes
     */
    private void orvatakkar() {
        spilari1KeyMap.put(KeyCode.UP, Stefna.UPP);
        spilari1KeyMap.put(KeyCode.DOWN, Stefna.NIDUR);
        spilari1KeyMap.put(KeyCode.RIGHT, Stefna.HAEGRI);
        spilari1KeyMap.put(KeyCode.LEFT, Stefna.VINSTRI);

        spilari2KeyMap.put(KeyCode.W, Stefna.UPP);
        spilari2KeyMap.put(KeyCode.S, Stefna.NIDUR);
        spilari2KeyMap.put(KeyCode.D, Stefna.HAEGRI);
        spilari2KeyMap.put(KeyCode.A, Stefna.VINSTRI);

        fxLeikbord.addEventFilter(KeyEvent.ANY, event -> {
            if (leikur.getKlukka().getTimi() > 0) {
                // TODO: Class fyrir Spilara?
                if (spilari1KeyMap.containsKey(event.getCode())) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        spilari1VirkirTakkar.put(event.getCode(), true);
                        fxLeikbord.setStefna(spilari1KeyMap.get(event.getCode()));
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        spilari1VirkirTakkar.remove(event.getCode());

                        if (spilari1VirkirTakkar.isEmpty()) {
                            fxLeikbord.setStefna(null);
                        } else {
                            // Find next currently held key to update direction
                            KeyCode naestiTakki = spilari1VirkirTakkar.keySet().iterator().next();
                            fxLeikbord.setStefna(spilari1KeyMap.get(naestiTakki));
                        }
                    }
                }

                if (spilari2KeyMap.containsKey(event.getCode())) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        spilari2VirkirTakkar.put(event.getCode(), true);
                        fxLeikbord.setStefna(spilari2KeyMap.get(event.getCode()));
                    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                        spilari2VirkirTakkar.remove(event.getCode());

                        if (spilari2VirkirTakkar.isEmpty()) {
                            fxLeikbord.setStefna(null);
                        } else {
                            // Find next currently held key to update direction
                            KeyCode naestiTakki = spilari2VirkirTakkar.keySet().iterator().next();
                            fxLeikbord.setStefna(spilari2KeyMap.get(naestiTakki));
                        }
                    }
                }
            }
        });
    }

    /**
     * Raesa klukku starts the clock in accordance with level:erfidleikastig
     * When time is finished it shows the LeiklokDialog dialog
     */
    public void raesaKlukku() {
        if (klukkutimalina != null) {
            klukkutimalina.stop();
        }
        leikur.getKlukka().getTimiProperty().set(leikur.getTimar()[leikur.getErfidleikastig()]);
        KeyFrame k = new KeyFrame(Duration.seconds(1), e -> {
            leikur.getKlukka().tic();
        });
        klukkutimalina = new Timeline(k);
        klukkutimalina.setCycleCount(leikur.getKlukka().getTimi());
        klukkutimalina.setOnFinished(e -> {
            gulltimalina.stop();
            leiktimalina.stop();
            LeiklokDialog dialog = new LeiklokDialog(leikur);
            dialog.setResultConverter(b -> {                                 // b er af taginu ButtonType
                if (b.getButtonData() == ButtonBar.ButtonData.OTHER) {
                    menuStyringController.onNyrLeikur();
                } else if (b.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu-view.fxml"));
                        Parent root = fxmlLoader.load();

                        Stage stage = (Stage) fxLeikbord.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }

                }
                return null;
            });
            dialog.show();
        });
        klukkutimalina.play();
    }

    /**
     * Hefja leik clears the table for a new game
     * Generates gold
     */
    public void hefjaLeik() {
        fxLeikbord.setjaBord();
        leikur.setSpilari1Stig(0);
        if (gulltimalina != null) {
            gulltimalina.stop();
        }
        KeyFrame k = new KeyFrame(Duration.seconds(3), //Makes new gold every 3 sec
                e -> fxLeikbord.meiraGull());
        KeyFrame k2 = new KeyFrame(Duration.seconds(5), //Makes new coal every 5 sec
                e -> fxLeikbord.meiraKol());

        gulltimalina = new Timeline(k, k2);            // Connect timeline
        gulltimalina.setCycleCount(Timeline.INDEFINITE);   // how long the timeline runs
        gulltimalina.play();                               // start the timeline

        if (leiktimalina != null) {
            leiktimalina.stop();
        }

        leiktimalina = new Timeline(new KeyFrame(Duration.millis(50),
                e -> fxLeikbord.afram()));            // Connect timeline
        leiktimalina.setCycleCount(Timeline.INDEFINITE);   // how long the timeline runs
        leiktimalina.play();                               // start the timeline
    }

    /**
     * Sets erfidleikastig sets the difficulty level for the game
     *
     * @param erfidleikastig the erfidleikastig
     */
    public void setErfidleikastig(int erfidleikastig) {
        leikur.setErfidleikastig(erfidleikastig);
    }
}