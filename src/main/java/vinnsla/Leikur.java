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

    private int erfidleikastig = 0; // 3 difficulty levels 0,1,2
    public static boolean tveirSpilarar = false; // TODO: Property?
    private final int[] timar = {45, 30, 15}; // Time in accordance with difficulty level
    private Klukka klukka = new Klukka(0); // Makes a new clock
    private Spilari spilari1 = new Spilari();
    private Spilari spilari2 = new Spilari();

    public Spilari getSpilari1() {
        return spilari1;
    }

    public Spilari getSpilari2() {
        return spilari2;
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
