package vinnsla;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Tonlist {
    MediaPlayer mediaPlayer;

    public void play() {
        String fileName = "backgroundMusic.mp3";
        playHitSound(fileName);
    }

    public void stop() {
        mediaPlayer.stop();
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
