package vinnsla;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Tonlist {
    MediaPlayer mediaPlayer;

    public void play() {
        String backgroundMusic = "backgroundMusic.mp3";
        playHitSound(backgroundMusic);
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void gameOverMusic() {
        String gameOverTonlist = "gameOver.mp3";
        playOnceSound(gameOverTonlist);
    }

    public void kolFoundSound() {
        String kolSound = "kolSound.mp3";
        playOnceSound(kolSound);
    }

    public void gullFoundSound() {
        String gullSound = "gullSound.mp3";
        playOnceSound(gullSound);
    }

    private void playOnceSound(String fileName) {
        String path = Objects.requireNonNull(getClass().getResource(fileName)).getPath();
        System.out.println(path);
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    private void playHitSound(String fileName) {
        String path = Objects.requireNonNull(getClass().getResource(fileName)).getPath();
        System.out.println(path);
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


}
