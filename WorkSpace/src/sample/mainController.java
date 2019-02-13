package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class mainController {

    @FXML private Stage primaryStage;
    @FXML private TextField ActualDirectory;
    @FXML private ScrollPane scrollPaneWithFolderContetnt;
    @FXML private Button openRepButton;
    private String lastRepositoryPath;
    private String lastRightClick;
    @FXML private String deletingPath;

    public  void openEditor(ActionEvent event) throws IOException {
        textEditorController newWindow = new textEditorController();
        newWindow.openEditorWithFile();
    }

//    private void openEditorWindow(ActionEvent event) throws IOException {
//        textEditorController newWindow = new textEditorController();
//        try {
//            newWindow.openEditorWithFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        newWindow.openFileFromButton(ActualDirectory.getText()+"\\"+((Button)event.getSource()).getText());
//    }

    public  void openCsvReader(ActionEvent event) throws IOException {
        csvReaderController newWindow = new csvReaderController();
        newWindow.openCsvReaderController();
    }

    public  void showFolderFiles(ActionEvent event) throws IOException {
        ActualDirectory.clear();
        final DirectoryChooser fileChooser = new DirectoryChooser();
        File path = fileChooser.showDialog(primaryStage);
        if(  path == null){
            ActualDirectory.setText("Select the path to your repository...");
            AnchorPane pane = new AnchorPane();
            scrollPaneWithFolderContetnt.setContent(pane);
            openRepButton.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        }
        else{
            lastRepositoryPath = path.toString();
            openRepButton.setStyle("-fx-font-style: italic;");
            UpdateRepositoryContent(path);
        }
    }

    public String ActualPath(){
        return ActualDirectory.getText();
    }

    private void UpdateRepositoryContent(File path){
        if (path != null) {
            ContextMenu contextMenu = new ContextMenu();
            Label lbl = new Label("Delete");
            MenuItem deleteFile = new CustomMenuItem(lbl);
            lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @FXML
                public void handle(MouseEvent event) {
                    String ActualDirectory_Text = ActualDirectory.getText() +"\\"+ lastRightClick;
                    Stage deleteScene = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/deleteScene.fxml"));
                    Parent root4 = null;
                    try {
                        root4 = (Parent)loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    deleteController deleteCon = loader.getController();
                    deleteCon.setPath(ActualDirectory_Text);
                    deleteScene.setTitle("Deleting?");
                    deleteScene.getIcons().add(new Image(Main.class.getResourceAsStream("graphic/icon.png")));
                    deleteScene.setScene(new Scene(root4, 400, 140));
                    deleteScene.setResizable(false);
                    deleteScene.setAlwaysOnTop(true);
                    deleteScene.show();
                    UpdateRepositoryContent(new File(ActualDirectory.getText()));
                }
            });
            contextMenu.getItems().add(deleteFile);

            ActualDirectory.setText(path.getAbsolutePath());
            File[] fileList = path.listFiles();
            AnchorPane pane = new AnchorPane();
            scrollPaneWithFolderContetnt.setContent(pane);
            String text ="";
            double value = 0;
            if( !path.toString().equals(lastRepositoryPath)) {
                Button backButton = new Button();
                backButton.setText("BACK");
                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String nativeDir = ActualDirectory.getText();
                        nativeDir = nativeDir.substring(0, nativeDir.lastIndexOf(File.separator));
                        if (nativeDir.length() == 2)
                            nativeDir += "\\";
                        File file = new File(nativeDir);
                        UpdateRepositoryContent(file);
                    }
                });
                backButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold;");
                backButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("graphic/back.png"))));
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
                newButton.setId(f.getName());
                newButton.setContextMenu(contextMenu);
                newButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @FXML
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            lastRightClick = ((Button) event.getSource()).getText();
                        }
                    }
                });
                if( f.isDirectory()) {
                    newButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent event) {
                            String folderName = ((Button)event.getSource()).getText();
                            String newPath = ActualDirectory.getText();
                            File file = new File (newPath+ "\\" +folderName);
                            UpdateRepositoryContent(file);
                        }
                    });
                    newButton.setStyle("-fx-background-color: transparent; -fx-font-style: italic;");
                    newButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("graphic/folder.png"))));
                    folderList[FL1] = newButton;
                    ++FL1;
                }else{
                    if(f.getName().substring(f.getName().lastIndexOf(".")+1,f.getName().length()).equals("txt"))
                        newButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("graphic/txt.png"))));
                    else if(f.getName().substring(f.getName().lastIndexOf(".")+1,f.getName().length()).equals("csv"))
                        newButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("graphic/csv.png"))));
                    newButton.setStyle("-fx-background-color: transparent;");
                    filesList[FL2] = newButton;
                    ++FL2;
                }
            }

            for( int i = 0; i < FL1; ++i) {
                AnchorPane.setTopAnchor(folderList[i], value);
                value += 22;
                pane.getChildren().add(folderList[i]);
            }
                value += 3;
            for( int i = 0; i < FL2; ++i) {
                AnchorPane.setTopAnchor(filesList[i], value);
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