package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main application class for the GoldRush game.
 * This class extends the Application class from JavaFX.
 */

public class GoldApplication extends Application {

    /**
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * @throws IOException if an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GoldApplication.class.getResource("main-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GoldRush");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}