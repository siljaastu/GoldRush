package vinnsla;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Objects;

/**
 * Þessi klasi heldur utan um tónlistina og sound effects í leiknum.
 */
public class Tonlist {
    MediaPlayer mediaPlayer;

    /**
     * Spilar bakgrunn tónlist á meðan leikurinn er í gangi.
     */
    public void play() {
        String backgroundMusic = "backgroundMusic.mp3";
        playHitSound(backgroundMusic);
    }

    /**
     * Stoppar að spila bakground tónlistina
     */
    public void stop() {
        if (this.mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Spilar leik lokið tónlist þegar þegar leikurinn er búinn.
     */
    public void gameOverMusic() {
        String gameOverTonlist = "gameOver.mp3";
        playOnceSound(gameOverTonlist);
    }

    /**
     * Spilar hljóð þegar kol er fundið
     */
    public void kolFoundSound() {
        String kolSound = "kolSound.mp3";
        playOnceSound(kolSound);
    }

    /**
     * Spilar hljóð þegar gull er fundið
     */
    public void gullFoundSound() {
        String gullSound = "gullSound.mp3";
        playOnceSound(gullSound);
    }

    /**
     * Aðferð sem heldur utan um hvort að hljóðið sé spilað einu sinni
     *
     * @param fileName Tekur inn nafnið á filenum sem er að spila
     */
    private void playOnceSound(String fileName) {
        URL resourceUrl = Objects.requireNonNull(getClass().getResource(fileName));
        Media media = new Media(resourceUrl.toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * Aðferð sem heldur utan um hvort að hljóðið sé spilað oftar en einu sinni
     *
     * @param fileName Tekur inn nafnið á filenum sem er að spila
     */
    private void playHitSound(String fileName) {
        URL resourceUrl = Objects.requireNonNull(getClass().getResource(fileName));
        Media media = new Media(resourceUrl.toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
}
