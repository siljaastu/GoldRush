package vinnsla;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.time.temporal.ChronoUnit;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 * Represents a clock in the GoldRush game.
 * This class is used to manage the game timer.
 *
 */
public class Klukka {
    private SimpleIntegerProperty timi; // Simple Integer property for time

    /**
     * Instantiates a new Klukka.
     *
     * @param timi the time
     */
    public Klukka(int timi) {
        this.timi = new SimpleIntegerProperty(timi);
    }

    /**
     * Tic. is a method to countdown the time
     */
    public void tic() {
        timi.setValue(timi.getValue() - 1);
    }

    public int getTimi() {
        return timi.get();
    }

    public SimpleIntegerProperty getTimiProperty() {
        return timi;
    }
}
