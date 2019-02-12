package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class mainController implements Initializable {

    @FXML private Stage primaryStage;
    @FXML private TextField ActualDirectory;
    @FXML private ScrollPane scrollPaneWithFolderContetnt;

    private String lastRepositoryPath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public  void openEditor(ActionEvent event) throws IOException {
        textEditorController newWindow = new textEditorController();
        newWindow.openEditorWithFile("123");
    }

    public  void showFolderFiles(ActionEvent event) throws IOException {
        ActualDirectory.clear();
        final DirectoryChooser fileChooser = new DirectoryChooser();
        File path = fileChooser.showDialog(primaryStage);
        if(  path == null){
            ActualDirectory.setText("Select the path to your repository...");
            AnchorPane pane = new AnchorPane();
            scrollPaneWithFolderContetnt.setContent(pane);
        }
        else{
            lastRepositoryPath = path.toString();
            UpdateRepositoryContent(path);
        }
    }

    private void UpdateRepositoryContent(File path){
        if (path != null) {
            ActualDirectory.setText(path.getAbsolutePath());
            File[] fileList = path.listFiles();
            AnchorPane pane = new AnchorPane();

            scrollPaneWithFolderContetnt.setContent(pane);
            String text ="";
            double value = 0;
            if( !path.toString().equals(lastRepositoryPath)) {
                Button backButton = new Button();
                backButton.setText("<-- BACK");
                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String nativeDir = ActualDirectory.getText();
                        System.out.println(nativeDir);
                        nativeDir = nativeDir.substring(0, nativeDir.lastIndexOf(File.separator));
                        System.out.println(nativeDir);
                        if (nativeDir.length() == 2)
                            nativeDir += "\\";
                        File file = new File(nativeDir);
                        UpdateRepositoryContent(file);
                    }
                });
                backButton.setMinWidth(100);
                pane.getChildren().add(backButton);
                value += 30;
            }
            Button [] folderList = new Button[fileList.length];
            Button [] filesList = new Button[fileList.length];
            int FL1 = 0;
            int FL2 = 0;
            for (File f : fileList) {
                Button newButton = new Button();
                newButton.setText(f.getName());
//                button5.setOnAction(new EventHandler<ActionEvent>() {
//                });
                if( f.isDirectory()) {
                    newButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            String folderName = ((Button)event.getSource()).getText();
                            String newPath = ActualDirectory.getText();
                            File file = new File (newPath+ "\\" +folderName);
                            System.out.println(file);
                            UpdateRepositoryContent(file);
                        }
                    });
                    folderList[FL1] = newButton;
                    ++FL1;
                }else{
                    filesList[FL2] = newButton;
                    ++FL2;
                }
            }

            for( int i = 0; i < FL1; ++i) {
                AnchorPane.setTopAnchor(folderList[i], value);
                folderList[i].setStyle("-fx-background-color: transparent; -fx-font-weight: bold;");
                value += 22;
                pane.getChildren().add(folderList[i]);
            }
                value += 3;
            for( int i = 0; i < FL2; ++i) {
                AnchorPane.setTopAnchor(filesList[i], value);
                filesList[i].setStyle("-fx-background-color: transparent; -fx-font-style: italic; -fx-padding: 0 0 0 22");
                value += 22;
                pane.getChildren().add(filesList[i]);
            }

        }
    }

    public  void endApplication(ActionEvent event) throws IOException {
        Stage quitStage = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("fxml/quitScene.fxml"));
        quitStage.setTitle("Exiting?");
        quitStage.getIcons().add(new Image(Main.class.getResourceAsStream("graphic/icon.png")));
        quitStage.setScene(new Scene(root2, 400, 140));
        quitStage.setResizable(false);
        quitStage.setAlwaysOnTop(true);
        quitStage.show();
    }

    public void MouseClickedOnTree(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            System.out.println("You clicked me!");
        }
    }
}
