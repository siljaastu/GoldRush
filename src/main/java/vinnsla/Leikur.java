package vinnsla;


/******************************************************************************
 *  Nafn: Silja Ástudóttir, tölvupóstur: sia62@hi.is
 *
 * Represents the game state in the GoldRush game.
 * This class manages the game's score, level, and other game-related data.
 * Has necessary getters and setters.
 */
public class Leikur {

    private int erfidleikastig = 0; // 3 difficulty levels 0,1,2
    public static boolean tveirSpilarar = false; //
    private final int[] timar = {60, 45, 30}; // Time in accordance with difficulty level
    private final Klukka klukka = new Klukka(0); // Makes a new clock
    private final Spilari spilari1 = new Spilari();
    private final Spilari spilari2 = new Spilari();

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
}
