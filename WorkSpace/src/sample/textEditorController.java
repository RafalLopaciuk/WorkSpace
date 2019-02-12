package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class textEditorController {

    @FXML private TextArea documentContent;
    @FXML
    private Stage primaryStage;

    @FXML private javafx.scene.control.MenuItem exitButton;
    @FXML private AnchorPane editScene;

    private String openFile;

    public void openEditorWithFile(String openingFile) throws IOException {
            Stage editionStage = new Stage();
            Parent root2 = FXMLLoader.load(getClass().getResource("fxml/textEditorWindow.fxml"));
            editionStage.setTitle("Text Editor");
            editionStage.getIcons().add(new Image(Main.class.getResourceAsStream("graphic/icon.png")));
            editionStage.setScene(new Scene(root2, 595, 700));
            TextArea documentContentArea = new TextArea();
            editionStage.show();
    }

    public  void openNewFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if( file != null){
            openFile = file.toString();
            documentContent.clear();
            try {
                Scanner s = new Scanner(file);
                String line = "";
                while (s.hasNextLine()) {
                    line = s.nextLine();
                    documentContent.appendText(line + "\n"); // display the found integer
                }
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
            }
        }
    }

    public  void saveAsLastOpen(ActionEvent event) throws IOException {
        File file = new File(openFile);
        SaveFile(documentContent.getText(), file);
    }

    public  void saveAs(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            openFile = file.toString();
            SaveFile(documentContent.getText(), file);
        }
    }

    private void SaveFile(String content, File file){
        try {
            System.out.println(file.toString());
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public  void quitEditor(ActionEvent event) throws IOException {
        Stage stage = (Stage) editScene.getScene().getWindow();
        stage.close();
    }
}
