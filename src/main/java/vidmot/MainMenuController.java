package vidmot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MainMenuController {
    Boolean isOnePlayer;
    @FXML
    private Label hiscoreLabel;
    public void initialize(){
        displayHiScores();
    }


    //Lesari fyrir hiscores.txt file sem birtir hiscores á valmynd
    private void displayHiScores() {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Paths.get("src/main/hiscores.txt").toFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n"); 
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            contentBuilder.append("Hiscores skrá ekki fundin."); 
        }
        
        hiscoreLabel.setText(contentBuilder.toString()); 
    }








    // Takkinn ef valið er einn leikmann
    @FXML
    private void handleOnePlayer(ActionEvent event) {
        isOnePlayer = true;
        swapToGame(event);
        getPlayerAmount();
    }
    
    // Takkinn ef valið er tvo leikmenn
    @FXML
    private void handleTwoPlayer(ActionEvent event) {
        isOnePlayer = false;
        swapToGame(event);
        getPlayerAmount();
    }

    // Skilar true ef það er 1 leikmaður og false ef það eru tveir leikmenn
    private Boolean getPlayerAmount(){
        System.out.println("There is one player: " + isOnePlayer);
        return this.isOnePlayer;
    }




    //Method til þess að skipta yfir í goldrush view fxml
    private void swapToGame(ActionEvent event){
        try {
            // Load the FXML for the sample scene
            Parent sampleRoot = FXMLLoader.load(getClass().getResource("goldrush-view.fxml"));
            Scene sampleScene = new Scene(sampleRoot);

            // Get the current stage (window) using the event's source
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            stage.setScene(sampleScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}