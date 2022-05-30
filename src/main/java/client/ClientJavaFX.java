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
            // = FXMLLoader.load(getClass().getClassLoader().getResource("/ClientJavaFX.fxml"));
            //Group root = new Group();
            Scene scene = new Scene(root, 500, 500);
            //Stage stage = new Stage();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*String sceneFile = "../src/main/resources/client/ClientJavaFX.fxml";
        Parent root = null;
        URL url  = null;
        try
        {
            url  = getClass().getResource( sceneFile );
            root = FXMLLoader.load( url );
            System.out.println( "  fxmlResource = " + sceneFile );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            //throw ex;
        }*/
    }
}