package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class quitController {

    @FXML private javafx.scene.control.Button closeButton;

    public  void cancelExit(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public  void endOfApplication(ActionEvent event) throws IOException {
        Platform.exit();
        System.exit(0);
    }
}
