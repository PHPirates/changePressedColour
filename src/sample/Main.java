package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        File file = new File("src/sample/sample.css");
        scene.getStylesheets().add(file.toURI().toString());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
