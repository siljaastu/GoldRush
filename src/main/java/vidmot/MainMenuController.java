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

    private HiscoreManager hiscoreManager = new HiscoreManager("src/main/hiscores.txt");


    public void initialize(){
        displayHiscore();
    }



    private void displayHiscore(){
        StringBuilder stringBuilder = new StringBuilder();
        for(String score : hiscoreManager.readHiScores()){
            stringBuilder.append(score).append("\n");
        }
        hiscoreLabel.setText(stringBuilder.toString());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("goldrush-view.fxml"));
            Parent sampleRoot = loader.load();
            Scene sampleScene = new Scene(sampleRoot);

            // Get the current stage (window) using the event's source
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            GoldController controller = loader.getController();
            controller.setStage(stage);

            // Set the new scene on the current stage
            stage.setScene(sampleScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}