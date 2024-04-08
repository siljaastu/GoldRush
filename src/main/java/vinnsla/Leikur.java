package vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 * Represents the game state in the GoldRush game.
 * This class manages the game's score, level, and other game-related data.
 * Has necessary getters and setters.
 *
 */
public class Leikur {

    private SimpleIntegerProperty stig = new SimpleIntegerProperty(); // Simple Integer property for score
    private int erfidleikastig = 0; // 3 difficulty levels 0,1,2
    private final int[] timar = {60, 30, 15}; // Time in accordance with difficulty level
    private Klukka klukka = new Klukka(0); // Makes a new clock

    public int getStig() {
        return stig.get();
    }

    public void setStig(int stig) {
        this.stig.set(stig);
    }

    public SimpleIntegerProperty stigProperty() {
        return stig;
    }

    public int getErfidleikastig() {
        return erfidleikastig;
    }

    public void setErfidleikastig(int erfidleikastig) {
        this.erfidleikastig = erfidleikastig;
    }

    public int[] getTimar() {
        return timar;
    }

    public Klukka getKlukka() {
        return klukka;
    }
}
