package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

public class deleteController {

    @FXML private TextField ActualDirectory;

    public void setPath(String ActualDirectory){
        this.ActualDirectory.setText(ActualDirectory);
    }

    public void deleteYes (ActionEvent event) throws IOException{
        String path = ActualDirectory.getText();
        File file = new File(path);
        RandomAccessFile raf=new RandomAccessFile(file,"rw");
        raf.close();
        if(file.delete())
            System.out.println("Deleted");
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();

    }

    public void deleteNo(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
