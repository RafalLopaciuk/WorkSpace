package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/mainScene.fxml"));
        primaryStage.setTitle("Work Space");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("graphic/icon.png")));
        primaryStage.setScene(new Scene(root, 480, 640));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
