package vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import vidmot.Stefna;

import java.util.HashMap;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 *  This class is used to manage points for a player in the game
 *  as well as how the golddigger for said player moves on the gameboard.
 *
 *****************************************************************************/
public class Spilari {
    public final HashMap<KeyCode, Stefna> attir = new HashMap<>(); // Makes a map for keycodes and directions for player 1
    public final HashMap<KeyCode, Boolean> virkirTakkar = new HashMap<>(); // Makes a map for registering currently held keys for player 1
    private final SimpleIntegerProperty stig = new SimpleIntegerProperty(); // Simple Integer property for score

    public Stefna yttATakka(KeyEvent event) {
        if (attir.containsKey(event.getCode())) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                virkirTakkar.put(event.getCode(), true);
                return attir.get(event.getCode());
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                virkirTakkar.remove(event.getCode());

                if (virkirTakkar.isEmpty()) {
                    return Stefna.KYRR;
                } else {
                    // Find next currently held key to update direction
                    KeyCode naestiTakki = virkirTakkar.keySet().iterator().next();
                    return attir.get(naestiTakki);
                }
            }
        }
        return null;
    }

    public int getStig() {
        return stig.get();
    }

    public SimpleIntegerProperty stigProperty() {
        return stig;
    }

    public void setStig(int stig) {
        this.stig.set(stig);
    }
}
