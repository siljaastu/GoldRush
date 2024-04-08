package vidmot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HiscoreManager {
    private final String path;

    public HiscoreManager(String filePath) {
        this.path = filePath;
    }

    public List<String> readHiScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public void updateHiScores(String playerName, int playerScore) {
        List<String> scores = readHiScores();

        boolean scoreAdded = false;
        for (int i = 0; i < scores.size(); i++) {
            String[] parts = scores.get(i).split(": ");
            int existingScore = Integer.parseInt(parts[1]);
            if (playerScore > existingScore) {
                scores.add(i, (i + 1) + ". " + playerName + ": " + playerScore);
                scoreAdded = true;
                break;
            }
        }

        if (!scoreAdded && scores.size() < 5) {
            scores.add(scores.size() + ". " + playerName + ": " + playerScore);
        }

        List<String> updatedScores = new ArrayList<>();
        for (int i = 0; i < Math.min(scores.size(), 5); i++) {
            String[] parts = scores.get(i).split(": ");
            updatedScores.add((i + 1) + ". " + parts[1]);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (String scoreLine : updatedScores) {
                writer.println(scoreLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
