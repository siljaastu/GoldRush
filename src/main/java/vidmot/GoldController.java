package vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
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
    private Timeline klukkutimalina; // Timeline for the clock
    private HashMap<KeyCode, Stefna> attir = new HashMap<>(); // Makes a map for keycodes and directions
    private Stage stage;
    /**
     * Initializes the controller.
     * This method is called to initialize a controller after its root element has been
     * completely processed.
     */
    public void initialize() {
        menuStyringController.setGoldController(this);
        orvatakkar();
        fxKlukka.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(leikur.getKlukka().getTimi());
        }, leikur.getKlukka().getTimiProperty()));
        fxLeikbord.setLeikur(leikur);
            // binds the score in Leikur
        fxStig.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(leikur.getStig());
        }, leikur.stigProperty()));
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
        attir.put(KeyCode.UP, Stefna.UPP);
        attir.put(KeyCode.DOWN, Stefna.NIDUR);
        attir.put(KeyCode.RIGHT, Stefna.HAEGRI);
        attir.put(KeyCode.LEFT, Stefna.VINSTRI);
        fxLeikbord.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (leikur.getKlukka().getTimi() > 0) {
                fxLeikbord.setStefna(attir.get(event.getCode()));
                fxLeikbord.afram();
            }
        });
    }

    public void onMainMenu() {

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
            System.out.println(leikur.getErfidleikastig());
            leikur.getKlukka().tic();
        });
        klukkutimalina = new Timeline(k);
        klukkutimalina.setCycleCount(leikur.getKlukka().getTimi());
        klukkutimalina.setOnFinished(e -> {
            gulltimalina.stop();

            LeiklokDialog dialog = new LeiklokDialog(leikur);
            dialog.setResultConverter(b -> {                                 // b er af taginu ButtonType
                if (b.getButtonData() == ButtonBar.ButtonData.OTHER) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("goldrush-view.fxml"));
                        Parent root = fxmlLoader.load();

                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
//                    menuStyringController.onNyrLeikur();
                }
                else if(b.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu-view.fxml"));
                        Parent root = fxmlLoader.load();
                        System.out.println("fxleikbord: " + fxKlukka);
                        System.out.println("Stage: "+stage);
                        System.out.println("Stage Scene: "+ fxKlukka.getScene());
                        System.out.println("Stage Scene Window: "+ fxKlukka.getScene().getWindow());
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
                else if (b.getButtonData() == ButtonBar.ButtonData.FINISH) {
                    try {
                        menuStyringController.onHaetta();
                    } catch (Exception ignored){}
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
        leikur.setStig(0);
        if (gulltimalina != null) {
            gulltimalina.stop();
        }
        KeyFrame k = new KeyFrame(Duration.seconds(3), //Makes new gold every 3 sec
                e -> fxLeikbord.meiraGull());

        gulltimalina = new Timeline(k);                     // Connect timeline
        gulltimalina.setCycleCount(Timeline.INDEFINITE);   // how long the timeline runs
        gulltimalina.play();                               // start the timeline
    }

    /**
     * Sets erfidleikastig sets the difficulty level for the game
     *
     * @param erfidleikastig the erfidleikastig
     */
    public void setErfidleikastig(int erfidleikastig) {
        leikur.setErfidleikastig(erfidleikastig);
    }

    public void setStage(Stage stage) { this.stage = stage;}
}