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
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vinnsla.Leikur;
import vinnsla.Spilari;

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
    private Label fxStig1;       // Label for the score for Player 1
    @FXML
    private Label fxStig2;       // Label for the score for Player 2
    @FXML
    private MenuController menuStyringController; // MenuController item
    public Leikur leikur = new Leikur(); // Leikur instance
    private Timeline gulltimalina;  // Timeline for the gold
    private Timeline leiktimalina;  // Timeline for the gameloop
    private Timeline klukkutimalina; // Timeline for the clock
    private Stage stage;

    /**
     * Initializes the controller.
     * This method is called to initialize a controller after its root element has been
     * completely processed.
     */
    public void initialize() {
        System.out.println("GoldController initialize " + leikur.isTveirSpilarar());

        menuStyringController.setGoldController(this);
        orvatakkar();
        fxKlukka.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(leikur.getKlukka().getTimi());
        }, leikur.getKlukka().getTimiProperty()));

        fxLeikbord.setLeikur(leikur);
        // binds the score in Leikur
        fxStig1.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(leikur.getSpilari1().getStig());
        }, leikur.getSpilari1().stigProperty()));

        fxStig2.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(leikur.getSpilari2().getStig());
        }, leikur.getSpilari2().stigProperty()));
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
     * and sets up key event handler to update directions
     */
    private void orvatakkar() {
        Spilari sp1 = leikur.getSpilari1();
        Spilari sp2 = leikur.getSpilari2();

        sp1.attir.put(KeyCode.UP, Stefna.UPP);
        sp1.attir.put(KeyCode.DOWN, Stefna.NIDUR);
        sp1.attir.put(KeyCode.RIGHT, Stefna.HAEGRI);
        sp1.attir.put(KeyCode.LEFT, Stefna.VINSTRI);

        sp2.attir.put(KeyCode.W, Stefna.UPP);
        sp2.attir.put(KeyCode.S, Stefna.NIDUR);
        sp2.attir.put(KeyCode.D, Stefna.HAEGRI);
        sp2.attir.put(KeyCode.A, Stefna.VINSTRI);

        fxLeikbord.addEventFilter(KeyEvent.ANY, event -> {
            if (leikur.getKlukka().getTimi() > 0) {
                Stefna stefna1 = sp1.yttATakka(event);
                if (stefna1 != null) {
                    fxLeikbord.setStefna(stefna1);
                }

                Stefna stefna2 = sp2.yttATakka(event);
                if (stefna2 != null) {
                    fxLeikbord.setStefna2(stefna2);
                }
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
            leikur.getKlukka().tic();
        });
        klukkutimalina = new Timeline(k);
        klukkutimalina.setCycleCount(leikur.getKlukka().getTimi());
        klukkutimalina.setOnFinished(e -> {
            gulltimalina.stop();
            leiktimalina.stop();

            LeiklokDialog dialog = new LeiklokDialog(leikur);
            dialog.setResultConverter(b -> {
                System.out.println("button data: "+ b.getButtonData());
                // b er af taginu ButtonType
                if (b.getButtonData() == ButtonBar.ButtonData.YES) {
                    System.out.println("Hefja leik" + b.getButtonData());
                    //hefjaLeik();
                    menuStyringController.onNyrLeikur();
                }
                else if(b.getButtonData() == ButtonBar.ButtonData.BACK_PREVIOUS) {
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
                dialog.getDialogPane().getScene().getWindow().hide();
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
        leikur.getSpilari1().setStig(0);
        leikur.getSpilari2().setStig(0);
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

    public void setStage(Stage stage) { this.stage = stage;}
}