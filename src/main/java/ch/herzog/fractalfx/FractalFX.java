package ch.herzog.fractalfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FractalFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Region root = new FXMLLoader(getClass().getResource("/ui/FractalFXUI.fxml")).load();
            root.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            stage.setTitle("FractalFX");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> System.exit(0));
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FractalFX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
