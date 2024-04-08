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

    private SimpleIntegerProperty spilari1Stig = new SimpleIntegerProperty(); // Simple Integer property for score
    private SimpleIntegerProperty spilari2Stig = new SimpleIntegerProperty(); // Simple Integer property for score
    private int erfidleikastig = 0; // 3 difficulty levels 0,1,2
    private boolean tveirSpilarar = false; // TODO: Property?
    private final int[] timar = {45, 30, 15}; // Time in accordance with difficulty level

    private Klukka klukka = new Klukka(0); // Makes a new clock

    public int getSpilari1Stig() {
        return spilari1Stig.get();
    }

    public void setSpilari1Stig(int spilari1Stig) {
        this.spilari1Stig.set(spilari1Stig);
    }

    public SimpleIntegerProperty spilari1StigProperty() {
        return spilari1Stig;
    }

    public int getSpilari2Stig() {
        return spilari2Stig.get();
    }

    public SimpleIntegerProperty spilari2StigProperty() {
        return spilari2Stig;
    }

    public void setSpilari2Stig(int spilari2Stig) {
        this.spilari2Stig.set(spilari2Stig);
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

    public boolean isTveirSpilarar() {
        return tveirSpilarar;
    }

    public void setTveirSpilarar(boolean tveirSpilarar) {
        this.tveirSpilarar = tveirSpilarar;
    }
}
