package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class ClientJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ClientJavaFX.fxml"));
            root = fxmlLoader.load();
            Scene scene = new Scene(root);

            primaryStage.setMaximized(true);
            primaryStage.setFullScreen(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}