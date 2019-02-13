package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class csvReaderController {

    @FXML private Stage primaryStage;
    @FXML private ScrollPane ArrayArea;
    @FXML private Label TableLabel;

    public void openCsvReaderController() throws IOException {
        Stage editionStage = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("fxml/csvReader.fxml"));
        editionStage.setTitle("CSV Reader");
        editionStage.getIcons().add(new Image(Main.class.getResourceAsStream("graphic/icon.png")));
        editionStage.setScene(new Scene(root2, 652, 492));
        editionStage.show();
    }

    public  void openNewCsv(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV filse (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if( file != null) {
            TableLabel.setText(file.getName());
            TableView<ObservableList<String>> tableView = new TableView<>();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            String cvsSplitBy = ",";

            String tmp;
            String[] firstList = line.split(cvsSplitBy);
            for(int i = 0; i < firstList.length; ++i){
                tmp = firstList[i];
                if( tmp.charAt(0) == '"' || tmp.charAt(0) == ' ') tmp = tmp.substring(1,tmp.length()-1);
                if( tmp.charAt(1) == '"') tmp = tmp.substring(2,tmp.length()-1);
                if( tmp.charAt(tmp.length()-1) == '"') tmp = tmp.substring(0,tmp.length()-2);
                firstList[i] = tmp;
            }

            List<String> firstListList = new ArrayList<>();
            firstListList.addAll(Arrays.asList(firstList));
            List<String> columnNames = firstListList;
            for (int i = 0; i < columnNames.size(); i++) {
                final int finalIdx = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                        columnNames.get(i)
                );
                column.setCellValueFactory(param ->
                        new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );
                tableView.getColumns().add(column);
            }

            while ((line = br.readLine()) != null) {
                firstList = line.split(cvsSplitBy);
                firstListList.clear();
                firstListList.addAll(Arrays.asList(firstList));
                tableView.getItems().add(
                        FXCollections.observableArrayList(
                                firstListList
                        )
                );
            }

            AnchorPane pane = new AnchorPane();
            pane.getChildren().add(tableView);
            ArrayArea.setContent(pane);

        }
    }
}
