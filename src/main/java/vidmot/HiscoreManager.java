package vidmot;

import java.io.*;

public class HiscoreManager {
    private final String path;

    public HiscoreManager() {
        this.path = "src/main/hiscores.txt";
    }

    public int readHiScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateHiScore(int newScore) {
        int currentHighScore = readHiScore();
        if (newScore > currentHighScore) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
                writer.println(newScore);
                return newScore;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return currentHighScore;
    }
}